package umlParser;

import java.util.Arrays;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

public class ClassAssociationVisitor extends ClassVisitor {
	
	ClassInfo info;

	public ClassAssociationVisitor(int api, ClassInfo info) {
		super(api);
		this.info = info;
	}

	public ClassAssociationVisitor(int api, ClassVisitor decorated) {
		super(api, decorated);
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		FieldInfo fieldInfo = new FieldInfo();
		info.getFields().add(fieldInfo);

		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			fieldInfo.setAccess("+");
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			fieldInfo.setAccess("-");
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			fieldInfo.setAccess("#");
		}
		String type = Type.getType(desc).getClassName();
		type = type.lastIndexOf(".")>-1 ? type.substring(type.lastIndexOf(".")+1) : type;
		if(type.equals("ArrayList") && signature != null){
			type = signature.substring(signature.lastIndexOf("<L") + 2, signature.lastIndexOf(">") - 1);
			if(type.contains("/")){
				type = type.substring(type.lastIndexOf("/") + 1);
			}
			type = "ArrayList_" + type + "";
		}
		fieldInfo.setClassName(type);
		fieldInfo.setName(name);

		return toDecorate;

	}

}
