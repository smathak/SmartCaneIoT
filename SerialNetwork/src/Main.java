import java.net.*;
import java.util.*;

import Serial.Serial;
import TCPIP.Client;
import TCPIP.Server;
import gnu.io.CommPortIdentifier;
import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//Server server = new Server();
		Client client = new Client();
		System.out.println("Client Creation Success");
		
		Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
		ArrayList<String> portList = new ArrayList<String>();
		
		while(ports.hasMoreElements()){
			String portName = ports.nextElement().getName();
			portList.add(portName);
		}
	
		System.out.print("SerialPorts: ");
		for(int i=0; i<portList.size(); i++){
			System.out.print(portList.get(i)+" ");
		}
		System.out.println();
		
		Scanner scan = new Scanner(System.in);
		System.out.print("Input Serial Port: "); 
		String port = scan.nextLine();
		
		Serial serial = new Serial();
		System.out.println("Connect serial port");
		serial.connect(port, client);
	}

}
