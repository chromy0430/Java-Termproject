package Project2D;

import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.*;		
		
public class GameFrame extends JFrame
{
	public GameFrame()
	{
		setSize(1600,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("GAME");
		setVisible(true);
		
		GamePanel gamePanel = new GamePanel();
		add(gamePanel);
		
		pack();
		
		//마우스 커서 삭제
		//this.setCursor(this.getToolkit().createCustomCursor( new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
		setLocationRelativeTo(null);
		setResizable(false);
		
		gamePanel.startGameThread();
		gamePanel.requestFocus();
	}
}