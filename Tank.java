// import org.eclipse.swt.SWT;
// import org.eclipse.swt.events.PaintEvent;
// import org.eclipse.swt.widgets.Display;
import java.io.*;

public class Tank extends Elements implements Serializable {
	private int x;
	private int y;
	private int color;
	public int direction;
	
	public Tank(int gridCol, int gridRow, int color, int direction) {
		x = gridCol;
		y = gridRow;
		this.color = color;
		this.direction = direction;
	}
	
	public void changeDirection(char dir) {
		if (dir == 'l') {
			direction--;
		}
		if (dir == 'r') {
			direction++;
		}
		if (direction < 1) {
			direction = 8;
		}
		if (direction > 8) {
			direction = 1;
		}
	}
	
	public Elements[][] move(Elements[][] grid, char dir) {
		if(dir == 'f') {
			if(this.direction == 1 && this.y > 0 && grid[y-1][x] == null) {
				grid[y][x] = null;
				y--;
				grid[y][x] = this;
			} else if(this.direction == 2 && this.y > 0 && this.x < 59 && grid[y-1][x+1] == null) {
				grid[y][x] = null;
				y--;
				x++;
				grid[y][x] = this;
			} else if(this.direction == 3 && this.x < 59 && grid[y][x+1] == null) {
				grid[y][x] = null;
				x++;
				grid[y][x] = this;
			} else if(this.direction == 4 && this.y < 59 && this.x < 59 && grid[y+1][x+1] == null) {
				grid[y][x] = null;
				y++;
				x++;
				grid[y][x] = this;
			} else if(this.direction == 5 && this.y < 59 && grid[y+1][x] == null) {
				grid[y][x] = null;
				y++;
				grid[y][x] = this;
			} else if(this.direction == 6 && this.y < 59 && this.x > 0 && grid[y+1][x-1] == null) {
				grid[y][x] = null;
				y++;
				x--;
				grid[y][x] = this;
			} else if(this.direction == 7 && this.x > 0 && grid[y][x-1] == null) {
				grid[y][x] = null;
				x--;
				grid[y][x] = this;
			} else if(this.direction == 8 && this.y > 0 && this.x > 0 && grid[y-1][x-1] == null) {
				grid[y][x] = null;
				y--;
				x--;
				grid[y][x] = this;
			}
		}
		return grid;
}
	
	/*public void draw(PaintEvent screen, Display display) {
		screen.gc.setForeground(display.getSystemColor(color));
		screen.gc.fillRectangle(10*x, 10*y, 10, 10);
		screen.gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
		screen.gc.fillOval(10*x, 10*y, 10, 10);
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
	}*/

	@Override
	public String toString() {
		return "Tank [x=" + x + ", y=" + y + ", direction=" + direction + "]";
	}
}