package server;

public class Fetcher extends Thread {

	private Mailbox mailbox;

	/**
	 * Creates a thread that continuously tries to fetch a message from the
	 * mailbox
	 * 
	 * @param mailbox
	 */

	public Fetcher(Mailbox mailbox) {
		this.mailbox = mailbox;
	}

	public void run() {
		while (true) {
			try {
				Message message = mailbox.fetchMsg();
				if(message.getType()!=null){
				for(String s : Mailbox.clientList.keySet()){
					System.out.println(s);
					WritingProxy thread1 = new WritingProxy(message, Mailbox.clientList.get(s));
					thread1.start();
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
