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
import net.wms.dao.StoragemanagementImp;

public class Storagedelete extends IndexAdmin{
	int id ;

	private JTable table;

	public Storagedelete(String name) {
		super(name);
		init();
	}
	public void init() {
		Font t = new Font("",Font.BOLD, 24);
		final Font f = new Font("",Font.BOLD, 15);
		JLabel title = new JLabel("orderlist");
		title.setFont(t);
		title.setBounds(130, 40, 100, 40);
		final Vector c = new Vector();

		c.add("id");
		c.add("name");
		c.add("type");
		c.add("user");
		final StoragemanagementImp s = new StoragemanagementImp();
		try {
			s.Query("select * from storage");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		table = new JTable(s.vec,c);
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

		final JButton delete = new JButton("删除");

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(id == 0){
					JOptionPane.showMessageDialog(null, "删除失败请选择需要删除的记录！");
				}else{
					try {


						int mess = JOptionPane.showConfirmDialog(
								null,"确定删除记录？","友情提示：",
								JOptionPane.YES_NO_OPTION );

						//0 == 确定 ，1 == 取消
						if(mess == 0){

							s.Delete("delete from storage where id ="+id);

							JOptionPane.showMessageDialog(null, "删除成功");
							s.Query("select * from storage");

							JTable new_table = new JTable(s.vec,c);
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
