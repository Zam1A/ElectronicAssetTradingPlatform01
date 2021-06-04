package net.wms.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.wms.bean.User;
import net.wms.dao.LoginUseImp;
/**

 *
 */

public class Login {
		Font d = new Font("", Font.BOLD, 22);
		Font f = new Font("", Font.BOLD, 15);
		JFrame logingui = new JFrame("Login View");
		JLabel userlogin = new JLabel("Online trading platform");
		JLabel username = new JLabel("username:");
		JLabel password = new JLabel("password:");
		JLabel usertyle = new JLabel("userType");
		JTextField name = new JTextField();
		JTextField pwd = new JPasswordField();
		JComboBox box = new JComboBox(new String[]{"admin","employee"} );
		JButton login = new JButton("sign in");
		User user = new User();
	public void LoginGui() {
		logingui.setBounds(450, 200, 550, 350);
		logingui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logingui.setLayout(null);
		userlogin.setBounds(160, 30, 250, 30);
		userlogin.setFont(d);
		username.setBounds(110, 80, 100, 30);
		username.setFont(f);
		password.setBounds(110, 120, 100, 30);
		password.setFont(f);
		usertyle.setBounds(110, 160, 100, 30);
		usertyle.setFont(f);
		name.setBounds(200, 80, 180, 30);
		name.setFont(f);
		pwd.setBounds(200, 120, 180, 30);
		box.setBounds(200, 160, 100, 30);
		box.setFont(f);
		login.setBounds(200, 200, 80, 30);
		login.setFont(f);
		logingui.add(userlogin);
		logingui.add(username);
		logingui.add(password);
		logingui.add(usertyle);
		logingui.add(name);
		logingui.add(pwd);
		logingui.add(box);
		logingui.add(login);
		logingui.setVisible(true);
		((JComponent) logingui.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon("Images//0jpg");
		JLabel background = new JLabel(img);
		logingui.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));//����ǩ�Ž�������
		background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());//���ñ�ǩ�Ĵ�С
		box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(box.getSelectedItem().equals("admin")){
					//���ñ�־����ֵ
					user.setFlag("2");
				}else{
					user.setFlag("1");
				}
			}
		});
		//����¼��ť���ü����¼�
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

					String name_text = name.getText();
					String pwd_text = pwd.getText();

					user.setusername(name_text);
					user.setuserpwd(pwd_text);

					LoginUseImp l = new LoginUseImp();

					String state = user.getFlag();

					if(state != "1" && state != "2") {
						state = "2";
					}

					if(state == "2") {
						try {
							boolean flag = l.Query(user, "select * from users where username=? and userpwd=? and flag="+state);
							if(flag) {

								JOptionPane.showMessageDialog(null, "Login Successful");
								logingui.setVisible(false);

								new IndexAdmin(name_text);
							} else {
								JOptionPane.showMessageDialog(null, "Login failed, please check the user name and password to log in again");
								name.setText("");
								pwd.setText("");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

				} else if(state == "1") {
					try {

						boolean flag = l.Query(user, "select * from users where username=? and userpwd=? and flag="+state);
						if(flag) {
							JOptionPane.showMessageDialog(null, "Login Successful");
							logingui.setVisible(false);	
							new Index(name_text);
						} else {
							JOptionPane.showMessageDialog(null, "Login failed, please check the user name and password to log in again");
							name.setText("");
							pwd.setText("");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		Login l = new Login();
		l.LoginGui();
	}
}
