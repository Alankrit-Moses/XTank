/**
 * File: Main.java
 * Assignment: CSC335PA3
 * @author Aman Dwivedi
 *
 * Description: This is the Main class. It displays the opening window and lets the user
 * choose between creating a new server or joining an existing server.
 */
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.net.*;

public class Main {
	
	private static Display display;
    private static Shell shell;
    private static Canvas canvas;
	private static Server server;
	private static Client client;
    public static void main(String args[])
	{
		Display display = new Display();
		Shell sh = new Shell(display);
		
		menu(display, sh);
		
		sh.setSize(900,700);
		sh.open();
		
		while( !sh.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
    
    /**
     * This function displays the opening window.
     * @param display: SWT display
     * @param sh: shell associated with current display
     */
    public static void menu(Display display, Shell sh)
    {
    	
    	Label l1 = new Label(sh,SWT.NONE);
		l1.setText("XTANK GAME");
		l1.setBounds(270,120,630,70);
		l1.setFont( new Font(display,"Comic Sans MS", 30, SWT.BOLD));
		
		Button newServer = new Button(sh,SWT.PUSH);
		newServer.setBounds(300,300,300,40);
		newServer.setText("CREATE NEW SERVER");
		newServer.setFont( new Font(display,"Comic Sans MS", 10, SWT.BOLD ));
		
		Button join = new Button(sh,SWT.PUSH);
		join.setBounds(300,400,300,40);
		join.setText("JOIN SERVER");
		join.setFont( new Font(display,"Comic Sans MS", 10, SWT.BOLD ));
		
		newServer.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				join.setVisible(false);
				join.setEnabled(false);
				newServer.setVisible(false);
				newServer.setEnabled(false);
				Label l2 = new Label(sh,SWT.NONE);
				l2.setText("Enter Armor (100-200): ");
				l2.setBounds(270,320,200,70);
				l2.setFont( new Font(display,"Comic Sans MS", 10, SWT.BOLD ));
				
				Text t1 = new Text(sh, 0);
				t1.setBounds(550, 320, 50, 30);
				
				Label l3 = new Label(sh,SWT.NONE);
				l3.setText("Enter Damage (20-100): ");
				l3.setBounds(270,400,200,70);
				l3.setFont( new Font(display,"Comic Sans MS", 10, SWT.BOLD ));
				
				Text t2 = new Text(sh, 0);
				t2.setBounds(550, 400, 50, 30);
				
				
				Button start = new Button(sh,SWT.PUSH);
				start.setBounds(350,500,100,100);
				start.setText("START");
				start.setFont( new Font(display,"Comic Sans MS", 10, SWT.BOLD ));
				
				start.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						int armor = Integer.parseInt(t1.getText());
						int damage = Integer.parseInt(t2.getText());
						try {
							new Thread(server = new Server(damage, armor)).start();
							display.getShells()[0].dispose();
							client = new Client(server.getAddress(), display);
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}
		});
		
		join.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				join.setVisible(false);
				join.setEnabled(false);
				newServer.setVisible(false);
				newServer.setEnabled(false);
				Label l2 = new Label(sh,SWT.NONE);
				l2.setText("Enter Server Address:");
				l2.setBounds(270,320,630,70);
				l2.setFont( new Font(display,"Comic Sans MS", 10, SWT.BOLD ));
				
				Text t3 = new Text(sh, 0);
				t3.setBounds(270, 400, 570, 70);
				
				Button start = new Button(sh,SWT.PUSH);
				start.setBounds(300,500,100,100);
				start.setText("JOIN");
				start.setFont( new Font(display,"Comic Sans MS", 10, SWT.BOLD ));
				
				start.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						String serverAddress = t3.getText();
						display.getShells()[0].dispose();
						try {
							client = new Client(serverAddress, display);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}
		});
    }
}
