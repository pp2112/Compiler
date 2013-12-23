package code;

import semantic_check.EnumType;
import semantic_check.Type;

public class Tuple {
	
	private String id;
	private int offset;
	private Type type;
	
	public Tuple(String id, int offset, Type type){
		this.id = id;
		this.offset = offset;
		this.type = type;
	}
	
	public String getId(){
		return id;
	}
	
	public int getOffset(){
		return offset;
	}
	
	public void setOffset(int offset){
		this.offset = offset;
	}
	
	public Type getType(){
		return type;
	}
	
	public EnumType getEnumType(){
		return type.getEnumType();
	}

	public void updateOffset(int offset) {
		this.offset+=offset;
	}
	
	public boolean equals(Tuple other){
		return id.equals(other.getId()) && offset==other.getOffset() && type.equals(other.getType());
	}
	
	public String toString(){
		return "Tuple [ID=" + id + ", offset=" + offset + ", Type=" + type + "]\n";
	}

	
	
	
	
	

}
