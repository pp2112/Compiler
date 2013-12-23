package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class Operation implements Code{

	private Operand operand;
	private ArrayList<Variable> list;
	
	public Operation(Operand operand, Variable var1){
		this.operand = operand;
		this.list = new ArrayList<Variable>(Arrays.asList(var1));
	}
	
	public Operation(Operand operand, Variable var1, Variable var2){
		this.operand = operand;
		this.list = new ArrayList<Variable>(Arrays.asList(var1, var2));
	}
	
	public Operation(Operand operand, Variable var1, Variable var2, Variable var3){
		this.operand = operand;
		this.list = new ArrayList<Variable>(Arrays.asList(var1, var2, var3));
	}
	
	public Operation(Operand operand, Variable var1, Variable var2, Variable var3, Variable var4){
		this.operand = operand;
		this.list = new ArrayList<Variable>(Arrays.asList(var1, var2, var3, var4));
	}
	
	public String toString(){
		String result = "\t" + operand.toString() + " ";
		for (Iterator<Variable> iterator = list.iterator(); iterator.hasNext();){
			result += iterator.next();
			if (iterator.hasNext())
				result += ", ";
		}
		return result;
	}
	
}
