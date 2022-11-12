import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
public class Server implements Runnable{
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
		//acceptClient();
	}
	
	public void run()
	{
		boolean check = true;
		while(true){
			Socket client;
			try {
				client = s.accept();
				ClientHandler handler = new ClientHandler(client,clients,g);
				clients.add(handler);
				System.out.println("Client connected "+client);
				pool.execute(handler);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getAddress() {
		return (""+s.getInetAddress()).split("/")[1];
	}
 
	public static void main(String args[]) throws IOException
	{
		Server s =  new Server();
	}
}