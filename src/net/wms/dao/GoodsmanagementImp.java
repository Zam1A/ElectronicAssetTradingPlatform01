package net.wms.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.wms.bean.Goods;
import net.wms.util.LocalDataStore;

public class GoodsmanagementImp implements Goodsmanagement{
			public static Vector vec = new Vector();

			private static final Pattern ID_PATTERN = Pattern.compile("\\bid\\s*=\\s*([0-9]+)", Pattern.CASE_INSENSITIVE);
			private static final Pattern OWNER_PATTERN = Pattern.compile("storageID\\s*=\\s*'([^']*)'", Pattern.CASE_INSENSITIVE);

			public void Query(String sql) throws SQLException {
				List<Goods> goodsList = selectGoods(sql);
				vec.removeAllElements();
				for (Goods goods : goodsList) {
					Vector v = new Vector();
					v.add(goods.getId());
					v.add(goods.getGoodsname());
					v.add(goods.getGoodsstyle());
					v.add(goods.getGoodsnumber());
					v.add(goods.getStorageID());
					vec.add(v);
				}
			}
			
			public boolean Query1(Goods goods, String sql) throws SQLException {
				Integer id = extractInt(ID_PATTERN, sql);
				if (id == null) {
					return false;
				}
				Goods savedGoods = LocalDataStore.findGoodsById(id.intValue());
				if (savedGoods == null) {
					return false;
				}
				goods.setId(savedGoods.getId());
				goods.setGoodsname(savedGoods.getGoodsname());
				goods.setGoodsstyle(savedGoods.getGoodsstyle());
				goods.setGoodsnumber(savedGoods.getGoodsnumber());
				goods.setStorageID(savedGoods.getStorageID());
				return true;
			}
			
			public void Add(Goods goods, String sql) throws SQLException {
				try {
					LocalDataStore.addGoods(goods);
				} catch (RuntimeException e) {
					throw new SQLException(e);
				}
			}

			public void Delete(String sql) throws SQLException {
				Integer id = extractInt(ID_PATTERN, sql);
				if (id != null) {
					LocalDataStore.deleteGoods(id.intValue());
				}
			}

			public void Update(Goods goods, String sql) throws SQLException {
				Integer id = goods.getId() > 0 ? Integer.valueOf(goods.getId()) : extractInt(ID_PATTERN, sql);
				if (id != null) {
					LocalDataStore.updateGoods(id.intValue(), goods);
				}
			}

			private List<Goods> selectGoods(String sql) {
				Integer id = extractInt(ID_PATTERN, sql);
				if (id != null) {
					List<Goods> goodsList = new ArrayList<Goods>();
					Goods goods = LocalDataStore.findGoodsById(id.intValue());
					if (goods != null) {
						goodsList.add(goods);
					}
					return goodsList;
				}
				String owner = extract(OWNER_PATTERN, sql);
				if (owner != null) {
					return LocalDataStore.getGoodsByOwner(owner);
				}
				return LocalDataStore.getGoods();
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
