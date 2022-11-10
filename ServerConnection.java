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
                Elements arr[][] = (Elements[][])(isr.readObject());
                System.out.println("asdkjalksdjalksjlkjldasd");
                System.out.println("Got an array");
                clientInstance.setGrid(arr);
                clientInstance.draw();
                os.writeObject(clientInstance.getGrid());
                os.flush();
            }
            catch(Exception e){
            }
        }
    }
}