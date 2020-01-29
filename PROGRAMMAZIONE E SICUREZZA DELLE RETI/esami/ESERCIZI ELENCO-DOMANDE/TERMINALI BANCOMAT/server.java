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

class BancomatServer
{
  public static void main(String args[])
  {
    try
    {
      ServerSocket server = new ServerSocket(1111);
      while(true)
      {
        new logServer(server.accept()).run();
        System.out.println("Il bancomat ha accettato la tua richiesta di connessione...");
      }
    }
    catch(IOException e)
    {
      System.out.println("Errore creazione socket. Abort()");
    }
  }
}

class logServer implements Runnable
{
  Socket s = null;
  BufferedOutputStream outBuffer = null;
  BufferedReader reader = null;
  PrintStream outStream = null;
  String log = "";

  public logServer(Socket s)
  {
    this.s = s;
  }

  public void run()
  {
    try
    {
      reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
      outBuffer = new BufferedOutputStream(s.getOutputStream());
      outStream = new PrintStream(outBuffer,true);

      while((log = reader.readLine()) != null)
      {
        System.out.println(log);
        if(log.contains("Errore"))
          outStream.println("Transazione fallita");
        else
          outStream.println("Transazione eseguita");
      }
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
