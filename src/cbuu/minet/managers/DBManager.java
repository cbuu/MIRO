package cbuu.minet.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBManager {
	
	private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	private static DBManager instance = null;
	private LinkedList<Connection> connectionsPool = null;
	
	private int MAX_CONNECTIONS = 20;
	
	private DBManager(){
	}
	
	public static DBManager getInstance() throws SQLException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	public void initMySQL() throws SQLException {
		try {
			Class.forName(MYSQL_DRIVER);
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","800008");
			String database = "create database if not exists mirodb;";
			
			Statement statement = con.createStatement();
			statement.execute(database);
			statement.close();
			con.close();
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mirodb","root","800008");
			statement = con.createStatement();
			String usertable = "create table if not exists user(username varchar(15) not null , " +
					"icon integer not null," +
					"ip varchar(15)," +
					"primary key (username));";
			statement.execute(usertable);
			
			String identification = "create table if not exists identification(username varchar(15) not null," +
					"password varchar(15) not null," +
					"primary key (username)," +
					"foreign key (username) references user(username))";
			statement.execute(identification);
			
			statement.close();
			con.close();
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initPool();
	}
	
	private void initPool() throws SQLException{
		connectionsPool = new LinkedList<Connection>();
		
		for(int i=0;i<MAX_CONNECTIONS;i++){
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mirodb","root","800008");
			//con.setAutoCommit(false);
			connectionsPool.addFirst(con);
		}
	}
	
	public Connection getConection() throws Exception {
		synchronized (connectionsPool) {
			if(connectionsPool.isEmpty()){
				throw new Exception("there is no spare connection!");
			}
			return connectionsPool.removeLast();
		}
	}
	
	public static void close(Connection connection,Statement statement,ResultSet resultSet) throws SQLException {
		if (resultSet!=null) {
			resultSet.close();
		}
		
		if (statement!=null) {
			statement.close();
		}
		
		if(connection!=null){
			connection.close();
		}
	}
	
	public void closeConection(Connection connection) {
		synchronized (connectionsPool) {
			connectionsPool.addFirst(connection);
		}
	}
}
