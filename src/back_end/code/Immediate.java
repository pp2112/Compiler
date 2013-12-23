package code;

public class Immediate implements Variable{

	String value;
	
	public Immediate(int value){
		this.value = new Integer(value).toString();
	}
	
	public Immediate(String value){
		this.value = value;
	}
	
	public String toString(){
		return "#" + value;
	}
	
}
