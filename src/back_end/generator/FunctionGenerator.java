package generator;

import java.util.Iterator;

import semantic_check.EnumType;
import semantic_check.Type;
import code.Branch;
import code.Code;
import code.Directive;
import code.LabelTable;

public class FunctionGenerator extends Generator<Code>{

	protected int maxByte;
	protected int currentByte;	
	protected String label;
	protected Directive directive;
	protected LabelTable labelTable;
	protected FunctionGenerator previous;
	
	public FunctionGenerator(){
		super();
		this.label = null;
		this.labelTable = new LabelTable();
		this.directive = null;
		this.previous = null;
		this.maxByte = 0;
		this.currentByte = 0;
	}
	
	public FunctionGenerator(String label){
		super();
		this.label = label;
		this.labelTable = new LabelTable();
		this.directive = null;
		this.previous = null;
		this.maxByte = 0;
		this.currentByte = 0;
	}
	
	public FunctionGenerator(FunctionGenerator previous){
		super();
		this.label = null;
		this.labelTable = new LabelTable();
		this.directive = null;
		this.previous = previous;
		this.maxByte = 0;
		this.currentByte = 0;
	}
	
	public FunctionGenerator(String label, FunctionGenerator previous){
		super();
		this.label = label;
		this.labelTable = new LabelTable();
		this.directive = null;
		this.previous = previous;
		this.maxByte = 0;
		this.currentByte = 0;
	}
	
	public String getLabel(){
		return label;
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
		currentByte += offset;
	}
	
	public int getOffset(String id)
	{
		int result = (labelTable.indexOf(id) != -1) ? labelTable.getOffset(id) : previous.getOffset(id);
		return result;
	}
	
	public int getIdentOffset(String id){
		int result = (labelTable.indexOf(id) != -1) ? labelTable.getIdentOffset(id) : previous.getIdentOffset(id);
		return result;
	}
	
	public int getLastOffset(){
		int result = labelTable.getLastOffset();
		return (result > maxByte) ? maxByte : result;
	}
	
	public Type getType(String id){
		Type result = (labelTable.indexOf(id) != -1) ? labelTable.getType(id) : previous.getType(id);
		return result;
	}
	
	public EnumType getEnumType(String id){
		EnumType result = (labelTable.indexOf(id) != -1) ? labelTable.getEnumType(id) : previous.getEnumType(id);
	  return result;
	}
	
	public FunctionGenerator getPrevious(){
		return previous;
	}
	
	public void setPrevious(FunctionGenerator previous){
		this.previous = previous;
	}
	
	public void updateAllOffset(int offset){
		labelTable.updateOffset(offset);
		if (previous != null) {
			previous.updateAllOffset(offset);
		}
	}
	
	public void updateCurrentOffset(int offset){
		labelTable.updateOffset(offset);
	}
	
	public void addDirective(String directive){
		this.directive = new Directive(directive);
	}
	
	public void addBranch(int label){
		list.add(new Branch(label));
	}
	
	public void addLabel(String id, int offset, Type type){
		labelTable.add(id, offset, type);
	}
	
	public LabelTable getLabelTable(){
		return labelTable;
	}
	
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

}
