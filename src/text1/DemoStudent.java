package text1;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import text2.DButil;
import text3.User;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DemoStudent {
	
	/*学生选课界面*/
	private JFrame jf;
	private JTextField textField;
	private JPanel panel;
	private JComboBox comboBox;
	private JTable table;
	
	private DButil dbUtil = new DButil();

	private static String studentid;
	private static String password;
	private static String authority;
	static User currentUserstudent = new User(studentid,password,authority);
	private Studentmodel studentmodel;
	
	DemoStudent(User currentUser){
		currentUserstudent=currentUser;
		jf=new JFrame("学生界面");
		jf.setBackground(new Color(240, 248, 255));
		jf.getContentPane().setBackground(new Color(240, 248, 255));
		jf.setBounds(700, 220, 800, 475);
		jf.getContentPane().setLayout(new BorderLayout());
		
		/*输入框*/
		textField = new JTextField();
		textField.setBackground(new Color(255, 255, 255));
		textField.setBounds(411, 30, 172, 39);
		textField.setForeground(new Color(105, 105, 105));
		textField.setFont(new Font("微软雅黑 Light", Font.PLAIN, 15));
		textField.setText("检索词");
		textField.setColumns(10);
		
		/*下拉框*/
		comboBox = new JComboBox();
		comboBox.setBackground(new Color(248, 248, 255));
		comboBox.setFont(new Font("微软雅黑 Light", Font.PLAIN, 15));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"课程号", "课程名称", "学分", "上课时间", "上课地点", "状态"}));
		comboBox.setBounds(160, 30, 134, 38);
		
		/*查询按钮*/
		JButton btnNewButton = new JButton("查询");
		btnNewButton.setForeground(new Color(25, 25, 112));
		btnNewButton.setBounds(608, 31, 79, 38);
		btnNewButton.setBackground(new Color(240, 255, 255));
		btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		//查询响应事件
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				InquiryActionPerformed(e);
			}
		});
		
		/*文字提示*/
		JLabel lblNewLabel = new JLabel("查询条件");
		lblNewLabel.setForeground(new Color(25, 25, 112));
		lblNewLabel.setFont(new Font("黑体", Font.PLAIN, 18));
		lblNewLabel.setBounds(61, 32, 89, 38);
		
		//选课按钮
		JButton btnNewButton_1 = new JButton("选课");
		btnNewButton_1.setBackground(new Color(240, 255, 255));
		btnNewButton_1.setForeground(new Color(25, 25, 112));
		btnNewButton_1.setFont(new Font("黑体", Font.PLAIN, 19));
		btnNewButton_1.setBounds(61, 93, 626, 45);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectActionPerformed(e);
			}
		});

		
		/*放入panel中*/
		panel=new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setLayout(null);
		panel.add(comboBox);
		panel.add(btnNewButton);
		panel.add(textField);
		panel.add(lblNewLabel);
		panel.add(btnNewButton_1);
		jf.getContentPane().add(panel,BorderLayout.CENTER);
		
		

		/*选课信息栏*/
		Vector<Vector<Object>> rowData = new Vector<>();
		//将数据库的数据导入到表格
		String sql = "select * from course";
		try {
			Connection con = dbUtil.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Vector<Object> rowVector = new Vector<>();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String credithour = rs.getString("CreditHour");
				//System.out.println(credithour);
				String classtime = rs.getString("ClassTime");
				String classroom = rs.getString("ClassRoom");
				int stateid = rs.getInt("state_id");
				
				rowVector.addElement(id);
				rowVector.addElement(name);
				rowVector.addElement(credithour);
				rowVector.addElement(classtime);
				rowVector.addElement(classroom);
				rowVector.addElement(stateid);
				rowData.addElement(rowVector);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "发生错误");
		}
		//创建tablemodel Studentmodel
		studentmodel = Studentmodel.assembleModel(rowData);
		//table关联model
		table = new JTable(studentmodel);
		
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
		Vector<String> columns = Studentmodel.getcolumns();
		StudentCellRender render = new StudentCellRender();
		for(int i = 0; i < columns.size(); ++i) {
			TableColumn column = table.getColumn(columns.get(i));
			column.setCellRenderer(render);
			if(i==0 ||i==2) {
				column.setPreferredWidth(20);
			}
			else if(i==1 ||i==3){
				column.setPreferredWidth(150);
			}
			else {
				column.setPreferredWidth(60);
			}
		}
		//设置JScrollPane滚动面板
		JScrollPane jscrollPane = new JScrollPane(table);
		jscrollPane.setPreferredSize(new Dimension(250,248));
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
		menu2.setForeground(new Color(70, 130, 180));
		menu2.setBackground(new Color(240, 248, 255));
		menu2.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 17));
		menuBar.add(menu2);
		
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
		menu4.setBackground(new Color(240, 248, 255));
		menu4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		menuBar.add(menu4);
		//我的成绩响应事件
		menu4.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				new DemoStudentmark(currentUserstudent);
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
		
		jf.setVisible(true);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);	
		
	}
	
	private void SelectActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		int count = table.getSelectedRow();
		int num = table.getSelectedRows().length;
        if(num!=0){
        	String course_id;
        	String student_id = currentUserstudent.getstudentid();
        	course_id=table.getValueAt(count, 0).toString();
        	//System.out.println(course_id);
            if(JOptionPane.showConfirmDialog(null,"确定选择这门课吗？","消息",JOptionPane.OK_CANCEL_OPTION)==0) {
                Connection con = null;
                try {
                	con = dbUtil.getCon();
                	int i = DemoStudentoperation.selectlogin(con, student_id, course_id);
                	if(i==0) {
                		JOptionPane.showMessageDialog(null, "勿重复选课");
                	}
                } 
                catch (Exception e) {
                	JOptionPane.showMessageDialog(null,"请登入后选课");
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"请选择课程！");
        }
	}

	//点击查询事件处理
	private void InquiryActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String dropdown = this.comboBox.getSelectedItem().toString();
		String keyword = this.textField.getText();
		Vector<Vector<Object>> Data = new Vector<>();
		Connection con = null;
		try {
			con = dbUtil.getCon();
			Data = DemoStudentoperation.login(con,dropdown,keyword);
			studentmodel = Studentmodel.assembleModel(Data);
			Vector<String> columns = Studentmodel.getcolumns();
			StudentCellRender render = new StudentCellRender();
			for(int i = 0; i < columns.size(); ++i) {
				TableColumn column = table.getColumn(columns.get(i));
				column.setCellRenderer(render);
				if(i==0 ||i==2) {
					column.setPreferredWidth(20);
				}
				else if(i==1 ||i==3){
					column.setPreferredWidth(150);
				}
				else {
					column.setPreferredWidth(60);
				}
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "发生错误!!!");
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
		new DemoStudent(currentUserstudent);
	}
}

//自定义Component
class StudentCellRender extends DefaultTableCellRenderer{
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
class Studentmodel extends DefaultTableModel{
	
	static Vector<String> columnNames=new Vector<>();
	static {
		columnNames.addElement("课程号");
		columnNames.addElement("课程名称");
		columnNames.addElement("学分");
		columnNames.addElement("上课时间");
		columnNames.addElement("上课地点");
		columnNames.addElement("状态");
	}
	
	private Studentmodel() {
		super(null,columnNames);
	}
	private static Studentmodel studentmodel = new Studentmodel();
	public static Studentmodel assembleModel(Vector<Vector<Object>> data) {
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