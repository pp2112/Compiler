package generator;

import java.io.PrintStream;
import java.util.ArrayList;

public class CodeGenerator {

	private ArrayList<FunctionGenerator> functions;
	private ArrayList<MessageGenerator> messages;
	private PrintStream outputStream;
	
	public CodeGenerator(PrintStream outputStream){
		this.functions = new ArrayList<FunctionGenerator>();
		this.messages = new ArrayList<MessageGenerator>();
		this.outputStream = outputStream;
	}
	
	public void addMessage(MessageGenerator other){
		messages.add(other);
	}
	
	public void addFunction(FunctionGenerator other){
		functions.add(other);
	}
	
	public void print(){
		if (!messages.isEmpty()){
			outputStream.println(".data\n");
			for (MessageGenerator generator : messages)
				outputStream.println(generator);
			outputStream.println();
		}
		outputStream.println(".text\n\n.global main");
		for (FunctionGenerator generator : functions)
			outputStream.println(generator);
	}
	
	
}
