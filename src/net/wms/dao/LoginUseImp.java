package net.wms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import net.wms.bean.User;
import net.wms.util.DB;

public class LoginUseImp implements LoginUse{
		public static Vector vec = new Vector();
		//��ȡ���ݿ�����
		Connection conn = DB.getConnection();
		//��ѯ����
		public boolean Query(User user, String sql) throws SQLException {
			// TODO Auto-generated method stub
			//����SQL���
			PreparedStatement pra = conn.prepareStatement(sql);
			pra.setString(1, user.getusername());
			pra.setString(2, user.getuserpwd());
			//��������
			ResultSet rs = pra.executeQuery();
			//���������
			while(rs.next()){
				
				//��ȡ���ݿ����ֶ�
				String name = rs.getString("username");
				String pass = rs.getString("userpwd");
				//�ж��û����������Ƿ�������ݿ�����
				if(name.equals(user.getusername()) && pass.equals(user.getuserpwd())) {
					//�������ݷ��� true
					return true;
				}else{
					//���������ݷ���false
					return false;
				}
			}
			return false;
		}
		
		public boolean Query1(User user, String sql) throws SQLException {
			// TODO Auto-generated method stub
			//����SQL���
			PreparedStatement pra = conn.prepareStatement(sql);
			//��������
			ResultSet rs = pra.executeQuery();
			//���������
			while(rs.next()){
				
				//��ȡ���ݿ����ֶ�
				String name = rs.getString("username");
				String pass = rs.getString("userpwd");
				String flag = rs.getString("flag");
				String integrate = rs.getString("integrate");
				//�ж��û����������Ƿ�������ݿ�����
				if(name.equals(user.getusername()) ) {
					//�������ݷ��� true
					user.setusername(name);
					user.setuserpwd(pass);
					user.setFlag(flag);
					user.setIntegrate(integrate);
					return true;
				}else{
					//���������ݷ���false
					return false;
				}
			}
			return false;
		}
		
		public void Add(User user, String sql) throws SQLException {
			// TODO Auto-generated method stub
			PreparedStatement pra = conn.prepareStatement(sql);
			pra.setString(1, user.getusername());
			pra.setString(2, user.getuserpwd());
			pra.setString(3, user.getFlag());
			pra.setString(4, user.getIntegrate());
			pra.executeUpdate();
			pra.close();
		}

		public void Delete(User user, String sql) throws SQLException {
			// TODO Auto-generated method stub
			PreparedStatement pra = conn.prepareStatement(sql);
			pra.executeUpdate();
			pra.close();
		}

		public void Update(User user, String sql) throws SQLException {
			// TODO Auto-generated method stub
			PreparedStatement pra = conn.prepareStatement(sql);
			pra.executeUpdate();
			pra.close();
		}
		public void Select(String sql) throws SQLException {
			PreparedStatement pra = conn.prepareStatement(sql);
			ResultSet rs = pra.executeQuery();
			vec.removeAllElements();
			while(rs.next()) {
				Vector v = new Vector();
				v.add(rs.getInt("id"));
				v.add(rs.getString("username"));
				if(rs.getString("flag").equals("1")) {
					v.add("Users");
				}else {
					v.add("Manager");
				}
				v.add(rs.getString("integrate"));
				vec.add(v);
			}
		}
}
