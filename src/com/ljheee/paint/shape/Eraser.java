package com.ljheee.paint.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
/**
 * 橡皮擦
 * 原理就是：把颜色设为白色
 * @author ljheee
 *
 */
public class Eraser extends Shape{

	public Eraser(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}
	
	@Override
	public void draw(Graphics2D g) {
		Stroke s1 = g.getStroke();
		g.setColor(Color.white);
		g.setStroke(new BasicStroke(4.0f,   //设置粗度
	               BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g.drawLine(x1, y1, x2, y2);
		g.setStroke(s1);
	}

}
