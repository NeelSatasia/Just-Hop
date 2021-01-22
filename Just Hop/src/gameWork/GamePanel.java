package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
	
	Timer timer = new Timer(5, this);
	
	private int ballX = 300;
	private int ballY = 200;
	
	private Ball ball = new Ball(ballX, ballY, 20, 20, Color.RED);
	
	private Block[] blocks = new Block[10];
	private int[] blocksXPositions = new int[10];
	private int[] blocksYPositions = new int[10];
	private int[] blocksWidth = new int[10];
	
	private int brickYPositioner = -25;
	
	private int blockFallingSpeed = 1;
	private int ballFallingSpeed = 4;
	
	private int ballHorizontalDirection = 0;
	
	private boolean isBallJumping = false;
	private boolean isBallFalling = false;
	private boolean isBallInTheAir = false;
	
	private int ballJumpYDistance = 0;
	private int ballJumpSpeed = 0;
	
	private int changeSpeedTimer = 0;
	
	private int currentIndex = 0;
	
	public GamePanel() {
		
		changeBrickXPositions();
		
		for(int i = 0; i < blocks.length; i++) {
			blocksYPositions[i] += brickYPositioner;
			brickYPositioner = blocksYPositions[i] + 100;
			blocksWidth[i] = (int)(Math.random() * 31) + 30;
		}
		
		ballX = (int) (blocksXPositions[0] + (30 - ball.getHeight()/2));
		ballY = (int) (blocksYPositions[0] - ball.getHeight());
		
		ball.setLocation(ballX, ballY);
		
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		
		if(ballY > 600) {
			timer.stop();
		}
		
		ball.setBounds(ballX, ballY, (int) ball.getWidth(), (int) ball.getHeight());
		
		for(int i = 0; i < blocks.length; i++) {
			blocks[i] = new Block(blocksXPositions[i], blocksYPositions[i], blocksWidth[i], 5, Color.BLACK);
			blocks[i].draw(g);
		}
		
		ball.draw(g);
		
		if(collisionCheck()) {
			ballFallingSpeed = blockFallingSpeed;
			ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight());
		} else if(isBallJumping == false) {
			isBallFalling = true;
			currentIndex = -1;
			if(ballFallingSpeed < 15) {
				ballFallingSpeed++;
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < blocks.length; i++) {
			if(blocksYPositions[i] > 100 * blocks.length) {
				changeSpeedTimer++;
				if(changeSpeedTimer == 10) {
					blockFallingSpeed = (int)(Math.random() * 2) + 1;
					changeSpeedTimer = 0;
				}
				
				blocksYPositions[i] = -25;
				
				int blockXPosition = (int)(Math.random() * 3);
				
				switch(blockXPosition) {
					case 0:
						blockXPosition = 200;
						break;
					case 1:
						blockXPosition = 350;
						break;
					case 2:
						blockXPosition = 500;
						break;
				}
				if((i + 1 == blocksYPositions.length && blocksXPositions[0] == blockXPosition) || (i + 1 < blocksXPositions.length && blocksXPositions[i+1] == blockXPosition)) {
					if(blockXPosition == 200) {
						blockXPosition += 150;
					} else if(blockXPosition == 500) {
						blockXPosition -= 150;
					} else {
						int randNum = (int)(Math.random() * 2);
						switch(randNum) {
							case 0:
								blockXPosition -= 150;
								break;
							case 1:
								blockXPosition += 150;
								break;
						}
					}
				} 
				if((i + 1 == blocks.length && (blockXPosition != 350 && blocksXPositions[0] != 350)) || (i + 1 < blocks.length && (blockXPosition != 350 && blocksXPositions[i+1] != 350))) {
					blockXPosition = 350;
				}
				
				int brickWidthSize = (int)(Math.random() * 31) + 30;
				blocksWidth[i] = brickWidthSize;
				blocks[i].setSize(brickWidthSize, 5);
				
				blocksXPositions[i] = blockXPosition;
			}
			
			blocksYPositions[i] += blockFallingSpeed;
		}
		
		if(isBallJumping == true) {
			if(ballJumpYDistance > 0) {
				if(ballJumpYDistance <= 45) {
					ballJumpSpeed--;
				}
				ballY -= ballJumpSpeed;
				ballJumpYDistance -= 5;
			} else {
				isBallJumping = false;
				isBallFalling = true;
				ballFallingSpeed = 4;
			}
		}
		
		if(isBallInTheAir == true && isBallJumping == false && isBallFalling == false) {
			ballHorizontalDirection = 0;
			isBallInTheAir = false;
		}
		
		ballX += ballHorizontalDirection;
		ballY += ballFallingSpeed;
		
		repaint();
	}
	
	public void changeBrickXPositions() {
		for(int i = 0; i < blocksXPositions.length; i++) {
			int brickXPosition = (int)(Math.random() * 3);
			switch(brickXPosition) {
				case 0:
					brickXPosition = 200;
					break;
				case 1:
					brickXPosition = 350;
					break;
				case 2:
					brickXPosition = 500;
					break;
			}
			
			if(i - 1 >= 0 && brickXPosition == blocksXPositions[i-1]) {
				if(brickXPosition == 200) {
					brickXPosition += 150;
				} else if(brickXPosition == 500) {
					brickXPosition -= 150;
				} else {
					int randNum = (int)(Math.random() * 2);
					switch(randNum) {
						case 0:
							brickXPosition -= 150;
							break;
						case 1:
							brickXPosition += 150;
							break;
					}
				}
			}
			if(i - 1 >= 0 && (brickXPosition != 350 && blocksXPositions[i-1] != 350)) {
				brickXPosition = 350;
			}
			
			
			blocksXPositions[i] = brickXPosition;
		}
	}
		
	public boolean collisionCheck() {
		for(int i = 0; i < blocks.length; i++) {
			if(ballY + ballFallingSpeed > (int)(blocksYPositions[i] - ball.getHeight()) && ballY <= (int)(blocksYPositions[i]) && (ball.getX() + ball.getWidth() > blocksXPositions[i] && ball.getX() < blocksXPositions[i] + blocksWidth[i])) {
				isBallFalling = false;
				currentIndex = i;
				return true;
			}
		}
		
		return false;
	}
	
	public void changeBallHorizontalDirection(String direction) {
		switch(direction) {
			case "left":
				ballHorizontalDirection = -4;
				break;
			case "right":
				ballHorizontalDirection = 4;
				break;
			case "none":
				ballHorizontalDirection = 0;
				break;
		}
	}
	
	public void makeBallJump() {
		if(isBallJumping == false && isBallFalling == false) {
			isBallJumping = true;
			ballJumpYDistance = 150;
			ballJumpSpeed = 5;
			ballFallingSpeed = 0;
			currentIndex = -1;
		}
	}

	public boolean isBallJumping() {
		return isBallJumping;
	}
	
	public void isBallInTheAir(boolean isTrue) {
		isBallInTheAir = isTrue;
	}

	public boolean isBallFalling() {
		return isBallFalling;
	}
}
