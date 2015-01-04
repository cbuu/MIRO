package cbuu.minet.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cbuu.minet.common.Dao;
import cbuu.minet.common.User;
import cbuu.minet.managers.DBManager;
import cbuu.minet.util.DebugLog;

public class UserDao extends Dao{
	
	public UserDao(Connection connection) {
		super(connection);
		
		DebugLog.log(connection.toString());
	}

	public int addUser(User user) {
		PreparedStatement stmt = null;
		int result = 0;
		
		String username = user.getUsername();
		int icon = user.getIconNum();
		String ip =  user.getIP();
		int state = user.getState();
		try {
			String sql = "insert into user values(?,?,?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setInt(2, icon);
			stmt.setString(3, ip);
			stmt.setInt(4, state);
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

	public int updateUser(User user) {
		PreparedStatement stmt = null;
		int result = 0;
		
		String username = user.getUsername();
		int icon = user.getIconNum();
		String ip = user.getIP();
		int state = user.getState();
		try {
			String sql = "update user set icon=?,ip=?,state=? where username=?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, icon);
			stmt.setString(2, ip);
			stmt.setInt(3, state);
			stmt.setString(4, username);
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
	
	public int deleteUserByUsername(String username) throws SQLException, Exception{
		
		DaoFactory.creatIdentificationDao().deleteByUsername(username);
		
		PreparedStatement stmt = null;
		int result = 0;
		
		try {
			String sql = "delete from user where username=?";
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

	public User queryUserByUsername(String username){
		PreparedStatement stmt = null;
		User user = null;
		ResultSet rs = null;
		try {
			String sql = "select * from user where username=?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			rs =stmt.executeQuery();
			if (rs.next()) {
				user = new User(rs.getString(1), rs.getString(3));
				user.setIconNum(rs.getInt(2));
				user.setState(rs.getInt(4));
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
		return user;
	}
	
	public ArrayList<User> queryUsersForList() {
		ArrayList<User> list = new ArrayList<User>();
		
		PreparedStatement stmt = null;
		User user = null;
		ResultSet rs = null;
		try {
			String sql = "select * from user";
			stmt = connection.prepareStatement(sql);
			rs =stmt.executeQuery(sql);
			
			while (rs.next()) {
				user = new User(rs.getString(1), rs.getString(3));
				user.setIconNum(rs.getInt(2));
				user.setState(rs.getInt(4));
				list.add(user);
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
		
		return list;
	}
	
}
