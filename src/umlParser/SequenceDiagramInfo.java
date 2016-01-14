package umlParser;
import java.util.ArrayList;
import java.util.HashMap;

public class SequenceDiagramInfo {
	
	HashMap<String, String> objects = new HashMap<String, String>();
	ArrayList<MessageInfo> message = new ArrayList<MessageInfo>();
	String packageName;

	public HashMap<String, String> getObjects(){
		return this.objects;
	}

	public ArrayList<MessageInfo> getMessages(){
		return this.message;
	}
	
	public void setPackageName(String name){
		this.packageName = name;
	}
	
	public String getPackageName(){
		return this.packageName;
	}
}
