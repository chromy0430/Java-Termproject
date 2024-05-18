package Project2D;

import java.awt.Color;
import java.awt.Graphics2D;

public class Monster extends Entity
{
	GamePanel gp;
	Player pl;
	// 추적속도
	int Tspeed = 3;
	
	public Monster(GamePanel gp, Player pl, int x, int y)
	{
		this.gp = gp;
		this.pl = pl;
		this.x = x;
		this.y = y;
	}
	
	public void update()
	{
		// 플레이어와 몬스터 간의 x, y 거리 계산
	    double distanceX = pl.x - this.x;
	    double distanceY = pl.y - this.y;

	    // 거리의 크기 계산
	    double magnitude = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

	    // 거리가 0이 아니면 단위 벡터 계산
	    if (magnitude != 0) {
	        distanceX /= magnitude;
	        distanceY /= magnitude;
	    }
	    
	    double adjustedSpeed = Tspeed;
	    if (Math.abs(distanceX) > 0 && Math.abs(distanceY) > 0) {
	        // 대각선 이동 시 속도 조정
	        adjustedSpeed = Tspeed / Math.sqrt(2);
	    }

	    // 몬스터의 위치 업데이트 (이동 속도는 2로 가정)
	    x += distanceX * Tspeed;
	    y += distanceY * Tspeed;
	}
	
	public void draw(Graphics2D g2)
	{
		g2.setColor(Color.RED);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
	}
}
