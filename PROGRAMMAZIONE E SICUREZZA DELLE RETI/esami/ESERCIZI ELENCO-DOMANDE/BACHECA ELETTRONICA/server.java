/*
Un server implementa un servizio di bacheca elettronica, dove gli utenti appartenenti ad un gruppo possono
scrivere dei messaggi e tali messaggi vengono visualizzati dagli altri utenti del gruppo. Per motivi tecnologici,
il server non dispone del servizio di PUSH, e i client non possono stare connessi tutto il tempo al server, ma si
connetteranno periodicamente per controllare se ci sono nuovi messaggi da scaricare. Il ritardo nel ricevere i
messaggi non è un parametro di prestazioni importante. Nell fase di invio dei messaggi, invece, l’utente che
ha creato il messaggio deve essere sicuro che il messaggio sia arrivato sul server.
Dati questi requisiti si chiede di:
1. Scrivere il codice in Java lato client e lato server per implementare l’invio dei messaggi sulla bacheca
elettronica, giustificando le scelte fatte.
2. Scrivere il codice in Java lato client e lato server per implementare la ricezione dei messaggi presenti
sulla bacheca elettronica, giustificando le scelte fatte.
*/

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class server
{
  static ArrayList<String> messaggi = new ArrayList<>();
  public static void main(String args[])
  {
    try
    {
      //Socket
      ServerSocket server = new ServerSocket(1111);
      Socket client = null;
      BufferedReader reader = null;
      BufferedOutputStream outBuffer = null;
      PrintWriter outWriter = null;
      while(true)
      {
        System.out.println("Attesa...");
        client = server.accept();
        System.out.println("Connesso...");
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outBuffer = new BufferedOutputStream(client.getOutputStream());
        outWriter = new PrintWriter(outBuffer,true);
        String type = reader.readLine();
        if(type.charAt(0) != '1')
        {
          System.out.println("MODALITA' RICEZIONE MESSAGGIO...");
          String mex = reader.readLine();
          messaggi.add(mex);
          outWriter.println("Messaggio ricevuto correttamente");
        }
        else
        {
          System.out.println("MODALITA' VISUALIZZAZIONE MESSAGGI...");
          viewMex(outWriter);
        }
      }
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
      for(String s : messaggi)
        messaggi.remove(s);
    }
  }

  public static void viewMex(PrintWriter outWriter)
  {
    for(String i : messaggi)
      outWriter.println(i);
  }

  public static ArrayList<String> mex()
  {
    return messaggi;
  }
}
