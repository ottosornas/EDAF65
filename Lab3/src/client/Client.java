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
	private String username;
	/**
	 * Creates the client itself
	 * 
	 * @param socket
	 */
	public Client(Socket socket, String username) {
		this.socket = socket;
		ip = socket.getInetAddress();
		this.username = username;
		
		System.out.println("Socket port: " + socket.getPort());
		
		ClientReader thread1 = new ClientReader(socket);
		thread1.start();
		write();
	}

	public void write() {
		Scanner scan = new Scanner(System.in);
		OutputStream os;
		try {
			os = socket.getOutputStream();
			while (true) {
				try {
					String msg = scan.nextLine();
					String ms2 = username + ":" + msg + "*";
					os.write(ms2.getBytes());
					if (msg.contains("Q:")) {
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			socket.close();
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
		if (args.length != 2) {
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
		
		Client client = new Client(socket, args[1]);
		client.write();
	}
}
