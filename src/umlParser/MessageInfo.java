package umlParser;
import java.util.ArrayList;

public class MessageInfo {
	
	int depth;
	String caller;
	String callee;
	String answer;
	String message;
	ArrayList<String> parameters;

	public MessageInfo(int depth, String caller, String callee, String answer, String message, ArrayList<String> parameters){
		this.depth = depth;
		this.caller = caller;
		this.callee = callee;
		this.answer = answer;
		this.message = message;
		this.parameters = parameters;
	}
	
	public int getDepth(){
		return this.depth;
	}
	
	public void setDepth(int depth){
		this.depth = depth;
	}
	
	public String getCaller(){
		return this.caller;
	}
	
	public void setCaller(String caller){
		this.caller = caller;
	}
	
	public String getCallee(){
		return this.callee;
	}
	
	public void setCallee(String callee){
		this.callee = callee;
	}
	
	public String getAnswer(){
		return this.answer;
	}
	
	public void setAnswer(String answer){
		this.answer = answer;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public ArrayList<String> getParameters() {
		return parameters;
	}
	
	public String getParametersString() {
		if(this.parameters.size() == 0){
			return "()";
		}
		String returnString = "(";
		for(String param : this.parameters){
			returnString = returnString + param + ", ";
		}
		return returnString.substring(0, returnString.length()-2) + ")";
	}
}
