package umlParser;


import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class AdapterTargetVisitor extends ClassVisitor {
	
	private ArrayList<String> interfaces;

	public AdapterTargetVisitor(int api, ArrayList<String> interfaces) {
		super(api);
		this.interfaces = interfaces;
	}

	public AdapterTargetVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if((access & Opcodes.ACC_INTERFACE) != 0)
			this.interfaces.add(name);
	}

}