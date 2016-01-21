package umlParser;

import java.io.IOException;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLUsesParser extends UMLParser{
	
	UMLParser parser;

	public UMLUsesParser(UMLParser parser) {
		this.parser = parser;
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.parse(args);
		this.setClasses(this.parser.getClasses());
		this.setClassListAbbreviated(this.parser.getClassListAbbreviated());
		this.setClassListFull(this.parser.getClassListFull());
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor paramsUsesVisitor = new GetParamsUsesVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));
			ClassVisitor returnUsesVisitor = new GetReturnUsesVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));
			reader.accept(paramsUsesVisitor, ClassReader.EXPAND_FRAMES);
			reader.accept(returnUsesVisitor, ClassReader.EXPAND_FRAMES);
		}
		
		System.out.print("\n\n\tedge [\n\t\tstyle = \"dashed\"\n\t\tarrowhead = \"vee\"\n\t]\n\n");
		for (ClassInfo classInfo : this.getClasses()) {
			for (String usedClassName : classInfo.getUsedClasses()) {
				if (this.getClassListAbbreviated().contains(usedClassName.contains("ArrayList") ? usedClassName.replace("ArrayList_", "")
						: usedClassName)) {
					System.out.println("\t" + classInfo.getName() + "->" + (usedClassName.contains("ArrayList")
							? usedClassName.replace("ArrayList_", "") : usedClassName));
				}
			}
		}
	}
	

}
