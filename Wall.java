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
	
	@Override
	public void draw(PaintEvent screen, Display display) {
		/*screen.gc.setForeground(display.getSystemColor(color));
		if(direction == 1) {
			screen.gc.fillRectangle((10*x)+4, 10*y, 2, 10);
		} else if(direction == 2) {
			screen.gc.fillRectangle(10*x, (10*y)+4, 10, 2);
		}*/
		
	}

	public boolean move(Elements[][] grid, char dir) {
		return false;
	}

}