package cbuu.minet.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;

import cbuu.minet.managers.DBManager;

public class Dao{
	protected Connection connection = null;
	
	public Dao(Connection connection) {
		this.connection = connection;
	}
	
	public void close() throws SQLException {
		DBManager.getInstance().closeConection(connection);
	}
}
