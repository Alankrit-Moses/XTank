import java.util.Scanner;
import org.eclipse.swt.SWT;

public class Main {
	
	public static void main(String args[]) {
		Elements[][] grid = new Elements[60][60];
		Tank t = new Tank(1, 2, SWT.COLOR_BLACK, 2);
		grid[2][1] = t;
		printGrid(grid);
		Scanner sc = new Scanner(System.in);
		   
        
        char c = sc.next().charAt(0);
		while(c != 'e') {
			if(c == 'f' || c == 'b') {
				grid = t.move(grid, c);
				printGrid(grid);
			}
			else if(c == 'l' || c == 'r') {
				t.changeDirection(c);
				System.out.println(t.direction);
			}
			c = sc.next().charAt(0);
		}
	}
	
	public static void printGrid(Elements[][] grid) {
		System.out.println("\f");
		for(int i = 0; i < 60; i++) {
			for(int j = 0; j < 60; j++) {
				if (grid[i][j] == null)
					System.out.print("n ");
				else
					System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}
}
