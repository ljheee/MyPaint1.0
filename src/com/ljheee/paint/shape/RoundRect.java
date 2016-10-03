package com.ljheee.paint.shape;

import java.awt.Color;
import java.awt.Graphics2D;

public class RoundRect extends Shape{

	public RoundRect(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.drawRoundRect(Math.min(x1,x2),Math.min(y1,y2),
                Math.abs(x1-x2),Math.abs(y1-y2),
                50,35);
	}

}
