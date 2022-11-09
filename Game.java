import java.util.Random;

import org.eclipse.swt.SWT;

public class Game {
	private Elements[][] grid;
	
	public Game() {
		grid = new Elements[60][60];
	}
	
	public boolean move(char dir, Tank tank) {
		if(dir == 'f') {
			if(tank.direction == 1 && tank.y > 0 && grid[tank.y-1][tank.x] == null) {
				grid[tank.y][tank.x] = null;
				tank.y--;
				grid[tank.y][tank.x] = tank;
			} else if(tank.direction == 2 && tank.y > 0 && tank.x < 59 && grid[tank.y-1][tank.x+1] == null) {
				grid[tank.y][tank.x] = null;
				tank.y--;
				tank.x++;
				grid[tank.y][tank.x] = tank;
			} else if(tank.direction == 3 && tank.x < 59 && grid[tank.y][tank.x+1] == null) {
				grid[tank.y][tank.x] = null;
				tank.x++;
				grid[tank.y][tank.x] = tank;
			} else if(tank.direction == 4 && tank.y < 59 && tank.x < 59 && grid[tank.y+1][tank.x+1] == null) {
				grid[tank.y][tank.x] = null;
				tank.y++;
				tank.x++;
				grid[tank.y][tank.x] = tank;
			} else if(tank.direction == 5 && tank.y < 59 && grid[tank.y+1][tank.x] == null) {
				grid[tank.y][tank.x] = null;
				tank.y++;
				grid[tank.y][tank.x] = tank;
			} else if(tank.direction == 6 && tank.y < 59 && tank.x > 0 && grid[tank.y+1][tank.x-1] == null) {
				grid[tank.y][tank.x] = null;
				tank.y++;
				tank.x--;
				grid[tank.y][tank.x] = tank;
			} else if(tank.direction == 7 && tank.x > 0 && grid[tank.y][tank.x-1] == null) {
				grid[tank.y][tank.x] = null;
				tank.x--;
				grid[tank.y][tank.x] = tank;
			} else if(tank.direction == 8 && tank.y > 0 && tank.x > 0 && grid[tank.y-1][tank.x-1] == null) {
				grid[tank.y][tank.x] = null;
				tank.y--;
				tank.x--;
				grid[tank.y][tank.x] = tank;
			}
		}
		return false;
	}
	
	public void addTank() {
		Random rand = new Random();
		int x = rand.nextInt(60);
		int y = rand.nextInt(60);
		while(grid[y][x] != null) {
			x = rand.nextInt(60);
			y = rand.nextInt(60);
		}
		Tank t = new Tank(x, y, SWT.COLOR_BLUE, 1);
		grid[y][x] = t;
	}
}
