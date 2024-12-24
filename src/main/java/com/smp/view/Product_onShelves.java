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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Product_onShelves extends JPanel {
    private JTextField jt_id;
    private JTextField jt_name;
    private JTextField jt_type;
    private JTextField jt_price;
    private JTextField jt_date;
    private JTable table;
    private Object[][] data;

    private Color tableColor = Color.decode("#104E8B");
    private Color buttonColor2 = Color.decode("#1874CD");

    // 创建表头
    String[] columnNames = {"序号","编号", "名称", "价格", "分类", "上架日期"};

    //	获取商品数据,SQL语句
    String sql1 = "select * from product_on_shelf order by id ";// 查询所有上架商品

    String sqlImage = "SELECT image_path from ProductImage where product_id = ? ";

    // 初始化数据
    void init() {
        try {
            ResultSet set = DataUtils.query(sql1, new Object[]{});
            Object[][] arrays = DataUtils.getSetArrays(set);
            data = arrays;
            // 将 Timestamp 转换成 String
            for (Object[] row : arrays) {
                if (row[5] instanceof Timestamp) {
                    row[5] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp) row[5]);
                }
            }
            data = arrays;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create the panel.
     */
    public Product_onShelves() {
        setLayout(null);

        JLabel lbNewLabel = new JLabel("架上商品一览");
        lbNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        lbNewLabel.setBounds(259, 10, 125, 20);
        add(lbNewLabel);
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
                        jt_date.setText((String) table.getValueAt(row, 5));
                    }
                }
            }
        });

        // 标签和文本框:
        // 编号:
        JLabel lblNewLabel = new JLabel("\u7F16\u53F7\uFF1A");
        lblNewLabel.setBounds(35, 378, 54, 15);
        add(lblNewLabel);

        jt_id = new JTextField();
        jt_id.setBounds(74, 372, 101, 29);
        add(jt_id);
        jt_id.setColumns(10);
        // 名称：
        JLabel jl_name = new JLabel("\u540D\u79F0\uFF1A");
        jl_name.setBounds(200, 378, 54, 15);
        add(jl_name);

        jt_name = new JTextField();
        jt_name.setBounds(239, 372, 210, 29);
        add(jt_name);
        jt_name.setColumns(10);
        //分类：
        JLabel lblNewLabel_2 = new JLabel("\u5206\u7C7B\uFF1A");
        lblNewLabel_2.setBounds(200, 419, 54, 15);
        add(lblNewLabel_2);

        jt_type = new JTextField();
        jt_type.setBounds(239, 413, 105, 29);
        add(jt_type);
        jt_type.setColumns(10);
        //价格：
        JLabel lblNewLabel_3 = new JLabel("\u4EF7\u683C\uFF1A");
        lblNewLabel_3.setBounds(35, 419, 54, 15);
        add(lblNewLabel_3);

        jt_price = new JTextField();
        jt_price.setBounds(74, 413, 101, 29);
        add(jt_price);
        jt_price.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("日期:");
        lblNewLabel_4.setBounds(365, 419, 54, 15);
        add(lblNewLabel_4);

        jt_date = new JTextField();
        jt_date.setBounds(404, 413, 180, 29);
        add(jt_date);
        jt_date.setColumns(10);

        // 搜索按钮及其监听器
        JButton jb_search = new JButton("搜索");
        jb_search.setBackground(buttonColor2);
        jb_search.setForeground(Color.BLACK);
        jb_search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql4 = "select * from product_on_shelf where 1=1 ";
                //				获取输入的值
                String name = jt_name.getText();
                String type = jt_type.getText();
                String price = jt_price.getText();
                String pdate = jt_date.getText();
                ArrayList<Object> list = new ArrayList<>();
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
                if (!pdate.equals("")) {
                    pdate = "%" + pdate + "%";
                    sql4 = sql4 + " and shelf_date LIKE ?";
                    list.add(pdate);
                }
                try {
                    ResultSet set = DataUtils.query(sql4, list.toArray(new Object[list.size()]));
                    Object[][] arrays = DataUtils.getSetArrays(set);
                    // Timestamp 转换成 String
                    for (Object[] row : arrays) {
                        if (row[5] instanceof Timestamp) {
                            row[5] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp) row[5]);
                        }
                    }
                    DefaultTableModel model = new DefaultTableModel(arrays, columnNames);
                    table.setModel(model);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"操作失败！请重试");
                    throw new RuntimeException(ex);
                }

            }
        });
        jb_search.setBounds(35, 447, 93, 23);
        add(jb_search);

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

        // 重置
        JButton jb_reset = new JButton("重置");
        jb_reset.setBackground(buttonColor2);
        jb_reset.setForeground(Color.BLACK);
        jb_reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jt_name.setText("");
                jt_id.setText("");
                jt_price.setText("");
                jt_type.setText("");
                jt_date.setText("");
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

    public void reload() {
        init();
        DefaultTableModel tableModel = new DefaultTableModel(data,columnNames);
        table.setModel(tableModel);
    }

}
