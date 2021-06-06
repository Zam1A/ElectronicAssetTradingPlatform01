package net.wms.test;

import net.wms.util.DB;

import java.sql.Connection;

public class DBtest {

    public static void main(String[] args) {

       try {
           Connection conn = DB.getConnection();
           System.out.println(conn);
       }catch (Exception e){
           e.printStackTrace();
       }



    }


}
