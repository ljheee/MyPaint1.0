package com.ljheee.paint.shape;

import java.awt.Color;
import java.awt.Graphics2D;

public class Line extends Shape {

	public Line(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.drawLine(x1, y1, x2, y2);
	}

}
