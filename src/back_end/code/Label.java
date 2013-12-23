package code;

public class Label implements Variable{

	String label;
	
	public Label(String label){
		this.label = label;
	}
	
	public String toString(){
		return label;
	}
	
}
