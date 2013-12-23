package semantic_check;


public class ArrayType extends Type {
	
	private BigType type;
	
	public ArrayType(BigType type){
		this.type = type;
	}

	@Override
	public BigType getType() {
		return type;
	}
	
	public EnumType getEnumType(){
		if (type instanceof Type)
			return type.getEnumType();
		return (EnumType) type;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Type){
			if (!type.equals(EnumType.ERROR) && ((Type) other).getEnumType().equals(EnumType.EMPTYARRAY))
				return true;
			if (other instanceof ArrayType){
				ArrayType otherArray = (ArrayType)other;

				if (isError())
					return false;

				return (type.equals(otherArray.getEnumType()));
				
			}	
		}
		
		return false;
	}
	
	@Override
	public int hashCode(){
		return getEnumType().getValue();
	}
	
	public String toString(){
		return type.toString() + "[]";
	}

}
