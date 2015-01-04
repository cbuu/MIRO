package cbuu.minet.database;

import java.sql.Connection;
import java.sql.SQLException;

import cbuu.minet.common.Dao;
import cbuu.minet.managers.DBManager;

public class DaoFactory {

	public static UserDao creatUserDao() throws SQLException, Exception {
		Connection connection = DBManager.getInstance().getConection();
		return new UserDao(connection);
	}
	
	public static IdentificationDao creatIdentificationDao() throws SQLException, Exception {
		Connection connection = DBManager.getInstance().getConection();
		return new IdentificationDao(connection);
	}
	
}
