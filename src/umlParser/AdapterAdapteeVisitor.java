package umlParser;

import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

public class AdapterAdapteeVisitor extends ClassVisitor{
	
	private ArrayList<String> constructorParams = new ArrayList<String>();
	private ArrayList<String> fieldsFound = new ArrayList<String>();
	String className;
	HashMap<String, ArrayList<String>> adapters;

	public AdapterAdapteeVisitor(int api, HashMap<String, ArrayList<String>> adapters, String className) {
		super(api);
		this.className = className;
		this.adapters = adapters;
	}

	
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		type = type.lastIndexOf(".")>-1 ? type.substring(type.lastIndexOf(".")+1) : type;
		if (!this.fieldsFound.contains(type)) {
			this.fieldsFound.add(type);
			if(this.constructorParams.contains(type)){
				if(!this.adapters.keySet().contains(this.className.replace("/", "."))){
					this.adapters.put(className.replace("/", "."), new ArrayList<String>());
				}
				this.adapters.get(className.replace("/", ".")).add(type.replace("/", "."));
			}
		}
		
		return toDecorate;

	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		if(name.equals("<init>")){
			Type[] argTypes = Type.getArgumentTypes(desc);
			for (Type t : argTypes) {
				String className = t.getClassName().substring(t.getClassName().lastIndexOf(".") + 1);
				if (!this.constructorParams.contains(className)) {
					this.constructorParams.add(className);
					if(this.fieldsFound.contains(className)){
						if(!this.adapters.keySet().contains(this.className.replace("/", "."))){
							this.adapters.put(this.className.replace("/", "."), new ArrayList<String>());
						}
						this.adapters.get(this.className.replace("/", ".")).add(className.replace("/", "."));
					}
				}
			}
		}

		return toDecorate;
	}
}
