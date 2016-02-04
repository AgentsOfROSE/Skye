package umlParser;

import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CompositeComponentVisitor extends ClassVisitor {
	ArrayList<String> abstractClasses;

	public CompositeComponentVisitor(int api, ArrayList<String> abstractClasses) {
		super(api);
		this.abstractClasses = abstractClasses;
	}

	public CompositeComponentVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if((access & Opcodes.ACC_INTERFACE) != 0 || (access & Opcodes.ACC_ABSTRACT) != 0)
			this.abstractClasses.add(name);
	}
}
