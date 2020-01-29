/*
Implementare un servizio di monitoraggio della temperatura all'interno di un edificio. Ogni sensore di temperatura è dotato di interfaccia WiFi e
si comporta da client nei confronti di un'applicazione che raccoglie i valori su tutto l'edificio per visualizzare dei grafici con l'andamento
temporale. Il dato viene trasmesso ogni 5 secondi. Se il dato non è disponibile, il server applica una semplica interpolazione lineare.
 Si scriva il codice del client e del server che non necessita di gestire più ricezioni contemporaneamente.
 Si discuta la scelta del protocollo di livello trasporto.
NOTA: per sospendere un processo per un certo numero di millisecondi+nanosecondi si usa
static void Thread.sleep(long millis, int nanos) throws InterruptedException
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.lang.Math;

class TempClient
{
  public static void main(String args[])
  {
    try
    {
      Socket client = new Socket("localhost",1111);
      BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      BufferedOutputStream outBuffer = new BufferedOutputStream(client.getOutputStream());
      PrintStream outWriter = new PrintStream(outBuffer,true);

      while(true)
      {
        try
        {
          System.out.println("Aspetto...");
          Thread.sleep(5000,0);
        }
        catch(Exception e)
        {
          System.out.println(e.getMessage());
        }
        //invio interfaccia
        System.out.println("---------------------------------");
        System.out.println("INTERFACE: WIFI 802.11");
        System.out.println("INDIRIZZO IP: 127.0.0.1");
        System.out.println("PORTA: 1111");
        System.out.println("INVIO DATO IN CORSO...");
        //Scanner sc = new Scanner(System.in);
        //String temp = sc.nextLine();
        double minTemp = -1.0; // numero minimo
        double maxTemp = 35.0; // numero massimo
        double c = ((maxTemp + minTemp) + 1);
        int temp = (int)(Math.random()*c);
        //Richiedo all'utente il valore
        outWriter.println(temp + "° C");
        System.out.println("INVIO EFFETTUATO");
        System.out.println("---------------------------------");

        //rispostadal server
        String received = reader.readLine();
        System.out.println(received);
        if(received.equals("END"))
        {
          //chiusure
          reader.close();
          outBuffer.close();
          outWriter.close();
          client.close();
          break;
        }
      }
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
