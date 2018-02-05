package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mailbox {

	private static ArrayList<Message> messages;
	private Participants participants;
	private ExecutorService es;

	/**
	 * Creates the mailbox containing all the messages
	 */
	
	public Mailbox() {
		messages = new ArrayList<Message>();
		participants = new Participants();
		es = Executors.newFixedThreadPool(10);
	}
	
	/**
	 * Adds a message to the list and notifies all threads listening
	 * @param message
	 */
	public synchronized void addMsg(Message message) {
		messages.add(message);
		notifyAll();
	}

	/**
	 * Fetches the first message in the list and acts diffrently depending on what type of message it is
	 * @throws InterruptedException
	 */
	public synchronized void fetchMsg() throws InterruptedException {
		if (messages.isEmpty()) {
			wait();
		} else {
			Message msg = messages.remove(0);
			switch (msg.getType()) {
			case 'E':
				es.submit(new EchoThread(msg.getMessage(), participants.returnMatch(msg.getIP())));
				break;
			case 'Q':
				participants.kick(msg.getIP());
				break;
			case 'M':
				for (Socket socket : Participants.sockets) {
					es.submit(new EchoThread(msg.getMessage(), socket));
				}
				break;
			default:
				System.out.println("in default");
				es.submit(new EchoThread(msg.getMessage(), participants.returnMatch(msg.getIP())));
				break;
			}
		}

	}

}
