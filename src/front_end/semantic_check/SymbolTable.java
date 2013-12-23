package semantic_check;

import java.util.HashMap;
public class SymbolTable {

	SymbolTable prev;
	HashMap<String, Type> env;

	public SymbolTable() {
		prev = null;
		env = new HashMap<String, Type>();
	}

	public SymbolTable(SymbolTable symtab) {
		prev = symtab;
		env = new HashMap<String, Type>();
	}

	// It adds an entry to the hashmap
	void add(String id, Type t) {
		env.put(id, t);
	}
	
	boolean existsIdent(String id){
		return env.containsKey(id);
	}

	// It returns true if the id given exists in the hashmap, otherwise false
	boolean isInScope(String id) {
		return env.containsKey(id) || (prev != null && prev.isInScope(id));
	}

	// It returns the type of the given id
	Type getType(String id) {
		Type t = env.get(id);
		if (t == null) {
		 return getTypePrev(id, prev);
		}
		return t;		
	}

	Type getTypePrev(String id, SymbolTable current) {
		if (current == null) {
			return new SingleType(EnumType.ERROR);
		}
		if (!current.getType(id).equals(new SingleType(EnumType.ERROR))) {
			return current.getType(id);
		}
		return getTypePrev(id, current.getPrevTable());
	}

	// It returns the previous SymbolTable
	SymbolTable getPrevTable() {
		return prev;
	}
	
	@Override
	public String toString(){
		if (prev != null){
			return env.toString() + prev.toString();
		}
		return env.toString();
	}
}
