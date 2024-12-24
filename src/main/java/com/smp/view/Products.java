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
import java.util.ArrayList;

public class Products extends JPanel {
	private JTextField jt_id;
	private JTextField jt_name;
	private JTextField jt_type;
	private JTextField jt_price;
	private JTextField jt_status;
	private JTable table;

	private Object[][] data;

	private Color tableColor = Color.decode("#104E8B");
	private Color buttonColor2 = Color.decode("#1874CD");

	// 创建表头
	String[] columnNames = {"序号","编号", "名称", "价格", "分类", "状态"};

	//	获取商品数据,SQL语句
	String sql1 = "SELECT * from product";// 查询所有上架商品

	String sql2 = "insert into product (id, name, price, category, status) values (0,?,?,?,?)"; // 插入商品

	String sql3 = "delete from product where id=?";// 删除商品

	String sql5 = "update product set name=? , price=? , category=? where id=?";// 更新商品

	String sql6 = "insert into inventory values (?,0,'无')";// 插入库存

	String sql7 = "select * from product where name=? && price=? && category=? && status=?";// 查询商品

	String sql8 = "delete from inventory where product_id=?";// 删除库存

	String sql9 = "update product set status=1 where id=?";// 上架商品
	String sql11 = "insert into product_on_shelf values (?,?,?,?,?)";// 插入上架商品表
	String sql13 = "delete from product_off_shelf where id=?";// 删除商品

	String sql10 = "update product set status=0 where id=?";// 下架商品
	String sql12 = "insert into product_off_shelf values (?,?,?,?,?)";// 插入下降商品表
	String sql14 = "delete from product_on_shelf where id=?";// 删除商品
	String sqlImage = "SELECT image_path from ProductImage where product_id = ?";

	// 初始化数据
	void init() {
		try {
			ResultSet set = DataUtils.query(sql1, new Object[]{});
			Object[][] arrays = DataUtils.getSetArrays(set);
			data = arrays;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create the panel.
	 */
	public Products() {
		setLayout(null);
		// 新增按钮及其监听器
		JButton jb_add = new JButton("新增");
		jb_add.setBackground(buttonColor2);
		jb_add.setForeground(Color.BLACK);
		jb_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				获取输入的值
				String name = jt_name.getText();
				String type = jt_type.getText();
				String price = jt_price.getText();
				String status = jt_status.getText();
				if (name.equals("") || type.equals("") || price.equals("") || status.equals("") )
				{
					JOptionPane.showMessageDialog(null,"请填写完整信息！编号无需填写！");
				}
				else if ( !status.equals("1") && !status.equals("0") ) {
					JOptionPane.showMessageDialog(null,"状态请填写1或0! 1对应上架，0对应下架");
				} else {
					try {
						int id = 0;
						int update = DataUtils.Update(sql2, new Object[]{name, price, type, status});
						reload();
//					获取刚刚的商品，创建库存记录
						ResultSet set = DataUtils.query(sql7, new Object[]{name, price, type, status});
						while (set.next()) {
							id = (int) set.getLong(1);
						}
						DataUtils.Update(sql6, new Object[]{id});
						JOptionPane.showMessageDialog(null,"操作成功！");
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null,"操作失败！请重试");
						throw new RuntimeException(ex);
					}
				}
			}
		});
		jb_add.setBounds(10, 10, 93, 23);
		add(jb_add);

		// 删除按钮及其监听器
		JButton jb_del = new JButton("删除选中");
		jb_del.setBackground(buttonColor2);
		jb_del.setForeground(Color.BLACK);
		jb_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				删除选中行
				int row = table.getSelectedRow();
				if (row != -1) {
					int id = (int) table.getValueAt(row, 1);
					try {
						DataUtils.Update(sql3, new Object[]{id});
//						同时删除库存表记录
						DataUtils.Update(sql8, new Object[]{id});
						reload();
						JOptionPane.showMessageDialog(null,"操作成功！");
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null,"操作失败！请重试");
						throw new RuntimeException(ex);
					}
				}
			}
		});
		jb_del.setBounds(123, 10, 93, 23);
		add(jb_del);

		// 上架按钮及其监听器
		JButton jb_OnShelves = new JButton("上架");
		jb_OnShelves.setBackground(buttonColor2);
		jb_OnShelves.setForeground(Color.BLACK);
		jb_OnShelves.setBounds(273, 10, 93, 23);
		add(jb_OnShelves);
		jb_OnShelves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 上架选中的商品
				int row = table.getSelectedRow();
				String name = jt_name.getText();
				String type = jt_type.getText();
				String price = jt_price.getText();
				String status = "1";
				if (row != -1) {
					int id = (int) table.getValueAt(row, 1);
					try {
						DataUtils.Update(sql9, new Object[]{id});
						//DataUtils.Update(sql11, new Object[]{id,name,price,type,status});
						//DataUtils.Update(sql13, new Object[]{id});
						reload();
						JOptionPane.showMessageDialog(null,"商品上架成功！");
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, "商品上架失败！请重试");
						throw new RuntimeException(ex);
					}
				} else {
					JOptionPane.showMessageDialog(null, "请先选择一个产品！");
				}
			}
		});
		// 下架按钮及其监听器
		JButton jb_OffShelves = new JButton("下架");
		jb_OffShelves.setBackground(buttonColor2);
		jb_OffShelves.setForeground(Color.BLACK);
		jb_OffShelves.setBounds(386, 10, 93, 23);
		add(jb_OffShelves);
		jb_OffShelves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 上架选中的商品
				int row = table.getSelectedRow();
				String name = jt_name.getText();
				String type = jt_type.getText();
				String price = jt_price.getText();
				String status = "0";
				if (row != -1) {
					int id = (int) table.getValueAt(row, 1);
					try {
						DataUtils.Update(sql10, new Object[]{id});
						//DataUtils.Update(sql12, new Object[]{id,name,price,type,status});
						//DataUtils.Update(sql14, new Object[]{id});
						reload();
						JOptionPane.showMessageDialog(null,"商品下架成功！");
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, "商品下架失败！请重试");
						throw new RuntimeException(ex);
					}
				} else {
					JOptionPane.showMessageDialog(null, "请先选择一个产品！");
				}
			}
		});

		// 滚动面板
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setBounds(10, 57, 570, 304);
		scrollPane.setBounds(10, 57, 680, 304);
		add(scrollPane);

		// 创建示例数据
		init();

		// 创建表格并将数据填充到表格中
		table = new JTable(data,columnNames);
		table.setBackground(tableColor);
		scrollPane.setViewportView(table);
		// 表格行选择监听器
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // 确保事件不会被重复触发
					int row = table.getSelectedRow();

					if (row != -1) {
						// 获取选中行的数据
						jt_id.setText(table.getValueAt(row, 1).toString());
						jt_name.setText((String) table.getValueAt(row, 2));
						jt_price.setText((String) table.getValueAt(row, 3));
						jt_type.setText((String) table.getValueAt(row, 4));
						jt_status.setText((String) table.getValueAt(row, 5));
					}
				}
			}
		});

		// 标签和文本框
		JLabel lblNewLabel = new JLabel("\u7F16\u53F7\uFF1A");
		lblNewLabel.setBounds(35, 378, 54, 15);
		add(lblNewLabel);

		jt_id = new JTextField();
		jt_id.setBounds(74, 372, 101, 29);
		add(jt_id);
		jt_id.setColumns(10);

		JLabel jl_name = new JLabel("\u540D\u79F0\uFF1A");
		jl_name.setBounds(200, 378, 54, 15);
		add(jl_name);

		jt_name = new JTextField();
		jt_name.setBounds(239, 372, 210, 29);
		add(jt_name);
		jt_name.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("\u5206\u7C7B\uFF1A");
		lblNewLabel_2.setBounds(200, 419, 54, 15);
		add(lblNewLabel_2);

		jt_type = new JTextField();
		jt_type.setBounds(239, 413, 105, 29);
		add(jt_type);
		jt_type.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("\u4EF7\u683C\uFF1A");
		lblNewLabel_3.setBounds(35, 419, 54, 15);
		add(lblNewLabel_3);

		jt_price = new JTextField();
		jt_price.setBounds(74, 413, 101, 29);
		add(jt_price);
		jt_price.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("状态");
		lblNewLabel_4.setBounds(365, 419, 54, 15);
		add(lblNewLabel_4);

		jt_status = new JTextField();
		jt_status.setBounds(404, 413, 101, 29);
		add(jt_status);
		jt_status.setColumns(10);

		// 搜索按钮及其监听器
		JButton jb_search = new JButton("搜索");
		jb_search.setBackground(buttonColor2);
		jb_search.setForeground(Color.BLACK);
		jb_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql4 = "select * from product where 1=1 ";
				//				获取输入的值
				String name = jt_name.getText();
				String type = jt_type.getText();
				String price = jt_price.getText();
				String status = jt_status.getText();
				ArrayList<Object> list = new ArrayList<>();
//
//				for (int i = 0; i < table.getRowCount(); ++i) {
//					if (jt_id.getText().equals(table.getValueAt(i, 1)) ||
//							name.equals(table.getValueAt(i, 2)) ||
//							price.equals(table.getValueAt(i, 3))||type.equals(table.getValueAt(i, 4))) {
//						table.setRowSelectionInterval(i, i);
//						return;
//					}
//				}
				if (!name.equals("")) {
					sql4 = sql4 + " and name=?";
					list.add(name);
				}
				if (!type.equals("")) {
					sql4 = sql4 + " and category=?  ";
					list.add(type);
				}
				if (!price.equals("")) {
					sql4 = sql4 + " and price=?";
					list.add(price);
				}
				if (!status.equals("")) {
					sql4 = sql4 + " and status=?";
					list.add(status);
				}
//
				try {
					ResultSet set = DataUtils.query(sql4, list.toArray(new Object[list.size()]));
					DefaultTableModel model = new DefaultTableModel(DataUtils.getSetArrays(set), columnNames);
					table.setModel(model);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null,"操作失败！请重试");
					throw new RuntimeException(ex);
				}

			}
		});
		jb_search.setBounds(35, 447, 93, 23);
		add(jb_search);

		// 修改按钮及其监听器
		JButton jb_update = new JButton("修改选中");
		jb_update.setBackground(buttonColor2);
		jb_update.setForeground(Color.BLACK);
		jb_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 修改选中
				// 获取输入的值
				String name = jt_name.getText();
				String type = jt_type.getText();
				String price = jt_price.getText();
				String id = jt_id.getText();
				try {
					DataUtils.Update(sql5, new Object[]{name, price, type, id});
					JOptionPane.showMessageDialog(null,"操作成功！");
					reload();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null,"操作失败！请重试");
					throw new RuntimeException(ex);
				}

			}
		});
		jb_update.setBounds(169, 447, 93, 23);
		add(jb_update);

		JButton jb_showImage = new JButton("图片");
		jb_showImage.setBackground(buttonColor2);
		jb_showImage.setForeground(Color.BLACK);
		jb_showImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int id = (int) table.getValueAt(row, 1);
					try {
						ResultSet set = DataUtils.query(sqlImage, new Object[]{id});
						if (set.next()) {
							String imagePath = set.getString("image_path");
							showImage(imagePath);
						} else {
							JOptionPane.showMessageDialog(null, "未找到对应的图片！");
						}
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, "操作失败！请重试");
						throw new RuntimeException(ex);
					}
				} else {
					JOptionPane.showMessageDialog(null, "请先选择一个产品！");
				}
			}
		});
		jb_showImage.setBounds(439, 447, 93, 23);
		add(jb_showImage);

		JButton jb_reset = new JButton("重置");
		jb_reset.setBackground(buttonColor2);
		jb_reset.setForeground(Color.BLACK);
		jb_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jt_name.setText("");
				jt_id.setText("");
				jt_price.setText("");
				jt_type.setText("");
				jt_status.setText("");
				reload();
			}
		});
		jb_reset.setBounds(304, 447, 93, 23);
		add(jb_reset);


	}

	// 显示图片的逻辑
	private void showImage(String imagePath) {
		JFrame frame = new JFrame("产品图片");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel NewLabel = new JLabel(imagePath);
		NewLabel.setBounds(51, 175, 217, 115);
		add(NewLabel);
		ImageIcon imageIcon = new ImageIcon(imagePath);
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);  // transform it back

		JLabel label = new JLabel(imageIcon);
		frame.add(label);

		frame.setVisible(true);
	}

	private void reload() {
		init();
		DefaultTableModel tableModel = new DefaultTableModel(data,columnNames);
		table.setModel(tableModel);
	}
}
