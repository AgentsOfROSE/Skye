package umlParser;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class SequenceDiagramParserTest {

	@Test
	public void parseTest1() throws IOException{
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		info.setPackageName("umlParser");
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor visitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, "umlParser.Student", "makeFriend", new ArrayList<String>(), 1);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMessages().get(0).getClass().getTypeName().equals("umlParser.MessageInfo"));
		assertTrue(info.getObjects().get(0).equals("Student"));
	}
	
	@Test
	public void parseTest2() throws IOException{
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		info.setPackageName("umlParser");
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor visitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, "umlParser.Student", "hatesLife", new ArrayList<String>(), 1);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMessages().get(0).getClass().getTypeName().equals("umlParser.MessageInfo"));
		assertTrue(info.getMessages().size() == 2);
		assertTrue(info.getObjects().get(0).equals("RoseHulmanStudent"));
	}
	
	@Test
	public void parseTest3() throws IOException{
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		info.setPackageName("umlParser");
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor visitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, "umlParser.Student", "age", new ArrayList<String>(), 1);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMessages().size() == 0);
		assertTrue(info.getObjects().size() == 0);
	}
	
	@Test
	public void parseTest4() throws IOException{
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		info.setPackageName("umlParser");
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor visitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, "umlParser.Student", "cries", new ArrayList<String>(), 1);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMessages().get(0).getClass().getTypeName().equals("umlParser.MessageInfo"));
		assertTrue(info.getMessages().size() == 3);
		assertTrue(info.getObjects().size() == 1);
	}
	
	@Test
	public void parseTest5() throws IOException{
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		info.setPackageName("umlParser");
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor visitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, "umlParser.Student", "jumpUpAndDown", new ArrayList<String>(), info.getMaxDepth());
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMessages().size() == 0);
		assertTrue(info.getObjects().size() == 0);
	}
	
	@Test
	public void edgeCase1() throws IOException{
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		info.setPackageName("umlParser");
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor visitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, "umlParser.Student", "random", new ArrayList<String>(), info.getMaxDepth());
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMessages().size() == 1);
		assertTrue(info.getObjects().size() == 0);
	}
	
	@Test
	public void edgeCase2() throws IOException{
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		info.setPackageName("umlParser");
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor visitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, "umlParser.Student", "numFriends", new ArrayList<String>(), info.getMaxDepth());
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMessages().size() == 0);
		assertTrue(info.getObjects().size() == 0);
	}

}
