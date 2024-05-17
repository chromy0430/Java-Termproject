package Project2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
	}
	public void setDefaultValues() {
		
		x = 100;
		y = 100;
		speed = 4;
	}
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/golem-walk.png"));
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {

		// 키 누르는 프레임 끊김 현상 해결
		// 각 방향키가 눌렸을 때 플레이어 위치 업데이트
		if (keyH.upPressed == true) // W키 눌리면
		{
			y -= speed;
		} else if (keyH.downPressed == true) // S키 눌리면
		{
			y += speed;
		} else if (keyH.leftPressed == true) // A키 눌리면
		{
			x -= speed;
		} else if (keyH.rightPressed == true) // D키 눌리면
		{
			x += speed;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		g2.setColor(Color.white);
		// g2.fillRoundRect(playerX, playerY, 48, 48, 48, 48);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
	}
}
