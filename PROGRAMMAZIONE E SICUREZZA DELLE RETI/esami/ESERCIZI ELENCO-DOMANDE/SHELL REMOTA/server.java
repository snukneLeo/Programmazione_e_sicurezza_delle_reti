/*
Implementare una semplice versione di terminale remoto. Il client si connette ad un server (IP e porta sono
passati in linea di comando ad entrambi) e chiede all’utente di scrivere un comando come in un normale
terminale a caratteri (detto anche shell o console) di Linux. Il comando viene eseguito dal server e il relativo
output viene stampato a video dal client. Il client deve poter ricevere più richieste di servizio senza terminare.
Si chiede di:
1) scrivere il codice Java lato client e lato server per implementare tale applicazione;
2) motivare la scelta del protocollo di livello trasporto;
3) discutere, senza riportare il relativo codice, le modifiche necessarie perché il client, rimanendo connesso,
possa mandare più richieste in sequenza e il server possa gestire più richieste contemporaneamente.
NOTA: per eseguire un comando esterno e catturarne l’output si tragga spunto dalla seguente funzione:

*/
import java.util.*;
import java.net.*;
import java.io.*;

class ShellServer
{
  public static void main(String args[])
  {
    try
    {
      ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
      while(true)
      {
        System.out.println("Attesa...");
        new ControllServer(server.accept()).run(); //NON SERVE IL THREAD
      }
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}

class ControllServer implements Runnable
{
  Socket s = null;
  public ControllServer(Socket s)
  {
    this.s = s;
  }

  public void run()
  {
    System.out.println("Client connesso...");
    try
    {
      BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
      BufferedOutputStream outBuffer = new BufferedOutputStream(s.getOutputStream());
      PrintWriter outWriter = new PrintWriter(outBuffer,true);
      String command = "", outputCommand = "";
      //leggo dal client il comando
      command = reader.readLine();
      outputCommand = executeCommand(command);
      outWriter.println(outputCommand);
      outWriter.println("END");
    }
    catch(IOException e)
    {
      System.out.println("ErroreServer");
    }
  }

  private String executeCommand(String command)
  {
    StringBuffer output = new StringBuffer();
    Process p;
    try
    {
      p = Runtime.getRuntime().exec(command);
      p.waitFor();
      BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line = "";
      while ((line = reader.readLine())!= null)
      {
        output.append(line + "\n");
      }
    }
    catch (Exception e)
    {
      System.out.println("Errore!!!!");
      e.printStackTrace();
    }
    return output.toString();
  }
}
