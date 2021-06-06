package net.wms.dao;

import java.sql.SQLException;

import net.wms.bean.Goods;


public interface Goodsmanagement {

			public void Query(String sql) throws SQLException;

			public void Add(Goods goods,String sql)throws SQLException;

			public void Delete(String sql)throws SQLException;

			public void Update(Goods goods,String sql)throws SQLException;
}
