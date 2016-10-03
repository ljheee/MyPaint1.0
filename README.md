# MyPaint1.0
MyPaint1.0
g = (Graphics2D)drawPanel.getGraphics();

			switch (commandTool) {
			case "line":	//Ö±Ïß   
				shape = new Line(x0, y0, xEnd, yEnd, commandColor);
	 	 		shape.draw(g);
	 	 		repaint();
	 	 		shapelist.addShape(shape);
				break;
			
			case "rect":	   
				shape = new Rectangle(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				shapelist.addShape(shape);
				break;
			case "color_picker":	// 
				
				return;