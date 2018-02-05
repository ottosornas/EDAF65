package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class InputReader extends Thread {

	private Socket socket;
	private InputStream inStream;
	private Mailbox mailbox;
	
	public InputReader(Socket socket, Mailbox mailbox) {
		this.mailbox = mailbox;
		this.socket = socket;
		
		try {
			inStream = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		
		while (!socket.isClosed()) {
			try {
				StringBuilder sb = new StringBuilder();
				int ch = inStream.read();
				while(ch!=13) {
					sb.append((char) ch);
					ch = inStream.read();	
				}
				System.out.println("Message: " + sb.toString());
				mailbox.addMsg(new Message(socket.getInetAddress().toString(), sb.toString()));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
