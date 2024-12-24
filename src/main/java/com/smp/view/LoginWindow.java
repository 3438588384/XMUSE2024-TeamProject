package com.smp.view;

import com.smp.utils.DataUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

class LoginWindow extends JFrame implements ActionListener {
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;
    private static String loggedInUser;
    private Color customColor = Color.decode("#191970");
    private Color customColor2 = Color.decode("#2b4490");

    public LoginWindow() {
        setTitle("用户登录");

        // 创建输入框和按钮
        userField = new JTextField(30);
        passwordField = new JPasswordField(30);

        // 设置输入框的背景色
        userField.setBackground(customColor2);
        passwordField.setBackground(customColor2);

        loginButton = new JButton("登录");
        cancelButton = new JButton("取消");
        loginButton.setBackground(customColor2); // 按钮背景色
        cancelButton.setBackground(customColor2); // 按钮背景色

        // 添加事件监听器
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // 创建面板并添加组件
        JPanel panel1 = new JPanel(new GridLayout(2, 1, 5, 5));
        panel1.setBackground(customColor);
        panel1.add(new JLabel("用户："));
        panel1.add(new JLabel("密码："));

        JPanel panel2 = new JPanel(new GridLayout(2, 1, 5, 5));
        panel2.setBackground(customColor);
        panel2.add(userField);
        panel2.add(passwordField);

        JPanel panel3 = new JPanel(new FlowLayout());
        panel3.setBackground(customColor);
        panel3.add(loginButton);
        panel3.add(cancelButton);

        // 添加组件到窗口
        Container container = getContentPane();
        container.setLayout(new BorderLayout(5, 5));
        container.setBackground(customColor); // 设置窗口背景色
        container.add(panel1, BorderLayout.WEST);
        container.add(panel2, BorderLayout.CENTER);
        container.add(panel3, BorderLayout.SOUTH);

        // 设置窗口属性
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 实现事件监听器接口
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {  // 登录
            String user = userField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            if (user.isEmpty() || pass.isEmpty()) {  // 用户名或密码为空
                JOptionPane.showMessageDialog(this, "用户名或密码不能为空！");
            } else if (isOK(user, pass)) {  // 用户名和密码正确
                loggedInUser = user; // 保存登录的用户名
                dispose();  // 关闭当前窗口
                boolean isAdmin = isAdmin(user); // 检查是否为管理员
                int uid = findid(user, pass);
                new MainWin(user, isAdmin, uid).setVisible(true);  // 打开表格窗口，并传递用户名和管理员状态
            } else {  // 用户名或密码错误
                JOptionPane.showMessageDialog(this, "用户名或密码错误！");
            }
        } else if (e.getSource() == cancelButton) {  // 取消
            System.exit(0);  // 退出程序
        }
    }

    public boolean isOK(String user, String password) {
        String sql = "select * from user where username=?";
        try {
            ResultSet set = DataUtils.query(sql, new Object[]{user});
            if (set.next()) {
                String name = set.getString(2);
                String pwd = set.getString(3);
                if (user.equals(name) && password.equals(pwd)) {
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean isAdmin(String user) {
        String sql = "select * from admin where username=?";
        try {
            ResultSet set = DataUtils.query(sql, new Object[]{user});
            return set.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findid(String user, String password) {
        String sql = "select * from user where username=?";
        try {
            ResultSet set = DataUtils.query(sql, new Object[]{user});
            if (set.next()) {
                String name = set.getString(2);
                String pwd = set.getString(3);
                if (user.equals(name) && password.equals(pwd)) {
                    return Integer.parseInt(set.getString(1));
                }
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new LoginWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
