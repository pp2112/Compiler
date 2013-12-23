package code_generator;
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

public class PrintUtil {

	private RegList regs;

	public PrintUtil(RegList regs) {
		this.regs = regs;
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

	private FunctionGenerator p_print_ln(int msgNumber) {
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

	public FunctionGenerator handlePrintStat(int msgNumber, Statement stat_type) {
		switch (stat_type) {
		case PRINTLN_STR:
			return p_print_string(msgNumber);
		case PRINTLN_BOOL:
			return p_print_bool(msgNumber);
		case PRINTLN_INT:
			return p_print_int(msgNumber);
		case PRINT_LN:
			return p_print_ln(msgNumber);
		case PRINTLN_REFERENCE:
			return p_print_reference(msgNumber);
		default:
			break;
		}
		System.out.println("Invalid Print type !");
		return null;
	}

	public MessageGenerator handleMessageStat(int msgNumber, Statement stat_type) {
		switch (stat_type) {
		case PRINTLN_MSG:
			return println(msgNumber);
		case PRINT_STR_MSG:
			return print(msgNumber);
		default:
			break;
		}
		System.out.println("Invalid Message type !");
		return null;
	}

}
