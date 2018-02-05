package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.Client;

public class Server {

	private int port;
	private boolean connected;
	private ExecutorService es;

	/**
	 * Creates the server on the given port
	 * 
	 * @param port
	 */
	public Server(int port) {

		es = Executors.newFixedThreadPool(10);
		this.port = port;

	}

	/**
	 * Starts the server
	 */
	public void startServer() {

		connected = true;

		try {
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Server started on port: " + port);

			while (connected) {

				Socket socket = ss.accept();

				if (socket.isConnected()) {
					System.out.println("Client connected on IP: " + socket.getInetAddress());
					new Client(socket);
					es.submit(new InputReader(socket));
				}
			}

		} catch (IOException e) {
			System.out.println("Failed to start server");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Server(30000).startServer();
	}
}
