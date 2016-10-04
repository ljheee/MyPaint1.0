package com.ljheee.paint.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * 画刷--颜料桶
 * 是一个特殊的Shape，背景Panel重绘时不能再创建新的[随机数生成]
 * 只能把创建时的参数记录下来，在背景Panel重绘时，画出创建时的
 * @author ljheee
 *
 */
public class AirBrush extends Shape{
	/*设置标志：在创建时设为1；背景Panel重绘时设为2*/
	public int flag = 1;
	
	public int[] xx = new int[150];
	public int[] yy = new int[150];

	Color c = null;
	
	public AirBrush(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}

	@Override
	public void draw(Graphics2D g) {
		if(flag == 1){//创建时，动态生成随机数
			g.setColor(color);
			c = g.getColor();
			Random random = new Random();
	        for (int i = 0; i < 150; i++)
	        {
	            int s;
	            double r;
	            s = random.nextInt(360); 
	            r =random.nextDouble();
	            int dx=(int) ( 50*Math.sqrt(r*r*r)*Math.sin((double)s)) ;
	            int dy=(int) ( 50*Math.sqrt(r*r*r)*Math.cos((double)s)) ;
	            xx[i] = x1+dx;
	            yy[i] = y1+dy;           
	            g.fillOval(xx[i], yy[i], 2, 2);
	        }
		}else{//背景Panel重绘时
			g.setColor(c);
			for (int i = 0; i < 150; i++) {
				g.fillOval(xx[i], yy[i], 2, 2);
			}
		}
	}
}
