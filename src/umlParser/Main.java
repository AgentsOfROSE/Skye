package umlParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	private static HashMap<String, Parsable> parserMap = new HashMap<String, Parsable>();
	
	static {
		parserMap.put("uml", new UMLTextParser());
		parserMap.put("sequence", new SequenceDiagramTextParser());
	}

	public static void main(String[] args) throws IOException {
		String param;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter 'uml' or 'sequence' to choose which parser to use: ");
		param = in.nextLine();
		Parsable parser = parserMap.get(param);
		parser.parse(args);

	}

}
