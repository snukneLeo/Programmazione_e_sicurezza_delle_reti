import java.io.*;
import java.net.*;

class UDPserver
{
  public static void main(String args[])
  {
    try
    {
      DatagramSocket server = new DatagramSocket(9876);
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];
      InetAddress IPAddress = null;
      int port = 0, i = 0;

      System.out.println("Attesa connessione...");
      while(i < 10)
      {
          try
          {
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            try
            {
              Thread.sleep(1000,0);
            }
            catch(Exception e)
            {
              System.out.println(e.getMessage());
            }
            server.receive(receivePacket);
            String ricevoTemp = new String(receivePacket.getData());
            System.out.println(ricevoTemp.trim());
            if(ricevoTemp == null)
            {
              System.out.println("Dato non ricevuto... analisi della temperatura di default...");
              System.out.println("0.0 °C");
              sendData = ("RECEVED DATA DEFAULT").getBytes();
              IPAddress = receivePacket.getAddress(); //ip
              port = receivePacket.getPort(); //porta
              DatagramPacket sendPacket = new DatagramPacket (sendData,sendData.length,IPAddress,port);
              server.send(sendPacket);
            }
            else
            {
              //System.out.println(received);
              IPAddress = receivePacket.getAddress(); //ip
              port = receivePacket.getPort(); //porta
              sendData = ("RECEVED DATA").getBytes();
              System.out.println("Dato ricevuto correttamente...");
              DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
              server.send(sendPacket);
            }
          }
          catch(Exception e)
          {
            System.out.println(e.getMessage());
          }
          i++;
      }
      sendData = new byte[1024];
      sendData = String.valueOf("END").getBytes();
      System.out.println("Messaggio di fine...");
      DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
      server.send(sendPacket);

    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }

  }
}
