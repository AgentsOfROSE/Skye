package umlParser;

import java.util.ArrayList;

public interface UMLParsable extends Parsable{
	
	public ArrayList<ClassInfo> getClasses();
	public ArrayList<String> getClassListAbbreviated();
	public ArrayList<String> getClassListFull();
	public void setClasses(ArrayList<ClassInfo> classes);
	public void setClassListAbbreviated(ArrayList<String> classListAbbreviated);
	public void setClassListFull(ArrayList<String> classListFull);

}
