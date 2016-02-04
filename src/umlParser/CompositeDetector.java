package umlParser;

import java.io.IOException;
import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CompositeDetector implements PatternDetector {

	@Override
	public String detect(String[] classes) throws IOException {
		ArrayList<String> abstractList = new ArrayList<String>();

		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorInterfaceVisitor = new CompositeComponentVisitor(Opcodes.ASM5, abstractList);
			reader.accept(decoratorInterfaceVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorAbstractVisitor = new CompositeCompositeVisitor(Opcodes.ASM5, abstractMap, abstractList);
			reader.accept(decoratorAbstractVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorConcreteVisitor = new CompositeLeafVisitor(Opcodes.ASM5, abstractMap.keySet(), concreteDecoratorList);
			reader.accept(decoratorConcreteVisitor, ClassReader.EXPAND_FRAMES);
		}
		String returnString = "Composite-";
		return null;
	}

}
