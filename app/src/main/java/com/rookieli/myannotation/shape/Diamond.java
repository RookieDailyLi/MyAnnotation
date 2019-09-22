package com.rookieli.myannotation.shape;

import com.rookieli.libannotation.Table;

/**
 * 时间: 2019/9/15 0015
 * 作者: liyong
 * 描述: class description
 */
@Table(name = "xxx")
public class Diamond implements Shape {


	@Override
	public void draw() {
		System.out.println("-------draw Circle-------");
	}
}
