package code;

public class Address implements Variable {
	
	Variable value;
	int offset;
	boolean exclamation;
	
	public Address(Variable value){
		this.value = value;
		this.exclamation = false;
	}
	
	public Address(Variable value, int i) {
		this.value = value;
		this.offset = i;
		this.exclamation = false;
	}
	
	public Address(Variable value, int i, boolean exclamation){
		 this.value = value;
		 this.offset = i;
		 this.exclamation = exclamation;
	}
	
	public String toString(){
		if(offset == 0){
			return "[" + value + "]";
		} else if (exclamation){
			return "[" + value + ", " + "#" + offset + "]!";
		} else {
			return "[" + value + ", " + "#" + offset + "]";
		}
		
	}

}
