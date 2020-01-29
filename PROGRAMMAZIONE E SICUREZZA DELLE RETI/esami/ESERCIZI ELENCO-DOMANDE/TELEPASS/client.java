/*
 * Implementare un servizio di telepass: quando l'auto si avvicina al casello spedisce al server
il proprio numero di targa e il server tiene traccia in un
vettore del numero di passaggi per ogni targa. Il server deve gestire più richieste
contemporaneamente. Si discuta la scelta del protocollo di livello
trasporto.
 * */
 import java.util.Scanner;
 import java.io.*;
 import java.net.*;
 import java.util.*;
 import java.lang.*;

 public class client
 {
   public static void main(String args[])
   {
      //definisco il socket client
      Socket socketClient = null;
      //definisco il buffer di lettura delle cose dal server
      BufferedReader reader = null;
      //definisco lo stream di uscita per mandare le cose al server
      PrintStream outStream = null;
      //definisco il buffer di output da client verso server
      BufferedOutputStream outBuffer = null;
      //definisco lo scanner per la lettura da tastiera in input dall'utente
      Scanner sc = new Scanner(System.in);
      //defiisco la variabile targa
      String targa = null;
      //definisco la variabile che otterrò al server
      String response = null;

      //creo le varie parti
      try
      {
        //definisco il socketClient
        socketClient = new Socket("localhost",2323);
        //definisco i vari buffer e outStream
        reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        outBuffer = new BufferedOutputStream(socketClient.getOutputStream());
        outStream = new PrintStream(outBuffer,true);
        //chiedo all'utente la targa della propria vettura
        System.out.print("Inserisci la targa: ");
        targa = sc.nextLine();
        System.out.println();

        //invio al server la mia targa
        outStream.println(targa);

        //aspetto che il server mi risponda
        System.out.println("Attendo che il server mi possa far passare...");

        //attendo la risposta
        while((response = reader.readLine()) != null)
          System.out.println(response);
        //dopo aver ricevuto la risposta esco e vado in autostrada
        System.out.println("La sbarra si è alzata e posso passare...");

        //chiudo i vari buffer e Socket
        socketClient.close();
        outStream.close();
        reader.close();
        outBuffer.close();
        return; //esco
      }
      catch(IOException e)
      {
        System.out.println(e.getMessage());
      }
   }
 }
