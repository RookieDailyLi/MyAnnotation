package com.rookieli.myannotation.shape;

import com.rookieli.libannotation.Factory;

/**
 * 时间: 2019/9/15 0015
 * 作者: liyong
 * 描述: class description
 */
@Factory(shape = "Triangle",type = Shape.class)
public class Triangle implements Shape {
	@Override
	public void draw() {

	}
}
