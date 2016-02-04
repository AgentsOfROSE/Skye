package umlParser;

import java.util.ArrayList;
import java.util.Set;

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class CompositeLeafVisitor extends ClassVisitor {

	public CompositeLeafVisitor(int api, Set<String> abstracts, ArrayList<String> concreteClasses) {
		super(api);
	}

	public CompositeLeafVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

}
