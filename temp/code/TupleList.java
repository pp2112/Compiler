package code;
import java.util.ArrayList;
import java.util.Iterator;
import code_generator.*;
import semantic_check.*;

public class TupleList extends ArrayList<Tuple>{
	
	TupleList previous;
	
	public TupleList(){
		super();
		previous = null;
	}
	
	public TupleList(TupleList other){
		super();
		previous = other;
	}
	
	public void add(String id, int offset, String value, Type type){
		this.add(new Tuple(id, offset, value, type));
	}
	
	public void set(String id, String value){
		int offset = get(indexOf(id)).getOffset();
		Type type = get(indexOf(id)).getType();
		this.set(indexOf(id), new Tuple(id, offset, value, type));
	}
	
	public int getOffset(String id){
		return get(indexOf(id)).getOffset();
	}
	
	public EnumType getEnumType(String id){
		return get(indexOf(id)).getType().getEnumType();
	}
	
	// return the byte reserved for the variable
	public int getIdentOffset(String id, int maxByte){
		
		Type type = getType(id);
		
		if (type instanceof SingleType){
			if (type.equals(CodeUtil.CHAR) || type.equals(CodeUtil.BOOL))
				return 1;
			else if (type.equals(CodeUtil.INT) || type.equals(CodeUtil.STRING))
				return 4;
		} else if (type instanceof PairType){
			return 4;
		} else if (type instanceof ArrayType){
			return 4;
		}
		return 0;
	}
	
	public Type getType(String id){
		return get(indexOf(id)).getType();
	}

	public boolean contains(String id){
		for(Tuple tuple : this){
			if(tuple.getId().equals(id))
				return true;
		}
		return false;		
	}
	
	//returns the index of the the tuple containing the key
	//returns -1 if the key is not in the list
	//The index returned could be use with get() to retrieve the tuple
	public int indexOf(String id){
		for(int i = 0; i < this.size() ; i++){
			if(this.get(i).getId().equals(id)){
				return i;
			}
		}
		return -1 ;
		
	}
	
	//return the last occurrence of the tuple containing the key
	//returns -1 if the key is not in the list
	@Override
	public int lastIndexOf(Object obj){
		for(int i = size()-1 ; i >=0 ; i--){
			if(this.get(i).getId().equals(obj)){
				return i;
			}
		}
		return -1 ;
	}
	
	//remove the first occurrence of the tuple containing the key, if it's in the list
	public boolean remove(String id){
		for(int i = size()-1 ; i >=0 ; i--){
			if(this.get(i).getId().equals(id)){
				this.remove(i);
				return true;
			}
		}
		return false ;
	}

	public void updateOffset(int offset) {
		for (Iterator<Tuple> iterator = iterator(); iterator.hasNext();){
			iterator.next().updateOffset(offset);
		}
		
	}

	public int getLastOffset() {
		return this.get(this.size()-1).getOffset();
	}

	public String getValue(String id) {
		return get(indexOf(id)).getValue();
	}
	
}