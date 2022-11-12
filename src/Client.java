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
    private boolean gameOver = false;
    private Tank t;
    
    public Client(String address) throws Exception {
    	Display.setAppName("XTANK");
        display = new Display();
        shell = new Shell(display);
        shell.setText("XTANK");
        shell.setLayout(new GridLayout());
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
        shell.setSize(1050, 1050);
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
	        pw.println(command);
	        pw.flush();
        }
        catch(Exception e){
        	System.out.println(e);
        }
    }

    public void setGrid(Elements[][] grid)
    {
        this.grid = grid;
        if(((Tank)grid[t.getY()][t.getX()])==null)
        {
        	gameOver = true;
        	freezeClient();
        }
        this.redraw = true;
    }

    public Elements[][] getGrid()
    {
        return this.grid;
    }
    
    public void setTank(Tank t)
    {
    	this.t = t;
    }
    
    public void draw() {
        this.shell.addPaintListener(new PaintListener(){
            public void paintControl(PaintEvent e)
            {
            	if(grid!=null)
            	{
            		Rectangle rect = shell.getClientArea();
                    e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
                    e.gc.fillRectangle(rect.x, rect.y, rect.width, rect.height);
                    e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
                    for(int i = 0; i < 20; i++) {
                    	for(int j = 0; j < 20; j++) {
                    		if (grid[i][j] != null) {
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
        		if(!gameOver)
        		{
	        		System.out.println(e.keyCode);
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
	        	        grid[t.getY()][t.getX()] = t;
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
	        	        grid[t.getY()][t.getX()] = t;
	        	        writable = true;
	        	        redraw = true;
	        		}
	        		else if(e.keyCode==32)
	        		{
	        			runServer("grid");
	        	        while(grid==null) {
	        	        	System.out.print("");
	        	        }
	        	        grid = t.shoot(grid);
	        	        grid[t.getY()][t.getX()] = t;
	        	        writable = true;
	        	        redraw = true;
	        		}
        		}
        	}

			@Override
			public void keyReleased(KeyEvent e) {}
        });
    }
    
    public static void main(String args[]) throws Exception
    {
        Client s = new Client("100.64.1.149");
    }
    
    public void freezeClient()
    {
    	runServer("grid");
        while(grid==null) {
        	System.out.print("");
        }
    	grid[t.getY()][t.getX()] = null;
        writable = true;
        redraw = true;
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