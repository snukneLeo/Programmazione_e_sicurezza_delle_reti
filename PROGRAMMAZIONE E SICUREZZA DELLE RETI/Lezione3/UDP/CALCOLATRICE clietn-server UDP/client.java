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
        //creo il buffer per l'invio
        //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //socket UDP
        DatagramSocket clientSocket = new DatagramSocket();
        //ottengo l'ip
        InetAddress IPAddress = InetAddress.getByName("localhost");
        //creo i canali di trasmissione
        byte[] sendData = new byte[1024];
        byte[] receivedata = new byte[1024];
        //MANCA IL SEND
        /////////////////////////////////////
        System.out.print("Inserisci il primo operando:");
        //String primoOp = in.readLine();
        n = sc.nextInt();
        sendData = String.valueOf(n).getBytes();
        //invio il primo addendo
        //sendData = primoOp.getBytes();
        DatagramPacket sendPacket1 = new DatagramPacket(sendData,sendData.length,IPAddress,9876);
        clientSocket.send(sendPacket1);

        System.out.print("Inserisci il secondo operando:");
        //String secondoOp = in.readLine();
        n = sc.nextInt();
        sendData = String.valueOf(n).getBytes();
        //invio il secondo addendo
        sendData = String.valueOf(n).getBytes();
        DatagramPacket sendPacket2 = new DatagramPacket(sendData,sendData.length,IPAddress,9876);
        clientSocket.send(sendPacket2);

        System.out.println("Attesa del server...");
        DatagramPacket receivePacket = new DatagramPacket(receivedata,receivedata.length);
        clientSocket.receive(receivePacket);
        String result = new String(receivePacket.getData());
        System.out.println("CALCOLO FROM SERVER: " + result);
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
