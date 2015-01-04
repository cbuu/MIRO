package minet.cbuu.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NIOServer {

	private ServerSocketChannel channel = null;

	public NIOServer() throws IOException {
		
	}
	
	private void SocketChannelTest() throws IOException{
		SocketChannel socketChannel = SocketChannel.open();
		//socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("jenkov.com", 80));
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		int bytesRead = socketChannel.read(buf);
		
		System.out.println(bytesRead);
		
		buf.flip();
		
		String string = getString(buf);
		System.out.print(string);
		

	}
	
	private void FileChannelTest() throws IOException{
		RandomAccessFile file = new RandomAccessFile("./res/test.txt", "rw");
		FileChannel fileChannel = file.getChannel();
		
		String newData = "New String to write to file..." + System.currentTimeMillis();

		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());

		buf.flip();

		while(buf.hasRemaining()) {
			fileChannel.write(buf);
		}

//		ByteBuffer buffer = ByteBuffer.allocate(128);
//		fileChannel.read(buffer);
//		
//		buffer.flip();
//		String string = getString(buffer);
//		
//		System.out.print(string);
//		System.out.print("nani");
	}

	public static String getString(ByteBuffer buffer) {
		Charset charset = null;
		String string = null;
		charset = Charset.forName("UTF-8");
		string = charset.decode(buffer).toString();
		return string;
	}

	public static void main(String[] args) throws IOException {
		NIOServer server = new NIOServer();
		
		//server.FileChannelTest();
		server.SocketChannelTest();
	}

}
