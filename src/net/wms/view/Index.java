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

public class Index {

	public  JFrame index;
	private JMenuBar management;
	private JMenu user;
	private JMenu goods;
	private JMenu storage;
	private JMenuItem exit;
	private JMenuItem usernews;
	private  JMenuItem storageselect01;
	private JMenuItem userupdate;
	private JMenuItem goodsadd;
	private JMenuItem goodsdelete;
	private JMenuItem goodsupdate;

	private JMenuItem goodslist;


	public Index(String name) {
		indexadmin();

		user.add(usernews);
		user.add(storageselect01);
		user.add(userupdate);
		user.add(exit);

		goods.add(goodsadd);
		goods.add(goodsdelete);
		goods.add(goodsupdate);

		storage.add(goodslist);
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

		Font f = new Font("", Font.BOLD, 15);
		management = new JMenuBar();
		user = new JMenu(" user");
		user.setFont(f);
		goods = new JMenu(" mygoods");
		goods.setFont(f);
		storage = new JMenu(" goodslist");
		storage.setFont(f);
		exit = new JMenuItem("updateuser");
		exit.setFont(f);
		usernews = new JMenuItem("Personal center ");
		usernews.setFont(f);
		storageselect01 = new JMenuItem("orderlist");
		storageselect01.setFont(f);
		userupdate = new JMenuItem("update pwd");
		userupdate.setFont(f);goodsadd = new JMenuItem("addgoods");
		goodsadd.setFont(f);
		goodsdelete = new JMenuItem("delgoods");
		goodsdelete.setFont(f);
		goodsupdate = new JMenuItem("updategoods");
		goodsupdate.setFont(f);
		goodslist = new JMenuItem("goodslist");
		goodslist.setFont(f);
	}


	private void action(final String name) {
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				Login.main(null);
			}
		});
		usernews.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Usernews(name);
			}
		});
		storageselect01.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Storageselect01(name);
			}
		});
		userupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Userupdate(name);
			}
		});
		goodsadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Goodsadd(name);
			}
		});
		goodsdelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Goodsdelete(name);
			}
		});
		goodsupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new GoodsUpdate(name);
			}
		});

		goodslist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				index.setVisible(false);
				new Goodslist(name);
			}
		});

	}
}
