

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
		MethodVisitor methodVisitor = new MyMethodVisitor(Opcodes.ASM5, toDecorate, info);
		
		methodInfo.setReturnType(Type.getReturnType(desc).getClassName());
		
		Type[] argTypes = Type.getArgumentTypes(desc);	
		for(Type t: argTypes){
			String className = t.getClassName().substring(t.getClassName().lastIndexOf(".") + 1);
			methodInfo.getParams().add(className);
			if(!info.getUsedClasses().contains(className)){
				info.getUsedClasses().add(className);
			}
		}
		
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			methodInfo.setAccess("+");
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			methodInfo.setAccess("-");
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			methodInfo.setAccess("#");
		}
		methodInfo.setName(name);
//		System.out.print(symbol +" "+name.replace("<", "").replace(">", "")+"(");
//		for(int i = 0; i<stypes.size(); i++){
//			System.out.print("Param"+(i+1)+" : " + stypes.get(i));
//			if(i <= stypes.size()-2)
//				System.out.print(", ");
//		}
//		System.out.print( ") : " + returnType+"\\l");
//		
		return methodVisitor;
	}

}
