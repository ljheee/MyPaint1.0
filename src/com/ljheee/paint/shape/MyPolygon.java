package com.ljheee.paint.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
/**
 * ¶à±ßÐÎ
 * @author ljheee
 *
 */
public class MyPolygon extends Shape{

	private Polygon polygon = new Polygon();
	public int npoints = 0;
	public int[] xpoints = new int[1000];
	public int[] ypoints = new int[1000];
	
	public MyPolygon(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}

	@Override
	public void draw(Graphics2D g) {
		polygon.npoints = npoints;
		polygon.xpoints = xpoints;
		polygon.ypoints = ypoints;
		g.setColor(color);
		g.drawPolygon(polygon);
	}
}
