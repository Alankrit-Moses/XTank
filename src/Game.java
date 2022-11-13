/**
 * File: Game.java
 * Assignment: CSC335PA3
 * @author Alankrit Moses
 *
 * Description: This is the Game class. It contains the main grid of the game and is responsible 
 * for managing the bullets.
 */
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
	
    private Elements grid[][];
	public Game() {
        // If change numbers here, then change in client as well
		grid = new Elements[20][20];
		
		for(int x1=0;x1<20;x1++)
		{
			int x = ThreadLocalRandom.current().nextInt(0,20);
			int y = ThreadLocalRandom.current().nextInt(0,20);
			while(grid[y][x]!=null)
			{
				x = ThreadLocalRandom.current().nextInt(0,20);	
				y = ThreadLocalRandom.current().nextInt(0,20);
			}
			grid[y][x] = new Wall(x,y,1,1);
		}
	}

	/**
	 * Getter for grid
	 */
    public synchronized Elements[][] getGrid()
    {
        return grid;
    }

	/**
     * Setter for grid
     */
    public synchronized void setGrid(Elements[][] grid)
    {
        this.grid = grid;
    }

	public synchronized void printGrid() {
		for(int x=0;x<20;x++)
		{
			for(int y=0;y<20;y++)
			{
				if(grid[x][y]==null)
					System.out.print('.');
				else if(grid[x][y] instanceof Tank)
					System.out.print('T');
				else if(grid[x][y] instanceof Wall)
					System.out.print('W');
				else
					System.out.print('B');
			}
			System.out.println();
		}
	}

	/**
     * Removes the bullet when it hits a tank or a wall.
     */
	public synchronized void removeBullets() {
		for(int x=0;x<20;x++)
		{
			for(int y=0;y<20;y++)
			{
				if(grid[x][y] instanceof Bullet)
					grid[x][y] = null;
				else if(grid[x][y] instanceof Tank)
				{
					if(((Tank)grid[x][y]).isdestroyed())
							grid[x][y] = null;
				}
			}
			System.out.println();
		}
	}
}
