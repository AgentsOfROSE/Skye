package umlParser;

import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;

public class CompositeCompositeVisitor extends ClassVisitor{

	private HashMap<String, String> compositeMap;
	private ArrayList<String> allInterfaces;
	private ArrayList<String> implementedInterfaces = new ArrayList<String>();
	private ArrayList<String> storedInterfaces = new ArrayList<String>();
	private String compositeName = null;
	

	public CompositeCompositeVisitor(int api, HashMap<String, String> compositeMap, ArrayList<String> interfaces) {
		super(api);
		this.compositeMap = compositeMap;
		this.allInterfaces = interfaces;
	}

	public CompositeCompositeVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			this.compositeName = name;
			for(String interfaceName : interfaces){
				if(this.allInterfaces.contains(interfaceName)) {
					implementedInterfaces.add(interfaceName);
					if(storedInterfaces.contains(interfaceName))
						compositeMap.put(name.replace("/", "."), interfaceName.replace("/", "."));
				}
			}
			if(this.allInterfaces.contains(superName)) {
				implementedInterfaces.add(superName);
				if(storedInterfaces.contains(superName))
					compositeMap.put(name.replace("/", "."), superName.replace("/", "."));
			}
		
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		for(String interfaceName : this.allInterfaces){
			if(signature != null && signature.contains("<L"+interfaceName+";>")){
				this.storedInterfaces.add(interfaceName);
				if(implementedInterfaces.contains(interfaceName))
					compositeMap.put(this.compositeName.replace("/", "."), interfaceName.replace("/", "."));
			}
		}
		return toDecorate;

	}

}
