package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import client.ClientReader;

public class Client {

	private InetAddress ip = null;
	private static final int port = 30000;
	private Socket socket;

	/**
	 * Creates the client itself (den här är antagligen fel)
	 * 
	 * @param socket
	 */
	public Client(Socket socket) {
		this.socket = socket;
		ip = socket.getInetAddress();
		
		System.out.println("Socket port: " + socket.getPort());
		
		ClientReader thread1 = new ClientReader(socket);
		thread1.start();
		run();
	}

	public void run() {
		Scanner scan = new Scanner(System.in);
		OutputStream os;
		try {
			os = socket.getOutputStream();
			while (true) {
				try {
					String msg = scan.nextLine();
					os.write(msg.getBytes());
					if (msg.contains("Q:")) {
						socket.close();
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		scan.close();
		
	}

	public String getClient() {
		return ip + ":" + port;
	}

	public Socket getSocket() {
		return socket;
	}
	
	public static void main(String[] args){
		if (args.length != 1) {
			System.out.println("Syntax: java Client <address>");
			System.exit(1);
		}
		Socket socket = null;
		try {
			socket = new Socket(args[0], port);
			
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		
		Client client = new Client(socket);
		client.run();
	}
}
