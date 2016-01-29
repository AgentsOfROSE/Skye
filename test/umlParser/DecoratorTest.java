package umlParser;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class DecoratorTest {

	@Test
	public void InputStreamReaderTest() throws IOException{
		String className1 = "java.io.InputStreamReader";
		String className2 = "java.io.FileReader";
		String className3 = "java.io.InputStream";
		String className4 = "java.lang.Readable";
		String className5 = "java.io.BufferedReader";
		String[] classes = new String[]{className1, className2, className3, className4, className5};
		String returnValue = (new DecoratorDetector()).detect(classes);
		assertTrue(returnValue.equals("Decorator-~"));
	}
	
	@Test
	public void OutputStreamReaderTest() throws IOException{
		String className1 = "java.io.OutputStreamWriter";
		String className2 = "java.io.FileReader";
		String className3 = "java.io.OutputStream";
		String className4 = "java.io.BufferedOutputStream";
		String className5 = "java.io.File";
		String[] classes = new String[]{className1, className2, className3, className4, className5};
		String returnValue = (new DecoratorDetector()).detect(classes);
		assertTrue(returnValue.equals("Decorator-~"));
	}
	
	@Test
	public void MouseAdapterTest() throws IOException{
		String className1 = "java.awt.event.MouseAdapter";
		String className2 = "java.awt.event.MouseMotionListener";
		String className3 = "java.awt.event.MouseEvent";
		String[] classes = new String[]{className1, className2, className3};
		String returnValue = (new DecoratorDetector()).detect(classes);
		assertTrue(returnValue.equals("Decorator-"+"~"));
	}
	
	@Test
	public void adapterSolutionTest() throws IOException{
		String className1 = "adapterSolution.App";
		String className2 = "adapterSolution.IteratorToEnumerationAdapter";
		String className3 = "adapterSolution.LinearTransformer";
		String className4 = "java.util.Enumeration";
		String className5 = "java.util.Iterator";
		String[] classes = new String[]{className1, className2, className3, className4, className5};
		String returnValue = (new DecoratorDetector()).detect(classes);
		assertTrue(returnValue.contains(""));
	}
	
	@Test
	public void decoratorSolutionTest() throws IOException{
		String className1 = "decoratorSolution.DecryptionInputStream";
		String className2 = "decoratorSolution.EncryptionOutputStream";
		String className3 = "decoratorSolution.IDecryption";
		String className4 = "decoratorSolution.IEncryption";
		String className5 = "decoratorSolution.SubstitutionCipher";
		String className6 = "decoratorSolution.TextEditorApp";
		String className7 = "java.io.InputStream";
		String className8 = "java.io.OutputStream";
		String className9 = "java.io.FilterInputStream";
		String className10 = "java.io.FilterOutputStream";
		String[] classes = new String[]{className1, className2, className3, className4, className5, className6, className7, className8, className9, className10};
		String returnValue = (new DecoratorDetector()).detect(classes);
		assertTrue(returnValue.contains("Decorator:java.io.FilterInputStream"));
		assertTrue(returnValue.contains("Decorator:java.io.FilterOutputStream"));
		assertTrue(returnValue.contains("Decorator:decoratorSolution.DecryptionInputStream"));
		assertTrue(returnValue.contains("Decorator:decoratorSolution.EncryptionOutputStream"));
		assertTrue(returnValue.contains("Component:java.io.InputStream"));
		assertTrue(returnValue.contains("Component:java.io.OutputStream"));

	}

}
