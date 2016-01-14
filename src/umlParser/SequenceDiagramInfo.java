package umlParser;
import java.util.ArrayList;

public class SequenceDiagramInfo {
	
	HashMap<String, String> objects = new HashMap<String, String>();
	ArrayList<MessageInfo> message = new ArrayList<MessageInfo>();

	public HashMap<String, String> getObjects(){
		return this.objects;
	}

	public ArrayList<MessageInfo> getMessages(){
		return this.messages;
	}
}
