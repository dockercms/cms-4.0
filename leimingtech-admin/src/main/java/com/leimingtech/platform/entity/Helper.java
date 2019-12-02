package com.leimingtech.platform.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Helper {
	
 public Connection  requestConnection() {
  Connection conn = null;
  String url = null;
  String user = null;
  String password = null;
  try {
   Class.forName("com.mysql.jdbc.Driver"); //加载mysq驱动
  } catch (ClassNotFoundException e) {
   System.out.println("驱动加载错误");
   e.printStackTrace();//打印出错详细信息
  }
  try {
   url = "jdbc:mysql://192.168.1.240:3306/lmcms?useUnicode=true&characterEncoding=UTF-8";//简单写法：url = "jdbc:myqsl://localhost/test(数据库名)? user=root(用户)&password=yqs2602555(密码)";
   user = "root";
   password = "root";
   conn = DriverManager.getConnection(url,user,password);
  } catch (SQLException e) {
   System.out.println("数据库链接错误");
   e.printStackTrace();
  }
 return conn;
}
}