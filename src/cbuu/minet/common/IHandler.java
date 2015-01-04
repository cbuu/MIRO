package cbuu.minet.common;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class IHandler {
	
	protected final String DEFAULT_CODER = "UTF-8";

	protected Socket socket = null;
	protected PrintWriter pw = null;
	
	protected IMessage msg = null;
	
	public IHandler(Socket socket,IMessage msg)  {
		this.socket = socket;
		this.msg = msg;
		try {
			this.pw = getWriter(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PrintWriter getWriter(Socket socket) throws IOException {
		return new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),
				DEFAULT_CODER), true);
	}

	protected void sendMessage(IMessage msg) {
		pw.println(msg.toString());
	}
	
	//this method is to handle the network
	public abstract void run();
}
