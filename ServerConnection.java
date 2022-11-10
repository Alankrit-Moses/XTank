import java.util.*;
import java.io.*;
import java.net.*;
public class ServerConnection implements Runnable
{
    private Socket server;
    private Client clientInstance;
    private ObjectInputStream isr;
    private ObjectOutputStream os;
    public ServerConnection(Socket server, Client client) throws IOException
    {
        this.clientInstance = client;
        this.server = server;
        isr = new ObjectInputStream(server.getInputStream());
        os = new ObjectOutputStream(server.getOutputStream());
    }

    public synchronized void run()
    {
        while(true)
        {
            try
            {
            	Object obj = isr.readObject();
            	if(obj instanceof Elements[][])
            	{
            		Elements arr[][] = (Elements[][])(obj);
            		System.out.println("NON-ACCESSIBLE 1");
                    clientInstance.setGrid(arr);
                    System.out.println("NON-ACCESSIBLE 2");
                    os.writeObject(clientInstance.getGrid());
                    os.flush();
            	}
            	else if(((String)(obj)).equals("tank"))
            	{
            		System.out.println("Setting TANK!");
            		obj = isr.readObject();
            		Tank t = (Tank)(obj);
                    clientInstance.setTank(t);
            	}
            	else if(((String)(obj)).equals("print"))
            	{
            		System.out.println("ORDER TO PRINT!!!");
            		obj = isr.readObject();
            		Elements[][] arr = (Elements[][])(obj);
            		clientInstance.setGrid(arr);
            	}
            }
            catch(Exception e){
            	e.printStackTrace();
            }
        }
    }
}