package com.rookieli.myannotation.shape;

import com.rookieli.libannotation.Factory;

/**
 * 时间: 2019/9/15 0015
 * 作者: liyong
 * 描述: class description
 */
@Factory(shape = "Rectangle",type = Shape.class)
public class Rectangle implements Shape {
	@Override
	public void draw() {
		System.out.println("-------draw Rectangle-------");
	}
}
