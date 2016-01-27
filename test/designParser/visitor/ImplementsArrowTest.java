package designParser.visitor;

import java.io.IOException;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import designParser.asm.visitor.ClassDeclarationVisitor;
import designParser.asm.visitor.ClassFieldVisitor;
import designParser.asm.visitor.ClassMethodVisitor;
import designParser.asm.visitor.ModelBuilderClassVisitor;
import designParser.markupGen.api.MarkupGenerator;
import designParser.markupGen.impl.UmlGenerator;
import designParser.model.api.IDesignModel;
import designParser.model.impl.DesignModel;

public class ImplementsArrowTest {

	private final static String[] OBJECT_NAMES = { "appLauncherSltn.DirectoryMonitorService",
			"appLauncherSltn.IDirectoryMonitorService" };

	@Test
	public void test() throws IOException {
		IDesignModel designModel = new DesignModel(OBJECT_NAMES);
		for (String className : OBJECT_NAMES) {
			ClassReader reader = new ClassReader(className);

			ModelBuilderClassVisitor decVisitor;
			decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, designModel);
			ModelBuilderClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor);
			ModelBuilderClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			designModel = methodVisitor.getModel();
		}

		MarkupGenerator umlGenerator = new UmlGenerator("TestImplementsArrow", designModel);
		System.out.println(umlGenerator.getMarkup());
	}

}
