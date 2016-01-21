package umlParser;

import java.io.IOException;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLExtendsParser extends UMLParser{
	
	UMLParser parser;

	public UMLExtendsParser(UMLParser parser) {
		this.parser = parser;
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.parse(args);
		this.setClasses(this.parser.getClasses());
		this.setClassListAbbreviated(this.parser.getClassListAbbreviated());
		this.setClassListFull(this.parser.getClassListFull());
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor extVisitor = new ClassExtensionVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));
			reader.accept(extVisitor, ClassReader.EXPAND_FRAMES);
		}
		
		System.out.print("\n\tedge [\n\t\tarrowhead = \"empty\"\n\t]\n\n");
		for (ClassInfo classInfo : this.getClasses()) {
			if (this.getClassListAbbreviated().contains(classInfo.getExtendedClass())) {
				System.out.print("\t" + classInfo.getName() + " -> " + classInfo.getExtendedClass() + "\n");
			}
		}
	}
	

}
