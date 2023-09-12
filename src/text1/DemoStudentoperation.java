package text1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.xml.crypto.Data;

import text3.User;

public class DemoStudentoperation {
	
	public static Vector<Vector<Object>> login(Connection con,String dropdown,String keyword )throws SQLException{
		Vector<Vector<Object>> data = new Vector<>();
		if("课程号".equals(dropdown)) {
			dropdown = "id";
		}
		else if("课程名称".equals(dropdown)) {
			dropdown = "name";
		}
		else if("学分".equals(dropdown)) {
			dropdown = "CreditHour";
		}
		else if("上课时间".equals(dropdown)) {
			dropdown = "ClassTime";
		}
		else if("上课地点".equals(dropdown)) {
			dropdown = "ClassRoom";
		}
		else {
			dropdown = "state_id";
		}
		String sql = "select * from course where "+dropdown+" like '%"+keyword+"%'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Vector<Object> oneRecord = new Vector<>();
			int id = rs.getInt("id");
			String name = rs.getString("name");
			float credithour = rs.getFloat("CreditHour");
			String classtime = rs.getString("ClassTime");
			String classroom = rs.getString("ClassRoom");
			int stateid = rs.getInt("state_id");
			oneRecord.addElement(id);
			oneRecord.addElement(name);
			oneRecord.addElement(credithour);
			oneRecord.addElement(classtime);
			oneRecord.addElement(classroom);
			oneRecord.addElement(stateid);
			data.addElement(oneRecord);
			//System.out.println(name+classroom);
		}
		return data;
	}
	public static int selectlogin(Connection con, String student_id, String course_id)throws Exception{
		String Sql = "select * from curriculum where student_id=? and course_id=?";
		PreparedStatement Pstmt = con.prepareStatement(Sql);
		Pstmt.setString(1, student_id);
		Pstmt.setString(2, course_id);
		ResultSet rs = Pstmt.executeQuery();
		if(!rs.next()) {
			return 0;
		}
		
		String sql = "insert into curriculum (student_id,course_id) values (?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, student_id);
		pstmt.setString(2, course_id);
		pstmt.executeUpdate();
		return 1;
	}
}


