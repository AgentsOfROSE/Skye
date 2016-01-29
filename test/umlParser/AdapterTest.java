package umlParser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class AdapterTest {

	@Test
	public void InputStreamReaderTest() throws IOException{
		String className1 = "java.io.InputStreamReader";
		String className2 = "java.io.FileReader";
		String className3 = "java.io.InputStream";
		String className4 = "java.lang.Readable";
		String className5 = "java.io.BufferedReader";
		String[] classes = new String[]{className1, className2, className3, className4, className5};
		String returnValue = (new AdapterDetector()).detect(classes);
		assertTrue(returnValue.equals("Adapter-~"));
	}
	
	@Test
	public void OutputStreamReaderTest() throws IOException{
		String className1 = "java.io.OutputStreamWriter";
		String className2 = "java.io.FileReader";
		String className3 = "java.io.OutputStream";
		String className4 = "java.io.BufferedOutputStream";
		String className5 = "java.io.File";
		String[] classes = new String[]{className1, className2, className3, className4, className5};
		String returnValue = (new AdapterDetector()).detect(classes);
		assertTrue(returnValue.equals("Adapter-~"));
	}
	
	@Test
	public void MouseAdapterTest() throws IOException{
		String className1 = "java.awt.event.MouseAdapter";
		String className2 = "java.awt.event.MouseMotionListener";
		String className3 = "java.awt.event.MouseEvent";
		String className4 = "java.awt.event.MouseListener";
		String[] classes = new String[]{className1, className2, className3, className4};
		String returnValue = (new AdapterDetector()).detect(classes);
		System.out.println(returnValue);
		assertTrue(returnValue.contains("Adapter-~"));
	}
	
	@Test
	public void adapterSolutionTest() throws IOException{
		String className1 = "adapterSolution.App";
		String className2 = "adapterSolution.IteratorToEnumerationAdapter";
		String className3 = "adapterSolution.LinearTransformer";
		String className4 = "java.util.Enumeration";
		String className5 = "java.util.Iterator";
		String[] classes = new String[]{className1, className2, className3, className4, className5};
		String returnValue = (new AdapterDetector()).detect(classes);
		assertTrue(returnValue.contains("Adapter:adapterSolution.IteratorToEnumerationAdapter"));
		assertTrue(returnValue.contains("Adaptee:java.util.Iterator"));
		assertTrue(returnValue.contains("Target:java.util.Enumeration"));
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
		String returnValue = (new AdapterDetector()).detect(classes);
		assertFalse(returnValue.contains("Decorator:java.io.FilterInputStream"));
		assertFalse(returnValue.contains("Decorator:java.io.FilterOutputStream"));
		assertFalse(returnValue.contains("Decorator:decoratorSolution.DecryptionInputStream"));
		assertFalse(returnValue.contains("Decorator:decoratorSolution.EncryptionOutputStream"));
		assertFalse(returnValue.contains("Component:java.io.InputStream"));
		assertFalse(returnValue.contains("Component:java.io.OutputStream"));

	}

}