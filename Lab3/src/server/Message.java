package server;

public class Message {
	private String username;
	private String message;
	private String inetAdress;
	private String type;
	/**
	 * Creates a message to store
	 * @param inetAdress - need to able to link the message to the corresponding client
	 * @param message
	 */
	public Message(String ip, String message){
		this.message = message;
		this.inetAdress = ip;
		splitString();
	}
	
	private void splitString(){
		 String[] split = message.split(":");
	        if(split.length <3){
	        	//System.out.println("Wrong format");
	        	return;
	        }else{
		 	username=split[0].trim();
	        type=split[1].trim();
	        message=split[2].trim();
	        }
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getIP(){
		return inetAdress;
	}
	/**
	 * Different types of messages acts differently
	 * @return
	 */
	public String getType(){
		return type;
	}
}
