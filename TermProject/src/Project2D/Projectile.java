package Project2D;

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile extends Entity
{
	GamePanel gp;
	double dx, dy;
	double speed = 6.5;
	boolean active;
	
	public Projectile(GamePanel gp, int x, int y) // 원본
	{
		this.gp = gp;
		this.x = x;
		this.y = y;
		this.active = true;
	}
	
	public Projectile(int startX, int startY, float targetX, float targetY) // 5월 18일 오전 추가
	{
		this.x = startX;
	    this.y = startY;

//	    // 발사 지점과 조준점 간의 벡터 계산
//	    Vector2f projectilePos = new Vector2f((float) startX, (float) startY);
//	    Vector2f targetPos = new Vector2f(targetX, targetY);
//	    Vector2f directionVector = new Vector2f(targetPos.x - projectilePos.x, targetPos.y - projectilePos.y);
//
//	    // 벡터의 각도 계산 (라디안 값)
//	    float angle = (float) Math.atan2(directionVector.y, directionVector.x);
//
//	    // 라디안 값을 degree 값으로 변환 (0 ~ 360도)
//	    float angleInDegrees = (float) Math.toDegrees(angle);
//	    if (angleInDegrees < 0) {
//	        angleInDegrees += 360;
//	    }
//
//	    // 각도를 기반으로 dx, dy 계산
//	    this.dx = Math.cos(Math.toRadians(angleInDegrees)) * speed;
//	    this.dy = Math.sin(Math.toRadians(angleInDegrees)) * speed;
////        this.x = startX + 20; // 투사체가 발사되는 좌표
////        this.y = startY; // 투사체가 발사되는 좌표
//        
        // 방향 계산
       double angle = Math.atan2(targetY - startY, targetX - startX);
        //angle = Math.max(-Math.PI / 2, Math.min(Math.PI / 2, angle)); //한쪽방향으로 180도만 발사가능         
        
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
	
	public boolean isCollidingWith(Monster monster) 
	{
        return x < monster.x + gp.tileSize &&
               x + 10 > monster.x &&
               y < monster.y + gp.tileSize &&
               y + 10 > monster.y;
    }
}
