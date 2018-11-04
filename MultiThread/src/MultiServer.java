import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServer {
	private ArrayList<MultiServerThread> clientList = new ArrayList<MultiServerThread>();
	private ServerSocket server = null;

	public MultiServer() {
		try {
			server = new ServerSocket(6000);
			System.out.println("server start..");
			while (true) {
				Socket socket = server.accept();
				InetAddress ip = socket.getInetAddress();
				String ipAddress = ip.getHostAddress();
				System.out.println(ipAddress + " connected !");
				MultiServerThread t = new MultiServerThread(clientList, socket);

				t.start(); 
				clientList.add(t);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//<?xml version="1.0" encoding="UTF-8" standalone="no"?><sensor value="155"/>
	public static void main(String[] args) {
		new MultiServer();
	}
}