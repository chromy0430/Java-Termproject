package Project2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.*;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;

	public Player(GamePanel gp, KeyHandler keyH) {

		this.gp = gp;
		this.keyH = keyH;

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {

		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage() {
		try {

			// 위로 걷기 사진
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/w1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/w2.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/player/w3.png"));
			up4 = ImageIO.read(getClass().getResourceAsStream("/player/w4.png"));
			up5 = ImageIO.read(getClass().getResourceAsStream("/player/w5.png"));
			up6 = ImageIO.read(getClass().getResourceAsStream("/player/w6.png"));
			up7 = ImageIO.read(getClass().getResourceAsStream("/player/w7.png"));

			// 왼쪽으로 걷기 사진
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/a1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/a2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/player/a3.png"));
			left4 = ImageIO.read(getClass().getResourceAsStream("/player/a4.png"));
			left5 = ImageIO.read(getClass().getResourceAsStream("/player/a5.png"));
			left6 = ImageIO.read(getClass().getResourceAsStream("/player/a6.png"));
			left7 = ImageIO.read(getClass().getResourceAsStream("/player/a7.png"));

			// 아래로 걷기 사진
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/s1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/s2.png"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/player/s3.png"));
			down4 = ImageIO.read(getClass().getResourceAsStream("/player/s4.png"));
			down5 = ImageIO.read(getClass().getResourceAsStream("/player/s5.png"));
			down6 = ImageIO.read(getClass().getResourceAsStream("/player/s6.png"));
			down7 = ImageIO.read(getClass().getResourceAsStream("/player/s7.png"));

			// 오른쪽으로 걷기 사진
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/d1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/d2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/player/d3.png"));
			right4 = ImageIO.read(getClass().getResourceAsStream("/player/d4.png"));
			right5 = ImageIO.read(getClass().getResourceAsStream("/player/d5.png"));
			right6 = ImageIO.read(getClass().getResourceAsStream("/player/d6.png"));
			right7 = ImageIO.read(getClass().getResourceAsStream("/player/d7.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {

		// 키 누르는 프레임 끊김 현상 해결
		// 각 방향키가 눌렸을 때 플레이어 위치 업데이트
//		if (keyH.upPressed == true) // W키 눌리면
//		{
//			direction = "up";
//			y -= speed;
//		} else if (keyH.downPressed == true) // S키 눌리면
//		{
//			direction = "down";
//			y += speed;
//		} else if (keyH.leftPressed == true) // A키 눌리면
//		{
//			direction = "left";
//			x -= speed;
//		} else if (keyH.rightPressed == true) // D키 눌리면
//		{
//			direction = "right";
//			x += speed;
//		}
		// 대각선 구현완료
		if (keyH.upPressed == true || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

			if (keyH.upPressed == true) // W키 눌리면
			{
				direction = "up";
				y -= speed;
			}
			if (keyH.downPressed == true) // S키 눌리면
			{
				direction = "down";
				y += speed;
			}
			if (keyH.leftPressed == true) // A키 눌리면
			{
				direction = "left";
				x -= speed;
			}
			if (keyH.rightPressed == true) // D키 눌리면
			{
				direction = "right";
				x += speed;
			}

			spriteCounter++;
			if (spriteCounter > 10) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 3;
				} else if (spriteNum == 3) {
					spriteNum = 4;
				} else if (spriteNum == 4) {
					spriteNum = 5;
				} else if (spriteNum == 5) {
					spriteNum = 6;
				} else if (spriteNum == 6) {
					spriteNum = 7;
				} else if (spriteNum == 7) {
					spriteNum = 1;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}

	public void draw(Graphics2D g2) {

//		g2.setColor(Color.white);
//		g2.fillRoundRect(playerX, playerY, 48, 48, 48, 48);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		BufferedImage image = null;

		switch (direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			if (spriteNum == 3) {
				image = up3;
			}
			if (spriteNum == 4) {
				image = up4;
			}
			if (spriteNum == 5) {
				image = up5;
			}
			if (spriteNum == 6) {
				image = up6;
			}
			if (spriteNum == 7) {
				image = up7;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			if (spriteNum == 3) {
				image = down3;
			}
			if (spriteNum == 4) {
				image = down4;
			}
			if (spriteNum == 5) {
				image = down5;
			}
			if (spriteNum == 6) {
				image = down6;
			}
			if (spriteNum == 7) {
				image = down7;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			if (spriteNum == 3) {
				image = left3;
			}
			if (spriteNum == 4) {
				image = left4;
			}
			if (spriteNum == 5) {
				image = left5;
			}
			if (spriteNum == 6) {
				image = left6;
			}
			if (spriteNum == 7) {
				image = left7;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			if (spriteNum == 3) {
				image = right3;
			}
			if (spriteNum == 4) {
				image = right4;
			}
			if (spriteNum == 5) {
				image = right5;
			}
			if (spriteNum == 6) {
				image = right6;
			}
			if (spriteNum == 7) {
				image = right7;
			}
			break;
		}
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

	}
}
