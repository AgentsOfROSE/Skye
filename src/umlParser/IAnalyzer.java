package umlParser;

import java.util.ArrayList;

public interface IAnalyzer {

	public boolean execute(String phase);
	public boolean executeAll(ArrayList<String> phases);
	
}
