import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;

public class Bullet extends Elements{
	private int x;
	private int y;
	private int color;
	private int direction;
	
	public Bullet(int gridCol, int gridRow, int color, int direction) {
		x = gridCol;
		y = gridRow;
		this.color = color;
		this.direction = direction;
	}

	@Override
	public void draw(PaintEvent screen, Display display) {
		screen.gc.setForeground(display.getSystemColor(color));
		int xOffset = 0;
		int yOffset = 0;
		if(direction == 1) {
			yOffset = -5;
		} else if(direction == 2) {
			yOffset = -4;
			xOffset = 4;
		} else if(direction == 3) {
			xOffset = 5;
		} else if(direction == 4) {
			yOffset = 4;
			xOffset = 4;
		} else if(direction == 5) {
			yOffset = 5;
		} else if(direction == 6) {
			yOffset = 4;
			xOffset = -4;
		} else if(direction == 7) {
			xOffset = -5;
		} else if(direction == 8) {
			yOffset = -4;
			xOffset = -4;
		}
		screen.gc.drawLine((10*x)+5, (10*y)+5, (10*x)+5+xOffset, (10*y)+5+yOffset);		
	}

	@Override
	public boolean move(Elements[][] grid, char dir) {
		// TODO Auto-generated method stub
		return false;
	}

}
