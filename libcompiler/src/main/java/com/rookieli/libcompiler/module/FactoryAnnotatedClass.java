package com.rookieli.libcompiler.module;

import com.rookieli.libannotation.Factory;
import com.rookieli.libcompiler.exception.IllegalAnnotateElementTypeException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
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
        } catch (MirroredTypeException mirrorTypeException) {
            TypeMirror typeMirror = mirrorTypeException.getTypeMirror();
        }

        TypeMirror superTypeMirror = typeElement.getSuperclass();
//        while (true) {
//
//        }


        //被Factory注解的类必须有public的构造方法

    }
}
