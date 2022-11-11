import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
    public boolean redraw = false;
    public boolean writable = false;
    private Display display;
    private Shell shell;
    private Canvas canvas;
    private Tank t; 
    
    public Client(String address) throws Exception {
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
        
        
    }

    public void runServer(String command)
    {
    	try 
    	{
	        //System.out.println("You said: "+command);
	        pw.println(command);
	        pw.flush();
        }
        catch(Exception e){
        	System.out.println(e);
        }
    }

    public void setGrid(Elements[][] grid)
    {
    	//System.out.println("GRID WAS SET!!!" + grid);
        this.grid = grid;
        this.redraw = true;
    }

    public Elements[][] getGrid()
    {
        return this.grid;
    }
    
    public void setTank(Tank t)
    {
    	System.out.println("Tank before: "+this.t);
    	this.t = t;
    	System.out.println("Tank SET: "+this.t);
    }
    
    public void draw() {
        this.shell.addPaintListener(new PaintListener(){
            public void paintControl(PaintEvent e)
            {
            	if(grid!=null)
            	{
                	//System.out.println("DRAW WAS CALLED!!!");
            		Rectangle rect = shell.getClientArea();
                    e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
                    e.gc.fillRectangle(rect.x, rect.y, rect.width, rect.height);
                    e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
                    for(int i = 0; i < 20; i++) {
                    	for(int j = 0; j < 20; j++) {
                    		if (grid[i][j] != null) {
                    			//System.out.println("WORKS");
                    			grid[i][j].draw(e,display);
                    		}
                    	}
                    }
            	}
            	
            }
        });
        
        shell.addKeyListener(new KeyListener() {
        	public void keyPressed(KeyEvent e)
        	{
    			grid = null;
    			writable = false;
        		Tank oldTank = t;
        		if(e.keyCode==16777217)
        		{
        			runServer("grid");
        	        while(grid==null) {
        	        	System.out.print("");
        	        }
        	        t = t.move(grid, 'f');
        	        writable = true;
        	        redraw = true;
        		}
        		else if(e.keyCode==16777218)
        		{
        			runServer("grid");
        	        while(grid==null) {
        	        	System.out.print("");
        	        }
        	        t = t.move(grid, 'b');
        	        writable = true;
        	        redraw = true;
        		}
        		else if(e.keyCode==16777219)
        		{
        			System.out.println("TURNING LEFT");
        			runServer("grid");
        	        while(grid==null) {
        	        	System.out.print("");
        	        }
        	        t.changeDirection('l');
        	        writable = true;
        	        redraw = true;
        		}
        		else if(e.keyCode==16777220)
        		{
        			System.out.println("TURNING right");
        			runServer("grid");
        	        while(grid==null) {
        	        	System.out.print("");
        	        }
        	        t.changeDirection('r');
        	        writable = true;
        	        redraw = true;
        		}
        	}
        	
        	private void direction(char c) {}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
        });
    }
    
    public static void main(String args[]) throws Exception
    {
        Client s = new Client("DONE");
    }

	public Tank getTank() {
		return t;
	}
	public void printGrid() {
		for(int x=0;x<20;x++)
		{
			for(int y=0;y<20;y++)
			{
				if(grid[x][y]==null)
					System.out.print('.');
				else
					System.out.print('T');
			}
			System.out.println();
		}
	}
}