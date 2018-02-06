package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class InputReader extends Thread {

	private Socket socket;
	private InputStream inStream;
	private Mailbox mailbox;

	/**
	 * Creates an inputreader which reads the client's output
	 * 
	 * @param socket
	 * @param mailbox
	 */
	public InputReader(Socket socket, Mailbox mailbox) {
		this.mailbox = mailbox;
		this.socket = socket;
		try {
			inStream = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the output and adds it as a message to the mailbox
	 */
	public void run() {

		while (!socket.isClosed()) {
			try {
				StringBuilder sb = new StringBuilder();
				Message msg;
				int ch = inStream.read();
				while ((ch != 42) && (ch != -1)) {
					System.out.println("Ch: " + ch);
					sb.append((char) ch);
					ch = inStream.read();
				}
				//System.out.println("Message: " + sb.toString());

				msg = new Message(socket.getInetAddress().toString(), sb.toString());
				mailbox.newClient(msg.getUsername(), this.socket);
				mailbox.addMsg(msg);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
