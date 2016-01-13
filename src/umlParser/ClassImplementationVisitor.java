package umlParser;


import jdk.internal.org.objectweb.asm.ClassVisitor;

public class ClassImplementationVisitor extends ClassVisitor {
	
	private ClassInfo info;

	public ClassImplementationVisitor(int api, ClassInfo info) {
		super(api);
		this.info = info;
	}

	public ClassImplementationVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if(interfaces.length != 0){
			for(String inter : interfaces){
				String[] interPath = inter.split("/");
				info.getImplementedClasses().add(interPath[interPath.length-1]);
			}
		}
		
	}

}
