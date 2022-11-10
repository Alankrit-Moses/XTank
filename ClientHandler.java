import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ClientHandler implements Runnable {

	private Socket client;
	private BufferedReader input;
	private ObjectOutputStream output;
	private ObjectInputStream isr;
	ArrayList<ClientHandler> clients;
	Game g;
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients, Game g) throws IOException
	{
		this.g = g;
		this.client = clientSocket;
		this.clients = clients;
		this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.output = new ObjectOutputStream(client.getOutputStream());
		System.out.println("Streams created");
		isr = new ObjectInputStream(client.getInputStream());
	}

	public void run()
	{
		try{
			while(true)
			{
				String command = this.getReader().readLine();
				System.out.println("Command Read");
				if(command.startsWith("grid"))
				{
					output.writeObject(g.getGrid());
					output.flush();
					System.out.println("Grid given: "+g.getGrid());
					g.setGrid((Elements[][])(isr.readObject()));
				}
				else if(command.equals("generate"))
				{
					int x = 3;
					int y = 3;
					Elements[][] grid = g.getGrid();
					while(grid[x][y]!=null)
					{
						x = ThreadLocalRandom.current().nextInt(0,20);	
						y = ThreadLocalRandom.current().nextInt(0,20);
					}
					System.out.println(x+" "+y);
					Elements tank = new Tank(x,y,1,1);
					grid[x][y] = tank;
					g.setGrid(grid);
					System.out.println("Added new TANK!");
					output.writeObject("tank");
					output.writeObject(tank);
					output.flush();
					//outToAll("print",grid);
					System.out.println("WRITING SUCCESS..."+grid+"   "+grid[x][y]);
					g.setGrid(grid);
					//g.setGrid((Elements[][])(isr.readObject()));
					System.out.println("SETTING SUCCESS...");
					g.printGrid();
				}
				outToAll("print",g.getGrid());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void outToAll(Elements arr[][]) throws IOException, ClassNotFoundException
	{
		for(ClientHandler client1: clients){
			if(client1!=this)
			{
				client1.output.writeObject(g.getGrid());
				client1.output.flush();
				client1.isr.readObject();
			}
		}
		System.out.println("Written to all and READ FROM ALL");
	}

	public void outToAll(String msg, Elements arr[][]) throws IOException
	{
		for(ClientHandler client: clients){
			if(client!=this)
			{
				client.output.writeObject(msg);
				client.output.writeObject(g.getGrid());
				client.output.flush();
			}
		}
	}

	public BufferedReader getReader()
	{
		return this.input;
	}
}