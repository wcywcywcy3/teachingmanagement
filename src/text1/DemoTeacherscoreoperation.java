package text1;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import text2.DButil;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Color;

public class DemoTeacherscoreoperation extends JDialog{

	private JDialog jf;
	private JTextField textField;
	private DButil dbUtil = new DButil();
	private static String Coursename;
	private static String Studentname;
	
	DemoTeacherscoreoperation(String coursename, String studentname){
		Coursename = coursename;
		Studentname = studentname;
		this.setModal(true);
		
		jf=new JDialog(jf,"成绩输入");
		jf.getContentPane().setBackground(new Color(240, 248, 255));
		JButton btnNewButton = new JButton("输入");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		btnNewButton.setBounds(133, 157, 113, 31);
		jf.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				yesActionPerformed(e);
			}
		});
		
		textField = new JTextField();
		textField.setFont(new Font("等线", Font.PLAIN, 17));
		textField.setBounds(115, 79, 148, 36);
		jf.getContentPane().add(textField);
		textField.setColumns(10);
		
		jf.setBounds(900, 320, 400, 275);
		//jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		jf.setVisible(true);
	}
	private void yesActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String score = this.textField.getText();
		String Sql = "update curriculum,course,student set score="+score+" where curriculum.course_id=course.id and curriculum.student_id=student.studentid and course.name='"
		+Coursename+"' and student.studentname='"+Studentname+"'";
		try {
			Connection con = dbUtil.getCon();
			PreparedStatement pstmt = con.prepareStatement(Sql);
			//pstmt.setString(1,score);
			//System.out.println(pstmt);
			int i=pstmt.executeUpdate();
			if(i>0) {
				JOptionPane.showMessageDialog(null, "设置成功");
			}
			else {
				JOptionPane.showMessageDialog(null, "请选择学生");
			}			
		}catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "请输入成绩");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {
        	System.out.println(e);
        }
		new DemoTeacherscoreoperation(Coursename,Studentname);
	}

}
