package cbuu.minet.handlers;

import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;

import cbuu.minet.common.IHandler;
import cbuu.minet.common.IMessage;
import cbuu.minet.common.User;
import cbuu.minet.database.DaoFactory;
import cbuu.minet.managers.UserManager;
import cbuu.minet.util.DebugLog;

public class RegisterHandler extends IHandler{

	public RegisterHandler(Socket socket,IMessage msg) {
		super(socket,msg);
	}

	@Override
	public void run() {
		
		HashMap<String, String> args = msg.getArgs();
		
		String username = args.get("username");
		String password = args.get("password");
		String ip 				  = socket.getInetAddress().toString();
		
		DebugLog.log("username", username);
		DebugLog.log("password", password);
		
		IMessage newMsg = new IMessage(IMessage.MSG_RESPOND);
		int result = 0;
		int result2 = 0;
		User newUser = new User(username, ip);
		newUser.setState(User.STATE_ONLINE);
		try {
			result = UserManager.getInstance().addUser(newUser);
			result2 = DaoFactory.creatIdentificationDao().addUser(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == 1&&result2==1) {
			newMsg.addArgs("code", IMessage.OK);
			newMsg.addArgs("content", "register successfully!");
		}else {
			newMsg.addArgs("code", IMessage.ERROE);
			newMsg.addArgs("content", "error!");
		}
		sendMessage(newMsg);
	}
	
}
