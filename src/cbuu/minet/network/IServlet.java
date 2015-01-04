package cbuu.minet.network;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import com.google.gson.Gson;

import cbuu.minet.common.IHandler;
import cbuu.minet.common.IMessage;
import cbuu.minet.handlers.HandlerFactory;
import cbuu.minet.util.DebugLog;

public class IServlet implements Runnable {

	private final String DEFAULT_CODER = "UTF-8";

	private Socket socket = null;
	private BufferedReader br = null;

	public IServlet(Socket socket) throws IOException {
		this.socket = socket;
		this.br = getReader(socket);
	}

	public BufferedReader getReader(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(is, DEFAULT_CODER));
	}

	@Override
	public void run() {
		String receiveString = null;
		try {
			while ((receiveString = br.readLine()) != null) {
				DebugLog.log("receive", receiveString);
				
				IMessage msg = IMessage.toMessage(receiveString);

				IHandler handler = HandlerFactory.createHandler(socket,msg);

				handler.run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
