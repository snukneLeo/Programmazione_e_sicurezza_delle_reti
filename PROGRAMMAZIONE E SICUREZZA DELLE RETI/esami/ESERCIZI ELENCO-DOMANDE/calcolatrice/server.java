/*
Implementare un servizio di calcolo remoto: il client spedisce al server due numeri interi e il tipo di operatore (somma, sottrazione, moltiplicazione,
 divisione) e il server, dopo aver svolto il calcolo, restituisce al client il risultato. Il server gestisce una richiesta alla volta.
- si discuta la scelta del protocollo di livello trasporto
- che modifiche occorre fare al server per fargli gestire più richieste contemporaneamente?
*/

import java.io.*;
import java.net.*;

class Server
{
  public static void main(String args[])
  {
    //creo il socket
    Socket client = null;
    ServerSocket server = null;
    String [] arr = new String [3];
    String line = null;
    int i = 0, n = 0;

    try
    {
      server = new ServerSocket(1111);
      System.out.println("Server creato");
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }

    System.out.println("Attesa connessione...");
    try
    {
    	client = server.accept();
      System.out.println("Connesione stabilita");
    }
    catch(IOException e)
    {
    	System.out.println(e.getMessage());
    }

    //corpo principale
    try
    {
      BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      BufferedOutputStream outBuffer = new BufferedOutputStream(client.getOutputStream());
      PrintWriter outStream = new PrintWriter(outBuffer,true);
      do
      {
        System.out.println("Lettura del mex che mi sta inviando");
        line = new String(reader.readLine());
        arr[i] = line;
        System.out.println(line);
        i++;
      }
      while (i < arr.length);

      switch (arr[2].charAt(0))
      {
        case '+':
          n = Integer.parseInt((arr[0])) + Integer.parseInt((arr[1]));
        break;
        case '-':
            n = Integer.parseInt((arr[0])) - Integer.parseInt((arr[1]));
        break;
        case '*':
            n = Integer.parseInt((arr[0])) * Integer.parseInt((arr[1]));
        break;
        case '/':
            n = Integer.parseInt((arr[0])) / Integer.parseInt((arr[1]));
        break;
        default:
          System.out.println("Errore");
        break;
      }
      System.out.println("Calcolo ed invio al client la risposta...");
      System.out.println(arr[0] + arr[2].charAt(0) + arr[1] + "= " + n);
      outStream.println(arr[0] + arr[2].charAt(0) + arr[1] + "= " + n);
      //chiusure
      reader.close();
      outBuffer.close();
      outStream.close();
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
