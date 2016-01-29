package umlParser;
import java.util.ArrayList;

public class SequenceDiagramInfo {
	
	ArrayList<String> objects = new ArrayList<String>();
	ArrayList<MessageInfo> message = new ArrayList<MessageInfo>();
	String packageName;
	int maxDepth = 5;

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

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
