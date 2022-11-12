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
				try {
					new Thread(server = new Server()).start();;
					display.getShells()[0].dispose();
					client = new Client(server.getAddress(), display);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				
				Text t1 = new Text(sh, 0);
				t1.setBounds(270, 400, 570, 70);
				
				Button start = new Button(sh,SWT.PUSH);
				start.setBounds(300,500,100,100);
				start.setText("JOIN");
				start.setFont( new Font(display,"Comic Sans MS", 10, SWT.BOLD ));
				
				start.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						String serverAddress = t1.getText();
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