package com.smp.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class About extends JPanel {

	/**
	 * Create the panel.
	 */
	public About() {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("\u7cfb\u7edf\u7b80\u4ecb\uFF1A");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel.setBounds(50, 100, 115, 46);
		add(lblNewLabel);

//		可以自定义
		JLabel lblNewLabel_1 = new JLabel("      该系统用于商品信息管理，使用该系统的普通用户可以查看、搜索上架商品");
		lblNewLabel_1.setBounds(80, 150, 700, 100);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("的信息及商品图片，管理员可以查看并修改商品本身信息、库存信息，以及管理");
		lblNewLabel_2.setBounds(80, 170, 700, 100);
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("其他用户的权限。");
		lblNewLabel_3.setBounds(80, 190, 700, 100);
		add(lblNewLabel_3);

	}

}
