package com.ljheee.paint.shape;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {

	public int x1,y1,x2,y2;
	public Color color;
	
	/**
	 * 父类构造函数
	 */
	public Shape(int x1,int y1,int x2,int y2,Color color){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;

	}
	
	/**
	 * 父类画图的方法
	 */
	public  void draw(Graphics g){};
	
}
