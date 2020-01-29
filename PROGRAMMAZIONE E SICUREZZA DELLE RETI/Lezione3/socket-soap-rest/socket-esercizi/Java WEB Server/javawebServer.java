import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.net.*;

class WebServer
{
    public static void main (String args[])
    {
      //SERVER SOCKET
      ServerSocket server = null;

      //CLIENT SOCKET
      Socket client = null;

      try
      {
        server = new ServerSocket (80); //porta
      }
      catch(IOException e)
      {
        System.out.println(e.getMessage());
      }

      //STAMPO CHE È IN ATTESA
      System.out.println("Attesa...");

      //ACCETTO LA CONNESSIONE DEL CLIENT VERSO IL SERVER
      try
      {
         client = server.accept();
      }
      catch(Exception e)
      {
          System.out.println(e.getMessage());
      }

      //CREAZIONE DEL BUFFER
      try
      {
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
	BufferedOutputStream outBuffer = new BufferedOutputStream(client.getOutputStream());
	PrintStream outStream = new PrintStream(outBuffer, true);
        //HEADER HTTP NEL BUFFER

        outStream.println("HTTP/1.1 200 OK\nContent-Type: text/html\nServer: My java Web\n");
        
        
       	BufferedReader fileReader  = new BufferedReader(new InputStreamReader(new FileInputStream("prova.html")));

	//LETTURA DEL CHE COSA CHIEDE IL CLIENT LEGGO CHE COSA HA IL CLIENT E LO STAMPO A VIDEO
        String line = null;
        /*while((line = reader.readLine()) != null)
        {
          System.out.println(line);
        }*/
        
        
	String tmp = null;
	while ((tmp = fileReader.readLine()) != null && (line = reader.readLine()) != null) 
	{
	    outStream.println(tmp);
	    System.out.println(line);
	}
	
        outStream.close();
        fileReader.close();
        reader.close();
        client.close();
        server.close();
      }
      catch(IOException e)
      {
        System.out.println(e.getMessage());
      }
    }
  }
