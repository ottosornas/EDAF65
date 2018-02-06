package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Mailbox {

	public static ArrayList<Message> messages;
	public static HashMap<String, Socket> clientList;

	/**
	 * Creates the mailbox containing all the messages
	 */
	
	public Mailbox() {
		messages = new ArrayList<Message>();
		clientList = new HashMap<>();
	}
	
	public void newClient(String username, Socket socket){
		if(!clientList.containsKey(username)){
		clientList.put(username, socket);
		}
	}
	
	/**
	 * Adds a message to the list and notifies all threads listening
	 * @param message
	 */
	public synchronized void addMsg(Message message) {
		messages.add(message);
		notifyAll();
	}
	
	public synchronized Message fetchMsg() throws InterruptedException{
		while(messages.isEmpty()){
			wait();
		}
		return messages.remove(0);
		
	}

}
