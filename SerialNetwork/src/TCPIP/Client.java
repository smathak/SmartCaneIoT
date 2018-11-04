package TCPIP;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.*;

public class Client {
	public static void main(String[] args) throws IOException{
		Client client = new Client();
	}
	
	DataInputStream input;
	DataOutputStream output;
	Socket socket;
	
	public Client() throws IOException{
		this.setSocket();
		this.setInput();
		this.setOutput();
	
		//input = this.getInput();
		//output = this.getOutput();
		//socket = this.getSocket();
	}
	
	public DataInputStream getInput() {
		return input;
	}
	public void setInput() throws IOException {
		this.input = new DataInputStream(new BufferedInputStream(this.getSocket().getInputStream()));
	}
	public DataOutputStream getOutput() {
		return output;
	}
	public void setOutput() throws IOException {
		this.output = new DataOutputStream((this.getSocket().getOutputStream()));
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket() throws UnknownHostException, IOException {
		Scanner scan = new Scanner(System.in);
		System.out.print("Input ip address: "); 
		String ip = scan.nextLine();
		System.out.println("ip is: " + ip);
		System.out.print("Input Port Number: ");
		int portNumber = scan.nextInt();
		System.out.println("portNumber is: "+portNumber);
		try {
			System.out.println("client socket is going to connect to server");
			this.socket = new Socket(ip, portNumber); // connect 
			System.out.println("connection success");
		} catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("successfully connected");
	}
}
