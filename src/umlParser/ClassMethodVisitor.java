package umlParser;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

public class ClassMethodVisitor extends ClassVisitor {

	ClassInfo info;

	public ClassMethodVisitor(int arg0, ClassInfo info) {
		super(arg0);
		this.info = info;
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodInfo methodInfo = new MethodInfo();
		info.getMethods().add(methodInfo);
		String returnType = Type.getReturnType(desc).getClassName()
				.substring(Type.getReturnType(desc).getClassName().lastIndexOf(".") + 1);
		if(returnType.equals("ArrayList") && signature != null){
			returnType = signature.substring(signature.indexOf(")"));
			returnType = returnType.substring(returnType.lastIndexOf("<L") + 2);
			returnType = returnType.substring(returnType.lastIndexOf("/") + 1, returnType.lastIndexOf(">") - 1);
			returnType = "ArrayList_" + returnType + "";
		}
		methodInfo.setReturnType(returnType);
		
		Type[] argTypes = Type.getArgumentTypes(desc);
		String currentSig = "";
		if(signature != null){
			currentSig = signature.substring(signature.indexOf("(") + 1, signature.indexOf(")"));
		}
		for (Type t : argTypes) {
			String className = t.getClassName().substring(t.getClassName().lastIndexOf(".") + 1);
			if(className.equals("ArrayList") && signature != null){
				className = currentSig.substring(currentSig.indexOf("<L") + 2, currentSig.indexOf(">") - 1);
				currentSig = currentSig.substring(currentSig.indexOf(">") + 1);
				if(className.contains("/")){
					className = className.substring(className.lastIndexOf("/") + 1);
				}
				className = "ArrayList_" + className + "";
			}
			methodInfo.getParams().add(className);
		}

		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			methodInfo.setAccess("+");
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			methodInfo.setAccess("-");
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			methodInfo.setAccess("#");
		}
		methodInfo.setName(name);
		return toDecorate;
	}

}
