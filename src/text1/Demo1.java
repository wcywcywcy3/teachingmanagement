package text1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import text2.DButil;
import text2.StringUtil;
import text3.User;
import text3.teacherUser;
public class Demo1 {

	/*登入界面*/
	private JFrame jf;
	private JTextField textField1;
	private JTextField textField2;
	private ButtonGroup group = new ButtonGroup();//单选框唯一匹配控件
	
	private DButil dbUtil = new DButil();
	private Demo1operation demo1operation = new Demo1operation();
	
	Demo1(){
		jf=new JFrame("教务信息管理系统");
		jf.getContentPane().setFont(new Font("微軟正黑體 Light", Font.PLAIN, 18));
		jf.getContentPane().setBackground(new Color(240, 248, 255));
		jf.getContentPane().setLayout(null);
		
		/*文字提醒*/
		JLabel lblNewLabel1 = new JLabel("账号信息");
		lblNewLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		lblNewLabel1.setForeground(new Color(0, 0, 128));
		lblNewLabel1.setBounds(215, 92, 76, 31);
		jf.getContentPane().add(lblNewLabel1);
		
		JLabel lblNewLabel2 = new JLabel("角色信息");
		lblNewLabel2.setForeground(new Color(0, 0, 128));
		lblNewLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		lblNewLabel2.setBounds(215, 232, 76, 24);
		jf.getContentPane().add(lblNewLabel2);
		
		JLabel Label = new JLabel("教务管理系统");
		Label.setForeground(new Color(0, 0, 128));
		Label.setFont(new Font("微軟正黑體 Light", Font.BOLD, 30));
		Label.setBounds(246, 10, 194, 72);
		jf.getContentPane().add(Label);
		
		/*输入框*/
		textField1 = new JTextField();
		textField1.setToolTipText("输入账号");
		textField1.setForeground(new Color(102, 102, 102));
		textField1.setFont(new Font("等线", Font.BOLD, 16));
		textField1.setBounds(214, 135, 264, 31);
		jf.getContentPane().add(textField1);
		textField1.setColumns(10);
		
		textField2 = new JTextField();
		textField2.setBackground(new Color(255, 255, 255));
		textField2.setToolTipText("输入密码");
		textField2.setForeground(new Color(102, 102, 102));
		textField2.setFont(new Font("等线", Font.BOLD, 16));
		textField2.setColumns(10);
		textField2.setBounds(214, 176, 264, 31);
		jf.getContentPane().add(textField2);
		
		/*单选框*/
		JRadioButton rdbtnNewRadioButton1 = new JRadioButton("学生");
		rdbtnNewRadioButton1.setSelected(true);
		rdbtnNewRadioButton1.setBackground(new Color(240, 248, 255));
		rdbtnNewRadioButton1.setFont(new Font("幼圆", Font.PLAIN, 15));
		rdbtnNewRadioButton1.setBounds(259, 274, 63, 23);
		rdbtnNewRadioButton1.setActionCommand(rdbtnNewRadioButton1.getText());
		jf.getContentPane().add(rdbtnNewRadioButton1,BorderLayout.NORTH);
		
		JRadioButton rdbtnNewRadioButton2 = new JRadioButton("老师");
		rdbtnNewRadioButton2.setBackground(new Color(240, 248, 255));
		rdbtnNewRadioButton2.setFont(new Font("幼圆", Font.PLAIN, 15));
		rdbtnNewRadioButton2.setBounds(377, 274, 63, 23);
		rdbtnNewRadioButton2.setActionCommand(rdbtnNewRadioButton2.getText());
		jf.getContentPane().add(rdbtnNewRadioButton2,BorderLayout.CENTER);
		
		group.add(rdbtnNewRadioButton1);
		group.add(rdbtnNewRadioButton2);
		
		
		/*提交按钮*/
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(70, 130, 180));
		btnNewButton.setFont(new Font("等线", Font.PLAIN, 24));
		btnNewButton.setBounds(215, 321, 269, 43);
		jf.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loginActionPerformed(e);
			}
		});
		
		jf.setBounds(700, 220, 744, 454);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	
	//	登入事件处理
	private void loginActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String studentid = this.textField1.getText();
		String teacherid = this.textField1.getText();
		String password = this.textField2.getText();
		String authority= this.group.getSelection().getActionCommand();
		
		if(StringUtil.isEmpty(studentid)) {
			JOptionPane.showMessageDialog(null, "账号不能为空");
			return;
		}
		if(StringUtil.isEmpty(password)) {
			JOptionPane.showMessageDialog(null, "密码不能为空");
			return;
		}
		if("学生".equals(authority)) {
			User user = new User(studentid,password,authority);
			Connection con = null;
			try {
				con = dbUtil.getCon();
				User currentUser = demo1operation.login(con, user);
				if(currentUser != null) {
					new DemoStudenthome(currentUser);
					jf.dispose();
					//JOptionPane.showMessageDialog(null, "登入成功");
				}
				else {
					JOptionPane.showMessageDialog(null, "信息错误");
				}
			} 
			catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "发生错误");
			}
		}
		if("老师".equals(authority)) {
			teacherUser user = new teacherUser(teacherid,password,authority);
			Connection con = null;
			try {
				con = dbUtil.getCon();
				teacherUser currentUser = demo1operation.loginteacher(con, user);
				if(currentUser != null) {
					new DemoTeacherhome(currentUser);
					jf.dispose();
					//JOptionPane.showMessageDialog(null, "登入成功");
				}
				else {
					JOptionPane.showMessageDialog(null, "登入失败");
				}
			} 
			catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "发生错误");
			}
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
		new Demo1();
	}
}
