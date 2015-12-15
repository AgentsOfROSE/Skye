
import java.util.Arrays;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {

	public ClassFieldVisitor(int api) {
		super(api);
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated) {
		super(api, decorated);
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);

		String symbol = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			symbol = "+";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			symbol = "-";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			symbol = "#";
		}
		String type = Type.getType(desc).getClassName();
		String classname = type.lastIndexOf(".")>-1 ? type.substring(type.lastIndexOf(".")+1) : type;
		System.out.print(symbol + " " + name + " : " + classname + "\\l");

		return toDecorate;

	}

}
