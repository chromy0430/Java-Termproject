package Project2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	final int originalTileSize = 16; // 16x16 타일
	final int scale = 3; // 타일 크기 배율

	public final int tileSize = originalTileSize * scale; // 48*48 타일 크기
	final int maxScreenCol = 16; // 화면에 표시될 최대 가로 수
	final int maxScreenRow = 12; // 화면에 표시될 최대 세로 수
	final int screenWidth = 1600;//tileSize * maxScreenCol; // 화면 너비 768픽셀
	final int screenHeight = 900;//tileSize * maxScreenRow; // 화면 높이 576픽셀

	KeyHandler keyH = new KeyHandler(); // KeyHandler 객체
	MouseHandler mouseH = new MouseHandler();
	Thread gameThread; // 게임 쓰레드
	Player player = new Player(this, keyH, mouseH);

	// FPS 설정
	int FPS = 60; // 초당 프레임

	// 플레이어 위치 기본값 초기화
	int playerX = 100; // 플레이어 초기 X 좌표
	int playerY = 100; // 플레이어 초기 y 좌표
	int playerSpeed = 4; // 플레이어 이동 속도

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // 패널 크기 설정
		this.setBackground(Color.black); // 배경 색상
		this.setDoubleBuffered(true); // 화면 깜빡임 방지

		this.setFocusable(true); // 이 컴포넌트로부터 먼저 키를 입력 받을 수 있다.
		this.addKeyListener(keyH); // 키 리스너 추가
		this.addMouseListener(mouseH);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this); // 새 쓰레드 생성
		gameThread.start(); // 쓰레드 시작
	}

	public void run() {
		double drawInterval = 1000000000 / FPS; // 0.016666 초 = 0.02초
		double delta = 0;
		long lastTime = System.nanoTime(); // 마지막 시간 기록
		long currentTime;
		long timer = 0; // 1초마다 fps 출력을 위한 타이머
		int drawCount = 0; // 프레임 수

		while (gameThread != null) { // 쓰레드가 실행 중
			currentTime = System.nanoTime(); // 현재 시간 기록

			delta += (currentTime - lastTime) / drawInterval; // 시간 간격 누적
			timer += (currentTime - lastTime); // 타이머 누적
			lastTime = currentTime; // 시간 갱신

			if (delta >= 1) { // 프레임 갱신 간격되면
				update(); // 초당 60프레임 만큼 업데이트
				repaint(); // 업데이트되는 만큼 이미지 재생성
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
		
		player.update();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		player.draw(g2);
		
		g2.dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// 구현 필요 없음
	}
	
	// UPDATE 고쳐서 주석 처리 해놓음
	@Override
	public void keyPressed(KeyEvent e) {
//        int code = e.getKeyCode();
//
//        if (code == KeyEvent.VK_W) {
//            playerY -= playerSpeed;
//        }
//        if (code == KeyEvent.VK_S) {
//            playerY += playerSpeed;
//        }
//        if (code == KeyEvent.VK_A) {
//            playerX -= playerSpeed;
//        }
//        if (code == KeyEvent.VK_D) {
//            playerX += playerSpeed;
//        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// 구현 필요 없음
	}
}
