package semantic_check;


public class SingleType extends Type{
	
	private BigType type;
	
	public SingleType(BigType type){
		this.type = type;
	}
	
	public EnumType getEnumType(){
		if (type instanceof EnumType)
			return (EnumType) type;
		return type.getEnumType();
	}
	
	@Override
	public BigType getType() {
		return new SingleType(type);
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof SingleType){
			SingleType otherSingle = (SingleType)other;
			if (isError())
				return false;			
			return (type.equals(otherSingle.getEnumType()));
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return type.getEnumType().getValue();
	}
	
	public String toString(){
		return type.toString().toUpperCase();
	}
	
}
