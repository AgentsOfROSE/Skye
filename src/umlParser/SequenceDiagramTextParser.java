package umlParser;

import java.io.IOException;
import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class SequenceDiagramTextParser implements Parsable {

	@Override
	public void parse(String[] args) throws IOException {
		String className = args[0];
		String methodName = args[1];
		String[] params = args[2].substring(1, args[2].length()).split(" ");
		int maxDepth = 5;
		if (args.length == 4) {
			maxDepth = Integer.parseInt(args[3]);
		}
		
		ClassReader reader = new ClassReader(className);
		int depth = 1;
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		info.setPackageName(className.substring(0, className.lastIndexOf(".")+1));
		System.out.println(className.substring(className.lastIndexOf(".")+1)+":"+className.substring(className.lastIndexOf(".")+1));
		ClassVisitor methodVisitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info,  className, methodName, depth);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		//info.getMessages().stream().forEach(message -> System.out.println("Caller: " + message.getCaller() + "\nCallee: " + message.getCallee() + "\nMessage: " + message.getMessage() + "\nAnswer: " + message.getAnswer()));
	
		
		for(String object : info.getObjects()){
			System.out.println("/" + object + ":" + object);
		}
		System.out.println("");
		
		for(MessageInfo message : info.getMessages()){
			System.out.println(message.getCaller()+":"+message.getAnswer()+"="+message.getCallee()+"."+message.getMessage());
		}
	}

}
