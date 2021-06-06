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
import net.wms.bean.Storage;
import net.wms.dao.GoodsmanagementImp;
import net.wms.dao.StoragemanagementImp;

public class Storageupdate extends IndexAdmin{
	int id ;

	private JTable table;
	JTextField name;
	JTextField style;
	JTextField s_id;

	public Storageupdate(String name) {
		super(name);
		init();
	}
	public void init() {
		Font t = new Font("",Font.BOLD, 24);
		final Font f = new Font("",Font.BOLD, 15);
		JLabel title = new JLabel("info");
		JLabel storagename = new JLabel("name：");
		storagename.setBounds(160, 220, 80, 30);
		storagename.setFont(f);
		name = new JTextField();
		name.setBounds(240, 220, 150, 30);
		name.setFont(f);
		JLabel storagestyle = new JLabel("type：");
		storagestyle.setBounds(160, 270, 80, 30);
		storagestyle.setFont(f);
		style = new JTextField();
		style.setBounds(240, 270, 150, 30);
		style.setFont(f);
		JLabel storageid = new JLabel("no：");
		storageid.setBounds(160, 320, 80, 30);
		storageid.setFont(f);
		s_id = new JTextField();
		s_id.setBounds(240, 320, 150, 30);
		s_id.setFont(f);
		title.setFont(t);
		title.setBounds(230, 30, 100, 30);
		final Vector c = new Vector();
		//添加数据
		c.add("no");
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


				id = (int)table.getValueAt(table.getSelectedRow(), 0);

				String sname = (String)table.getValueAt(table.getSelectedRow(), 1);

				String sstyle =(String)table.getValueAt(table.getSelectedRow(), 2);

				String sid=(String)table.getValueAt(table.getSelectedRow(), 3).toString();

				name.setText(sname);
				style.setText(sstyle);
				s_id.setText(sid);
				super.mouseClicked(e);
			}
		});

		final JScrollPane js = new JScrollPane(table);
		js.setBounds(100, 70, 400, 130);

		final JButton update = new JButton("修改");

		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(id == 0){
					JOptionPane.showMessageDialog(null, "修改失败请选择需要修改的记录！");
				}else{
					try {


						int mess = JOptionPane.showConfirmDialog(
								null,"确定修改记录？","友情提示：",
								JOptionPane.YES_NO_OPTION );

						if(mess == 0){

							Storage storage = new Storage();
							storage.setStoragename(name.getText());
							storage.setStoragestyle(style.getText());
							storage.setStorageID(s_id.getText());
							s.Update(storage,"update storage set storagename = ?,storagestyle=?,storageID=? where id = "+ id);

							JOptionPane.showMessageDialog(null, "修改成功");
							s.Query("select * from storage");


							JTable new_table = new JTable(s.vec,c);
							new_table.setFont(f);
							new_table.getTableHeader().setFont(f);

							JScrollPane p = new JScrollPane(new_table);

							p.setBounds(100, 70, 400, 130);

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
		index.add(storagename);
		index.add(name);
		index.add(storagestyle);
		index.add(style);
		index.add(storageid);
		index.add(s_id);
		index.add(js);

		index.add(update);
	}
}
