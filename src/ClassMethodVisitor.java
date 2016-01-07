

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.commons.Method;
import jdk.internal.org.objectweb.asm.Label;

public class ClassMethodVisitor extends ClassVisitor {

	ClassInfo info;
	
	public ClassMethodVisitor(int arg0, ClassInfo info) {
		super(arg0);
		this.info = info;
		// TODO Auto-generated constructor stub
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, 
			String signature, String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, 
				signature, exceptions);
		MethodInfo methodInfo = new MethodInfo();
		info.getMethods().add(methodInfo);
		
		methodInfo.setReturnType(Type.getReturnType(desc).getClassName());
		
		Type[] argTypes = Type.getArgumentTypes(desc);	
		for(Type t: argTypes){
			methodInfo.getParams().add(t.getClassName().substring(t.getClassName().lastIndexOf(".") + 1));
		}
		
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			methodInfo.setAccess("+");
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			methodInfo.setAccess("-");
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			methodInfo.setAccess("#");
		}
				
		return toDecorate;
	}

}
