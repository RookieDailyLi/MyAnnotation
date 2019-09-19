package com.rookieli.libcompiler.module;

import com.rookieli.libannotation.Factory;
import com.rookieli.libcompiler.exception.IllegalAnnotateElementTypeException;
import com.rookieli.libcompiler.exception.ProcessorException;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @data: 2019/9/16 0016
 * @author: liyong
 * @desc: class description
 */
public class FactoryAnnotatedClass {
	private Elements elementUtil;
	private Types typeUtil;
	private TypeElement typeElement;
	private String qualifiedGroupName;
	private String simpleGroupName;
	private String shapeName;

	public FactoryAnnotatedClass(Elements elementUtil, Types typeUtil, Element element) {
		this.elementUtil = elementUtil;
		this.typeUtil = typeUtil;
		checkElementValid(element);
	}

	private void checkElementValid(Element element) {
		//排除接口、注解、枚举类型
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalAnnotateElementTypeException(typeElement, "Only class can be annotated by @%s", Factory.class.getSimpleName());
		}
		this.typeElement = (TypeElement) element;

		//Factory注解的shape()不能为空
		Factory factory = typeElement.getAnnotation(Factory.class);
		shapeName = factory.shape();
		if (shapeName.length() == 0) {
			throw new IllegalArgumentException(String.format("@Factory annotation,shape() must not be empty"));
		}


		try {
			Class cls = factory.type();
			qualifiedGroupName = cls.getCanonicalName();
			simpleGroupName = cls.getSimpleName();
		} catch (MirroredTypeException mirrorTypeException) {
			DeclaredType declaredType = (DeclaredType) mirrorTypeException.getTypeMirror();
			TypeElement typeElement = (TypeElement) declaredType.asElement();
			qualifiedGroupName = typeElement.getQualifiedName().toString();
			simpleGroupName = typeElement.getSimpleName().toString();
		}

		//Factory.type()如果为接口Class类型，那么typeElement也必须实现该接口，
		TypeElement superTypeElement = elementUtil.getTypeElement(qualifiedGroupName);
		if (superTypeElement.getKind() == ElementKind.INTERFACE) {
			List superInterfaces = typeElement.getInterfaces();
			if (!superInterfaces.contains(superTypeElement.asType())) {
				throw new ProcessorException(typeElement,
						"The class %s annotated with @%s must implement the interface %s",
						typeElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
						qualifiedGroupName);
			}
		} else {    //如果Factory.type()为类Class类型，那么typeElement也必需直接或者间接继承该类
			TypeElement currentTypeElement = typeElement;
			while (true) {    //循环找其父类看父类是否包含注解的type()值
				TypeMirror superTypeMirror = currentTypeElement.getSuperclass();
				if (superTypeMirror.getKind() == TypeKind.NONE) {
					throw new ProcessorException(typeElement
							, "The class %s annotated with @%s must inherit from %s"
							, typeElement.getQualifiedName().toString()
							, Factory.class.getSimpleName()
							, qualifiedGroupName);
				}

				String qualified = ((TypeElement) typeUtil.asElement(superTypeMirror)).getQualifiedName().toString();
				if (qualified.equals(qualifiedGroupName)){
					break;
				}

				//父类型赋值为当前类型继续往上找
				currentTypeElement = (TypeElement) typeUtil.asElement(superTypeMirror);
			}
		}

		//被Factory注解的类必须为public
		if (!typeElement.getModifiers().contains(Modifier.PUBLIC)) {
			throw new ProcessorException(typeElement, "The class %s is not public.",
					typeElement.getQualifiedName().toString());
		}

		//被Factory注解的类必须非abstract
		if (!typeElement.getModifiers().contains(Modifier.PUBLIC)) {
			throw new ProcessorException(typeElement, "The class %s must not abstract.",
					typeElement.getQualifiedName().toString());
		}

		//被Factory注解的类必须有public的构造方法
		List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
		boolean hasPublicConstruct = false;
		for (Element enclosedElement : enclosedElements) {
			if (enclosedElement.getKind() == ElementKind.CONSTRUCTOR) {
				if (enclosedElement.getModifiers().contains(Modifier.PUBLIC)) {
					hasPublicConstruct = true;
					break;
				}
			}
		}

		if (!hasPublicConstruct) {
			throw new ProcessorException(typeElement, "The class %s is must have a public construct",
					typeElement.getQualifiedName().toString());
		}

	}
}
