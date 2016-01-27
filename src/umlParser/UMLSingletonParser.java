package umlParser;

import java.io.IOException;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLSingletonParser extends AbstractUMLParser{
	

	public UMLSingletonParser(UMLParsable parser) {
		super(parser);
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor singletonVisitor = new SingletonVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)), className);
			reader.accept(singletonVisitor, ClassReader.EXPAND_FRAMES);
		}
		this.parser.parse(args);
	}
	

}
