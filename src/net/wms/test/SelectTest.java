package net.wms.test;

import net.wms.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class SelectTest {


    public void Query(String sql) throws SQLException {
        Connection conn = DB.getConnection();
        PreparedStatement pra = conn.prepareStatement(sql);
        ResultSet rs = pra.executeQuery();
        while (rs.next()) {

            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("goodsname"));
            System.out.println(rs.getString("goodsstyle"));
            System.out.println(rs.getInt("goodsnumber"));
            System.out.println(rs.getString("storageID"));
        }
    }



    public static void main(String[] args) {
        SelectTest selectTest=new SelectTest();
        try {
            selectTest.Query("select * from goods");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}