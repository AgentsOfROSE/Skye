package umlParser;

import java.io.IOException;

public class UMLEndParser extends AbstractUMLParser{
	
	public UMLEndParser(UMLParsable parser) {
		super(parser);
	}
	
	public void parse(String[] args) throws IOException {
		for (String className : args) {
			this.getClassListFull().add(className);
			this.getClassListAbbreviated().add(className.substring(className.lastIndexOf(".") + 1));
			ClassInfo info = new ClassInfo();
			this.getClasses().add(info);
		}
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		this.parser.parse(args);
		System.out.println("\n}");
	}
	

}
