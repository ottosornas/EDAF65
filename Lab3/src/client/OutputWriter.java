package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class OutputWriter extends Thread {

	OutputStream os;
	Socket socket;
	Scanner scan;

	public OutputWriter(Socket socket) {
		try {
			this.socket = socket;
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		scan = new Scanner(System.in);
		while (!socket.isClosed()) {
			try {
				String msg = scan.nextLine();
				os.write(msg.getBytes());
				if (msg.contains("Q:")) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
