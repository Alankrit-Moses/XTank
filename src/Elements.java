/**
 * File: Elements.java
 * Assignment: CSC335PA3
 * @author Aman Dwivedi
 *
 * Description: This is the Elements abstract class.
 */
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;

public abstract class Elements {
	private int x;
	private int y;
	private int color;
	private int direction;
	
	public abstract void draw(PaintEvent screen, Display display);
	
}
