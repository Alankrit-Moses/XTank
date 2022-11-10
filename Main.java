import java.util.Scanner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {
	private static Display display;
    private static Shell shell;
    private static Canvas canvas;
	
	public static void main(String args[]) {
		Display.setAppName("Test");
        display = new Display();
        shell = new Shell(display);
        shell.setText("Test");
        shell.setSize(600, 600);
        shell.setLayout(new GridLayout());
        Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
        canvas = new Canvas(upperComp, SWT.NONE);
        canvas.setSize(600, 600);
        
		Elements[][] grid = new Elements[60][60];
		Tank t = new Tank(1, 2, SWT.COLOR_BLACK, 2);
		grid[2][1] = t;
		printGrid(grid);
		Scanner sc = new Scanner(System.in);
		   
        
        char c = sc.next().charAt(0);
		while(c != 'e') {
			if(c == 'f' || c == 'b') {
				t.move(grid, c);
				draw(canvas, grid);
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
	
	public static void draw(Canvas canvas, Elements[][] grid) {
		
	}
}
