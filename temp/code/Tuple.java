package code;

import semantic_check.Type;

public class Tuple {
	
	private String id;
	private int offset;
	private String value;
	private Type type;
	
	public Tuple(String id, int offset, String value, Type type) {
		this.id = id;
		this.offset = offset;
		this.value = value;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean equals(Tuple other){
		return id.equals(other.getId()) && offset==other.getOffset() && value.equals(other.getValue());
	}
	
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		return "Tuple [ID=" + id + ", offset=" + offset + ", Value=" + value + "]";
	}

	public void updateOffset(int offset) {
		this.offset += offset;
	}

}
