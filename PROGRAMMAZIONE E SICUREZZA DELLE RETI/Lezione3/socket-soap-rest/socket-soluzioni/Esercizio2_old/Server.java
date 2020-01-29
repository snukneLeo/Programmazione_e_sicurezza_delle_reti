// SERVER

import java.io.*; 
import java.net.*;

class Server
{
    public static void main(String args[])
    {
        ServerSocket serverSock = null;
        Socket cs = null;
        int numero = 1;
        String port = "4444";
        System.out.print("Creazione ServerSocket...");

        if (args.length > 0)
        {
            port = args[0];
        }
        else
        {
            System.out.println("Uso: java Server1 PORT");
            return;
        }


        try 
        {
            serverSock = new ServerSocket(Integer.parseInt(port));
        }
        catch (IOException e) 
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        while (numero<3) // condizione arbitraria 
        { 
            System.out.print("Attesa connessione...");

            try { cs = serverSock.accept(); }
            catch (IOException e) 
            {
                System.err.println("Connessione fallita");
                System.exit(2);
            }

            System.out.println("Conness. da " + cs);

            try 
            {
                BufferedOutputStream b = new                                                BufferedOutputStream(cs.getOutputStream());
                PrintStream os = new PrintStream(b,false);
                os.println("Nuovo numero: " + numero);
                numero++;
                os.println("Stop"); os.close(); 
                cs.close();
            }
            catch (Exception e) 
            {
                System.out.println("Errore: " +e);
                System.exit(3);
            }
        }
    }
}
