package umlParser;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

public class GetParamsUsesVisitor extends ClassVisitor {

	ClassInfo info;

	public GetParamsUsesVisitor(int arg0, ClassInfo info) {
		super(arg0);
		this.info = info;
	}

	public GetParamsUsesVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
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
			if (!info.getUsedClasses().contains(className)) {
				info.getUsedClasses().add(className);
			}
		}

		return toDecorate;
	}

}
