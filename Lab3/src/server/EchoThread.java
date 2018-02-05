package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class EchoThread extends Thread {

	private String message;
	private OutputStream os;
	private Socket socket;
	
	/**
	 * This class send messages from the server to the clients
	 * @param message
	 * @param socket
	 */
	public EchoThread(String message, Socket socket) {
		this.message = message;
		this.socket = socket;
		try {
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try{
			os.write(("IP: " + socket.getInetAddress() + " said " + message).getBytes());
		}catch(Exception e){
			
		}
	}
}
