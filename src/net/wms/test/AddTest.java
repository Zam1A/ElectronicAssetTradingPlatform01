package net.wms.test;

import net.wms.bean.User;
import net.wms.dao.LoginUseImp;
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
        loginUseImp.Add(user, "insert into users(username,userpwd,flag,integrate) values(?,?,?,?)");

        System.out.println("success");
    }
}
