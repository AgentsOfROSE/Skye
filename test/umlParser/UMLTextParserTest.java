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
		ClassVisitor extVisitor = new ClassExtensionVisitor(Opcodes.ASM5, info);
		reader.accept(extVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getExtendedClass().equals("Student"));
		assertTrue(info.getImplementedClasses().size() == 0);
	}

	@Test
	public void extendsNotFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, info);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getExtendedClass()==null);
	}

	@Test
	public void implementsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor implVisitor = new ClassImplementationVisitor(Opcodes.ASM5, info);
		reader.accept(implVisitor, ClassReader.EXPAND_FRAMES);
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
	
	@Test
	public void publicMethodsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, info);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("ageUp"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("age"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("laugh"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("hug"))
				.toArray().length == 0);
		assertFalse(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("ageUp") && methodInfo.getAccess().equals(PUBLIC_ACCESS))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("age") && methodInfo.getAccess().equals(PUBLIC_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("age") && methodInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);
	}
	
	@Test
	public void protectedMethodsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, info);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("ageUp"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("sleep"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("laugh"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("hug"))
				.toArray().length == 0);
		assertTrue(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("sleep") && methodInfo.getAccess().equals(PROTECTED_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("sleep") && methodInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);
	}
	
	@Test
	public void defaultMethodsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, info);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("ageUp"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("sleep"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("laugh"))
				.toArray().length > 0);
		assertTrue(info.getMethods().stream().filter(methodInfo -> methodInfo.getName().equals("hug"))
				.toArray().length == 0);
		assertTrue(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("laugh") && methodInfo.getAccess().equals(DEFAULT_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getMethods().stream().filter(
				methodInfo -> methodInfo.getName().equals("laugh") && methodInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);
	}
	
	@Test
	public void allMethodsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, info);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getMethods().size() == 9);
	}
	
	@Test
	public void privateFieldsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, info);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("age"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("height"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("gender"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("name"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("age") && fieldInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("height") && fieldInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("gender") && fieldInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("name") && fieldInfo.getAccess().equals(PRIVATE_ACCESS))
				.toArray().length > 0);
	}
	
	@Test
	public void publicFieldsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, info);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("age"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("height"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("gender"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("name"))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("age") && fieldInfo.getAccess().equals(PUBLIC_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("height") && fieldInfo.getAccess().equals(PUBLIC_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("gender") && fieldInfo.getAccess().equals(PUBLIC_ACCESS))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("name") && fieldInfo.getAccess().equals(PUBLIC_ACCESS))
				.toArray().length > 0);
	}
	
	@Test
	public void protectedFieldsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, info);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("age"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("height"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("gender"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("name"))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("age") && fieldInfo.getAccess().equals(PROTECTED_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("height") && fieldInfo.getAccess().equals(PROTECTED_ACCESS))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("gender") && fieldInfo.getAccess().equals(PROTECTED_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("name") && fieldInfo.getAccess().equals(PROTECTED_ACCESS))
				.toArray().length > 0);
	}
	
	@Test
	public void defaultFieldsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, info);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("age"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("height"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("gender"))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("name"))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("age") && fieldInfo.getAccess().equals(DEFAULT_ACCESS))
				.toArray().length > 0);
		assertTrue(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("height") && fieldInfo.getAccess().equals(DEFAULT_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("gender") && fieldInfo.getAccess().equals(DEFAULT_ACCESS))
				.toArray().length > 0);
		assertFalse(info.getFields().stream().filter(fieldInfo -> fieldInfo.getName().equals("name") && fieldInfo.getAccess().equals(DEFAULT_ACCESS))
				.toArray().length > 0);
	}
	
	@Test
	public void allFieldsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, info);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getFields().size() == 4);
	}
	
	@Test
	public void noFieldsFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.RoseHulmanStudent");
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, info);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getFields().size() == 0);
	}
	
	@Test
	public void usesFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor declVisitor = new ClassMethodVisitor(Opcodes.ASM5, info);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getUsedClasses().contains("int"));
		assertTrue(info.getUsedClasses().size() > 0);
	}
	
	@Test
	public void usesNotFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.RoseHulmanStudent");
		ClassVisitor declVisitor = new ClassMethodVisitor(Opcodes.ASM5, info);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		assertFalse(info.getUsedClasses().contains("String"));
		assertTrue(info.getUsedClasses().size() == 0);
	}
	
	@Test
	public void associateFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.Student");
		ClassVisitor declVisitor = new ClassFieldVisitor(Opcodes.ASM5, info);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		assertTrue(info.getAssociatedClasses().contains("String"));
		assertTrue(info.getAssociatedClasses().size() == 3);
	}
	
	@Test
	public void associateNotFoundTest() throws IOException {
		ClassInfo info = new ClassInfo();
		ClassReader reader = new ClassReader("umlParser.RoseHulmanStudent");
		ClassVisitor declVisitor = new ClassFieldVisitor(Opcodes.ASM5, info);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		assertFalse(info.getAssociatedClasses().contains("int"));
		assertTrue(info.getAssociatedClasses().size() == 0);
	}
}
