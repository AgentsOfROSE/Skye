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
			String[] superPath = superName.split("/");
			System.out.print("\t" + path[path.length - 1] + " -> " + superPath[superPath.length - 1]);
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
