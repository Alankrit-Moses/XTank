import java.net.*;
import java.io.*;
import java.util.*;
public class Client {
    
    private Socket client;
	BufferedReader isr;
	PrintWriter pw;
    public Client(String address) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Server Address: ");
        address = sc.nextLine();
        this.client = new Socket(address,4444);

        ServerConnection serverConn = new ServerConnection(this.client);

        new Thread(serverConn).start();

        pw = new PrintWriter(client.getOutputStream(),true);
        this.runServer();
    }
    public void runServer()
    {
        Scanner sc = new Scanner(System.in);
        try{
            while(true)
            {
                String command = sc.nextLine();
                System.out.println("You said: "+command);
                pw.println(command);
            }
        }
        catch(Exception e){}
    }
    public static void main(String args[]) throws IOException
    {
        Client s = new Client("DONE");
    }
}
