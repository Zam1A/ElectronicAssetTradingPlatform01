package net.wms.test;

import net.wms.dao.GoodsmanagementImp;

import java.sql.SQLException;
public class SelectTest {


    public void Query(String sql) throws SQLException {
        GoodsmanagementImp goodsmanagementImp = new GoodsmanagementImp();
        goodsmanagementImp.Query(sql);
        for (Object row : GoodsmanagementImp.vec) {
            System.out.println(row);
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
