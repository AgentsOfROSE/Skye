import java.io.IOException;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;


public class UMLTextParrser {
	public static void main(String[] args) throws IOException{
		for(String className: args){
			ClassReader reader = new ClassReader(className);
			
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5);
			
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
					declVisitor);
			
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
			
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
	}

}
