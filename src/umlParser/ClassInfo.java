package umlParser;
import java.util.ArrayList;

public class ClassInfo {
	
	private String name;
	private ArrayList<FieldInfo> fields = new ArrayList<FieldInfo>();
	private ArrayList<MethodInfo> methods = new ArrayList<MethodInfo>();
	private String extendedClass;
	private ArrayList<String> implementedClasses = new ArrayList<String>(); 
	private ArrayList<String> usedClasses = new ArrayList<String>();
	private ArrayList<String> associatedClasses = new ArrayList<String>();
	private ArrayList<String> patterns = new ArrayList<String>();
	private String frameColor = "black";
	
	public ArrayList<String> getAssociatedClasses() {
		return associatedClasses;
	}

	public void setAssociatedClasses(ArrayList<String> associatedClasses) {
		this.associatedClasses = associatedClasses;
	}

	public ClassInfo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<FieldInfo> getFields() {
		return fields;
	}

	public void setFields(ArrayList<FieldInfo> fields) {
		this.fields = fields;
	}

	public ArrayList<MethodInfo> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<MethodInfo> methods) {
		this.methods = methods;
	}

	public String getExtendedClass() {
		return extendedClass;
	}

	public void setExtendedClass(String extendedClass) {
		this.extendedClass = extendedClass;
	}

	public ArrayList<String> getImplementedClasses() {
		return implementedClasses;
	}

	public void setImplementedClasses(ArrayList<String> implementedClasses) {
		this.implementedClasses = implementedClasses;
	}

	public ArrayList<String> getUsedClasses() {
		return usedClasses;
	}

	public void setUsedClasses(ArrayList<String> usedClasses) {
		this.usedClasses = usedClasses;
	}
	
	public ArrayList<String> getPatterns(){
		return this.patterns;
	}
	
	public String getFrameColor(){
		return this.frameColor;
	}
	
	public void setFrameColor(String color){
		this.frameColor = color;
	}

}
