/**
 * File: Client.java
 * Assignment: CSC335PA3
 * @author Alankrit Moses
 *
 * Description: This is the Client class. It keeps track of the current client and
 * it draws the screen on the user window. 
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
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
    private String serverAddress;
    
    public Client(String address, Display display) throws Exception {
    	this.serverAddress = address;
    	Display.setAppName("XTANK");
        this.display = display;
        //display.getShells()[0].dispose();
        shell = new Shell(display);
        shell.setText("XTANK");
        shell.setLayout(new GridLayout());
        this.client = new Socket(address,4444);
        ServerConnection serverConn = new ServerConnection(this.client, this);

        new Thread(serverConn).start();
        pw = new PrintWriter(client.getOutputStream(),true);
        //System.out.println("grid: "+grid);
        this.runServer("generate");
        System.out.println("AFTER GENERATING");
        this.runServer("grid");
        while(grid==null) {
        	System.out.print("");
        }
        draw();
        shell.open();
        shell.setSize(1400, 1050);
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
	
	/**
     * Writes the commands in the output stream.
     * @param command: The command to write in output stream. 
     */
    public void runServer(String command)
    {
    	try 
    	{
    		System.out.println("You said"+command);
	        pw.println(command);
	        pw.flush();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }

	 /**
     * Sets the current grid to the new grid.
     * @param grid: New grid to be used
     */
    public void setGrid(Elements[][] grid)
    {
        this.grid = grid;
        try{
        	t = (Tank) grid[t.getY()][t.getX()];
        }
        catch(Exception e){
        	t = null;
        }
        if(t==null)
        	this.gameOver = true;
        this.redraw = true;
    }

	/**
     * Getter for the current grid
     */
    public Elements[][] getGrid()
    {
        return this.grid;
    }
    
	/**
     * Setter for the tank object
     */
    public void setTank(Tank t)
    {
    	this.t = t;
    }
    
	/**
     * Draws the entire screen for the client.
     */
    public void draw() {
        this.shell.addPaintListener(new PaintListener(){
            public void paintControl(PaintEvent e)
            {
            	if(grid!=null)
            	{
                    e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
                    e.gc.fillRectangle(0, 0, 1000, 1050);
                    e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
                    for(int i = 0; i < 20; i++) {
                    	for(int j = 0; j < 20; j++) {
                    		if (grid[i][j] != null) {
                    			grid[i][j].draw(e,display);
                    		}
                    	}
                    }
            	}
            	e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_BLUE));
                e.gc.fillRectangle(1000, 0, 1400, 1050);
            	//e.gc.setBackground(display.getSystemColor(SWT.COLOR_CYAN));
            	e.gc.setForeground(display.getSystemColor(SWT.COLOR_YELLOW));
            	e.gc.setFont(new Font(display, "Helvetica", 16, SWT.BOLD));
            	e.gc.drawString("Up arrow:", 1020, 50);
            	e.gc.drawString("To move forward", 1020, 90);
            	e.gc.drawString("Down arrow:", 1020, 150);
            	e.gc.drawString("To move backward", 1020, 190);
            	e.gc.drawString("Right arrow", 1020, 250);
            	e.gc.drawString("To turn clockwise", 1020, 290);
            	e.gc.drawString("Left arrow:", 1020, 350);
            	e.gc.drawString("To turn anti-clockwise", 1020, 390);
            	e.gc.drawString("Space:", 1020, 450);
            	e.gc.drawString("To shoot", 1020, 490);
            	if(gameOver) {
            		e.gc.drawString("GAME OVER!!!", 1020, 600);
            	}
            	e.gc.drawString("Server Address:", 1020, 900);
            	e.gc.drawString(serverAddress, 1020, 940);
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
	        	        t = ((Tank)grid[t.getY()][t.getX()]).move(grid, 'f');
	        	        writable = true;
	        	        redraw = true;
	        		}
	        		else if(e.keyCode==16777218)
	        		{
	        			runServer("grid");
	        	        while(grid==null) {
	        	        	System.out.print("");
	        	        }
	        	        t = ((Tank)grid[t.getY()][t.getX()]).move(grid, 'b');
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
	        	        t = ((Tank)grid[t.getY()][t.getX()]).changeDirection('l');
	        	        //grid[t.getY()][t.getX()] = t;
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
	        	        t = ((Tank)grid[t.getY()][t.getX()]).changeDirection('r');
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
	 /**
     * Getter for the tank object
     */
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
