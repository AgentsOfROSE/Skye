package umlParser;


import java.util.ArrayList;
import java.util.Set;

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class DecoratorConcreteVisitor extends ClassVisitor {
	
	private Set<String> abstracts;
	private ArrayList<String> concreteClasses;

	public DecoratorConcreteVisitor(int api, Set<String> abstracts, ArrayList<String> concreteClasses) {
		super(api);
		this.abstracts = abstracts;
		this.concreteClasses = concreteClasses;
	}

	public DecoratorConcreteVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if(this.abstracts.contains(superName.replace("/", ".")))
			this.concreteClasses.add(name.replace("/", "."));
	}

}