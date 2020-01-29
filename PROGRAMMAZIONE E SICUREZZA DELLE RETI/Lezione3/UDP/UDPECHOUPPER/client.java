/*
  ECHO UPPER PER UDP
*/

//guest -> invitato
//host -> padrone

// CLIENT
import java.io.*;
import java.net.*;
class UDPClient {
public static void main(String args[]) throws Exception
{
  try
  {
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    //SOCKET UDP
    DatagramSocket clientSocket = new DatagramSocket();
    //
    InetAddress IPAddress = InetAddress.getByName("localhost");// IP
    // destinazione
    //contenuto del pacchetto
    //non è orientato alla connessione
    //array di byte per l'invio e per la ricezione
    //tcp -> crea un tubo per la connessione con i vari stream
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];
    ///////////////////////////////////////////////////////////
    System.out.println("Inserisci il messaggio per il server\n");
    String sentence = inFromUser.readLine();
    sendData = sentence.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, 9876); // IP e PORTA
    // destinazione
    clientSocket.send(sendPacket);
    //quello che ricevo dal server
    DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
    clientSocket.receive(receivePacket);
    String modifiedSentence = new String(receivePacket.getData());
    System.out.println("FROM SERVER:" + modifiedSentence);
    //chiusure
    clientSocket.close();
  }
  catch (IOException e)
  {
    System.out.println(e.getMessage());
  }
  }
}
