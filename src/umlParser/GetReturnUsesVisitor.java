package umlParser;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

public class GetReturnUsesVisitor extends ClassVisitor {

	ClassInfo info;

	public GetReturnUsesVisitor(int arg0, ClassInfo info) {
		super(arg0);
		this.info = info;
	}

	public GetReturnUsesVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		
		String returnType = Type.getReturnType(desc).getClassName()
				.substring(Type.getReturnType(desc).getClassName().lastIndexOf(".") + 1);
		if(returnType.equals("ArrayList") && signature != null){
			returnType = signature.substring(signature.indexOf(")"));
			returnType = returnType.substring(returnType.lastIndexOf("<L") + 2);
			returnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.lastIndexOf(">") - 1);
			returnType = "ArrayList_" + returnType + "";
		}
		if (!returnType.equals("void")) {
			if (!info.getUsedClasses().contains(returnType)) {
				info.getUsedClasses().add(returnType);
			}
		}

		return toDecorate;
	}

}
