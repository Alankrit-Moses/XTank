/**
 * File: Bullet.java
 * Assignment: CSC335PA3
 * @author Alankrit Moses
 *
 * Description: This is the Bullet Class. It inherits from Elements. It keeps track of the bullet
 * from a tank and draws it accordingly on the screen.
 */
import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;

public class Bullet extends Elements implements Serializable{

	int x,y;
	public boolean drawable; 
	public Bullet(int x, int y)
	{
		this.x = x;
		this.y = y;
		drawable = true;
	}
	
	/**
	 * This method is used to draw the bullet on the screen.
	 */
	@Override
	public void draw(PaintEvent screen, Display display) {
		screen.gc.drawOval(50*x+15, 50*y+15, 20, 20);
		screen.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_YELLOW));
		screen.gc.fillOval(50*x+16, 50*y+16, 19, 19);
	}
	
}
