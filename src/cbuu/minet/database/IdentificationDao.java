package cbuu.minet.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cbuu.minet.common.Dao;
import cbuu.minet.common.User;
import cbuu.minet.managers.DBManager;

public class IdentificationDao extends Dao{

	public IdentificationDao(Connection connection) {
		super(connection);
	}
	
	public String queryPasswordByUsername(String username){
		PreparedStatement stmt = null;
		String password = null;
		ResultSet rs = null;
		try {
			String sql = "select password from identification where username=?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			rs =stmt.executeQuery();
			if (rs.next()) {
				password = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					DBManager.getInstance().closeConection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return password;
	}
	
	public int deleteByUsername(String username){
		PreparedStatement stmt = null;
		int result = 0;
		
		try {
			String sql = "delete from identification where username=?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			result =  stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					DBManager.getInstance().closeConection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	public int updateUser(String username,String password) {
		PreparedStatement stmt = null;
		int result = 0;
		try {
			String sql = "update identification set password=? where username=?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, password);
			stmt.setString(2, username);
			result =  stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					DBManager.getInstance().closeConection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	public int addUser(String username,String password){
		PreparedStatement stmt = null;
		int result = 0;
		
		try {
			String sql = "insert into identification values(?,?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			result =  stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					DBManager.getInstance().closeConection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}






}
