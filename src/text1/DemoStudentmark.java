package text1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.ListSelectionModel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import text2.DButil;
import text3.User;
import javax.swing.JLabel;

public class DemoStudentmark {

	/*学生成绩界面*/
	private JFrame jf;
	private JTable table;
	private Studentmodel3 studentmodel3;
	private DButil dbUtil = new DButil();
	private JPanel panel;
	
	private static String studentid;
	private static String password;
	private static String authority;
	static User currentUserstudent = new User(studentid,password,authority);
	
	DemoStudentmark(User currentUser){
		currentUserstudent=currentUser;
		jf=new JFrame("学生界面");
		jf.getContentPane().setFont(new Font("微軟正黑體 Light", Font.PLAIN, 18));
		jf.getContentPane().setBackground(new Color(240, 248, 255));
		panel=new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setLayout(null);
		jf.getContentPane().add(panel,BorderLayout.CENTER);
		
		/*上部分词云*/
		JLabel lblNewLabel_5 = new JLabel("熟读而精思");
		lblNewLabel_5.setFont(new Font("宋体", Font.BOLD, 22));
		lblNewLabel_5.setBounds(119, 34, 121, 61);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel = new JLabel("广见闻，多阅读，勤实验");
		lblNewLabel.setFont(new Font("微软雅黑 Light", Font.PLAIN, 13));
		lblNewLabel.setBounds(216, 93, 188, 61);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("非静无以成学");
		lblNewLabel_1.setFont(new Font("幼圆", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(53, 126, 121, 44);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("学而时习之，不亦说乎？");
		lblNewLabel_2.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(405, 73, 212, 56);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("或作或辍，一曝十寒");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 19));
		lblNewLabel_3.setBounds(537, 121, 198, 51);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("博观而约取，厚积而薄发");
		lblNewLabel_4.setFont(new Font("微軟正黑體 Light", Font.BOLD, 16));
		lblNewLabel_4.setBounds(339, 10, 198, 73);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_7 = new JLabel("温故而知新");
		lblNewLabel_7.setFont(new Font("黑体", Font.BOLD, 18));
		lblNewLabel_7.setBounds(302, 164, 116, 26);
		panel.add(lblNewLabel_7);
		
		/*下半部分的课程展示栏*/
		String studentid = currentUser.getstudentid();
		Vector<Vector<Object>> rowData = new Vector<>();
		//将数据库的数据导入到表格
		String sql = "select * from course,curriculum where curriculum.course_id= course.id and curriculum.student_id="+studentid;
		try {
			Connection con = dbUtil.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			//System.out.println(currentUser.getname());
			while(rs.next()) {
				Vector<Object> rowVector = new Vector<>();
				String name = rs.getString("name");
				String credithour = rs.getString("CreditHour");
				String score = rs.getString("score");
				//System.out.println(currentUser.getname());
				rowVector.addElement(name);
				rowVector.addElement(credithour);
				rowVector.addElement(score);
				rowData.addElement(rowVector);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "发生错误");
		}
		//创建tablemodel Studentmodel3
		studentmodel3 = Studentmodel3.assembleModel(rowData);
		//table关联model
		table = new JTable(studentmodel3);
		
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
		Vector<String> columns = Studentmodel3.getcolumns();
		StudentCellRender3 render = new StudentCellRender3();
		for(int i = 0; i < columns.size(); ++i) {
			TableColumn column = table.getColumn(columns.get(i));
			column.setCellRenderer(render);
			if(i==0) {
				column.setPreferredWidth(120);
			}
			else if(i==1 ||i==2){
				column.setPreferredWidth(20);
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
				new DemoStudenthome(currentUserstudent);
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
							
		JMenu menu2 = new JMenu("选课中心");
		menu2.setBackground(new Color(240, 248, 255));
		menu2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		menuBar.add(menu2);
		//选课中心响应时间
		menu2.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				new DemoStudent(currentUserstudent);
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
							
		JMenu menu3 = new JMenu("我的课表");
		menu3.setBackground(new Color(240, 248, 255));
		menu3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		menuBar.add(menu3);
		//我的课表响应事件
		menu3.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				new DemoStudentcourse(currentUserstudent);
				jf.dispose();
				//System.out.println("我的课表");
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
						
		JMenu menu4 = new JMenu("我的成绩");
		menu4.setForeground(new Color(70, 130, 180));
		menu4.setBackground(new Color(240, 248, 255));
		menu4.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 17));
		menuBar.add(menu4);
		
		jf.setBounds(700, 220, 800, 475);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setVisible(true);
		
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
		new DemoStudentmark(currentUserstudent);
	}
}

//自定义Component
class StudentCellRender3 extends DefaultTableCellRenderer{
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

//自定义model
class Studentmodel3 extends DefaultTableModel{
	
	static Vector<String> columnNames=new Vector<>();
	static {
		columnNames.addElement("课程名称");
		columnNames.addElement("学分");
		columnNames.addElement("成绩");
	}
	
	private Studentmodel3() {
		super(null,columnNames);
	}
	private static Studentmodel3 studentmodel = new Studentmodel3();
	public static Studentmodel3 assembleModel(Vector<Vector<Object>> data) {
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
