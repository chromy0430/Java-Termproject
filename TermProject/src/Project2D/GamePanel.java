package Project2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements Runnable 
{
	final int originalTileSize = 10; // 16x16 타일
	final int scale = 3; // 타일 크기 배율

	public final int tileSize = originalTileSize * scale; // 48*48 타일 크기
	final int maxScreenCol = 32; // 화면에 표시될 최대 가로 수
	final int maxScreenRow = 18; // 화면에 표시될 최대 세로 수
	final int screenWidth = 1280;//tileSize * maxScreenCol; // 화면 너비 768픽셀
	final int screenHeight = 720;//tileSize * maxScreenRow; // 화면 높이 576픽셀
	
	MouseHandler mouseH = new MouseHandler(); // MouseHandler 객체 생성 17일 22:07 추가
	MouseMotionHandler mouseMotionH = new MouseMotionHandler(); // 18일 12:15 추가
	
	// 5월18일 오후 추가
	ArrayList<Monster> monsters = new ArrayList<>(); 
	long GameStartTime; //레벨업 타이머 추가
	boolean showLevelUp = false; // 레벨업 논리형 추가
	boolean gamePaused = false;
	long lastLevelUpTime;
		
	KeyHandler keyH = new KeyHandler(); // KeyHandler 객체
	Thread gameThread; // 게임 쓰레드
	
	Player player = new Player(this, keyH, mouseH); // 마우스 클릭 매개변수 추가
	
	// FPS 설정
	int FPS = 60; // 초당 프레임

	// 플레이어 위치 기본값 초기화
	int playerX = 100; // 플레이어 초기 X 좌표
	int playerY = 100; // 플레이어 초기 y 좌표
	int playerSpeed = 4; // 플레이어 이동 속도
	
	//몬스터 기본 값초기화
	int MobCount = 5;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // 패널 크기 설정
		this.setBackground(Color.black); // 배경 색상
		this.setDoubleBuffered(true); // 화면 깜빡임 방지

		this.setFocusable(true); // 이 컴포넌트로부터 먼저 키를 입력 받을 수 있다.
		this.addKeyListener(keyH); // 키 리스너 추가
		this.addMouseListener(mouseH);
		this.addMouseMotionListener(mouseMotionH);		
		
		// 생성자 내부에서 몬스터 초기화(18일 오후 추가)
		for (int i = 0; i < MobCount; i++) {
		    int randomX = (int) (Math.random() * (screenWidth - tileSize));
		    int randomY = (int) (Math.random() * (screenHeight - tileSize));
		    monsters.add(new Monster(this, player, randomX, randomY));
		}
	}
	
	public void startGameThread() {
		gameThread = new Thread(this); // 새 쓰레드 생성
		GameStartTime = System.currentTimeMillis(); // 18일 오후 추가
		//lastLevelUpTime = System.currentTimeMillis(); // 18일 오후 추가
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
				if(!gamePaused)
				{
					update();
				}
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

	public void update() 
	{
		player.update();
        
        // 18일 오후 추가
		for (Monster monster : monsters)
		{
			monster.update();
		} 
		if (!showLevelUp && (System.currentTimeMillis() - GameStartTime) >= 10000) 
		{
	       showLevelUp = true;
	       gamePaused = true;	

	    }
//		if (!showLevelUp && System.currentTimeMillis() - lastLevelUpTime >= 10000) {
//	        showLevelUp = true;
//	        gamePaused = true;
//	    }
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		player.draw(g2);
		
		// 조준점 그리기 5월 18일 추가
		g2.setColor(Color.white);
        int crosshairSize = 10;
        int crosshairX = mouseMotionH.mouseX - crosshairSize / 2;
        int crosshairY = mouseMotionH.mouseY - crosshairSize / 2;
        g2.drawOval(crosshairX, crosshairY, crosshairSize, crosshairSize);
		
        // 18일 오후 추가
        for (Monster monster : monsters) {
            monster.draw(g2);
        }
        
        if (showLevelUp) {
            drawLevelUpCards(g2);
        }
        
		g2.dispose();
	}
	
	// 카드 창 표시 기능 (18일 오후 추가)
    private void drawLevelUpCards(Graphics2D g2) {
        int cardWidth = 200;
        int cardHeight = 300;
        int gap = 50;
        int startX = (screenWidth - (cardWidth * 3 + gap * 2)) / 2;
        int startY = (screenHeight - cardHeight) / 2;

        for (int i = 0; i < 3; i++) {
            int x = startX + i * (cardWidth + gap);
            g2.setColor(Color.white);
            g2.fillRect(x, startY, cardWidth, cardHeight);
            g2.setColor(Color.black);
            g2.drawRect(x, startY, cardWidth, cardHeight);
            g2.drawString("Card " + (i + 1), x + cardWidth / 2 - 20, startY + cardHeight / 2);
        }
    }   
    // 카드 체크기능
    private void checkCardClick() {
        if (showLevelUp && mouseH.mousePressed) {
            int cardWidth = 200;
            int cardHeight = 300;
            int gap = 50;
            int startX = (screenWidth - (cardWidth * 3 + gap * 2)) / 2;
            int startY = (screenHeight - cardHeight) / 2;

            for (int i = 0; i < 3; i++) {
                int x = startX + i * (cardWidth + gap);
                if (mouseH.mouseX >= x && mouseH.mouseX <= x + cardWidth && mouseH.mouseY >= startY && mouseH.mouseY <= startY + cardHeight) {
                	showLevelUp = false;
                    gamePaused = false; // 게임 재개
                    mouseH.mousePressed = false; // 마우스 클릭 상태 초기화
                    GameStartTime= System.currentTimeMillis();
                    //lastLevelUpTime = System.currentTimeMillis();
                    System.out.println("Card " + (i + 1) + " selected");                    
                    // 카드 효과 적용 로직 추가
                    break;
                }
            }
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintComponent(g);
        checkCardClick();
    }

}
