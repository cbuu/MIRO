package cbuu.minet.handlers;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;

import cbuu.minet.common.IHandler;
import cbuu.minet.common.IMessage;
import cbuu.minet.common.User;
import cbuu.minet.managers.UserManager;

public class PullHandler extends IHandler{

	public PullHandler(Socket socket,IMessage msg) {
		super(socket,msg);
	}

	@Override
	public void run() {
//		HashMap<String, User> map = UserManager.getInstance().getUserMap();
//		
//		ArrayList<User> users = new ArrayList<User>(map.values());
//		
//		Gson gson = new Gson();
//		
//		String data = gson.toJson(users);
//		
//		IMessage msg = new IMessage(IMessage.MSG_PUSH);
//		msg.setData(data);
//		
//		sendMessage(msg);
		
	}

}
