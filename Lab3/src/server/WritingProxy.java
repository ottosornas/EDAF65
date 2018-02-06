package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class WritingProxy extends Thread {

	private String message;
	private OutputStream os;
	private Socket socket;
	private Mailbox mailbox;
	private Participants participants;

	/**
	 * This class send messages from the server to the clients
	 * 
	 * @param message
	 * @param socket
	 */
	public WritingProxy(Mailbox mailbox, Socket socket, Participants participants) {
		this.socket = socket;
		this.mailbox = mailbox;
		this.participants = participants;
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
		Message msg = Mailbox.messages.remove(0);
		System.out.println("Message type: " + msg.getType());
		switch (msg.getType()) {
		case 'E':
			os.write(("IP: " + socket.getInetAddress() + " said " + message).getBytes());
			break;
		case 'Q':
			participants.kick(msg.getIP());
			break;
		case 'M':
			for (Socket socket : Participants.sockets) {
				os.write(("IP: " + socket.getInetAddress() + " said " + message).getBytes());
			}
			break;
		default:
			System.out.println("in default");
			os.write(("IP: " + socket.getInetAddress() + " said " + message).getBytes());
			break;
		}

	}
}
