package umlParser;

import java.io.IOException;

public class UMLEndParser extends UMLParser{
	
	UMLParser parser;

	public UMLEndParser(UMLParser parser) {
		this.parser = parser;
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.parse(args);
		System.out.println("\n}");
	}
	

}
