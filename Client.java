import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.DisposeEvent;
//import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
public class Client {

    private Socket client;
	private PrintWriter pw;
    private Elements[][] grid;
    private boolean redraw = false;
    private Display display;
    private Shell shell;
    private Canvas canvas;
    private Tank t;
    
    public Client(String address) throws Exception {

    	System.out.println("ALANKRIT JAY-SYNTH MOZEZ sucks");
    	Display.setAppName("XTANK");
        display = new Display();
        shell = new Shell(display);
        shell.setText("XTANK");
        shell.setLayout(new GridLayout());
    	// ---- create widgets for the interface
        //Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
        //canvas = new Canvas(upperComp, SWT.NONE);
        //canvas.setSize(1000, 1000);

        // ---- canvas for the document
        
        
        Scanner sc = new Scanner(System.in);
    	System.out.println("Enter Server Address: ");
        address = sc.nextLine();
        this.client = new Socket(address,4444);
        ServerConnection serverConn = new ServerConnection(this.client, this);

        new Thread(serverConn).start();
        pw = new PrintWriter(client.getOutputStream(),true);
        System.out.println("grid: "+grid);
        this.runServer("generate");
        this.runServer("grid");
        while(grid==null) {
        	System.out.print("");
        }
        System.out.println("grid: "+grid[3][3]);
        draw();
        shell.open();
        shell.setSize(1000, 1000);
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch()) {
            }
            if(redraw)
            {
            	shell.redraw();
            	redraw = false;
            }
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
        catch(Exception e){
        	System.out.println(e);
        }
    }

    public void setGrid(Elements[][] grid)
    {
    	System.out.println("GRID WAS SET!!!" + grid);
        this.grid = grid;
        System.out.println("TRYING TO DRAW");
        this.redraw = true;
    }

    public Elements[][] getGrid()
    {
        return this.grid;
    }
    
    public void setTank(Tank t)
    {
    	this.t = t;
    	System.out.println("Tank SET: "+this.t);
    }
    
    public void draw() {

        System.out.println("DRAWING...");
        this.shell.addPaintListener(new PaintListener(){
            public void paintControl(PaintEvent e)
            {
            	System.out.println("DRAW WAS CALLED!!!");
            	Rectangle rect = shell.getClientArea();
                e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
                e.gc.fillRectangle(rect.x, rect.y, rect.width, rect.height);
                e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
                for(int i = 0; i < 20; i++) {
                	for(int j = 0; j < 20; j++) {
                		if (grid[i][j] != null) {
                			System.out.println("WORKS");
                			grid[i][j].draw(e,display);
                		}
                	}
                }
            }
        });
        shell.setSize(1000,1000);
    }
    
    public static void main(String args[]) throws Exception
    {
        Client s = new Client("DONE");
    }
}