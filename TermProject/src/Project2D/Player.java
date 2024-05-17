package Project2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;
	MouseHandler mouseH; // 마우스로부터 입력 받는 클래스 초기화
	
	//추가
	ArrayList<Projectile> projectiles; // 투사체 관리
	private long lastShotTime; // 쿨타임 계산용 시간
	private long shotCooldown = 500; // 0.5초 쿨타임 설정
	

	public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {

		this.gp = gp;
		this.keyH = keyH;
		
		//추가
		this.projectiles = new ArrayList<>();
		this.mouseH = mouseH;
		
		setDefaultValues();
	}

	public void setDefaultValues() {

		x = 100;
		y = 100;
		speed = 4;
		lastShotTime = 0; // 초기화
	}

	public void getPlayerImage() {
		try {

			up1 = ImageIO.read(getClass().getResourceAsStream("/player/golem-walk.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {

		// 키 누르는 프레임 끊김 현상 해결
		// 각 방향키가 눌렸을 때 플레이어 위치 업데이트
//		if (keyH.upPressed == true) // W키 눌리면
//		{
//			y -= speed;
//		} else if (keyH.downPressed == true) // S키 눌리면
//		{
//			y += speed;
//		} else if (keyH.leftPressed == true) // A키 눌리면
//		{
//			x -= speed;
//		} else if (keyH.rightPressed == true) // D키 눌리면
//		{
//			x += speed;
//		}
		// 대각선 구현완료
		if (keyH.upPressed == true) // W키 눌리면
		{
			y -= speed;
		}
		if (keyH.downPressed == true) // S키 눌리면
		{
			y += speed;
		}
		if (keyH.leftPressed == true) // A키 눌리면
		{
			x -= speed;
		}
		if (keyH.rightPressed == true) // D키 눌리면
		{
			x += speed;
		}
		
		long currentTime = System.currentTimeMillis();
		if (keyH.spacePressed && currentTime - lastShotTime >= shotCooldown) // 쿨타임보다 
		{
			projectiles.add(new Projectile(x, y));		// 리스트에 투사체 추가	
			lastShotTime = currentTime; // 마지막 발사시간 갱신
		}
		if (mouseH.mousePressed && currentTime - lastShotTime >= shotCooldown) {
            projectiles.add(new Projectile(x, y));
            lastShotTime = currentTime; // 마지막 발사 시간 갱신
        }
		
		
		
		for (Projectile projectile : projectiles)
		{
			projectile.update();			
		}
		
		projectiles.removeIf(p -> p.y < 0); // 화면밖으로 투사체가 나갈 시 자동으로 삭제
	}

	public void draw(Graphics2D g2) {

		g2.setColor(Color.white);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		//투사체 그리기
		for (Projectile projectile : projectiles)
		{
			projectile.draw(g2);
		}

	}
}
