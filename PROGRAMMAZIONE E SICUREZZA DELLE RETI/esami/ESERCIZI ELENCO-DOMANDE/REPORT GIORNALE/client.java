/*
Implementare un’applicazione avanzata di invio di informazioni giornalistiche.
Ciascun reporter attraverso il
proprio client invia brevi messaggi testuali ad un server che li pubblicherà su
 di un giornale.
giornale Il server risponde
a ciascun messaggio ricevuto inviando la stringa “OK” al client corrispondente.
Il reporter, nell’inviare un
messaggio gli assegna una priorità (alta, media, bassa);
per evitare di inserire la priorità nel testo del
messaggio,
ggio, occorre spedire il messaggio su una porta del server corrispondente alla
sua priorità (ad es.
4000=alta, 3000=media, 2000=bassa
=bassa). Emulare la pubblicazione dei messaggi mediante la loro semplice
stampa a video da parte del server stesso. Si chiede di:
1) scrivere il codice Java lato client e lato server per implementare tale
applicazione
ne;
2) discutere la scelta del protocollo di livello trasporto.
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;

class ReportClient
{
  public static void main(String args[])
  {
    try
    {
      //DA USARE CON L'UDP E NON TCP
      Socket client = new Socket("localhost",1111);
      Socket priority = null;

      System.out.println("ok");
      BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      BufferedOutputStream outBuffer = new BufferedOutputStream(client.getOutputStream());
      PrintWriter outWriter = new PrintWriter(outBuffer,true);

      BufferedOutputStream outBufferPriority = null;
      PrintWriter outWriterPriority = null;

      System.out.println("Inserisci il report: ");
      //Scrittura del report
      Scanner sc = new Scanner(System.in);
      String writeReport = sc.nextLine();
      //invio del report al server
      outWriter.println(writeReport);
      System.out.println("Report inviato correttamente...");

      System.out.print("Inserisci la priorità del report (A=alta,B=bassa,M=media): ");
      sc = new Scanner(System.in);
      String py = sc.next();

      if(py.charAt(0) == 'A')
      {
        priority = new Socket("localhost",4000);
        outBufferPriority = new BufferedOutputStream(priority.getOutputStream());
        outWriterPriority = new PrintWriter(outBufferPriority, true);
        outWriterPriority.println("A");
      }
      if(py.charAt(0) == 'M')
      {
        priority = new Socket("localhost",3000);
        outBufferPriority = new BufferedOutputStream(priority.getOutputStream());
        outWriterPriority = new PrintWriter(outBufferPriority, true);
        outWriterPriority.println("M");
      }
      if(py.charAt(0) == 'B')
      {
        priority = new Socket("localhost",2000);
        outBufferPriority = new BufferedOutputStream(priority.getOutputStream());
        outWriterPriority = new PrintWriter(outBufferPriority, true);
        outWriterPriority.println("B");
      }

      //ricezione della risposta dal server
      String reportResponse = reader.readLine();
      if(reportResponse != null)
        System.out.println(reportResponse);
      else
        System.out.println("Errore... Server not found report");
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
      System.out.println("asdijsdpasdmsdaosdjiasoudbjnsdIDN813782370392183213");
    }
  }
}
