package umlParser;

import java.io.IOException;

import org.junit.Test;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class SequenceDiagramParserTest {

	@Test
	public void parseTest1() throws IOException{
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor visitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, "umlParser.Student", "getAge", 1);
		info.setPackageName("umlParser");
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		System.out.println("Messages : " + info.getMessages());
		System.out.println("Objects : " + info.getObjects());
		

	}

}
