package server;

public class Monitor extends Thread{

	private Mailbox mailbox;
	
	/**
	 * Creates a monitor thread that continuously tries to fetch a message from the mailbox
	 * @param mailbox
	 */
	public Monitor(Mailbox mailbox){
		this.mailbox = mailbox;
	}
	
	public void run(){
		while(true){
			try{
				mailbox.fetchMsg();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
