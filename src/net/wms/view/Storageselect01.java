package net.wms.view;

import java.awt.Font;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.wms.dao.StoragemanagementImp;

public class Storageselect01 extends Index{

    int id;
    private JTable table;
    private String names;

    public Storageselect01(String name) {
        super(name);
        names=name;
        init();
    }
    @SuppressWarnings("unchecked")
    public void init() {
        Font t = new Font("楷体",Font.BOLD, 24);
        final Font f = new Font("楷体",Font.BOLD, 15);
        JLabel title = new JLabel("仓库信息");
        title.setFont(t);
        title.setBounds(230, 40, 100, 40);
        Vector v = new Vector();
        v.add("id");
        v.add("order");
        v.add("type");
        v.add("price");
        v.add("seller");
        StoragemanagementImp s = new StoragemanagementImp();
        table = new JTable(s.vec,v);
        table.setFont(f);
        table.getTableHeader().setFont(f);
        JScrollPane jp = new JScrollPane(table);
        jp.setBounds(100, 120, 400, 200);
        try {
            s.Query("select * from storage where seller='"+names+"'" );
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        index.add(title);
        index.add(jp);
    }
}
