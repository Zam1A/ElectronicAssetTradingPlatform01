package net.wms.dao;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.wms.bean.User;
import net.wms.util.LocalDataStore;

public class LoginUseImp implements LoginUse{
		public static Vector vec = new Vector();

		private static final Pattern FLAG_PATTERN = Pattern.compile("flag\\s*=\\s*'?([0-9]+)'?", Pattern.CASE_INSENSITIVE);
		private static final Pattern USERNAME_PATTERN = Pattern.compile("username\\s*=\\s*'([^']*)'", Pattern.CASE_INSENSITIVE);
		private static final Pattern PASSWORD_PATTERN = Pattern.compile("userpwd\\s*=\\s*'([^']*)'", Pattern.CASE_INSENSITIVE);
		private static final Pattern INTEGRATE_SUB_PATTERN = Pattern.compile("integrate\\s*=\\s*integrate\\s*-\\s*'?([0-9]+)'?", Pattern.CASE_INSENSITIVE);

		public boolean Query(User user, String sql) throws SQLException {
			User savedUser = LocalDataStore.findUser(user.getusername());
			String expectedFlag = firstNonBlank(user.getFlag(), extract(FLAG_PATTERN, sql));
			return savedUser != null
					&& savedUser.getuserpwd().equals(user.getuserpwd())
					&& (expectedFlag == null || expectedFlag.equals(savedUser.getFlag()));
		}
		
		public boolean Query1(User user, String sql) throws SQLException {
			String username = firstNonBlank(user.getusername(), extract(USERNAME_PATTERN, sql));
			User savedUser = LocalDataStore.findUser(username);
			if (savedUser == null) {
				return false;
			}
			user.setId(savedUser.getId());
			user.setusername(savedUser.getusername());
			user.setuserpwd(savedUser.getuserpwd());
			user.setFlag(savedUser.getFlag());
			user.setIntegrate(savedUser.getIntegrate());
			return true;
		}
		
		public void Add(User user, String sql) throws SQLException {
			try {
				LocalDataStore.addUser(user);
			} catch (RuntimeException e) {
				throw new SQLException(e);
			}
		}

		public void Delete(User user, String sql) throws SQLException {
			try {
				String command = sql == null ? "" : sql.trim().toLowerCase(Locale.ENGLISH);
				String username = firstNonBlank(user.getusername(), extract(USERNAME_PATTERN, sql));
				if (command.startsWith("delete from users")) {
					LocalDataStore.deleteUser(username);
				} else if (command.startsWith("update users")) {
					String password = extract(PASSWORD_PATTERN, sql);
					String amount = extract(INTEGRATE_SUB_PATTERN, sql);
					if (password != null) {
						LocalDataStore.updatePassword(username, password);
					} else if (amount != null) {
						LocalDataStore.adjustIntegrate(username, -Integer.parseInt(amount));
					}
				}
			} catch (RuntimeException e) {
				throw new SQLException(e);
			}
		}

		public void Update(User user, String sql) throws SQLException {
			Delete(user, sql);
		}
		public void Select(String sql) throws SQLException {
			vec.removeAllElements();
			for (User user : LocalDataStore.getUsers()) {
				Vector v = new Vector();
				v.add(user.getId());
				v.add(user.getusername());
				if("1".equals(user.getFlag())) {
					v.add("Users");
				}else {
					v.add("Manager");
				}
				v.add(user.getIntegrate());
				vec.add(v);
			}
		}

		private String extract(Pattern pattern, String sql) {
			if (sql == null) {
				return null;
			}
			Matcher matcher = pattern.matcher(sql);
			return matcher.find() ? matcher.group(1) : null;
		}

		private String firstNonBlank(String first, String second) {
			return first != null && first.trim().length() > 0 ? first : second;
		}
}
