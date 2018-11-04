
import java.io.DataInputStream;
import java.io.IOException;

public class MultiClientThread extends Thread {
	private DataInputStream instream;
	
	public MultiClientThread(DataInputStream in) {
		this.instream = in;
	}

	public void run() {
		try {
			while (true) {
				int text = instream.readInt();
				System.out.println("value: " + text);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}