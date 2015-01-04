package minet.cbuu.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cbuu.minet.util.DebugLog;

public class NoBlockServer
{
	private int port = 9394;
	private Selector selector = null;
	//private ServerSocket serverSocket;
	private ServerSocketChannel ssc = null;
	
	//thread pool
	private ExecutorService executorService;
	private final int POOL_SIZE = 4;
	private Charset charset = Charset.forName("GBK");
	
	public NoBlockServer() throws IOException
	{
		//adapt to the number of CPU
		executorService = Executors.newFixedThreadPool(
				Runtime.getRuntime().availableProcessors()*POOL_SIZE);
		
		//serverSocket = new ServerSocket(port);
		selector = Selector.open();
		ssc = ServerSocketChannel.open();
		ssc.socket().setReuseAddress(true);
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(port));
		
		System.out.println("start");
	}
	
	
	
	public void servive() throws IOException
	{
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		while(selector.select()>0)
		{
			Set readykeys = selector.selectedKeys();
			Iterator iterator = readykeys.iterator();
			while(iterator.hasNext())
			{
				SelectionKey key = null;
				try {
					key = (SelectionKey) iterator.next();
					iterator.remove();
					if(key.isAcceptable())
					{
						ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
						SocketChannel socketChannel = serverSocketChannel.accept();
						socketChannel.configureBlocking(false);
						Socket socket = socketChannel.socket();
						System.out.println("connect"+socket.getInetAddress()+":"+socket.getPort());
						ByteBuffer buff = ByteBuffer.allocate(1024);
						//register the read and write event and carry a buff to store the data from Client
						socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE,buff);
					}
					if(key.isReadable())
					{
						DebugLog.log("reading");
						receive(key);
					}
					if(key.isWritable())
					{
						//send(key);
					}
				} catch (IOException e) {
					e.printStackTrace();
					try {
						if(key!=null)
						{
							key.cancel();
							key.channel().close();
						}
					} catch (Exception e2) {
						e.printStackTrace();
					}
				} 
				
			}
		}
	}
	
	public void  receive(SelectionKey key) throws IOException {

		//begin to receive
		ByteBuffer buffer  = (ByteBuffer) key.attachment();
		SocketChannel socketChannel = (SocketChannel)key.channel();
		
		socketChannel.read(buffer);
		buffer.flip();
		String msg = decode(buffer);
		DebugLog.log(msg);
		
		//ByteBuffer readBuffer = ByteBuffer.allocate(1024);
		//socketChannel.read(readBuffer);
		
		//String msg = decode(readBuffer);
		//if(msg.indexOf("\r\n") == -1)return;

		//flip is used to set the limit to the position 
		//and set the position to 0
		//readBuffer.flip();
		
		//set the buffer limitation to capacity
		//buffer.limit(buffer.capacity());
		//copy the readBuffer to the buffer
		//buffer.put(readBuffer);
	}
	
	public void  send(SelectionKey key) throws IOException {
		//begin to write
		ByteBuffer buffer = (ByteBuffer)key.attachment();
		SocketChannel socketChannel = (SocketChannel)key.channel();
		
		//flip is used to set the limit to the position 
		//and set the position to 0
		buffer.flip();
		String data = decode(buffer);
		if(data.indexOf("\r\n") == -1)return;
		//intercept a line data
		String outputData = data.substring(0,data.indexOf("\n")+1);
		System.out.print(outputData);
		
		//BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
		//String myMsg = localReader.readLine();
		
		ByteBuffer outputbuff = encode("echo:"+outputData);
		while(outputbuff.hasRemaining())
		{
			socketChannel.write(outputbuff);
		}
		ByteBuffer temp = encode(outputData);
		buffer.position(temp.limit());
		buffer.compact();
		if(outputData.equals("end\r\n"))
		{
			key.cancel();
			key.channel().close();
			System.out.println("connection closed");
		}
	}
	
	
	//decode the byte to string 
	public String decode(ByteBuffer byteBuffer) {
		CharBuffer charBuffer = charset.decode(byteBuffer);
		return charBuffer.toString();
	}
	
	//encode the string to byte
	public ByteBuffer encode(String str) {
		return charset.encode(str);
	}
	
	public static void main(String []arg) throws IOException
	{
		new NoBlockServer().servive();
	}

}
