package generator;

import code.Code;

public class MessageGenerator extends Generator<Code> {

	private String msg;	
	private String label;

	public MessageGenerator(int numMsg, String msg) {
		this.label = "msg_" + numMsg;
		this.msg = msg;
	}
	
	//This function returns the size of a string excluding the escape characters
	public int getLength(){
		int count = 0;
		
		for(int i = 0 ; i<msg.length(); i++){
			switch(msg.charAt(i)){
				case '\\':
					count++;
					 i++;
					 break;
				case '\"':
					count++;
					break;
				default:
					break;
			}
		}
		
		return msg.length() - count;
	}
	
	public String toString() {
		String result = label + ":\n";
		result += "\t.word " + getLength() + "\n";
		result += "\t.ascii\t" + msg;
		return result;
	}
}
