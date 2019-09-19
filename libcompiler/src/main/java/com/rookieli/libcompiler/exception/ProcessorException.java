package com.rookieli.libcompiler.exception;

import javax.lang.model.element.Element;

/**
 * @data: 2019/9/19 0019
 * @author: liyong
 * @desc: class description
 */
public class ProcessorException extends RuntimeException {
	private Element element;

	public ProcessorException(Element element, String s, Object... args) {
		super(String.format(s, args));
		this.element = element;
	}

	public Element getElement() {
		return element;
	}
}
