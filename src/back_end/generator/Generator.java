package generator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Generator<T> {

	protected List<T> list;
	
	public Generator(){
		this.list = new ArrayList<T>();
	}
	
	public void add(T t){
		list.add(t);
	}
	
	public String toString(){
		String result = "";
		for(Iterator<T> iterator = list.iterator(); iterator.hasNext();){
			result += iterator.next() + "\n";
		}
		return result;
	}
	
}


