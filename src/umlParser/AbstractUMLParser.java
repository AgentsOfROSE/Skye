package umlParser;

import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractUMLParser implements UMLParsable{
	protected UMLParsable parser;
	protected ArrayList<ClassInfo> classes;
	protected ArrayList<String> classListAbbreviated;
	protected ArrayList<String> classListFull;
	
	public AbstractUMLParser(UMLParsable parser){
		this.parser = parser;
		this.classes = new ArrayList<ClassInfo>();
	    this.classListAbbreviated = new ArrayList<String>();
	    this.classListFull = new ArrayList<String>();
	}
	
	abstract public void parse(String[] args) throws IOException;
	public ArrayList<ClassInfo> getClasses() {
		return classes;
	}
	
	public ArrayList<String> getClassListAbbreviated() {
		return classListAbbreviated;
	}
	
	public ArrayList<String> getClassListFull() {
		return classListFull;
	}
	
	public void setClasses(ArrayList<ClassInfo> classes) {
		this.classes = classes;
	}
	
	public void setClassListAbbreviated(ArrayList<String> classListAbbreviated) {
		this.classListAbbreviated = classListAbbreviated;
	}
	
	public void setClassListFull(ArrayList<String> classListFull) {
		this.classListFull = classListFull;
	}

}
