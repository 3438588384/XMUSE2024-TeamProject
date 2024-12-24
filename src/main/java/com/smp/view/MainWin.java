package com.smp.view;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class MainWin extends JFrame {

	private JPanel contentPane;
	private String username;
	private boolean isAdmin;
	private Color customColor = Color.decode("#191970");
	private Color customColor2 = Color.decode("#27408B");
	private Color customColor3 = Color.decode("#1874CD");


	/**
	 * Create the frame.
	 */
	public MainWin(String username, boolean isAdmin, int uid) {
		this.username = username;
		this.isAdmin = isAdmin;
		setTitle("商品信息管理系统");
		// 设置默认关闭操作
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口大小和位置
		setBounds(100, 100, 920, 600);
		// 创建主面板并设置边框
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// 设置主面板背景色
		contentPane.setBackground(customColor);
		// 将主面板设置为内容面板
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 创建用于显示卡片的面板
		JPanel panel = new JPanel();
		panel.setBounds(150, 10, 700, 476);
		panel.setBackground(customColor); // 设置子面板背景色
		contentPane.add(panel);

		// 创建CardLayout
		CardLayout card = new CardLayout();
		panel.setLayout(card);

		// 添加各个面板到CardLayout中，并设置背景色
		JPanel product_onShelvesPanel = new Product_onShelves();
		product_onShelvesPanel.setBackground(customColor2);//表格外面一圈的颜色
		panel.add(product_onShelvesPanel, "product_onShelves");

		JPanel productPanel = new Products();
		productPanel.setBackground(customColor2);
		panel.add(productPanel, "product");

		JPanel inventoryPanel = new Inventory();
		inventoryPanel.setBackground(customColor2);
		panel.add(inventoryPanel, "inventory");

		JPanel aboutPanel = new About();
		aboutPanel.setBackground(customColor2);
		panel.add(aboutPanel, "about");

		JPanel userPanel = new User(uid);
		userPanel.setBackground(customColor2);
		panel.add(userPanel, "user");

		// 创建并添加“查看图片”按钮
		JButton ShowProductButton = new JButton("架上商品");
		ShowProductButton.setBounds(10, 58, 130, 23);
		ShowProductButton.setBackground(customColor3); // 按钮背景色
		ShowProductButton.setForeground(Color.BLACK); // 按钮前景色
		contentPane.add(ShowProductButton);
		ShowProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 显示商品图片面板
				card.show(panel, "product_onShelves");
			}
		});

		// 创建并添加“商品信息”按钮
		JButton btnNewButton = new JButton("\u5546\u54C1\u4FE1\u606F");
		btnNewButton.setBackground(customColor3); // 按钮背景色
		btnNewButton.setForeground(Color.BLACK); // 按钮前景色
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isAdmin) { // 只有admin表中的用户可以查看所有商品信息并进行修改
					// 显示“商品信息”面板
					card.show(panel, "product");
				} else {
					JOptionPane.showMessageDialog(contentPane, "您没有权限访问此页面！");
				}
			}
		});
		btnNewButton.setBounds(10, 128, 130, 23);
		contentPane.add(btnNewButton);

		// 创建并添加“库存信息”按钮
		JButton btnNewButton_1 = new JButton("\u5E93\u5B58\u4FE1\u606F");
		btnNewButton_1.setBackground(customColor3); // 按钮背景色
		btnNewButton_1.setForeground(Color.BLACK); // 按钮前景色
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isAdmin) { // 只有admin表中的用户可以查看库存信息
					// 显示“库存信息”面板
					card.show(panel, "inventory");
				} else {
					JOptionPane.showMessageDialog(contentPane, "您没有权限访问此页面！");
				}
			}
		});
		btnNewButton_1.setBounds(10, 189, 130, 23);
		contentPane.add(btnNewButton_1);

		// 创建并添加“关于”按钮
		JButton btnNewButton_2 = new JButton("\u5173\u4E8E");
		btnNewButton_2.setBackground(customColor3); // 按钮背景色
		btnNewButton_2.setForeground(Color.BLACK); // 按钮前景色
		btnNewButton_2.setBounds(10, 250, 130, 23);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 显示“关于”面板
				card.show(panel, "about");
			}
		});

		// 创建并添加“退出”按钮
		JButton out_button = new JButton("退出");
		out_button.setBackground(customColor3); // 按钮背景色
		out_button.setForeground(Color.BLACK); // 按钮前景色
		out_button.setBounds(10, 310, 130, 23);
		contentPane.add(out_button);
		out_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginWindow();
			}
		});

		// 创建并添加“用户表权限”按钮
		JButton user_button = new JButton("用户权限");
		user_button.setBackground(customColor3); // 按钮背景色
		user_button.setForeground(Color.BLACK); // 按钮前景色
		user_button.setBounds(10, 370, 130, 23);
		contentPane.add(user_button);
		user_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isAdmin) { // 只有admin表中的用户可以查看所有商品信息并进行修改
					// 显示“用户信息”面板
					card.show(panel, "user");
				} else {
					JOptionPane.showMessageDialog(contentPane, "您没有权限访问此页面！");
				}
			}
		});

		// 显示默认的“商品信息”面板
		card.show(panel, "product_onShelves");
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWin frame = new MainWin("admin", true, 1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
