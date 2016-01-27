package umlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLDecoratorParser extends AbstractUMLParser{

	public UMLDecoratorParser(UMLParsable parser) {
		super(parser);
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		/* START CHANGES HERE */
		ArrayList<String> interfaceList = new ArrayList<String>();
		HashMap<String, String> abstractMap = new HashMap<String, String>();
		ArrayList<String> concreteDecoratorList = new ArrayList<String>();
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorInterfaceVisitor = new DecoratorInterfaceVisitor(Opcodes.ASM5, interfaceList);
			reader.accept(decoratorInterfaceVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorAbstractVisitor = new DecoratorAbstractVisitor(Opcodes.ASM5, abstractMap, interfaceList);
			reader.accept(decoratorAbstractVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor decoratorConcreteVisitor = new DecoratorConcreteVisitor(Opcodes.ASM5, abstractMap.keySet(), concreteDecoratorList);
			reader.accept(decoratorConcreteVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String abstractName : abstractMap.keySet()){
			 ClassInfo abstractInfo = this.getClasses().get(this.getClassListFull().indexOf(abstractName));
			 abstractInfo.getAnnotations().add("Decorator");
			 abstractInfo.getPatterns().add("Decorator");
			 abstractInfo.setClassFillColor("green");
			 ClassInfo interfaceInfo = this.getClasses().get(this.getClassListFull().indexOf(abstractMap.get(abstractName))); 
			 if(!interfaceInfo.getPatterns().contains("Decorator")){
				 interfaceInfo.getAnnotations().add("Component");
				 interfaceInfo.getPatterns().add("Decorator");
				 interfaceInfo.setClassFillColor("green");
			 }
		}
		for(String concreteName : concreteDecoratorList){
			 ClassInfo concreteInfo = this.getClasses().get(this.getClassListFull().indexOf(concreteName));
			 concreteInfo.getAnnotations().add("Decorator");
			 concreteInfo.getPatterns().add("Decorator");
			 concreteInfo.setClassFillColor("green");
		}
		/* END CHANGES HERE */
		this.parser.parse(args);
	}
	

}
