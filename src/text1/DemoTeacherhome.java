package text1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import text3.teacherUser;
import java.awt.BorderLayout;


public class DemoTeacherhome extends JFrame{
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private LabelPanel lp = null;
	private JPanel jpanel;
	
	private String name;
	private String department;
	private static String tecaherid;
	private static String password;
	private static String authority;
	static teacherUser currentUserteacher = new teacherUser(tecaherid,password,authority);
	
	DemoTeacherhome(teacherUser currentUser){
		currentUserteacher=currentUser;
		setTitle("老师界面");
		setBackground(new Color(240, 248, 255));
		getContentPane().setFont(new Font("微軟正黑體 Light", Font.PLAIN, 18));
		getContentPane().setBackground(new Color(240, 248, 255));
		getContentPane().setLayout(new BorderLayout());
		
		// 添加时间的JPanel
		lp = new LabelPanel();
		lp.setBackground(new Color(220, 220, 220));
		lp.add(new TimePanel());
		getContentPane().add(lp,BorderLayout.SOUTH);
		// 设置时间变化的任务
		Timer timer = new Timer();
		timer.schedule(new ShowTime(), new Date(), 1000);
		
		//获得老师者的名字和班级
		name = currentUser.getname();
		department = currentUser.getdepartment();
		//中部欢迎标语
		jpanel=new JPanel();
		jpanel.setBackground(new Color(240, 248, 255));
		jpanel.setLayout(null);
		JButton btnNewButton = new JButton("欢迎 "+department+name+" 老师");
		btnNewButton.setForeground(new Color(0, 0, 139));
		btnNewButton.setBounds(236, 183, 540, 80);
		btnNewButton.setFont(new Font("等线", Font.PLAIN, 38));
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		jpanel.add(btnNewButton);
		getContentPane().add(jpanel,BorderLayout.CENTER);
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				exitActionPerformed(e);
			}
		});
		
		//头部菜单栏
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(240, 248, 255));
		menuBar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		setJMenuBar(menuBar);
		
							
		JMenu menu1 = new JMenu("首页");
		menu1.setForeground(new Color(70, 130, 180));
		menu1.setBackground(new Color(240, 248, 255));
		menu1.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 17));
		menuBar.add(menu1);
		
							
		JMenu menu2 = new JMenu("课程设置");
		menu2.setBackground(new Color(240, 248, 255));
		menu2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		menuBar.add(menu2);
		//课程设置响应事件
		menu2.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				new DemoTeacher(currentUserteacher);
				dispose();
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
		menu3.setBackground(new Color(240, 248, 255));
		menu3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		menuBar.add(menu3);	
		//成绩管理响应事件	
		menu3.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				new DemoTeacherscore(currentUserteacher);
				dispose();
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
		
		setBounds(700, 220, 800, 475);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	//退出事件响应
	private void exitActionPerformed(ActionEvent e) {
		Random rand = new Random();
		int i = rand.nextInt(5);
		System.out.println(i);
		if(i==0) {
			new Demojoy();
			dispose();
		}
		else {
			new Demo1();
			dispose();
		}
		
	}

	class ShowTime extends TimerTask {
		public void run() {
			// 刷新
			lp.label.setText(sdf.format(new Date()));
			repaint();
		}
	}
	class TimePanel extends JPanel {
		public void paint(Graphics g) {
		super.paint(g);
			// 显示时间
			g.setFont(new Font("微軟正黑體 Light", Font.PLAIN, 30));
			g.drawString(sdf.format(new Date()), 10, 10);
		}
	}
	class LabelPanel extends JPanel {
		JLabel label = new JLabel();
		public LabelPanel() {
			add(label);
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
		new DemoTeacherhome(currentUserteacher);
	}
}
