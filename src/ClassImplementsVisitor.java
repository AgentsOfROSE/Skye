import java.util.Arrays;

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class ClassImplementsVisitor extends ClassVisitor {

	public ClassImplementsVisitor(int arg0) {
		super(arg0);
	}

	public ClassImplementsVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		String[] path = name.split("/");
		if(interfaces.length != 0){
			for(String inter : interfaces){
				System.out.println("\t" + path[path.length-1] + "->" + inter);
			}
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
