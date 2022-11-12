import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;

public class Bullet extends Elements implements Serializable{

	int x,y;
	public boolean drawable; 
	public Bullet(int x, int y)
	{
		this.x = x;
		this.y = y;
		drawable = true;
	}
	
	@Override
	public void draw(PaintEvent screen, Display display) {
		screen.gc.drawOval(50*x+15, 50*y+15, 20, 20);
		screen.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_YELLOW));
		screen.gc.fillOval(50*x+16, 50*y+16, 19, 19);
	}
	
}
