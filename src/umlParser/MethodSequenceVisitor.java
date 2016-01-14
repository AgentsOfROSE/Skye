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
		if(owner.replace("/", ".").contains(info.getPackageName()))
		if (opcode != 183) {
			info.getMessages()
					.add(new MessageInfo(this.depth, this.className.substring(this.className.lastIndexOf(".") + 1),
							owner.substring(owner.lastIndexOf("/") + 1), returnType.equals("void") ? "" : returnType,
							name));
		}
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		if (type.replaceAll("/", ".").contains(info.getPackageName())) {
			info.getObjects().add(type.substring(type.lastIndexOf("/") + 1));
			info.getMessages().add(new MessageInfo(this.depth,
					this.className.substring(this.className.lastIndexOf(".") + 1), type.substring(type.lastIndexOf("/") + 1), "", "new"));
		}
	}

}
