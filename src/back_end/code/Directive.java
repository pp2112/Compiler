package code;

public class Directive implements Code {

	private String directive;
	public Directive(String directive){
		this.directive = directive;
	}
	
	@Override
	public String toString(){
		return "\t."+directive;
	}
}
