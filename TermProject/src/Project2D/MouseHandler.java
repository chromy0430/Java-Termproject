package Project2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter 
{
	public boolean mousePressed;
	public int mouseX, mouseY;
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		mousePressed = true;
		mouseX = e.getX();
		mouseY = e.getY();		
	}
	
	
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		mousePressed = false;
	}
}