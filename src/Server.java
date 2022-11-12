import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
public class Server{
	ServerSocket s;
	ArrayList<ClientHandler> clients;
	private static ExecutorService pool;
	private Game g;
	public Server() throws IOException
	{
		pool = Executors.newFixedThreadPool(12);
		clients = new ArrayList<>();
		s = new ServerSocket(4444,0,InetAddress.getLocalHost());
		g = new Game();
		System.out.println("Server at: "+s.getInetAddress());
		System.out.println("Server Started: waiting for client...");
		acceptClient();
	}
	
	public void acceptClient() throws IOException
	{
		while(true){
			Socket client = s.accept();
			ClientHandler handler = new ClientHandler(client,clients,g);
			clients.add(handler);
			System.out.println("Client connected "+client);
			pool.execute(handler);
		}
	}
 
	public static void main(String args[]) throws IOException
	{
		Server s =  new Server();
	}
}