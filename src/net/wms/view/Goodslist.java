package net.wms.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.wms.bean.Storage;
import net.wms.bean.User;
import net.wms.dao.GoodsmanagementImp;
import net.wms.dao.LoginUseImp;
import net.wms.dao.StoragemanagementImp;
import net.wms.util.DB;

public class Goodslist extends Index {
    int id;

    private JTable table;
    private String names;

    public Goodslist(String name) {
        super(name);
        names = name;
        init();
    }

    public void init() {
        Font t = new Font("", Font.BOLD, 24);
        final Font f = new Font("", Font.BOLD, 15);
        JLabel title = new JLabel("goodslist");
        title.setFont(t);
        title.setBounds(130, 40, 200, 40);
        final Vector c = new Vector();

        c.add("goodsid");
        c.add("name");
        c.add("type");
        c.add("price");
        c.add("user");
        final GoodsmanagementImp s = new GoodsmanagementImp();
        try {
            s.Query("select * from goods");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        table = new JTable(s.vec, c);
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

        final JButton delete = new JButton("buy");

        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                if (id == 0) {
                    JOptionPane.showMessageDialog(null, "Failed to purchase, please select the record to delete！");
                } else {
                    try {

                        int mess = JOptionPane.showConfirmDialog(
                                null, "Definitely buy?？", "Helpful hints：",
                                JOptionPane.YES_NO_OPTION);

                        if (mess == 0) {
                            LoginUseImp l = new LoginUseImp();
                            User user = new User();
                            user.setusername(names);

                            try {

                                s.Query("select id, goodsname ,goodsstyle, goodsnumber, storageID  from goods where id =" + id);
                                Storage storage = new Storage();

                                Connection conn = DB.getConnection();
                                PreparedStatement pra = conn.prepareStatement("select id, goodsname ,goodsstyle, goodsnumber, storageID from goods where id =" + id);

                                ResultSet rs = pra.executeQuery();
                                while (rs.next()) {
                                    storage.setStoragename(rs.getString("goodsname"));
                                    storage.setStoragestyle(rs.getString("goodsstyle"));
                                    storage.setStorageID(rs.getString("goodsnumber"));

                                }
                                   storage.setSeller(names);
                                StoragemanagementImp s = new StoragemanagementImp();
                                s.Add(storage, "insert into storage(storagename,storagestyle,storageID,seller)" + "values(?,?,?,?)");

                                l.Delete(user, "update users set integrate= integrate -'" + Integer.parseInt(storage.getStorageID()) + "' where username='" + names + "'");
                                s.Delete(" delete from goods where id =" + id);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Purchase failed");
                            }
                            JOptionPane.showMessageDialog(null, "Purchase successful!");
                            s.Query("select * from goods");

                            JTable new_table = new JTable(s.vec, c);
                            new_table.setFont(f);
                            new_table.getTableHeader().setFont(f);

                            JScrollPane p = new JScrollPane(new_table);

                            p.setBounds(100, 120, 400, 200);

                            index.remove(js);

                            index.add(p);
                            index.repaint();
                        }

                    } catch (Exception e2) {
                        System.err.println(e2.getMessage());
                        e2.printStackTrace();
                    }
                }
            }
        });

        delete.setBounds(250, 350, 80, 30);
        index.add(title);
        index.add(js);
        index.add(delete);
    }
}
