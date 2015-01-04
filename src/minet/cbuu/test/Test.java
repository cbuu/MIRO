package minet.cbuu.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


import cbuu.minet.common.IMessage;
import cbuu.minet.util.DebugLog;

import com.google.gson.Gson;

public class Test {
	public Test() {
		// TODO Auto-generated constructor stub
	}
	

	
	public static void main(String[] args) throws UnknownHostException, IOException, SQLException {
		

//		DebugLog.log(InetAddress.getLocalHost().getHostAddress());
		
//	  Socket socket = new Socket("127.0.0.1",9394);
//	  DebugLog.log(socket.getLocalAddress().getHostAddress());
//		
//		IMessage iMessage = new IMessage(IMessage.MSG_LOGIN);
//		
//		String string = "407471516";
		
//		iMessage.setData(string.getBytes("UTF8"));
//		iMessage.setIP("192.168.1.1");
//		iMessage.setPort("80");
		
//		iMessage.addArgs("username", "CBUU");
//		iMessage.addArgs("password", "12330065");
//		
//		Gson gson = new Gson();
//		String json = gson.toJson(iMessage);
//		
//		System.out.println(json);
		
//		IMessage message = gson.fromJson(json, IMessage.class);
//		
//		String dataString=new String(message.getData(),"UTF-8");
//		
//		System.out.println(dataString);
		
		Connection dbConn = TopicFriendDB.getInstance().getConnection();
		Statement statement = dbConn.createStatement();
		statement.executeUpdate("create table USER(USERNAME TEXT PRIMARY KEY)");
		statement.close();
		
		//PreparedStatement insertStmt=dbConn.prepareStatement("insert into usr(sex) values(?)");
		//insertStmt.setInt(1, 16);
		//insertStmt.executeUpdate();
		//insertStmt.close();
		
//		PreparedStatement selectStmt=dbConn.prepareStatement("select sex from usr");
//		ResultSet selectRes=selectStmt.executeQuery();
//		if(selectRes.next())
//		{
//			int sex=selectRes.getInt("sex");
//			System.out.println("sex:"+sex);
//		}
//		selectRes.close();
//		
//		selectStmt.close();
		
		dbConn.commit();
	}
}
