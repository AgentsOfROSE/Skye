package umlParser;

import java.util.ArrayList;

public class GUIConfigInfo {
	
	private String inputFolder;
	private ArrayList<String> inputClasses = new ArrayList<String>();
	private String outputFolder;
	private String dotPath;
	private ArrayList<String> phases = new ArrayList<String>();

	public GUIConfigInfo() {
	}
	
	public String getInputFolder() {
		return inputFolder;
	}

	public void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
	}

	public ArrayList<String> getInputClasses() {
		return inputClasses;
	}

	public void setInputClasses(ArrayList<String> inputClasses) {
		this.inputClasses = inputClasses;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public String getDotPath() {
		return dotPath;
	}

	public void setDotPath(String dotPath) {
		this.dotPath = dotPath;
	}

	public ArrayList<String> getPhases() {
		return phases;
	}

	public void setPhases(ArrayList<String> phases) {
		this.phases = phases;
	}

}
