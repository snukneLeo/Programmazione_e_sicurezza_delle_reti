//CLIENT

import java.io.*;
import java.net.*;
import java.util.Scanner;

class EchoClient {
	public static void main(String args[]) {

		// socket
		Socket clientSocket = null;
		/*Scanner port = new Scanner(System.in);
		System.out.println("Si prega di inserire la porta per la connessione: ");
		int porta = port.nextInt();
		Scanner ip = new Scanner(System.in);
		System.out.println("Inserisci l'ip del server per connettersi: (se stessa macchina scrivere --> localhost)");
		String ipServer = ip.nextLine();*/

		try {
			clientSocket = new Socket(args[0], Integer.parseInt(args[1])); // IP e porta del server //10.96.108.175
			System.out.println("Socket creata: " + clientSocket);

			// creazione del BufferedOutputStream e PrintWriter per
			// inviare la stringa al server
			BufferedOutputStream outBuffer = new BufferedOutputStream(clientSocket.getOutputStream());
			PrintWriter outWriter = new PrintWriter(outBuffer, true);

			// creazione del BufferedReader per leggere la risposta del server
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			// Invio messsaggio
			//outWriter.println("echo up");
			Scanner k = new Scanner(System.in);
			String s;

			do
			{
				s = k.nextLine();
				outWriter.println(s);
				if (s.equals("Stop"))
					break;
			}while(s != "Stop");

			// ricezione risposta dal server
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println("Ricevuto: "+ line);
				if (line.equals("Stop");
					break;
			}
			// chiudo il Socket (client)
			clientSocket.close();
			// chiudo PrintWriter
			outWriter.close();
			// chiudo BufferedReader
			reader.close();
		} catch (IOException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}
}
