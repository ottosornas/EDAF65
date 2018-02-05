package server;

import java.net.Socket;
import java.util.ArrayList;

public class Participants {

	public static ArrayList<Socket> sockets;

	public Participants() {
		sockets = new ArrayList<Socket>();
	}

	public void addParticipant(Socket socket) {
		sockets.add(socket);
	}

	public void kick(String IP) {
		Socket temp;
		if ((temp = returnMatch(IP)) != null) {
			sockets.remove(temp);
		}
	}

	public Socket returnMatch(String IP) {
		for (Socket socket : sockets) {
			if (socket.getInetAddress().toString().equals(IP)) {
				return socket;
			}
		}

		return null;
	}
}
