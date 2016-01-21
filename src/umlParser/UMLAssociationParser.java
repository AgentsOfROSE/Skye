package umlParser;

import java.io.IOException;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLAssociationParser extends UMLParser{
	
	UMLParser parser;

	public UMLAssociationParser(UMLParser parser) {
		this.parser = parser;
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.parse(args);
		this.setClasses(this.parser.getClasses());
		this.setClassListAbbreviated(this.parser.getClassListAbbreviated());
		this.setClassListFull(this.parser.getClassListFull());
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor assocVisitor = new ClassAssociationVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));
			reader.accept(assocVisitor, ClassReader.EXPAND_FRAMES);
		}
		
		System.out.print("\n\n\tedge [\n\t\tstyle = \"solid\"\n\t\tarrowhead = \"vee\"\n\t]\n\n");
		for (ClassInfo classInfo : this.getClasses()) {
			for (String associatedClass : classInfo.getAssociatedClasses()) {
				if (this.getClassListAbbreviated().contains(associatedClass.contains("ArrayList") ? associatedClass.replace("ArrayList_", "")
						: associatedClass)) {
					System.out.println("\t" + classInfo.getName() + "->" + (associatedClass.contains("ArrayList")
							? associatedClass.replace("ArrayList_", "") : associatedClass));
				}
			}
		}
	}
	

}
