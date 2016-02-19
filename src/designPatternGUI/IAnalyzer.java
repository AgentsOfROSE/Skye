package designPatternGUI;

import java.util.ArrayList;
import java.util.HashMap;

public interface IAnalyzer {

	public boolean execute(String phase);
	public boolean executeAll(ArrayList<String> phases);
	HashMap<String, ArrayList<String>> exceptions = new HashMap<>();
	public default void addException(String phase, String className){
		if(exceptions.containsKey(phase)){
			if(exceptions.get(phase).contains(className)){
				return;
			} else {
				exceptions.get(phase).add(className);
			}
		} else {
			ArrayList<String> exceptionList = new ArrayList<String>();
			exceptionList.add(className);
			exceptions.put(phase, exceptionList);
		}
	}
	
	public default boolean removeException(String phase, String className){
		if((!exceptions.containsKey(phase)) || (!exceptions.get(phase).contains(className))){
			return false;
		}  else {
			exceptions.get(phase).remove(className);
			return true;
		}
	}
}
