package TCPIP;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class Server extends Thread{
	public static void main(String[] args) throws IOException{
		Server server = new Server();
		server.start();
	}
	
	ServerSocket serverSocket;
	Socket socket;
	DataInputStream input;
	DataOutputStream output;
	
	public Server() throws IOException{
		this.setServerSocket();
		this.setSocket();
		this.setInput();
		this.setOutput();
		
		this.input = this.getInput();
		this.output = this.getOutput();
		this.serverSocket = this.getServerSocket();
		
		System.out.println("server made");
	}
	
	public DataInputStream getInput() {
		return input;
	}

	public void setInput() throws IOException {
		this.input = new DataInputStream(this.getSocket().getInputStream());
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public void setOutput() throws IOException {
		this.output = new DataOutputStream(new BufferedOutputStream(this.getSocket().getOutputStream()));
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket() throws IOException {
		System.out.println("Server socket is wating for an client's connection");
		
		this.socket = this.getServerSocket().accept();
		System.out.println("socket created by accepting client");
	}	

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket() throws IOException {
		this.serverSocket = new ServerSocket(6000);
		System.out.println("ServerSocket Creadted with Port Number 6000");
	}
	public void accept() throws IOException{
		this.socket = serverSocket.accept();
		System.out.println("server accept client");
	}
	
	@Override
	public void run(){
		System.out.println("Server Thread is running");
		int data = 0;
		while(true){
			try {
				data = this.getInput().readInt();
				System.out.println("data from client: "+data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
