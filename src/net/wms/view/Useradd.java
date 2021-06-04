package net.wms.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.wms.bean.User;
import net.wms.dao.LoginUseImp;

public class Useradd extends IndexAdmin{

	JTextField name;
	JTextField pwd;
	JTextField style;
	JTextField integrate;

	User user = new User();
	
	//���캯��
	public Useradd(String name) {
		super(name);
		init();
	}
	
	
	public void init() {

		Font d = new Font("����", Font.BOLD, 24);
		Font f = new Font("����", Font.BOLD, 18);

		JLabel usertitle = new JLabel("add user");

		JLabel username = new JLabel("name");
		name = new JTextField();

		JLabel userpwd = new JLabel("pwd");
		pwd = new JTextField();

		JLabel userstyle = new JLabel("type");
		style = new JTextField();

		JLabel userintegrate = new JLabel("integrate");
		integrate = new JTextField();

		JButton submit = new JButton("commit");
		JButton reset = new JButton("reset");
		usertitle.setBounds(250, 60, 200, 40);
		usertitle.setFont(d);
		username.setBounds(160, 140, 200, 30);
		username.setFont(f);
		name.setBounds(240, 140, 200, 30);
		name.setFont(f);
		userpwd.setBounds(160, 200, 200, 30);
		userpwd.setFont(f);
		pwd.setBounds(240, 200, 200, 30);
		pwd.setFont(f);
		userstyle.setBounds(160, 260, 100, 30);
		userstyle.setFont(f);
		style.setBounds(240, 260, 200, 30);
		style.setFont(f);

		userintegrate.setBounds(160, 320, 100, 30);
		userintegrate.setFont(f);
		integrate.setBounds(240, 320, 200, 30);
		integrate.setFont(f);


		submit.setBounds(200, 360, 100, 30);
		submit.setFont(f);
		reset.setBounds(310, 360, 100, 30);
		reset.setFont(f);

		index.add(usertitle);
		index.add(username);
		index.add(name);

		index.add(userpwd);
		index.add(pwd);

		index.add(style);
		index.add(userstyle);

		index.add(integrate);
		index.add(userintegrate);

		index.add(submit);
		index.add(reset);
		

		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//����û�����������û�����
				name.setText("");
				pwd.setText("");
				style.setText("");
				integrate.setText("");
			}
		});
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(name.getText().equals("") || pwd.getText().equals("") || style.getText().equals("")) {

					JOptionPane.showMessageDialog(null,"Please input what you want to search");
				} else {

					user.setusername(name.getText());
					user.setuserpwd(pwd.getText());
					user.setFlag(style.getText());
					user.setIntegrate(integrate.getText());

					LoginUseImp l = new LoginUseImp();
					try {
						//ִ��sql���
						l.Add(user, "insert into users(username,userpwd,flag,integrate) values(?,?,?,?)");
						//����ı���
						name.setText("");
						pwd.setText("");
						style.setText("");
						integrate.setText("");
						//��ӳɹ���ʾ��
						JOptionPane.showMessageDialog(null, "successful");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
