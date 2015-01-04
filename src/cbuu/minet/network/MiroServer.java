package cbuu.minet.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import cbuu.minet.managers.DBManager;
import cbuu.minet.util.DebugLog;

public class MiroServer {

	private final int PORT= 9394;
	private ServerSocket serverSocket = null;
	
	//thread pool
	private ExecutorService threadPool = null;
	private final int POOL_SIZE = 4;
	
	public MiroServer() throws IOException, SQLException {
		
		DBManager.getInstance().initMySQL();
		
		threadPool = Executors.newFixedThreadPool(
				POOL_SIZE * Runtime.getRuntime().availableProcessors());
		
		serverSocket = new ServerSocket(PORT);
		
		DebugLog.log("Sever Start!");
	}
	
	public void servive() throws IOException
	{
		
		while (true)
		{
			try
			{
				Socket socket =serverSocket.accept();
				DebugLog.log("connect accept"+socket.getInetAddress()+" : "+socket.getPort());
				threadPool.execute(new IServlet(socket));
			} 
			catch (IOException e)
			{
				DebugLog.log("Server close!!!");
				serverSocket.close();
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		new MiroServer().servive();
	}

}
