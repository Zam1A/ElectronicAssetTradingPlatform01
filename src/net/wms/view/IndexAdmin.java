package net.wms.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class IndexAdmin {

	public  JFrame index;
	private JMenuBar management;
	private JMenu user;
	private JMenu goods;
	private JMenu storage;
	private JMenuItem exit;
	private JMenuItem useradd;
	private JMenuItem userdelete;
	private JMenuItem userupdate;
	private JMenuItem userselect;

	private JMenuItem goodsdelete;


	private JMenuItem storagedelete;

	Font f = new Font("", Font.BOLD, 15);


	public IndexAdmin(String name) {
		indexadmin();
		//菜单的添加
		//给用户菜单添加条目
		user.add(useradd);
		user.add(userselect);
		user.add(userdelete);
		user.add(userupdate);
		user.add(exit);
		//给商品菜单添加条目

		goods.add(goodsdelete);

		//给仓库菜单添加条目

		storage.add(storagedelete);

		//将菜单添加到菜单栏里
		management.add(user);
		management.add(goods);
		management.add(storage);
		init(name);
		action(name);
	}

	private void init(String name) {
		//初始化框架
		index = new JFrame("welcome:"+name);
		//设置框架大小及位置
		index.setBounds(500, 100, 600, 500);
		index.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//将菜单栏设置进框架
		index.setJMenuBar(management);
		//清空框架格式
		index.setLayout(null);
		//将框架转换为容器
		((JComponent) index.getContentPane()).setOpaque(false);
		//声明图片对象
		ImageIcon img = null;
		//产生随机数
		Random r = new Random();
		int i = r.nextInt(5);
		//用随机数的值获取不同的图片
		switch (i) {
			case 0:
				img = new ImageIcon("Images//0.jpg");
				break;
			case 1:
				img = new ImageIcon("Images//1.jpg");
				break;
			case 2:
				img = new ImageIcon("Images//2.jpg");
				break;
			case 3:
				img = new ImageIcon("Images//3.jpg");
				break;
			case 4:
				img = new ImageIcon("Images//4.jpg");
				break;
			default:
				break;
		}
		//初始化标签
		JLabel background = new JLabel(img);
		//将标签添加进框架index（添加进容器中）
		index.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		//设置标签大小
		background.setBounds(0, 20, img.getIconWidth(), img.getIconHeight());
		//设置可视化
		index.setVisible(true);
	}

	public void indexadmin() {
		//对象初始化以及设置字体
		management = new JMenuBar();
		//菜单初始化
		user = new JMenu(" UserManagement");
		user.setFont(f);
		goods = new JMenu(" goodsManagement");
		goods.setFont(f);
		storage = new JMenu(" WarehouseManagement");
		storage.setFont(f);
		//菜单条目初始化
		exit = new JMenuItem("Login.pas");
		exit.setFont(f);
		useradd = new JMenuItem("add user");
		useradd.setFont(f);
		userdelete = new JMenuItem("del user");
		userdelete.setFont(f);
		userupdate = new JMenuItem("Change MyPassword");
		userupdate.setFont(f);
		userselect = new JMenuItem("select user");
		userselect.setFont(f);

		goodsdelete = new JMenuItem("goodsList");
		goodsdelete.setFont(f);

		storagedelete = new JMenuItem("orderList");
		storagedelete.setFont(f);

	}

	//给所有的菜单条目设置监听事件
	private void action(final String name) {
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//界面转换
				//将原来页面设置为不可见
				index.setVisible(false);
				//调用函数转到登陆页面
				Login.main(null);
			}
		});
		useradd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//界面转换
				//将原来页面设置为不可见
				index.setVisible(false);
				//用构造函数获取新页面
				new Useradd(name);
			}
		});
		userselect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Userselect(name);
			}
		});
		userdelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Userdelete(name);
			}
		});
		userupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Adminupdate(name);
			}
		});

		goodsdelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//界面转换
				index.setVisible(false);
				new GoodsSelect(name);
			}
		});


		storagedelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//界面转换
				index.setVisible(false);
				new Storageselect(name);
			}
		});

	}
}