package com.ljheee.paint.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.*;

import com.ljheee.paint.component.DrawPanel;
import com.ljheee.paint.shape.AirBrush;
import com.ljheee.paint.shape.DottedRectangle;
import com.ljheee.paint.shape.Eraser;
import com.ljheee.paint.shape.Line;
import com.ljheee.paint.shape.MyPolygon;
import com.ljheee.paint.shape.Oval;
import com.ljheee.paint.shape.Pencil;
import com.ljheee.paint.shape.Rectangle;
import com.ljheee.paint.shape.RoundRect;
import com.ljheee.paint.shape.Shape;
import com.ljheee.paint.shape.ShapeList;
import com.ljheee.paint.ui.about.About;


/**
 * 主界面
 * @author ljheee
 *
 */
public class MainUI extends JFrame {

	private static final long serialVersionUID = 1L;

	JMenuItem newProc, localTaskmgr, exitH, refreshNow, maxLarge, minLarge, aboutItem;

	private ButtonGroup buttongroup = new ButtonGroup();
	// 当按下其中一按钮时，当前按钮呈现下陷状态，剩下所有按钮是弹起状态
	private JToggleButton toolBtns[] = new JToggleButton[16];

	private JLabel leftInfo = new JLabel("状态栏:");
	private JLabel pathInfo = new JLabel("  ");
	private JLabel timeInfo = new JLabel("  ");
	
	JPanel drawPanel = null;
	
	Graphics2D g = null;
	int x0=0,y0=0,xEnd=0,yEnd=0;
	int xPX = 0,yPX = 0;
	String commandTool = "pencil";
	Color commandColor = Color.black;
	
	ShapeList shapelist = new ShapeList();
	Shape shape = null;
	
	BufferedImage[] bufferedImages = new BufferedImage[50];
	int count = 0;
	BufferedImage bufImg;//用于记录最新操作生成的画面
	
	public MainUI() {
		super();
		this.setSize(900, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		g = (Graphics2D) this.getGraphics();

		JPanel topJPanel = new JPanel();
		topJPanel.setLayout(new GridLayout(2, 1));

		JRootPane rootPane = new JRootPane();
		rootPane.setBackground(Color.gray);
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("文件(F)");
		JMenu editMenu = new JMenu("主页(O)");
		JMenu viewMenu = new JMenu("查看(V)");
		JMenu helpMenu = new JMenu("帮助(H)");

		rootPane.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);

		newProc = new JMenuItem("新建");
		exitH = new JMenuItem("退出");

		aboutItem = new JMenuItem("关于");
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new About(MainUI.this,"关于",true);
			}
		});
		helpMenu.add(aboutItem);

		// 文件菜单 --添加菜单项
		fileMenu.add(newProc);
		fileMenu.add(exitH);

		
		
		JPanel panel2 = new JPanel(new GridLayout(1, 2));// 画图工具
		panel2.setAlignmentX(TOP_ALIGNMENT);
		JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// toolPanel.setBackground(new Color(240, 240, 240));
		toolPanel.setPreferredSize(new Dimension(220, 66));
		// 创建一个具有“浮雕化”外观效果的边框
		toolPanel.setBorder(BorderFactory.createEtchedBorder());
		// 按钮数组
		String icons[] = { "star", "dot_rect", "eraser", "fill", "color_picker", "magnifier", "pencil", "brush",
				"air_brush", "word", "line", "curve", "rect", "polygon", "oval", "round_rect" };
		String tips[] = { "星形", "虚边矩形", "橡皮擦", "填充", "颜色选择", "放大镜", "铅笔", "画刷", "air画刷", "文字", "直线", "曲线", "矩形", "多边形",
				"椭圆", "圆角矩形" };

		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(new Color(240, 240, 240));
		btnPanel.setPreferredSize(new Dimension(220, 53));
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));

		
		for (int i = 0; i < icons.length; i++) {
			int ii = i;
			toolBtns[i] = new JToggleButton();
			toolBtns[i].setIcon(new ImageIcon("images/" + icons[i] + ".jpg"));
			toolBtns[i].setToolTipText(tips[i]);
			buttongroup.add(toolBtns[i]);

			// 设置按钮大小
			toolBtns[i].setPreferredSize(new Dimension(25, 25));

			// 添加按钮到工具面板
			btnPanel.add(toolBtns[i]);

			// 设置command
			if(i!=4){
				toolBtns[i].setActionCommand(icons[i]);
			}
			

			toolBtns[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(ii==4){//index=4；第五个是颜色选择器
						commandColor = JColorChooser.showDialog(MainUI.this, "选择颜色", Color.blue);;
						return;
					}
					commandTool = e.getActionCommand();
				}
			});
			
		}
		toolBtns[7].setSelected(true);
		toolPanel.add(btnPanel);

		// 颜色数组
		Color colors[] = { new Color(0, 0, 0), new Color(128, 128, 128), new Color(128, 0, 0), new Color(128, 128, 0),
				new Color(0, 128, 0), new Color(0, 128, 128), new Color(0, 0, 128), new Color(128, 0, 128),
				new Color(128, 128, 64), new Color(0, 64, 64), new Color(0, 128, 255), new Color(0, 64, 128),
				new Color(128, 0, 255), new Color(128, 64, 0), new Color(255, 255, 255), new Color(192, 192, 192),
				new Color(255, 0, 0), new Color(255, 255, 0), new Color(0, 255, 0), new Color(0, 255, 255),
				new Color(0, 0, 255), new Color(255, 0, 255), new Color(255, 255, 128), new Color(0, 255, 128),
				new Color(128, 255, 255), new Color(128, 128, 255), new Color(255, 0, 128), new Color(255, 128, 64) };

		JPanel colorPanel = new JPanel();
		// 设置背景色和大小
		colorPanel.setBackground(new Color(240, 240, 240));
		colorPanel.setPreferredSize(new Dimension(300, 50));
		// 设置颜色面板为流式布局，并设置为左对齐，水平间距为0（表示与窗口左边紧贴着），垂直间距为7
		colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 4));
		// 创建一个具有“浮雕化”外观效果的边框
		colorPanel.setBorder(BorderFactory.createEtchedBorder());

		// 显示前景色、背景色的大按钮
		JButton big_button = new JButton();
		big_button.setPreferredSize(new Dimension(36, 36));
		big_button.setBackground(new Color(240, 240, 240));
		// 创建一个具有凹入斜面边缘的边框
		big_button.setBorder(BorderFactory.createLoweredBevelBorder());

		// 添加按钮到颜色面板
		colorPanel.add(big_button);

		// 颜色面板中的存放按钮的面板
		JPanel colorPanelBtn = new JPanel();
		colorPanelBtn.setBackground(new Color(240, 240, 240));
		colorPanelBtn.setPreferredSize(new Dimension(250, 36));
		colorPanelBtn.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		// 创建按钮
		for (int i = 0; i < colors.length; i++) {
			JButton jbutton = new JButton();
			// 设置按钮颜色
			jbutton.setBackground(colors[i]);
			// 设置按钮大小
			jbutton.setPreferredSize(new Dimension(15, 15));
			// 创建一个具有凹入斜面边缘的边框
			jbutton.setBorder(BorderFactory.createLoweredBevelBorder());

			// 添加按钮到颜色面板
			colorPanelBtn.add(jbutton);
			colorPanel.add(colorPanelBtn);

			jbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					commandColor = ((JButton)e.getSource()).getBackground();
				}
			});
		}

		panel2.add(toolPanel);
		panel2.add(colorPanel);

		topJPanel.add(rootPane);// 菜单 :文件、编辑、查看
		topJPanel.add(panel2);//
		this.add(topJPanel, BorderLayout.NORTH);
		
		

		// center--drawingPanel
		drawPanel = new DrawPanel(shapelist);
		bufImg = (BufferedImage) drawPanel.createImage(drawPanel.getWidth(), drawPanel.getHeight());
    	
		drawPanel.addMouseListener(new MouseListenerImpl());
		drawPanel.addMouseMotionListener(new MouseMotionImpl());
    	this.add(drawPanel);
		
		

		// south--状态栏
		JToolBar bottomToolBar = new JToolBar();
		bottomToolBar.setFloatable(false);// 设置JToolBar不可拖动

		bottomToolBar.setPreferredSize(new Dimension(this.getWidth(), 20));
		bottomToolBar.add(leftInfo);

		// bottomToolBar.addSeparator(); //此方法添加分隔符 无效
		JSeparator jsSeparator = new JSeparator(SwingConstants.VERTICAL);
		bottomToolBar.add(jsSeparator);// 添加分隔符

		leftInfo.setPreferredSize(new Dimension(200, 20));
		leftInfo.setHorizontalTextPosition(SwingConstants.LEFT);

		bottomToolBar.add(pathInfo);
		pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
		bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));// 添加分隔符

		bottomToolBar.add(timeInfo);
		timeInfo.setPreferredSize(new Dimension(70, 20));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		timeInfo.setText(sdf.format(new Date()));

		this.add(bottomToolBar, BorderLayout.SOUTH);// 下面--放“状态栏”

		
		this.setVisible(true);
	}
	
	/**
	 * 鼠标
	 * @author ljheee
	 *
	 */
	class MouseListenerImpl implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			x0 = e.getX();
			y0 = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			xEnd = e.getX();
			yEnd = e.getY();
			
			g = (Graphics2D)drawPanel.getGraphics();

			if(commandTool.equals("air_brush")){
				AirBrush airBrush = new AirBrush(x0, y0, xEnd, yEnd, commandColor);
				shape = airBrush;
				airBrush.flag = 1;
				shape.draw(g);
				repaint();
				shapelist.addShape(shape);
				return;
			}
			
			switch (commandTool) {
			case "line":	//直线   
	 	 		shape.draw(g);
//	 	 		repaint();
	 	 		shapelist.addShape(shape);
				break;
			
			case "rect":	   
				shape.draw(g);
				repaint();
				shapelist.addShape(shape);
				break;
				
			case "round_rect":	   
				shape.draw(g);
				repaint();
				shapelist.addShape(shape);
				break;
			case "dot_rect":	   
				shape.draw(g);
				repaint();
				shapelist.addShape(shape);
				break;
			case "oval":	   
				shape.draw(g);
				repaint();
				shapelist.addShape(shape);
				break;
			case "pencil":	   
				shape.draw(g);
				repaint();
				shapelist.addShape(shape);
				break;
				
			case "polygon":	   
				shape.draw(g);
				repaint();
				shapelist.addShape(shape);
				break;
				
			}
			
		}
		
	}
	
	/**
	 * 鼠标拖动
	 * @author ljheee
	 *
	 */
	class MouseMotionImpl implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			
			xEnd = e.getX();
			yEnd = e.getY();
			
			g = (Graphics2D)drawPanel.getGraphics();
			
			//铅笔或橡皮擦
			if(commandTool.equals("pencil")||commandTool.equals("eraser")){
				xEnd = e.getX();
				yEnd = e.getY();
				shape = new Pencil(x0, y0, xEnd, yEnd, commandColor);
				if(commandTool.equals("eraser")){
					shape = new Eraser(x0, y0, xEnd, yEnd, Color.white);
				}
				shape.draw(g);
				repaint();
				shapelist.addShape(shape);
				
				//交换坐标
				x0 = xEnd;
				y0 = yEnd;
			}
			
			
				
			switch (commandTool) {
				
			case "line":	//直线   
				shape = new Line(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
			
			case "rect":	//矩形   
				shape = new Rectangle(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
				
			case "round_rect":	//圆角矩形   
				shape = new RoundRect(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
			case "dot_rect":	//虚边矩形   
				shape = new DottedRectangle(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
				
			case "oval":	//椭圆   
				shape = new Oval(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
				
			case "polygon":	//多边形   
				shape = new MyPolygon(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
				
				
				
			default:
				break;
			}
			
			
			
			
			xPX = e.getX();
			yPX = e.getY();
			pathInfo.setText("  "+xPX+","+yPX);//状态栏显示“鼠标像素信息”
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			xPX = e.getX();
			yPX = e.getY();
			pathInfo.setText("  "+xPX+","+yPX);//状态栏显示“鼠标像素信息”
		}
	}
	
	

	public static void main(String[] args) {
		new MainUI();
	}

}
