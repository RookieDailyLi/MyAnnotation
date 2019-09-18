package com.rookieli.libcompiler;

import com.google.auto.service.AutoService;
import com.rookieli.libannotation.Factory;
import com.rookieli.libcompiler.exception.IllegalAnnotateElementTypeException;
import com.rookieli.libcompiler.module.FactoryAnnotatedClass;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class FactoryProcessor extends AbstractProcessor {
	private Elements elementUtil;
	private Filer filer;
	private Types typeUtil;
	private Messager messager;
	private Map<String, FactoryAnnotatedClass> annotatedClassGroup = new LinkedHashMap<>();

	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {
		this.elementUtil = processingEnvironment.getElementUtils();
		this.filer = processingEnvironment.getFiler();
		this.typeUtil = processingEnvironment.getTypeUtils();
		this.messager = processingEnvironment.getMessager();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		LinkedHashSet hashSet = new LinkedHashSet();
		hashSet.add(Factory.class.getCanonicalName());
		return hashSet;
	}

	@Override
	public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

		for (Element element : roundEnvironment.getElementsAnnotatedWith(Factory.class)) {
			messager.printMessage(Diagnostic.Kind.NOTE, "element = " + element.getSimpleName());
			FactoryAnnotatedClass factoryAnnotatedClass = new FactoryAnnotatedClass(elementUtil,typeUtil,element);
		}
		return true;
	}


	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}
