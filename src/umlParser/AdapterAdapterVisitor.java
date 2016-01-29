package umlParser;


import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class AdapterAdapterVisitor extends ClassVisitor {
	
	private HashMap<String, String> classMap;
	private ArrayList<String> allInterfaces;
	

	public AdapterAdapterVisitor(int api, HashMap<String, String> classMap, ArrayList<String> interfaces) {
		super(api);
		this.classMap = classMap;
		this.allInterfaces = interfaces;
	}

	public AdapterAdapterVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			for(String interfaceName : interfaces){
				if(this.allInterfaces.contains(interfaceName)) {
					classMap.put(name.replace("/", "."), interfaceName.replace("/", "."));
				}
			}
	}

}