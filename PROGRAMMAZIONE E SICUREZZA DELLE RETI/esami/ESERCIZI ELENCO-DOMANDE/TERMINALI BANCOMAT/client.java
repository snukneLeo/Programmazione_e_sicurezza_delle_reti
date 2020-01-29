/*
Implementare un servizio di monitoraggio dei terminali Bancomat di un certo istituto bancario. Ogni terminale bancomat si comporta da client
nei confronti di un'applicazione che raccoglie dei report testuali (log) sull'attività del bancomat. Il dato viene trasmesso ogni 5 minuti.
Si scriva il codice del client e del server che necessita di gestire più ricezioni contemporaneamente.
Si discuta la scelta del protocollo di livello trasporto.
NOTA: per sospendere un processo per un certo numero di millisecondi+nanosecondi si usa
static void Thread.sleep(long millis, int nanos) throws InterruptedException
*/
import java.io.*;
import java.net.*;

class BancomatClient
{
  public static void main(String args[])
  {
    try
    {
      Socket client = new Socket("localhost",1111);
      BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      BufferedOutputStream outBuffer = new BufferedOutputStream(client.getOutputStream());
      PrintStream outStream = new PrintStream(outBuffer,true);

      //array per pin
      String pinString = "";
      int numberID = 0;
      int money = 0;
      int c = 0;

      System.out.println("Accesso al bancomat...");
      System.out.println("User" + numberID);
      System.out.println("Inserimento pin...");
      System.out.println("°°°°°°");
      c = (10-1) + 1;
      for(int i = 0; i<5; i++)
        pinString += (int)Math.random()*c;
      System.out.println("PIN CORRETTO...");
      System.out.print("Indicare la cifra da prelevare... ");
      money = (int)(Math.random()*3000);
      System.out.println(money);
      try
      {
        Thread.sleep(4000,0); //4 scondi e 0 nano
      }
      catch(Exception e)
      {
        System.out.println(e.getMessage());
      }
      if(money >= 2700)
        outStream.println("Accesso da: User" + numberID + " Il pin è: " + pinString + " Ha tentato di prelevare: " + money + "€" +" Errore (0xb81)");
      else
        outStream.println("Accesso da: User" + numberID + " Il pin è: " + pinString + " Ha prelevato: " + money + "€" );
      numberID++;

      //ricezione server
      String receved = reader.readLine();
      System.out.println(receved);
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}
