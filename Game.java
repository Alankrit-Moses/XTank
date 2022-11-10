import java.util.Random;

public class Game {
	
    private Elements grid[][];
	public Game() {
        // If change numbers here, then change in client as well
		grid = new Elements[100][100];
	}

    public Elements[][] getGrid()
    {
        return grid;
    }

    public void setGrid(Elements[][] grid)
    {
        this.grid = grid;
    }
}