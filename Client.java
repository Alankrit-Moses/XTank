import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
public class Client {

    private Socket client;
	private PrintWriter pw;
    private Elements[][] grid;
    public Client(String address) throws Exception {
        Scanner sc = new Scanner(System.in);
        grid = new Elements[100][100];
        System.out.println("Enter Server Address: ");
        address = sc.nextLine();
        this.client = new Socket(address,4444);
        ServerConnection serverConn = new ServerConnection(this.client, this);

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
                pw.flush();
            }
        }
        catch(Exception e){}
    }

    public void setGrid(Elements[][] grid)
    {
        this.grid = grid;
    }

    public Elements[][] getGrid()
    {
        return this.grid;
    }

    public static void main(String args[]) throws Exception
    {
        Client s = new Client("DONE");
    }
}
