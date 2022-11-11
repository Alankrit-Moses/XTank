import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;
//import java.awt.event.PaintEvent;
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
	
	public Tank move(Elements[][] grid, char dir) {
		int x1 = x;
		int y1 = y;
		if(dir == 'f') {
			if(this.direction == 1 && this.y > 0 && grid[y-1][x] == null) {
				y--;
			} else if(this.direction == 2 && this.y > 0 && this.x < 19 && grid[y-1][x+1] == null) {
				y--;
				x++;
			} else if(this.direction == 3 && this.x < 19 && grid[y][x+1] == null) {
				x++;
			} else if(this.direction == 4 && this.y < 19 && this.x < 19 && grid[y+1][x+1] == null) {
				y++;
				x++;
			} else if(this.direction == 5 && this.y < 19 && grid[y+1][x] == null) {
				y++;
			} else if(this.direction == 6 && this.y < 19 && this.x > 0 && grid[y+1][x-1] == null) {
				y++;
				x--;
			} else if(this.direction == 7 && this.x > 0 && grid[y][x-1] == null) {
				x--;
			} else if(this.direction == 8 && this.y > 0 && this.x > 0 && grid[y-1][x-1] == null) {
				y--;
				x--;
			}
		}
		else if(dir == 'b') {
			if(this.direction == 1 && this.y < 19 && grid[y+1][x] == null) {
				y++;
			} else if(this.direction == 2 && this.y < 19 && this.x > 0 && grid[y+1][x-1] == null) {
				y++;
				x--;
			} else if(this.direction == 3 && this.x > 0 && grid[y][x-1] == null) {
				x--;
			} else if(this.direction == 4 && this.y > 0 && this.x > 0 && grid[y-1][x-1] == null) {
				y--;
				x--;
			} else if(this.direction == 5 && this.y > 0 && grid[y-1][x] == null) {
				y--;
			} else if(this.direction == 6 && this.y > 0 && this.x < 19 && grid[y-1][x+1] == null) {
				y--;
				x++;
			} else if(this.direction == 7 && this.x < 19 && grid[y][x+1] == null) {
				x++;
			} else if(this.direction == 8 && this.y < 19 && this.x < 19 && grid[y+1][x+1] == null) {
				y++;
				x++;
			}
		}
		Tank newTank = new Tank(x,y,color,direction);
		grid[y1][x1] = null;
		grid[y][x] = newTank;
		return newTank;
}
	
	public void draw(PaintEvent screen, Display display) {
		//System.out.println("I T   I S   D R A W I N G !");
		screen.gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
		screen.gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		screen.gc.drawRectangle(24*x, 24*y, 24, 24);
		screen.gc.fillRectangle(24*x, 24*y, 48, 48);
		//System.out.println("body: " + 10*x + 10*y);
		screen.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
		screen.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
		screen.gc.drawOval(24*x, 24*y, 24, 24);
		screen.gc.fillOval(24*x, 24*y, 48, 48);
		int xOffset = 0;
		int yOffset = 0;
		if(direction == 1) {
			yOffset = -10;
		} else if(direction == 2) {
			yOffset = -8;
			xOffset = 8;
		} else if(direction == 3) {
			xOffset = 10;
		} else if(direction == 4) {
			yOffset = 8;
			xOffset = 8;
		} else if(direction == 5) {
			yOffset = 10;
		} else if(direction == 6) {
			yOffset = 8;
			xOffset = -8;
		} else if(direction == 7) {
			xOffset = -10;
		} else if(direction == 8) {
			yOffset = -8;
			xOffset = -8;
		}
		screen.gc.drawLine((24*x)+25, (24*y)+25, (24*x)+25+xOffset, (24*y)+25+yOffset);
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	@Override
	public String toString() {
		return "Tank [x=" + x + ", y=" + y + ", direction=" + direction + "]";
	}
}