package umlParser;

import java.io.IOException;
import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLTextParser implements Parsable {

	public void parse(String[] args) throws IOException {
		ArrayList<ClassInfo> classes = new ArrayList<ClassInfo>();
		ArrayList<String> classList = new ArrayList<String>();
		for (String className : args) {
			classList.add(className.substring(className.lastIndexOf(".") + 1));
			ClassInfo info = new ClassInfo();
			classes.add(info);
			ClassReader reader = new ClassReader(className);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, info);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, info);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, info);

			reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
			reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}

		System.out.println("digraph G {\n\tfontname = \"Bitstream Vera Sans\"\n\tfontsize = 8 \n\n\t"
				+ "node [\n\t\t fontname = \"Bitstream Vera Sans\" \n\t\t fontsize = 8 \n\t\t shape = \"record\" \n\t] "
				+ "\n\n\tedge [\n\t\t fontname = \"Bitstream Vera Sans\"\n\t\t fontsize = 8 \n\t]\n");
		for (ClassInfo classInfo : classes) {
			System.out.print("\t" + classInfo.getName() + " [ \n \t \t label = \"{" + classInfo.getName() + "|");
			for (FieldInfo fieldInfo : classInfo.getFields()) {
				System.out.print(fieldInfo.getAccess() + " " + fieldInfo.getName() + " : " + fieldInfo.getClassName() + "\\l");
			}
			System.out.print("|");
			for (MethodInfo methodInfo : classInfo.getMethods()) {
				System.out.print(
						methodInfo.getAccess() + " " + methodInfo.getName().replace("<", "").replace(">", "") + "(");
				for (int i = 0; i < methodInfo.getParams().size(); i++) {
					System.out.print("Param" + (i + 1) + " : " + methodInfo.getParams().get(i));
					if (i <= methodInfo.getParams().size() - 2)
						System.out.print(", ");
				}
				System.out.print(") : " + methodInfo.getReturnType() + "\\l");
			}
			System.out.println("}\"\n\t]\n");
		}

		System.out.print("\n\tedge [\n\t\tarrowhead = \"empty\"\n\t]\n\n");
		for (ClassInfo classInfo : classes) {
			if (classList.contains(classInfo.getExtendedClass())) {
				System.out.print("\t" + classInfo.getName() + " -> " + classInfo.getExtendedClass() + "\n");
			}
		}

		System.out.print("\n\n\tedge [\n\t\tstyle = \"dashed\"\n\t\tarrowhead = \"normal\"\n\t]\n\n");
		for (ClassInfo classInfo : classes) {
			for (String interfaceName : classInfo.getImplementedClasses()) {
				if (classList.contains(interfaceName)) {
					System.out.println("\t" + classInfo.getName() + "->" + interfaceName);
				}
			}
		}

		System.out.print("\n\n\tedge [\n\t\tstyle = \"dashed\"\n\t\tarrowhead = \"vee\"\n\t]\n\n");
		for (ClassInfo classInfo : classes) {
			for (String usedClassName : classInfo.getUsedClasses()) {
				if (classList.contains(usedClassName.contains("ArrayList") ? usedClassName.replace("ArrayList_", "")
						: usedClassName)) {
					System.out.println("\t" + classInfo.getName() + "->" + (usedClassName.contains("ArrayList")
							? usedClassName.replace("ArrayList_", "") : usedClassName));
				}
			}
		}

		System.out.print("\n\n\tedge [\n\t\tstyle = \"solid\"\n\t\tarrowhead = \"vee\"\n\t]\n\n");
		for (ClassInfo classInfo : classes) {
			for (String associatedClass : classInfo.getAssociatedClasses()) {
				if (classList.contains(associatedClass.contains("ArrayList") ? associatedClass.replace("ArrayList_", "")
						: associatedClass)) {
					System.out.println("\t" + classInfo.getName() + "->" + (associatedClass.contains("ArrayList")
							? associatedClass.replace("ArrayList_", "") : associatedClass));
				}
			}
		}

		System.out.println("\n}");
	}

}
