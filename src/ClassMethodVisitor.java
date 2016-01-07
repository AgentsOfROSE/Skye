

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
	public MethodVisitor visitMethod(int access, String name, String desc, 
			String signature, String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, 
				signature, exceptions);
		MethodInfo methodInfo = new MethodInfo();
		info.getMethods().add(methodInfo);
		MethodVisitor methodVisitor = new MyMethodVisitor(Opcodes.ASM5, toDecorate, info);
		
		methodInfo.setReturnType(Type.getReturnType(desc).getClassName());
		
		Type[] argTypes = Type.getArgumentTypes(desc);	
		for(Type t: argTypes){
			String className = t.getClassName().substring(t.getClassName().lastIndexOf(".") + 1);
			methodInfo.getParams().add(className);
			if(!info.getUsedClasses().contains(className)){
				info.getUsedClasses().add(className);
			}
		}
		
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			methodInfo.setAccess("+");
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			methodInfo.setAccess("-");
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			methodInfo.setAccess("#");
		}
		methodInfo.setName(name);

		return methodVisitor;
	}

}
