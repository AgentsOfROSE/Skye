

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {

	public ClassFieldVisitor(int api) {
		super(api);
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated) {
		super(api, decorated);
	}
	
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value){
		FieldVisitor toDecorate = super.visitField(access, name, 
				desc, signature, value);
	
		String type = Type.getType(desc).getClassName();
		System.out.println("	"+type+" "+name);
		
		return toDecorate;
		
	}

}
