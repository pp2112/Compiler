package generator;
import java.io.PrintStream;


public class CodeGenerator{
	
	/* CodeGenerator is basically a list which stores all the instructions
	 * of a program.
	 */
	private BasicGenerator data;
	private BasicGenerator text;
	private PrintStream outputStream;
	
	public CodeGenerator(String label, PrintStream outputStream){
		 this.data = new BasicGenerator(".data");
		 this.text = new BasicGenerator(".global main");
		 this.outputStream = outputStream;
	}
	
	public void addMessage(MessageGenerator other){
		 data.add(other);
	}
	
	public void addFunction(FunctionGenerator other){
		text.add(other);
	}
	
	public void print(){
		if (!data.isEmpty())
			outputStream.println(data);
		outputStream.println(".text\n");
		outputStream.println(text);
	}
	
}
