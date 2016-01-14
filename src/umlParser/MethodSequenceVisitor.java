package umlParser;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import jdk.internal.org.objectweb.asm.MethodVisitor;

public class MethodSequenceVisitor extends MethodVisitor {

	private SequenceDiagramInfo info;
	private String className;
	private int depth;
	
	
	public MethodSequenceVisitor(int arg0) {
		super(arg0);
	}

	public MethodSequenceVisitor(int arg0, SequenceDiagramInfo info, int depth, String className, MethodVisitor arg1) {
		super(arg0, arg1);
		this.info = info;
		this.className = className;
		this.depth = depth;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		String returnType = Type.getReturnType(desc).getClassName()
				.substring(Type.getReturnType(desc).getClassName().lastIndexOf(".") + 1);
		info.getMessages().add(new MessageInfo(this.depth, owner, this.className, returnType, desc));
	}
	
	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		String newObjectName = "Class"+info.getObjects().size();
		info.getObjects().put(newObjectName, type.substring(type.lastIndexOf("/")));
		info.getMessages().add(new MessageInfo(this.depth, newObjectName, this.className, "", "<<create>>"));
	}

}
