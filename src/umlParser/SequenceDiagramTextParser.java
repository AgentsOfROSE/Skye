package umlParser;

import java.io.IOException;
import java.util.ArrayList;

public class SequenceDiagramTextParser implements Parsable {

	@Override
	public void parse(String[] args) throws IOException {
		String className = args[0];
		String methodName = args[1];
		String[] params = args[2].substring(1, args[2].length()).split(" ");
		int maxDepth = 5;
		if (args.length == 4) {
			maxDepth = Integer.parseInt(args[3]);
		}
	}

}
