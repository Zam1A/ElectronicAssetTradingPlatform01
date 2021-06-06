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

import net.wms.dao.GoodsmanagementImp;

public class Goodsdelete extends Index{
	int id ;

	private JTable table;
  private  String ids;
	public Goodsdelete(String name) {
		super(name);
		ids=name;
		init(name);
	}
	public void init( String names) {
		Font t = new Font("",Font.BOLD, 24);
		final Font f = new Font("",Font.BOLD, 15);
		JLabel title = new JLabel("goods");
		title.setFont(t);
		title.setBounds(230, 40, 100, 40);
		final Vector c = new Vector();

		c.add("id");
		c.add("goodsname");
		c.add("goodstype");
		c.add("goodsprice");
		c.add("user");
		final GoodsmanagementImp g = new GoodsmanagementImp();
		try {
			g.Query("select * from goods where storageID='"+names+"'");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		table = new JTable(g.vec,c);
		table.setFont(f);
		table.getTableHeader().setFont(f);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				id = (int) table.getValueAt(
						table.getSelectedRow(), 0);
				System.out.println(id);
				super.mouseClicked(e);
			}
		});

		final JScrollPane js = new JScrollPane(table);
		js.setBounds(100, 120, 400, 200);

		final JButton delete = new JButton("remove");

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				if(id == 0){
					JOptionPane.showMessageDialog(null, "Delete fails Please select the record to delete!");
				}else{
					try {

						int mess = JOptionPane.showConfirmDialog(
								null,"Do you really want to delete this record?","friendly tips：",
								JOptionPane.YES_NO_OPTION );

						if(mess == 0){

							g.Delete("delete from goods where id ="+id);

							JOptionPane.showMessageDialog(null, "success");
							g.Query("select * from goods where storageID='"+ids+"'");

							JTable new_table = new JTable(g.vec,c);
							new_table.setFont(f);
							new_table.getTableHeader().setFont(f);

							JScrollPane p = new JScrollPane(new_table);

							p.setBounds(100, 120, 400, 200);

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

		delete.setBounds(250, 350, 80,30);
		index.add(title);
		index.add(js);

		index.add(delete);
	}
}
