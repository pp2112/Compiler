package code_generator;

import antlr.WaccBaseVisitor;
import antlr.WaccParser;
import generator.CodeGenerator;
import generator.ForLoopGenerator;
import generator.FunctionGenerator;
import generator.MessageGenerator;
import generator.RegList;

import java.io.PrintStream;
import java.util.Stack;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

import code.Address;
import code.Code;
import code.Constant;
import code.Immediate;
import code.Label;
import code.Operand;
import code.Operation;
import code.Register;
import semantic_check.ArrayType;
import semantic_check.BigType;
import semantic_check.EnumType;
import semantic_check.PairType;
import semantic_check.SingleType;
import semantic_check.Type;

public class CodeGenWalker extends WaccBaseVisitor<Integer> {

	private final int FOUR_BYTE = 4;
	private final int SINGLE_BYTE = 1;

	private int index;
	private int maxIndex;
	private int msgCount;
	private int branchCount;
	private int orBranchCount;
	private FlagSet flagSet;
	private CodeUtil codeUtil;
	private Stack<Integer> branchStack;
	private Stack<Integer> reservedByteStack;
	private FunctionGenerator main;
	private FunctionGenerator currentGenerator;
	private CodeGenerator codeGenerator;
	private PrintStream outputStream;
	private PreCodeGenWalker walker;
	private int nestedArrays = 0;

	public CodeGenWalker(PreCodeGenWalker walker, PrintStream outputStream) {

		this.walker = walker;
		this.index = 0;
		this.maxIndex = 0;
		this.msgCount = 0;
		this.branchCount = 0;
		this.orBranchCount = 0;
		this.flagSet = new FlagSet();
		this.codeUtil = new CodeUtil();
		this.branchStack = new Stack<Integer>();
		this.reservedByteStack = new Stack<Integer>();
		this.main = new FunctionGenerator("main");
		this.currentGenerator = main;
		this.codeGenerator = new CodeGenerator(outputStream);
		this.outputStream = outputStream;
	}

	@Override
	public Integer visitProgram(WaccParser.ProgramContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitParamList(WaccParser.ParamListContext ctx) {
		int childCount = ctx.getChildCount();
		int paramOffset = 0;
		for (int i = 0; i < childCount; i += 2) {
			String id = ctx.getChild(i).getChild(1).getText();
			Type type = getType(ctx.getChild(i).getChild(0));
			currentGenerator.addLabel(id, paramOffset, type);
			paramOffset += getOffSetValue(ctx.getChild(i));
		}

		return super.visitChildren(ctx);
	}

	private int getOffSetValue(ParseTree child) {
		if (matchKeyword(child.getChild(0), "CHAR") || (matchKeyword(child.getChild(0), "BOOL"))) {
			return 1;
		} else if (matchKeyword(child.getChild(0), "INT") || (matchKeyword(child.getChild(0), "STRING"))) {
			return 4;
		} else {
			// For Pairs and Arrays
			return 4;
		}
	}

	@Override
	public Integer visitParam(WaccParser.ParamContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitFunc(WaccParser.FuncContext ctx) {
		String funcName = "f_" + ctx.getChild(1).getText();
		if (ctx.getChild(3) instanceof WaccParser.ParamListContext) {
			for (int i = 0; i < ctx.getChild(3).getChildCount(); i += 2) {
				funcName += "_";
				ParseTree paramType = ctx.getChild(3).getChild(i).getChild(0);
				if (paramType.getChildCount() > 1) {
					// the array case
					Type t = getType(paramType);
					funcName += t.getEnumType().toString().toLowerCase();
					while (paramType.getChildCount() > 1) {
						funcName += "arr";
						paramType = paramType.getChild(0);
					}
				} else {
					funcName += paramType.getText();
				}
			}
		}
		// System.out.println(funcName);
		FunctionGenerator funcGenerator = new FunctionGenerator(funcName, currentGenerator);
		currentGenerator = funcGenerator;
		reservedByteStack.push(index);
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		currentGenerator.setCurrentByte(currentGenerator.getMaxByte());
		currentGenerator.addDirective("ltorg");
		codeGenerator.addFunction(currentGenerator);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitArgList(WaccParser.ArgListContext ctx) {
		return super.visitChildren(ctx);
	}

	// ==================== STAT ==================== //

	@Override
	public Integer visitStat_type_ident_equals_assignRHS(WaccParser.Stat_type_ident_equals_assignRHSContext ctx) {

		Type type = getType(ctx.getChild(0));
		String id = ctx.getChild(1).getText();
		int offset = getTypeOffset(type);
		currentGenerator.updateCurrentByte(-offset);
		currentGenerator.addLabel(id, currentGenerator.getCurrentByte(), type);
		evaluateAssignRhs(ctx.getChild(3));
		evaluateAssignLhs(ctx.getChild(1));

		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStat_assignLHS_unaryAssignOp(WaccParser.Stat_assignLHS_unaryAssignOpContext ctx) {
		if (ctx.getChild(1) instanceof WaccParser.UnaryAssignOp_plusContext) {
			increment(ctx.getChild(0));
		} else if (ctx.getChild(1) instanceof WaccParser.UnaryAssignOp_minusContext) {
			decrement(ctx.getChild(0));
		}
		return visitChildren(ctx);
	}

	private void increment(ParseTree ctx) {
		evaluateAssignRhs(ctx);
		currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
		currentGenerator.updateAllOffset(FOUR_BYTE);
		currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Constant(1)));
		currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
		currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0)));
		currentGenerator.updateAllOffset(-FOUR_BYTE);
		currentGenerator.add(new Operation(Operand.ADDS, Register.r0, Register.r0, Register.r1));
		currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
		flagSet.setFlag(Flags.OVERFLOW);
		if (ctx instanceof WaccParser.AssignRhsContext) {
			evaluateAssignLhs(ctx.getChild(0));
		} else {
			evaluateAssignLhs(ctx);
		}
	}

	private void decrement(ParseTree ctx) {
		evaluateAssignRhs(ctx.getChild(0));
		currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
		currentGenerator.updateAllOffset(FOUR_BYTE);
		currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Constant(1)));
		currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
		currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0)));
		currentGenerator.updateAllOffset(-FOUR_BYTE);
		currentGenerator.add(new Operation(Operand.SUBS, Register.r0, Register.r0, Register.r1));
		currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
		flagSet.setFlag(Flags.OVERFLOW);
		if (ctx instanceof WaccParser.AssignRhsContext) {
			evaluateAssignLhs(ctx.getChild(0));
		} else {
			evaluateAssignLhs(ctx);
		}
	}

	@Override
	public Integer visitStat_assignLHS_assignOp_assignRHS(WaccParser.Stat_assignLHS_assignOp_assignRHSContext ctx) {
		ParseTree assignOp = ctx.getChild(1);
		ParseTree RHS = ctx.getChild(2);
		if (ctx.getChildCount() == 4 && ctx.getChild(2) instanceof WaccParser.UnaryAssignOp_plusContext) {
			RHS = ctx.getChild(3);
			increment(RHS);
		} else if (ctx.getChildCount() == 4 && ctx.getChild(2) instanceof WaccParser.UnaryAssignOp_minusContext) {
			RHS = ctx.getChild(3);
			decrement(RHS);
		}
		if (assignOp instanceof WaccParser.AssignOp_equalsContext) {
			evaluateAssignRhs(RHS);
			evaluateAssignLhs(ctx.getChild(0));
		} else if (assignOp instanceof WaccParser.AssignOp_plus_equalsContext) {
			evaluateAssignRhs(ctx.getChild(0));
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(FOUR_BYTE);
			evaluateAssignRhs(RHS);
			currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(-FOUR_BYTE);
			currentGenerator.add(new Operation(Operand.ADDS, Register.r0, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
			flagSet.setFlag(Flags.OVERFLOW);
			evaluateAssignLhs(ctx.getChild(0));
		} else if (assignOp instanceof WaccParser.AssignOp_minus_equalsContext) {
			evaluateAssignRhs(ctx.getChild(0));
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(FOUR_BYTE);
			evaluateAssignRhs(RHS);
			currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(-FOUR_BYTE);
			currentGenerator.add(new Operation(Operand.SUBS, Register.r0, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
			flagSet.setFlag(Flags.OVERFLOW);
			evaluateAssignLhs(ctx.getChild(0));
		}
		if (ctx.getChildCount() == 4 && ctx.getChild(3) instanceof WaccParser.UnaryAssignOp_plusContext) {
			increment(RHS);
		} else if (ctx.getChildCount() == 4 && ctx.getChild(3) instanceof WaccParser.UnaryAssignOp_minusContext) {
			decrement(RHS);
		}
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStat_Return_expr(WaccParser.Stat_Return_exprContext ctx) {
		evaluateExpr(ctx.getChild(1));
		int offset = getFunctionMaxByte(currentGenerator);
		if (offset != 0) {
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, offset)));
			// currentGenerator.updateAllOffset(-offset);
		}

		currentGenerator.add(new Operation(Operand.POP, new RegList(Register.pc)));
		// currentGenerator.updateAllOffset(-FOUR_BYTE);

		return super.visitChildren(ctx);
	}

	private int getFunctionMaxByte(FunctionGenerator generator) {
		if ((generator.getLabel() != null) && generator.getPrevious() == main)
			return generator.getMaxByte();
		else
			return generator.getMaxByte() + getFunctionMaxByte(generator.getPrevious());

	}

	@Override
	public Integer visitStat_Print_expr(WaccParser.Stat_Print_exprContext ctx) {
		ParseTree child = ctx.getChild(1);
		flagSet.setFlag(Flags.PRINT);
		evaluateExpr(child);
		generatePrintType(child);
		return super.visitStat_Print_expr(ctx);
	}

	@Override
	public Integer visitStat_Println_expr(WaccParser.Stat_Println_exprContext ctx) {
		ParseTree child = ctx.getChild(1);
		flagSet.setFlag(Flags.PRINTLN);
		evaluateExpr(child);
		generatePrintType(child);
		currentGenerator.add(new Operation(Operand.BL, new Label("p_print_ln")));
		return super.visitStat_Println_expr(ctx);
	}

	private void generatePrintType(ParseTree tree) {

		if (tree instanceof WaccParser.Expr_intContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
			flagSet.setFlag(Flags.PRINTINT);

		} else if (tree instanceof WaccParser.Expr_boolContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_bool")));
			flagSet.setFlag(Flags.PRINTBOOL);

		} else if (tree instanceof WaccParser.Expr_charContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("putchar")));

		} else if (tree instanceof WaccParser.Expr_strContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_string")));
			flagSet.setFlag(Flags.PRINTSTRING);

		} else if (tree instanceof WaccParser.Expr_pairlitContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_reference")));
			flagSet.setFlag(Flags.PRINTREFERENCE);

		} else if (tree instanceof WaccParser.Expr_arrContext) {
			Type type = (Type) getType(tree).getType();
			if (type instanceof SingleType) {
				if (type.getEnumType().equals(EnumType.INT)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
					flagSet.setFlag(Flags.PRINTINT);
				} else if (type.getEnumType().equals(EnumType.CHAR)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("putchar")));
				} else if (type.getEnumType().equals(EnumType.BOOL)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_bool")));
					flagSet.setFlag(Flags.PRINTBOOL);
				} else if (type.getEnumType().equals(EnumType.STRING)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_string")));
					flagSet.setFlag(Flags.PRINTSTRING);
				}

			} else if (type instanceof PairType) {
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_reference")));
				flagSet.setFlag(Flags.PRINTREFERENCE);
			} else if (type instanceof ArrayType) {
				if (type.getEnumType().equals(EnumType.CHAR)) {
					// array of char = string
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_string")));
					flagSet.setFlag(Flags.PRINTSTRING);
				} else {
					// other types of array are printed as reference
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_reference")));
					flagSet.setFlag(Flags.PRINTREFERENCE);
				}
			}

		} else if (tree instanceof WaccParser.Expr_identContext) {
			Type type = currentGenerator.getType(tree.getText());
			if (type instanceof SingleType) {
				if (type.getEnumType().equals(EnumType.INT)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
					flagSet.setFlag(Flags.PRINTINT);
				} else if (type.getEnumType().equals(EnumType.CHAR)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("putchar")));
				} else if (type.getEnumType().equals(EnumType.BOOL)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_bool")));
					flagSet.setFlag(Flags.PRINTBOOL);
				} else if (type.getEnumType().equals(EnumType.STRING)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_string")));
					flagSet.setFlag(Flags.PRINTSTRING);
				}

			} else if (type instanceof PairType) {
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_reference")));
				flagSet.setFlag(Flags.PRINTREFERENCE);
			} else if (type instanceof ArrayType) {
				if (type.getEnumType().equals(EnumType.CHAR)) {
					// array of char = string
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_string")));
					flagSet.setFlag(Flags.PRINTSTRING);
				} else {
					// other types of array are printed as reference
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_reference")));
					flagSet.setFlag(Flags.PRINTREFERENCE);
				}
			}

		} else if (tree instanceof WaccParser.Expr_exprContext) {
			generatePrintType(tree.getChild(0));

		} else if (tree instanceof WaccParser.Expr_unaryContext) {
			ParseTree child = tree.getChild(0);
			if (child instanceof WaccParser.Unary_excContext) {

			} else if (child instanceof WaccParser.Unary_intContext) {
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
			} else if (child instanceof WaccParser.Unary_lenContext) {
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
				flagSet.setFlag(Flags.PRINTINT);
			} else if (child instanceof WaccParser.Unary_minusContext) {
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
				flagSet.setFlag(Flags.PRINTINT);
			} else if (child instanceof WaccParser.Unary_ordContext) {
				currentGenerator.add(new Operation(Operand.BL, new Label("putchar")));
			}

		} else if (tree instanceof WaccParser.Expr_MINUSContext || tree instanceof WaccParser.Expr_PLUSContext || tree instanceof WaccParser.Expr_MULTContext || tree instanceof WaccParser.Expr_DIVContext
				|| tree instanceof WaccParser.Expr_MODContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
			flagSet.setFlag(Flags.PRINTINT);

		} else if (tree instanceof WaccParser.Expr_GTContext || tree instanceof WaccParser.Expr_GTEQContext || tree instanceof WaccParser.Expr_LTContext || tree instanceof WaccParser.Expr_LTEQContext
				|| tree instanceof WaccParser.Expr_EQContext || tree instanceof WaccParser.Expr_NEQContext || tree instanceof WaccParser.Expr_ORContext || tree instanceof WaccParser.Expr_ANDContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_bool")));
			flagSet.setFlag(Flags.PRINTBOOL);
		}
	}

	@Override
	public Integer visitStat_if_expr_then_stat_else_stat_fi(WaccParser.Stat_if_expr_then_stat_else_stat_fiContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStat_while_expr_do_stat_done(WaccParser.Stat_while_expr_do_stat_doneContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStat_Read_assignLHS(WaccParser.Stat_Read_assignLHSContext ctx) {

		ParseTree child = ctx.getChild(1);
		String label = "p_read_";
		int offset = 0;
		int identOffset = 0;

		if (child instanceof WaccParser.AssignLHS_identContext) {
			offset = currentGenerator.getOffset(child.getText());
			identOffset = currentGenerator.getIdentOffset(child.getText());
			currentGenerator.add(new Operation(Operand.ADD, Register.r0, Register.sp, new Immediate(offset)));
		} else if (child instanceof WaccParser.AssignLHS_exprContext) {
			identOffset = evaluateArray(child);
			currentGenerator.add(new Operation(Operand.ADD, Register.r0, Register.r1, new Immediate(0)));
		} else if (child instanceof WaccParser.AssignLHS_pairElemContext) {
			identOffset = evaluatePairElem(child);
			currentGenerator.add(new Operation(Operand.ADD, Register.r0, Register.r1, new Immediate(0)));
		}

		if (identOffset == 4) {
			flagSet.setFlag(Flags.READINT);
			label += "int";
		} else if (identOffset == 1) {
			flagSet.setFlag(Flags.READCHAR);
			label += "char";
		}

		currentGenerator.add(new Operation(Operand.BL, new Label(label)));
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStat_Free_expr(WaccParser.Stat_Free_exprContext ctx) {
		evaluateExpr(ctx.getChild(1));
		currentGenerator.add(new Operation(Operand.BL, new Label("p_free_pair")));
		flagSet.setFlag(Flags.FREE);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStat_Exit_expr(WaccParser.Stat_Exit_exprContext ctx) {
		evaluateExpr(ctx.getChild(1));
		currentGenerator.add(new Operation(Operand.BL, new Label("exit")));
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStat_begin_stat_end(WaccParser.Stat_begin_stat_endContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStat_semicolon_stat(WaccParser.Stat_semicolon_statContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStat_SKIP(WaccParser.Stat_SKIPContext ctx) {
		return super.visitChildren(ctx);
	}

	// ==================== EXPR ==================== //

	@Override
	public Integer visitExpr_ident(WaccParser.Expr_identContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_bool(WaccParser.Expr_boolContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_char(WaccParser.Expr_charContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_int(WaccParser.Expr_intContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTINT);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_str(WaccParser.Expr_strContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTSTRING);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_pairlit(WaccParser.Expr_pairlitContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTREFERENCE);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_arr(WaccParser.Expr_arrContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_PLUS(WaccParser.Expr_PLUSContext ctx) {
		flagSet.setFlag(Flags.OVERFLOW);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_MINUS(WaccParser.Expr_MINUSContext ctx) {
		flagSet.setFlag(Flags.OVERFLOW);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_MULT(WaccParser.Expr_MULTContext ctx) {
		flagSet.setFlag(Flags.OVERFLOW);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_DIV(WaccParser.Expr_DIVContext ctx) {
		flagSet.setFlag(Flags.DIVIDEDBYZERO);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_MOD(WaccParser.Expr_MODContext ctx) {
		flagSet.setFlag(Flags.DIVIDEDBYZERO);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_GT(WaccParser.Expr_GTContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_GTEQ(WaccParser.Expr_GTEQContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_LT(WaccParser.Expr_LTContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_LTEQ(WaccParser.Expr_LTEQContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_EQ(WaccParser.Expr_EQContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_NEQ(WaccParser.Expr_NEQContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_OR(WaccParser.Expr_ORContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_AND(WaccParser.Expr_ANDContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_unary(WaccParser.Expr_unaryContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitExpr_expr(WaccParser.Expr_exprContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext
				|| ctx.getParent() instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext || ctx.getParent() instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext)
			evaluateExpr(ctx);
		return super.visitChildren(ctx);
	}

	// ==================== ASSIGNLHS ==================== //

	@Override
	public Integer visitAssignLHS_ident(WaccParser.AssignLHS_identContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitAssignLHS_pairElem(WaccParser.AssignLHS_pairElemContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitAssignLHS_expr(WaccParser.AssignLHS_exprContext ctx) {
		return super.visitChildren(ctx);
	}

	// ==================== ASSIGNRHS ==================== //

	@Override
	public Integer visitAssignRHS_call(WaccParser.AssignRHS_callContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitAssignRHS_newPair(WaccParser.AssignRHS_newPairContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitAssignRHS_pairElem(WaccParser.AssignRHS_pairElemContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitAssignRHS_arrayLit(WaccParser.AssignRHS_arrayLitContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitAssignRHS_expr(WaccParser.AssignRHS_exprContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitAssignRHS_if_then_else(WaccParser.AssignRHS_if_then_elseContext ctx) {
		return super.visitChildren(ctx);
	}

	// ==================== TERMINAL ==================== //

	@Override
	public Integer visitTerminal_is(WaccParser.Terminal_isContext ctx) {

		// PUSH {lr}
		currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
		currentGenerator.updateAllOffset(FOUR_BYTE);

		// SUB sp, sp, #maxByte for declarations in functions
		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, Register.sp, new Immediate(currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(currentGenerator.getMaxByte());
		}
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitTerminal_begin(WaccParser.Terminal_beginContext ctx) {

		if (ctx.getParent() instanceof WaccParser.ProgramContext) {
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
			// Dont updateoffset
		} else {
			FunctionGenerator beginGenerator = new FunctionGenerator(currentGenerator);
			currentGenerator = beginGenerator;
			reservedByteStack.push(index);
			maxIndex++;
			index = maxIndex;
		}

		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		// SUB sp, sp, #
		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(currentGenerator.getMaxByte());
		}

		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitTerminal_end(WaccParser.Terminal_endContext ctx) {

		if (ctx.getParent() instanceof WaccParser.FuncContext) {
			currentGenerator.updateAllOffset(-currentGenerator.getMaxByte());
		}

		/*
		 * ADD sp, sp, # Functions do this in RETURN instead of END Program End and
		 * Stat End should do this
		 */
		
		if (currentGenerator.getMaxByte() != 0 && !(ctx.getParent() instanceof WaccParser.FuncContext)) {
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(-currentGenerator.getMaxByte());
		}

		if (ctx.getParent() instanceof WaccParser.Stat_begin_stat_endContext)
			currentGenerator.addCode();
		// MOV r0, #0 and POP {pc} for Program
		if (ctx.getParent() instanceof WaccParser.ProgramContext) {
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(0)));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.pc)));
			// Dont update offset, relative to program
		} else {
			// if currentGenerator is not main, it returns to the previous scope
			currentGenerator = currentGenerator.getPrevious();
			index = reservedByteStack.pop();
			// currentGenerator.setMaxByte(walker.getReservedByte(index));
			int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
			currentGenerator.setCurrentByte(currentByte);
		}

		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitTerminal_if(WaccParser.Terminal_ifContext ctx) {
		// Label Branch
		branchStack.push(branchCount);
		branchCount += 2;
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitTerminal_then(WaccParser.Terminal_thenContext ctx) {

		// cmp r0, #0
		currentGenerator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));

		// BEQ label x
		currentGenerator.add(new Operation(Operand.BEQ, new Label("L" + branchStack.peek())));
		FunctionGenerator thenGenerator = new FunctionGenerator(currentGenerator);
		currentGenerator = thenGenerator;

		// new scope for then branch
		reservedByteStack.push(index);
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		currentGenerator.setCurrentByte(currentGenerator.getMaxByte());

		// SUB sp, sp, #maxByte for declarations in Then scope
		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, walker.getReservedByte(index))));
			currentGenerator.updateAllOffset(currentGenerator.getMaxByte());
		}

		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitTerminal_else(WaccParser.Terminal_elseContext ctx) {
		// ADD sp, sp # for the then-scope

		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(-currentGenerator.getMaxByte());
		}

		// B label x+1
		currentGenerator.add(new Operation(Operand.B, new Label("L" + (branchStack.peek() + 1))));
		// Label x and new scope for else branch
		currentGenerator.addCode();

		// Return to the previous scope and create a new scope
		currentGenerator = currentGenerator.getPrevious();
		FunctionGenerator elseGenerator = new FunctionGenerator(branchStack.peek().toString(), currentGenerator);
		currentGenerator = elseGenerator;

		// retreive the reservedBte for the new (current) scope
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		// SUB sp, sp, #maxByte for declarations in the ELSE scope
		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(currentGenerator.getMaxByte());
		}

		return super.visitTerminal_else(ctx);
	}

	@Override
	public Integer visitTerminal_fi(WaccParser.Terminal_fiContext ctx) {
		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(-currentGenerator.getMaxByte());
		}

		// Label x+1
		currentGenerator.addBranch(branchStack.peek() + 1);
		branchStack.pop();

		// exit then and else branch
		currentGenerator.addCode();
		currentGenerator = currentGenerator.getPrevious();

		index = reservedByteStack.pop();
		// currentGenerator.setMaxByte(walker.getReservedByte(index)); NO NEED?
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitTerminal_while(WaccParser.Terminal_whileContext ctx) {
		// Saves the current branchNum
		branchStack.push(branchCount);
		// branchNum increase by two because while uses two labels
		branchCount += 2;
		// Label x (the beginning of the loop)
		currentGenerator.addBranch(branchStack.peek());
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitTerminal_do(WaccParser.Terminal_doContext ctx) {

		// cmp r0, #0
		currentGenerator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));

		// BEQ label x+1
		currentGenerator.add(new Operation(Operand.BEQ, new Label("L" + (branchStack.peek() + 1))));

		// new scope for DO scope
		FunctionGenerator whileGenerator = new FunctionGenerator(currentGenerator);
		currentGenerator = whileGenerator;
		reservedByteStack.push(index);
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		// SUB sp, sp #maxByte for declarations in DO scope
		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(currentGenerator.getMaxByte());
		}
		return super.visitTerminal_do(ctx);
	}

	@Override
	public Integer visitTerminal_done(WaccParser.Terminal_doneContext ctx) {

		// ADD sp, sp, #
		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(-currentGenerator.getMaxByte());
		}

		// B Lx
		currentGenerator.add(new Operation(Operand.B, new Label("L" + branchStack.peek())));
		// Label x+1 (End of loop)
		currentGenerator.addBranch(branchStack.peek() + 1);

		branchStack.pop();

		// exit while loop
		currentGenerator.addCode();
		currentGenerator = currentGenerator.getPrevious();

		index = reservedByteStack.pop();
		// currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		return super.visitTerminal_done(ctx);
	}

	// ============================= FOR ==============================//

	@Override
	public Integer visitTerminal_for(WaccParser.Terminal_forContext ctx) {
		System.out.println(currentGenerator.getMaxByte());
		// new Scope
		ForLoopGenerator forGenerator = new ForLoopGenerator(currentGenerator, branchCount);
		currentGenerator = forGenerator;
		reservedByteStack.push(index);
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		// Saves the current branchNum
		branchStack.push(branchCount);
		// branchNum increase by four because for loop uses four labels
		branchCount += 4;

		// SUB sp, sp #maxByte for declarations in FOR scope
		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(currentGenerator.getMaxByte());
		}

		return super.visitTerminal_for(ctx);
	}

	@Override
	public Integer visitTerminal_semicolon(WaccParser.Terminal_semicolonContext ctx) {
		if (currentGenerator instanceof ForLoopGenerator)
			((ForLoopGenerator) currentGenerator).next();

		return super.visitTerminal_semicolon(ctx);
	}

	@Override
	public Integer visitTerminal_fdo(WaccParser.Terminal_fdoContext ctx) {
		if (currentGenerator instanceof ForLoopGenerator)
			((ForLoopGenerator) currentGenerator).next();
		return super.visitTerminal_fdo(ctx);
	}

	@Override
	public Integer visitTerminal_fdone(WaccParser.Terminal_fdoneContext ctx) {

		// exit while loop
		currentGenerator.addCode();

		branchStack.pop();

		currentGenerator = currentGenerator.getPrevious();
		index = reservedByteStack.pop();
		//currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
		currentGenerator.setCurrentByte(currentByte);
		return super.visitTerminal_fdone(ctx);
	}

	// =============================== DOWHILEDONE ===========================//

	@Override
	public Integer visitTerminal_ddo(WaccParser.Terminal_ddoContext ctx) {

		// Saves the current branchNum
		branchStack.push(branchCount);
		// branchNum increase by two because while uses two labels
		branchCount += 1;

		// new scope for DO scope
		FunctionGenerator doWhileGenerator = new FunctionGenerator(branchStack.peek().toString(), currentGenerator);
		currentGenerator = doWhileGenerator;
		reservedByteStack.push(index);
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		// SUB sp, sp #maxByte for declarations in DO scope
		if (currentGenerator.getMaxByte() != 0) {
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateAllOffset(currentGenerator.getMaxByte());
		}

		return super.visitTerminal_ddo(ctx);
	}

	@Override
	public Integer visitTerminal_dwhile(WaccParser.Terminal_dwhileContext ctx) {
		return super.visitTerminal_dwhile(ctx);
	}

	@Override
	public Integer visitTerminal_ddone(WaccParser.Terminal_ddoneContext ctx) {
		// cmp r0, #0
		currentGenerator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));

		// BEQ label x+1
		currentGenerator.add(new Operation(Operand.BNE, new Label("L" + branchStack.peek())));

		// exit while loop
		currentGenerator.addCode();

		branchStack.pop();

		currentGenerator = currentGenerator.getPrevious();
		index = reservedByteStack.pop();
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLastOffset();
		currentGenerator.setCurrentByte(currentByte);
		return super.visitTerminal_ddone(ctx);
	}

	// ==================== UNARY ==================== //

	@Override
	public Integer visitUnary_exc(WaccParser.Unary_excContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTBOOL);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitUnary_int(WaccParser.Unary_intContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTINT);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitUnary_minus(WaccParser.Unary_minusContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTINT);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitUnary_ord(WaccParser.Unary_ordContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitUnary_len(WaccParser.Unary_lenContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTINT);
		return super.visitChildren(ctx);
	}

	// ==================== PAIR ==================== //

	@Override
	public Integer visitPairElem_FST_expr(WaccParser.PairElem_FST_exprContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitPairElem_SND_expr(WaccParser.PairElem_SND_exprContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitPair_base_t(WaccParser.Pair_base_tContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitPair_type(WaccParser.Pair_typeContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitPair_PAIR(WaccParser.Pair_PAIRContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitPairType(WaccParser.PairTypeContext ctx) {
		return super.visitChildren(ctx);
	}

	// ==================== TYPE ==================== //

	@Override
	public Integer visitType_arr(WaccParser.Type_arrContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitType_pair(WaccParser.Type_pairContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitType_base(WaccParser.Type_baseContext ctx) {
		return super.visitChildren(ctx);
	}

	// ==================== BASE ==================== //

	@Override
	public Integer visitIdent(WaccParser.IdentContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitBase_BOOL(WaccParser.Base_BOOLContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitBase_CHAR(WaccParser.Base_CHARContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitBase_INT(WaccParser.Base_INTContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitBase_STR(WaccParser.Base_STRContext ctx) {
		return super.visitChildren(ctx);
	}

	// ==================== LITERAL ==================== //

	@Override
	public Integer visitBoolLiter(WaccParser.BoolLiterContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitCharLiter(WaccParser.CharLiterContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitIntLiter(WaccParser.IntLiterContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitStringLiter(WaccParser.StringLiterContext ctx) {
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitPairLiter(WaccParser.PairLiterContext ctx) {
		if (isPrintChild(ctx))
			flagSet.setFlag(Flags.PRINTREFERENCE);
		return super.visitChildren(ctx);
	}

	@Override
	public Integer visitArrayLiter(WaccParser.ArrayLiterContext ctx) {
		return super.visitChildren(ctx);
	}

	// ==================== Helper Functions ==================== //

	private int evaluateExpr(ParseTree tree) {
		if (tree instanceof WaccParser.Expr_identContext) {
			if (currentGenerator.getIdentOffset(tree.getText()) == 4) {
				return evaluateFourBytesExpr(tree);
			} else if (currentGenerator.getIdentOffset(tree.getText()) == 1) {
				return evaluateSingleByteExpr(tree);
			}
		} else if (tree instanceof WaccParser.Expr_intContext) {
			return evaluateFourBytesExpr(tree);
		} else if (tree instanceof WaccParser.Expr_boolContext) {
			return evaluateSingleByteExpr(tree);
		} else if (tree instanceof WaccParser.Expr_charContext) {
			return evaluateSingleByteExpr(tree);
		} else if (tree instanceof WaccParser.Expr_strContext) {
			return evaluateFourBytesExpr(tree);
		} else if (tree instanceof WaccParser.Expr_pairlitContext) {
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(0)));
		} else if (tree instanceof WaccParser.Expr_arrContext) {
			evaluateExpr(tree.getChild(0));
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r4)));
			currentGenerator.updateAllOffset(FOUR_BYTE);
			currentGenerator.add(new Operation(Operand.MOV, Register.r4, Register.r0));
			evaluateExpr(tree.getChild(2));
			currentGenerator.add(new Operation(Operand.BL, new Label("p_check_array_bounds")));
			flagSet.setFlag(Flags.ARRAY_BOUNDS);
			currentGenerator.add(new Operation(Operand.ADD, Register.r4, Register.r4, new Immediate(4)));
			currentGenerator.add(new Operation(Operand.ADD, Register.r4, Register.r4, Register.r0, new Label("LSL #2")));
			currentGenerator.add(new Operation(Operand.LDR, Register.r4, new Address(Register.r4)));
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, Register.r4));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r4)));
			currentGenerator.updateAllOffset(-FOUR_BYTE);
		} else if (tree instanceof WaccParser.Expr_exprContext) {
			evaluateExpr(tree.getChild(1));
		} else if (tree instanceof WaccParser.Expr_unaryContext) {
			ParseTree child = tree.getChild(0);
			if (child instanceof WaccParser.Unary_excContext) {
				evaluateExpr(tree.getChild(1));
				currentGenerator.add(new Operation(Operand.EOR, Register.r0, Register.r0, new Immediate(1)));

			} else if (child instanceof WaccParser.Unary_intContext) {
				evaluateExpr(tree.getChild(1));

			} else if (child instanceof WaccParser.Unary_lenContext) {
				int offset = currentGenerator.getOffset(tree.getChild(1).getText());
				currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.sp, offset)));
				currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.r0)));
			} else if (child instanceof WaccParser.Unary_minusContext) {
				if (allConstant(tree.getChild(1))) {
					int result = 0 - calculateExpr(tree.getChild(1));
					currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Constant(result)));
				} else {
					evaluateExpr(tree.getChild(1));
					currentGenerator.add(new Operation(Operand.RSBS, Register.r0, Register.r0, new Immediate(0)));
					currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
					flagSet.setFlag(Flags.OVERFLOW);
				}
			} else if (child instanceof WaccParser.Unary_ordContext) {
				evaluateExpr(tree.getChild(1));
			}
		} else if (tree instanceof WaccParser.Expr_PLUSContext || tree instanceof WaccParser.Expr_MINUSContext || tree instanceof WaccParser.Expr_MULTContext || tree instanceof WaccParser.Expr_DIVContext
				|| tree instanceof WaccParser.Expr_MODContext) {
			if (allConstant(tree)) {
				int result = calculateExpr(tree);
				currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Constant(result)));
			} else {
				evaluateExpr(tree.getChild(0));
				currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
				currentGenerator.updateAllOffset(FOUR_BYTE);
				evaluateExpr(tree.getChild(2));
				currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
				currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0)));
				currentGenerator.updateAllOffset(-FOUR_BYTE);
				evaluateBinOp(tree);
			}
			return FOUR_BYTE;
		} else if (tree instanceof WaccParser.Expr_EQContext || tree instanceof WaccParser.Expr_NEQContext || tree instanceof WaccParser.Expr_GTContext || tree instanceof WaccParser.Expr_GTEQContext
				|| tree instanceof WaccParser.Expr_LTContext || tree instanceof WaccParser.Expr_LTEQContext) {
			int offset = evaluateExpr(tree.getChild(0));
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(FOUR_BYTE);
			evaluateExpr(tree.getChild(2));
			currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(-FOUR_BYTE);
			evaluateBinOp(tree);
			return offset;
		} else if (tree instanceof WaccParser.Expr_ANDContext) {
			evaluateExpr(tree.getChild(0));
			evaluateBinOp(tree);
			evaluateExpr(tree.getChild(2));
			if (flagSet.getFlag(Flags.AND)) {
				currentGenerator.addBranch(branchCount);
				branchCount++;
				flagSet.clearFlag(Flags.AND);
			}
		} else if (tree instanceof WaccParser.Expr_ORContext) {
			evaluateExpr(tree.getChild(0));
			evaluateBinOp(tree);
			evaluateExpr(tree.getChild(2));
			if (flagSet.getFlag(Flags.OR) && !(tree.getParent() instanceof WaccParser.Expr_ORContext)) {
				currentGenerator.addBranch(orBranchCount);
				flagSet.clearFlag(Flags.OR);
			}
		}
		return 0;

	}

	private void evaluateBinOp(ParseTree tree) {

		if (tree instanceof WaccParser.Expr_PLUSContext) {
			currentGenerator.add(new Operation(Operand.ADDS, Register.r0, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
			flagSet.setFlag(Flags.OVERFLOW);
		} else if (tree instanceof WaccParser.Expr_MINUSContext) {
			currentGenerator.add(new Operation(Operand.SUBS, Register.r0, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
			flagSet.setFlag(Flags.OVERFLOW);
		} else if (tree instanceof WaccParser.Expr_MULTContext) {
			currentGenerator.add(new Operation(Operand.SMULL, Register.r0, Register.r1, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.CMP, Register.r1, Register.r0, new Label("ASR #31")));
			currentGenerator.add(new Operation(Operand.BLNE, new Label("p_throw_overflow_error")));
			flagSet.setFlag(Flags.OVERFLOW);
		} else if (tree instanceof WaccParser.Expr_DIVContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_check_divide_by_zero")));
			flagSet.setFlag(Flags.DIVIDEDBYZERO);
			currentGenerator.add(new Operation(Operand.BL, new Label("__aeabi_idiv")));
		} else if (tree instanceof WaccParser.Expr_MODContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_check_divide_by_zero")));
			flagSet.setFlag(Flags.DIVIDEDBYZERO);
			currentGenerator.add(new Operation(Operand.BL, new Label("__aeabi_idivmod")));
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, Register.r1));
		} else if (tree instanceof WaccParser.Expr_GTContext) {
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.MOVGT, Register.r0, new Immediate(1)));
			currentGenerator.add(new Operation(Operand.MOVLE, Register.r0, new Immediate(0)));
		} else if (tree instanceof WaccParser.Expr_GTEQContext) {
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.MOVGE, Register.r0, new Immediate(1)));
			currentGenerator.add(new Operation(Operand.MOVLT, Register.r0, new Immediate(0)));
		} else if (tree instanceof WaccParser.Expr_LTContext) {
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.MOVLT, Register.r0, new Immediate(1)));
			currentGenerator.add(new Operation(Operand.MOVGE, Register.r0, new Immediate(0)));
		} else if (tree instanceof WaccParser.Expr_LTEQContext) {
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.MOVLE, Register.r0, new Immediate(1)));
			currentGenerator.add(new Operation(Operand.MOVGT, Register.r0, new Immediate(0)));
		} else if (tree instanceof WaccParser.Expr_EQContext) {
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.MOVEQ, Register.r0, new Immediate(1)));
			currentGenerator.add(new Operation(Operand.MOVNE, Register.r0, new Immediate(0)));
		} else if (tree instanceof WaccParser.Expr_NEQContext) {
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.MOVNE, Register.r0, new Immediate(1)));
			currentGenerator.add(new Operation(Operand.MOVEQ, Register.r0, new Immediate(0)));
		} else if (tree instanceof WaccParser.Expr_ANDContext) {
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));
			currentGenerator.add(new Operation(Operand.BEQ, new Label("L" + branchCount)));
			if (!(tree.getParent() instanceof WaccParser.Expr_ANDContext)) {
				flagSet.setFlag(Flags.AND);
			}
		} else if (tree instanceof WaccParser.Expr_ORContext) {
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, new Immediate(1)));
			if (!((tree.getChild(0) instanceof WaccParser.Expr_ORContext) || (tree.getChild(2) instanceof WaccParser.Expr_ORContext))) {
				flagSet.setFlag(Flags.OR);
				orBranchCount = branchCount;
			}
			currentGenerator.add(new Operation(Operand.BEQ, new Label("L" + orBranchCount)));
			branchCount++;

		}
	}

	private int evaluateFourBytesExpr(ParseTree tree) {
		if (isInt(tree)) {
			currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Constant(tree.getChild(0).getText())));
		} else if (isString(tree)) {
			codeGenerator.addMessage(new MessageGenerator(msgCount, tree.getText()));
			currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Label("=msg_" + msgCount++)));
		} else {
			String id = extractId(tree);
			int offset = currentGenerator.getOffset(id);
			currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(new Register(CodeUtil.sp, offset))));
		}
		return 4;
	}

	private int evaluateSingleByteExpr(ParseTree tree) {
		if (isBool(tree)) {
			int bool = tree.getText().equals("true") ? 1 : 0;
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(bool)));
		} else if (isChar(tree)) {
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(tree.getChild(0).getText())));
		} else {
			int offset = currentGenerator.getOffset(tree.getText());
			currentGenerator.add(new Operation(Operand.LDRSB, Register.r0, new Address(new Register(CodeUtil.sp, offset))));
		}
		return 1;
	}

	public void generateCode() {
		codeGenerator.addFunction(main);

		if (flagSet.getFlag(Flags.PRINTNULLPOINTER)) {
			codeGenerator.addFunction(codeUtil.p_check_null_pointer(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"NullReferenceError: dereference a null reference\\n\\0\""));
			flagSet.setFlag(Flags.RUNTIME);
			flagSet.setFlag(Flags.PRINTSTRING);
		}

		if (flagSet.getFlag(Flags.ARRAY_BOUNDS)) {
			codeGenerator.addFunction(codeUtil.p_check_array_bounds(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"ArrayIndexOutOfBoundsError: nevative index\\n\\0\""));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"ArrayIndexOutOfBoundsError: index too large\\n\\0\""));
			flagSet.setFlag(Flags.RUNTIME);
			flagSet.setFlag(Flags.PRINTSTRING);
		}

		if (flagSet.getFlag(Flags.DIVIDEDBYZERO)) {
			codeGenerator.addFunction(codeUtil.p_check_divide_by_zero(msgCount));
			flagSet.setFlag(Flags.OVERFLOW);
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"DivideByZeroError: divide or modulo by zero\\n\\0\""));
		}

		if (flagSet.getFlag(Flags.OVERFLOW)) {
			codeGenerator.addFunction(codeUtil.p_throw_overflow_error(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"OverflowError: the result is too small/large to store in a 4-byte signed-integer.\\n\""));
			flagSet.setFlag(Flags.PRINTSTRING);
			flagSet.setFlag(Flags.RUNTIME);
		}

		if (flagSet.getFlag(Flags.RUNTIME)) {
			codeGenerator.addFunction(codeUtil.p_throw_runtime_error());
		}

		if (flagSet.getFlag(Flags.READINT)) {
			codeGenerator.addFunction(codeUtil.p_read_int(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"%d\\0\""));
		}

		if (flagSet.getFlag(Flags.READCHAR)) {
			codeGenerator.addFunction(codeUtil.p_read_char(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\" %c\\0\""));
			msgCount++;
		}

		if (flagSet.getFlag(Flags.PRINTSTRING)) {
			codeGenerator.addFunction(codeUtil.p_print_string(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"%.*s\\0\""));
		}

		if (flagSet.getFlag(Flags.PRINTINT)) {
			codeGenerator.addFunction(codeUtil.p_print_int(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"%d\\0\""));
		}

		if (flagSet.getFlag(Flags.PRINTBOOL)) {
			codeGenerator.addFunction(codeUtil.p_print_bool(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"true\\0\""));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"false\\0\""));
		}

		if (flagSet.getFlag(Flags.PRINTLN)) {
			codeGenerator.addFunction(codeUtil.p_print_ln(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"\\0\""));
		}

		if (flagSet.getFlag(Flags.PRINTREFERENCE)) {
			codeGenerator.addFunction(codeUtil.p_print_reference(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"%p\\0\""));
		}

		if (flagSet.getFlag(Flags.FREE)) {
			codeGenerator.addFunction(codeUtil.p_free_pair());
		}

		codeGenerator.print();
	}

	// return true if the tree is part of an expression which is being printed
	private boolean isPrintChild(ParseTree tree) {
		if (tree instanceof WaccParser.ProgramContext) {
			return false;
		} else if (tree instanceof WaccParser.Stat_Print_exprContext || tree instanceof WaccParser.Stat_Println_exprContext) {
			return true;
		}
		return isPrintChild(tree.getParent());
	}

	private boolean isChar(ParseTree child) {
		return child instanceof WaccParser.Expr_charContext;
	}

	private boolean isBool(ParseTree child) {
		return child instanceof WaccParser.Expr_boolContext;
	}

	private boolean isInt(ParseTree child) {
		return child instanceof WaccParser.Expr_intContext;
	}

	private boolean isString(ParseTree child) {
		return child instanceof WaccParser.Expr_strContext;
	}

	private String extractId(ParseTree ctx) {
		String id = "Failed";
		if (ctx instanceof WaccParser.IdentContext || ctx instanceof WaccParser.Expr_identContext || ctx instanceof WaccParser.AssignLHS_identContext) {
			id = ctx.getText();
		} else if (ctx instanceof WaccParser.Expr_arrContext) {
			id = extractId(ctx.getChild(0));
		} else if (ctx instanceof WaccParser.PairElemContext) {
			id = extractId(ctx.getChild(1));
		}

		return id;
	}

	// Take the DESTINATION as argument
	private void evaluateAssignLhs(ParseTree ctx) {
		if (ctx instanceof WaccParser.AssignLHS_exprContext) {
			evaluateArray(ctx);
			Type type = (Type) getType(ctx).getType();
			Operand operand = (getTypeOffset(type) == 4) ? Operand.STR : Operand.STRB;
			currentGenerator.add(new Operation(operand, Register.r0, new Address(Register.r1)));
		} else if (ctx instanceof WaccParser.AssignLHS_identContext || ctx instanceof WaccParser.IdentContext || ctx instanceof WaccParser.Expr_identContext) {
			String id = extractId(ctx);
			Type type = getType(ctx);
			int offset = currentGenerator.getOffset(id);
			Operand operand = (getTypeOffset(type) == 4) ? Operand.STR : Operand.STRB;
			currentGenerator.add(new Operation(operand, Register.r0, new Address(new Register(CodeUtil.sp, offset))));
		} else if (ctx instanceof WaccParser.AssignLHS_pairElemContext) {
			evaluatePairElem(ctx);
			currentGenerator.add(new Operation(Operand.STR, Register.r0, new Address(Register.r1)));
		}
	}

	private int evaluatePairElem(ParseTree ctx) {
		currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
		currentGenerator.updateAllOffset(FOUR_BYTE);
		PairType type = (PairType) getType(ctx.getChild(0).getChild(1));
		evaluateExpr(ctx.getChild(0).getChild(1));
		currentGenerator.add(new Operation(Operand.BL, new Label("p_check_null_pointer")));
		flagSet.setFlag(Flags.PRINTNULLPOINTER);
		int immediate = (ctx.getChild(0) instanceof WaccParser.PairElem_FST_exprContext) ? 0 : 4;
		currentGenerator.add(new Operation(Operand.ADD, Register.r0, Register.r0, new Immediate(immediate)));
		currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
		currentGenerator.updateAllOffset(FOUR_BYTE);
		currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.r0)));
		currentGenerator.add(new Operation(Operand.BL, new Label("free")));
		flagSet.setFlag(Flags.FREE);
		currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(4)));
		currentGenerator.add(new Operation(Operand.BL, new Label("malloc")));
		currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r1)));
		currentGenerator.updateAllOffset(-FOUR_BYTE);
		currentGenerator.add(new Operation(Operand.STR, Register.r0, new Address(Register.r1)));
		currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
		currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0)));
		currentGenerator.updateAllOffset(-FOUR_BYTE);
		return (ctx.getChild(0) instanceof WaccParser.PairElem_FST_exprContext) ? getTypeOffset(type.getFirst()) : getTypeOffset(type.getSecond());
	}

	private int evaluateArray(ParseTree ctx) {
		currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0, Register.r4)));
		currentGenerator.updateAllOffset(2 * FOUR_BYTE);
		String id = extractId(ctx.getChild(0));
		int offset = currentGenerator.getOffset(id);
		currentGenerator.add(new Operation(Operand.ADD, Register.r4, Register.sp, new Immediate(offset)));
		ParseTree leftMostExpr = getLeftMostArrayLiter(ctx);
		evaluateArrayBracketExpr(leftMostExpr);
		currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r4));
		currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0, Register.r4)));
		currentGenerator.updateAllOffset(-2 * FOUR_BYTE);
		return getArrayElementOffset(currentGenerator.getType(id));
	}

	// for expr1[expr2], evaluate expr 2
	private void evaluateArrayBracketExpr(ParseTree ctx) {
		evaluateExpr(ctx.getChild(2));
		currentGenerator.add(new Operation(Operand.BL, new Label("p_check_array_bounds")));
		flagSet.setFlag(Flags.ARRAY_BOUNDS);
		currentGenerator.add(new Operation(Operand.LDR, Register.r4, new Address(Register.r4)));
		currentGenerator.add(new Operation(Operand.ADD, Register.r4, Register.r4, new Immediate(4)));
		if (getType(ctx.getChild(0)).getEnumType().equals(EnumType.CHAR)) {
			currentGenerator.add(new Operation(Operand.ADD, Register.r4, Register.r4, Register.r0));
		} else {
			currentGenerator.add(new Operation(Operand.ADD, Register.r4, Register.r4, Register.r0, new Label("LSL #2")));
		}
		if (ctx.getParent() instanceof WaccParser.Expr_arrContext) {
			evaluateArray(ctx.getParent());
		}
	}

	private ParseTree getLeftMostArrayLiter(ParseTree ctx) {
		if (ctx.getChild(0) instanceof WaccParser.Expr_identContext)
			return ctx;
		return getLeftMostArrayLiter(ctx.getChild(0));
	}

	private boolean matchKeyword(ParseTree tree, String str) {
		return (tree.getText().toUpperCase().equals(str));
	}

	private int evaluateAssignRhs(ParseTree tree) {
		//TODO: finish this
		if (tree instanceof WaccParser.AssignRHS_arrayLitContext) {
			evaluateArrayAssignment(tree.getChild(0));
		} else if (tree instanceof WaccParser.AssignRHS_callContext) {
			ParseTree child = tree.getChild(3);
			int totalOffset = 0;
			for (int i = child.getChildCount() - 1; i >= 0; i -= 2) {
				evaluateExpr(child.getChild(i));
				Type type = getType(child.getChild(i));
				int ArgOffset = getTypeOffset(type);
				if (ArgOffset == 4) {
					currentGenerator.add(new Operation(Operand.STR, Register.r0, new Address(Register.sp, -FOUR_BYTE, true)));
					currentGenerator.updateAllOffset(ArgOffset);
					totalOffset += ArgOffset;
				} else if (ArgOffset == 1) {
					currentGenerator.add(new Operation(Operand.STRB, Register.r0, new Address(Register.sp, -SINGLE_BYTE, true)));
					currentGenerator.updateAllOffset(ArgOffset);
					totalOffset += ArgOffset;
				}
			}

			String funcName = "f_" + tree.getChild(1).getText();
			if (tree.getChild(3) instanceof WaccParser.ArgListContext) {
				int numArgs = tree.getChild(3).getChildCount();
				for (int i = 0; i < numArgs; i += 2) {
					ParseTree t = tree.getChild(3).getChild(i);
					BigType paramType = getType(t);
					funcName += "_";
					if (paramType instanceof ArrayType) {
						funcName += paramType.getEnumType().toString().toLowerCase();
						while (paramType instanceof ArrayType) {
							funcName += "arr";
							paramType = paramType.getType();
						}
					} else {
						funcName += paramType.toString().toLowerCase();
					}
				}
			}
			// System.out.println(funcName);
			currentGenerator.add(new Operation(Operand.BL, new Label(funcName)));
			if (totalOffset != 0) {
				currentGenerator.add(new Operation(Operand.ADD, Register.sp, Register.sp, new Immediate(totalOffset)));
				currentGenerator.updateAllOffset(-totalOffset);
			}
		} else if (tree instanceof WaccParser.AssignRHS_exprContext) {
			evaluateExpr(tree.getChild(0));
		} else if (tree instanceof WaccParser.AssignRHS_newPairContext) {
			evaluateExpr(tree.getChild(2));
			Type type = getType(tree.getChild(2));
			int pairOffset = getTypeOffset(type);
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(FOUR_BYTE);
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(pairOffset)));
			currentGenerator.add(new Operation(Operand.BL, new Label("malloc")));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r1)));
			currentGenerator.updateAllOffset(-FOUR_BYTE);
			if (pairOffset == 4) {
				currentGenerator.add(new Operation(Operand.STR, Register.r1, new Address(Register.r0)));
			} else if (pairOffset == 1) {
				currentGenerator.add(new Operation(Operand.STRB, Register.r1, new Address(Register.r0)));
			}
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(FOUR_BYTE);

			evaluateExpr(tree.getChild(4));
			type = getType(tree.getChild(4));
			pairOffset = getTypeOffset(type);
			// System.out.println(tree.getChild(4).getText() + type + pairOffset);
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(FOUR_BYTE);
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(pairOffset)));
			currentGenerator.add(new Operation(Operand.BL, new Label("malloc")));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r1)));
			currentGenerator.updateAllOffset(-FOUR_BYTE);
			if (pairOffset == 4) {
				currentGenerator.add(new Operation(Operand.STR, Register.r1, new Address(Register.r0)));
			} else if (pairOffset == 1) {
				currentGenerator.add(new Operation(Operand.STRB, Register.r1, new Address(Register.r0)));
			}
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
			currentGenerator.updateAllOffset(FOUR_BYTE);
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(8)));
			currentGenerator.add(new Operation(Operand.BL, new Label("malloc")));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r1, Register.r2)));
			currentGenerator.updateAllOffset(-2 * FOUR_BYTE);
			currentGenerator.add(new Operation(Operand.STR, Register.r2, new Address(Register.r0)));
			currentGenerator.add(new Operation(Operand.STR, Register.r1, new Address(new Register(0, 4))));
		} else if (tree instanceof WaccParser.AssignRHS_pairElemContext) {
			ParseTree child = tree.getChild(0);
			evaluateExpr(child.getChild(1));
			currentGenerator.add(new Operation(Operand.BL, new Label("p_check_null_pointer")));
			flagSet.setFlag(Flags.PRINTNULLPOINTER);
			if (child instanceof WaccParser.PairElem_FST_exprContext) {
				Type type = (Type) ((PairType) getType(child.getChild(1))).getFirst();
				Operand operand = (getTypeOffset(type) == 1) ? Operand.LDRSB : Operand.LDR;
				currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.r0)));
				currentGenerator.add(new Operation(operand, Register.r0, new Address(Register.r0)));
			} else if (child instanceof WaccParser.PairElem_SND_exprContext) {
				Type type = (Type) ((PairType) getType(child.getChild(1))).getSecond();
				Operand operand = (getTypeOffset(type) == 1) ? Operand.LDRSB : Operand.LDR;
				currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(new Register(0, 4))));
				currentGenerator.add(new Operation(operand, Register.r0, new Address(Register.r0)));
			}
		} else if (tree instanceof WaccParser.AssignRHS_if_then_elseContext) {
			branchStack.push(branchCount);
			branchCount += 2;
			evaluateExpr(tree.getChild(0));
			// cmp r0, #0
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));
			// BEQ label x
			currentGenerator.add(new Operation(Operand.BEQ, new Label("L" + branchStack.peek())));
			evaluateAssignRhs(tree.getChild(2));
			// B label x+1
			currentGenerator.add(new Operation(Operand.B, new Label("L" + (branchStack.peek() + 1))));
			currentGenerator.addBranch(branchStack.peek());
			evaluateAssignRhs(tree.getChild(4));
			currentGenerator.addBranch(branchStack.peek() + 1);

		} else if (tree instanceof WaccParser.AssignLHS_identContext) {
			if (currentGenerator.getIdentOffset(tree.getText()) == 4) {
				return evaluateFourBytesExpr(tree);
			} else if (currentGenerator.getIdentOffset(tree.getText()) == 1) {
				return evaluateSingleByteExpr(tree);
			}
		}
		return 0;
	}

	private int evaluateArrayAssignment(ParseTree child) {
		int numberOfElements = (child.getChildCount() - 1) / 2;
		Type type = getType(child);
		int offset = FOUR_BYTE + numberOfElements * getArrayElementOffset(type);
		currentGenerator.add(new Operation(Operand.SUB, Register.sp, Register.sp, new Immediate(offset)));
		currentGenerator.updateCurrentByte(offset);
		currentGenerator.setMaxByte(currentGenerator.getMaxByte() + offset);
		currentGenerator.updateAllOffset(offset);
		int arrayOffset = FOUR_BYTE;
		int lastOffset = 0;
		for (int i = 0; i < numberOfElements; i++) {
			if (type.getType() instanceof ArrayType){
				//System.out.println(child.getChild(2*i+1).getClass());
				lastOffset += evaluateArrayAssignment(child.getChild(2*i+1));
			}
			else
				evaluateExpr(child.getChild(2 * i + 1));
			Operand operand = (getArrayElementOffset(type) == 4) ? Operand.STR : Operand.STRB;
			currentGenerator.add(new Operation(operand, Register.r0, new Address(new Register(CodeUtil.sp, arrayOffset+lastOffset))));
			arrayOffset += getArrayElementOffset(type);
		}
		currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(numberOfElements)));
		currentGenerator.add(new Operation(Operand.STR, Register.r0, new Address(new Register(CodeUtil.sp, lastOffset))));
		if(lastOffset!=0){
			currentGenerator.add(new Operation(Operand.ADD, Register.r1, Register.sp, new Immediate(lastOffset)));
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, Register.r1));
		}else{
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, Register.sp));
		}
		return offset;
	}

	// TODO: Finish this
	private int handleArrayAssignment(ParseTree child) {
		int numberOfElements = (child.getChildCount() - 1) / 2;
		/*if (child.getChild(1) instanceof WaccParser.ArrayLiterContext) {
			int offset = 0;
			for (int i = 0; i < numberOfElements; i++) {
				offset += handleArrayAssignment(child.getChild(2 * i + 1));
				int nestedChildren = child.getChild(2 * i + 1).getChildCount();
				nestedArrays += (nestedChildren - 1) * 2;
				currentGenerator.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(Register.sp, nestedArrays)));
			}
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp,(numberOfElements+1)*4)));
			currentGenerator.updateAllOffset((numberOfElements+1)*4);
			currentGenerator.setMaxByte(currentGenerator.getMaxByte()+(numberOfElements+1)*4);
			int finalOffset = 0;
			for (int i = numberOfElements - 1; i >= 0; i--) {
				finalOffset = nestedArrays + 12 + (4 * i);
				currentGenerator.add(new Operation(Operand.LDR, CodeUtil.InputReg, new Address(Register.sp, finalOffset)));
				currentGenerator.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(Register.sp, 4 * (numberOfElements - i))));
			}
			currentGenerator.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(numberOfElements)));
			currentGenerator.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(Register.sp)));
			currentGenerator.add(new Operation(Operand.MOV, CodeUtil.InputReg, Register.sp));
			return offset + (numberOfElements+1)*4;
		} else {*/
			Type type = getType(child);
			int offset = FOUR_BYTE + numberOfElements * getArrayElementOffset(type);
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, Register.sp, new Immediate(offset)));
			currentGenerator.updateCurrentByte(offset);
			currentGenerator.setMaxByte(currentGenerator.getMaxByte() + offset);
			currentGenerator.updateAllOffset(offset);
			int arrayOffset = FOUR_BYTE;
			for (int i = 0; i < numberOfElements; i++) {
				evaluateExpr(child.getChild(2 * i + 1));
				Operand operand = (getArrayElementOffset(type) == 4) ? Operand.STR : Operand.STRB;
				currentGenerator.add(new Operation(operand, Register.r0, new Address(new Register(CodeUtil.sp, arrayOffset))));
				arrayOffset += getArrayElementOffset(type);
			}
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(numberOfElements)));
			currentGenerator.add(new Operation(Operand.STR, Register.r0, new Address(Register.sp)));
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, Register.sp));
			return offset;
		//}
	}

	// return offset corresponding to different types
	private int getTypeOffset(BigType type) {
		if (type instanceof SingleType) {
			if (type.getEnumType().equals(EnumType.INT) || type.getEnumType().equals(EnumType.STRING) || type.getEnumType().equals(EnumType.NULL))
				return 4;
			else if (type.getEnumType().equals(EnumType.BOOL) || type.getEnumType().equals(EnumType.CHAR))
				return 1;
		} else if (type instanceof PairType || type instanceof ArrayType) {
			return 4;
		}
		return 0;
	}

	// Mainly for Array Declarations and Assignments
	private int getArrayElementOffset(Type type) {
		return getTypeOffset(type.getType());
	}

	// ==================== TYPE CHECK ==================== //

	private Type getType(ParseTree ctx) {

		// Ident
		if (ctx instanceof WaccParser.IdentContext) {
			return currentGenerator.getType(ctx.getText());
		}

		// AssignRhs
		if (ctx instanceof WaccParser.AssignRHS_exprContext) {
			return getType(ctx.getChild(0));
		}

		// AssignLhs
		if (ctx instanceof WaccParser.AssignLHS_identContext) {
			return currentGenerator.getType(ctx.getText());
		} else if (ctx instanceof WaccParser.AssignLHS_exprContext) {
			return getType(ctx.getChild(0));
		}

		// Expr
		if (ctx instanceof WaccParser.Expr_identContext) {
			return currentGenerator.getType(ctx.getText());
		} else if (ctx instanceof WaccParser.Expr_arrContext) {
			return (Type) getType(ctx.getChild(0)).getType();
		} else if (ctx instanceof WaccParser.Expr_unaryContext) {
			return getType(ctx.getChild(0));
		} else if (ctx instanceof WaccParser.Expr_PLUSContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Expr_MINUSContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Expr_MULTContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Expr_DIVContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Expr_MODContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Expr_GTContext) {
			return new SingleType(EnumType.BOOL);
		} else if (ctx instanceof WaccParser.Expr_GTEQContext) {
			return new SingleType(EnumType.BOOL);
		} else if (ctx instanceof WaccParser.Expr_LTContext) {
			return new SingleType(EnumType.BOOL);
		} else if (ctx instanceof WaccParser.Expr_LTEQContext) {
			return new SingleType(EnumType.BOOL);
		} else if (ctx instanceof WaccParser.Expr_EQContext) {
			return new SingleType(EnumType.BOOL);
		} else if (ctx instanceof WaccParser.Expr_NEQContext) {
			return new SingleType(EnumType.BOOL);
		} else if (ctx instanceof WaccParser.Expr_pairlitContext) {
			return new SingleType(EnumType.NULL);
		}

		// Unary
		if (ctx instanceof WaccParser.Unary_excContext) {
			return new SingleType(EnumType.BOOL);
		} else if (ctx instanceof WaccParser.Unary_minusContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Unary_intContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Unary_lenContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Unary_ordContext) {
			return new SingleType(EnumType.CHAR);
		}

		// Type
		if (ctx instanceof WaccParser.Type_baseContext) {
			return getType(ctx.getChild(0));
		} else if (ctx instanceof WaccParser.Type_arrContext) {
			return new ArrayType(getType(ctx.getChild(0)));
		}

		// baseType
		if (ctx instanceof WaccParser.Base_INTContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Base_BOOLContext) {
			return new SingleType(EnumType.BOOL);
		} else if (ctx instanceof WaccParser.Base_CHARContext) {
			return new SingleType(EnumType.CHAR);
		} else if (ctx instanceof WaccParser.Base_STRContext) {
			return new ArrayType(new SingleType(EnumType.CHAR));
		}

		if (ctx instanceof WaccParser.Expr_intContext) {
			return new SingleType(EnumType.INT);
		} else if (ctx instanceof WaccParser.Expr_boolContext) {
			return new SingleType(EnumType.BOOL);
		} else if (ctx instanceof WaccParser.Expr_charContext) {
			return new SingleType(EnumType.CHAR);
		} else if (ctx instanceof WaccParser.Expr_strContext) {
			return new ArrayType(new SingleType(EnumType.CHAR));
		}

		if (ctx instanceof WaccParser.ArrayLiterContext) {
			return new ArrayType(getType(ctx.getChild(1)));
		}

		if (ctx instanceof WaccParser.AssignRHS_newPairContext) {
			return new PairType(getType(ctx.getChild(2)), getType(ctx.getChild(4)));
		} else if (ctx instanceof WaccParser.Type_pairContext) {
			return getType(ctx.getChild(0));
		} else if (ctx instanceof WaccParser.PairTypeContext) {
			return new PairType(getType(ctx.getChild(2)), getType(ctx.getChild(4)));
		} else if (ctx instanceof WaccParser.Pair_base_tContext) {
			return getType(ctx.getChild(0));
		} else if (ctx instanceof WaccParser.Pair_typeContext) {
			return getType(ctx.getChild(0));
		} else if (ctx instanceof WaccParser.Pair_PAIRContext) {
			return new SingleType(EnumType.PAIR);
		} else if (ctx instanceof WaccParser.Type_baseContext) {
			return getType(ctx.getChild(0));
		}

		return new SingleType(EnumType.NULL);

	}

	private boolean allConstant(ParseTree ctx) {
		boolean result = true;
		if (ctx instanceof WaccParser.IdentContext) {
			return false;
		}
		for (int i = 0; i < ctx.getChildCount(); i++) {
			result = allConstant(ctx.getChild(i));
			if (!result)
				return false;
		}
		return true;
	}

	private int calculateExpr(ParseTree ctx) {
		if (ctx instanceof WaccParser.Expr_intContext) {
			return Integer.parseInt(ctx.getText());
		} else if (ctx instanceof WaccParser.Expr_PLUSContext) {
			return calculateExpr(ctx.getChild(0)) + calculateExpr(ctx.getChild(2));
		} else if (ctx instanceof WaccParser.Expr_MINUSContext) {
			return calculateExpr(ctx.getChild(0)) - calculateExpr(ctx.getChild(2));
		} else if (ctx instanceof WaccParser.Expr_MULTContext) {
			return calculateExpr(ctx.getChild(0)) * calculateExpr(ctx.getChild(2));
		} else if (ctx instanceof WaccParser.Expr_DIVContext) {
			return calculateExpr(ctx.getChild(0)) / calculateExpr(ctx.getChild(2));
		} else if (ctx instanceof WaccParser.Expr_MODContext) {
			return calculateExpr(ctx.getChild(0)) % calculateExpr(ctx.getChild(2));
		} else if (ctx instanceof WaccParser.Expr_unaryContext) {
			if (ctx.getChild(0) instanceof WaccParser.Unary_minusContext) {
				return Integer.parseInt(ctx.getText());
			}
		}
		return 0;
	}

}
