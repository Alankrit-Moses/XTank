import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
public class Server {
	ServerSocket s;
	ArrayList<ClientHandler> clients;
	private static ExecutorService pool;
	public Server() throws IOException
	{
		pool = Executors.newFixedThreadPool(200);
		clients = new ArrayList<>();
		s = new ServerSocket(4444,0,InetAddress.getLocalHost());
		System.out.println("Server at: "+s.getInetAddress());
		System.out.println("Server Started: waiting for client...");
		this.acceptClient();
	}
	
	public Socket acceptClient() throws IOException
	{
		while(true){
			Socket client = s.accept();
			System.out.println("REACHED");
			ClientHandler handler = new ClientHandler(client,clients);
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