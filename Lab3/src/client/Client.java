package client;

import java.net.Socket;

public class Client {
	
	public Client(Socket socket){
		OutputWriter thread1 = new OutputWriter(socket);
		thread1.start();
	}
	
}
