import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
//import java.awt.event.PaintEvent;
import java.io.*;

public class Tank extends Elements implements Serializable {
	private int x;
	private int y;
	private int color;
	public int direction;
	public int damage;
	public int health;
	private boolean destroyed;
	
	public Tank(int gridCol, int gridRow, int color, int direction) {
		x = gridCol;
		y = gridRow;
		this.color = color;
		this.direction = direction;
		destroyed = false;
		health = 100;
		damage = 21;
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
		screen.gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
		screen.gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		screen.gc.drawRectangle(50*x, 50*y, 24, 24);
		screen.gc.fillRectangle(50*x, 50*y, 48, 48);
		screen.gc.setForeground(display.getSystemColor(color));
		screen.gc.setBackground(display.getSystemColor(color));
		screen.gc.drawOval(50*x+4, 50*y+4, 40, 40);
		screen.gc.fillOval(50*x+4, 50*y+4, 40, 40);
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
		screen.gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
		screen.gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		screen.gc.drawLine((50*x)+24, (50*y)+24, (50*x)+24+xOffset, (50*y)+24+yOffset);
		screen.gc.drawLine((50*x)+25, (50*y)+24, (50*x)+25+xOffset, (50*y)+24+yOffset);
		screen.gc.drawLine((50*x)+23, (50*y)+24, (50*x)+23+xOffset, (50*y)+24+yOffset);
		//health bar
		screen.gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
		screen.gc.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
		screen.gc.drawRectangle(50*x-1, 50*y-10-1, 52, 6);
		screen.gc.fillRectangle(50*x, 50*y-10,health/2, 5);
	}
	
	public Elements[][] shoot(Elements[][] grid)
	{
		int x1 = x;
		int y1 = y;
		int xadder = 0;
		int yadder = 0;
		Tank hit = null;
		if(direction==8 || direction==1 || direction==2)
			yadder = -1;
		else if(direction==6 || direction==5 || direction==4)
			yadder = 1;
		if(direction==2 || direction==3 || direction==4)
			xadder = 1;
		else if(direction==6 || direction==7 || direction==8)
			xadder = -1;
		System.out.println("direction: "+direction);
		while(true)
		{
			x1+=xadder;
			y1+=yadder;
			if(x1>19 || x1<0 || y1>19 || y1<0)
			{
				break;
			}
			if(grid[y1][x1] instanceof Tank)
			{
				hit = (Tank)grid[y1][x1];
				hit.decrementHealth(this.damage);
				if(hit.health<=0)
				{
					hit.destroyed = true;
					grid[y1][x1] = null;
				}
				break;
			}
			else if(grid[y1][x1] instanceof Wall)
				break;
			else
			{
				grid[y1][x1] = new Bullet(x1,y1);
			}
		}
		return grid;
	}
	
	public void decrementHealth(int damage) {
		this.health-=damage;
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

	public boolean isdestroyed() {
		return destroyed;
	}
}