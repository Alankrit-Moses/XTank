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

    public void run()
    {
        while(true)
        {
            try
            {
            	Object obj = isr.readObject();
            	if(obj instanceof Elements[][])
            	{
            		Elements arr[][] = (Elements[][])(obj);
                    clientInstance.setGrid(arr);
                    os.writeObject(clientInstance.getGrid());
                    os.flush();
            	}
            	else if(((String)(obj)).equals("tank"))
            	{
            		System.out.println("\nSetting TANK!");
            		obj = isr.readObject();
            		Tank t = (Tank)(obj);
                    clientInstance.setTank(t);
            	}
            	else if(((String)(obj)).equals("print"))
            	{
            		obj = isr.readObject();
            		Elements[][] arr = (Elements[][])(obj);
            		clientInstance.setGrid(arr);
            	}
            	else if(((String)(obj)).equals("tankRead"))
            	{
            		os.writeObject(clientInstance.getTank());
                    os.flush();
            	}
            	else if(((String)(obj)).equals("Update"))
            	{
            		os.writeObject(clientInstance.getGrid());
                    os.flush();
            	}
            }
            catch(Exception e){
            }
        }
    }
}