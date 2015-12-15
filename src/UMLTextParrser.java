import java.io.IOException;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;


public class UMLTextParrser {
	public static void main(String[] args) throws IOException{
		System.out.println("digraph G {\n\tfontname = \"Bitstream Vera Sans\"\n\tfontsize = 8 \n\n\t"
				+ "node [\n\t\t fontname = \"Bitstream Vera Sans\" \n\t\t fontsize = 8 \n\t\t shape = record \n\t] "
						+ "\n\n\tedge [\n\t\t fontname = \"Bitstream Vera Sans\"\n\t\t fontsize = 8 \n\t]");
		for(String className: args){
			ClassReader reader = new ClassReader(className);
			
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5);
			
			reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
			
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5);
			
			reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
			System.out.print("|");
			
			//ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		}
		System.out.println("}");
	}

}
