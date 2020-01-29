/*
Implementare una semplice versione di chiosco per informazioni turistiche. Il processo client nel chiosco si
connette ad un server e propone all’utente un menu (1=HOTEL, 2=RISTORANTI, 3=BAR, 4=PARCHEGGI). La
specifica richiesta di informazioni viene passata al server e il relativo output viene stampato a video dal client.
Il processo client termina dopo la visualizzazione della risposta. Il server invece deve poter ricevere e
soddisfare più richieste di servizio IN SEQUENZA da diversi processi client senza terminare. Si chiede di:
1) scrivere il codice Java lato client e lato server per implementare tale applicazione;
2) motivare la scelta del protocollo di livello trasporto;
3) discutere, senza riportare il relativo codice, le modifiche necessarie al client e al server perché il client
rimanga connesso e possa mandare più richieste in sequenza e il server possa continuare a gestire
richieste da diversi client.
*/
import java.io.*;
import java.net.*;

class server
{
  public static void main(String args[])
  {
    try
    {
      //creo il serversocket
      ServerSocket server = new ServerSocket(2323);
      BufferedReader reader = null;
      BufferedOutputStream outBuffer = null;
      PrintWriter outWriter = null;
      Socket client = null;
      //sequenza di richieste
      while(true)
      {
        System.out.println("Attesa connessione...");
        //creo il socket e accetto la connessione al server
        client = server.accept();
        System.out.println("Connessione effettuata");
        //creo il BufferedReader
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outBuffer = new BufferedOutputStream(client.getOutputStream());
        outWriter = new PrintWriter(outBuffer,true);

        //leggo quello che invia il client
        String scelta = reader.readLine();
        System.out.println("RICEVO: " + scelta);
        //invio le info richieste al client
        String info = readInfo(scelta);
        outWriter.println(info);

      }
    }
    catch(Exception e)
    {
        System.out.println(e.getMessage());
    }
  }

  public static String readInfo(String scelta)
  {
    String info = "";
    if(scelta.charAt(0) == '1')
      info = "In questa zona ci sono ben 15 HOTEL... Dal più rustico al più lussuoso";
    else if(scelta.charAt(0) == '2')
      info = "In questa zona ci sono ben 35 RISTORANTI... Uno di questi ha 3 stelle michlen";
    else if(scelta.charAt(0) == '3')
      info = "In questa zona ci sono ben 20 BAR... Tutti con un proprio marchio caratteristico";
    else
      info = "In questa zona tutti i PARCHEGGI sono gratis";
    return info;
  }
}
