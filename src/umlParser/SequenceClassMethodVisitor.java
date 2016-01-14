package umlParser;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

public class SequenceClassMethodVisitor extends ClassVisitor {

	SequenceDiagramInfo info;
	String className;
	String methodName;
	int depth;

	public SequenceClassMethodVisitor(int arg0, SequenceDiagramInfo info, String className, String methodName, int depth) {
		super(arg0);
		this.info = info;
		this.className = className;
		this.methodName = methodName;
		this.depth = depth;
	}

	public SequenceClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		if(name.equals(methodName)){
			toDecorate = new MethodSequenceVisitor(Opcodes.ASM5, info, depth, className, toDecorate);
		}
		return toDecorate;
	}

}
