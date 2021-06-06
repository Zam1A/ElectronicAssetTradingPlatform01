package net.wms.view;

import java.awt.Font;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.wms.dao.GoodsmanagementImp;

public class GoodsSelect extends IndexAdmin{

	private JTable table;
	int id ;

	public GoodsSelect(String name) {
		super(name);
		init();
	}
	public void init() {
		Font t = new Font("",Font.BOLD, 24);
		final Font f = new Font("",Font.BOLD, 15);
		JLabel title = new JLabel("goods");
		title.setFont(t);
		title.setBounds(230, 40, 100, 40);
		final Vector c = new Vector();

		c.add("id");
		c.add("name");
		c.add("type");
		c.add("price");
		c.add("user");
		final GoodsmanagementImp g = new GoodsmanagementImp();

		table = new JTable(g.vec,c);
		table.setFont(f);
		table.getTableHeader().setFont(f);
		final JScrollPane js = new JScrollPane(table);
		js.setBounds(100, 120, 400, 200);
		try {
			g.Query("select * from goods");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		index.add(title);
		index.add(js);
	}
}
