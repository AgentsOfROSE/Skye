import java.util.ArrayList;

public class ClassInfo {
	
	private String name;
	private ArrayList<FieldInfo> fields = new ArrayList<FieldInfo>();
	private ArrayList<MethodInfo> methods = new ArrayList<MethodInfo>();
	private String extendedClass;
	private ArrayList<String> implementedClasses = new ArrayList<String>(); 

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

}
