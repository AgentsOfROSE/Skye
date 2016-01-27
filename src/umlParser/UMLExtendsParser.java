package umlParser;

import java.io.IOException;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLExtendsParser extends AbstractUMLParser{
	
	public UMLExtendsParser(UMLParsable parser) {
		super(parser);
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor extVisitor = new ClassExtensionVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));
			reader.accept(extVisitor, ClassReader.EXPAND_FRAMES);
		}
		this.parser.parse(args);
		
		System.out.print("\n\tedge [\n\t\tarrowhead = \"empty\"\n\t]\n\n");
		for (ClassInfo classInfo : this.getClasses()) {
			if (this.getClassListAbbreviated().contains(classInfo.getExtendedClass())) {
				System.out.print("\t" + classInfo.getName() + " -> " + classInfo.getExtendedClass() + "\n");
			}
		}
	}
	

}
