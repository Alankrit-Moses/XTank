import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public class ClientHandler implements Runnable {

	private Socket client;
	private BufferedReader input;
	private ObjectOutputStream output;
	private ObjectInputStream isr;
	ArrayList<ClientHandler> clients;
	Game g;
	Color color;
	private int[] available = new int[]{SWT.COLOR_RED,
			SWT.COLOR_BLUE,
			SWT.COLOR_GREEN,
			SWT.COLOR_MAGENTA,
			SWT.COLOR_CYAN,
			SWT.COLOR_DARK_RED,
			SWT.COLOR_DARK_BLUE,
			SWT.COLOR_GRAY,
			SWT.COLOR_DARK_GRAY,
			SWT.COLOR_DARK_GREEN,
			SWT.COLOR_DARK_MAGENTA,
			SWT.COLOR_DARK_CYAN};
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients, Game g) throws IOException
	{
		this.color = color;
		this.g = g;
		this.client = clientSocket;
		this.clients = clients;
		this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.output = new ObjectOutputStream(client.getOutputStream());
		isr = new ObjectInputStream(client.getInputStream());
	}

	public void run()
	{
		try{
			while(true)
			{
				String command = this.getReader().readLine();
				System.out.println(command);
				if(command.startsWith("grid"))
				{
					output.writeObject(g.getGrid());
					output.flush();
					g.setGrid((Elements[][])(isr.readObject()));
					g.printGrid();
				}
				else if(command.equals("generate"))
				{
					int x = ThreadLocalRandom.current().nextInt(0,20);	
					int y = ThreadLocalRandom.current().nextInt(0,20);
					Elements[][] grid = g.getGrid();
					while(grid[y][x]!=null)
					{
						x = ThreadLocalRandom.current().nextInt(0,20);	
						y = ThreadLocalRandom.current().nextInt(0,20);
					}
					Elements tank = new Tank(x,y,available[clients.size()],1);
					grid[y][x] = tank;
					g.setGrid(grid);
					output.writeObject("tank");
					output.writeObject(tank);
					output.flush();
					g.setGrid(grid);
					g.printGrid();
				}
				outToAll("print",g.getGrid());
				g.removeBullets();
			}
		}
		catch(Exception e){
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