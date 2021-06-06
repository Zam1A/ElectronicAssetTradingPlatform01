package net.wms.dao;

import java.sql.SQLException;

import net.wms.bean.Storage;

public interface Storagemanagement {

			public void Query(String sql) throws SQLException;

			public void Add(Storage storage,String sql)throws SQLException;

			public void Delete(String sql)throws SQLException;

			public void 
			Update(Storage storage,String sql)throws SQLException;
}
