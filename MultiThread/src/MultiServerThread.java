
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServerThread extends Thread {
	private Socket socket;
	private ArrayList<MultiServerThread> clientList;
	
	DataInputStream instream;
	DataOutputStream outstream;

	public MultiServerThread(ArrayList<MultiServerThread> clientList, Socket socket) {
		this.socket = socket;
		this.clientList = clientList;
	}

	public synchronized void run() {
		try {
			instream = new DataInputStream(socket.getInputStream());
			outstream = new DataOutputStream(socket.getOutputStream());
			
			while (true) {
				String msg = "";
				//int msg = instream.readInt();
				msg = instream.readUTF();
				System.out.println(msg);
				broadcasting(msg);
			}
		} catch (IOException e) {
			clientList.remove(this);
		}
	}

//	public void broadcasting(int msg) throws IOException {
//		for (MultiServerThread t : clientList) {
//			t.sendMsg(msg);
//		}
//	}
	
	public void broadcasting(String msg) throws IOException{
		for(MultiServerThread t : clientList){
			t.sendMsg(msg);
		}
	}

//	public void sendMsg(int msg) throws IOException {
//		outstream.writeInt(msg);
//		outstream.flush();
//	}
	
	public void sendMsg(String msg) throws IOException{
		outstream.writeUTF(msg);
		outstream.flush();
	}
}