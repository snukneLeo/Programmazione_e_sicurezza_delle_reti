import java.io.*;
import java.net.*;

class UDPClient {
	public static void main(String args[]) throws Exception {
		try {
			BufferedReader inFromUser = new BufferedReader(
					new InputStreamReader(System.in));
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");// IP
			// destinazione
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			System.out.println("Inserisci il primo valore\n");
			String val1 = inFromUser.readLine();
			sendData = val1.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, 9876); // IP e PORTA
			// destinazione
			clientSocket.send(sendPacket);

			System.out.println("Inserisci il secondo valore\n");
			String val2 = inFromUser.readLine();
			sendData = val2.getBytes();
			sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, 9876); // IP e PORTA
			// destinazione
			clientSocket.send(sendPacket);

			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			clientSocket.receive(receivePacket);
			String somma = new String(receivePacket.getData());
			System.out.println("FROM SERVER:" + somma);

			// invia ack al server
			sendData = ("ack").getBytes();
			sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, 9876); // IP e PORTA
			clientSocket.send(sendPacket);

			clientSocket.close();
			
		} catch (Exception e) {
		}
	}
}
