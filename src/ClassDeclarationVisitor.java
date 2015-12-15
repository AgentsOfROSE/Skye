

import java.util.Arrays;

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {

	public ClassDeclarationVisitor(int api) {
		super(api);
	}

	public ClassDeclarationVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		String[] path = name.split("/");
		System.out.println("\t" + path[path.length-1] + " [ \n \t \t label = \"{" + path[path.length-1] +"|");
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
