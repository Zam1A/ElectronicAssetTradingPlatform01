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

		user.add(useradd);
		user.add(userselect);
		user.add(userdelete);
		user.add(userupdate);
		user.add(exit);


		goods.add(goodsdelete);



		storage.add(storagedelete);


		management.add(user);
		management.add(goods);
		management.add(storage);
		init(name);
		action(name);
	}

	private void init(String name) {

		index = new JFrame("welcome:"+name);

		index.setBounds(500, 100, 600, 500);
		index.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		index.setJMenuBar(management);

		index.setLayout(null);

		((JComponent) index.getContentPane()).setOpaque(false);

		ImageIcon img = null;

		Random r = new Random();
		int i = r.nextInt(5);

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

		JLabel background = new JLabel(img);

		index.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));

		background.setBounds(0, 20, img.getIconWidth(), img.getIconHeight());

		index.setVisible(true);
	}

	public void indexadmin() {

		management = new JMenuBar();

		user = new JMenu(" UserManagement");
		user.setFont(f);
		goods = new JMenu(" goodsManagement");
		goods.setFont(f);
		storage = new JMenu(" WarehouseManagement");
		storage.setFont(f);

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


	private void action(final String name) {
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);

				Login.main(null);
			}
		});
		useradd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);

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

				index.setVisible(false);
				new GoodsSelect(name);
			}
		});


		storagedelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Storageselect(name);
			}
		});

	}
}