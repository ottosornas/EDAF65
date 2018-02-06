package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class WritingProxy extends Thread {

	private Message message;
	private OutputStream os;
	private Socket socket;

	/**
	 * This class send messages from the server to the clients
	 * 
	 * @param message
	 * @param socket
	 */
	public WritingProxy(Message message, Socket socket) {
		this.socket = socket;
		this.message = message;
		try {
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			sendMsg();
		} catch (Exception e) {

		}
	}

	/**
	 * Fetches the first message in the list and acts differently depending on
	 * what type of message it is
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void sendMsg() throws InterruptedException, IOException {
		System.out.println("Message type: " + message.getType());
		switch (message.getType()) {
		case "E":
			if (socket.equals(Mailbox.clientList.get(message.getUsername()))) {
				os.write(("You said: " + message.getMessage() + "*").getBytes());
			}
			break;
		case "Q":
			Mailbox.clientList.get(message.getUsername()).close();
			Mailbox.clientList.remove(message.getUsername());
			
			break;
		case "M":
			os.write(("User: " + message.getUsername() + " broadcasts " + message.getMessage() + "*").getBytes());
			break;
		default:
			System.out.println("in default");
			os.write(("IP: " + socket.getInetAddress() + " said " + message.getMessage() + "*").getBytes());
			break;
		}

	}
}
