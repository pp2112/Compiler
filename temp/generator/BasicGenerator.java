package generator;
import java.util.Iterator;

import code.Code;


public class BasicGenerator extends Generator<Generator<Code>> {
	
	public BasicGenerator(String label){
		super(label);
	}

	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public String toString(){
		String result = label + "\n";
		for(Iterator<Generator<Code>> iterator = list.iterator(); iterator.hasNext();){
			result += iterator.next() + "\n";
		}
		return result;
	}
}
