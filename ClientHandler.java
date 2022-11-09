import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler implements Runnable {

	private Socket client;
	private BufferedReader input;
	private PrintWriter output;
	ArrayList<ClientHandler> clients;
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException
	{
		this.client = clientSocket;
		this.clients = clients;
		this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.output = new PrintWriter(client.getOutputStream(),true);
	}

	public void run()
	{
		try{
			while(true)
			{
				String command = this.getReader().readLine();
				System.out.println("Server Listened:"+command);
				outToAll(command);
			}
		}
		catch(Exception e){}
	}

	public void outToAll(String msg) throws IOException
	{
		for(ClientHandler client: clients){
			client.output.println(msg);
		}
	}

	public PrintWriter getWriter()
	{
		return this.output;
	}

	public BufferedReader getReader()
	{
		return this.input;
	}
}
