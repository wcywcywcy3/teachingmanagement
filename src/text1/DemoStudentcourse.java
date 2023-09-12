package text1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
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
import javax.swing.table.TableModel;

import text2.DButil;
import text3.User;
import javax.swing.JLabel;

public class DemoStudentcourse {

	private JFrame jf;
	private JTable table;
	private Studentmodel2 studentmodel2;
	private JPanel panel;
	private DButil dbUtil = new DButil();
	
	private static String studentid;
	private static String password;
	private static String authority;
	static User currentUserstudent = new User(studentid,password,authority);
	
	DemoStudentcourse(User currentUser){
		currentUserstudent=currentUser;
		jf=new JFrame("学生界面");
		jf.getContentPane().setFont(new Font("微軟正黑體 Light", Font.PLAIN, 18));
		jf.getContentPane().setBackground(new Color(240, 248, 255));
		
		/*导出按钮*/
		JButton btnNewButton = new JButton("EXPORT");
		btnNewButton.setForeground(new Color(25, 25, 112));
		btnNewButton.setBounds(316, 54, 129, 42);
		btnNewButton.setBackground(new Color(240, 255, 255));
		btnNewButton.setFont(new Font("等线", Font.PLAIN, 23));
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ExportActionPerformed(e);
			}
		});
		panel=new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setLayout(null);
		panel.add(btnNewButton);
		jf.getContentPane().add(panel,BorderLayout.CENTER);
		
		/*下半部分的课程展示栏*/
		String studentid = currentUser.getstudentid();
		Vector<Vector<Object>> rowData = new Vector<>();
		//将数据库的数据导入到表格
		String sql = "select * from course,curriculum,teacher where curriculum.course_id= course.id and teacher.teacherid= course.teacher_id and curriculum.student_id="+studentid;
		try {
			Connection con = dbUtil.getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			//System.out.println(currentUser.getname());
			while(rs.next()) {
				Vector<Object> rowVector = new Vector<>();
				String name = rs.getString("name");
				String teachername = rs.getString("designation");
				String classtime = rs.getString("ClassTime");
				String classroom = rs.getString("ClassRoom");
				//System.out.println(currentUser.getname());
				rowVector.addElement(name);
				rowVector.addElement(teachername);
				rowVector.addElement(classtime);
				rowVector.addElement(classroom);
				rowData.addElement(rowVector);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "发生错误");
		}
		//创建tablemodel Studentmodel2
		studentmodel2 = Studentmodel2.assembleModel(rowData);
		//table关联model
		table = new JTable(studentmodel2);
		
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
		Vector<String> columns = Studentmodel2.getcolumns();
		StudentCellRender2 render = new StudentCellRender2();
		for(int i = 0; i < columns.size(); ++i) {
			TableColumn column = table.getColumn(columns.get(i));
			column.setCellRenderer(render);
			if(i==0) {
				column.setPreferredWidth(80);
			}
			else if(i==1 ||i==3){
				column.setPreferredWidth(20);
			}
			else {
				column.setPreferredWidth(150);
			}
		}
		//设置JScrollPane滚动面板
		JScrollPane jscrollPane = new JScrollPane(table);
		jscrollPane.setPreferredSize(new Dimension(250,230));
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
		//我的课表响应事件
		menu1.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				new DemoStudenthome(currentUserstudent);
				jf.dispose();
			}
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("愁啥");
			}
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("不懂");
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
		menu3.setForeground(new Color(70, 130, 180));
		menu3.setBackground(new Color(240, 248, 255));
		menu3.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 17));
		menuBar.add(menu3);
						
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
				//System.out.println("我的成绩");
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
		
		jf.setBounds(700, 220, 800, 475);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}

	private void ExportActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub  
         try {
        	 ExcelExporter export = new ExcelExporter();
        	 export.exportTable(table, new File("课表.xls"));
        	 JOptionPane.showMessageDialog(null, "完成");
         } catch (IOException ex) {
        	 JOptionPane.showMessageDialog(null, "信息错误");
             ex.printStackTrace();
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
		new DemoStudentcourse(currentUserstudent);
	}

}

class ExcelExporter {
	/**导出JTable到excel */
    public void exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));  
        for(int i=0; i < model.getColumnCount(); i++) {
            bWriter.write(model.getColumnName(i));
            bWriter.write("\t");
        }
        bWriter.newLine();
        for(int i=0; i< model.getRowCount(); i++) {
            for(int j=0; j < model.getColumnCount(); j++) {
                bWriter.write(model.getValueAt(i,j).toString());
                bWriter.write("\t");
            }
            bWriter.newLine();
        }
        bWriter.close();
    }
}


//自定义Component
class StudentCellRender2 extends DefaultTableCellRenderer{
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
class Studentmodel2 extends DefaultTableModel{
	
	static Vector<String> columnNames=new Vector<>();
	static {
		columnNames.addElement("课程名称");
		columnNames.addElement("老师");
		columnNames.addElement("上课时间");
		columnNames.addElement("上课地点");
	}
	
	private Studentmodel2() {
		super(null,columnNames);
	}
	private static Studentmodel2 studentmodel = new Studentmodel2();
	public static Studentmodel2 assembleModel(Vector<Vector<Object>> data) {
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
