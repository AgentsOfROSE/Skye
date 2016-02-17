package umlParser;

import java.io.IOException;

public class UMLDecoratorParser extends AbstractUMLParser{

	private String detected;
	
	public UMLDecoratorParser(UMLParsable parser) {
		super(parser);
	}
	
	public UMLDecoratorParser(String detected){
		super(null);
		this.detected = detected;
	}
	
	public UMLDecoratorParser(UMLParsable parser, String detected){
		super(parser);
		this.detected = detected;
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		if(detected == null){
			detected = (new DecoratorDetector()).detect(args).split("~")[0];
		}
		String[] decoratorClasses = detected.substring(detected.indexOf("-") + 1).split(";");
		if(decoratorClasses[0].length() > 0){
			for(String classInfo : decoratorClasses){
				String[] expandedInfo = classInfo.split(":");
				ClassInfo data = this.getClasses().get(this.getClassListFull().indexOf(expandedInfo[1]));
				data.getAnnotations().add(expandedInfo[0]);
				data.getPatterns().add("Decorator");
				data.setClassFillColor("green");
			}
		}
		this.parser.parse(args);
	}

}
