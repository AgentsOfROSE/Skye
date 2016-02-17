package umlParser;

import java.io.IOException;

public class UMLCompositeParser extends AbstractUMLParser {
	
	private String detected;

	public UMLCompositeParser(UMLParsable parser) {
		super(parser);
	}
	
	public UMLCompositeParser(String detected) {
		super(null);
		this.detected = detected;
	}
	
	public UMLCompositeParser(UMLParsable parser, String detected){
		super(parser);
		this.detected = detected;
	}

	@Override
	public void parse(String[] args) throws IOException {
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		if(detected == null){
			detected = (new CompositeDetector()).detect(args).split("~")[0];
		}
		String[] compositeClasses = detected.substring(detected.indexOf("-") + 1).split(";");
		if(compositeClasses[0].length() > 0){
			for(String classInfo : compositeClasses){
				String[] expandedInfo = classInfo.split(":");
				ClassInfo data = this.getClasses().get(this.getClassListFull().indexOf(expandedInfo[1]));
				data.getAnnotations().add(expandedInfo[0]);
				data.getPatterns().add("Composite");
				data.setClassFillColor("yellow");
			}
		}
		this.parser.parse(args);
	}

}
