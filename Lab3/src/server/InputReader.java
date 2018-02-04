package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class InputReader extends Thread {

	private Socket socket;
	private InputStream inStream;
	private Scanner scan;

	public InputReader(Socket socket) {

		this.socket = socket;

		try {
			inStream = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (socket.isConnected()) {
			int ch;
			StringBuilder sb = new StringBuilder();
			try {
				while ((ch = inStream.read()) != -1) { //https://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
					sb.append((char) ch);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(sb.toString());
		}
		scan.close();
	}

}
