package text1;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JOptionPane;

import text3.User;
import text3.teacherUser;

/**
 * 学生/老师登入验证
 * @author  
 * 
 */
public class DemoTeacheroperation{

	public static void insertlogin(Connection con, String Name, String Teacher_id, String creditHour, String classTime, String classRoom, int State_id)throws Exception{
		String sql = "insert into course (name,teacher_id,CreditHour,ClassTime,ClassRoom,state_id) values (?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, Name);
		pstmt.setString(2, Teacher_id);
		pstmt.setString(3, creditHour);
		pstmt.setString(4, classTime);
		pstmt.setString(5, classRoom);
		pstmt.setLong(6, State_id);
		//System.out.println(pstmt);
		int i = pstmt.executeUpdate();
		if(i>0) {
			JOptionPane.showMessageDialog(null, "设置成功");
		}
		else {
			JOptionPane.showMessageDialog(null, "设置失败");
		}
		return;
	}
	
	public static void deletelogin(Connection con, String Name)throws Exception{
		String sql = "delete from course where name='"+Name+"'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.execute();
		//ResultSet rs = pstmt.getGeneratedKeys();
		return;
	}
}

