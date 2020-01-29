import java.io.*;
import java.net.*;

class server
{
  public static void main(String args[])
  {
    try
    {
      DatagramSocket server = new DatagramSocket(9876);
      DatagramSocket serverP1 = new DatagramSocket(4000); //Alta
      DatagramSocket serverP2 = new DatagramSocket(3000); //Media
      DatagramSocket serverP3 = new DatagramSocket(2000); //Bassa
      while(true)
      {
        System.out.println("Attesa nuovo report...");
        byte[] sendData = new byte[1024];
        byte[] receveData = new byte[1024];
        byte[] p1 = new byte[1024];
        byte[] p2 = new byte[1024];
        byte[] p3 = new byte[1024];



        DatagramPacket ricevoP = new DatagramPacket(receveData,receveData.length);
        server.receive(ricevoP);
        String report = new String( ricevoP.getData());
        System.out.println(report.trim());

        if(getP1(p1,serverP1) == null)
          if(getP2(p2,serverP2) == null)
            if(getP3(p3,serverP3) == null);

        //invio la riposta al client
        int port = ricevoP.getPort();
        InetAddress ip = ricevoP.getAddress();
        sendData = ("OK").getBytes();
        DatagramPacket sendP = new DatagramPacket(sendData,sendData.length,ip,port);
        server.send(sendP);
        System.out.println("Messaggio inviato");
      }
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public static String getP1(byte [] p1,DatagramSocket serverP1)
  {
    p1 = new byte[1024];
    DatagramPacket ricevoPP;
    String pp1 = null;
    try
    {
      ricevoPP = new DatagramPacket(p1,p1.length);
      serverP1.receive(ricevoPP);
      pp1 = new String(ricevoPP.getData());
    }
    catch(IOException e)
    {

    }
    return pp1;
  }

  public static String getP2(byte [] p2,DatagramSocket serverP2)
  {
    p2 = new byte[1024];
    DatagramPacket ricevoPP2;
    String pp2 = null;
    try
    {
      ricevoPP2 = new DatagramPacket(p2,p2.length);
      serverP2.receive(ricevoPP2);
      pp2 = new String(ricevoPP2.getData());
    }
    catch(IOException e)
    {

    }
    return pp2;
  }

  public static String getP3(byte [] p3,DatagramSocket serverP3)
  {
    p3 = new byte[1024];
    DatagramPacket ricevoPP3;
    String pp3 = null;
    try
    {
      ricevoPP3 = new DatagramPacket(p3,p3.length);
      serverP3.receive(ricevoPP3);
      pp3 = new String(ricevoPP3.getData());
    }
    catch(IOException e)
    {

    }
    return pp3;
  }
}
