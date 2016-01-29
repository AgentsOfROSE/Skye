package umlParser;

import java.io.IOException;

public interface PatternDetector {
	public String detect(String[] classes) throws IOException;
}
