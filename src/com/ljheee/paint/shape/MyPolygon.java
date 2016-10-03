package com.ljheee.paint.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class MyPolygon extends Shape{

	Polygon polygon;
	public MyPolygon(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
		polygon = new Polygon();
		polygon.addPoint(x1, y1);
		polygon.addPoint(x2, y2);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.drawPolygon(polygon);
	}
}
