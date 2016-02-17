package umlParser;

import java.io.IOException;

public class UMLSingletonParser extends AbstractUMLParser{
	
	private String detected;

	public UMLSingletonParser(UMLParsable parser) {
		super(parser);
	}
	
	public UMLSingletonParser(String detected){
		super(null);
		this.detected = detected;
	}
	
	public UMLSingletonParser(UMLParsable parser, String detected){
		super(parser);
		this.detected = detected;
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		if(detected == null){
			detected = (new SingletonDetector()).detect(args).split("~")[0];
		}
		String[] singletonClasses = detected.substring(detected.indexOf("-") + 1).split(";");
		if(singletonClasses[0].length() > 0){
			for(String classInfo : singletonClasses){
				String[] expandedInfo = classInfo.split(":");
				ClassInfo data = this.getClasses().get(this.getClassListFull().indexOf(expandedInfo[1]));
				data.getAnnotations().add(expandedInfo[0]);
				data.getPatterns().add("Singleton");
				data.setFrameColor("blue");
			}
		}
		this.parser.parse(args);
	}
	

}
