import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
public class Client {

    private Socket client;
	private PrintWriter pw;
    private Elements[][] grid;
    
    private Display display;
    private Shell shell;
    private Canvas canvas;
    
    public Client(String address) throws Exception {

    	System.out.println("ALANKRIT JAY-SYNTH MOZEZ sucks");
    	Display.setAppName("XTANK");
        display = new Display();
        shell = new Shell(display);
        shell.setText("XTANK");
        shell.setSize(1000, 1000);
        shell.setLayout(new GridLayout());
    	// ---- create widgets for the interface
        Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
        canvas = new Canvas(upperComp, SWT.NONE);
        canvas.setSize(1000, 1000);

        // ---- canvas for the document
        
        
        Scanner sc = new Scanner(System.in);
        grid = new Elements[100][100];
        
//    	Tank t = new Tank(10, 10, SWT.COLOR_DARK_GREEN, 2);
//    	Tank t2 = new Tank(0, 0, SWT.COLOR_RED, 3);
//    	grid[10][10] = t;
//    	grid[0][0] = t2;
    	System.out.println("Enter Server Address: ");
        address = sc.nextLine();
        this.client = new Socket(address,4444);
        ServerConnection serverConn = new ServerConnection(this.client, this);

        new Thread(serverConn).start();

        pw = new PrintWriter(client.getOutputStream(),true);
        System.out.println("grid: "+grid);
        this.runServer("generate");
        System.out.println("grid: "+grid);
        draw();
        shell.open();
        while (!shell.isDisposed())
            if (!display.readAndDispatch()) {
            }
        display.dispose();
        
        System.out.println("ALANKRIT JAY-SYNTH MOZEZ sucks");
        //this.draw();
        
        
    }

    public void runServer(String command)
    {
    	try 
    	{
	        System.out.println("You said: "+command);
	        pw.println(command); 
	        pw.flush();
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
    
    public void draw() {
    	
        
        this.canvas.addPaintListener(e -> {
            org.eclipse.swt.graphics.Rectangle rect = shell.getClientArea();
            e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
            e.gc.fillRectangle(rect.x, rect.y, rect.width, rect.height);
            e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
            this.runServer("grid");
            for(int i = 0; i < 100; i++) {
            	for(int j = 0; j < 100; j++) {
            		if (grid[i][j] != null) {
            			System.out.println("WORKS");
            			grid[i][j].draw(e, display);
            		}
            	}
            }
        });
    	canvas.redraw();
    }
    
    public static void main(String args[]) throws Exception
    {
        Client s = new Client("DONE");
    }
}