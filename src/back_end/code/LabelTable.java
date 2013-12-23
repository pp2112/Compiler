package code;

import java.util.ArrayList;
import java.util.Iterator;

import semantic_check.EnumType;
import semantic_check.SingleType;
import semantic_check.PairType;
import semantic_check.ArrayType;
import semantic_check.Type;

public class LabelTable extends ArrayList<Tuple>{
	
	public LabelTable(){
		super();
	}
	
	public void add(String id, int offset, Type type){
		this.add(new Tuple(id, offset, type));
	}
	
	public Tuple getTuple(String id){
		return get(indexOf(id));
	}
	
	public int getOffset(String id){
		return getTuple(id).getOffset();
	}
	
	public Type getType(String id){
		return getTuple(id).getType();
	}
	
	public EnumType getEnumType(String id){
		return getTuple(id).getEnumType();
	}
	
	public int getIdentOffset(String id){

		Type type = getType(id);
		EnumType enumType = getEnumType(id);
		
		if (type instanceof SingleType){
			if (enumType.equals(EnumType.BOOL) || enumType.equals(EnumType.CHAR))
				return 1;
			else if (enumType.equals(EnumType.INT) || enumType.equals(EnumType.STRING))
				return 4;
		} else if (type instanceof PairType){
			return 4;
		} else if (type instanceof ArrayType){
			return 4;	
		}
		
		return 0;
	}
	
	public int getLastOffset() {
		return (!isEmpty()) ? this.get(this.size()-1).getOffset() : 0;
	}
	
	public int indexOf(String id){
		for(int i = 0; i < this.size() ; i++){
			if(this.get(i).getId().equals(id)){
				return i;
			}
		}
		return -1 ;
		
	}
	
	public void updateOffset(int offset){
		for (Iterator<Tuple> iterator = iterator(); iterator.hasNext();){
			iterator.next().updateOffset(offset);
		}
	}	
	
}
