package com.rookieli.myannotation.shape;

import com.rookieli.libannotation.Factory;

/**
 * 时间: 2019/9/15 0015
 * 作者: liyong
 * 描述: class description
 */
@Factory(shape = "Triangle",type = AbsShape.class)
public class Triangle extends AbsShape {
	@Override
	public void draw() {
		System.out.println("-------draw Triangle-------");
	}
}
