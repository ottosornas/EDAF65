package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientReader extends Thread {
	private InputStream is;
	private Socket socket;
	
	/**
	 * Creates an object that reads the messages from the server
	 * @param socket
	 */
	public ClientReader(Socket socket){
	        this.socket=socket;
	        try {
	            is=socket.getInputStream();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	public void run() {
		while (!socket.isClosed()) {
			try {
				System.out.println(readMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String readMessage() throws IOException {

		int c = is.read();
		StringBuilder sb = new StringBuilder();
		while ((c != -1) && (c != 42)) {
			sb.append((char) c);
			c = is.read();
		}
		return sb.toString();
	}
}
