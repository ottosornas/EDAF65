package server;

import java.util.ArrayList;

public class Mailbox {

	public static ArrayList<Message> messages;

	/**
	 * Creates the mailbox containing all the messages
	 */
	
	public Mailbox() {
		messages = new ArrayList<Message>();
	}
	
	/**
	 * Adds a message to the list and notifies all threads listening
	 * @param message
	 */
	public synchronized void addMsg(Message message) {
		System.out.println("Message added");
		messages.add(message);
		notifyAll();
	}
	
	public synchronized void fetchMsg() throws InterruptedException{
		if(messages.isEmpty()){
			wait();
		}
	}

}
