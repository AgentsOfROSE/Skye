package umlParser;


import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;

public class DecoratorAbstractVisitor extends ClassVisitor {
	
	private HashMap<String, String> abstractMap;
	private ArrayList<String> allInterfaces;
	private ArrayList<String> implementedInterfaces = new ArrayList<String>();
	private ArrayList<String> storedInterfaces = new ArrayList<String>();
	private String abstractName = null;
	

	public DecoratorAbstractVisitor(int api, HashMap<String, String> abstractMap, ArrayList<String> interfaces) {
		super(api);
		this.abstractMap = abstractMap;
		this.allInterfaces = interfaces;
	}

	public DecoratorAbstractVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			this.abstractName = name;
			for(String interfaceName : interfaces){
				if(this.allInterfaces.contains(interfaceName)) {
					implementedInterfaces.add(interfaceName);
					if(storedInterfaces.contains(interfaceName))
						abstractMap.put(name.replace("/", "."), interfaceName.replace("/", "."));
				}
			}
			if(this.allInterfaces.contains(superName)) {
				implementedInterfaces.add(superName);
				if(storedInterfaces.contains(superName))
					abstractMap.put(name.replace("/", "."), superName.replace("/", "."));
			}
		
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		for(String interfaceName : this.allInterfaces){
			if(desc.contains(interfaceName)){
				this.storedInterfaces.add(interfaceName);
				if(implementedInterfaces.contains(interfaceName))
					abstractMap.put(this.abstractName.replace("/", "."), interfaceName.replace("/", "."));
			}
		}
		return toDecorate;

	}

}