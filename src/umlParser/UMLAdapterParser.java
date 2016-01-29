package umlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLAdapterParser extends AbstractUMLParser{

	public UMLAdapterParser(UMLParsable parser) {
		super(parser);
	}
	
	public void parse(String[] args) throws IOException {
		this.parser.setClasses(this.getClasses());
		this.parser.setClassListAbbreviated(this.getClassListAbbreviated());
		this.parser.setClassListFull(this.getClassListFull());
		ArrayList<String> interfaceList = new ArrayList<String>();
		HashMap<String, String> possibleAdaptersMap = new HashMap<String, String>();
		HashMap<String, ArrayList<String>> possibleAdaptees = new HashMap<String, ArrayList<String>>();
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor adapterTargetVisitor = new AdapterTargetVisitor(Opcodes.ASM5, interfaceList);
			reader.accept(adapterTargetVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor adapterAdapterVisitor = new AdapterAdapterVisitor(Opcodes.ASM5, possibleAdaptersMap, interfaceList);
			reader.accept(adapterAdapterVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : this.parser.getClassListFull()){
			ClassReader reader = new ClassReader(className);
			ClassVisitor adapterAdapteeVisitor = new AdapterAdapteeVisitor(Opcodes.ASM5, possibleAdaptees, className.replace("/", "."));
			reader.accept(adapterAdapteeVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String possibleAdapter : possibleAdaptees.keySet()){
			if(possibleAdaptersMap.keySet().contains(possibleAdapter)){
				ClassInfo adapterInfo = this.getClasses().get(this.getClassListFull().indexOf(possibleAdapter));
				adapterInfo.getPatterns().add("Adapter");
				adapterInfo.getAnnotations().add("Adapter");
				adapterInfo.setClassFillColor("firebrick");
				for(String adaptee : possibleAdaptees.get(possibleAdapter)){
					String target = possibleAdaptersMap.get(possibleAdapter);
					ClassInfo targetInfo = this.getClasses().get(this.getClassListFull().indexOf(target));
					ClassInfo adapteeInfo = this.getClasses().get(this.getClassListAbbreviated().indexOf(adaptee));
					targetInfo.getAnnotations().add("Target");
					adapteeInfo.getAnnotations().add("Adaptee");
					targetInfo.getPatterns().add("Adapter");
					adapteeInfo.getPatterns().add("Adapter");
					targetInfo.setClassFillColor("firebrick");
					adapteeInfo.setClassFillColor("firebrick");
				}
			}
		}
		
		this.parser.parse(args);
	}
	

}
