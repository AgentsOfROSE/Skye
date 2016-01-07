import java.io.IOException;
import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;


public class UMLTextParser {
	public static void main(String[] args) throws IOException{
		ArrayList<ClassInfo> classes = new ArrayList<ClassInfo>();
		for(String className: args){
			ClassInfo info = new ClassInfo();
			classes.add(info);
			ClassReader reader = new ClassReader(className);
			
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, info);
			
			reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		}
		
		System.out.println("digraph G {\n\tfontname = \"Bitstream Vera Sans\"\n\tfontsize = 8 \n\n\t"
				+ "node [\n\t\t fontname = \"Bitstream Vera Sans\" \n\t\t fontsize = 8 \n\t\t shape = \"record\" \n\t] "
						+ "\n\n\tedge [\n\t\t fontname = \"Bitstream Vera Sans\"\n\t\t fontsize = 8 \n\t]");
		for(ClassInfo classInfo: classes){
			System.out.print("\t" + classInfo.getName() + " [ \n \t \t label = \"{" + classInfo.getName() +"|");
		}
		
		System.out.print("\n\n\tedge [\n\t\tarrowhead = \"empty\"\n\t]\n\n");
		for(ClassInfo classInfo: classes){
			System.out.print("\t" + classInfo.getName() + " -> " + classInfo.getExtendedClass() + "\n");
		}
		
		System.out.print("\n\n\tedge [\n\t\tstyle = \"dashed\"\n\t\tarrowhead = \"normal\"\n\t]");
		for(ClassInfo classInfo: classes){
			for(String interfaceName : classInfo.getImplementedClasses()){
				System.out.println("\t" + classInfo.getName() + "->" + interfaceName);
			}
		}

//			ClassReader reader = new ClassReader(className);
//			
//			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5);
//			
//			reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
//			
//			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5);
//			
//			reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
//			System.out.print("|");
//			
//			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5);
//			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
//			System.out.println("}\"\n\t]");
//		}
//			 
//		System.out.print("\n\n\tedge [\n\t\tstyle = \"dashed\"\n\t\tarrowhead = \"normal\"\n\t]");
//		for(String className: args){
//			ClassReader reader = new ClassReader(className);
//			 
//			ClassVisitor classImplVisitor = new ClassImplementsVisitor(Opcodes.ASM5);
//			 
//			reader.accept(classImplVisitor, ClassReader.EXPAND_FRAMES);
//		 }
//		
//		System.out.print("\n\n\tedge [\n\t\tstyle = \"dashed\"\n\t\tarrowhead = \"vee\"\n\t]");
//		
//		System.out.print("\n\n\tedge [\n\t\tstyle = \"normal\"\n\t\tarrowhead = \"vee\"\n\t]\n\n");
//		
//		
//		System.out.println("}");
	}

}
