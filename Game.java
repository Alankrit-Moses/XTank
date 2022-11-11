import java.util.Random;

public class Game {
	
    private Elements grid[][];
	public Game() {
        // If change numbers here, then change in client as well
		grid = new Elements[20][20];
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
				else
					System.out.print('T');
			}
			System.out.println();
		}
	}
}