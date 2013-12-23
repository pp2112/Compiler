package semantic_check;


public class FunctionType extends Type {

	private BigType returnType;
	private Type[] params;
	private int paramCount=0;
	private SymbolTable usableParams;
	
	public FunctionType(Type returnType, int numParams, SymbolTable usableParams){
		this.returnType = returnType;
		this.params = new Type[numParams];
		this.usableParams = usableParams;
	}
	
	public Type getReturnType(){
		return (Type) returnType;
	}
	
	public EnumType getEnumType(){
		return returnType.getEnumType();
	}
	
	public Type[] getParams(){
		return params;
	}
	
	public int getNumParams(){
		return paramCount;
	}
	
	public void putParam(String id, Type t){
		//System.out.println(id);
		//System.out.println(t);
		//System.out.println(paramCount);
		usableParams.add(id, t);
		//System.out.println(usableParams);
		params[paramCount] = t;
		paramCount++;
	}
	
	@Override
	public String toString(){
		return returnType.toString();
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Type){
			Type other2 = (Type)other;
			if (isError())
				return false;
			return (returnType.getEnumType().equals(other2.getEnumType()));
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return returnType.getEnumType().getValue();
	}

	@Override
	public BigType getType() {
		return returnType;
	}
}
