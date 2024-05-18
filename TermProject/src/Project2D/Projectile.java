package Project2D;

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile extends Entity
{
	double dx, dy;
	double speed = 5.0;
	
//	public Projectile(int x, int y) // 원본
//	{
//		this.x = x;
//		this.y = y;
//		this.speed = 5;		
//	}
	
	public Projectile(int startX, int startY, int targetX, int targetY) 
	{
        this.x = startX + 20; // 투사체가 발사되는 좌표
        this.y = startY; // 투사체가 발사되는 좌표
        
        // 방향 계산
        double angle = Math.atan2(targetY - startY, targetX - startX);
        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed;
    }
	
	public void update()
	{
		x += dx;
		y += dy;
		//y -= speed;
	}
	
	public void draw(Graphics2D g2)
	{
		if (g2 == null) { //1217
	        return; // g2가 null이면 작업 중단 
	    }
		
		g2.setColor(Color.red);
		g2.fillRoundRect(x, y, 10, 10, 10, 10);
	}
}
