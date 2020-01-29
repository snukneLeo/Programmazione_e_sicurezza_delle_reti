/*
Implementare un’applicazione avanzata di invio di informazioni giornalistiche. Ciascun reporter attraverso il
proprio client invia brevi messaggi testuali ad un server che li pubblicherà su di un giornale.
giornale Il server risponde
a ciascun messaggio ricevuto inviando la stringa “OK” al client corrispondente. Il reporter, nell’inviare un
messaggio gli assegna una priorità (alta, media, bassa);
bassa) ; per evitare di inserire la priorità nel testo del
messaggio,
ggio, occorre spedire il messaggio su una porta del server corrispondente alla sua priorità (ad es.
4000=alta, 3000=media, 2000=bassa
=bassa). Emulare la pubblicazione dei messaggi mediante la loro semplice
stampa a video da parte del server stesso. Si chiede di:
1) scrivere il codice Java lato client e lato server per implementare tale applicazione
ne;
2) discutere la scelta del protocollo di livello trasporto.
*/
import java.io.*;
import java.net.*;
class ReportServer
{
  public static void main(String args[])
  {
    try
    {
      //socket del server
      ServerSocket server = new ServerSocket(1111);
      System.out.println("Attesa...");

      try
      {
        while(true)
        {
          new Report(server.accept()).run();
          System.out.println("Connessione effettuata");
        }
      }
      catch(Exception e)
      {
        System.out.println(e.getMessage());
      }
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}

class Report implements Runnable
{
  Socket s = null;


  public Report(Socket s)
  {
    this.s = s;
  }

  public void run()
  {
    try
    {
      //creo il buffer per la Lettura
      BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
      BufferedOutputStream outBuffer = new BufferedOutputStream(s.getOutputStream());
      PrintWriter outWriter = new PrintWriter(outBuffer,true);

      String report = reader.readLine();
      System.out.println("Il report è: ");
      System.out.println(report);

      //invio la riposta al client
      if(report != null)
        outWriter.println("OK");
      else
        outWriter.println("Errore");

      try
      {
        ServerSocket serverPriority1 = new ServerSocket(4000);
        ServerSocket serverPriority2 = new ServerSocket(3000);
        ServerSocket serverPriority3 = new ServerSocket(2000);
        Socket pAlta  = serverPriority1.accept();
        Socket pMedia = serverPriority2.accept();
        Socket pBassa = serverPriority3.accept();
        System.out.println(pAlta + " -- " + pMedia + " -- " + pBassa);
        if(pAlta != null)
          new Priority(serverPriority1.accept()).run();
        else if(pMedia != null)
          new Priority(serverPriority2.accept()).run();
        else if(pBassa != null)
          new Priority(serverPriority3.accept()).run();
        else
          System.out.println("Errore")
      }
      catch(IOException e)
      {
        System.out.println(e.getMessage());
      }

    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}

class Priority implements Runnable
{
  Socket s = null;
  public Priority(Socket s)
  {
    if(s != null)
      this.s = s;
  }

  public void run()
  {
    try
    {
      //creo il buffer per la Lettura
      BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
      //BufferedOutputStream outBuffer = new BufferedOutputStream(s.getOutputStream());
      //PrintWriter outWriter = new PrintWriter(outBuffer,true);

      System.out.println(reader.readLine());
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}
