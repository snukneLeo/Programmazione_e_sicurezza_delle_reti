import java.util.*;
import java.io.*;
import java.net.*;

class UDPServer
{
  public static void main(String args[])
  {
    try
    {
      //socket UDP
      DatagramSocket serverSocket = new DatagramSocket(9876);
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];
      int i = 0;
      InetAddress IPAddress = null;
      int port = 0;
      int ris = 0;
      int op = -1;
      DatagramPacket receivePacket = null;
      String operando = "";
      System.out.println("Attesa connessione...");
      while(op != 0 )
      {
        try
        {
          receivePacket = new DatagramPacket(receiveData,receiveData.length);
          serverSocket.receive(receivePacket);
          operando = new String(receivePacket.getData());
          op = Integer.parseInt(operando.trim());
          System.out.println("RICEVUTO: " + operando);

          ris += op;
        }
        catch(NumberFormatException e)
        {
          System.out.println(e.getMessage());
        }
      }
      //invio il risultato al clientSocket
      IPAddress = receivePacket.getAddress();
      port = receivePacket.getPort();

      sendData = String.valueOf(ris).getBytes();

      DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
      serverSocket.send(sendPacket);
      System.out.println("Pacchetto inviato...");
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
