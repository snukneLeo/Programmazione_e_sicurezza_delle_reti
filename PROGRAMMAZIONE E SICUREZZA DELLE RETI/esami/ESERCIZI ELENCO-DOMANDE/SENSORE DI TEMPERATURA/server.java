/*
Implementare un servizio di monitoraggio della temperatura all'interno di un edificio. Ogni sensore di temperatura è dotato di interfaccia WiFi e
si comporta da client nei confronti di un'applicazione che raccoglie i valori su tutto l'edificio per visualizzare dei grafici con l'andamento
temporale. Il dato viene trasmesso ogni 5 secondi. Se il dato non è disponibile, il server applica una semplica interpolazione lineare.
 Si scriva il codice del client e del server che non necessita di gestire più ricezioni contemporaneamente.
 Si discuta la scelta del protocollo di livello trasporto.
NOTA: per sospendere un processo per un certo numero di millisecondi+nanosecondi si usa
static void Thread.sleep(long millis, int nanos) throws InterruptedException
*/
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

class TempServer
{
  public static void main(String args[])
  {
      try
      {
        ServerSocket server = new ServerSocket(1111);
        new ThreadServer(server.accept()).run();
      }
      catch(Exception e)
      {
        System.out.println(e.getMessage());
      }
  }
}

class ThreadServer implements Runnable
{
  Socket s = null;
  int i = 0;
  BufferedReader reader = null;
  BufferedOutputStream outBuffer = null;
  PrintWriter outWriter = null;
  //creo il costruttore della classe
  public ThreadServer(Socket s)
  {
    this.s = s;
  }

  public void run()
  {
    while(i<10)
    {
      try
      {
        //creo il buffer per la ricezione
        reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        //creo il buffer output
        outBuffer = new BufferedOutputStream(s.getOutputStream());
        //creo lo stream di uscita
        outWriter = new PrintWriter(outBuffer,true);

        //aspetta la ricezione del client
        System.out.println("Attesa...");
        System.out.println("Ricezione del dato...");
        String received = reader.readLine();
        if(received == null)
        {
          System.out.println("Dato non ricevuto... analisi della temperatura di default...");
          System.out.println(received = "0.0 °C");
          outWriter.println("RECEVED DATA DEFAULT");
        }
        else
        {
          System.out.println(received);
          System.out.println("Dato ricevuto correttamente...");
          outWriter.println("RECEVED DATA");
        }
      }
      catch(IOException e)
      {
        System.out.println(e.getMessage());
      }
      i++;
    }
    outWriter.println("END");
  }
}
