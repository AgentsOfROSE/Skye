package umlParser;

import java.io.IOException;

public class UMLAdapterParser extends AbstractUMLParser{

	public UMLAdapterParser(UMLParsable parser) {
		super(parser);
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		String detected = (new AdapterDetector()).detect(args).split("~")[0];
		String[] adapterClasses = detected.substring(detected.indexOf("-") + 1).split(";");
		if(adapterClasses[0].length() > 0){
			for(String classInfo : adapterClasses){
				String[] expandedInfo = classInfo.split(":");
				ClassInfo data = this.getClasses().get(this.getClassListFull().indexOf(expandedInfo[1]));
				data.getAnnotations().add(expandedInfo[0]);
				data.getPatterns().add("Adapter");
				data.setClassFillColor("firebrick");
			}
		}
		
		this.parser.parse(args);
	}
	

}
