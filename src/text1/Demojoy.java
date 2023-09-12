package text1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;
import text3.teacherUser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Toolkit;

public class Demojoy {
	
	//老师增删课程
	private JFrame jf;
	private JPanel panel;
	
	Demojoy(){
		jf=new JFrame("彩蛋");
		jf.setBackground(new Color(240, 255, 255));
		jf.setIconImage(Toolkit.getDefaultToolkit().getImage(Demojoy.class.getResource("/images/湖南科技大学校徽.png")));
		jf.setBounds(700, 220, 800, 475);
		
		panel=new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setLayout(null);
		jf.getContentPane().add(panel,BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("被你发现了,运气不错哦");
		btnNewButton.setForeground(new Color(248, 248, 255));
		btnNewButton.setBounds(-12, 158, 579, 111);
		btnNewButton.setFont(new Font("黑体", Font.ITALIC, 40));
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				exitActionPerformed(e);
			}
		});
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Demojoy.class.getResource("/images/banner.png")));
		lblNewLabel.setBounds(-4, 26, 786, 358);
		lblNewLabel.setOpaque(false);
		panel.add(lblNewLabel);
		
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	//退出事件响应
	private void exitActionPerformed(ActionEvent e) {
		new Demo1();
		jf.dispose();
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
		new Demojoy();
	}
}
