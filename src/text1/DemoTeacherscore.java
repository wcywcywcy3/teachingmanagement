package text1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import text2.DButil;
import text3.teacherUser;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JButton;

public class DemoTeacherscore {
	
	//老师增删课程
	private JFrame jf;
	private JPanel panel;
	private JTable table;
	
	private static String tecaherid;
	private static String password;
	private static String authority;
	
	private Studentmodel5 studentmodel5;
	static teacherUser currentUserteacher = new teacherUser(tecaherid,password,authority);
	private DButil dbUtil = new DButil();
	
	DemoTeacherscore(teacherUser currentUser){
		currentUserteacher=currentUser;
		String teachertid = currentUser.getteacherid();
		int num=0;
		try {
			String Sql = "select count(*) from course where course.teacher_id="+teachertid;
			Connection con = dbUtil.getCon();
			PreparedStatement pstmt = con.prepareStatement(Sql);
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				num = rs.getInt(1);
			}
		}
		catch (Exception evt) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "发生错误");
		}
		
		int Num=0;
		try {
			String Sql = "select count(distinct(name)) from course,curriculum,student where course.id=curriculum.course_id and curriculum.student_id=studentid and course.teacher_id="+teachertid;
			Connection con = dbUtil.getCon();
			PreparedStatement pstmt = con.prepareStatement(Sql);
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				Num = rs.getInt(1);
			}
		}
		catch (Exception evt) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "发生错误");
		}
		
		jf=new JFrame("老师界面");
		panel=new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setLayout(null);
		jf.getContentPane().add(panel,BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("你教学了"+num+"门课");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("华文中宋", Font.PLAIN, 24));
		lblNewLabel.setBounds(74, 54, 172, 62);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("共有"+Num+"位同学选择你的课程，请为他们输入、修改成绩");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("华文中宋", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(63, 126, 509, 80);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.setForeground(new Color(25, 25, 112));
		btnNewButton.setBackground(new Color(240, 255, 255));
		btnNewButton.setFont(new Font("等线", Font.PLAIN, 23));
		btnNewButton.setBounds(554, 88, 104, 44);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				enternActionPerformed(e);
			}
		});

		
		/*下半部分的课程展示栏*/
		Vector<Vector<Object>> rowData = new Vector<>();
		//老师当前教学的课程展示
		String sql = "select * from course,curriculum,student where course.id=curriculum.course_id and curriculum.student_id=studentid and course.teacher_id="+teachertid;
		try {
			Connection con = dbUtil.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			//System.out.println(currentUser.getname());
			while(rs.next()) {
				Vector<Object> rowVector = new Vector<>();
				String name = rs.getString("name");
				String studentname = rs.getString("studentname");
				String score = rs.getString("score");
				rowVector.addElement(name);
				rowVector.addElement(studentname);
				rowVector.addElement(score);
				rowData.addElement(rowVector);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "发生错误");
		}
		//创建tablemodel Studentmodel5
		studentmodel5 = Studentmodel5.assembleModel(rowData);
		//table关联model
		table = new JTable(studentmodel5);
		
		//设置表头
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		tableHeader.setForeground(Color.BLACK);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		//设置表体
		table.setFont(new Font("微软雅黑 Light", Font.PLAIN, 16));
		table.setForeground(Color.black);
		table.setGridColor(Color.white);
		table.setRowHeight(30);
		//设置多行选择
		table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//设置表格列的渲染方式
		Vector<String> columns = Studentmodel5.getcolumns();
		StudentCellRender5 render = new StudentCellRender5();
		for(int i = 0; i < columns.size(); ++i) {
			TableColumn column = table.getColumn(columns.get(i));
			column.setCellRenderer(render);
			if(i==0) {
				column.setPreferredWidth(120);
			}
		}
		//设置JScrollPane滚动面板
		JScrollPane jscrollPane = new JScrollPane(table);
		jscrollPane.setPreferredSize(new Dimension(250,180));
		jf.getContentPane().add(jscrollPane,BorderLayout.SOUTH);
		
		
		//头部菜单栏
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(240, 248, 255));
		menuBar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		jf.setJMenuBar(menuBar);
							
		JMenu menu1 = new JMenu("首页");
		menu1.setBackground(new Color(240, 248, 255));
		menu1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		menuBar.add(menu1);
		//首页响应事件
		menu1.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				new DemoTeacherhome(currentUserteacher);
				jf.dispose();
			}
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
			}
		});		
		
		JMenu menu2 = new JMenu("课程设置");
		menu2.setBackground(new Color(240, 248, 255));
		menu2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		menuBar.add(menu2);	
		//课程设置响应事件	
		menu2.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				new DemoTeacher(currentUserteacher);
				jf.dispose();
			}
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
			}
		});
						
		JMenu menu3 = new JMenu("成绩管理");
		menu3.setForeground(new Color(70, 130, 180));
		menu3.setBackground(new Color(240, 248, 255));
		menu3.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 17));
		menuBar.add(menu3);	
	
		
		jf.setBounds(700, 220, 800, 475);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	//输入成绩
	private void enternActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int count = table.getSelectedRow();
		int num = table.getSelectedRows().length;
       
        if(num!=0){
        	String coursename;
        	String studentname;
        	coursename=table.getValueAt(count, 0).toString(); 
        	studentname=table.getValueAt(count, 1).toString(); 
	        new DemoTeacherscoreoperation(coursename, studentname); 
        }
        else{
            JOptionPane.showMessageDialog(null,"请选择学生");
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
		new DemoTeacherscore(currentUserteacher);
	}
}

//自定义Component
class StudentCellRender5 extends DefaultTableCellRenderer{
	//在每一行的每一列显示之前都会调用
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(row % 2 == 0) {
			setBackground(Color.white);
		}
		else {
			setBackground(new Color(240, 248, 255));
		}
		setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}

class Studentmodel5 extends DefaultTableModel{
	
	static Vector<String> columnNames=new Vector<>();
	static {
		columnNames.addElement("课程名称");
		columnNames.addElement("学生");
		columnNames.addElement("成绩");
	}
	
	private Studentmodel5() {
		super(null,columnNames);
	}
	private static Studentmodel5 studentmodel = new Studentmodel5();
	public static Studentmodel5 assembleModel(Vector<Vector<Object>> data) {
		studentmodel.setDataVector(data, columnNames);
		return  studentmodel;
	}
	public static Vector<String> getcolumns(){
		return columnNames;
	}
	@Override
	public boolean isCellEditable(int row, int colum) {
		return false;
	}
	
}
