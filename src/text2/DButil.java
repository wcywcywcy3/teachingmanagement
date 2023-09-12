package text2;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库工具类
 * @author DELL
 *
 */

public class DButil {
	//数据库连接
	private String dbUrl="jdbc:mysql://localhost:3306/teachingmanagement?useUnicode=true&characterEncoding=UTF-8";
	
	private String dbUserName="root";
	private String dbPassword="753608";
	private String jdbcName="com.mysql.jdbc.Driver";
	
	public Connection getCon()throws Exception{
		Class.forName(jdbcName);
		Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;
	}
	
	public void closeCon(Connection con)throws Exception{
		if(con != null) {
			con.close();
		}
	}
	
	public static void main(String[] args) {
		DButil dbUtil = new DButil();
		try {
			dbUtil.getCon();
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
			
	}
}
