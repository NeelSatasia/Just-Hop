package gameWork;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayGame extends JFrame {
	
	GamePanel gamePanel;
	KeyListener ballControlKeys;
	
	public PlayGame() {
		new JFrame("Block To Block");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 800, 600);
		setVisible(true);
		
		gamePanel = new GamePanel();
		add(gamePanel);
		
		gamePanel.revalidate();
		gamePanel.repaint();
		
		ballControlKeys = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)) {
					gamePanel.changeBallHorizontalSpeed(4);
					gamePanel.isRightKeyDown = true;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					gamePanel.changeBallHorizontalSpeed(-4);
					gamePanel.isLeftKeyDown = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
					gamePanel.makeBallJump();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_B) {
					gamePanel.ballShootBullets(true);
				}
				
				if(e.getKeyCode() == KeyEvent.VK_N) {
					gamePanel.usingAbility(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					gamePanel.isRightKeyDown = false;
					if(gamePanel.ball.isFlying) {
						gamePanel.ballHorizontalSpeed = 0;
					}
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					gamePanel.isLeftKeyDown = false;
					if(gamePanel.ball.isFlying) {
						gamePanel.ballHorizontalSpeed = 0;
					}
				}
				
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					if(gamePanel.pause == false) {
						gamePanel.pauseTheGame(true);
					} else if(gamePanel.pause) {
						gamePanel.pauseTheGame(false);
					}
				}
				
				if(e.getKeyCode() == KeyEvent.VK_B) {
					gamePanel.ballShootBullets(false);
				}
				
				if(e.getKeyCode() == KeyEvent.VK_N) {
					gamePanel.usingAbility(false);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		};
		
		addKeyListener(ballControlKeys);
		
		setResizable(false);
	}
}
