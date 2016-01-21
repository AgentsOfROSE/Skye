package umlParser;

import java.io.IOException;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLImplementsParser extends UMLParser{
	
	UMLParser parser;

	public UMLImplementsParser(UMLParser parser) {
		this.parser = parser;
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor implVisitor = new ClassImplementationVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));
			reader.accept(implVisitor, ClassReader.EXPAND_FRAMES);
		}
		this.parser.parse(args);
		
		System.out.print("\n\n\tedge [\n\t\tstyle = \"dashed\"\n\t\tarrowhead = \"normal\"\n\t]\n\n");
		for (ClassInfo classInfo : this.getClasses()) {
			for (String interfaceName : classInfo.getImplementedClasses()) {
				if (this.getClassListAbbreviated().contains(interfaceName)) {
					System.out.println("\t" + classInfo.getName() + "->" + interfaceName);
				}
			}
		}

	}
	

}
