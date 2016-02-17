package umlParser;

import java.util.ArrayList;

public class Analyzer implements IAnalyzer {

	public Analyzer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(String phase) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return true;
	}

	@Override
	public boolean executeAll(ArrayList<String> phases) {
		// TODO Auto-generated method stub
		return false;
	}

}
