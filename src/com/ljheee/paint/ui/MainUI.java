package com.ljheee.paint.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
/**
 * 主界面
 * @author ljheee
 *
 */
public class MainUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	JMenuItem newProc,localTaskmgr, exitH, refreshNow, maxLarge, minLarge, aboutItem;
	
	public MainUI() {
		super();
		this.setSize(900, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JPanel topJPanel = new JPanel();
		topJPanel.setLayout(new GridLayout(2, 1));

		JRootPane rootPane = new JRootPane(); // 此panel，添加菜单
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
		helpMenu.add(aboutItem);

		// 文件菜单 --添加菜单项
		fileMenu.add(newProc);
		fileMenu.add(exitH);
		

		JRootPane panel2 = new JRootPane();//画图工具





		topJPanel.add(rootPane);// 菜单 :文件、编辑、查看
		topJPanel.add(panel2);// 
		this.getContentPane().add(topJPanel, BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	
	
	
	
	public static void main(String[] args) {
		new MainUI();
	}
	

}
