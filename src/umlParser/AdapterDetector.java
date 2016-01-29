package umlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class AdapterDetector implements PatternDetector {


	@Override
	public String detect(String[] classes) throws IOException {
		ArrayList<String> interfaceList = new ArrayList<String>();
		HashMap<String, String> possibleAdaptersMap = new HashMap<String, String>();
		HashMap<String, ArrayList<String>> possibleAdaptees = new HashMap<String, ArrayList<String>>();
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor adapterTargetVisitor = new AdapterTargetVisitor(Opcodes.ASM5, interfaceList);
			reader.accept(adapterTargetVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor adapterAdapterVisitor = new AdapterAdapterVisitor(Opcodes.ASM5, possibleAdaptersMap, interfaceList);
			reader.accept(adapterAdapterVisitor, ClassReader.EXPAND_FRAMES);
		}
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor adapterAdapteeVisitor = new AdapterAdapteeVisitor(Opcodes.ASM5, possibleAdaptees, className.replace("/", "."));
			reader.accept(adapterAdapteeVisitor, ClassReader.EXPAND_FRAMES);
		}
		
		String returnString = "Adapter-";
		
		for(String possibleAdapter : possibleAdaptees.keySet()){
			if(possibleAdaptersMap.keySet().contains(possibleAdapter)){
				returnString = returnString + "Adapter:" + possibleAdapter + ";";
				String target = possibleAdaptersMap.get(possibleAdapter);
				returnString = returnString + "Target:" + target + ";";
				for(String adaptee : possibleAdaptees.get(possibleAdapter)){
					returnString = returnString + "Adaptee:" + adaptee + ";";
				}
			}
		}
		return returnString + "~";
	}

}
