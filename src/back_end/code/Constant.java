package code;

public class Constant implements Variable{

	String value;
	
	public Constant(int value){
		this.value = new Integer(value).toString();
	}
	
	public Constant(String value){
		this.value = value;
	}
	
	public String toString(){
		return "=" + value;
	}
	
}
