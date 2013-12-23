package semantic_check;


public enum EnumType implements BigType {
	
	INT(0),
	BOOL(1),
	CHAR(2),
	STRING(3),
	PAIR(4),
	ERROR(5),
	EMPTYARRAY(6),
	NULL(7);
	
	private final int id;
	
	EnumType(int id){
		this.id = id;
	}
	
	protected int getValue(){
		return id;
	}

	@Override
	public EnumType getEnumType() {
		return this;
	}

	@Override
	public BigType getType() {
		return getEnumType();
	}

}
