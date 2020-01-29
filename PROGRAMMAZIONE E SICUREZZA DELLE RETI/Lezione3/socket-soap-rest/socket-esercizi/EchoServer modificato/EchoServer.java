// SERVER

import java.io.*;
import java.net.*;
import java.util.Scanner;

class EchoServer {
	public static void main(String args[]) {

		// sockets
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		System.out.println("Creazione ServerSocket...");
		/*System.out.println("Inserire la porta da mettersi in ascolto: (1025-...)");
		Scanner s = new Scanner(System.in);
		int porta = s.nextInt();*/


		try {
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));

			System.out.print("Attesa connessione...");
			clientSocket = serverSocket.accept();
			System.out.println("Conness. da " + clientSocket);

			// creazione BufferReader per leggere stringa in arrivo
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			// creazione del BufferedOutputStream per inviare la stringa maiuscola
			BufferedOutputStream outBuffer = new BufferedOutputStream(clientSocket.getOutputStream());
			PrintWriter outWriter = new PrintWriter(outBuffer, true);

			String text;
			// ricevzione della stringa
			do
			{
				text = new String(reader.readLine());
				System.out.println(text.toUpperCase());
				if (text.equals("Stop"))
					break;
			} while (text != "Stop");



			// invio della nuova stringa in maiuscolo
			outWriter.println(text.toUpperCase());
			//outWriter.println("Stop");

			// chiudo BufferedReaser
			reader.close();
			// chiudo PrintWriter
			outWriter.close();
			// chiudo il Socket (client)
			clientSocket.close();
			// chiudo ServerSocket
			serverSocket.close();
		} catch (Exception e) {
			System.out.println("Errore: " + e);
			System.exit(3);
		}
	}
}
