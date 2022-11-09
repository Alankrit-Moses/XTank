import java.util.*;
import java.io.*;
import java.net.*;
public class ServerConnection implements Runnable
{
    private Socket server;
    private BufferedReader isr;

    public ServerConnection(Socket server) throws IOException
    {
        this.server = server;
        isr = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }

    public void run()
    {
        while(true)
        {
            try
            {
                String command = isr.readLine();
                System.out.println("Someone said: "+command);
            }
            catch(Exception e){}
        }
    }
}