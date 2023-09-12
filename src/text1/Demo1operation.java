package text1;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import text3.User;
import text3.teacherUser;

/**
 * 学生/老师登入验证
 * @author  
 * 
 */
public class Demo1operation{

	public User login(Connection con,User user)throws Exception{
		User resultUser = null;
		String sql = "select * from student where studentid=? and password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getstudentid());
		pstmt.setString(2, user.getPassword());
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			resultUser = new User();
			//System.out.println(rs.getString("studentid"));
			resultUser.setstudentid(rs.getString("studentid"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setname(rs.getString("studentname"));
			resultUser.setclassnum(rs.getString("classnum"));
		}
		return resultUser;
	}
	
	public teacherUser loginteacher(Connection con,teacherUser user)throws Exception{
		teacherUser resultUser = null;
		String sql = "select * from teacher where teacherid=? and password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getteacherid());
		pstmt.setString(2, user.getPassword());
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			resultUser = new teacherUser();
			//System.out.println(rs.getString("teacherid"));
			resultUser.setteacherid(rs.getString("teacherid"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setname(rs.getString("designation"));
			resultUser.setdepartment(rs.getString("department"));
		}
		return resultUser;
	}
}
