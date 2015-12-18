import jdk.internal.org.objectweb.asm.ClassVisitor;

public class ClassExtendsVisitor extends ClassVisitor {

	public ClassExtendsVisitor(int api) {
		super(api);
	}

	public ClassExtendsVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		String[] path = name.split("/");
		if (!superName.equals("")) {
			System.out.print("\t" + path[path.length - 1] + " -> " + superName);
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
