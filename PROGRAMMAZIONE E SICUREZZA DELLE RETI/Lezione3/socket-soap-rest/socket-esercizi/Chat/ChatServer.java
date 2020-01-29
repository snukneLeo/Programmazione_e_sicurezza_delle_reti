import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.util.ArrayList;




class ChatServer
{

	public static void main(String args[]) throws Exception
	{

		ServerSocket conn = new ServerSocket(1111);;
		while(true)
		{
			new Server(conn.accept());
		}
	}
}

class Server implements Runnable
{
	Socket socket;
	static  ArrayList<PrintStream> list = new ArrayList<>();
	static ArrayList<PrintStream> nickname = new ArrayList<>();

	public Server(Socket s)
	{
		this.socket = s;
		new Thread(this).start();
	}

	public void run()
	{
		String from;
		BufferedReader reader = null;
		PrintStream outStream = null;
			try
			{
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outStream = new PrintStream(socket.getOutputStream());
				//nickname.add(outStream);
				list.add(outStream);

				System.out.println("Connected");
				while ((from = reader.readLine()) != null && !from.equals("")) {
					System.out.println(from);
					//outStream.print(from + "\r\n");
					int i = 0;
					for(PrintStream p : list)
					{
						if(p != outStream)
							p.print(from + "\r\n");
					}
				}
				socket.close();
			}
				catch (IOException e)
				{
					System.out.println(e.getMessage());
				}
				System.out.println("Disconnected");
				list.remove(outStream);
		}
}
