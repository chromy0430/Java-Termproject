package Project2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.image.*;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;

	// 22:07 추가
	MouseHandler mouseH; // 마우스로부터 입력 받는 클래스 초기화 22:07 추가
	ArrayList<Projectile> projectiles; // 투사체 관리
	private long lastShotTime; // 쿨타임 계산용 시간
	private long shotCooldown = 500; // 0.5초 쿨타임 설정
	

	public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {

		this.gp = gp;
		this.keyH = keyH;

		// 22:07 추가
		this.mouseH = mouseH;
		this.projectiles = new ArrayList<>();

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {

		x = 100;
		y = 100;
		speed = 4;
		direction = "down";

		lastShotTime = 0; // 쿨타임 계산용 22:07 추가
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
		
		// 수평 이동 여부를 확인하기 위한 플래그
	    boolean movingHorizontally = false;
	    // 수직 이동 여부를 확인하기 위한 플래그
	    boolean movingVertically = false;

		// 플레이어가 어느 방향이든 이동 중인 경우
		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true) {
			// 위로 이동 중인 경우
			if (keyH.upPressed == true) {
				direction = "up";
				movingVertically = true;
			}
			// 아래로 이동 중인 경우
			if (keyH.downPressed == true) {
				direction = "down";
				 movingVertically = true;
			}
			// 왼쪽으로 이동 중인 경우
			if (keyH.leftPressed == true) {
				direction = "left";
				movingHorizontally = true;
			}
			// 오른쪽으로 이동 중인 경우
			if (keyH.rightPressed == true) {
				direction = "right";
				movingHorizontally = true;
			}

			// 기본 속도를 임시 변수에 저장
			double adjustedSpeed = speed;
			// 수평 및 수직으로 동시에 이동 중인 경우 (대각선 이동)
			if (movingHorizontally && movingVertically) {
	            // 속도를 조정하여 대각선 이동 시 과도한 속도 증가 방지
	            adjustedSpeed = speed / Math.sqrt(2);
	        }			
			
			// 위로 이동
			if (keyH.upPressed) {
				y -= adjustedSpeed;
			}
			// 아래로 이동
			if (keyH.downPressed) {
				y += adjustedSpeed;
			}
			// 왼쪽으로 이동
			if (keyH.leftPressed) {
				x -= adjustedSpeed;
			}
			// 오른쪽으로 이동
			if (keyH.rightPressed) {
				x += adjustedSpeed;
			}

			// 스프라이트 카운터 증가
			spriteCounter++;
			// 스프라이트 카운터가 일정 수를 초과하면
			if (spriteCounter > 10) {
				// 스프라이트 번호 증가
				spriteNum++;
				// 스프라이트 번호가 7을 초과하면 다시 1로 리셋
				if (spriteNum == 7) {
					spriteNum = 1;
				}
				// 스프라이트 카운터 리셋
				spriteCounter = 0;
			}
		} else {
			spriteNum = 1;
		}

		long currentTime = System.currentTimeMillis(); // 현재 시간
		if (mouseH.mousePressed && currentTime - lastShotTime >= shotCooldown) {	
			
			// 마우스 커서 위치쪽을 향해 투사체 발사
			projectiles.add(new Projectile(x, y, gp.mouseMotionH.mouseX, gp.mouseMotionH.mouseY));         
			
			//projectiles.add(new Projectile(x, y)); // 원본
			
			lastShotTime = currentTime; // 마지막 발사 시간 갱신
		}

		for (Projectile projectile : projectiles) {
			projectile.update();
		}

		//projectiles.removeIf(p -> p.y < 0); // 원본 화면밖으로 투사체가 나갈 시 자동으로 삭제
		
		// 화면 밖으로 나간 투사체 제거 (단순히 화면 위로 나간 경우만 체크)
        projectiles.removeIf(p -> p.y < 0 || p.y > gp.screenHeight || p.x < 0 || p.x > gp.screenWidth);
    
	}

	public void draw(Graphics2D g2) {

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

		// 투사체 그리기
		for (Projectile projectile : projectiles) {
			projectile.draw(g2);
		}

	}
}
