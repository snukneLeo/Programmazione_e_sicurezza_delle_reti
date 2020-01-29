import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

class UDPServer {
	public static void main(String args[]) throws Exception {
		try {
			DatagramSocket serverSocket = new DatagramSocket(9876);
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			byte[] sendSomma = new byte[1024];
		  boolean gotAck = false;
			System.out.print("Attesa connessione...");
			while (true && !gotAck) {
				try {
				  // riceviamo due numeri e facciamo la somma
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					System.out.println("attesa primo valore");
					serverSocket.receive(receivePacket);
					String str1 = new String(receivePacket.getData());
					int val1 = Integer.parseInt(str1.trim());
					System.out.println("got it " + val1 );

				  receiveData = new byte[1024];
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					System.out.println("attesa secondo valore");
					serverSocket.receive(receivePacket);
					String str2 = new String(receivePacket.getData());
					int val2 = Integer.parseInt(str2.trim());
					System.out.println("got it " + val2 );

				  // rispondiamo al client con la somma
				  InetAddress IPAddress = receivePacket.getAddress();
				  int port = receivePacket.getPort();
					int somma = val1 + val2;
					sendSomma = (Integer.toString(somma)).getBytes();
					System.out.println("somma: " + somma );
					DatagramPacket sendPacket = new DatagramPacket(sendSomma,sendSomma.length, IPAddress, port);
					serverSocket.send(sendPacket);

					// aspetto per un ack dal client
					while(!gotAck){
							try{
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					System.out.println("attesa ack");
					serverSocket.setSoTimeout(10000);
					serverSocket.receive(receivePacket);
					String ack = new String(receivePacket.getData());
					if (ack.trim().equals("ack")){
						  System.out.println("ack ricevuto: " + ack);
							gotAck = true;
							serverSocket.close();
					}
							}catch(SocketTimeoutException e){
							// invia la somma ancora
						  System.out.println("reinvio somma");
							sendPacket = new DatagramPacket(sendSomma,sendSomma.length, IPAddress, port);
							serverSocket.send(sendPacket);
							}
					}

				} catch (UnknownHostException ue) {
				}
			}
		}catch (java.net.BindException b) {
			System.err.println("Impossibile avviare il server.");
		}
	}
}
