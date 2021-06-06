package net.wms.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

	private String Driver_name =
			"jdbc:mysql://81.69.245.16:3306/stock_manager?characterEncoding=utf8";

	private String USER = "root";

	private String PASS = "";

	public static Connection con;

	public DB(){
		try {

			Class.forName("com.mysql.jdbc.Driver");

			con = 
			DriverManager.getConnection(
			Driver_name, USER, PASS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnection(){
		if(con == null){
			new DB();
		}
		return con;
	}
}
