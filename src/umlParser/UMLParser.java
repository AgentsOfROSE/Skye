package umlParser;

import java.io.IOException;
import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLParser implements Parsable {
	
	private ArrayList<ClassInfo> classes;
	private ArrayList<String> classListAbbreviated;
	private ArrayList<String> classListFull;

	public UMLParser(){
		this.classes = new ArrayList<ClassInfo>();
	    this.classListAbbreviated = new ArrayList<String>();
	    this.classListFull = new ArrayList<String>();
	}

	public void parse(String[] args) throws IOException {
		for (String className : this.getClassListFull()) {
			ClassReader reader = new ClassReader(className);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, this.getClasses().get(this.getClassListFull().indexOf(className)));

			reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
			reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}

		System.out.println("digraph G {\n\tfontname = \"Bitstream Vera Sans\"\n\tfontsize = 8 \n\n\t"
				+ "node [\n\t\t fontname = \"Bitstream Vera Sans\" \n\t\t fontsize = 8 \n\t\t shape = \"record\" \n\t] "
				+ "\n\n\tedge [\n\t\t fontname = \"Bitstream Vera Sans\"\n\t\t fontsize = 8 \n\t]\n");
		for (ClassInfo classInfo : classes) {
			System.out.print("\t" + classInfo.getName() + " [ \n \t \t label = \"{");
			for(String pattern : classInfo.getPatterns()){
				System.out.print("\\<\\<"+pattern+"\\>\\>\\n");
			}
			System.out.print(classInfo.getName() + "|");
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
			System.out.print("}\",\n");
			System.out.print("\t \t color = " + classInfo.getFrameColor()+"\n");
			System.out.print("\t]\n");
		}
	}

	public ArrayList<ClassInfo> getClasses() {
		return classes;
	}
	
	public ArrayList<String> getClassListAbbreviated() {
		return classListAbbreviated;
	}
	
	public ArrayList<String> getClassListFull() {
		return classListFull;
	}
	
	public void setClasses(ArrayList<ClassInfo> classes) {
		this.classes = classes;
	}
	
	public void setClassListAbbreviated(ArrayList<String> classListAbbreviated) {
		this.classListAbbreviated = classListAbbreviated;
	}
	
	public void setClassListFull(ArrayList<String> classListFull) {
		this.classListFull = classListFull;
	}

}
