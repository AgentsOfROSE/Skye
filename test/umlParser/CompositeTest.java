package umlParser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class CompositeTest {

	@Test
	public void javaSwingTest() throws IOException{
		String className1 = "java.awt.Component";
		String className2 = "java.awt.Container";
		String className3 = "javax.swing.JComponent";
		String[] classes = new String[]{className1, className2, className3};
		String returnValue = (new CompositeDetector()).detect(classes);
		assertTrue(returnValue.contains("Composite:java.awt.Container"));
		assertTrue(returnValue.contains("Component:java.awt.Component"));
		assertTrue(returnValue.contains("Leaf:javax.swing.JComponent"));
	}
	
	@Test
	public void compositeSolutionTest() throws IOException{
		String className1 = "compositeSolution.AbstractSprite";
		String className2 = "compositeSolution.AnimationPanel";
		String className3 = "compositeSolution.AnimatorApp";
		String className4 = "compositeSolution.CircleSprite";
		String className5 = "compositeSolution.CompositeIterator";
		String className6 = "compositeSolution.DoubleTowerSprite";
		String className7 = "compositeSolution.ISprite";
		String className8 = "compositeSolution.MainWindow";
		String className9 = "compositeSolution.NullIterator";
		String className10 = "compositeSolution.RectangleSprite";
		String className11 = "compositeSolution.SnowmanSprite";
		String className12 = "compositeSolution.SpriteFactory";
		String className13 = "compositeSolution.TowerSprite";
		String[] classes = new String[]{className1, className2, className3, className4, className5, className6, className7, className8, className9, className10, className11, className12, className13};
		String returnValue = (new CompositeDetector()).detect(classes);
		assertTrue(returnValue.contains("Composite:compositeSolution.DoubleTowerSprite"));
		assertTrue(returnValue.contains("Composite:compositeSolution.SnowmanSprite"));		
		assertTrue(returnValue.contains("Composite:compositeSolution.TowerSprite"));
		assertTrue(returnValue.contains("Component:compositeSolution.AbstractSprite"));
		assertTrue(returnValue.contains("Leaf:compositeSolution.CircleSprite"));
		assertTrue(returnValue.contains("Leaf:compositeSolution.RectangleSprite"));
	}

	@Test
	public void adapterSolutionTest() throws IOException{
		String className1 = "adapterSolution.App";
		String className2 = "adapterSolution.IteratorToEnumerationAdapter";
		String className3 = "adapterSolution.LinearTransformer";
		String className4 = "java.util.Enumeration";
		String className5 = "java.util.Iterator";
		String[] classes = new String[]{className1, className2, className3, className4, className5};
		String returnValue = (new CompositeDetector()).detect(classes);
		assertTrue(returnValue.equals("Composite-~"));
	}
	
	@Test
	public void observerSolutionTest() throws IOException{
		String className1 = "problem.AppLauncher";
		String className2 = "problem.FileBehavior";
		String className3 = "problem.FileData";
		String className4 = "problem.HTMLDisplay";
		String className5 = "problem.Observer";
		String className6 = "problem.PrintHTMLFileName";
		String className7 = "problem.PrintHTMLFilePath";
		String className8 = "problem.Subject";
		String className9 = "problem.TextDisplay";
		String className10 = "problem.WordDisplay";
		String[] classes = new String[]{className1, className2, className3, className4, className5, className6, className7, className8, className9, className10};
		String returnValue = (new CompositeDetector()).detect(classes);
		assertTrue(returnValue.equals("Composite-~"));

	}

}