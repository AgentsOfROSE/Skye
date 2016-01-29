package umlParser;

import java.io.IOException;
import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class SingletonDetector implements PatternDetector {

	@Override
	public String detect(String[] classes) throws IOException {
		ArrayList<String> singletons = new ArrayList<String>();
		for(String className : classes){
			ClassReader reader = new ClassReader(className);
			ClassVisitor singletonVisitor = new SingletonVisitor(Opcodes.ASM5, singletons, className);
			reader.accept(singletonVisitor, ClassReader.EXPAND_FRAMES);
		}
		String result = "Singleton-";
		for(String singleton : singletons){
			result = result + "Singleton:"+singleton+";";
		}
		return result + "~";
	}

}
