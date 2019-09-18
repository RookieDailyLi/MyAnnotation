package com.rookieli.myannotation.factory;

import com.rookieli.myannotation.shape.Circle;
import com.rookieli.myannotation.shape.Shape;
import com.rookieli.myannotation.shape.Triangle;

/**
 * 时间: 2019/9/15 0015
 * 作者: liyong
 * 描述: class description
 */
public class ShapeFactory {
	public Shape create(String shape) {
		if (shape == null) {
			throw new IllegalArgumentException("shape is null!");
		}
		if ("Circle".equals(shape)) {
			return new Circle();
		}

		if ("Triangle".equals(shape)) {
			return new Triangle();
		}
		if ("Rectangle".equals(shape)) {
			return new Triangle();
		}

		throw new IllegalArgumentException("Unknown shape, shape = " + shape);
	}
}
