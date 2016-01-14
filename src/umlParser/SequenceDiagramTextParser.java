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
		String[] params = args[2].substring(1, args[2].length() - 1).split(" ");
		ArrayList<String> parameters = new ArrayList<String>();
		for (int i = 0; i < params.length; i++) {
			parameters.add(params[i]);
		}

		ClassReader reader = new ClassReader(className);
		SequenceDiagramInfo info = new SequenceDiagramInfo();
		if (args.length == 4) {
			info.setMaxDepth(Integer.parseInt(args[3]));
		}
		info.setPackageName(className.substring(0, className.lastIndexOf(".") + 1));
		System.out.println(className.substring(className.lastIndexOf(".") + 1) + ":"
				+ className.substring(className.lastIndexOf(".") + 1));
		for (int i = 0; i < params.length; i++) {
			if (parameters.get(i).contains(info.getPackageName())) {
				System.out.println(params[i].contains("<")
						? parameters.get(i).substring(parameters.get(i).lastIndexOf(".") + 1,
								parameters.get(i).lastIndexOf("<"))
						+ ":"
						+ parameters.get(i).substring(parameters.get(i).lastIndexOf(".") + 1,
								parameters.get(i).lastIndexOf("<"))
						: parameters.get(i).substring(parameters.get(i).lastIndexOf(".") + 1) + ":"
								+ parameters.get(i).substring(parameters.get(i).lastIndexOf(".") + 1));
			}
		}
		ClassVisitor methodVisitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, className, methodName, 1);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

		ArrayList<String> statics = new ArrayList<String>();
		for (MessageInfo message : info.getMessages()) {
			if (!info.getObjects().contains(message.getCallee())
					&& !message.getCallee().equals(className.substring(className.lastIndexOf(".") + 1))
					&& !statics.contains(message.getCallee()) && (parameters.stream()
							.filter(param -> param.contains(message.getCallee())).toArray().length == 0)) {
				statics.add(message.getCallee());
				System.out.println(message.getCallee() + ":" + message.getCallee());
			}
		}

		for (String object : info.getObjects()) {
			System.out.println("/" + object + ":" + object);
		}
		System.out.println("");

		for (MessageInfo message : info.getMessages()) {
			System.out.println(message.getCaller() + ":" + message.getAnswer() + "=" + message.getCallee() + "."
					+ message.getMessage());
		}
	}

}
