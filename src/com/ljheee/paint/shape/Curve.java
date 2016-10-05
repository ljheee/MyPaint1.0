package com.ljheee.paint.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;

/**
 * 三次贝塞尔曲线
 * @author ljheee
 *
 */
public class Curve extends Shape{

	public CubicCurve2D.Double cubicCurve2D = new CubicCurve2D.Double();//贝斯曲线
	
	double cx1,cy1,cx2,cy2;
	
	
	public Curve(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
//		cubicCurve2D.setCurve(new Point(x1, y1), new Point(0, 0), new Point(0, 0), new Point(x2, y2));
		//		cx1 = x1;
//		cy1 = y1;
//		cx2 = x2;
//		cy2 = y2;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.draw(cubicCurve2D);
	}
	
}
