package code_generator;
import generator.CodeGenerator;
import generator.FunctionGenerator;
import generator.MessageGenerator;
import generator.RegList;

import java.util.HashMap;

import org.antlr.v4.runtime.tree.ParseTree;

import semantic_check.EnumType;
import code.Address;
import code.Constant;
import code.Immediate;
import code.Label;
import code.Operand;
import code.Operation;
import code.Register;
import code.Variable;


public class PairUtil {

	private RegList regs;	
	int pairOffset;
	int currentOffset;
	private int strCount;
	private PreCodeGenWalker walker;
	private int numPairs;

	
 PairUtil(RegList regs,PreCodeGenWalker walker,FunctionGenerator main){
 	this.regs = regs;
 	this.pairOffset = walker.getNumPairs() * 4;
 	this.currentOffset = 0;
 	this.strCount = 0;
 	this.walker = walker;
 	this.numPairs = 0;
 }
 
	public int handlePairStat(EnumType firstType, EnumType secondType, FunctionGenerator main, CodeGenerator codeGen, PreCodeGenWalker walker,ParseTree ctx) {
	 int res = 0;
	 if(firstType.equals(EnumType.PAIR) || secondType.equals(EnumType.PAIR) || firstType.equals(EnumType.NULL) || secondType.equals(EnumType.NULL)){
	  res = handlePairCase(firstType, secondType,ctx,main,codeGen,walker);
	  return res;
	 }else{
	  res += handleElemAllocation(firstType,2, ctx, main,codeGen,walker);
	  res += handleElemAllocation(secondType, 4, ctx, main, codeGen, walker);
	 }	 
	 main.add(new Operation(Operand.MOV,CodeUtil.InputReg, new Immediate(8)));
	 main.add(new Operation(Operand.BL,new Label("malloc")));
	 main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg,Register.r2)));
	 main.add(new Operation(Operand.STR,Register.r2,new Address(CodeUtil.InputReg)));
	 main.add(new Operation(Operand.STR,CodeUtil.OutputReg,new Address(CodeUtil.InputReg,4)));
	 main.add(new Operation(Operand.STR,CodeUtil.InputReg,new Address(Register.sp,main.getOffset(ctx.getParent().getChild(1).getText()))));
	 pairOffset -= 4;
	 currentOffset+=4;
	 numPairs ++;
	 return res;	 
	}
	
	private int handleIntAllocation(EnumType type, int index, ParseTree ctx,
			FunctionGenerator main, CodeGenerator codeGen,
			PreCodeGenWalker walker) {
		
		int offsetSize = getOffsetSizeType(type);
		Variable assigner = getAssigner(type,walker,ctx,index);
		main.add(new Operation(Operand.LDR,CodeUtil.InputReg,assigner));		
		main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(offsetSize)));
		main.add(new Operation(Operand.BL,new Label("malloc")));
		main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg)));
		main.add(new Operation(Operand.STR,CodeUtil.OutputReg,new Address(CodeUtil.InputReg)));
		main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		return 0;
	}

	private int handleBoolAllocation(EnumType type, int index, ParseTree ctx,
			FunctionGenerator main, CodeGenerator codeGen,
			PreCodeGenWalker walker) {
		
		int offsetSize = getOffsetSizeType(type);
		Variable assigner = getPairAssigner(type,walker,ctx,index);
		main.add(new Operation(Operand.MOV,CodeUtil.InputReg,assigner));		
		main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(offsetSize)));
		main.add(new Operation(Operand.BL,new Label("malloc")));
		main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg)));
		main.add(new Operation(Operand.STRB,CodeUtil.OutputReg,new Address(CodeUtil.InputReg)));
		main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		return 0;		
	}

	private int handleStrElemAllocation(EnumType type, int index,
			ParseTree ctx, FunctionGenerator main, CodeGenerator codeGen,
			PreCodeGenWalker walker) {
		int offsetSize = getOffsetSizeType(type);		
		codeGen.addMessage(new MessageGenerator(walker.getMsgCount()+strCount,ctx.getChild(index).getText()));		
		Variable assigner = getAssigner(type,walker,ctx,index);
		main.add(new Operation(Operand.LDR,CodeUtil.InputReg,assigner));		
		main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(offsetSize)));
		main.add(new Operation(Operand.BL,new Label("malloc")));
		main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg)));
		main.add(new Operation(Operand.STR,CodeUtil.OutputReg,new Address(CodeUtil.InputReg)));
		main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		return 0;		
	}


	private int handleCharElemAllocation(EnumType type, int index,
			ParseTree ctx, FunctionGenerator main, CodeGenerator codeGen,
			PreCodeGenWalker walker) {		
		int offsetSize = getOffsetSizeType(type);
		Variable assigner = getPairAssigner(type,walker,ctx,index);
		main.add(new Operation(Operand.MOV,CodeUtil.InputReg,assigner));		
		main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(offsetSize)));
		main.add(new Operation(Operand.BL,new Label("malloc")));
		main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg)));
		main.add(new Operation(Operand.STRB,CodeUtil.OutputReg,new Address(CodeUtil.InputReg)));
		main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		return 0;		
	}
	private Variable getPairAssigner(EnumType type, PreCodeGenWalker walker,
			ParseTree ctx, int index) {
		 switch(type){
		  case INT : 	return new Constant(ctx.getChild(index).getText());
		  case CHAR: 	return new Immediate(ctx.getChild(index).getText());
		  case STRING:	Variable result = new Constant("msg_"+walker.getMsgCount()+strCount); strCount++; return result;
		  case BOOL: 	return new Immediate(getBoolImm(ctx.getChild(index).getText()));
		  case PAIR:	return new Immediate(ctx.getChild(index).getText());
		  default : 	return null;
		}
	}

	private int getBoolImm(String text) {
	 if(text.equals("true")){
	  return 1;
	 }
	 return 0;
	}

	private void handleNullCase(int index, ParseTree ctx,
			FunctionGenerator main, CodeGenerator codeGen,
			PreCodeGenWalker walker) {		
		int offsetSize = getOffsetSizeType(EnumType.NULL);
		main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(offsetSize)));
		main.add(new Operation(Operand.BL,new Label("malloc")));
		main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg)));
		main.add(new Operation(Operand.STR,CodeUtil.OutputReg,new Address(CodeUtil.InputReg)));	
	}
	
	private int handleElemAllocation(EnumType type, int index, ParseTree ctx, FunctionGenerator main,CodeGenerator codeGen,PreCodeGenWalker walker){
		if(type.equals(EnumType.NULL)){
	     handleNullCase(index,ctx,main,codeGen,walker);
	     return 0;
		}else if(type.equals(EnumType.CHAR)){
		 handleCharElemAllocation(type, index, ctx, main, codeGen, walker);
		 return 0;
		}else if(type.equals(EnumType.STRING)){
		 return handleStrElemAllocation(type, index, ctx, main, codeGen, walker);	
		}else if(type.equals(EnumType.BOOL)){
		 return handleBoolAllocation(type, index, ctx, main, codeGen, walker);
		}else if(type.equals(EnumType.INT)){
		 return handleIntAllocation(type, index, ctx, main, codeGen, walker);
		}
		return 0;
	}

	
	private int handlePairCase(EnumType firstType,EnumType secondType, ParseTree ctx,
			FunctionGenerator main, CodeGenerator codeGen,
			PreCodeGenWalker walker) {				
		
		if((firstType.equals(EnumType.PAIR) || firstType.equals(EnumType.NULL)) && 
		   (secondType.equals(EnumType.PAIR) || secondType.equals(EnumType.NULL))){
			handlePairsInside(firstType,2,ctx,main);
			handlePairsInside(secondType, 4, ctx, main);
			currentOffset = 0;
			pairOffset -= 4;
			main.add(new Operation(Operand.MOV,CodeUtil.InputReg, new Immediate(8)));
			main.add(new Operation(Operand.BL,new Label("malloc")));
			main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg,Register.r2)));
			main.add(new Operation(Operand.STR,Register.r2,new Address(CodeUtil.InputReg)));
			main.add(new Operation(Operand.STR,CodeUtil.OutputReg,new Address(CodeUtil.InputReg,4)));
			main.add(new Operation(Operand.STR,CodeUtil.InputReg,new Address(Register.sp,pairOffset)));	
			
			return 0;
		}			
		if(firstType.equals(EnumType.PAIR) || firstType.equals(EnumType.NULL)){
			int offsetSize = getOffsetSizeType(EnumType.PAIR);		
			
			if(firstType.equals(EnumType.PAIR)){
			 main.add(new Operation(Operand.LDR,CodeUtil.InputReg,new Address(new Register(CodeUtil.sp,pairOffset))));
			} else{
			 main.add(new Operation(Operand.MOV,CodeUtil.InputReg,new Immediate(0)));
			}
			
			handlePairCodeGeneration1(main, offsetSize, firstType);			
			handleElemAllocation(secondType, 4, ctx, main, codeGen, walker);	
			handlePairCodeGeneration2(main, offsetSize, firstType);						
		}else if (secondType.equals(EnumType.PAIR) || secondType.equals(EnumType.NULL)){
			int offsetSize = getOffsetSizeType(EnumType.PAIR);	
			handleElemAllocation(firstType, 2, ctx, main, codeGen, walker);
			if(secondType.equals(EnumType.PAIR)){
			 main.add(new Operation(Operand.LDR,CodeUtil.InputReg,new Address(new Register(CodeUtil.sp,pairOffset + 4))));
			} else{
			 main.add(new Operation(Operand.MOV,CodeUtil.InputReg,new Immediate(0)));
			}
			handlePairCodeGeneration1(main,offsetSize,secondType);
			handlePairCodeGeneration2(main, offsetSize, secondType);
		}			
		return 0;
	}

		private void handlePairCodeGeneration1(FunctionGenerator main, int offsetSize, EnumType Type) {
			main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
			main.add(new Operation(Operand.MOV, CodeUtil.InputReg, new Immediate(offsetSize)));
			main.add(new Operation(Operand.BL,new Label("malloc")));
			main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg)));
			main.add(new Operation(Operand.STR,CodeUtil.OutputReg,new Address(CodeUtil.InputReg)));
			main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		
	}
		
		private void handlePairCodeGeneration2(FunctionGenerator main,
				int offsetSize, EnumType Type) {				
				main.add(new Operation(Operand.MOV,CodeUtil.InputReg, new Immediate(8)));
				main.add(new Operation(Operand.BL,new Label("malloc")));
				main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg,Register.r2)));
				main.add(new Operation(Operand.STR,Register.r2,new Address(CodeUtil.InputReg)));
				main.add(new Operation(Operand.STR,CodeUtil.OutputReg,new Address(CodeUtil.InputReg,4)));
				main.add(new Operation(Operand.STR,CodeUtil.InputReg,new Address(Register.sp)));				
		}

		private void handlePairsInside(EnumType type, int index, ParseTree ctx,
		 FunctionGenerator main) {
	      if(type.equals(EnumType.NULL)){
		   main.add(new Operation(Operand.MOV,CodeUtil.InputReg,new Immediate(0)));
		   main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		   main.add(new Operation(Operand.MOV,CodeUtil.InputReg,new Immediate(4)));
		   main.add(new Operation(Operand.BL,new Label("malloc")));
		   main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg)));
		   main.add(new Operation(Operand.STR,CodeUtil.OutputReg,new Address(CodeUtil.InputReg)));
		   main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		   currentOffset += 4;
		  }else if(type.equals(EnumType.PAIR)){
		   main.add(new Operation(Operand.LDR,CodeUtil.InputReg,new Address(new Register(CodeUtil.sp,getPairOffset(ctx.getChild(index).getText()) - pairOffset + currentOffset))));
		   main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));
		   main.add(new Operation(Operand.MOV,CodeUtil.InputReg,new Immediate(4)));
		   main.add(new Operation(Operand.BL,new Label("malloc")));
		   main.add(new Operation(Operand.POP,new RegList(CodeUtil.OutputReg)));
		   main.add(new Operation(Operand.STR,CodeUtil.OutputReg,new Address(CodeUtil.InputReg)));
		   main.add(new Operation(Operand.PUSH,new RegList(CodeUtil.InputReg)));	
		   currentOffset += 4;
		 }		
		}

		private int getPairOffset(String text) {
		 if(text.equals("null")){
		   return 0;
		 }else{
		   return walker.getVarMapping(text);
		 }
		}

		private Variable getAssigner(EnumType type,PreCodeGenWalker walker,ParseTree ctx,int index) {
		 switch(type){
		  case INT : 	return new Constant(ctx.getChild(index).getText());
		  case CHAR: 	return new Immediate(ctx.getChild(index).getText());
		  case STRING:	Variable result = new Constant("msg_"+(walker.getMsgCount()+strCount)); strCount++; return result;
		  case BOOL: 	return new Immediate(ctx.getChild(index).getText());
		  case PAIR:	return new Immediate(ctx.getChild(index).getText());
		  default : 	return null;
		 }
		}

		private int getOffsetSizeType(EnumType type) {
			if(type.equals(EnumType.STRING) || type.equals(EnumType.INT) || type.equals(EnumType.PAIR) || type.equals(EnumType.NULL)){
				return 4;
			}
			return 1;
		}

		public void addNewPair(String ident) {
		 walker.addMapping(ident,pairOffset);
		}

		public int getNumPairsSoFar() {
		 return numPairs;
		}
		
		public int getFinalPairOffset(){
			return numPairs*4;
		}
	
}
