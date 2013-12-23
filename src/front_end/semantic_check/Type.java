package semantic_check;


public abstract class Type implements BigType{
	
	public abstract EnumType getEnumType();

	public boolean isError(){
		return getEnumType().equals(EnumType.ERROR);
	}
}