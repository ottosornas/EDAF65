package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class InputReader extends Thread {

	private Socket socket;
	private InputStream inStream;

	public InputReader(Socket socket) {

		this.socket = socket;

		try {
			inStream = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		
		while (!socket.isClosed()) {
			try {
				StringBuilder sb = new StringBuilder();
				int ch = inStream.read();
				while(ch!=13) { // https://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
					sb.append((char) ch);
					ch = inStream.read();	
				}
				System.out.println("Message: " + sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
