package code_generator;

import semantic_check.EnumType;
import semantic_check.SingleType;
import generator.FunctionGenerator;
import generator.MessageGenerator;
import generator.RegList;
import code.Address;
import code.Constant;
import code.Immediate;
import code.Label;
import code.Operand;
import code.Operation;
import code.Register;

public class CodeUtil {
	
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
	
	public CodeUtil(){
		
	}
	
	public FunctionGenerator p_print_int(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_print_int");
		generator.add(new Operation(Operand.PUSH,new RegList(Register.lr)));
		generator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
		generator.add(new Operation(Operand.LDR, Register.r0, new Constant("msg_"	+ msgNumber)));
		generator.add(new Operation(Operand.ADD, Register.r0, new Register(0, 4)));
		generator.add(new Operation(Operand.BL, new Label("printf")));
		generator.add(new Operation(Operand.MOV, Register.r0, new Immediate(0)));
		generator.add(new Operation(Operand.BL, new Label("fflush")));
		generator.add(new Operation(Operand.POP,new RegList(Register.pc)));
		return generator;
	}

	public FunctionGenerator p_print_string(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_print_string");
		generator.add(new Operation(Operand.PUSH,new RegList(Register.lr)));
		generator.add(new Operation(Operand.LDR, Register.r1, new Address(
				new Register(0))));
		generator.add(new Operation(Operand.ADD, Register.r2, new Register(0, 4)));
		generator.add(new Operation(Operand.LDR, Register.r0, new Constant("msg_"
				+ msgNumber)));
		generator.add(new Operation(Operand.ADD, Register.r0, new Register(0, 4)));
		generator.add(new Operation(Operand.BL, new Label("printf")));
		generator.add(new Operation(Operand.MOV, Register.r0, new Immediate(0)));
		generator.add(new Operation(Operand.BL, new Label("fflush")));
		generator.add(new Operation(Operand.POP,new RegList(Register.pc)));
		return generator;
	}

	public FunctionGenerator p_print_bool(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_print_bool");
		generator.add(new Operation(Operand.PUSH,new RegList(Register.lr)));
		generator.add(new Operation(Operand.CMP, Register.r0, new Immediate(0)));
		generator.add(new Operation(Operand.LDRNE, Register.r0, new Constant("msg_" + msgNumber)));
		generator.add(new Operation(Operand.LDREQ, Register.r0, new Constant("msg_" + (msgNumber+1))));
		generator.add(new Operation(Operand.ADD, Register.r0, Register.r0, new Immediate(4)));
		generator.add(new Operation(Operand.BL, new Label("printf")));
		generator.add(new Operation(Operand.MOV, Register.r0, new Immediate(0)));
		generator.add(new Operation(Operand.BL, new Label("fflush")));
		generator.add(new Operation(Operand.POP,new RegList(Register.pc)));
		return generator;
	}

	public FunctionGenerator p_print_reference(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_print_reference");
		generator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
		generator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
		generator.add(new Operation(Operand.LDR, Register.r0, new Constant("msg_" + msgNumber)));
		generator.add(new Operation(Operand.ADD, Register.r0, Register.r0, new Immediate(4)));
		generator.add(new Operation(Operand.BL, new Label("printf")));
		generator.add(new Operation(Operand.MOV, Register.r0, new Immediate(0)));
		generator.add(new Operation(Operand.BL, new Label("fflush")));
		generator.add(new Operation(Operand.POP, new RegList(Register.pc)));
		return generator;
	}
	
	public MessageGenerator print(int numMsg) {
		MessageGenerator generator = new MessageGenerator(numMsg, "\"%.*s\\0\"");
		return generator;
	}

	public MessageGenerator println(int numMsg) {
		MessageGenerator generator = new MessageGenerator(numMsg, "\"\\0\"");
		return generator;
	}
	
	public MessageGenerator printInt(int numMsg) {
		MessageGenerator generator = new MessageGenerator(numMsg, "\"%d\\0\"");
		return generator;
	}

	public FunctionGenerator p_print_ln(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_print_ln");
		generator.add(new Operation(Operand.PUSH,new RegList(Register.lr)));
		generator.add(new Operation(Operand.LDR, Register.r0, new Constant("msg_"
				+ (msgNumber))));
		generator.add(new Operation(Operand.ADD, Register.r0, new Register(0, 4)));
		generator.add(new Operation(Operand.BL, new Label("puts")));
		generator.add(new Operation(Operand.MOV, Register.r0, new Immediate(0)));
		generator.add(new Operation(Operand.BL, new Label("fflush")));
		generator.add(new Operation(Operand.POP,new RegList(Register.pc)));
		return generator;
	}
	
	public FunctionGenerator p_read_int(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_read_int");
		generator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
		generator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
		generator.add(new Operation(Operand.LDR, Register.r0, new Constant("msg_"
				+ msgNumber)));
		generator.add(new Operation(Operand.ADD, Register.r0, new Register(0, 4)));
		generator.add(new Operation(Operand.BL, new Label("scanf")));
		generator.add(new Operation(Operand.POP, new RegList(Register.pc)));
		return generator;
	}

	public FunctionGenerator p_read_char(int msgNumber) {
		FunctionGenerator generator = new FunctionGenerator("p_read_char");
		generator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
		generator.add(new Operation(Operand.MOV, Register.r1, Register.r0));
		generator.add(new Operation(Operand.LDR, Register.r0, new Constant("msg_"
				+ msgNumber)));
		generator.add(new Operation(Operand.ADD, Register.r0, new Register(0, 4)));
		generator.add(new Operation(Operand.BL, new Label("scanf")));
		generator.add(new Operation(Operand.POP, new RegList(Register.pc)));
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
	
	public FunctionGenerator p_check_divide_by_zero(int msgNumber){
			FunctionGenerator generator = new FunctionGenerator("p_check_divide_by_zero");
			generator.add(new Operation(Operand.PUSH, new RegList(Register.lr)));
			generator.add(new Operation(Operand.CMP, Register.r1, new Immediate(0)));
			generator.add(new Operation(Operand.LDREQ, Register.r0, new Constant("msg_" + msgNumber)));
			generator.add(new Operation(Operand.BLEQ, new Label("p_throw_runtime_error")));
			generator.add(new Operation(Operand.POP, new RegList(Register.pc)));
			return generator;
	}

}
