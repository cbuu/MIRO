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

public class LoginHandler extends IHandler{

	public LoginHandler(Socket socket,IMessage msg) {
		super(socket,msg);
	}

	@Override
	public void run() {
		HashMap<String, String> args = msg.getArgs();
		
		String ip = socket.getInetAddress().toString();
		String username = args.get("username");
		String password_ = args.get("password");
		DebugLog.log("login", username);
		
		String password = null;
		
		try {
			password = DaoFactory.creatIdentificationDao().queryPasswordByUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		IMessage newMsg = new IMessage(IMessage.MSG_RESPOND);
		int result = 0;
		if (password!=null) {
			DebugLog.log("password", password);
			if (password.equals(password_)) {
				User user = new User(username, ip);
				user.setState(User.STATE_ONLINE);
				try {
					result = DaoFactory.creatUserDao().updateUser(user);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (result==1) {
				newMsg.addArgs("code",IMessage.OK);
				newMsg.addArgs("content", "login successfully!");
			}else {
				newMsg.addArgs("code", IMessage.ERROE);
				newMsg.addArgs("content", "error");
			}
		}else {
			newMsg.addArgs("code", IMessage.ERROE);
			newMsg.addArgs("content", "error");
		}
		sendMessage(newMsg);
	}
}
