package com.ljheee.paint.shape;

import java.util.ArrayList;

public class ShapeList {
	
	ArrayList<Shape> list = new ArrayList<>();
	
	public void addShape(Shape shape){
		list.add(shape);
	}
	
	public Shape getShape(int index){
		return list.get(index);
	}
	
	
	public int size(){
		return list.size();
	}

}
