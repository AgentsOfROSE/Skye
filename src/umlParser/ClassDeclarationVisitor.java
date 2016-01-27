package umlParser;


import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	private ClassInfo info;

	public ClassDeclarationVisitor(int api, ClassInfo info) {
		super(api);
		this.info = info;
	}

	public ClassDeclarationVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if((access & Opcodes.ACC_INTERFACE) != 0)
			info.getAnnotations().add("Interface");
		String[] path = name.split("/");
		info.setName(path[path.length - 1]);
		
	}

}
