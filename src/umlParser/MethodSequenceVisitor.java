package umlParser;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodSequenceVisitor extends MethodVisitor {

	private SequenceDiagramInfo info;
	private String className;
	private int depth;

	public MethodSequenceVisitor(int arg0) {
		super(arg0);
	}

	public MethodSequenceVisitor(int arg0, SequenceDiagramInfo info, int depth, String className, MethodVisitor arg1) {
		super(arg0, arg1);
		this.info = info;
		this.className = className;
		this.depth = depth;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		String returnType = Type.getReturnType(desc).getClassName()
				.substring(Type.getReturnType(desc).getClassName().lastIndexOf(".") + 1);
		if (owner.replace("/", ".").contains(info.getPackageName()))
			if (opcode != 183) {
				String[] methodParams = desc.substring(desc.indexOf("(") + 1, desc.indexOf(")")).split(";");
				ArrayList<String> methodParameters = new ArrayList<String>();
				for(String param : methodParams){
					if(!param.equals("")){
						if(param.contains("java")){
							methodParameters.add(param.substring(1).replace("/", "."));
						} else {
							methodParameters.add(param.replace("/", "."));
						}
					}
				}
				MessageInfo message = new MessageInfo(this.depth, this.className.substring(this.className.lastIndexOf(".") + 1),
						owner.substring(owner.lastIndexOf("/") + 1),
						returnType.equals("void") ? "" : returnType, name, methodParameters);
				info.getMessages()
				.add(message);
				if(depth < info.getMaxDepth()){
					ClassReader reader;
					try {
						reader = new ClassReader(owner);
						ClassVisitor methodVisitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, message.getCallee(), message.getMessage(), methodParameters, depth + 1);
						reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(Type.getReturnType(desc).getClassName().contains(info.getPackageName()) && !info.getObjects().contains(returnType)){
					info.getObjects().add(returnType);
					message = new MessageInfo(this.depth, this.className.substring(this.className.lastIndexOf(".") + 1), returnType, "", "new", new ArrayList<String>());
					info.getMessages().add(message);
					if(depth < info.getMaxDepth()){
						ClassReader reader;
						try {
							reader = new ClassReader(Type.getReturnType(desc).getClassName().replace("/", "."));
							ClassVisitor methodVisitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, message.getCallee(), message.getMessage(), new ArrayList<String>(), depth + 1);
							reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		if (type.replaceAll("/", ".").contains(info.getPackageName())) {
			if (!info.getObjects().contains(type.substring(type.lastIndexOf("/") + 1))) {
				info.getObjects().add(type.substring(type.lastIndexOf("/") + 1));
				MessageInfo message = new MessageInfo(this.depth, this.className.substring(this.className.lastIndexOf(".") + 1),
						type.substring(type.lastIndexOf("/") + 1), "", "new", new ArrayList<String>());
				info.getMessages()
						.add(message);
				if(depth < info.getMaxDepth()){
					ClassReader reader;
					try {
						reader = new ClassReader(type);
						ClassVisitor methodVisitor = new SequenceClassMethodVisitor(Opcodes.ASM5, info, message.getCallee(), message.getMessage(), new ArrayList<String>(), depth + 1);
						reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
