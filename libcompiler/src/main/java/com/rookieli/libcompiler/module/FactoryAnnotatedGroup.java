package com.rookieli.libcompiler.module;

import com.rookieli.libcompiler.exception.ProcessorException;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * @data: 2019/9/16 0016
 * @author: liyong
 * @desc: class description
 */
public class FactoryAnnotatedGroup {

	public static final String SUFFIX = "Factory";
	private String groupName;
	private Map<String, FactoryAnnotatedClass> groupClass = new LinkedHashMap<>();

	public FactoryAnnotatedGroup(String groupName) {
		this.groupName = groupName;
	}

	public void add(FactoryAnnotatedClass factoryAnnotatedClass) {
		if (groupClass.get(factoryAnnotatedClass.getShapeName()) != null) {
			throw new ProcessorException(factoryAnnotatedClass.getTypeElement(), "shape() = %s have bean used", factoryAnnotatedClass.getShapeName());
		}
		groupClass.put(factoryAnnotatedClass.getShapeName(), factoryAnnotatedClass);
	}

	public void generateCode(Elements elementUtil, Filer filer) throws IOException {
		TypeElement typeElement = elementUtil.getTypeElement(groupName);
		PackageElement packageElement = elementUtil.getPackageOf(typeElement);

		MethodSpec.Builder methodSpec = MethodSpec.methodBuilder("create")
				.addModifiers(Modifier.PUBLIC)
				.addParameter(String.class, "shape")
				.returns(TypeName.get(elementUtil.getTypeElement(groupName).asType()));

		methodSpec.beginControlFlow("if (shape == null)")
				.addStatement("throw new $T($S)", IllegalArgumentException.class, "shape is null!")
				.endControlFlow();

		for (FactoryAnnotatedClass factoryAnnotatedClass : groupClass.values()) {
			ClassName clz = ClassName.get(factoryAnnotatedClass.getTypeElement());
			methodSpec.beginControlFlow("if ($S.equals(shape))", factoryAnnotatedClass.getShapeName())
					.addStatement("return new $T()", clz)
					.endControlFlow();
		}

		methodSpec.addStatement("throw new IllegalArgumentException($S + shape)", "Unknown shape, shape = ");

		TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName() + SUFFIX)
				.addMethod(methodSpec.build())
				.addModifiers(Modifier.PUBLIC)
				.build();

		if (packageElement.isUnnamed()) {
			throw new ProcessorException(typeElement, "%s unpackaged by specified package", typeElement.getSimpleName());
		}

		String packageName = packageElement.getQualifiedName().toString();
		JavaFile.builder(packageName, typeSpec)
				.build()
				.writeTo(filer);
	}


}
