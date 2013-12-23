package code_generator;
import generator.CodeGenerator;
import generator.FunctionGenerator;
import generator.MessageGenerator;
import generator.RegList;
import org.antlr.v4.runtime.tree.ParseTree;
import semantic_check.EnumType;
import semantic_check.SingleType;
import code.Address;
import code.Constant;
import code.Immediate;
import code.Label;
import code.Operand;
import code.Operation;
import code.Register;

public class CodeUtil {

	// These registers are used for register allocation when generating code;
	private RegList regs;
	private PrintUtil printHandler;
	private ArrayUtil arrayHandler;
	private PairUtil  pairHandler;
	
	public static final SingleType ERROR = new SingleType(EnumType.ERROR);
	public static final SingleType NULL = new SingleType(EnumType.NULL);
	public static final SingleType BOOL = new SingleType(EnumType.BOOL);
	public static final SingleType INT = new SingleType(EnumType.INT);
	public static final SingleType CHAR = new SingleType(EnumType.CHAR);
	public static final SingleType STRING = new SingleType(EnumType.STRING);
	public static final int sp = 13;
	public static final int lr = 14;
	public static final int pc = 15;
	public static final Register InputReg = Register.r0;
	public static final Register OutputReg = Register.r1;

	public CodeUtil(RegList regs,PreCodeGenWalker walker,FunctionGenerator main) {
		this.regs = regs;
		this.printHandler = new PrintUtil(regs);
		this.arrayHandler = new ArrayUtil(regs);
		this.pairHandler = new PairUtil(regs,walker,main);
	}
	
	public CodeUtil(RegList regs){
	 this.regs = regs;
	 this.printHandler = new PrintUtil(regs);
	 this.arrayHandler = new ArrayUtil(regs);
	}

	public int handleArrays(int numChildren, ParseTree ctx,Statement stat_type,FunctionGenerator main,CodeGenerator codeGen,PreCodeGenWalker walker,PairUtil pairUtil){
	 return arrayHandler.handleArrayStat(numChildren,ctx,stat_type, main,codeGen,walker,pairUtil);
	}
	
	public int handlePairs(EnumType firstType, EnumType secondType,FunctionGenerator main, CodeGenerator codeGen,PreCodeGenWalker walker, ParseTree ctx) {
	 return pairHandler.handlePairStat(firstType,secondType,main,codeGen,walker,ctx);
	}
	
	public void addNewPair(String ident) {
	 pairHandler.addNewPair(ident);
	}
	
	public int getArrayOffset(){
		return arrayHandler.getFinalOffset();
	}
	
	public int getPairOffset(){
		return pairHandler.getFinalPairOffset();
	}

	public FunctionGenerator handlePrint(int msgNumber, Statement stat_type) {
		return printHandler.handlePrintStat(msgNumber, stat_type);
	}

	public MessageGenerator handlePrintMessages(int msgNumber, Statement stat_type) {
		return printHandler.handleMessageStat(msgNumber, stat_type);
	}
	

	public FunctionGenerator p_read_int(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_read_int");
		generator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
		generator.add(new Operation(Operand.LDR, Register.r0, new Constant("msg_"
				+ msgNumber)));
		generator.add(new Operation(Operand.ADD, Register.r0, new Register(0, 4)));
		generator.add(new Operation(Operand.BL, new Label("scanf")));
		return generator;
	}

	public FunctionGenerator p_read_char(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_read_char");

		generator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
		generator.add(new Operation(Operand.LDR, Register.r0, new Constant("msg_"
				+ msgNumber)));
		generator.add(new Operation(Operand.ADD, Register.r0, new Register(0, 4)));
		generator.add(new Operation(Operand.BL, new Label("scanf")));

		return generator;
	}
	
	public FunctionGenerator p_throw_overflow_error(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_throw_overflow_error");
		generator.add(new Operation(Operand.LDR, Register.r0, new Constant("msg_" + msgNumber)));
		generator.add(new Operation(Operand.BL, new Label("p_throw_runtime_error")));
		return generator;
	}
	
	public FunctionGenerator p_throw_runtime_error() {
		FunctionGenerator generator = new FunctionGenerator("p_throw_runtime_error");
		generator.add(new Operation(Operand.BL, new Label("p_print_string")));
		generator.add(new Operation(Operand.MOV, Register.r0, new Immediate(-1)));
		generator.add(new Operation(Operand.BL, new Label("exit")));
		return generator;
	}
	
	public FunctionGenerator p_check_array_bounds(int msgNumber){
	  FunctionGenerator generator = new FunctionGenerator("p_check_array_bounds");
	  generator.add(new Operation(Operand.PUSH,new RegList(Register.lr)));
	  generator.add(new Operation(Operand.CMP,CodeUtil.InputReg,new Immediate(0)));
	  generator.add(new Operation(Operand.LDRLT,CodeUtil.InputReg,new Constant("msg_" + msgNumber)));
	  generator.add(new Operation(Operand.BLLT,new Label("p_throw_runtime_error")));
	  generator.add(new Operation(Operand.LDR,CodeUtil.OutputReg,new Address(Register.r4)));
	  generator.add(new Operation(Operand.CMP,CodeUtil.InputReg,CodeUtil.OutputReg));
	  generator.add(new Operation(Operand.LDRCS,CodeUtil.InputReg, new Constant("msg_" + (msgNumber+1))));
	  generator.add(new Operation(Operand.BLCS,new Label("p_throw_runtime_error")));	
	  generator.add(new Operation(Operand.POP,new RegList(Register.pc)));
	 return generator;  
	}

	public FunctionGenerator p_free_pair(){
		FunctionGenerator generator = new FunctionGenerator("p_free_pair");
		generator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
		generator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));
		generator.add(new Operation(Operand.POPEQ, new RegList(Register.pc)));
		generator.add(new Operation(Operand.PUSH, new RegList(Register.r0)));
		generator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.r0)));
		generator.add(new Operation(Operand.BL, new Label("free")));
		generator.add(new Operation(Operand.LDR, Register.r0, new Address(Register.sp)));
		generator.add(new Operation(Operand.LDR, Register.r0, new Address(new Register(0, 4))));
		generator.add(new Operation(Operand.BL, new Label("free")));
		generator.add(new Operation(Operand.POP, new RegList(Register.r0)));
		generator.add(new Operation(Operand.BL, new Label("free")));
		generator.add(new Operation(Operand.POP, new RegList(Register.pc)));
		return generator;
	}
	
	public FunctionGenerator p_check_null_pointer(int msgNumber){
		FunctionGenerator generator = new FunctionGenerator("p_check_null_pointer");
		generator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
		generator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));
		generator.add(new Operation(Operand.LDREQ, Register.r0, new Constant("msg_" + msgNumber)));
		generator.add(new Operation(Operand.BLEQ, new Label("p_throw_runtime_error")));
		generator.add(new Operation(Operand.POP, new RegList(Register.pc)));
		return generator;
	}
	public PairUtil getPairUtil() {
	 return pairHandler;
	}


}
