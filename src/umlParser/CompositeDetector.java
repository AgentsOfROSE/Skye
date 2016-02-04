package umlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CompositeDetector implements PatternDetector {

	@Override
	public String detect(String[] classes) throws IOException {
		ArrayList<String> abstractList = new ArrayList<String>();
		HashMap<String, String> compositeMap = new HashMap<String, String>();
		Set<String> leaves = new HashSet<String>();
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorInterfaceVisitor = new CompositeComponentVisitor(Opcodes.ASM5, abstractList);
			reader.accept(decoratorInterfaceVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorAbstractVisitor = new CompositeCompositeVisitor(Opcodes.ASM5, compositeMap, abstractList);
			reader.accept(decoratorAbstractVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorConcreteVisitor = new CompositeLeafVisitor(Opcodes.ASM5, new HashSet<String>(compositeMap.values()), compositeMap.keySet(), leaves);
			reader.accept(decoratorConcreteVisitor, ClassReader.EXPAND_FRAMES);
		}
		String returnString = "Composite-";
		for(String componentName : new HashSet<String>(compositeMap.values())){
			 returnString = returnString +"Component:"+componentName+";";
		}
		for(String composite : compositeMap.keySet()){
			returnString = returnString +"Composite:"+composite+";";
		}
		for(String leaf : leaves){
			 returnString = returnString +"Leaf:"+leaf+";";
		}
		return returnString + "~";
	}

}
