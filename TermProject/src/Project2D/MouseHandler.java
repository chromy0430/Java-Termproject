package Project2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter 
{
	public boolean mousePressed;
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		mousePressed = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		mousePressed = false;
	}
}