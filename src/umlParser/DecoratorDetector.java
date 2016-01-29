package umlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class DecoratorDetector implements PatternDetector {


	@Override
	public String detect(String[] classes) throws IOException {
		ArrayList<String> interfaceList = new ArrayList<String>();
		HashMap<String, String> abstractMap = new HashMap<String, String>();
		ArrayList<String> concreteDecoratorList = new ArrayList<String>();
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorInterfaceVisitor = new DecoratorInterfaceVisitor(Opcodes.ASM5, interfaceList);
			reader.accept(decoratorInterfaceVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorAbstractVisitor = new DecoratorAbstractVisitor(Opcodes.ASM5, abstractMap, interfaceList);
			reader.accept(decoratorAbstractVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorConcreteVisitor = new DecoratorConcreteVisitor(Opcodes.ASM5, abstractMap.keySet(), concreteDecoratorList);
			reader.accept(decoratorConcreteVisitor, ClassReader.EXPAND_FRAMES);
		}
		String returnString = "Decorator-";
		
		for(String abstractName : abstractMap.keySet()){
			 returnString = returnString +"Decorator:"+abstractName+";";
			 returnString = returnString +"Component:"+abstractMap.get(abstractName)+";";
		}
		for(String concreteName : concreteDecoratorList){
			 returnString = returnString +"Decorator:"+concreteName+";";
		}
		return returnString + "~";
	}

}
