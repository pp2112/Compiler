package generator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Generator<T> {

	protected List<T> list;
	protected String label;
	
	public Generator(String label){
		this.list = new ArrayList<T>();
		this.label = label;
	}
	
	public void add(T t){
		list.add(t);
	}
	
	public String getLabel(){
		return label;
	}
	
	public String toString(){
		String result = label + ":\n";
		for(Iterator<T> iterator = list.iterator(); iterator.hasNext();){
			result += iterator.next() + "\n";
		}
		return result;
	}
	
}


