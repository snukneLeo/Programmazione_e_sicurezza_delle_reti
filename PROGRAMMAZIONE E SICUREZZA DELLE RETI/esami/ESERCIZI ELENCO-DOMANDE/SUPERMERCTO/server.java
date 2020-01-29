/*
Le varie casse di un supermercato trasmettono ad un server centralizzato le transazioni degli acquirenti. I
messaggi che ciascuna cassa, in qualità di client, manda al server sono: invio dell’ID della tessera punti
(opzionale), invio del codice a barre di ciascun prodotto non appena esso viene passato sul lettore della cassa,
richiesta del totale speso. Si noti il fatto che per ciascun acquirente, un numero variabile di messaggi viene
trasmesso e le operazioni di cassa richiedono un tempo significativo perché manuali. Si chiede di:
1) definire il protocollo applicativo e, di conseguenza, discutere la scelta del protocollo di livello trasporto;
2) scrivere la porzione di codice Java lato client e lato server per implementare tale trasmissione.
*/
import java.io.*;
import java.net.*;

class server
{
  public static void main(String args[])
  {
    try
    {
      DatagramSocket server = new DatagramSocket(9876);
      byte[] receveData = new byte[1024];
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      String id = null;
      char endSpesa = ' ';
      String cost = null;
      int totaleSpesa = 0;
      String codeProduct = null;
      int  endAll = 0;
      String haveCard = null;
      InetAddress ip = null;
      //ricevo i dati dal client
      //String  = methodReceve(receveData, server,endAll,totaleSpesa);


      DatagramPacket ricevo1 = new DatagramPacket(receveData,receveData.length);
      server.receive(ricevo1);
      haveCard = new String(ricevo1.getData());
      ip = ricevo1.getAddress();

      receveData = new byte[1024];
      System.out.println(haveCard);
      if(haveCard.charAt(0) != 'n') //richiedo l'id
      {
        DatagramPacket ricevo2 = new DatagramPacket(receveData,receveData.length);
        server.receive(ricevo2);
        id = new String(ricevo2.getData());

        System.out.println("L'utente ha la tessera dei punti...");
        System.out.println("ID: " + id);
      }
      else
        System.out.println("L'utente non ha la tessera dei punti...");
      while(endSpesa != 'f')
      {
        receveData = new byte[1024];
        DatagramPacket ricevo3 = new DatagramPacket(receveData,receveData.length);
        server.receive(ricevo3);
        codeProduct = new String(ricevo3.getData());

        if(codeProduct.charAt(0) == 'f')
        {
          endSpesa = 'f';
          break;
        }
        System.out.println("CodeP: " + codeProduct);

        receveData = new byte[1024];
        DatagramPacket ricevo4 = new DatagramPacket(receveData,receveData.length);
        server.receive(ricevo4);
        cost = new String(ricevo4.getData());

        System.out.println("Costo: " + cost + "€ ");

        totaleSpesa += Integer.parseInt(cost.trim());
        System.out.println("Totale Spesa: " + totaleSpesa + "€ ");
      }

      System.out.println("Fine spesa...");
      System.out.println("Invio spesa totale...");

      byte[] sendData = new byte[1024];
      //invio la somma totale
      sendData = String.valueOf(totaleSpesa + "€").getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,9876);
      server.send(sendPacket);
      System.out.println("Totale: " + totaleSpesa);
      System.out.println("Fine");
      try
      {
        Thread.sleep(4000,0); //attendo 4 secondi per calcolare la somma totale
      }
      catch(Exception e)
      {
        System.out.println("Errore Calcolo prezzo...Spesa terminata!");
      }
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
