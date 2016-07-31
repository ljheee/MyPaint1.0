package com.ljheee.paint.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
/**
 * ������
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

		JRootPane rootPane = new JRootPane(); // ��panel����Ӳ˵�
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
		helpMenu.add(aboutItem);

		// �ļ��˵� --��Ӳ˵���
		fileMenu.add(newProc);
		fileMenu.add(exitH);
		

		JRootPane panel2 = new JRootPane();//��ͼ����





		topJPanel.add(rootPane);// �˵� :�ļ����༭���鿴
		topJPanel.add(panel2);// 
		this.getContentPane().add(topJPanel, BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	
	
	
	
	public static void main(String[] args) {
		new MainUI();
	}
	

}
