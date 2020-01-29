// CLIENT

import java.io.*; 
import java.net.*;

class Client
{
    public static void main(String args[])
    {
        Socket s = null;
        DataInputStream is = null;
        String indirizzo = "localhost";
        String port = "4444";
        
        if (args.length >= 2 )
        {
            indirizzo = args[0];
            port = args[1];
        }else{
            System.out.println("Uso: java Client1 IP PORT");
            return;
        }

        try 
        {
            s = new Socket(indirizzo, Integer.parseInt(port)); // IP e porta del server
            is = new DataInputStream(s.getInputStream());
        }
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
 
        System.out.println("Socket creata: " + s);

        try 
        {
            String line;
            while( (line=is.readLine())!=null ) 
            {
                System.out.println("Ricevuto: " + line);
                if (line.equals("Stop")) 
                    break;
            }
            is.close(); // chiusura stream
            s.close(); // chiusura socket
       }
       catch (IOException e) 
       {
           System.out.println(e.getMessage());
       }
    }  
}
