import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {
	public static void main(String args[]) {

		// socket
		Socket clientSocket = null;

		// streams
		BufferedReader reader = null;
		PrintStream outStream = null;

		if (args.length == 0) {
			System.out.println("Missing file name");
			System.exit(-1);
		}

		try {
			System.out.println("Inserisci l'indirizzo ip del server: (localhost se stessa macchina: )");
			Scanner ip = new Scanner(System.in);
			String ipServer = ip.nextLine();
			System.out.println("Inserisci la porta di connessione: ");
			Scanner p = new Scanner(System.in);
			int porta = p.nextInt();
			clientSocket = new Socket(ipServer, porta); // IP e porta del server
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		System.out.println("Socket creata: " + clientSocket);

		try
		{
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outStream = new PrintStream(new BufferedOutputStream(clientSocket.getOutputStream()), true);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		// --- invio messaggio
		System.out.println("Invio richiesta per: " + args[0]);
		//nome file nuovo
		String nomeFile = "";
		try
		{
			if(args[1] != "") //controllo che sia diverso da vuoto quello che mi passa l'utente
				nomeFile = args[1]; //se non è vuoto allora il nomeFile nuovo verrà assegnato correttamente
		}
		catch(Exception e) //se è vuoto entro in errore e il nomeFile sarà uguale a quello iniziale
		{
			nomeFile = args[0];
		}
		System.out.println("Nome file nuovo: " + nomeFile); //scrivo a video
		outStream.println(args[0]); //scrivo il nomeFile vecchio

		// --- stampa risposta del server
		System.out.println("Attesa risposta...");
		String line = null;
		//######################################################################
		//scrittura su file
		FileWriter w = null;
		try
		{
   			w =  new FileWriter(nomeFile); //scrivo su file con il nomeFile che ho assegnato precedentemente
   		}
   		catch(Exception e)
   		{
   			System.out.println("Errore scrittura...");
   		}

		try
		{
	  		while ((line = reader.readLine()) != null)
	  		{
				//System.out.println("Messaggio: " + line);
				w.write(line); //scrivo sul file ogni riga
			}
		//#########################################################################à

			// chiusure
			clientSocket.close();
			outStream.close();
			reader.close();
			w.flush(); //chiudo la scrittura
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
