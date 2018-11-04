package Serial;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import TCPIP.Client;
import TCPIP.Server;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;


public class Serial{
		
	/*public static void main(String[] args) throws IOException {
		try {
			(new Serial()).connect("COM5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
			
	public void connect(String portName, Client client) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName); // get COM5
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: port is alreay used");
		} else {
			int timeout = 30000;
			CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				
				//BufferedInputStream in = new BufferedInputStream(serialPort.getInputStream()); // SerialPort�� ���� �Ÿ������͸� �о�� ��ǲ ��Ʈ��
				//BufferedOutputStream out = new BufferedOutputStream(serialPort.getOutputStream());
				
				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();
				
				(new Thread(new SerialReader(in, client))).start();
				//(new Thread(new SerialWriter(out))).start();
				//System.out.println("connection success");
			} else {
				System.out.println("Error: only serial ports are handled by this example.");
			}
		}
	}// serial Port Ready

	public static class SerialReader implements Runnable  {
		InputStream in;
		
		Scanner scan = new Scanner(System.in);
		Client client = null;
		//Server server = null;
		
		public SerialReader(InputStream in, Client client) {
			this.in = in;
			this.client = client;
			System.out.println("SerialReader Constructor success");
		}
		
		public synchronized void run() {
//			System.out.println("SerialReaderThread run");
//			Scanner scan = new Scanner(System.in);
//			System.out.println("enter somthing: ");
//			String str = scan.nextLine();
//			try {
//				client.getOutput().writeUTF(str);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
			System.out.println("success");
			
			byte[] buffer = new byte[1024]; 
			int len = -1;
	
			String bufferArray = "";
			try {
				client.getOutput().writeUTF("hello");
				while ((len = this.in.read(buffer)) > -1) {
					
					String strBuffer = new String(buffer, 0, len);
					//System.out.println("strBuffer: "+strBuffer);
					for(int i=0; i<strBuffer.length(); i++){
						if(strBuffer.substring(i, i+1) instanceof String){
							//System.out.println("test: "+strBuffer.substring(i, i+1));
							if(strBuffer.substring(i, i+1).equals("s")){
								bufferArray = "";
							}
							else if(strBuffer.substring(i, i+1).equals("e")){
								//System.out.println("message to server: "+Integer.parseInt(bufferArray));
								//System.out.println(bufferArray);
								try {
									xmlChild xml = new xmlChild(Integer.parseInt(bufferArray));
									client.getOutput().writeUTF(xml.getContent());
								}
								catch (ParserConfigurationException e) {
									e.printStackTrace();
								}
								catch (NumberFormatException e) {
									e.printStackTrace();
								}
							}
							else{
								bufferArray += strBuffer.substring(i, i+1);
							}
						}
					}
		
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
//	
//	public static class SerialWriter implements Runnable {
//		DataOutputStream out;
//		xmlChild xc;
//		public SerialWriter(OutputStream out) {
//			this.out = new DataOutputStream(out);
//			try {
//				xc = new xmlChild();
//			} catch (ParserConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		public void run() {
//			
//			// TODO Auto-generated method stub
//			try {
//				int c = 0;
//				while ((c = System.in.read()) > -1) {
//					xc.setValue(c);
//					System.out.println("xml:" + xc.getContent());
//					this.out.writeUTF(xc.getContent());
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}