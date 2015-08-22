package cn.edu.fjnu.gis11.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {
	private static String driver=null;
	private static String url=null;
	private static String user=null;
	private static String password=null;
	
	static{
		try{
		InputStream inStream=JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
		Properties prop=new Properties();
		prop.load(inStream);
		driver=prop.getProperty("driver");
		url=prop.getProperty("url");
		user=prop.getProperty("user");
		password=prop.getProperty("password");
		Class.forName(driver);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, user, password);
	}
	
	public static void release(Connection conn,Statement st,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs=null;
		}
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			st=null;
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn=null;
		}
	}
	
}
