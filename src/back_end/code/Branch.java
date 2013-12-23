package code;

public class Branch implements Code {
	
	private int branch;
	public Branch(int branch){
		this.branch = branch;
	}
	
	@Override
	public String toString(){
		return "L"+branch+":";
	}
}
