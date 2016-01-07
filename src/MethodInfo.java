import java.util.ArrayList;

public class MethodInfo {
	
	private String name;
	private String access = " ";
	private String returnType;
	private ArrayList<String> params = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public ArrayList<String> getParams() {
		return params;
	}

	public void setParams(ArrayList<String> params) {
		this.params = params;
	}

	public MethodInfo() {
		// TODO Auto-generated constructor stub
	}

}
