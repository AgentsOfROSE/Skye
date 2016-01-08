package umlParser;


import java.util.Arrays;

import jdk.internal.org.objectweb.asm.ClassVisitor;

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
		String[] path = name.split("/");
		info.setName(path[path.length - 1]);
		if (!superName.equals("")) {
			String[] superPath = superName.split("/");
			info.setExtendedClass(superPath[superPath.length - 1]);
		}
		if(interfaces.length != 0){
			for(String inter : interfaces){
				String[] interPath = inter.split("/");
				info.getImplementedClasses().add(interPath[interPath.length-1]);
			}
		}
		
	}

//	@Override
//	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//		String[] path = name.split("/");
//		System.out.print("\t" + path[path.length-1] + " [ \n \t \t label = \"{" + path[path.length-1] +"|");
//		super.visit(version, access, name, signature, superName, interfaces);
//	}

}
