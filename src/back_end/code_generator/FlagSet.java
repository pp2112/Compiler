package code_generator;
import java.util.HashMap;


public class FlagSet {


	private HashMap<Flags, Boolean> flagSet = new HashMap<Flags, Boolean>();
	
	//Initiate all flag values to false
	public FlagSet(){
		for(Flags flag : Flags.values()){
			flagSet.put(flag, false);
		}
	}
	
	//Set the flag to true
	public void setFlag(Flags flag){
		flagSet.put(flag, true);
	}
	
	//Set the flag to false
	public void clearFlag(Flags flag){
		flagSet.put(flag, false);
	}
	
	//Return state of the flag
	public boolean getFlag(Flags flag){
		return flagSet.get(flag);
	}
}
