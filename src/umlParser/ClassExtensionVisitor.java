package umlParser;


import jdk.internal.org.objectweb.asm.ClassVisitor;

public class ClassExtensionVisitor extends ClassVisitor {
	
	private ClassInfo info;

	public ClassExtensionVisitor(int api, ClassInfo info) {
		super(api);
		this.info = info;
	}

	public ClassExtensionVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if (!superName.equals("")) {
			String[] superPath = superName.split("/");
			info.setExtendedClass(superPath[superPath.length - 1]);
		}
	
	}

}
