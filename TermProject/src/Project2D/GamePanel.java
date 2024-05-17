package Project2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener
{
	final int originalTileSize = 16; // 16x16 타일
	final int scale = 3;
	
	final int tileSize = originalTileSize * scale; // 48*48타일
	final int maxScreenCol = 16; // 
	final int maxScreenRow = 12; // 
	final int screenWidth = tileSize * maxScreenCol; // 768 픽셀
	final int screenHeight = tileSize * maxScreenRow; // 576픽셀 
	
	//KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	// FPS 설정
	int FPS = 60;
	
	// 플레이어 위치 기본값 초기화
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 10;
	//test
	//2번째 테스트
	//3rd test
	
	public GamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		this.setFocusable(true); // 이 컴포넌트로부터 먼저 키를 입력 받을 수 있다.
		this.addKeyListener(this);
	}
	
	
	
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void run()
	{
		double drawInterval = 1000000000/FPS; //0.016666 초 = 0.02초
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread !=null)
		{
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1)
			{
				update(); // 초당 60프레임 만큼 업데이트			
				repaint(); // 업데이트되는 만큼 이미지 재생성
				delta--;
			}
			
			if(timer >= 1000000000)
			{
				System.out.println("FPS : " + drawCount);
				
				drawCount = 0;
				timer = 0;
			}
			
		}
	}
	
	public void update()
	{	    
//		if(keyH.upPressed == true)
//		{
//			System.out.println("pressed W");
//			playerY += playerSpeed;
//			//playerY = playerY - playerSpeed;
//		}
//		else if(keyH.downPressed == true)
//		{
//			playerY -= playerSpeed;
//		}
//		else if(keyH.leftPressed == true)
//		{
//			playerX -= playerSpeed;
//		}
//		else if(keyH.rightPressed == true)
//		{
//			playerX += playerSpeed;
//		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.white);
		//g2.fillRoundRect(playerX, playerY, 48, 48, 48, 48);
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose();
	}
	
	@Override
    public void keyTyped(KeyEvent e) {
        // 구현 필요 없음
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            playerY -= playerSpeed;
        }
        if (code == KeyEvent.VK_S) {
            playerY += playerSpeed;
        }
        if (code == KeyEvent.VK_A) {
            playerX -= playerSpeed;
        }
        if (code == KeyEvent.VK_D) {
            playerX += playerSpeed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 구현 필요 없음
    }
}
