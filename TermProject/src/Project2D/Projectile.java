package Project2D;

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile extends Entity
{
	public Projectile(int x, int y)
	{
		this.x = x + 20;
		this.y = y;
		this.speed = 5;
	}
	
	public void update()
	{
		y -= speed;
	}
	
	public void draw(Graphics2D g2)
	{
		g2.setColor(Color.red);
		g2.fillRoundRect(x, y, 10, 10, 10, 10);
	}
}
