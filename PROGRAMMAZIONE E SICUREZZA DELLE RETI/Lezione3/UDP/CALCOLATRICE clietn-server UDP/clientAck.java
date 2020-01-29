import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;
class UDPClient
{
  public static void main(String args [])
  {
    try
    {
        Scanner sc = new Scanner(System.in);
        int n = 0;
        DatagramSocket clientSocket = new DatagramSocket();
        //ottengo l'ip
        InetAddress IPAddress = InetAddress.getByName("localhost");
        //creo i canali di trasmissione
        byte[] sendData = new byte[1024];
        byte[] receivedata = new byte[1024];
      //  byte[] sendAck = new byte[1024];

        System.out.print("Inserisci il primo operando: ");
        n = sc.nextInt();
        //invio il primo addendo
        sendData = String.valueOf(n).getBytes();
        DatagramPacket sendPacket1 = new DatagramPacket(sendData,sendData.length,IPAddress,9876);
        clientSocket.send(sendPacket1);

        System.out.print("Inserisci il secondo operando: ");
        //String secondoOp = in.readLine();
        n = sc.nextInt();
        //invio il secondo addendo
        sendData = String.valueOf(n).getBytes();
        DatagramPacket sendPacket2 = new DatagramPacket(sendData,sendData.length,IPAddress,9876);
        clientSocket.send(sendPacket2);

        //attesa per il calcolo dal server
        System.out.println("Attesa del server...");
        DatagramPacket receivePacket = new DatagramPacket(receivedata,receivedata.length);
        clientSocket.receive(receivePacket);
        String result = new String(receivePacket.getData());
        System.out.println("CALCOLO FROM SERVER: " + result);


        //invio l'ack al server
        sendData = new byte[1024];
        System.out.println("Invio l'ack al server");
        sendData = ("ack").getBytes();
        DatagramPacket sendAckpacket = new DatagramPacket(sendData,sendData.length,IPAddress,9876);
        clientSocket.send(sendAckpacket);
        System.out.println("Ack inviato!");

        ////////////////////////////////////////////////////////////////////////////
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
