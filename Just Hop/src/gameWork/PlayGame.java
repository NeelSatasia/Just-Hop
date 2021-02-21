package gameWork;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayGame {
	
	private JFrame gameFrame;
	private GamePanel gamePanel;
	private KeyListener ballControlKeys;
	
	public PlayGame() {
		gameFrame = new JFrame("Just Hop");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setBounds(50, 50, 800, 600);
		gameFrame.setVisible(true);
		
		gamePanel = new GamePanel();
		gameFrame.add(gamePanel);
		
		gamePanel.revalidate();
		gamePanel.repaint();
		
		ballControlKeys = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					gamePanel.changeBallHorizontalDirection("right");
					gamePanel.horizontalKeysAction(true);
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					gamePanel.changeBallHorizontalDirection("left");
					gamePanel.horizontalKeysAction(true);
				}
				if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
					gamePanel.makeBallJump();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(gamePanel.isFrictionlessMode()) {
					gamePanel.stopBallSlowly(true);
				} /*else if (gamePanel.isBallJumping == false && gamePanel.isBallFalling == false) {
					gamePanel.changeBallHorizontalDirection("none");
				}*/
				if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					gamePanel.horizontalKeysAction(false);
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					gamePanel.horizontalKeysAction(false);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		};
		
		gameFrame.addKeyListener(ballControlKeys);
		
		gameFrame.setResizable(false);
	}
}
