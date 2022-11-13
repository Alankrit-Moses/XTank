/**
 * File: Wall.java
 * Assignment: CSC335PA3
 * @author Aman Dwivedi
 *
 * Description: This is the Wall class. It creates a Wall object which holds its position,
 * direction.
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;
import java.io.Serializable;

public class Wall extends Elements implements Serializable{
	public int x;
	public int y;
	private int color;
	public int direction;
	
	public Wall(int gridCol, int gridRow, int color, int direction) {
		x = gridCol;
		y = gridRow;
		this.color = color;
		this.direction = direction;
	}
	
	/**
	 * Draws the wall on the screen
	 */	
	@Override
	public void draw(PaintEvent screen, Display display) {
		screen.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
		screen.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
		if(direction == 1) {
			screen.gc.fillRectangle((50*x), 50*y, 50, 50);
		} else if(direction == 2) {
			screen.gc.fillRectangle(50*x, (50*y), 50, 50);
		}
	}

	public boolean move(Elements[][] grid, char dir) {
		return false;
	}

}
