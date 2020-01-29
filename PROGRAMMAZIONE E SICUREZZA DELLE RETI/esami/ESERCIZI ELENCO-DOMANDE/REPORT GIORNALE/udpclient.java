import java.io.*;
import java.net.*;
import java.util.Scanner;

class client
{
  public static void main(String args[])
  {
    try
    {
      DatagramSocket client = new DatagramSocket();
      byte[] sendData = new byte[1024];
      byte[] receveData = new byte[1024];
      InetAddress ip = InetAddress.getByName("localhost");

      System.out.println("Inserisci report: ");
      Scanner sc = new Scanner(System.in);
      String report = sc.nextLine();

      sendData = report.getBytes();
      DatagramPacket sendP = new DatagramPacket(sendData,sendData.length,ip,9876);
      client.send(sendP);
      System.out.println("Invio report...");

      System.out.print("Scegli la priorità (A,M,B): ");
      Scanner p = new Scanner(System.in);
      String pp = p.next();

      sendData = new byte[1024];
      if(pp.charAt(0) == 'A')
      {
        sendData = ("Alta").getBytes();
        DatagramPacket sendPP = new DatagramPacket(sendData,sendData.length,ip,4000);
        client.send(sendPP);
      }
      else if(pp.charAt(0) == 'M')
      {
        sendData = ("Media").getBytes();
        DatagramPacket sendPP = new DatagramPacket(sendData,sendData.length,ip,3000);
        client.send(sendPP);
      }
      else
      {
        sendData = ("Bassa").getBytes();
        DatagramPacket sendPP = new DatagramPacket(sendData,sendData.length,ip,2000);
        client.send(sendPP);
      }

      //ricevo dal server
      DatagramPacket receveP = new DatagramPacket(receveData,receveData.length);
      client.receive(receveP);
      String response = new String(receveP.getData());
      System.out.println(response.trim());
    }
    catch(Exception e)
    {

    }
  }
}
