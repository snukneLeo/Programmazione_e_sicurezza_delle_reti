/*
 * Implementare un servizio di telepass: quando l'auto si avvicina al casello spedisce al server
il proprio numero di targa e il server tiene traccia in un
vettore del numero di passaggi per ogni targa. Il server deve gestire più richieste
contemporaneamente. Si discuta la scelta del protocollo di livello
trasporto.
 * */
 import java.io.*;
 import java.net.*;
 import java.util.ArrayList;

 class server
 {
   public static void main(String args[])
   {
     try
     {
       //creazione socket server
       ServerSocket server = new ServerSocket(2323);
      // System.out.println("ServerSocket creato");
       //accetto più client creando per ogni client una thread diversa
      System.out.println("Sono in attesa di macchine...");
      while(true)
      {
       new Receved(server.accept()).run();
      }
     }
     catch(IOException e)
     {
       System.out.println(e.getMessage());
     }
   }
 }

 class Receved implements Runnable
 {
   Socket s = null;
   BufferedReader reader = null;
   BufferedOutputStream outBuffer = null;
   PrintWriter outStream = null;
   String receved = null;
   //creo il mio vettore di targhe
   static String [] listTarga = new String[10];
   //creo il mio vettore di contatori
   static int [] counterTarga = new int[10];
   static int count = 1, k = 0;
   public Receved(Socket s)
   {
     this.s = s;
   }

   public void run()
   {
      try
      {
      //  System.out.println("Sto elaborando...");
        //definisco tutte le cose che mi servono
        reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        outBuffer = new BufferedOutputStream(s.getOutputStream());
        outStream = new PrintWriter(outBuffer,true);
        //leggo il messaggio dal server
        //System.out.println("Aspetto...");
        receved = reader.readLine();
        for(int i = 0; i<listTarga.length;i++)
        {
          String s = listTarga[i];
          if(receved.equals(s))
            outStream.println(++counterTarga[i]);
          else
            outStream.println("Prima volta");
        }
        System.out.println("Ho fatto tutto...");

        //chiusure
        reader.close();
        outStream.close();
        outBuffer.close();
      }
      catch(IOException e)
      {
        System.out.println(e.getMessage());
      }
   }
 }
