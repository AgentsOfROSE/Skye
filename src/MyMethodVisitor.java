import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

public class MyMethodVisitor extends MethodVisitor {
	private ClassInfo info;

	public MyMethodVisitor(int arg0) {
		super(arg0);
	}

	public MyMethodVisitor(int arg0, MethodVisitor arg1, ClassInfo info) {
		super(arg0, arg1);
		this.info = info;
	}
	
//	@Override
//	public void visitMethodInsn(int opcode,String owner,String name,String desc, boolean itf){
//		super.visitMethodInsn(opcode, owner, name, desc, itf);
//		Type[] argTypes = Type.getArgumentTypes(desc);	
//		for(Type t: argTypes){
//			String className = t.getClassName().substring(t.getClassName().lastIndexOf(".") + 1);
//			if(!info.getUsedClasses().contains(className)){
//				info.getUsedClasses().add(className);
//			}
//		}
//	}

}
