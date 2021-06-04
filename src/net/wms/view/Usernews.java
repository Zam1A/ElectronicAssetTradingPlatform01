package net.wms.view;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JLabel;

import net.wms.bean.User;
import net.wms.dao.LoginUseImp;

public class Usernews extends Index{
	//声明对象
	JLabel uname;
	JLabel pwd;
	JLabel style;
	JLabel integrate;
	//为User类初始化user对象
	User user = new User();
	//构造函数
	public Usernews(String name) {
		super(name);
		init();
		//将用户名设置进user对象
		user.setusername(name);
		LoginUseImp l = new LoginUseImp();
		try {
			//执行按姓名查找的神奇了语句
			l.Query1(user, "SELECT id,username,userpwd,integrate,flag FROM users WHERE username= '"+name+"'");
			uname.setText(user.getusername());
			pwd.setText(user.getuserpwd());
			//通过标志量判断普通用户和管理员
			if(user.getFlag().equals("1")) {
				style.setText("employee");
			} else {
				style.setText("admin");
			}
			integrate.setText(user.getIntegrate());
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void init() {
		//初始化字体
		Font d = new Font("楷体", Font.BOLD, 24);
		Font f = new Font("楷体", Font.BOLD, 18);
		//初始化对象
		JLabel usertitle = new JLabel("userinfo");
		JLabel username = new JLabel("name：");
		uname = new JLabel();
		JLabel userpwd = new JLabel("pwd：");
		pwd = new JLabel();
		JLabel userstyle = new JLabel("type：");
		style = new JLabel();
		JLabel userintegrate = new JLabel("integrate：");
		integrate = new JLabel();
		//设置对象大小、位置和字体
		usertitle.setBounds(230, 60, 200, 40);
		usertitle.setFont(d);
		username.setBounds(180, 130, 80, 30);
		username.setFont(f);
		uname.setBounds(260, 130, 150, 30);
		uname.setFont(f);
		userpwd.setBounds(180, 190, 80, 30);
		userpwd.setFont(f);
		pwd.setBounds(260, 190, 150, 30);
		pwd.setFont(f);

		userstyle.setBounds(180, 250, 80, 30);
		userstyle.setFont(f);
		style.setBounds(260, 250, 150, 30);
		style.setFont(f);

		userintegrate.setBounds(180, 310, 120, 30);
		userintegrate.setFont(f);
		integrate.setBounds(260, 340, 150, 30);
		integrate.setFont(f);
		//将对象添加进框架
		index.add(usertitle);
		index.add(username);
		index.add(uname);
		index.add(userpwd);
		index.add(pwd);
		index.add(userstyle);
		index.add(style);
		index.add(userintegrate);
		index.add(integrate);
	}
}
