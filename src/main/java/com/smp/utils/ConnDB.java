package com.smp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnDB {
	// 获取连接通道
	// Connection 
	public static Connection getConnection() {
		Connection conn = null;
		// 1.加载驱动类
		try {
			// Mysql驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 获取连接通道
			String url ="jdbc:mysql://localhost:3306/product_manage?useSSL=false&serverTimezone=Asia/Shanghai";
			String user = "root";
			String password = "zxzhou";
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("Conn to DB success!");
		} catch (ClassNotFoundException  e) {
			// 1.类名错误 2.外部jar没有引用
			System.out.println("Conn to DB failed! -- at ClassNotFoundException");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Conn to Db failed! -- at SQLException");
			e.printStackTrace();
		}
		return conn;
	}
	// 关闭资源
	public  static void closeDB(ResultSet rs,Statement pst,Connection conn) {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(pst!=null) {
				pst.close();
			}
			if(conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(ConnDB.getConnection());
	}
}
