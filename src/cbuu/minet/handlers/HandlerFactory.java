package cbuu.minet.handlers;

import java.net.Socket;

import cbuu.minet.common.IHandler;
import cbuu.minet.common.IMessage;

public class HandlerFactory {
	
	public HandlerFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static IHandler createHandler(Socket socket,IMessage msg) {
		IHandler handler = null;
		
		switch (msg.getType()) {
		case IMessage.MSG_LOGIN:
			handler =  new LoginHandler(socket,msg);break;
		case IMessage.MSG_REGISTER:
			handler = new RegisterHandler(socket,msg);break;
		case IMessage.MSG_PULL:
			handler = new PullHandler(socket,msg);break;
		case IMessage.MSG_ATTEND:
			break;
		case IMessage.MSG_POST:
			break;
		case IMessage.MSG_SEND_BROCAST:
			break;
		case IMessage.MSG_BEATHEART:
			break;
		}
		
		return handler;
	}
}
