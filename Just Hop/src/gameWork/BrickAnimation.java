package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.*;

public class BrickAnimation extends JPanel implements ActionListener {
	
	Timer timer = new Timer(5, this);
	
	private Brick[] bricks = new Brick[10];
	private int[] bricksXPositions = new int[10];
	private int[] bricksYPositions = new int[10];
	
	private int brickYPositioner = -25;
	
	private int ballX = 300;
	private int ballY = 200;
	
	private Ball ball = new Ball(ballX, ballY, 20, 20, Color.RED);;
	
	private int brickFallingSpeed = 1;
	private int ballFallingSpeed = 4;
	
	private int ballHorizontalDirection = 0;
	
	private boolean isBallJumping = false;
	private boolean isBallFalling = false;
	private boolean isBallInTheAir = false;
	
	private int ballJumpYDistance = 0;
	private int ballJumpSpeed = 0;
	
	private int changeSpeedTimer = 0;
	
	private int currentIndex = 0;
	
	public BrickAnimation() {
		
		changeBrickXPositions();
		
		for(int i = 0; i < bricks.length; i++) {
			bricksYPositions[i] += brickYPositioner;
			brickYPositioner = bricksYPositions[i] + 100;
		}
		
		ballX = (int) (bricksXPositions[0] + (30 - ball.getHeight()/2));
		ballY = (int) (bricksYPositions[0] - ball.getHeight());
		
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
		
		for(int i = 0; i < bricks.length; i++) {
			bricks[i] = new Brick(bricksXPositions[i], bricksYPositions[i], 60, 5, Color.BLACK);
			bricks[i].draw(g);
		}
		
		ball.setBounds(ballX, ballY, (int) ball.getWidth(), (int) ball.getHeight());
		
		ball.draw(g);
		collisionCheck();
		if(currentIndex >= 0) {
			ballFallingSpeed = brickFallingSpeed;
		} else if(isBallJumping == false) {
			ballFallingSpeed++;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < bricks.length; i++) {
			if(bricksYPositions[i] > 1000) {
				changeSpeedTimer++;
				if(changeSpeedTimer == 10) {
					brickFallingSpeed = (int)(Math.random() * 2) + 1;
					changeSpeedTimer = 0;
				}
				
				bricksYPositions[i] = -25;
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
				if((i + 1 == bricksYPositions.length && bricksXPositions[0] == brickXPosition) || (i + 1 < bricksXPositions.length && bricksXPositions[i+1] == brickXPosition)) {
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
				if((i + 1 == bricks.length && (brickXPosition != 350 && bricksXPositions[0] != 350)) || (i + 1 < bricks.length && (brickXPosition != 350 && bricksXPositions[i+1] != 350))) {
					brickXPosition = 350;
				}
				
				bricksXPositions[i] = brickXPosition;
			}
			
			bricksYPositions[i] += brickFallingSpeed;
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
		
		/*for(int i = 0; i < bricks.length; i++) {
			if(collisionCheck() && isBallJumping == false && isBallFalling == false) {
				ballY = (int)(bricksYPositions[i] - ball.getHeight());
				break;
			}
		}*/
		
		if(isBallInTheAir == true && collisionCheck()) {
			ballHorizontalDirection = 0;
			isBallInTheAir = false;
		}
		
		ballX += ballHorizontalDirection;
		ballY += ballFallingSpeed;
		
		if(currentIndex > -1) {
			ballY = (int)(bricksYPositions[currentIndex] - ball.getHeight());
		}
		//ball.getY() + ball.getHeight() >= bricks[i].getY()
		repaint();
	}
	
	public void changeBrickXPositions() {
		for(int i = 0; i < bricksXPositions.length; i++) {
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
			
			if(i - 1 >= 0 && brickXPosition == bricksXPositions[i-1]) {
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
			if(i - 1 >= 0 && (brickXPosition != 350 && bricksXPositions[i-1] != 350)) {
				brickXPosition = 350;
			}
			
			
			bricksXPositions[i] = brickXPosition;
		}
	}
		
	public boolean collisionCheck() {
		for(int i = 0; i < bricks.length; i++) {
			if(bricks[i].intersects(ball)) {
				if(ballY >= (int)(bricksYPositions[i] - ball.getHeight()) && (ball.getX() + ball.getWidth() > bricks[i].getX() && ball.getX() < bricks[i].getX() + bricks[i].getWidth())) {
					isBallFalling = false;
					currentIndex = i;
					return true;
				}
			}
		}
		
		currentIndex = -1;
		return false;
	}
	
	public void changeHorizontalDirection(String direction) {
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
	
	public void jump() {
		if(currentIndex > -1) {
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
	
	public void isInTheAir(boolean isTrue) {
		isBallInTheAir = isTrue;
	}

	public boolean isBallFalling() {
		return isBallFalling;
	}
}
