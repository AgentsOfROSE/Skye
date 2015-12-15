

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

	public ClassMethodVisitor(int arg0) {
		super(arg0);
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
		
		String returnType = Type.getReturnType(desc).getClassName();
		
		Type[] argTypes = Type.getArgumentTypes(desc);		
		
		List<String> stypes = new ArrayList<String>();
		for(Type t: argTypes){
			stypes.add(t.getClassName().substring(t.getClassName().lastIndexOf(".") + 1));
		}
		
		String symbol = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			symbol = "+";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			symbol = "-";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			symbol = "#";
		}
		
		System.out.print(symbol +" "+name+"(");
		for(int i = 0; i<stypes.size(); i++){
			System.out.print("Param"+(i+1)+" : " + stypes.get(i));
			if(i <= stypes.size()-2)
				System.out.print(", ");
		}
		System.out.print( ") : " + returnType+"\\l");
		
		return toDecorate;
	}

}
