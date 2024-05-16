package Project2D;

import javax.swing.*;		
		
public class GameFrame extends JFrame
{
	public GameFrame()
	{
		setSize(1200,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("GAME");
		setVisible(true);
		
		GamePanel gamePanel = new GamePanel();
		add(gamePanel);
		
		pack();
		
		setLocationRelativeTo(null);
		setResizable(false);
		
		gamePanel.startGameThread();
	}
}