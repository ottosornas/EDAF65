package server;

public class Message {
	private String inetAdress;
	private String message;
	
	public Message(String inetAdress, String message){
		this.inetAdress = inetAdress;
		this.message = message;
	}
	
	public String getIP(){
		return inetAdress;
	}
	
	public String getMessage(){
		return message;
	}
	
	public char getType(){
		if(message.contains("E:")){
			return 'E';
		}else if(message.contains("Q:")){
			return 'Q';
		}else if(message.contains("M:")){
			return 'M';
		}
		
		return 'N';
	}
}
