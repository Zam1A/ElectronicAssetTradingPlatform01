package net.wms.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.wms.bean.User;
import net.wms.dao.LoginUseImp;

public class Userdelete extends IndexAdmin {
	//�������ֶ���
	JLabel name;
	JLabel pwd;
	JLabel style;
	JTextField dname;
	//ΪUser���ʼ������
	User user = new User();
	
	//���캯��
	public Userdelete(String name) {
		super(name);
		init();
	}
	
	public void init() {
		//��ʼ������
		Font d = new Font("����", Font.BOLD, 24);
		Font f = new Font("����", Font.BOLD, 18);
		//��ʼ������
		JLabel userdelete = new JLabel("DELUSER");
		JLabel deletename = new JLabel("USERNAME");
		dname = new JTextField();

		JLabel username = new JLabel("username");
		name = new JLabel();
		JLabel userpwd = new JLabel("pwd");
		pwd = new JLabel();
		JLabel userstyle = new JLabel("type");
		style = new JLabel();
		JButton submit = new JButton("OK");
		JButton delete = new JButton("remove");
		//���ö���
		userdelete.setBounds(250, 30, 150, 40);
		userdelete.setFont(d);
		deletename.setBounds(120, 90, 150, 30);
		deletename.setFont(f);
		dname.setBounds(230, 90, 150, 30);
		dname.setFont(f);
		username.setBounds(160, 200, 80, 30);
		username.setFont(f);
		name.setBounds(240, 200, 150, 30);
		name.setFont(f);
		userpwd.setBounds(160, 260, 80, 30);
		userpwd.setFont(f);
		pwd.setBounds(240, 260, 150, 30);
		pwd.setFont(f);
		userstyle.setBounds(160, 320, 80, 30);
		userstyle.setFont(f);
		style.setBounds(240, 320, 150, 30);
		style.setFont(f);
		submit.setBounds(390, 90, 80, 30);
		submit.setFont(f);
		delete.setBounds(250, 380, 80, 30);
		delete.setFont(f);
		//��Ӷ���
		index.add(userdelete);
		index.add(deletename);
		index.add(dname);
		index.add(submit);
		index.add(username);
		index.add(name);
		index.add(userpwd);
		index.add(pwd);
		index.add(style);
		index.add(userstyle);
		index.add(delete);
		
		//Ϊȷ����ť��Ӽ����¼�
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//���ı�����ȡ���û������ý�user������
				user.setusername(dname.getText());
				LoginUseImp l = new LoginUseImp();
				boolean b;
				try {
					//ִ��sql���
					b = l.Query1(user, "select * from users where username= '"+dname.getText()+"'");
					if(b){
						//�����ݿ��е�ֵ���ý��ı���
						name.setText(user.getusername());
						pwd.setText(user.getuserpwd());
						style.setText(user.getFlag());
					} else {
						//δ���ҵ���ʾ��
						JOptionPane.showMessageDialog(null,"select is null");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//Ϊɾ����ť��Ӽ����¼�
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//��ѡ�е�������ӽ��ı���
				user.setusername(dname.getText());
				LoginUseImp l = new LoginUseImp();
				try {
					//ִ��ɾ����sql���
					l.Delete(user, "delete from users where username='"+dname.getText()+"'");
					JOptionPane.showMessageDialog(null, "success");
				} catch (SQLException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
	}
}