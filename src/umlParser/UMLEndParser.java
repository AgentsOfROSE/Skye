package umlParser;

import java.io.IOException;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLEndParser extends UMLParser{
	
	UMLParser parser;

	public UMLEndParser(UMLParser parser) {
		this.parser = parser;
	}
	
	public void parse(String[] args) throws IOException {
		for (String className : args) {
			this.getClassListFull().add(className);
			this.getClassListAbbreviated().add(className.substring(className.lastIndexOf(".") + 1));
			ClassInfo info = new ClassInfo();
			this.getClasses().add(info);
		}
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		this.parser.parse(args);
		System.out.println("\n}");
	}
	

}
