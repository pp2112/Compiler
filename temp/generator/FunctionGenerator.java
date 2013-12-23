package generator;
import java.util.Iterator;

import code.*;
import semantic_check.EnumType;
import semantic_check.Type;


public class FunctionGenerator extends Generator<Code> {
	
	protected Directive directive;
	protected TupleList labelTable;
	protected FunctionGenerator previous;
	protected int maxByte;
	protected int currentByte;
	
	public FunctionGenerator(String label){
		super(label);
		this.labelTable = new TupleList();
		this.directive = null;
		this.previous = this;
		this.maxByte = 0;
		this.currentByte = 0;
	}
	
	public FunctionGenerator(String label, FunctionGenerator previous){
		super(label);
		this.labelTable = new TupleList();
		this.directive = null;
		this.previous = previous;
	}
	
	public void setMaxByte(int maxByte){
		this.maxByte = maxByte;
	}
	
	public int getMaxByte() {
		return maxByte;
	}
	
	public void setCurrentByte(int currentByte){
		this.currentByte = currentByte;
	}
	
	public int getCurrentByte(){
		return currentByte;
	}
	
	public void updateCurrentByte(int offset){
		if(currentByte != 0){
		currentByte -= offset;
		}
	}
	
	/* Assuming the programs are valid, so the ident we are looking for must
	 * be in the label table.
	 */
	
	/* This function gets the offset of the ident, if the ident is not in this label table,
	 * it will look for it in the previous label table
	 */
	public int getOffset(String id)
	{
		int result = (labelTable.indexOf(id) != -1) ? labelTable.getOffset(id) : maxByte + previous.getOffset(id);
		return result;
	}
	
	/* This function gets the offset of the type of the ident, if the ident is not in this label table,
	 * it will look for it in the previous label table
	 */
	public int getIdentOffset(String id){
		int result = (labelTable.indexOf(id) != -1) ? labelTable.getIdentOffset(id, maxByte) : previous.getIdentOffset(id);
		return result;
	}
	
	/* This function gets the type of the ident, if the ident is not in this label table,
	 * it will look for it in the previous label table
	 */
	public Type getType(String id){
		Type result = (labelTable.indexOf(id) != -1) ? labelTable.getType(id) : previous.getType(id);
		return result;
	}
	
	/* This function gets the EnumType of the ident, if the ident is not in this label table,
	 * it will look for it in the previous label table
	 */
	public EnumType getEnumType(String id){
		EnumType result = (labelTable.indexOf(id) != -1) ? labelTable.getEnumType(id) : previous.getEnumType(id);
	  return result;
	}
	
	/* This function gets the value of the ident, if the ident is not in this label table,
	 * it will look for it in the previous label table
	 */
	public String getValue(String id) {
		String result = (labelTable.indexOf(id) != -1) ? labelTable.getValue(id) : previous.getValue(id);
		return result;
	}
	
	//Returns the previous FunctionGenerator, if it is main, it will return itself
	public FunctionGenerator getPrevious(){
		return previous;
	}
	
	public void addDirective(String directive){
		this.directive = new Directive(directive);
	}
	
	//Adds a new label to the code, e.g. L0:
	public void addBranch(int label){
		list.add(new Branch(label));
	}
	
	//Adds a new tuple for the ident
	public void addLabel(String id, int offset, String value, Type type){
		labelTable.add(id, offset, value, type);
	}
	
	//Returns the labelTable
	public TupleList getLabelTable(){
		return labelTable;
	}
	
	//It adds the offset to all the offset of the ident in the label table
	public void updateLabelOffset(int offset){
		labelTable.updateOffset(offset);
	}
	
	/* It adds the code from this function generator to the previous function
	 * generator.
	 */
	
	public void addCode(){
		if(label != null)
			previous.addBranch(Integer.parseInt(label));
		for(Iterator<Code> iterator = list.iterator(); iterator.hasNext();){
			previous.add(iterator.next());
		}
	}
	
	public String toString(){
		if (directive != null)
			list.add(directive);
		String result = new String();
		
		if(label != null){
			result = label + ":\n";
		}
		
		for(Iterator<Code> iterator = list.iterator(); iterator.hasNext();){
			result += iterator.next() + "\n";
		}
		return result;
	}

	public void updateMaxByte(int offset) {
		maxByte += offset;
	}

}
