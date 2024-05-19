package Project2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	final int originalTileSize = 16; // 16x16 타일
	final int scale = 3; // 타일 크기 배율

	public final int tileSize = originalTileSize * scale; // 48*48 타일 크기
	public final int maxScreenCol = 16; // 화면에 표시될 최대 가로 수
	public final int maxScreenRow = 12; // 화면에 표시될 최대 세로 수
	public final int screenWidth = tileSize * maxScreenCol; // 화면 너비 768픽셀
	public final int screenHeight = tileSize * maxScreenRow; // 화면 높이 576픽셀

	MouseHandler mouseH = new MouseHandler(); // MouseHandler 객체 생성 17일 22:07 추가
	MouseMotionHandler mouseMotionH = new MouseMotionHandler(); // 18일 12:15 추가

	// 월드 세팅
	public final int maxWorldCol = 50; // 월드의 최대 가로 수
	public final int maxWorldRow = 50; // 월드의 최대 세로 수
	public final int worldWidth = tileSize * maxWorldCol; // 월드 너비
	public final int worldHeight = tileSize * maxWorldRow; // 월드 높이

	// 5월 18일 오후 추가
	ArrayList<Monster> monsters = new ArrayList<>(); // 몬스터 리스트
	long GameStartTime; // 게임 시작 시간
	boolean showLevelUp = false; // 레벨업 표시 여부
	boolean gamePaused = false; // 게임 일시정지 여부
	long lastLevelUpTime; // 마지막 레벨업 시간

	ArrayList<Projectile> projectiles = new ArrayList<>();
	CardEffect Effect = new CardEffect();

	TileManager tileM = new TileManager(this); // 타일 매니저 객체 생성 0519 추가
	KeyHandler keyH = new KeyHandler(); // 키 핸들러 객체 생성
	Thread gameThread; // 게임 쓰레드

	public Player player = new Player(this, keyH, mouseH); // 플레이어 객체 생성, 마우스 클릭 매개변수 추가

	// FPS 설정
	int FPS = 60; // 초당 프레임 수

	// 플레이어 위치 기본값 초기화
	int playerX = 100; // 플레이어 초기 X 좌표
	int playerY = 100; // 플레이어 초기 Y 좌표
	int playerSpeed = 4; // 플레이어 이동 속도

	// 몬스터 기본 값 초기화
	int MobCount = 5; // 몬스터 수
	
	// 이미지 변수 선언
    BufferedImage cardImage1, cardImage2, cardImage3; // 19일 오후 추가
    private BufferedImage A1, A2, A3, A4;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // 패널 크기 설정
		this.setBackground(Color.black); // 배경 색상 설정
		this.setDoubleBuffered(true); // 더블 버퍼링 설정

		this.setFocusable(true); // 이 컴포넌트로부터 먼저 키 입력 받을 수 있도록 설정
		this.addKeyListener(keyH); // 키 리스너 추가
		this.addMouseListener(mouseH); // 마우스 리스너 추가
		this.addMouseMotionListener(mouseMotionH); // 마우스 모션 리스너 추가

		try {
			A1 = cardImage1 = ImageIO.read(getClass().getResourceAsStream("/card/Card1.png"));
			A2 = cardImage2 = ImageIO.read(getClass().getResourceAsStream("/card/Card2.png"));
			A3 = cardImage3 = ImageIO.read(getClass().getResourceAsStream("/card/Card3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 생성자 내부에서 몬스터 초기화 (18일 오후 추가)
		for (int i = 0; i < MobCount; i++) {
			int randomX = (int) (Math.random() * (screenWidth - tileSize)); // 랜덤 X 좌표
			int randomY = (int) (Math.random() * (screenHeight - tileSize)); // 랜덤 Y 좌표
			monsters.add(new Monster(this, player, randomX, randomY)); // 몬스터 추가
		}
	}

	public void startGameThread() {
		gameThread = new Thread(this); // 새 쓰레드 생성
		GameStartTime = System.currentTimeMillis(); // 게임 시작 시간 기록 (18일 오후 추가)
		// lastLevelUpTime = System.currentTimeMillis(); // 마지막 레벨업 시간 기록 (18일 오후 추가)
		gameThread.start(); // 쓰레드 시작
	}

	public void run() {
		double drawInterval = 1000000000 / FPS; // 프레임 간격 계산 (0.016666초 = 0.02초)
		double delta = 0; // 시간 간격 누적 변수
		long lastTime = System.nanoTime(); // 마지막 시간 기록
		long currentTime; // 현재 시간
		long timer = 0; // 1초마다 FPS 출력을 위한 타이머
		int drawCount = 0; // 프레임 수

		while (gameThread != null) { // 쓰레드가 실행 중일 때
			currentTime = System.nanoTime(); // 현재 시간 기록

			delta += (currentTime - lastTime) / drawInterval; // 시간 간격 누적
			timer += (currentTime - lastTime); // 타이머 누적
			lastTime = currentTime; // 시간 갱신

			if (delta >= 1) { // 프레임 갱신 간격이 되었을 때
				if (!gamePaused) { // 게임이 일시정지되지 않았을 때
					update(); // 업데이트 호출
				}
				repaint(); // 업데이트된 만큼 화면 다시 그리기
				delta--; // 누적된 간격 감소
				drawCount++; // 프레임 수 증가
			}

			if (timer >= 1000000000) { // 1초가 지났을 때
				System.out.println("FPS : " + drawCount); // FPS 출력

				drawCount = 0; // 프레임 수 초기화
				timer = 0; // 타이머 초기화
			}

		}
	}

	public void update() {
		player.update(); // 플레이어 업데이트

		// 몬스터 업데이트 (18일 오후 추가)
		for (Monster monster : monsters) {
			monster.update(); // 몬스터 업데이트
		}

		for (Projectile projectile : projectiles) {
			projectile.update();
		}

		if (!showLevelUp && (System.currentTimeMillis() - GameStartTime) >= 10000) { // 10초가 지났을 때
			showLevelUp = true; // 레벨업 표시
			gamePaused = true; // 게임 일시정지

		}
//		if (!showLevelUp && System.currentTimeMillis() - lastLevelUpTime >= 10000) {
//	        showLevelUp = true;
//	        gamePaused = true;
//	    }
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // 부모 클래스의 paintComponent 호출
		Graphics2D g2 = (Graphics2D) g; // Graphics2D 객체로 변환

		tileM.draw(g2); // 타일 그리기 0519

		player.draw(g2); // 플레이어 그리기

		// 조준점 그리기 5월 18일 추가
		g2.setColor(Color.white); // 조준점 색상 설정
		int crosshairSize = 10; // 조준점 크기
		int crosshairX = (int) (mouseMotionH.mouseX - crosshairSize / 2); // 조준점 X 좌표
		int crosshairY = (int) (mouseMotionH.mouseY - crosshairSize / 2); // 조준점 Y 좌표
		g2.drawOval(crosshairX, crosshairY, crosshairSize, crosshairSize); // 조준점 그리기

		// 몬스터 그리기 (18일 오후 추가)
		for (Monster monster : monsters) {
			monster.draw(g2); // 몬스터 그리기
		}

		if (showLevelUp) { // 레벨업 표시가 true일 때
			drawLevelUpCards(g2); // 레벨업 카드 그리기
		}

		g2.dispose(); // Graphics2D 객체 해제
	}

	// 카드 창 표시 기능 (18일 오후 추가)
	private void drawLevelUpCards(Graphics2D g2) {
		int cardWidth = 200; // 카드 너비
		int cardHeight = 300; // 카드 높이
		int gap = 50; // 카드 사이의 간격
		int startX = (screenWidth - (cardWidth * 3 + gap * 2)) / 2; // 시작 X 좌표
		int startY = (screenHeight - cardHeight) / 2; // 시작 Y 좌표
		
		BufferedImage[] cardImages = {cardImage1, cardImage2, cardImage3};

		for (int i = 0; i < 3; i++) {
			int x = startX + i * (cardWidth + gap);
			g2.setColor(Color.white);
			g2.fillRect(x, startY, cardWidth, cardHeight);
			g2.setColor(Color.black);
			g2.drawRect(x, startY, cardWidth, cardHeight);
			// g2.drawString("Card " + (i + 1), x + cardWidth / 2 - 20, startY + cardHeight
			// / 2);
			// 이미지 그리기 19일 오후 추가
			if (cardImages[i] != null) {
				g2.drawImage(cardImages[i], x, startY, cardWidth, cardHeight, null);
			} else {
				g2.drawString("카드 이미지 불러오기 실패", x + cardWidth / 2 - 20, startY + cardHeight / 2);
			}
		}
	}

	// 카드 클릭 체크 기능
	private void checkCardClick() {
		if (showLevelUp && mouseH.mousePressed) {
			int cardWidth = 200;
			int cardHeight = 300;
			int gap = 50;
			int startX = (screenWidth - (cardWidth * 3 + gap * 2)) / 2;
			int startY = (screenHeight - cardHeight) / 2;

			for (int i = 0; i < 3; i++) {
				int x = startX + i * (cardWidth + gap);
				if (mouseH.mouseX >= x && mouseH.mouseX <= x + cardWidth && mouseH.mouseY >= startY
						&& mouseH.mouseY <= startY + cardHeight) {
					showLevelUp = false;
					gamePaused = false; // 게임 재개
					mouseH.mousePressed = false; // 마우스 클릭 상태 초기화
					GameStartTime = System.currentTimeMillis();
					// lastLevelUpTime = System.currentTimeMillis();
					System.out.println("Card " + (i + 1) + " selected");
					// 카드 효과 적용 로직 추가
					// break;
					// 카드 효과 적용 19일 오후 추가
					switch (CardEffect.cardEffect.values()[i]) {
					case A1:
						playerSpeed += 5;
						System.out.println("현재 속도는 " + playerSpeed);
						break;
					case A2:
						playerSpeed -= 10;
						System.out.println("현재 속도는 " + playerSpeed);
						break;
					case A3:
						playerSpeed = 20;
						System.out.println("현재 속도는 " + playerSpeed);
						break;
					default:
						// 예외 처리
						System.out.println("현재 속도는 " + playerSpeed);
						break;
					}

					break;
				}
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g); // 부모 클래스의 paint 호출
		paintComponent(g); // paintComponent 호출
		checkCardClick(); // 카드 클릭 체크 호출
	}

}
