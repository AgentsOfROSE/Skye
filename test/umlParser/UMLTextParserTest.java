package umlParser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class UMLTextParserTest {

	private String[] classes = new String[] { "Person", "Student", "RoseHulmanStudent" };
	private String PUBLIC_ACCESS = "+";
	private String PRIVATE_ACCESS = "-";
	private String PROTECTED_ACCESS = "#";
	private String DEFAULT_ACCESS = " ";

	@Test
	public void extendsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.RoseHulmanStudent");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, info);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getExtendedClass().equals("Student"));
		assertTrue(info.getImplementedClasses().size() == 0);
	}

	@Test
	public void extendsNotFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, info);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getExtendedClass().equals("Object"));
	}

	@Test
	public void implementsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, info);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getImplementedClasses().contains("Person"));
	}

	@Test
	public void implementsNotFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.RoseHulmanStudent");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, info);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getImplementedClasses().size() == 0);
	}

	@Test
	public void privateMethodsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, info);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		System.out.println(info.getMethods().stream().map(methodInfo -> methodInfo.getName()).toString());
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("ageUp"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("ageDown"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("age"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("hug"))
				.toArray().length == 0);
		assertTrue(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("ageUp") && methodInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("ageUp") && methodInfo.getAccess().equals(DEFAULT_ACCESS))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("ageDown") && methodInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("ageDown") && methodInfo.getAccess().equals(DEFAULT_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("age") && methodInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);

	}

	/*
	 * public static void main(String[] args) throws IOException {
	 * ArrayList<ClassInfo> classes = new ArrayList<ClassInfo>(); for (String
	 * className : args) { ClassInfo info = new ClassInfo(); classes.add(info);
	 * ClassReader reader = new ClassReader(className);
	 * 
	 * ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,
	 * info); ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
	 * info); ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
	 * info);
	 * 
	 * reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
	 * reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
	 * reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES); }
	 */

}
