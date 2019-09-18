package com.rookieli.libcompiler.exception;

import javax.lang.model.element.Element;

/**
 * @data: 2019/9/16 0016
 * @author: liyong
 * @desc: class description
 */
public class IllegalAnnotateElementTypeException extends RuntimeException {
	private Element element;

	public IllegalAnnotateElementTypeException(Element element, String msg, Object... args) {
		super(String.format(msg, args));
	}

	public void setElement(Element element) {
		this.element = element;
	}
}
