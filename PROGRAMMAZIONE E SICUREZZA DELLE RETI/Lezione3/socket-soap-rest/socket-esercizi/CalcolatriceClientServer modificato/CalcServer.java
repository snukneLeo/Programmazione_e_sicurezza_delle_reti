// SERVER

import java.io.*;
import java.net.*;
import java.util.Scanner;

class CalcServer {
	public static void main(String args[]) {

		// sockets
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		try {
			System.err.println("Creazione ServerSocket");
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

			System.out.println("Attesa connessione...");

			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Connessione fallita");
				System.exit(2);
			}

			System.out.println("Connessione da " + serverSocket);

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedOutputStream outBuffer = new BufferedOutputStream(clientSocket.getOutputStream());
				PrintStream outStream = new PrintStream(outBuffer, true);

				String line,primo;
				int limite = 0, x = 0,NumeroPrime = 0,d = 0;
				

				line = new String(reader.readLine());
				primo = new String(reader.readLine());
				limite = Integer.parseInt(line);
				NumeroPrime = Integer.parseInt(primo);
				for(int i = 2; i<limite; i++)
				{
				    if(NumeroPrime%i!=0)
				    {
				    	System.out.println(i);
				    }
				}
				outStream.println("Il limite " + limite + " è stato raggiunto");
				
				// chiusure
				outStream.close();
				reader.close();
				clientSocket.close();
				serverSocket.close();
			} catch (Exception e) {
				System.out.println("Errore: " + e);
				System.exit(3);
			}
	}
}
