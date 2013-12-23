package code;

public class Register implements Variable{
	
	public static Register r0  = new Register(0);
	public static Register r1  = new Register(1);
	public static Register r2  = new Register(2);
	public static Register r3  = new Register(3);
	public static Register r4  = new Register(4);
	public static Register r5  = new Register(5);
	public static Register r6  = new Register(6);
	public static Register r7  = new Register(7);
	public static Register r8  = new Register(8);
	public static Register r9  = new Register(9);
	public static Register r10 = new Register(10);
	public static Register r11 = new Register(11);
	public static Register r12 = new Register(12);
	public static Register sp = new Register(13);
	public static Register lr = new Register(14);
	public static Register pc = new Register(15);
	
	int value;
	int offset;
	boolean inUse;
	
	public Register(int value){
		this.value = value;
		this.offset = 0;
		this.inUse = false;
	}
	
	public Register(int value, int offset){
		this.value = value;
		this.offset = offset;
		this.inUse = false;
	}
	
	public boolean checkInUse(){
		return inUse;
	}
	
	public void setInUse(){
		this.inUse = true;
	}
	
	public String toString(){
		String offsetString = "";
		if (offset != 0){
			offsetString = ", #" + offset;
		}
		switch(value){
		case 13 : return "sp" + offsetString; 
		case 14 : return "lr" + offsetString;
		case 15 : return "pc" + offsetString;
		default : return "r"+ value + offsetString;
		}
	}

}
