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
import java.util.Scanner;
class client
{
  public static void main (String args[])
  {
    try
    {
      //creo il socket client
      Socket client = new Socket("localhost",2323);
      //creo il buffer
      BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      BufferedOutputStream outBuffer = new BufferedOutputStream(client.getOutputStream());
      PrintWriter outWriter = new PrintWriter(outBuffer,true);

      //stampo il menu
      viewMenu();

      //l'utente inserisci la scelta
      Scanner sc = new Scanner(System.in);
      int scelta = sc.nextInt();

      //invio al server la scelta effettuata
      outWriter.println(scelta);
      //aseptto e ricevo la risposta del server
      String viewinfo = reader.readLine();
      System.out.println(viewinfo);
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public static void viewMenu()
  {
    System.out.println("MENU----------------------------------------------------");
    System.out.println("1-HOTEL");
    System.out.println("2-RISTORANTI");
    System.out.println("3-BAR");
    System.out.println("4-PARCHEGGI");
    System.out.println("--------------------------------------------------------");
  }
}
