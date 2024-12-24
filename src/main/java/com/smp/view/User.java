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

public class User extends JPanel {
    private JTextField jt_id;
    private JTextField jt_username;
    private JTextField jt_pwd;
    private JTable table;

    private Object[][] data;

    private Color tableColor = Color.decode("#104E8B");
    private Color buttonColor2 = Color.decode("#1874CD");

    // 创建表头
    String[] columnNames = {"序号","编号", "用户名", "密码", "状态"};

    //	获取商品数据,SQL语句

    String sql1 =   "SELECT \n" +
            "    u.id,\n" +
            "    u.username,\n" +
            "    u.password,\n" +
            "    CASE\n" +
            "        WHEN a.id IS NOT NULL THEN '1'\n" +
            "        ELSE '0'\n" +
            "    END AS status\n" +
            "FROM \n" +
            "    user u\n" +
            "LEFT JOIN \n" +
            "    admin a ON u.id = a.id;\n";// 查询所有上架商品

    String sql2 = "insert into user (id, username, password) values (0,?,?)"; // 插入商品

    String sql3 = "delete from user where id=?";// 删除商品

    String sql5 = "update user set username=? , password=? where id=?";// 更新商品

    String sql7 = "select * from user where username=? && password=?";// 查询商品

    String sql9 = "INSERT INTO admin (id, username, password) SELECT * FROM user WHERE id = ?";
    String sql10 = "delete from admin WHERE id = ? ";
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
    public User(int uid) {
        setLayout(null);
        // 新增按钮及其监听器
        JButton jb_add = new JButton("新增");
        jb_add.setBackground(buttonColor2);
        jb_add.setForeground(Color.BLACK);
        jb_add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//				获取输入的值
                String username = jt_username.getText();
                String pwd = jt_pwd.getText();
                if (username.equals("") || pwd.equals("") )
                {
                    JOptionPane.showMessageDialog(null,"请填写完整信息！编号无需填写！");
                } else {
                    try {
                        int id = 0;
                        int update = DataUtils.Update(sql2, new Object[]{username, pwd});
                        reload();
//					获取刚刚的商品，创建库存记录
                        ResultSet set = DataUtils.query(sql7, new Object[]{username, pwd});
                        while (set.next()) {
                            id = (int) set.getLong(1);
                        }
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
                    if (id != uid) {
                        try {
                            DataUtils.Update(sql3, new Object[]{id});
                            reload();
                            JOptionPane.showMessageDialog(null,"操作成功！");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,"操作失败！请重试");
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,"此为当前用户");
                    }
                }
            }
        });
        jb_del.setBounds(123, 10, 93, 23);
        add(jb_del);

        // 赋予按钮及其监听器
        JButton jb_grant = new JButton("赋予权限");
        jb_grant.setBackground(buttonColor2);
        jb_grant.setForeground(Color.BLACK);
        jb_grant.setBounds(273, 10, 120, 23);
        add(jb_grant);
        jb_grant.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 上架选中的商品
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) table.getValueAt(row, 1);
                    if (id != uid) {
                        try {
                            DataUtils.Update(sql9, new Object[]{id});
                            reload();
                            JOptionPane.showMessageDialog(null,"赋予权限成功！");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "赋予权限失败！请重试");
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "此为当前用户");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请先选择一个用户！");
                }
            }
        });
        // 收回权限按钮及其监听器
        JButton jb_revoke = new JButton("收回权限");
        jb_revoke.setBackground(buttonColor2);
        jb_revoke.setForeground(Color.BLACK);
        //jb_revoke.setBounds(386, 10, 120, 23);
        jb_revoke.setBounds(399, 10, 120, 23);
        add(jb_revoke);
        jb_revoke.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 上架选中的商品
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) table.getValueAt(row, 1);
                    if (id != 1 && id != uid) {
                        try {
                            DataUtils.Update(sql10, new Object[]{id});
                            reload();
                            JOptionPane.showMessageDialog(null,"收回权限成功！");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "收回权限失败！请重试");
                            throw new RuntimeException(ex);
                        }
                    } else if (id == 1){
                        JOptionPane.showMessageDialog(null, "用户1为超级用户，不能收回权限！");
                    } else {
                        JOptionPane.showMessageDialog(null, "此为当前用户");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请先选择一个用户！");
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
                        jt_username.setText((String) table.getValueAt(row, 2));
                        jt_pwd.setText((String) table.getValueAt(row, 3));
                    }
                }
            }
        });

        // 标签和文本框
        JLabel lblNewLabel = new JLabel("编号:");
        lblNewLabel.setBounds(35, 378, 54, 18);
        add(lblNewLabel);

        jt_id = new JTextField();
        jt_id.setBounds(74, 372, 101, 35);
        add(jt_id);
        jt_id.setColumns(10);

        JLabel jl_name = new JLabel("用户名:");
        jl_name.setBounds(200, 378, 54, 18);
        add(jl_name);

        jt_username = new JTextField();
        jt_username.setBounds(255, 372, 160, 35);
        add(jt_username);
        jt_username.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("密码: ");
        lblNewLabel_4.setBounds(430, 378, 120, 18);
        add(lblNewLabel_4);

        jt_pwd = new JTextField();
        jt_pwd.setBounds(469, 372, 160, 35);
        add(jt_pwd);
        jt_pwd.setColumns(10);

        // 搜索按钮及其监听器
        JButton jb_search = new JButton("搜索");
        jb_search.setBackground(buttonColor2);
        jb_search.setForeground(Color.BLACK);
        jb_search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql4 = "select * from user where 1=1 ";
                //				获取输入的值
                String id = jt_id.getText();
                String name = jt_username.getText();
                String pwd = jt_pwd.getText();
                ArrayList<Object> list = new ArrayList<>();
                if (!id.equals("")) {
                    sql4 = sql4 + " and id=?";
                    list.add(id);
                }
                if (!name.equals("")) {
                    sql4 = sql4 + " and username=?";
                    list.add(name);
                }
                if (!pwd.equals("")) {
                    sql4 = sql4 + " and password=?  ";
                    list.add(pwd);
                }
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
                String name = jt_username.getText();
                String pwd = jt_pwd.getText();
                String id = jt_id.getText();
                try {
                    DataUtils.Update(sql5, new Object[]{name, pwd, id});
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

        JButton jb_reset = new JButton("重置");
        jb_reset.setBackground(buttonColor2);
        jb_reset.setForeground(Color.BLACK);
        jb_reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jt_id.setText("");
                jt_username.setText("");
                jt_pwd.setText("");
                reload();
            }
        });
        jb_reset.setBounds(304, 447, 93, 23);
        add(jb_reset);
    }

    private void reload() {
        init();
        DefaultTableModel tableModel = new DefaultTableModel(data,columnNames);
        table.setModel(tableModel);
    }
}
