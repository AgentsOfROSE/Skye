package umlParser;
import java.util.ArrayList;
import java.util.HashMap;

public class SequenceDiagramInfo {
	
	ArrayList<String> objects = new ArrayList<String>();
	ArrayList<MessageInfo> message = new ArrayList<MessageInfo>();
	String packageName;

	public ArrayList<String> getObjects(){
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
