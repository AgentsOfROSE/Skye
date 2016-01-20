package umlParser;

import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class SequenceClassMethodVisitor extends ClassVisitor {

	SequenceDiagramInfo info;
	String className;
	String methodName;
	int depth;
	ArrayList<String> parameters = new ArrayList<String>();

	public SequenceClassMethodVisitor(int arg0, SequenceDiagramInfo info, String className, String methodName, ArrayList<String> parameters, int depth) {
		super(arg0);
		this.info = info;
		this.className = className;
		this.methodName = methodName;
		this.depth = depth;
		this.parameters = parameters;
		for(String param : this.parameters){
			if(param.contains("<")){
				this.parameters.remove(param);
				this.parameters.add(param.substring(0, param.indexOf("<")));
			}
		}
		
	}

	public SequenceClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		String[] methodParams = desc.substring(desc.indexOf("(") + 1, desc.indexOf(")")).split(";");
		ArrayList<String> methodParameters = new ArrayList<String>();
		if(name.equals(methodName)){
			for(String param : methodParams){
				if(!param.equals("")){
					if(param.contains("java")){
						methodParameters.add(param.substring(1).replace("/", "."));
					} else {
						methodParameters.add(param.replace("/", "."));
					}
				}
			}
			if(methodParameters.equals(parameters)){
				toDecorate = new MethodSequenceVisitor(Opcodes.ASM5, info, depth, className, toDecorate);
			}
		}
		return toDecorate;
	}

}
