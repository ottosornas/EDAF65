package server;

public class Monitor extends Thread{

	private Mailbox mailbox;
	
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
