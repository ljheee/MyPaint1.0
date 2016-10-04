package com.ljheee.paint.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.ljheee.paint.shape.AirBrush;
import com.ljheee.paint.shape.Shape;
import com.ljheee.paint.shape.ShapeList;

public class DrawPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	ShapeList list = null;
	Shape shape;
	
	public DrawPanel(ShapeList lists) {
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		//创建一个具有凹入斜面边缘的边框
		setBorder(BorderFactory.createLoweredBevelBorder());
		setBackground(Color.white);
		this.list = lists;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g; // 定义画笔
		
		for (int i = 0; i < list.size(); i++) {
			shape = list.getShape(i);
			if(shape instanceof AirBrush){
				AirBrush airBrush = (AirBrush) shape;
				airBrush.flag = 2;
			}
			shape.draw(g2d);
		}
	}
	
}
