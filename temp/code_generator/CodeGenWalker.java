package code_generator;

import generator.CodeGenerator;
import generator.FunctionGenerator;
import generator.MessageGenerator;
import generator.RegList;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Stack;

import org.antlr.v4.runtime.tree.ParseTree;

import semantic_check.ArrayType;
import semantic_check.EnumType;
import semantic_check.PairType;
import semantic_check.SingleType;
import semantic_check.SymbolTable;
import semantic_check.TreeWalkerUtil;
import semantic_check.Type;
import antlr.WaccBaseVisitor;
import antlr.WaccParser;
import code.Address;
import code.Constant;
import code.Immediate;
import code.Label;
import code.Operand;
import code.Operation;
import code.Register;

public class CodeGenWalker extends WaccBaseVisitor<Integer> {
	
	private final int REGISTER_BYTE = 4;

	private PrintStream outputStream;
	private HashMap<String, Integer> pairAddress;
	private PreCodeGenWalker walker;
	private CodeGenerator codeGenerator;
	private FunctionGenerator currentGenerator;
	private FunctionGenerator main;
	private int msgCount;
	private int branchNum;
	private TreeWalkerUtil current;
	private RegList registers;
	private CodeUtil codePrinter;
	private Stack<Integer> branchStack;
	private int otherStrCount;
	private int index;
	private int maxIndex;
	private Stack<Integer> reservedByteStack;
	private FlagSet flagSet;
	private int orBranchNum;

	public CodeGenWalker(PreCodeGenWalker walker, PrintStream outputStream) {
		this.codeGenerator = new CodeGenerator(".global main", outputStream);
		this.main = new FunctionGenerator("main");
		this.currentGenerator = main;
		this.walker = walker;
		this.outputStream = outputStream;
		this.msgCount = 0;
		this.branchNum = 0;
		this.otherStrCount = 0;
		this.index = 0; // for reservedByteStack
		this.maxIndex = 0; // for reservedByteStack
		this.current = new TreeWalkerUtil(new SymbolTable());
		this.registers = new RegList();
		this.pairAddress = new HashMap<String, Integer>();
		initialiseRegs();
		this.branchStack = new Stack<Integer>();
		this.reservedByteStack = new Stack<Integer>();
		this.codePrinter = new CodeUtil(registers, walker, main); // handles all
		// the code
		// constraints;
		this.flagSet = new FlagSet();
		this.orBranchNum=0;
	}

	@Override
	public Integer visitProgram(WaccParser.ProgramContext ctx) {
		return super.visitProgram(ctx);
	}

	@Override
	public Integer visitFunc(WaccParser.FuncContext ctx) {

		FunctionGenerator funcGenerator = new FunctionGenerator("f_" + ctx.getChild(1).getText(), currentGenerator);
		currentGenerator = funcGenerator;
		reservedByteStack.push(index);
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		currentGenerator.setCurrentByte(currentGenerator.getMaxByte());
		currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
		//function PUSH {lr} offset updated in visitParamList
		//System.out.println(currentGenerator.getMaxByte());

		currentGenerator.addDirective("ltorg");
		codeGenerator.addFunction(currentGenerator);

		return super.visitFunc(ctx);
	}

	private int getOffSetValue(ParseTree child) {
		if (matchKeyword(child.getChild(0), "CHAR") || (matchKeyword(child.getChild(0), "BOOL"))) {
			return 1;
		} else if (matchKeyword(child.getChild(0), "INT") || (matchKeyword(child.getChild(0), "STRING"))) {
			return 4;
		}
		return 0;
	}

	@Override
	public Integer visitParamList(WaccParser.ParamListContext ctx) {
		int childCount = ctx.getChildCount();
		int paramOffset = 0;
		for (int i = 0; i < childCount; i += 2) {
			currentGenerator.addLabel(ctx.getChild(i).getChild(1).getText(), paramOffset, null, current.getType(ctx.getChild(i).getChild(0)));
			paramOffset += getOffSetValue(ctx.getChild(i));			
		}
		
		currentGenerator.updateLabelOffset(REGISTER_BYTE);
		
		return super.visitParamList(ctx);
	}

	@Override
	public Integer visitParam(WaccParser.ParamContext ctx) {
		return super.visitParam(ctx);
	}

	// ==================== STATEMENTS =================== //

	@Override
	public Integer visitStat_SKIP(WaccParser.Stat_SKIPContext ctx) {
		return super.visitStat_SKIP(ctx);
	}

	@Override
	public Integer visitStat_begin_stat_end(WaccParser.Stat_begin_stat_endContext ctx) {
		return super.visitStat_begin_stat_end(ctx);
	}

	@Override
	public Integer visitStat_Return_expr(WaccParser.Stat_Return_exprContext ctx) {
		evaluateExpr(ctx.getChild(1));
		//System.out.println(currentGenerator.getPrevious().getLabel() + ": " + currentGenerator.getPrevious().getMaxByte());
		int returnOffset = getReturnOffset(currentGenerator);
		//System.out.println("retoff="+ returnOffset);
		if (returnOffset != 0)
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, returnOffset)));

			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.pc)));
			//currentGenerator.updateLabelOffset(-REGISTER_BYTE);

		return super.visitStat_Return_expr(ctx);
	}
	private Integer getReturnOffset(FunctionGenerator f){
		//System.out.println(f.getLabel() + ": " + f.getMaxByte());
		if ((f.getLabel() != null) && f.getLabel().charAt(0)=='f'){
			return f.getMaxByte();
		} else {
			return getReturnOffset(f.getPrevious());
		}
	}


	@Override
	public Integer visitStat_Free_expr(WaccParser.Stat_Free_exprContext ctx) {
		evaluateExpr(ctx.getChild(1));
		storeAssignRhs(ctx.getChild(1));
		currentGenerator.add(new Operation(Operand.BL, new Label("p_free_pair")));
		flagSet.setFlag(Flags.FREE);
		return super.visitStat_Free_expr(ctx);
	}

	@Override
	public Integer visitStat_type_ident_equals_assignRHS(WaccParser.Stat_type_ident_equals_assignRHSContext ctx) {

		Type type = current.getType(ctx.getChild(0)); // Type of the
		// variableexcept slight
		String id = ctx.getChild(1).getText(); // Name of the variable
		ParseTree valueTree = ctx.getChild(3); // ParseTree of the value
		String value = valueTree.getText(); // value of the variable
		int offset = 1; // Bytes used by the variable (1 byte by Default)

		if (type.equals(CodeUtil.INT) || type.equals(CodeUtil.STRING) || type instanceof ArrayType) {
			offset = 4;
		}
		
		if (type instanceof PairType) {
			codePrinter.addNewPair(ctx.getChild(1).getText());
			offset = 4;
		}

		currentGenerator.updateCurrentByte(offset);
		currentGenerator.getLabelTable().add(id, currentGenerator.getCurrentByte(), value, type);
		return super.visitStat_type_ident_equals_assignRHS(ctx);
	}

	@Override
	public Integer visitStat_assignLHS_equals_assignRHS(WaccParser.Stat_assignLHS_equals_assignRHSContext ctx) {		
		return super.visitStat_assignLHS_equals_assignRHS(ctx);
	}

	@Override
	public Integer visitStat_Read_assignLHS(WaccParser.Stat_Read_assignLHSContext ctx) {

		String label = "p_read_";
		int offset = currentGenerator.getOffset(ctx.getChild(1).getText());


		if (ctx.getChild(1) instanceof WaccParser.AssignLHS_identContext){
			if (currentGenerator.getIdentOffset(ctx.getChild(1).getText())==4){
				flagSet.setFlag(Flags.READINT);
				label += "int";
			} else if (currentGenerator.getIdentOffset(ctx.getChild(1).getText()) ==1){
				flagSet.setFlag(Flags.READCHAR);
				label += "char";
			}
		}
		currentGenerator.add(new Operation(Operand.ADD, Register.r0, Register.sp, new Immediate(offset)));
		currentGenerator.add(new Operation(Operand.BL, new Label(label)));

		return super.visitStat_Read_assignLHS(ctx);
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

	@Override
	public Integer visitStat_Print_expr(WaccParser.Stat_Print_exprContext ctx) {

		ParseTree child = ctx.getChild(1);
		flagSet.setFlag(Flags.PRINT);
		evaluateExpr(child);
		generatePrintType(child);

		return super.visitStat_Print_expr(ctx);
	}

	@Override
	public Integer visitStat_Exit_expr(WaccParser.Stat_Exit_exprContext ctx) {
		return super.visitStat_Exit_expr(ctx);
	}

	@Override
	public Integer visitStat_semicolon_stat(WaccParser.Stat_semicolon_statContext ctx) {
		return super.visitStat_semicolon_stat(ctx);
	}

	// ==================== ASSIGNLHS =================== //

	@Override
	public Integer visitAssignLHS_ident(WaccParser.AssignLHS_identContext ctx) {
		return super.visitAssignLHS_ident(ctx);
	}

	@Override
	public Integer visitAssignLHS_pairElem(WaccParser.AssignLHS_pairElemContext ctx) {
		return super.visitAssignLHS_pairElem(ctx);
	}

	@Override
	public Integer visitAssignLHS_expr(WaccParser.AssignLHS_exprContext ctx) {
		
		//System.out.println(ctx.getChild(0).getText());
		if(ctx.getChild(0).getText().contains("[")){
	   main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg,Register.r4)));
	   main.add(new Operation(Operand.ADD,Register.r4,new Register(CodeUtil.sp,main.getMaxByte())));
	   try{
	  	 int value = Integer.parseInt(ctx.getChild(0).getChild(2).getText());
	  	 main.add(new Operation(Operand.LDR,new Constant(value)));
	   }catch(NumberFormatException e){
	  	 main.add(new Operation(Operand.LDR,new Constant(Integer.parseInt(currentGenerator.getValue(ctx.getChild(0).getChild(2).getText())))));
	   }
	   main.add(new Operation(Operand.BL,new Label("p_check_array_bounds")));
	   main.add(new Operation(Operand.LDR,Register.r4,new Address(Register.r4)));
	   main.add(new Operation(Operand.ADD,Register.r4,new Register(4,4 /*change*/)));
	   main.add(new Operation(Operand.ADD,Register.r4,Register.r4,new Label(CodeUtil.InputReg + ", LSL " + new Immediate(2))));
	   main.add(new Operation(Operand.MOV,CodeUtil.OutputReg,Register.r4));
	   main.add(new Operation(Operand.POP,new RegList(CodeUtil.InputReg,Register.r4)));
	   main.add(new Operation(Operand.STR,CodeUtil.InputReg,new Address(CodeUtil.OutputReg)));   
	   
	   flagSet.setFlag(Flags.ARRAY_BOUNDS);
	   
		}
		
		return super.visitAssignLHS_expr(ctx);
	}

	// ==================== ASSIGNRHS =================== //

	@Override
	public Integer visitAssignRHS_arrayLit(WaccParser.AssignRHS_arrayLitContext ctx) {
	// Generate code for the Expression
		/*currentGenerator.add(new Operation(Operand.SUB, Register.sp, Register.sp, new Immediate(4+getArraySize(ctx.getChild(0))*4)));
		evaluateExpr(ctx.getChild(0));
		currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(getArraySize(ctx.getChild(0)))));
		currentGenerator.add(new Operation(Operand.STR, Register.r0, Register.sp));
		currentGenerator.add(new Operation(Operand.MOV, Register.r0, Register.sp));
		currentGenerator.add(new Operation(Operand.STR, Register.r0, new Address(Register.sp, getArraySize(ctx.getChild(0))*4 + 8)));
			// Generator code for storing the Expression into r0 */
		return super.visitAssignRHS_arrayLit(ctx);
	}

	@Override
	public Integer visitAssignRHS_expr(WaccParser.AssignRHS_exprContext ctx) {
		// Generate code for the Expression
		evaluateExpr(ctx.getChild(0)); 
		// Generator code for storing the Expression into r0
		storeAssignRhs(ctx);
		return super.visitAssignRHS_expr(ctx);
	}

	@Override
	public Integer visitAssignRHS_pairElem(WaccParser.AssignRHS_pairElemContext ctx) {
		// TODO
		return super.visitAssignRHS_pairElem(ctx);
	}

	@Override
	public Integer visitAssignRHS_call(WaccParser.AssignRHS_callContext ctx) {
		// arglist exists
		if (ctx.getChildCount() != 4) {
			ParseTree child = ctx.getChild(3);
			int totalOffset = 0;
			for (int i = child.getChildCount() - 1; i >= 0; i -= 2) {
				evaluateExpr(child.getChild(i));
				int offset = -4;

				if (isBool(child.getChild(i)) || isChar(child.getChild(i))) {
					offset = -1;
				} else if (isIdent(child.getChild(i))) {
					offset = -(currentGenerator.getIdentOffset(child.getChild(i).getText()));
				}
				if (offset == -1) {
					currentGenerator.add(new Operation(Operand.STRB, Register.r0, new Address(Register.sp, offset, true)));
				} else if (offset == -4) {
					currentGenerator.add(new Operation(Operand.STR, Register.r0, new Address(Register.sp, offset, true)));
				}
				totalOffset -= offset;
			}
			currentGenerator.add(new Operation(Operand.BL, new Label("f_" + ctx.getChild(1).getText())));
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, Register.sp, new Immediate(totalOffset)));
		}

		storeAssignRhs(ctx);
		return super.visitAssignRHS_call(ctx);
	}
 
	private void storeAssignRhs(ParseTree ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_type_ident_equals_assignRHSContext) {
			Type type = current.getType(ctx.getParent().getChild(0));
			Operand operand = (currentGenerator.getIdentOffset(ctx.getParent().getChild(1).getText()) == 4) ? Operand.STR : Operand.STRB;

			if (!(type instanceof PairType)) {
				int offset = currentGenerator.getOffset(ctx.getParent().getChild(1).getText());
				Register register = (currentGenerator.getCurrentByte() == 0) ? Register.sp : new Register(CodeUtil.sp, currentGenerator.getCurrentByte());
				currentGenerator.add(new Operation(operand, Register.r0, new Address(register)));
				//TODO
			}
		} else if (ctx.getParent() instanceof WaccParser.Stat_assignLHS_equals_assignRHSContext) {
			if (ctx.getParent().getChild(0) instanceof WaccParser.AssignLHS_pairElemContext){
				
					currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
					currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.sp, currentGenerator.getOffset(extractId(ctx.getParent().getChild(0).getChild(0).getChild(1))) + 4)));
					currentGenerator.add(new Operation(Operand.BL, new Label("p_check_null_pointer")));
					flagSet.setFlag(Flags.PRINTNULLPOINTER);
					if (ctx.getParent().getChild(0).getChild(0) instanceof WaccParser.PairElem_FST_exprContext) {
						currentGenerator.add(new Operation(Operand.ADD, Register.r0, Register.r0, new Immediate(0)));
					}else{
						currentGenerator.add(new Operation(Operand.ADD, Register.r0, Register.r0, new Immediate(4)));
					}
					currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
					currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.r0)));
					currentGenerator.add(new Operation(Operand.BL, new Label("free")));
					currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(4)));
					currentGenerator.add(new Operation(Operand.BL, new Label("malloc")));
					currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r1)));
					currentGenerator.add(new Operation(Operand.STR, Register.r0, new Address(Register.r1)));
					currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
					currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0)));
					currentGenerator.add(new Operation(Operand.STR, Register.r0, new Address(Register.r1)));
				
				
			}else{
				String id = extractId(ctx.getParent().getChild(0).getChild(0));
				int offset = currentGenerator.getOffset(id);
				Operand operand = (currentGenerator.getIdentOffset(id) == 4) ? Operand.STR : Operand.STRB;
				currentGenerator.add(new Operation(operand, Register.r0, new Address(new Register(CodeUtil.sp, offset))));
			}
		}
	}

	private String extractId(ParseTree ctx) {
		String id = "Failed";
		if (ctx instanceof WaccParser.IdentContext || ctx instanceof WaccParser.Expr_identContext) {
			id = ctx.getText();
		} else if (ctx instanceof WaccParser.Expr_arrContext) {
			id = extractId(ctx.getChild(0));
		} else if (ctx instanceof WaccParser.PairElemContext) {
			id = extractId(ctx.getChild(1));
		}

		return id;
	}

	@Override
	public Integer visitAssignRHS_newPair(WaccParser.AssignRHS_newPairContext ctx) {
		otherStrCount += generateNewPair(ctx);
		return super.visitAssignRHS_newPair(ctx);
	}

	// ==================== EXPR =================== //
	
	/*
	 * General idea: We usually evaluate an expression in declaration and
	 * assignment under AssignRhs, so visitAssignRhs handles most of the cases
	 * but when we encounter situation like if_then_else or while_do loop, which
	 * are not under assignRhs, we have to evaluate the expression when
	 * we visit the expr_node
	 */

	@Override
	public Integer visitExpr_int(WaccParser.Expr_intContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTINT);
		}
		return 0;
	}

	@Override
	public Integer visitIntLiter(WaccParser.IntLiterContext ctx) {
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTINT);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_bool(WaccParser.Expr_boolContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitBoolLiter(WaccParser.BoolLiterContext ctx) {
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_char(WaccParser.Expr_charContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		return 0;
	}

	@Override
	public Integer visitChar_lit(WaccParser.Char_litContext ctx) {
		return 0;
	}

	@Override
	public Integer visitExpr_str(WaccParser.Expr_strContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTSTRING);
		}
		return 0;
	}

	@Override
	public Integer visitStr_lit(WaccParser.Str_litContext ctx) {
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTSTRING);
		}
		return 0;
	}

	@Override
	public Integer visitPairLiter(WaccParser.PairLiterContext ctx) {
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTREFERENCE);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_pairlit(WaccParser.Expr_pairlitContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTREFERENCE);
		}
		return 0;
	}
	
	@Override
	public Integer visitExpr_arr(WaccParser.Expr_arrContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_ident(WaccParser.Expr_identContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		
		Type type = currentGenerator.getType(ctx.getText());
		
		if (isPrintChild(ctx)) {
			if (type instanceof SingleType){
				if (type.getEnumType().equals(EnumType.INT)) {
					flagSet.setFlag(Flags.PRINTINT);
				} else if (type.getEnumType().equals(EnumType.CHAR)) {

				} else if (type.getEnumType().equals(EnumType.BOOL)) {
					flagSet.setFlag(Flags.PRINTBOOL);
				} else if (type.getEnumType().equals(EnumType.STRING)) {
					flagSet.setFlag(Flags.PRINTSTRING);
				}
			} else if (type instanceof PairType) {
					flagSet.setFlag(Flags.PRINTREFERENCE);
			} else if (type instanceof ArrayType) {
					if(type.getEnumType().equals(EnumType.CHAR)){
						//array of char = string
						flagSet.setFlag(Flags.PRINTSTRING);
					}else{
						//other types of array are printed as reference
						flagSet.setFlag(Flags.PRINTREFERENCE);
					}
			}
		}
		return 0;
	}

	@Override
	public Integer visitExpr_unary(WaccParser.Expr_unaryContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_MINUS(WaccParser.Expr_MINUSContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		flagSet.setFlag(Flags.OVERFLOW);
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTINT);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_PLUS(WaccParser.Expr_PLUSContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		flagSet.setFlag(Flags.OVERFLOW);
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTINT);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_MULT(WaccParser.Expr_MULTContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		flagSet.setFlag(Flags.OVERFLOW);
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTINT);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_DIV(WaccParser.Expr_DIVContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		flagSet.setFlag(Flags.DIVIDEDBYZERO);
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTINT);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_MOD(WaccParser.Expr_MODContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		flagSet.setFlag(Flags.DIVIDEDBYZERO);
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTINT);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_GT(WaccParser.Expr_GTContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_GTEQ(WaccParser.Expr_GTEQContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_LT(WaccParser.Expr_LTContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_LTEQ(WaccParser.Expr_LTEQContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_EQ(WaccParser.Expr_EQContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_NEQ(WaccParser.Expr_NEQContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_OR(WaccParser.Expr_ORContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_AND(WaccParser.Expr_ANDContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		if (isPrintChild(ctx)) {
			flagSet.setFlag(Flags.PRINTBOOL);
		}
		return 0;
	}

	@Override
	public Integer visitExpr_expr(WaccParser.Expr_exprContext ctx) {
		if (ctx.getParent() instanceof WaccParser.Stat_while_expr_do_stat_doneContext || ctx.getParent() instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			evaluateExpr(ctx);
		}
		return 0;
	}

	// ==================== TERMINAL =================== //

	@Override
	public Integer visitTerminal_is(WaccParser.Terminal_isContext ctx) {
		// SUB sp, sp, #
		if (currentGenerator.getMaxByte() != 0){
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateLabelOffset(currentGenerator.getMaxByte());
		}	
		return super.visitTerminal_is(ctx);
	}

	// =========================== IFTHENELSE =========================== //
	@Override
	public Integer visitTerminal_if(WaccParser.Terminal_ifContext ctx) {
		// Label Branch
		branchStack.push(branchNum);
		branchNum += 2;
		return super.visitTerminal_if(ctx);
	}

	@Override
	public Integer visitTerminal_then(WaccParser.Terminal_thenContext ctx) {

		// cmp r0, #0
		currentGenerator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));
		// BEQ label x
		currentGenerator.add(new Operation(Operand.BEQ, new Label("L" + branchStack.peek())));
		FunctionGenerator thenGenerator = new FunctionGenerator(null, currentGenerator);
		currentGenerator = thenGenerator;

		// new scope for then branch
		reservedByteStack.push(index);
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		currentGenerator.setCurrentByte(currentGenerator.getMaxByte());

		// SUB sp, sp, #
		if (currentGenerator.getMaxByte() != 0)
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, walker.getReservedByte(index))));

		return super.visitTerminal_then(ctx);
	}

	@Override
	public Integer visitTerminal_else(WaccParser.Terminal_elseContext ctx) {
		// ADD sp, sp # for the then-scope
		if (currentGenerator.getMaxByte() != 0)
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));

		// B label x+1
		currentGenerator.add(new Operation(Operand.B, new Label("L" + (branchStack.peek() + 1))));
		// Label x and new scope for else branch
		currentGenerator.addCode();

		currentGenerator = currentGenerator.getPrevious();
		FunctionGenerator elseGenerator = new FunctionGenerator(branchStack.peek().toString(), currentGenerator);
		currentGenerator = elseGenerator;

		// retreive the reservedBte for the new (current) scope
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLabelTable().getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		if (currentGenerator.getMaxByte() != 0)
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));

		return super.visitTerminal_else(ctx);
	}

	@Override
	public Integer visitTerminal_fi(WaccParser.Terminal_fiContext ctx) {

		if (currentGenerator.getMaxByte() != 0)
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
		// Label x+1
		currentGenerator.addBranch(branchStack.peek() + 1);
		branchStack.pop();
		// exit then and else branch
		currentGenerator.addCode();
		currentGenerator = currentGenerator.getPrevious();

		index = reservedByteStack.pop();
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLabelTable().getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		return super.visitTerminal_fi(ctx);
	}

	// =========================== WHILE =========================== //

	 /* The structure of the WHILE assembly code is as followed:
	  * Code generated from evaluating the condition expression
	  * cmp r0, #0  (check if condition is true)
	  * BEQ (jump to end of while loop if false)
	  * Code generated from evaluating the body code
	  * B (jump to label of the start of while loop)
	  * End of loop 
	  */
	
	@Override
	public Integer visitTerminal_while(WaccParser.Terminal_whileContext ctx) {
		// Saves the current branchNum
		branchStack.push(branchNum);
		// branchNum increase by two because while uses two labels
		branchNum += 2;
		// Label x (the beginning of the loop)
		currentGenerator.addBranch(branchStack.peek());
		return super.visitTerminal_while(ctx);
	}

	@Override
	public Integer visitTerminal_do(WaccParser.Terminal_doContext ctx) {

		// cmp r0, #0
		currentGenerator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));
		// BEQ label x+1
		currentGenerator.add(new Operation(Operand.BEQ, new Label("L" + (branchStack.peek() + 1))));

		// new scope
		FunctionGenerator whileGenerator = new FunctionGenerator(null, currentGenerator);
		currentGenerator = whileGenerator;
		reservedByteStack.push(index);
		maxIndex++;
		index = maxIndex;
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLabelTable().getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		// SUB sp, sp #
		if (currentGenerator.getMaxByte() != 0){
			currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));
			currentGenerator.updateLabelOffset(currentGenerator.getMaxByte());
			//currentGenerator.updateMaxByte(currentGenerator.getMaxByte());
		}
		return super.visitTerminal_do(ctx);
	}

	@Override
	public Integer visitTerminal_done(WaccParser.Terminal_doneContext ctx) {
		// ADD sp, sp, #
		if (currentGenerator.getMaxByte() != 0)
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));

		// B Lx
		currentGenerator.add(new Operation(Operand.B, new Label("L" + branchStack.peek())));
		// Label x+1 (End of loop)
		currentGenerator.addBranch(branchStack.peek() + 1);

		branchStack.pop();

		// exit while loop
		currentGenerator.addCode();
		currentGenerator = currentGenerator.getPrevious();

		index = reservedByteStack.pop();
		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLabelTable().getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		return super.visitTerminal_done(ctx);
	}

	// =========================== BEGINEND =========================== //
	@Override
	public Integer visitTerminal_begin(WaccParser.Terminal_beginContext ctx) {
		// PUSH {lr}
		if (ctx.getParent() instanceof WaccParser.ProgramContext) {
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
		}
		//if (walker.getNumArrays() > 0) {
		//	currentGenerator.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, walker.getNumArrays() * 4)));
		//}

		if (ctx.getParent() instanceof WaccParser.Stat_begin_stat_endContext) {
			reservedByteStack.push(index);
			maxIndex++;
			index = maxIndex;
		}

		currentGenerator.setMaxByte(walker.getReservedByte(index));
		int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLabelTable().getLastOffset();
		currentGenerator.setCurrentByte(currentByte);

		// SUB sp, sp, #
		if (currentGenerator.getMaxByte() != 0)
			main.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));

		return super.visitTerminal_begin(ctx);
	}

	@Override
	public Integer visitTerminal_end(WaccParser.Terminal_endContext ctx) {
		
		// ADD sp, sp, #
		if (currentGenerator.getMaxByte() != 0 && (!(ctx.getParent() instanceof WaccParser.FuncContext)))
			currentGenerator.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, currentGenerator.getMaxByte())));

		// MOV r0, #0
		// POP {pc} for Program
		if (ctx.getParent() instanceof WaccParser.ProgramContext) {
			currentGenerator.add(new Operation(Operand.MOV, Register.r0, new Immediate(0)));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.pc)));
		}

		currentGenerator = currentGenerator.getPrevious();

		//
		if (ctx.getParent() instanceof WaccParser.Stat_begin_stat_endContext || ctx.getParent() instanceof WaccParser.FuncContext) {
			index = reservedByteStack.pop();
			currentGenerator.setMaxByte(walker.getReservedByte(index));
			int currentByte = (currentGenerator.getLabelTable().isEmpty()) ? currentGenerator.getMaxByte() : currentGenerator.getLabelTable().getLastOffset();
			currentGenerator.setCurrentByte(currentByte);
		}

		return super.visitTerminal_end(ctx);
	}

	// =============================== PAIR ===============================//
	
	@Override
	public Integer visitPairElem_FST_expr(WaccParser.PairElem_FST_exprContext ctx) {
		return super.visitPairElem_FST_expr(ctx);
	}
	
	@Override
	public Integer visitPairELEM_SND_expr(WaccParser.PairELEM_SND_exprContext ctx) {
		return super.visitPairELEM_SND_expr(ctx);
	}

	// ==================== =================== //

	@Override
	public Integer visitArgList(WaccParser.ArgListContext ctx) {
		return super.visitArgList(ctx);
	}

	@Override
	public Integer visitBase_CHAR(WaccParser.Base_CHARContext ctx) {
		return super.visitBase_CHAR(ctx);
	}

	@Override
	public Integer visitBase_INT(WaccParser.Base_INTContext ctx) {
		return super.visitBase_INT(ctx);
	}

	@Override
	public Integer visitPair_PAIR(WaccParser.Pair_PAIRContext ctx) {
		return super.visitPair_PAIR(ctx);
	}

	@Override
	public Integer visitBase_BOOL(WaccParser.Base_BOOLContext ctx) {
		return super.visitBase_BOOL(ctx);
	}

	@Override
	public Integer visitPairType(WaccParser.PairTypeContext ctx) {
		return super.visitPairType(ctx);
	}

	@Override
	public Integer visitType_base(WaccParser.Type_baseContext ctx) {
		return super.visitType_base(ctx);
	}

	@Override
	public Integer visitBase_STR(WaccParser.Base_STRContext ctx) {
		return super.visitBase_STR(ctx);
	}

	@Override
	public Integer visitType_arr(WaccParser.Type_arrContext ctx) {
		return super.visitType_arr(ctx);
	}

	@Override
	public Integer visitIdent(WaccParser.IdentContext ctx) {
		return super.visitIdent(ctx);
	}

	@Override
	public Integer visitType_pair(WaccParser.Type_pairContext ctx) {
		return super.visitType_pair(ctx);
	}

	@Override
	public Integer visitPair_base_t(WaccParser.Pair_base_tContext ctx) {
		return super.visitPair_base_t(ctx);
	}

	@Override
	public Integer visitPair_type(WaccParser.Pair_typeContext ctx) {
		return super.visitPair_type(ctx);
	}

	@Override
	public Integer visitArrayLiter(WaccParser.ArrayLiterContext ctx) {
		int numChildren = ctx.getChildCount();
		
		if(ctx.getParent().getParent().getChild(1).getText().equals("=")){
		 return 0;
		}		
		if (ctx.getChildCount() == 2) {
			codePrinter.handleArrays(0, ctx, Statement.ARRAY_INIT_EMPTY, main, codeGenerator, walker, codePrinter.getPairUtil());
			return super.visitArrayLiter(ctx);
		}
		Type elementsType = current.getExprType(ctx.getChild(1));
		if (elementsType.equals(new SingleType(EnumType.INT))) {
			codePrinter.handleArrays(numChildren, ctx, Statement.ARRAY_INIT_INT, currentGenerator, codeGenerator, walker, codePrinter.getPairUtil());
			return super.visitArrayLiter(ctx);
		} else if (elementsType.equals(new SingleType(EnumType.CHAR))) {
			codePrinter.handleArrays(numChildren, ctx, Statement.ARRAY_INIT_CHAR, currentGenerator, codeGenerator, walker, codePrinter.getPairUtil());
			return super.visitArrayLiter(ctx);
		} else if (elementsType.equals(new SingleType(EnumType.BOOL))) {
			codePrinter.handleArrays(numChildren, ctx, Statement.ARRAY_INIT_BOOL, currentGenerator, codeGenerator, walker, codePrinter.getPairUtil()); // allows
			// bool[]
			// x
			// =
			return super.visitArrayLiter(ctx);
		} else if (elementsType.equals(new SingleType(EnumType.STRING))) {
			otherStrCount += codePrinter.handleArrays(numChildren, ctx, Statement.ARRAY_INIT_STRING, currentGenerator, codeGenerator, walker, codePrinter.getPairUtil());
			return super.visitArrayLiter(ctx);
		}
		codePrinter.handleArrays(numChildren, ctx, Statement.ARRAY_INIT_PAIR, currentGenerator, codeGenerator, walker, codePrinter.getPairUtil());
		return super.visitArrayLiter(ctx); 
	}

	// ==================== =================== //

	private int evaluateExpr(ParseTree tree) {

		//recurse through a tree of expression to generate code
		if (isInt(tree)) {
			return evaluateFourBytesExpr(tree);

		} else if (isBool(tree)) {
			return evaluateSingleByteExpr(tree);

		} else if (isChar(tree)) {
			return evaluateSingleByteExpr(tree);

		} else if (isString(tree)) {
			return evaluateFourBytesExpr(tree);

		} else if (tree instanceof WaccParser.Expr_pairlitContext) {
			
		} else if (isIdent(tree)) {
			if (currentGenerator.getIdentOffset(tree.getText()) == 4) {
				return evaluateFourBytesExpr(tree);
			} else if (currentGenerator.getIdentOffset(tree.getText()) == 1) {
				return evaluateSingleByteExpr(tree);
			}

		} else if (tree instanceof WaccParser.Expr_unaryContext) {
			ParseTree child = tree.getChild(0);
			if (child instanceof WaccParser.Unary_excContext) {
				evaluateExpr(tree.getChild(1));
				currentGenerator.add(new Operation(Operand.EOR, Register.r0, Register.r0, Register.r1));
			} else if (child instanceof WaccParser.Unary_intContext) {
				evaluateExpr(tree.getChild(1));
			} else if (child instanceof WaccParser.Unary_lenContext) {
				int offset = currentGenerator.getOffset(tree.getChild(1).getText());
				currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.sp, offset)));
				currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.r0)));
			} else if (child instanceof WaccParser.Unary_minusContext) {
				evaluateExpr(tree.getChild(1));
				currentGenerator.add(new Operation(Operand.RSBS, Register.r0, Register.r0, new Immediate(0)));
				currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
			} else if (child instanceof WaccParser.Unary_ordContext) {
				evaluateExpr(tree.getChild(1));
			}
		} else if (tree instanceof WaccParser.Expr_PLUSContext  || 
							 tree instanceof WaccParser.Expr_MINUSContext || 
						 	 tree instanceof WaccParser.Expr_MULTContext  || 
							 tree instanceof WaccParser.Expr_DIVContext   || 
							 tree instanceof WaccParser.Expr_MODContext   || 
						 	 tree instanceof WaccParser.Expr_EQContext    || 
							 tree instanceof WaccParser.Expr_NEQContext   || 
							 tree instanceof WaccParser.Expr_GTContext    || 
							 tree instanceof WaccParser.Expr_GTEQContext  ||
							 tree instanceof WaccParser.Expr_LTContext    || 
							 tree instanceof WaccParser.Expr_LTEQContext) {
			int offset = evaluateExpr(tree.getChild(0));
			currentGenerator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
			//System.out.println(currentGenerator.getLabelTable());
			currentGenerator.updateLabelOffset(offset);
			currentGenerator.updateMaxByte(offset);
			//System.out.println(currentGenerator.getLabelTable());
			evaluateExpr(tree.getChild(2));
			currentGenerator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
			currentGenerator.add(new Operation(Operand.POP, new RegList(Register.r0)));
			currentGenerator.updateLabelOffset(-offset);
			currentGenerator.updateMaxByte(-offset);
			evaluateBinOp(tree);
			return offset;
		} else if (tree instanceof WaccParser.Expr_ANDContext) {
			evaluateExpr(tree.getChild(0));
			evaluateBinOp(tree);
			evaluateExpr(tree.getChild(2));
			if (flagSet.getFlag(Flags.AND)) {
				currentGenerator.addBranch(branchNum);
				branchNum++;
				flagSet.clearFlag(Flags.AND);
			}
		} else if (tree instanceof WaccParser.Expr_ORContext) {
			evaluateExpr(tree.getChild(0));
			evaluateBinOp(tree);
			evaluateExpr(tree.getChild(2));
			
			if (flagSet.getFlag(Flags.OR) && !(tree.getParent() instanceof WaccParser.Expr_ORContext)) {
				currentGenerator.addBranch(orBranchNum);
				flagSet.clearFlag(Flags.OR);
			}
		} else if (tree instanceof WaccParser.ArrayLiterContext) {
			return 0;
		} else if (tree instanceof WaccParser.Expr_exprContext) {
			return evaluateExpr(tree.getChild(1));
		} else if (tree instanceof WaccParser.Expr_arrContext) {
			//TODO
		}
		return 0;

	}

	private void evaluateBinOp(ParseTree tree) {

		if (tree instanceof WaccParser.Expr_PLUSContext) {
			currentGenerator.add(new Operation(Operand.ADDS, Register.r0, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
		} else if (tree instanceof WaccParser.Expr_MINUSContext) {
			currentGenerator.add(new Operation(Operand.SUBS, Register.r0, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.BLVS, new Label("p_throw_overflow_error")));
		} else if (tree instanceof WaccParser.Expr_MULTContext) {
			currentGenerator.add(new Operation(Operand.SMULL, Register.r0, Register.r1, Register.r0, Register.r1));
			currentGenerator.add(new Operation(Operand.CMP, Register.r1, Register.r0, new Label("ASR #31")));
			currentGenerator.add(new Operation(Operand.BLNE, new Label("p_throw_overflow_error")));
		} else if (tree instanceof WaccParser.Expr_DIVContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_check_divide_by_zero")));
			currentGenerator.add(new Operation(Operand.BL, new Label("__aeabi_idiv")));
		} else if (tree instanceof WaccParser.Expr_MODContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_check_divide_by_zero")));
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
			currentGenerator.add(new Operation(Operand.BEQ, new Label("L" + branchNum)));
			if (!(tree.getParent() instanceof WaccParser.Expr_ANDContext)) {
				flagSet.setFlag(Flags.AND);
			}
		} else if (tree instanceof WaccParser.Expr_ORContext) {
			currentGenerator.add(new Operation(Operand.CMP, Register.r0, new Immediate(1)));
			if (!((tree.getChild(0) instanceof WaccParser.Expr_ORContext)||(tree.getChild(2) instanceof WaccParser.Expr_ORContext))) {
				flagSet.setFlag(Flags.OR);
				orBranchNum = branchNum;
			}
			currentGenerator.add(new Operation(Operand.BEQ, new Label("L" + orBranchNum)));
			//System.out.println(orBranchNum);
			branchNum++;
			
		}
	}

	private int evaluateFourBytesExpr(ParseTree tree) {
		if (isInt(tree)) {
			currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Constant(tree.getChild(0).getText())));
		} else if (isString(tree)) {
			codeGenerator.addMessage(new MessageGenerator(msgCount, tree.getText()));
			currentGenerator.add(new Operation(Operand.LDR, Register.r0, new Label("=msg_" + msgCount++)));
		} else {
			//System.out.println(tree.getText());
			int offset = currentGenerator.getOffset(tree.getText());
			//System.out.println(offset);
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

	private boolean isIdent(ParseTree child) {
		return child instanceof WaccParser.Expr_identContext;
	}

	private int generateNewPair(ParseTree ctx) {
		Type element1 = current.getExprType(ctx.getChild(2));
		Type element2 = current.getExprType(ctx.getChild(4));
		EnumType firstType = getTypeEnum(element1);
		EnumType secondType = getTypeEnum(element2);
		return codePrinter.handlePairs(firstType, secondType, currentGenerator, codeGenerator, walker, ctx);
	}

	private EnumType getTypeEnum(Type element) {
		if (element.equals(new SingleType(EnumType.STRING))) {
			return EnumType.STRING;
		} else if (element.equals(new SingleType(EnumType.INT))) {
			return EnumType.INT;
		} else if (element.equals(new SingleType(EnumType.CHAR))) {
			return EnumType.CHAR;
		} else if (element.equals(new SingleType(EnumType.BOOL))) {
			return EnumType.BOOL;
		} else if (element.equals(new SingleType(EnumType.NULL))) {
			return EnumType.NULL;
		} else if (element != null) {
			return EnumType.PAIR;
		}
		return null;
	}

	private boolean matchKeyword(ParseTree tree, String str) {
		return (tree.getText().toUpperCase().equals(str));
	}

	public void generateCode() {
		codeGenerator.addFunction(main);

		if (flagSet.getFlag(Flags.PRINTNULLPOINTER)){
			codeGenerator.addFunction(codePrinter.p_check_null_pointer(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"NullReferenceError: dereference a null reference\\n\\0\""));
			flagSet.setFlag(Flags.RUNTIME);
			flagSet.setFlag(Flags.PRINTSTRING);
		}
		
		if(flagSet.getFlag(Flags.ARRAY_BOUNDS)){
		 codeGenerator.addFunction(codePrinter.p_check_array_bounds(msgCount));
		 codeGenerator.addMessage(new MessageGenerator(msgCount++,"ArrayIndexOutOfBoundsError: nevative index\n\0"));
		 codeGenerator.addMessage(new MessageGenerator(msgCount,"ArrayIndexOutOfBoundsError: index too large\n\0"));
		 flagSet.setFlag(Flags.RUNTIME);
		 flagSet.setFlag(Flags.PRINTSTRING);
		}
		
		if (flagSet.getFlag(Flags.OVERFLOW)) {
			codeGenerator.addFunction(codePrinter.p_throw_overflow_error(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"OverflowError: the result is too small/large to store in a 4-byte signed-integer.\\n\""));
			flagSet.setFlag(Flags.PRINTSTRING);
			flagSet.setFlag(Flags.RUNTIME);
		}

		if (flagSet.getFlag(Flags.RUNTIME)) {
			codeGenerator.addFunction(codePrinter.p_throw_runtime_error());
		}

		if (flagSet.getFlag(Flags.READINT)) {
			codeGenerator.addFunction(codePrinter.p_read_int(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"%d\\0\""));
		}

		if (flagSet.getFlag(Flags.READCHAR)) {
			codeGenerator.addFunction(codePrinter.p_read_char(msgCount));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\" %c\\0\""));
			msgCount++;
		}

		if (flagSet.getFlag(Flags.PRINTSTRING)) {
			codeGenerator.addFunction(codePrinter.handlePrint(msgCount, Statement.PRINTLN_STR));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"%.*s\\0\""));
		}

		if (flagSet.getFlag(Flags.PRINTINT)) {
			codeGenerator.addFunction(codePrinter.handlePrint(msgCount, Statement.PRINTLN_INT));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"%d\\0\""));
		}

		if (flagSet.getFlag(Flags.PRINTBOOL)) {
			codeGenerator.addFunction(codePrinter.handlePrint(msgCount, Statement.PRINTLN_BOOL));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"true\\0\""));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"false\\0\""));
		}

		if (flagSet.getFlag(Flags.PRINTLN)) {
			codeGenerator.addFunction(codePrinter.handlePrint(msgCount, Statement.PRINT_LN));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"\\0\""));
		}

		if (flagSet.getFlag(Flags.PRINTREFERENCE)){
			codeGenerator.addFunction(codePrinter.handlePrint(msgCount, Statement.PRINTLN_REFERENCE));
			codeGenerator.addMessage(new MessageGenerator(msgCount++, "\"%p\\0\""));
		}
		
		if (flagSet.getFlag(Flags.FREE)){
			codeGenerator.addFunction(codePrinter.p_free_pair());
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

	private void generatePrintType(ParseTree tree) {
		if (tree instanceof WaccParser.Expr_intContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
		} else if (tree instanceof WaccParser.Expr_boolContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_bool")));
		} else if (tree instanceof WaccParser.Expr_charContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("putchar")));
		} else if (tree instanceof WaccParser.Expr_strContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_string")));
		} else if (tree instanceof WaccParser.Expr_pairlitContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_reference")));
		} else if (tree instanceof WaccParser.Expr_arrContext){
			if(currentGenerator.getEnumType(extractId(tree.getChild(0))).equals(EnumType.CHAR)){
				//array of char = string
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_string")));
			}else{
				//other types of array are printed as reference
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_reference")));
			}
			
		} else if (tree instanceof WaccParser.Expr_identContext) {
			Type type = currentGenerator.getType(tree.getText());
			if (type instanceof SingleType) {
				if (type.getEnumType().equals(EnumType.INT)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
				} else if (type.getEnumType().equals(EnumType.CHAR)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("putchar")));
				} else if (type.getEnumType().equals(EnumType.BOOL)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_bool")));
				} else if (type.getEnumType().equals(EnumType.STRING)) {
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_string")));
				}
			} else if (type instanceof PairType) {
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_reference")));
			} else if (type instanceof ArrayType) {
				// TODO: what is the BL when priting Array
				if(type.getEnumType().equals(EnumType.CHAR)){
					//array of char = string
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_string")));
				}else{
					//other types of array are printed as reference
					currentGenerator.add(new Operation(Operand.BL, new Label("p_print_reference")));
				}
			}
		} else if (tree instanceof WaccParser.Expr_exprContext) {
			// TODO
		} else if (tree instanceof WaccParser.Expr_unaryContext) {
			ParseTree child = tree.getChild(0);
			if (child instanceof WaccParser.Unary_excContext) {
				
			} else if (child instanceof WaccParser.Unary_intContext) {
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_char")));
			} else if (child instanceof WaccParser.Unary_lenContext) {
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
			} else if (child instanceof WaccParser.Unary_minusContext) {
				currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
			} else if (child instanceof WaccParser.Unary_ordContext) {
				currentGenerator.add(new Operation(Operand.BL, new Label("putchar")));
			}
		} else if (tree instanceof WaccParser.Expr_MINUSContext || tree instanceof WaccParser.Expr_PLUSContext || tree instanceof WaccParser.Expr_MULTContext || tree instanceof WaccParser.Expr_DIVContext
				|| tree instanceof WaccParser.Expr_MODContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_int")));
		} else if (tree instanceof WaccParser.Expr_GTContext || tree instanceof WaccParser.Expr_GTEQContext || tree instanceof WaccParser.Expr_LTContext || tree instanceof WaccParser.Expr_LTEQContext
				|| tree instanceof WaccParser.Expr_EQContext || tree instanceof WaccParser.Expr_NEQContext || tree instanceof WaccParser.Expr_ORContext || tree instanceof WaccParser.Expr_ANDContext) {
			currentGenerator.add(new Operation(Operand.BL, new Label("p_print_bool")));
		}
	}
	
	private int getArraySize(ParseTree ctx){
		return (ctx.getChildCount() - 1 )/2;
	}

	private void initialiseRegs() {
		registers.addAll();
	}

}
