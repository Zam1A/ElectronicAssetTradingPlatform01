package net.wms.test;

import net.wms.bean.User;
import net.wms.dao.LoginUseImp;
import net.wms.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTest {





    public static void main(String[] args) throws SQLException {
        LoginUseImp loginUseImp=new LoginUseImp();
        User user=new User();
        Long a= System. currentTimeMillis();
        user.setusername(a.toString() );
        user.setuserpwd("1");
        user.setFlag("1");
        user.setIntegrate("10000");
      try {
          Connection conn = DB.getConnection();
          PreparedStatement pra = conn.prepareStatement("insert into users(username,userpwd,flag,integrate) values(?,?,?,?)");
          pra.setString(1, user.getusername());
          pra.setString(2, user.getuserpwd());
          pra.setString(3, user.getFlag());
          pra.setString(4, user.getIntegrate());
          pra.executeUpdate();
          pra.close();
      }catch (Exception e){
          e.printStackTrace();
      }

        System.out.println("success");
    }
}
