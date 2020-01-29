/*
Implementare un servizio di calcolo remoto: il client spedisce al server due numeri interi e il tipo di operatore (somma, sottrazione, moltiplicazione,
 divisione) e il server, dopo aver svolto il calcolo, restituisce al client il risultato. Il server gestisce una richiesta alla volta.
- si discuta la scelta del protocollo di livello trasporto
- che modifiche occorre fare al server per fargli gestire più richieste contemporaneamente?
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;


class CalcClient
{
	public static void main(String args[])
  {
    //definisco socket, buffered, stream
    Socket socket = null;
    BufferedReader reader = null;
    PrintWriter outStream = null;
		BufferedOutputStream outBuffer = null;

    String [] calcolo = new String[3];
    String n;
		int j = 0;
		Scanner sc = new Scanner(System.in);

    //creo la socket clientSocket
    try
    {
      socket = new Socket("localhost",1111);
      System.out.println("Client connesso: " + socket);
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }

    //creo il buffer e lo stream
    try
    {
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outBuffer = new BufferedOutputStream(socket.getOutputStream());
			outStream = new PrintWriter(outBuffer,true);

      while(j<1)
      {
        System.out.println("Inserisci un numero: ");
        n = sc.nextLine();
        calcolo[0] = n;
				outStream.println(n);
        System.out.println("Inserisci un altro numero: ");
        n = sc.nextLine();
        calcolo[1] = n;
				outStream.println(n);
        System.out.println("Inserisci un'operatore ('+');('-');('/');('*'): ");
        n = sc.nextLine();
        calcolo[2] = n;
				outStream.println(n);
				j++;
      }
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

			System.out.println("Attesa risposta...");
      //Lettura dal server della risposta
      String line = null;
      try
      {
				while ((line = reader.readLine()) != null)
				{
					System.out.println("Risposta dal server: "+ line);
				}

	      //chiusure
	      reader.close();
	      outStream.close();
	      socket.close();
    	}
	    catch(Exception e)
	    {
	      System.out.println(e.getMessage());
	    }
		}
	}
