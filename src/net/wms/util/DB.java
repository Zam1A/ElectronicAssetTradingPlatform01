package net.wms.util;

import java.sql.Connection;

@Deprecated
public class DB {

	private DB(){
	}

	public static Connection getConnection(){
		throw new UnsupportedOperationException(
				"The application now uses LocalDataStore instead of a remote MySQL connection.");
	}
}
