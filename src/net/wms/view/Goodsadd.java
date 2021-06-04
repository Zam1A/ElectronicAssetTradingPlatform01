package net.wms.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.wms.bean.Goods;
import net.wms.dao.GoodsmanagementImp;
import net.wms.dao.StoragemanagementImp;

public class Goodsadd extends Index{
	JTextField name;
	JTextField style;
	JTextField number;
	JTextField id;
	Goods goods = new Goods();
	public Goodsadd(String name) {
		super(name);
		String str=name;
		System.out.println(name);
		init(name);
	}
	public void init( String names) {
		Font d = new Font("", Font.BOLD, 24);
		Font f = new Font("", Font.BOLD, 18);
		JLabel goodstitle = new JLabel("add goods");
		JLabel goodsname = new JLabel("gname：");
		name = new JTextField();
		JLabel goodsstyle = new JLabel("type：");
		style = new JTextField();
		JLabel goodsnumber = new JLabel("price：");
		number = new JTextField();
		JLabel storageid = new JLabel("user：");

		id = new JTextField(names);
		JButton submit = new JButton("commit");
		JButton reset = new JButton("reset");
		goodstitle.setBounds(250, 40, 100, 40);
		goodstitle.setFont(d);
		goodsname.setBounds(160, 120, 80, 30);
		goodsname.setFont(f);
		name.setBounds(240, 120, 150, 30);
		name.setFont(f);
		goodsstyle.setBounds(160, 180, 80, 30);
		goodsstyle.setFont(f);
		style.setBounds(240, 180, 150, 30);
		style.setFont(f);
		goodsnumber.setBounds(160, 240, 80, 30);
		goodsnumber.setFont(f);
		number.setBounds(240, 240, 150, 30);
		number.setFont(f);
		storageid.setBounds(160, 300, 80, 30);
		storageid.setFont(f);
		id.setBounds(240, 300, 150, 30);
		id.setFont(f);
		submit.setBounds(190, 360, 80, 30);
		submit.setFont(f);
		reset.setBounds(300, 360, 80, 30);
		reset.setFont(f);
		index.add(goodstitle);
		index.add(goodsname);
		index.add(name);
		index.add(goodsstyle);
		index.add(style);
		index.add(goodsnumber);
		index.add(number);
		index.add(storageid);
		index.add(id);
		index.add(submit);
		index.add(reset);

		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				name.setText("");
				style.setText("");
				number.setText("");
			}
		});
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(name.getText().equals("") || style.getText().equals("") || number.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Please enter a word");
				} else {
					goods.setGoodsname(name.getText());
					goods.setGoodsstyle(style.getText());
					goods.setGoodsnumber(Integer.parseInt(number.getText()));
					goods.setStorageID(id.getText());
					GoodsmanagementImp g = new GoodsmanagementImp();
					try {
						g.Add(goods, "insert into goods(goodsname,goodsstyle,goodsnumber,storageID)" + "values(?,?,?,?)");
						name.setText("");
						style.setText("");
						number.setText("");
						JOptionPane.showMessageDialog(null, "success");
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
