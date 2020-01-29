// CLIENT

import java.io.*;
import java.net.*;
import java.util.Scanner;

class CalcClient {
	public static void main(String args[]) {

		// socket
		Socket clientSocket = null;

		// streams
		BufferedReader reader = null;
		PrintStream outStream = null;
		String LimitePrime = args[2];
		int nPrimo = Integer.parseInt(args[3]);
		

		try {
			clientSocket = new Socket(args[0], Integer.parseInt(args[1])); // IP e porta del server
			System.out.println("Socket creata: " + clientSocket);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		try {
		reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outStream = new PrintStream(new BufferedOutputStream(clientSocket.getOutputStream()),true);
		} catch(IOException e){
			e.printStackTrace();
		}

		// invio valori al server (da linea comandi)
		for (int i = 2; i < args.length; i++) {
			System.out.println("Sending " + args[i]);
			outStream.println(args[i]);
		}

		outStream.println(LimitePrime);
		outStream.println(nPrimo);
		System.out.println("Attesa risposta...");
		String line = null;

		try {
			line = new String(reader.readLine());
			System.out.println("Msg dal server: " + line);

			// chiusure
			reader.close();
			outStream.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
