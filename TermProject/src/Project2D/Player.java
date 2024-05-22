package Project2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.image.*;

public class Player extends Entity {

	// 이미지 스프라이트 05/22 18:30
	BufferedImage[] upFrames; // 위로 이동 이미지 배열
	BufferedImage[] downFrames; // 아래로 이동 이미지 배열
	BufferedImage[] leftFrames; // 왼쪽으로 이동 이미지 배열
	BufferedImage[] rightFrames; // 오른쪽으로 이동 이미지 배열
	BufferedImage idleFrame; // 멈췄을 때 기본 이미지
	int currentFrame = 0; // 현재 프레임
	int frameCount = 7; // 총 프레임 수
	int frameDelay = 10; // 프레임 간격 (애니메이션 속도 조절)
	int frameCounter = 0; // 프레임 카운터

	GamePanel gp;
	KeyHandler keyH;

//	public final int screenX; //05/22 1:45 수정
//	public final int screenY; //05/22 1:45 수정

	// 5월 22일 수정
	public int x; // 05/22 1:45 수정
	public int y; // 05/22 1:45 수정

	// 19일 16시 추가
	public int exp = 0; // 경험치 변수 추가

	// 22:07 추가
	MouseHandler mouseH; // 마우스로부터 입력 받는 클래스 초기화 22:07 추가
	ArrayList<Projectile> projectiles; // 투사체 관리
	private long lastShotTime; // 쿨타임 계산용 시간
	private long shotCooldown = 500; // 0.5초 쿨타임 설정

	public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {

		this.gp = gp;
		this.keyH = keyH;

//		screenX = gp.screenWidth/2 - (gp.tileSize/2);
//		screenY = gp.screenHeight/2 - (gp.tileSize/2);

		// 22:07 추가
		this.mouseH = mouseH;
		this.projectiles = new ArrayList<>();

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {

		x = gp.tileSize * 20; // 05/22 1:45 수정
		y = gp.tileSize * 11; // 05/22 1:45 수정
//		worldX = gp.tileSize * 23; //05/22 1:45 수정
//		worldY = gp.tileSize * 21; //05/22 1:45 수정
		speed = 2;
		direction = "down";

		lastShotTime = 0; // 쿨타임 계산용 22:07 추가
	}

	public void getPlayerImage() {
		try {
			// 위로 이동 이미지 로드 05/22 18:30
			upFrames = new BufferedImage[7];
			for (int i = 0; i < 7; i++) {
				upFrames[i] = ImageIO.read(getClass().getResourceAsStream("/player/w" + (i + 1) + ".png"));
			}

			// 아래로 이동 이미지 로드 05/22 18:30
			downFrames = new BufferedImage[7];
			for (int i = 0; i < 7; i++) {
				downFrames[i] = ImageIO.read(getClass().getResourceAsStream("/player/s" + (i + 1) + ".png"));
			}

			// 왼쪽으로 이동 이미지 로드 05/22 18:30
			leftFrames = new BufferedImage[7];
			for (int i = 0; i < 7; i++) {
			
				leftFrames[i] = ImageIO.read(getClass().getResourceAsStream("/player/a" + (i + 1) + ".png"));
			}

			// 오른쪽으로 이동 이미지 로드 05/22 18:30
			rightFrames = new BufferedImage[7];
			for (int i = 0; i < 7; i++) {
				rightFrames[i] = ImageIO.read(getClass().getResourceAsStream("/player/d" + (i + 1) + ".png"));
			}

			// 멈췄을 때 기본 이미지 로드 05/22 18:30
			idleFrame = ImageIO.read(getClass().getResourceAsStream("/player/s1.png"));
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
				y -= adjustedSpeed; // 05/22 1:45 수정
//				worldY -= adjustedSpeed;
			}
			// 아래로 이동
			if (keyH.downPressed) {
				y += adjustedSpeed; // 05/22 1:45 수정
//				worldY += adjustedSpeed;
			}
			// 왼쪽으로 이동
			if (keyH.leftPressed) {
				x -= adjustedSpeed; // 05/22 1:45 수정
//				worldX -= adjustedSpeed;
			}
			// 오른쪽으로 이동
			if (keyH.rightPressed) {
				x += adjustedSpeed; // 05/22 1:45 수정
//				worldX += adjustedSpeed;
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
			spriteNum = 0;
		}

		// 프레임 카운터를 증가시킵니다.05/22 18:30
		frameCounter++; 
		// 지정된 프레임 간격마다 현재 프레임을 업데이트합니다. 05/22 18:30
		if (frameCounter >= frameDelay) {
			frameCounter = 0; // 프레임 카운터 초기화
			currentFrame = (currentFrame + 1) % frameCount; // 다음 프레임으로 이동
		}

		long currentTime = System.currentTimeMillis(); // 현재 시간
		if (mouseH.mousePressed && currentTime - lastShotTime >= shotCooldown) {

			// 마우스 커서 위치쪽을 향해 투사체 발사
			projectiles.add(new Projectile(x + 20, y + 20, gp.mouseMotionH.mouseX, gp.mouseMotionH.mouseY));

//			projectiles.add(new Projectile(x, y )); // 원본

			lastShotTime = currentTime; // 마지막 발사 시간 갱신
		}

		for (Projectile projectile : projectiles) {
			projectile.update();
		}

		// projectiles.removeIf(p -> p.y < 0); // 원본 화면밖으로 투사체가 나갈 시 자동으로 삭제

		// 화면 밖으로 나간 투사체 제거 (단순히 화면 위로 나간 경우만 체크)
		projectiles.removeIf(p -> p.y < 0 || p.y > gp.screenHeight || p.x < 0 || p.x > gp.screenWidth); // 05/22 1:45 수정

	}

	public void draw(Graphics2D g2) {

		BufferedImage playerImage = null;

	    if (spriteNum == 0) { // 멈췄을 때 기본 이미지 사용 05/22 18:30
	        playerImage = idleFrame;
	    } else { // 이동 중일 때 애니메이션 프레임 사용 05/22 18:30
	        switch (direction) {
	            case "up":
	                playerImage = upFrames[currentFrame];
	                break;
	            case "down":
	                playerImage = downFrames[currentFrame];
	                break;
	            case "left":
	                playerImage = leftFrames[currentFrame];
	                break;
	            case "right":
	                playerImage = rightFrames[currentFrame];
	                break;
	        }
	    }

		g2.drawImage(playerImage, x, y, gp.tileSize, gp.tileSize, null); // 05/22 1:45 수정

		// 투사체 그리기
		for (Projectile projectile : projectiles) {
			projectile.draw(g2);
		}

	}
}