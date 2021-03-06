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

class serverMex
{
  static server s;
  public static void main(String args[])
  {
    try
    {
      //Socket
      ServerSocket server = new ServerSocket(1212);
      Socket client = null;
      BufferedReader reader = null;
      BufferedOutputStream outBuffer = null;
      PrintWriter outWriter = null;
      while(true)
      {
        System.out.println("Attesa...");
        client = server.accept();
        System.out.println("Connesso...");

        for(String i : s.mex())
          outWriter.println(i);
      }
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}
