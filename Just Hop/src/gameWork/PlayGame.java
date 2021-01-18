package gameWork;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayGame {
	
	private JFrame gameFrame;
	private BrickAnimation bricks;
	private KeyListener ballControlKeys;
	
	public PlayGame() {
		gameFrame = new JFrame("Just Hop");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setBounds(50, 50, 800, 600);
		gameFrame.setVisible(true);
		
		bricks = new BrickAnimation();
		gameFrame.add(bricks);
		
		bricks.revalidate();
		bricks.repaint();
		
		ballControlKeys = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					bricks.changeHorizontalDirection("right");
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					bricks.changeHorizontalDirection("left");
				}
				if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
					bricks.jump();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(bricks.isBallJumping() || bricks.isBallFalling()) {
					bricks.isInTheAir(true);
				} else {
					bricks.changeHorizontalDirection("none");
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		};
		
		gameFrame.addKeyListener(ballControlKeys);
	}
}
