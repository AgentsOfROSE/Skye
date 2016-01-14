package umlParser;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import jdk.internal.org.objectweb.asm.MethodVisitor;

public class MethodReturnsVisitor extends MethodVisitor {

	private ClassInfo info;
	
	
	public MethodReturnsVisitor(int arg0) {
		super(arg0);
	}

	public MethodReturnsVisitor(int arg0, ClassInfo info, MethodVisitor arg1) {
		super(arg0, arg1);
		this.info = info;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		String returnType = Type.getReturnType(desc).getClassName()
				.substring(Type.getReturnType(desc).getClassName().lastIndexOf(".") + 1);
		if(!info.getUsedClasses().contains(returnType)){
			info.getUsedClasses().add(returnType);
		}
	}
	
	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		System.out.println(type);
		String returnType = type.substring(type.lastIndexOf("/") + 1);
		if(!info.getUsedClasses().contains(returnType)){
			info.getUsedClasses().add(returnType);
		}
	}

}
