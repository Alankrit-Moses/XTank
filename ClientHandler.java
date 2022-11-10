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
						x = ThreadLocalRandom.current().nextInt(0,101);	
						y = ThreadLocalRandom.current().nextInt(0,101);
					}
					System.out.println(x+" "+y);
					Elements tank = new Tank(x,y,1,1);
					grid[x][y] = tank;
					g.setGrid(grid);
					System.out.println("WRITING SUCCESS..."+grid+"   "+grid[x][y]);
					output.writeObject(grid);
					output.flush();
					g.setGrid((Elements[][])(isr.readObject()));
					System.out.println("SETTING SUCCESS...");
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public void outToAll(Elements arr[][]) throws IOException
	{
		for(ClientHandler client: clients){
			client.output.writeObject(g.getGrid());
			client.output.flush();
		}
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
