package server;

public class Monitor{

	private Mailbox mailbox;
	private Participants participants;
	/**
	 * Creates a monitor thread that continuously tries to fetch a message from the mailbox
	 * @param mailbox
	 */
	public Monitor(Mailbox mailbox, Participants participants){
		this.mailbox = mailbox;
		this.participants = participants;
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
