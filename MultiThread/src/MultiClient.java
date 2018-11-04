
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.*;

public class MultiClient {
	private Socket socket;
	
	DataOutputStream outstream;
	DataInputStream instream;
	DataInputStream inKeyBoard;

	public MultiClient() {
		try {
			socket = new Socket("localhost", 6000);
			System.out.println("connected to server");
			
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			
			outstream = new DataOutputStream(socket.getOutputStream());
			instream = new DataInputStream(socket.getInputStream());
			MultiClientThread t = new MultiClientThread(instream);
			t.start();
			while (true) {
				int text = scan.nextInt();
				outstream.writeInt(text);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MultiClient();
	}
}
