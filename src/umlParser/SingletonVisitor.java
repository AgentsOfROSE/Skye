package umlParser;


import jdk.internal.org.objectweb.asm.ClassVisitor;

public class SingletonVisitor extends ClassVisitor {
	
	private ClassInfo info;

	public SingletonVisitor(int api, ClassInfo info) {
		super(api);
		this.info = info;
	}

	public SingletonVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		//DO SOMETIHNG
	}

}
