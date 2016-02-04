package umlParser;

import java.util.ArrayList;
import java.util.Set;

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class CompositeLeafVisitor extends ClassVisitor {

	Set<String> components;
	Set<String> composites;
	Set<String> leaves;
	
	public CompositeLeafVisitor(int api, Set<String> components, Set<String> composites, Set<String> leaves) {
		super(api);
		this.components = components;
		this.composites = composites;
		this.leaves = leaves;
	}

	public CompositeLeafVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		for(String interfaceName : interfaces){
			if((components.contains(interfaceName.replace("/", ".")) || composites.contains(interfaceName.replace("/", "."))) && !composites.contains(name.replace("/", "."))){
				leaves.add(name.replace("/", "."));
			}
		}
		if((components.contains(superName.replace("/", ".")) || composites.contains(superName.replace("/", "."))) && !composites.contains(name.replace("/", "."))){
			leaves.add(name.replace("/", "."));
		}
	}
	
}
