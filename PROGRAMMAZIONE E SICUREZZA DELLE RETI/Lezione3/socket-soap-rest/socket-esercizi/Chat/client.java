import java.net.*;
import java.io.*;
import java.lang.Thread;

class Client
{
  public static void main(String args[]) throws Exception
  {
    try
    {
      Socket socket = new Socket("localhost",1111);
      //creo una istanza della classe Thread per ricevere dal socket
      Receved rv = new Receved(socket);
      Thread thread = new Thread(rv);
      thread.start();
      //creo una istanza della classe Thread per scrivere da tastiera
      KeyRead kr = new KeyRead(socket);
      Thread thread2 = new Thread(kr);
      thread2.start();

      /*NickName nk = new NickName(socket);
      Thread thread3 = new Thread(nk);
      thread3.start();
*/
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}


class Receved implements Runnable
{
  Socket s;
  BufferedReader receive = null;
  int value = 0;
  //NickName nk = new NickName();
  public Receved (Socket s)
  {
    this.s = s;
  }
  //method of thread
  public void run()
  {
    try
    {
      receive = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
      String msg = null;
      while((msg = receive.readLine()) != null) //leggo finchè c'è qualcosa
      {
        if(getvalue() == 0)
        {
          System.out.println(msg);
          System.out.println("Please enter something to send to server...");
        }
      }
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public String nick(Socket s)
  {
    BufferedReader bf = null;
    String nicknamesendToserver = null;
    try
    {
        System.out.print("Inserisci il tuo nickname: ");
        bf = new BufferedReader(new InputStreamReader(System.in));
        nicknamesendToserver = bf.readLine();
        value = 1;
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
    return nicknamesendToserver;
  }

  public int getvalue()
  {
    return value;
  }
}

class KeyRead implements Runnable
{
  Socket s;
  PrintWriter printer = null;
  BufferedReader brinput = null;
  String nickname = null;
  public KeyRead(Socket s)
  {
    this.s = s;
  }
  Receved rc = new Receved(s);
  //method of thread
  public void run()
  {
    try
    {
      System.out.println("Client connected to " + s.getInetAddress() + "on port " + s.getPort());
      String nick = rc.nick(s);
      this.printer = new PrintWriter(s.getOutputStream(), true);
      //this.printer.println(nick);
      while(true)
      {
        System.out.println("Type your message to send to server..Type 'EXIT' to exit");
        brinput = new BufferedReader(new InputStreamReader(System.in));
        String msgtoServerString = null;
        msgtoServerString = brinput.readLine();
        this.printer.println(nick + ": " + msgtoServerString);
        this.printer.flush();
        if(msgtoServerString.equals("EXIT"))
          break;
      }
      s.close();
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}
