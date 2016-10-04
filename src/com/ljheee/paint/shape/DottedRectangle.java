package com.ljheee.paint.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
/**
 * 虚边矩形
 * @author ljheee
 *
 */
public class DottedRectangle extends Shape{

	public DottedRectangle(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}

	@Override
	public void draw(Graphics2D g) {
		Stroke s1 = g.getStroke();
		/** new float[] { 5, 5, } 数组可以改变虚线的密度* */
        Stroke dash = new BasicStroke(0.5f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 0.5f, new float[] { 5, 5, }, 0f);
        g.setStroke(dash);
        g.setColor(color);
        g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
        g.setStroke(s1);//设为原来的实边，不影响下次画图
	}
}
