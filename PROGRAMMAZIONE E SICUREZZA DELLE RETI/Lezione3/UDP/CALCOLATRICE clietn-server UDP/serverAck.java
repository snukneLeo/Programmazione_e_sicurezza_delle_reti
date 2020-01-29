import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

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
    //  byte[] receiveAck = new byte[1024];

      int i = 0,port = 0,ris = 0, op1 = -1, op2 = -1;
      InetAddress IPAddress = null;
      DatagramPacket receivePacket = null;
      String operando = "";
      boolean recevedACK = false;

      System.out.println("Attesa connessione...");
      while(true && recevedACK != true)
      {
        try
        {
          //primo operatore
          receivePacket = new DatagramPacket(receiveData,receiveData.length);
          serverSocket.receive(receivePacket);
          operando = new String(receivePacket.getData());
          op1 = Integer.parseInt(operando.trim());
          System.out.println("RICEVUTO: " + operando);

          //secondo operatore
          receiveData = new byte[1024];
          receivePacket = new DatagramPacket(receiveData,receiveData.length);
          serverSocket.receive(receivePacket);
          operando = new String(receivePacket.getData());
          op2 = Integer.parseInt(operando.trim());
          System.out.println("RICEVUTO: " + operando);

          //invio il risultato al clientSocket
          IPAddress = receivePacket.getAddress();
          port = receivePacket.getPort();
          ris = op1 + op2;//sommo
          sendData = String.valueOf(ris).getBytes();
          DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
          serverSocket.send(sendPacket);
          System.out.println("Pacchetto inviato...");

          while(recevedACK != true)
          {
            try
            {
              receivePacket = new DatagramPacket(receiveData,receiveData.length);
              serverSocket.setSoTimeout(5000);
              serverSocket.receive(receivePacket);
              String acknoledge = new String (receivePacket.getData());
              if(acknoledge.trim().equals("ack"))
              {
                System.out.println("Ack ricevuto " + acknoledge);
                recevedACK = true;
                serverSocket.close();
              }
            }
            catch(SocketTimeoutException e)
            {
              //se sono qui significa che devo rimandare la somma
              System.out.println("Rinvio della somma...");
              DatagramPacket sendPacket1 = new DatagramPacket(sendData,sendData.length,IPAddress,port);
              serverSocket.send(sendPacket1);
            }
          }
        }
        catch(NumberFormatException e)
        {
          System.out.println(e.getMessage());
        }
      }
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
