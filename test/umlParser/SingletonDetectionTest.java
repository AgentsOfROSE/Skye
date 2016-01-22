package umlParser;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class SingletonDetectionTest {

	@Test
	public void RuntimeTest() throws IOException{
		String className = "java.lang.Runtime";
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader(className);
		ClassVisitor singletonVisitor = new SingletonVisitor(Opcodes.ASM5, info, className);
		reader.accept(singletonVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getPatterns().contains("Singleton"));
	}
	
	@Test
	public void CalendarTest() throws IOException{
		String className = "java.util.Calendar";
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader(className);
		ClassVisitor singletonVisitor = new SingletonVisitor(Opcodes.ASM5, info, className);
		reader.accept(singletonVisitor, ClassReader.EXPAND_FRAMES);
		assertFalse(info.getPatterns().contains("Singleton"));
	}
	
	@Test
	public void FilterInputStreamTest() throws IOException{
		String className = "java.io.FilterInputStream";
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader(className);
		ClassVisitor singletonVisitor = new SingletonVisitor(Opcodes.ASM5, info, className);
		reader.accept(singletonVisitor, ClassReader.EXPAND_FRAMES);
		assertFalse(info.getPatterns().contains("Singleton"));
	}
	
	@Test
	public void DesktopTest() throws IOException{
		String className = "java.awt.Desktop";
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader(className);
		ClassVisitor singletonVisitor = new SingletonVisitor(Opcodes.ASM5, info, className);
		reader.accept(singletonVisitor, ClassReader.EXPAND_FRAMES);
		assertFalse(info.getPatterns().contains("Singleton"));
	}
	
	@Test
	public void EagerTest() throws IOException{
		String className = "umlParser.RoseHulmanSGAPresident";
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader(className);
		ClassVisitor singletonVisitor = new SingletonVisitor(Opcodes.ASM5, info, className);
		reader.accept(singletonVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getPatterns().contains("Singleton"));
	}
	
	@Test
	public void LazyTest() throws IOException{
		String className = "umlParser.RoseHulmanSABPresident";
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader(className);
		ClassVisitor singletonVisitor = new SingletonVisitor(Opcodes.ASM5, info, className);
		reader.accept(singletonVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getPatterns().contains("Singleton"));
	}
}
