package net.wms.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.wms.bean.Goods;
import net.wms.dao.GoodsmanagementImp;
import net.wms.dao.StoragemanagementImp;

public class GoodsUpdate extends Index{
	int id ;

	private  static  String ids;
	private JTable table;
	JTextField name;
	JTextField style;
	JTextField number;
	JTextField s_id;

	public GoodsUpdate(String name) {

		super(name);
		ids=name;
		init(name);
	}
	public void init( String names) {
		Font t = new Font("楷体",Font.BOLD, 24);
		final Font f = new Font("楷体",Font.BOLD, 15);
		JLabel title = new JLabel("goods");
		JLabel goodsname = new JLabel("goodsname：");
		goodsname.setBounds(160, 180, 80, 30);
		name = new JTextField();
		name.setBounds(240, 180, 150, 30);
		JLabel goodsstyle = new JLabel("type：");
		goodsstyle.setBounds(160, 230, 80, 30);
		style = new JTextField();
		style.setBounds(240, 230, 150, 30);
		JLabel goodsnumber = new JLabel("price：");
		goodsnumber.setBounds(160, 280, 80, 30);
		number = new JTextField();
		number.setBounds(240, 280, 150, 30);
		JLabel storageid = new JLabel("userid：");
		storageid.setBounds(160, 330, 80, 30);
		StoragemanagementImp s = new StoragemanagementImp();

		s_id = new JTextField(names);
		s_id.setBounds(240, 330, 150, 30);
		title.setFont(t);
		title.setBounds(230, 10, 100, 30);
		final Vector c = new Vector();

		c.add("id");
		c.add("goodname");
		c.add("type");
		c.add("price");
		c.add("user");
		final GoodsmanagementImp g = new GoodsmanagementImp();
		try {
			g.Query("select * from goods where storageID='"+names+"'");
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		table = new JTable(g.vec,c);
		table.setFont(f);
		table.getTableHeader().setFont(f);



		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				id = (int)table.getValueAt(table.getSelectedRow(), 0);


				String gname = (String)table.getValueAt(table.getSelectedRow(), 1);

				String gstyle = (String)table.getValueAt(table.getSelectedRow(), 2);

				String gnumber=table.getValueAt(table.getSelectedRow(), 3).toString();
				String sid=table.getValueAt(table.getSelectedRow(), 4).toString();

				name.setText(gname);
				style.setText(gstyle);
				number.setText(gnumber);
				s_id.setText(sid);
				super.mouseClicked(e);
			}
		});

		final JScrollPane js = new JScrollPane(table);
		js.setBounds(100, 60, 400, 100);
		final JButton update = new JButton("update");

		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(id == 0){
					JOptionPane.showMessageDialog(null, "Please check the attachment！");
				}else{
					try {

						int mess = JOptionPane.showConfirmDialog(
								null,"Modify the log or modify the record？","Tips：",
								JOptionPane.YES_NO_OPTION );


						if(mess == 0){

							Goods goods = new Goods();
							goods.setId(id);
							goods.setGoodsname(name.getText());
							goods.setGoodsstyle(style.getText());
							goods.setGoodsnumber(Integer.parseInt(number.getText()));
							g.Update(goods,"update goods set goodsname = ?,goodsstyle=?,goodsnumber=? where id = "+ id);

							JOptionPane.showMessageDialog(null, "success");
							name.setText("");
							style.setText("");
							number.setText("");
							g.Query("select * from goods where storageID='"+ids+"'");

							JTable new_table = new JTable(g.vec,c);
							new_table.setFont(f);
							new_table.getTableHeader().setFont(f);

							JScrollPane p = new JScrollPane(new_table);

							p.setBounds(100, 60, 400, 100);

							index.remove(js);

							index.add(p);

							index.repaint();
						}

					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
		});
		update.setBounds(250, 380, 80,30);
		index.add(title);
		index.add(goodsname);
		index.add(name);
		index.add(goodsstyle);
		index.add(style);
		index.add(goodsnumber);
		index.add(number);
		index.add(storageid);
		index.add(s_id);
		index.add(js);
		index.add(update);
	}
}
