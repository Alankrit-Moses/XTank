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

    public synchronized Elements[][] getGrid()
    {
        return grid;
    }

    public void setGrid(Elements[][] grid)
    {
        this.grid = grid;
    }

	public void printGrid() {
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

	public void removeBullets() {
		for(int x=0;x<20;x++)
		{
			for(int y=0;y<20;y++)
			{
				if(grid[x][y] instanceof Bullet)
					grid[x][y] = null;
			}
			System.out.println();
		}
	}
}