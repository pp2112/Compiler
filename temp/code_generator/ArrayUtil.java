package code_generator;

import org.antlr.v4.runtime.tree.ParseTree;

import semantic_check.EnumType;
import semantic_check.SingleType;
import semantic_check.SymbolTable;
import semantic_check.TreeWalkerUtil;

import generator.CodeGenerator;
import generator.FunctionGenerator;
import generator.MessageGenerator;
import generator.RegList;
import code.*;

public class ArrayUtil {
	private RegList regs;
	private int offset;
	private int numStrings;
	private int numArrays;
	private int numPairArrays;
	private semantic_check.TreeWalkerUtil util;
	private final int FOUR_BYTES = 4;

	ArrayUtil(RegList regs) {
		this.regs = regs;
		this.offset = 0;
		this.numStrings = 0;
		this.numArrays = 0;
		this.util = new TreeWalkerUtil(new SymbolTable());
	}

	public int handleArrayStat(int numChildren, ParseTree ctx, Statement stat_type, FunctionGenerator main, CodeGenerator code, PreCodeGenWalker walker, PairUtil pairUtil) {
		switch (stat_type) {
		case ARRAY_INIT_STRING:
			HandleArraySTRING_Lit(numChildren, ctx, main, code, walker);
			numStrings += (numChildren - 1) / 2;
			break;
		case ARRAY_INIT_BOOL:
			HandleArrayBOOL_Lit(numChildren, ctx, main);
			break;
		case ARRAY_INIT_PAIR:
			HandleArrayPAIR_Lit(numChildren, ctx, main, walker, pairUtil);
			break;
		case ARRAY_INIT_INT:
			HandleArrayINT_Lit(numChildren, ctx, main);
			break;
		case ARRAY_INIT_CHAR:
			HandleArrayCHAR_Lit(numChildren, ctx, main);
			break;
		case ARRAY_INIT_EMPTY:
			HandleEmptyArray_Lit(0, ctx, main);
			break;
		default:
			System.out.println("Invalid Array Type");
			break;
		}
		numArrays++;
		return numStrings;
	}

	private void HandleEmptyArray_Lit(int numChildren, ParseTree ctx, FunctionGenerator main) {
		int offset = FOUR_BYTES;
		main.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, offset)));
		for (int i = 1, j = 1; i < numChildren; i += 2, j++) {
			main.add(new Operation(Operand.LDR, CodeUtil.InputReg, new Constant(ctx.getChild(i).getText())));
			main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, j * FOUR_BYTES))));
		}
		addOffsets(main, EnumType.INT, numChildren, ctx);
	}

	private void HandleArrayPAIR_Lit(int numChildren, ParseTree ctx, FunctionGenerator main, PreCodeGenWalker walker, PairUtil pairUtil) {
		int numElements = (numChildren - 1) / 2;
		int offset = 0;
		main.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, FOUR_BYTES * (numElements + 1))));
		for (int i = 1, j = 1; i < numChildren; i += 2, j++) {
			if (util.getExprType(ctx.getChild(i)).equals(new SingleType(EnumType.NULL))) {
				main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(0)));
			} else {
				String varName = ctx.getChild(i).getText();
				offset = (pairUtil.getNumPairsSoFar() + numArrays + 1) * FOUR_BYTES + numPairArrays * 8;
				int pairoffset = (walker.getVarMapping(varName)) + offset;
				System.out.println(pairoffset);
				main.add(new Operation(Operand.LDR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, pairoffset))));
			}
			main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, (j * FOUR_BYTES)))));
		}
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(numElements)));
		main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(Register.sp)));
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, Register.sp));
		main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, FOUR_BYTES * (numElements) + (numArrays + 1) * FOUR_BYTES + numPairArrays * 8))));
		main.add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp, FOUR_BYTES * (numElements + 2))));
		numPairArrays += 1;
	}

	private void HandleArrayBOOL_Lit(int numChildren, ParseTree ctx, FunctionGenerator main) {
		int elements = (numChildren - 1) / 2;
		int offset = FOUR_BYTES + elements;
		main.updateLabelOffset(offset);
		main.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, offset)));
		for (int i = 1, j = 1; i < numChildren; i += 2, j++) {
			main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(getBoolVal(ctx.getChild(i).getText()))));
			main.add(new Operation(Operand.STRB, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, FOUR_BYTES + (j - 1)))));
		}
		addOffsets(main, EnumType.CHAR, numChildren, ctx);
	}

	private void HandleArraySTRING_Lit(int numChildren, ParseTree ctx, FunctionGenerator main, CodeGenerator code, PreCodeGenWalker walker) {
		int offset = 2 * (numChildren - 1) + FOUR_BYTES;
		main.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, offset)));
		main.updateLabelOffset(offset);
		for (int i = 1, j = 1; i < numChildren; i += 2, j++) {
			code.addMessage(new MessageGenerator(walker.getMsgCount() + (numStrings + (j - 1)), ctx.getChild(i).getText()));
			main.add(new Operation(Operand.LDR, CodeUtil.InputReg, new Constant("msg_" + (numStrings + (j - 1)))));
			main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, j * FOUR_BYTES))));
		}
		addOffsets(main, EnumType.INT, numChildren, ctx);
	}

	private void HandleArrayCHAR_Lit(int numChildren, ParseTree ctx, FunctionGenerator main) {
		int elements = (numChildren - 1) / 2;
		int offset = FOUR_BYTES + elements;
		main.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, offset)));
		main.updateLabelOffset(offset);
		for (int i = 1, j = 1; i < numChildren; i += 2, j++) {
			main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(ctx.getChild(i).getText())));
			main.add(new Operation(Operand.STRB, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, FOUR_BYTES + (j - 1)))));
		}
		addOffsets(main, EnumType.CHAR, numChildren, ctx);
	}

	private void HandleArrayINT_Lit(int numChildren, ParseTree ctx, FunctionGenerator main) {
		int offset = 2 * (numChildren - 1) + FOUR_BYTES;
		main.add(new Operation(Operand.SUB, Register.sp, new Register(CodeUtil.sp, offset)));
		main.updateLabelOffset(offset);
		for (int i = 1, j = 1; i < numChildren; i += 2, j++) {
			main.add(new Operation(Operand.LDR, CodeUtil.InputReg, new Constant(ctx.getChild(i).getText())));
			main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, (j * FOUR_BYTES)))));
		}
		addOffsets(main, EnumType.INT, numChildren, ctx);
	}

	private void addOffsets(FunctionGenerator main, EnumType type, int numChildren, ParseTree ctx) {
		switch (type) {
		case STRING:
			bigOffsets(main, numChildren, ctx);
			break;
		case INT:
			bigOffsets(main, numChildren, ctx);
			break;
		case CHAR:
			smallOffsets(main, numChildren, ctx);
			break;
		case BOOL:
			smallOffsets(main, numChildren, ctx);
			break;
		default:
			break;
		}
	}

	private void bigOffsets(FunctionGenerator main, int numChildren, ParseTree ctx) {
		int elements = (numChildren - 1) / 2;
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate((numChildren - 1) / 2)));
		main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp))));
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, Register.sp));
		int arrayOffset = (main.getOffset(ctx.getParent().getParent().getChild(1).getText()) + (elements + 1) * 4) - 2*(numChildren - 1)-FOUR_BYTES;
		main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, arrayOffset))));
		this.offset += FOUR_BYTES * (1 + elements);
	}

	private void smallOffsets(FunctionGenerator main, int numChildren, ParseTree ctx) {
		int elements = (numChildren - 1) / 2;
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate((numChildren - 1) / 2)));
		main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp))));
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, Register.sp));
		int arrayOffset = (main.getOffset(ctx.getParent().getParent().getChild(1).getText()) + (elements + FOUR_BYTES)) - FOUR_BYTES - elements;
		main.add(new Operation(Operand.STR, CodeUtil.InputReg, new Address(new Register(CodeUtil.sp, arrayOffset))));
		this.offset += FOUR_BYTES + elements;
	}

	public int getFinalOffset() {
		return offset == 0 ? 0 : offset;
	}

	private int getBoolVal(String s) {
		if (s.equals("true")) {
			return 1;
		}
		return 0;
	}

}
