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
import java.util.*;
class client
{
  public static void main(String args[])
  {
    try
    {
      DatagramSocket client = new DatagramSocket();
      InetAddress ip = InetAddress.getByName("localhost");
      //variabili che mi servono per inviare e riceve i dati
      String haveCard = null;
      String id = null;
      String code = null;
      String costTotale = null;
      String sumObject = null;
      char endSpesa = ' ';
      String codeRandom = null;
      int prezzo = 0;

      //lettura con il buffer
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      byte[] sendData = new byte[1024];
      byte[] receveData = new byte[1024];

      System.out.print("Ha la tessera dei punti? (y/n): ");
      haveCard = reader.readLine();

      //invio la prima informazione... se ha la tessera o no...
      methodSend(sendData,ip,haveCard,client);

      //controllo se il cliente ha la tessera o no
      if(haveCard.charAt(0) == 'y') //ha la tessera
      {
        System.out.print("Prego inserire l'ID della tessera: ");
        id = reader.readLine();
        //invio l'ID
        methodSend(sendData,ip,id,client);
      }
      //proseguo con il passare i prodotti...
      Random rand = new Random();
      while(endSpesa != 'f')
      {
        System.out.println("Passa i prodotti...");
        //genero casualmente un code per il prodotto
        codeRandom = " ";
        for(int i = 0; i<12; i++)
        {
            codeRandom += rand.nextInt(10); //creo il codice di 12 cifre
        }
        System.out.println("Codice: " + codeRandom);
        prezzo = rand.nextInt(100) + 1;
        try
        {
          Thread.sleep(2000,0); //attendo 2 secondi per calcolare il prezzo del prodotto
        }
        catch(Exception e)
        {
          System.out.println("Errore Calcolo prezzo...Spesa terminata!");
        }
        System.out.println("Prezzo: " + prezzo);
        //invio il codeRandom
        methodSend(sendData,ip,codeRandom,client);
        //invio il prezzo
        methodSend(sendData,ip,String.valueOf(prezzo),client);
        System.out.println("Digita f per finire la spesa oppure n per continuare...");
        endSpesa = reader.readLine().charAt(0);
      }
      //invio al server se ho finito la spesa
      methodSend(sendData,ip,String.valueOf(endSpesa),client);

      System.out.println("Attendere... Richiesta totale della spesa in corso...");
    /*  try
      {
        Thread.sleep(1000,0); //attendo 4 secondi per calcolare la somma totale
      }
      catch(Exception e)
      {
        System.out.println("Errore Calcolo prezzo...Spesa terminata!");
      }*/
      //ricevo la somma dal server
      DatagramPacket totaleSpesa = new DatagramPacket(receveData,receveData.length);
      client.receive(totaleSpesa);
      costTotale = new String(totaleSpesa.getData());
      System.out.print("La spesa totale è: " + costTotale);

      System.out.println();
      System.out.println("Arrivederci!");

    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public static void methodSend(byte[] sendData,InetAddress ip,String value,DatagramSocket client)
  {
    try
    {
      sendData = new byte[1024];
      sendData = value.getBytes(); //inserisco il dato da inviare al server
      DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,9876);
      //invio le cose al server
      client.send(sendPacket);
    }
    catch(IOException e)
    {
      System.out.println("Send data in error!");
      System.out.println(e.getMessage());
    }
  }
}
