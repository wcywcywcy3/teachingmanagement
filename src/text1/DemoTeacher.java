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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class DemoTeacher {
	
	//老师增删课程
	private JFrame jf;
	private JPanel panel;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	
	private static String ID;
	private static String tecaherid;
	private static String password;
	private static String authority;
	
	private Studentmodel4 studentmodel4;
	static teacherUser currentUserteacher = new teacherUser(tecaherid,password,authority);
	private DButil dbUtil = new DButil();
	
	DemoTeacher(teacherUser currentUser){
		currentUserteacher=currentUser;
		
		jf=new JFrame("老师界面");
		panel=new JPanel();
		panel.setForeground(new Color(0, 0, 0));
		panel.setBackground(new Color(240, 248, 255));
		jf.getContentPane().add(panel,BorderLayout.CENTER);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(133, 52, 147, 28);
		textField.setFont(new Font("等线", Font.PLAIN, 15));
		textField.setColumns(10);
		panel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setBounds(394, 124, 147, 28);
		textField_1.setFont(new Font("等线", Font.PLAIN, 15));
		textField_1.setColumns(10);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(394, 52, 147, 28);
		textField_2.setFont(new Font("等线", Font.PLAIN, 15));
		textField_2.setColumns(10);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(133, 123, 147, 29);
		textField_3.setFont(new Font("等线", Font.PLAIN, 15));
		textField_3.setColumns(10);
		panel.add(textField_3);
		
		JLabel lblNewLabel = new JLabel("课程名称");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(43, 57, 75, 18);
		lblNewLabel.setFont(new Font("幼圆", Font.PLAIN, 16));
		panel.add(lblNewLabel);
		
		JLabel label = new JLabel("学分");
		label.setForeground(new Color(0, 0, 0));
		label.setBounds(339, 57, 37, 18);
		label.setFont(new Font("幼圆", Font.PLAIN, 16));
		panel.add(label);
		
		JLabel label_1 = new JLabel("上课时间");
		label_1.setForeground(new Color(0, 0, 0));
		label_1.setBounds(43, 121, 75, 37);
		label_1.setFont(new Font("幼圆", Font.PLAIN, 16));
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("上课地点");
		label_2.setForeground(new Color(0, 0, 0));
		label_2.setBounds(313, 130, 71, 18);
		label_2.setFont(new Font("幼圆", Font.PLAIN, 16));
		panel.add(label_2);
		
		JButton btnNewButton = new JButton("确认");
		btnNewButton.setForeground(new Color(25, 25, 112));
		btnNewButton.setBounds(612, 51, 75, 29);
		btnNewButton.setBackground(new Color(245, 255, 250));
		btnNewButton.setFont(new Font("等线", Font.PLAIN, 18));
		panel.add(btnNewButton);
		
		JButton button = new JButton("删除");
		button.setForeground(new Color(25, 25, 112));
		button.setFont(new Font("等线", Font.PLAIN, 18));
		button.setBackground(new Color(245, 255, 250));
		button.setBounds(612, 124, 75, 28);
		panel.add(button);
		
		JLabel lblNewLabel_1 = new JLabel("本界面课程管理有增添教学课程和删除所教课程功能 上方输入相关信息科后确认可申请新课程；而选中下方展示的自己所管理的课程可删除 ");
		lblNewLabel_1.setFont(new Font("微軟正黑體 Light", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(25, 179, 751, 15);
		panel.add(lblNewLabel_1);
		
		//提交按钮的响应事件
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				submitActionPerformed(e);
			}
		});
		//删除按钮
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				deleteActionPerformed(e);
			}
		});

			
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
		menu2.setForeground(new Color(70, 130, 180));
		menu2.setBackground(new Color(240, 248, 255));
		menu2.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 17));
		menuBar.add(menu2);		
						
		JMenu menu3 = new JMenu("成绩管理");
		menu3.setBackground(new Color(240, 248, 255));
		menu3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		menuBar.add(menu3);	
		//成绩管理响应事件	
		menu3.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				new DemoTeacherscore(currentUserteacher);
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
		
		/*下半部分的课程展示栏*/
		String teachertid = currentUser.getteacherid();
		Vector<Vector<Object>> rowData = new Vector<>();
		//老师当前教学的课程展示
		String sql = "select * from course where course.teacher_id="+teachertid;
		try {
			Connection con = dbUtil.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			//System.out.println(currentUser.getname());
			while(rs.next()) {
				Vector<Object> rowVector = new Vector<>();
				String name = rs.getString("name");
				String credithour = rs.getString("CreditHour");
				String classTime = rs.getString("ClassTime");
				String classRoom = rs.getString("ClassRoom");
				//System.out.println(currentUser.getname());
				rowVector.addElement(name);
				rowVector.addElement(credithour);
				rowVector.addElement(classTime);
				rowVector.addElement(classRoom);
				rowData.addElement(rowVector);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "发生错误");
		}
		//创建tablemodel Studentmodel4
		studentmodel4 = Studentmodel4.assembleModel(rowData);
		//table关联model
		table = new JTable(studentmodel4);
		
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
		Vector<String> columns = Studentmodel4.getcolumns();
		StudentCellRender4 render = new StudentCellRender4();
		for(int i = 0; i < columns.size(); ++i) {
			TableColumn column = table.getColumn(columns.get(i));
			column.setCellRenderer(render);
			if(i==0) {
				column.setPreferredWidth(80);
			}
			else if(i==1 ||i==3){
				column.setPreferredWidth(30);
			}
			else {
				column.setPreferredWidth(150);
			}
		}
		//设置JScrollPane滚动面板
		JScrollPane jscrollPane = new JScrollPane(table);
		jscrollPane.setPreferredSize(new Dimension(250,180));
		jf.getContentPane().add(jscrollPane,BorderLayout.SOUTH);
		
		
		ID = currentUser.getteacherid();
		jf.setBounds(700, 220, 800, 475);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	//删除响应事件执行
	protected void deleteActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		int count = table.getSelectedRow();
		int num = table.getSelectedRows().length;
       
        if(num!=0){
        	String name;
        	name=table.getValueAt(count, 0).toString();
            if(JOptionPane.showConfirmDialog(null,"确定删除所选的数据吗？","消息",JOptionPane.OK_CANCEL_OPTION)==0) {
                Connection con = null;
                try {
                	con = dbUtil.getCon();
                	DemoTeacheroperation.deletelogin(con, name);
                } 
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"请选择要删除的列！");
        }
	}
	
	//提交响应事件执行
	private void submitActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String name = this.textField.getText();
		String CreditHour = this.textField_2.getText();
		String ClassTime = this.textField_3.getText();
		String ClassRoom = this.textField_1.getText();
		Vector<Vector<Object>> Data = new Vector<>();
		Connection con = null;
		try {
			con = dbUtil.getCon();
			DemoTeacheroperation.insertlogin(con,name,ID,CreditHour,ClassTime,ClassRoom,3);
		} 
		catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "请输入正确信息");
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
		new DemoTeacher(currentUserteacher);
	}
}

//自定义Component
class StudentCellRender4 extends DefaultTableCellRenderer{
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
class Studentmodel4 extends DefaultTableModel{
	
	static Vector<String> columnNames=new Vector<>();
	static {
		columnNames.addElement("课程名称");
		columnNames.addElement("学分");
		columnNames.addElement("上课时间");
		columnNames.addElement("上课地点");
	}
	
	private Studentmodel4() {
		super(null,columnNames);
	}
	private static Studentmodel4 studentmodel = new Studentmodel4();
	public static Studentmodel4 assembleModel(Vector<Vector<Object>> data) {
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
