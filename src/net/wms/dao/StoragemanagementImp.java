package net.wms.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.wms.bean.Storage;
import net.wms.util.LocalDataStore;

public class StoragemanagementImp implements Storagemanagement{
			public static Vector vec = new Vector();
			public static Vector vr = new Vector();

			private static final Pattern ID_PATTERN = Pattern.compile("\\bid\\s*=\\s*([0-9]+)", Pattern.CASE_INSENSITIVE);
			private static final Pattern SELLER_PATTERN = Pattern.compile("seller\\s*=\\s*'([^']*)'", Pattern.CASE_INSENSITIVE);

			public void Query(String sql) throws SQLException {
				List<Storage> storageList = selectStorage(sql);
				vec.removeAllElements();
				for (Storage storage : storageList) {
					Vector v = new Vector();
					v.add(storage.getId());
					v.add(storage.getStoragename());
					v.add(storage.getStoragestyle());
					v.add(storage.getStorageID());
					v.add(storage.getSeller());
					vec.add(v);
				}
			}
			
			public void Query1(String sql) throws SQLException {
				vr.removeAllElements();
				for (Storage storage : selectStorage(sql)) {
					vr.add(storage.getStorageID());
				}
			}
			
			public void Add(Storage storage, String sql) throws SQLException {
				try {
					LocalDataStore.addStorage(storage);
				} catch (RuntimeException e) {
					throw new SQLException(e);
				}
			}

			public void Delete(String sql) throws SQLException {
				Integer id = extractInt(ID_PATTERN, sql);
				if (id != null) {
					LocalDataStore.deleteStorage(id.intValue());
				}
			}

			public void Update(Storage storage, String sql) throws SQLException {
				Integer id = storage.getId() > 0 ? Integer.valueOf(storage.getId()) : extractInt(ID_PATTERN, sql);
				if (id != null) {
					LocalDataStore.updateStorage(id.intValue(), storage);
				}
			}

			private List<Storage> selectStorage(String sql) {
				String seller = extract(SELLER_PATTERN, sql);
				if (seller != null) {
					return LocalDataStore.getStorageBySeller(seller);
				}
				return LocalDataStore.getStorage();
			}

			private Integer extractInt(Pattern pattern, String sql) {
				String value = extract(pattern, sql);
				if (value == null) {
					return null;
				}
				try {
					return Integer.valueOf(value);
				} catch (NumberFormatException e) {
					return null;
				}
			}

			private String extract(Pattern pattern, String sql) {
				if (sql == null) {
					return null;
				}
				Matcher matcher = pattern.matcher(sql);
				return matcher.find() ? matcher.group(1) : null;
			}
}
