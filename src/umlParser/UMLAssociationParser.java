package umlParser;

import java.io.IOException;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLAssociationParser extends AbstractUMLParser{
	
	public UMLAssociationParser(UMLParsable parser) {
		super(parser);
	}

	public void parse(String[] args) throws IOException {
		HashMap<String, String> labels = new HashMap<String, String>();
		labels.put("Component:Decorator", "Decorates");
		labels.put("Adaptee:Adapter", "Adapts");
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor assocVisitor = new ClassAssociationVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));
			reader.accept(assocVisitor, ClassReader.EXPAND_FRAMES);
		}
		this.parser.parse(args);
		
		System.out.print("\n\n\tedge [\n\t\tstyle = \"solid\"\n\t\tarrowhead = \"vee\"\n\t]\n\n");
		for (ClassInfo classInfo : this.getClasses()) {
			for (String associatedClass : classInfo.getAssociatedClasses()) {
				if (this.getClassListAbbreviated().contains(associatedClass.contains("ArrayList") ? associatedClass.replace("ArrayList_", "")
						: associatedClass)) {
					System.out.print("\t" + classInfo.getName() + "->" + (associatedClass.contains("ArrayList")
							? associatedClass.replace("ArrayList_", "") : associatedClass) + "[label=\"");
					for(String associatedAnnotation: (this.getClasses().get(this.getClassListAbbreviated().indexOf((associatedClass.contains("ArrayList")
							? associatedClass.replace("ArrayList_", "") : associatedClass))).getAnnotations())){
						for(String thisAnnotation: this.getClasses().get(this.getClassListAbbreviated().indexOf(classInfo.getName())).getAnnotations()){
							if(labels.containsKey(associatedAnnotation+":"+thisAnnotation))
								System.out.print("<<"+labels.get(associatedAnnotation+":"+thisAnnotation)+">>");
						}
					}
					System.out.println("\"]");
				}
			}
		}
	}
	

}
