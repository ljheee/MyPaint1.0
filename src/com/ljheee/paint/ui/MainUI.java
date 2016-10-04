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
 * ������
 * @author ljheee
 *
 */
public class MainUI extends JFrame {

	private static final long serialVersionUID = 1L;

	JMenuItem newProc, localTaskmgr, exitH, refreshNow, maxLarge, minLarge, aboutItem;

	private ButtonGroup buttongroup = new ButtonGroup();
	// ����������һ��ťʱ����ǰ��ť��������״̬��ʣ�����а�ť�ǵ���״̬
	private JToggleButton toolBtns[] = new JToggleButton[16];

	private JLabel leftInfo = new JLabel("״̬��:");
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
	BufferedImage bufImg;//���ڼ�¼���²������ɵĻ���
	
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

		JMenu fileMenu = new JMenu("�ļ�(F)");
		JMenu editMenu = new JMenu("��ҳ(O)");
		JMenu viewMenu = new JMenu("�鿴(V)");
		JMenu helpMenu = new JMenu("����(H)");

		rootPane.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);

		newProc = new JMenuItem("�½�");
		exitH = new JMenuItem("�˳�");

		aboutItem = new JMenuItem("����");
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new About(MainUI.this,"����",true);
			}
		});
		helpMenu.add(aboutItem);

		// �ļ��˵� --��Ӳ˵���
		fileMenu.add(newProc);
		fileMenu.add(exitH);

		
		
		JPanel panel2 = new JPanel(new GridLayout(1, 2));// ��ͼ����
		panel2.setAlignmentX(TOP_ALIGNMENT);
		JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// toolPanel.setBackground(new Color(240, 240, 240));
		toolPanel.setPreferredSize(new Dimension(220, 66));
		// ����һ�����С����񻯡����Ч���ı߿�
		toolPanel.setBorder(BorderFactory.createEtchedBorder());
		// ��ť����
		String icons[] = { "star", "dot_rect", "eraser", "fill", "color_picker", "magnifier", "pencil", "brush",
				"air_brush", "word", "line", "curve", "rect", "polygon", "oval", "round_rect" };
		String tips[] = { "����", "��߾���", "��Ƥ��", "���", "��ɫѡ��", "�Ŵ�", "Ǧ��", "��ˢ", "air��ˢ", "����", "ֱ��", "����", "����", "�����",
				"��Բ", "Բ�Ǿ���" };

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

			// ���ð�ť��С
			toolBtns[i].setPreferredSize(new Dimension(25, 25));

			// ��Ӱ�ť���������
			btnPanel.add(toolBtns[i]);

			// ����command
			if(i!=4){
				toolBtns[i].setActionCommand(icons[i]);
			}
			

			toolBtns[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(ii==4){//index=4�����������ɫѡ����
						commandColor = JColorChooser.showDialog(MainUI.this, "ѡ����ɫ", Color.blue);;
						return;
					}
					commandTool = e.getActionCommand();
				}
			});
			
		}
		toolBtns[7].setSelected(true);
		toolPanel.add(btnPanel);

		// ��ɫ����
		Color colors[] = { new Color(0, 0, 0), new Color(128, 128, 128), new Color(128, 0, 0), new Color(128, 128, 0),
				new Color(0, 128, 0), new Color(0, 128, 128), new Color(0, 0, 128), new Color(128, 0, 128),
				new Color(128, 128, 64), new Color(0, 64, 64), new Color(0, 128, 255), new Color(0, 64, 128),
				new Color(128, 0, 255), new Color(128, 64, 0), new Color(255, 255, 255), new Color(192, 192, 192),
				new Color(255, 0, 0), new Color(255, 255, 0), new Color(0, 255, 0), new Color(0, 255, 255),
				new Color(0, 0, 255), new Color(255, 0, 255), new Color(255, 255, 128), new Color(0, 255, 128),
				new Color(128, 255, 255), new Color(128, 128, 255), new Color(255, 0, 128), new Color(255, 128, 64) };

		JPanel colorPanel = new JPanel();
		// ���ñ���ɫ�ʹ�С
		colorPanel.setBackground(new Color(240, 240, 240));
		colorPanel.setPreferredSize(new Dimension(300, 50));
		// ������ɫ���Ϊ��ʽ���֣�������Ϊ����룬ˮƽ���Ϊ0����ʾ�봰����߽����ţ�����ֱ���Ϊ7
		colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 4));
		// ����һ�����С����񻯡����Ч���ı߿�
		colorPanel.setBorder(BorderFactory.createEtchedBorder());

		// ��ʾǰ��ɫ������ɫ�Ĵ�ť
		JButton big_button = new JButton();
		big_button.setPreferredSize(new Dimension(36, 36));
		big_button.setBackground(new Color(240, 240, 240));
		// ����һ�����а���б���Ե�ı߿�
		big_button.setBorder(BorderFactory.createLoweredBevelBorder());

		// ��Ӱ�ť����ɫ���
		colorPanel.add(big_button);

		// ��ɫ����еĴ�Ű�ť�����
		JPanel colorPanelBtn = new JPanel();
		colorPanelBtn.setBackground(new Color(240, 240, 240));
		colorPanelBtn.setPreferredSize(new Dimension(250, 36));
		colorPanelBtn.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		// ������ť
		for (int i = 0; i < colors.length; i++) {
			JButton jbutton = new JButton();
			// ���ð�ť��ɫ
			jbutton.setBackground(colors[i]);
			// ���ð�ť��С
			jbutton.setPreferredSize(new Dimension(15, 15));
			// ����һ�����а���б���Ե�ı߿�
			jbutton.setBorder(BorderFactory.createLoweredBevelBorder());

			// ��Ӱ�ť����ɫ���
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

		topJPanel.add(rootPane);// �˵� :�ļ����༭���鿴
		topJPanel.add(panel2);//
		this.add(topJPanel, BorderLayout.NORTH);
		
		

		// center--drawingPanel
		drawPanel = new DrawPanel(shapelist);
		bufImg = (BufferedImage) drawPanel.createImage(drawPanel.getWidth(), drawPanel.getHeight());
    	
		drawPanel.addMouseListener(new MouseListenerImpl());
		drawPanel.addMouseMotionListener(new MouseMotionImpl());
    	this.add(drawPanel);
		
		

		// south--״̬��
		JToolBar bottomToolBar = new JToolBar();
		bottomToolBar.setFloatable(false);// ����JToolBar�����϶�

		bottomToolBar.setPreferredSize(new Dimension(this.getWidth(), 20));
		bottomToolBar.add(leftInfo);

		// bottomToolBar.addSeparator(); //�˷�����ӷָ��� ��Ч
		JSeparator jsSeparator = new JSeparator(SwingConstants.VERTICAL);
		bottomToolBar.add(jsSeparator);// ��ӷָ���

		leftInfo.setPreferredSize(new Dimension(200, 20));
		leftInfo.setHorizontalTextPosition(SwingConstants.LEFT);

		bottomToolBar.add(pathInfo);
		pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
		bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));// ��ӷָ���

		bottomToolBar.add(timeInfo);
		timeInfo.setPreferredSize(new Dimension(70, 20));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		timeInfo.setText(sdf.format(new Date()));

		this.add(bottomToolBar, BorderLayout.SOUTH);// ����--�š�״̬����

		
		this.setVisible(true);
	}
	
	/**
	 * ���
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
			case "line":	//ֱ��   
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
	 * ����϶�
	 * @author ljheee
	 *
	 */
	class MouseMotionImpl implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			
			xEnd = e.getX();
			yEnd = e.getY();
			
			g = (Graphics2D)drawPanel.getGraphics();
			
			//Ǧ�ʻ���Ƥ��
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
				
				//��������
				x0 = xEnd;
				y0 = yEnd;
			}
			
			
				
			switch (commandTool) {
				
			case "line":	//ֱ��   
				shape = new Line(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
			
			case "rect":	//����   
				shape = new Rectangle(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
				
			case "round_rect":	//Բ�Ǿ���   
				shape = new RoundRect(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
			case "dot_rect":	//��߾���   
				shape = new DottedRectangle(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
				
			case "oval":	//��Բ   
				shape = new Oval(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
				
			case "polygon":	//�����   
				shape = new MyPolygon(x0, y0, xEnd, yEnd, commandColor);
				shape.draw(g);
				repaint();
				break;
				
				
				
			default:
				break;
			}
			
			
			
			
			xPX = e.getX();
			yPX = e.getY();
			pathInfo.setText("  "+xPX+","+yPX);//״̬����ʾ�����������Ϣ��
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			xPX = e.getX();
			yPX = e.getY();
			pathInfo.setText("  "+xPX+","+yPX);//״̬����ʾ�����������Ϣ��
		}
	}
	
	

	public static void main(String[] args) {
		new MainUI();
	}

}
