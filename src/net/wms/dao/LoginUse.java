package net.wms.dao;

import java.sql.SQLException;

import net.wms.bean.User;

public interface LoginUse {

		public boolean 
		Query(User user,String sql) throws SQLException;

		public void 
		Add(User user,String sql)throws SQLException;

		public void Delete(User user,String sql)throws SQLException;

		public void 
		Update(User user,String sql)throws SQLException;
}
