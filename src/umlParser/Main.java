package umlParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

	private static HashMap<String, Parsable> parserMap = new HashMap<String, Parsable>();
	
	static {
		parserMap.put("uml", new UMLEndParser(new UMLDecoratorParser(new UMLSingletonParser(new UMLUsesParser(new UMLAssociationParser(new UMLImplementsParser(new UMLExtendsParser(new UMLParser()))))))));
		parserMap.put("sequence", new SequenceDiagramTextParser());
	}

	public static void main(String[] args) throws IOException {
		Parsable parser = parserMap.get(args[0]);
		parser.parse(Arrays.copyOfRange(args, 1, args.length));

	}

}
