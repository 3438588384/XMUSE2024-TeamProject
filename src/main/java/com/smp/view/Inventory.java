package com.smp.view;

import com.smp.utils.DataUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventory extends JPanel {
	private JTable table;
	private JTextField jt_num;
	private JTextField jt_note;

	private int isSelected = 0;

	private Color tableColor = Color.decode("#104E8B");
	private Color buttonColor2 = Color.decode("#1874CD");

	Object[][] data;
	// SQL 查询语句，用于获取产品库存信息
	String sql = "SELECT id,name,stock,note \n" +
			"FROM product\n" +
			"LEFT JOIN inventory\n" +
			"ON product.id=inventory.product_id\n" +
			"WHERE stock IS NOT NULL;";
	// SQL 更新语句，用于更新库存信息
	String sql2 = "update inventory set stock=? , note=? where product_id=?";



	// 创建表头
	String[] columnNames = {"序号", "编号", "名称", "库存","备注"};
	// 初始化数据，从数据库中读取
	void init() {
		try {
			ResultSet set = DataUtils.query(sql, new Object[]{});
			Object[][] arrays = DataUtils.getSetArrays(set);
			data = arrays;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create the panel.
	 */
	public Inventory() {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 576, 344);
		add(scrollPane);

		jt_num = new JTextField();
		jt_num.setBounds(100, 388, 98, 29);
		add(jt_num);
		jt_num.setColumns(10);

		jt_note = new JTextField();
		jt_note.setBounds(344, 388, 98, 29);
		add(jt_note);
		jt_note.setColumns(10);
		// 初始化数据
		init();

		// 创建表格
		table = new JTable(data,columnNames);
		table.setBackground(tableColor);
		// 添加表格行选择监听器
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // 确保事件不会被重复触发
					int row = table.getSelectedRow();
					if (row != -1) {
						// 获取选中行的编号和库存
						isSelected = Integer.parseInt(table.getValueAt(row, 1).toString());
						jt_num.setText(table.getValueAt(row, 3).toString());
						jt_note.setText(table.getValueAt(row, 4).toString());
					}
				}
			}
		});

		scrollPane.setViewportView(table);
		// 库存量
		JLabel lblNewLabel = new JLabel("\u5E93\u5B58\u91CF\uFF1A");
		lblNewLabel.setBounds(33, 394, 70, 15);
		add(lblNewLabel);
		// 备注
		JLabel lblNewLabel_1 = new JLabel("\u5907\u6CE8\uFF1A");
		lblNewLabel_1.setBounds(304, 394, 54, 15);
		add(lblNewLabel_1);


		//修改选中行信息
		JButton btnNewButton = new JButton("修改选中行信息");
		btnNewButton.setBackground(buttonColor2);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获取输入框中的库存和备注值
				String num = jt_num.getText();
				String note = jt_note.getText();
				// 根据库存值自动设置备注
				try {
					int stock = Integer.parseInt(num);
					if (stock == 0) {
						note = "无";
					} else if (stock < 50) {
						note = "不足";
					} else if (stock < 100) {
						note = "适量";
					} else {
						note = "充足";
					}
					// 更新数据库中的库存和备注信息
					DataUtils.Update(sql2, new Object[]{num, note,isSelected});
					init();
					DefaultTableModel model = new DefaultTableModel(data, columnNames);
					table.setModel(model);
					JOptionPane.showMessageDialog(null,"操作成功！");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null,"操作失败！请重试");
					throw new RuntimeException(ex);
				}
			}
		});
		btnNewButton.setBounds(49, 428, 191, 23);
		add(btnNewButton);

//		手动刷新按钮
		JButton refresh = new JButton("刷新");
		refresh.setBackground(buttonColor2);
		refresh.setForeground(Color.BLACK);
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				init();
				DefaultTableModel model = new DefaultTableModel(data, columnNames);
				table.setModel(model);
			}
		});
		refresh.setBounds(250, 428, 93, 23);
		add(refresh);

		JLabel lblNewLabel_2 = new JLabel("库存一览");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(259, 10, 125, 20);
		add(lblNewLabel_2);

	}

}
