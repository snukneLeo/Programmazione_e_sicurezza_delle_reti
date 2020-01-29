import java.io.*;
import java.net.*;
class UDPclient
{
  public static void main(String args[])
  {
    try
    {
      DatagramSocket client = new DatagramSocket();
      InetAddress ip = InetAddress.getByName("localhost");

      //creo i vari byte per l'invio e la ricezione
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];

      while(true)
      {
        //invio interfaccia
        System.out.println("---------------------------------");
        System.out.println("INTERFACE: WIFI 802.11");
        System.out.println("INDIRIZZO IP: 127.0.0.1");
        System.out.println("PORTA: 1111");
        System.out.println("INVIO DATO IN CORSO...");
        double minTemp = -1;
        double maxTemp = 35;
        double c = ((maxTemp + minTemp) + 1);
        int temp = (int)(Math.random()*c);
        //invio i dati
        sendData = String.valueOf(temp + "°").getBytes();
        System.out.println(temp +"°");
        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,9876);
        client.send(sendPacket);
        System.out.println("INVIO EFFETTUATO");
        System.out.println("---------------------------------");

        //quello che ricevo dal server
        DatagramPacket recevedP = new DatagramPacket(receiveData,receiveData.length);
        client.receive(recevedP);
        String returnRes = new String (recevedP.getData());
        System.out.println(returnRes.trim());
        if(returnRes.trim().equals("END"))
        {
          System.out.println("Termino...");
          break;
        }
        receiveData = new byte[1024];
      }
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}
