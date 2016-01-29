package umlParser;


import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class SingletonDetectionTest {

	@Test
	public void RuntimeTest() throws IOException{
		String className = "java.lang.Runtime";
		String[] classes = new String[]{className};
		String returnValue = (new SingletonDetector()).detect(classes);
		assertTrue(returnValue.equals("Singleton; :"+className+"~"));
	}
	
	@Test
	public void CalendarTest() throws IOException{
		String className = "java.util.Calendar";
		String[] classes = new String[]{className};
		String returnValue = (new SingletonDetector()).detect(classes);
		assertTrue(returnValue.equals(""));
	}
	
	@Test
	public void FilterInputStreamTest() throws IOException{
		String className = "java.io.FilterInputStream";
		String[] classes = new String[]{className};
		String returnValue = (new SingletonDetector()).detect(classes);
		assertTrue(returnValue.equals(""));
	}
	
	@Test
	public void DesktopTest() throws IOException{
		String className = "java.awt.Desktop";
		String[] classes = new String[]{className};
		String returnValue = (new SingletonDetector()).detect(classes);
		assertTrue(returnValue.equals(""));
	}
	
	@Test
	public void EagerTest() throws IOException{
		String className = "umlParser.RoseHulmanSGAPresident";
		String[] classes = new String[]{className};
		String returnValue = (new SingletonDetector()).detect(classes);
		assertTrue(returnValue.equals("Singleton; :"+className+"~"));
	}
	
	@Test
	public void LazyTest() throws IOException{
		String className = "umlParser.RoseHulmanSABPresident";
		String[] classes = new String[]{className};
		String returnValue = (new SingletonDetector()).detect(classes);
		assertTrue(returnValue.equals("Singleton; :"+className+"~"));
	}
}
