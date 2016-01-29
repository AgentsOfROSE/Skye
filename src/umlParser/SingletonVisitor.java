package umlParser;


import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class SingletonVisitor extends ClassVisitor {
	
	private ArrayList<String> singletons;
	private String className;
	private boolean fieldFound = false;
	private boolean methodFound = false;

	public SingletonVisitor(int api, ArrayList<String> singletons, String className) {
		super(api);
		this.singletons = singletons;
		this.className = className.replace(".", "/");
	}

	public SingletonVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		if((access & Opcodes.ACC_PUBLIC) != 0){
			if((access & Opcodes.ACC_STATIC) != 0){
				if(desc.substring(desc.indexOf(")")).contains(this.className)){
					methodFound = true;
					if(methodFound && fieldFound){
						this.singletons.add(this.className.replace("/", "."));
					}
				}
			}
		}
		return toDecorate;
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		if ((access & Opcodes.ACC_PRIVATE) != 0) {
			if ((access & Opcodes.ACC_STATIC) != 0){
				if(desc.contains(this.className)){
					fieldFound = true;
					if(methodFound && fieldFound){
						this.singletons.add(this.className.replace("/", "."));
					}
				}
			}
		}
		return toDecorate;

	}

}
