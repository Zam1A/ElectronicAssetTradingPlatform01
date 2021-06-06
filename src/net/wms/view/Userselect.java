package net.wms.view;

import java.awt.Font;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.wms.dao.LoginUseImp;

public class Userselect extends IndexAdmin{

	int id;
	private JTable table;


	public Userselect(String name) {
		super(name);
		init();
	}

	public void init() {

		Font t = new Font("楷体",Font.BOLD, 24);
		final Font f = new Font("楷体",Font.BOLD, 15);

		JLabel title = new JLabel("userList");
		title.setFont(t);
		title.setBounds(230, 40, 300, 40);

		Vector v = new Vector();

		v.add("id");
		v.add("username");
		v.add("usertype");
		v.add("integrate");
		LoginUseImp l = new LoginUseImp();

		table = new JTable(l.vec,v);
		table.setFont(f);
		JScrollPane jp = new JScrollPane(table);
		jp.setBounds(100, 120, 400, 200);
		try {
			l.Select("select * from users");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		index.add(title);
		index.add(jp);
	}
}
