package client;

import java.net.InetAddress;
import java.net.Socket;

public class Client {
	
	private InetAddress ip;
	private int port;
	private Socket socket;
	
	/**
	 * Creates the client itself (den här är antagligen fel)
	 * @param socket
	 */
	public Client(Socket socket){
		this.socket = socket;
		ip = socket.getInetAddress();
		port = socket.getPort();
		
		OutputWriter thread1 = new OutputWriter(socket);
		ClientReader thread2 = new ClientReader(socket);
		thread1.start();
		thread2.start();
	}
	
	public String getClient(){
		return ip + ":" + port;
	}
	
	public Socket getSocket(){
		return socket;
	}
}
