package gameWork;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
	
	Timer timer = new Timer(5, this);
	
	int ballX = 300;
	int ballY = 200;
	
	Ball ball = new Ball(ballX, ballY, 15, 15, new Color(0, 179, 89));
	
	Blocks[] blocks = new Blocks[10];
	int[] blocksXPositions = new int[blocks.length];
	int[] blocksYPositions = new int[blocks.length];
	int[] blocksWidth = new int[blocks.length];
	int[] blocksColorTransparency = new int[blocks.length];
	
	int blockYPositioner = -25;
	
	int blockFallingSpeed = 1;
	int ballFallingSpeed = blockFallingSpeed;
	
	int ballHorizontalDirection = 0;
	
	boolean isBallJumping = false;
	boolean isBallFalling = false;
	
	int ballJumpYDistance = 0;
	int ballJumpSpeed = 0;
	
	int changeBlocksSpeedTimer = 0;
	
	int currentIndex = 0;
	private int previousCurrentIndex = 0;
	
	boolean didScore = false;
	int score = 0;
	JLabel scoreLabel = new JLabel("Score: " + score);
	
	int ballHealth = 100;
	JLabel ballHealthLabel = new JLabel("Health: " + ballHealth);
	
	int differentTypesOfBlocks = 3;
	
	int ballHealthLosingSpeed = 0;
	boolean isBallLosingHealth = false;
	
	boolean stopBallSlowly = false;
	double slowingDownSpeed = 0.0;
	
	boolean isRightKeyDown = false;
	boolean isLeftKeyDown = false;
	
	JCheckBox[] modes = new JCheckBox[3];
	
	public GamePanel() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		changeBrickXPositions();
		
		ballX = (int) (blocksXPositions[3]);
		ballY = (int) (blocksYPositions[3] - ball.getHeight());
		
		ball.setLocation(ballX, ballY);
		
		add(scoreLabel);
		scoreLabel.setBounds(10, 10, 150, 30);
		scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		add(ballHealthLabel);
		ballHealthLabel.setBounds(10, 40, 150, 30);
		ballHealthLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		modes[0] = new JCheckBox("Blocks With Random Sizes");
		modes[1] = new JCheckBox("Slippery Blocks");
		modes[2] = new JCheckBox("Transparent Blocks");
		
		for(int i = 0; i < modes.length; i++) {
			modes[i].setFocusable(false);
			modes[i].setFont(new Font("Consolas", Font.PLAIN, 10));
			
			int j = i;
			modes[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == modes[j]) {
						if(modes[j].isSelected()) {
							modes[j].setSelected(true);
						} else {
							modes[j].setSelected(false);
						}
					}
				}
			});
		}
		
		for(int i = 0; i < blocks.length; i++) {
			blocksYPositions[i] += blockYPositioner;
			blockYPositioner = blocksYPositions[i] + 100;
			
			if(modes[0].isSelected()) {
				blocksWidth[i] = (int)(Math.random() * 31) + 30;
			} else {
				blocksWidth[i] = 60;
			}
			
			blocksColorTransparency[i] = 255;
			
			generateRandomBlock(i);
		}
		
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(ballHealth == 0) {
			timer.stop();
			
			JButton gameOverButton = new JButton("Try Again");
			add(gameOverButton);
			gameOverButton.setBounds(370, 285, 60, 30);
			gameOverButton.setEnabled(true);
			gameOverButton.setBorder(null);
			gameOverButton.setBackground(Color.BLACK);
			gameOverButton.setForeground(Color.WHITE);
			gameOverButton.setFocusable(false);
			
			int modesListY = 320;
			
			for(int i = 0; i < modes.length; i++) {
				add(modes[i]);
				modes[i].setBounds(310, modesListY, 190, 20);
				modesListY += 25;
			}
			
			gameOverButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gameOverButton.hide();
					remove(gameOverButton);
					
					for(int i = 0; i < modes.length; i++) {
						remove(modes[i]);
					}
					
					score = 0;
					scoreLabel.setText("Score: " + score);
					
					ballHealth = 100;
					ballHealthLabel.setText("Health: " + ballHealth);
					
					ballHorizontalDirection = 0;
					ballFallingSpeed = blockFallingSpeed;
					slowingDownSpeed = 0.0;
					ballJumpYDistance = 0;
					ballJumpSpeed = 0;
					ballHealthLosingSpeed = 0;
					
					isBallLosingHealth = false;
					isBallJumping = false;
					isBallFalling = false;
					stopBallSlowly = false;
					
					changeBrickXPositions();
					
					blockYPositioner = -25;
					
					for(int i = 0; i < blocks.length; i++) {
						blocksYPositions[i] = 0;
						blocksYPositions[i] += blockYPositioner;
						blockYPositioner = blocksYPositions[i] + 100;
						
						if(modes[0].isSelected()) {
							blocksWidth[i] = (int)(Math.random() * 31) + 30;
						} else {
							blocksWidth[i] = 60;
						}
						
						blocksColorTransparency[i] = 255;
						
						generateRandomBlock(i);
						blocks[i].draw(g);
					}
					
					ballX = (int) (blocksXPositions[3]);
					ballY = (int) (blocksYPositions[3] - ball.getHeight());
					
					ball.setLocation(ballX, ballY);
					
					ball.changeColor(new Color(0, 179, 89));
					
					timer.restart();
				}
			});
		} else {
			ball.setBounds(ballX, ballY, (int) ball.getWidth(), (int) ball.getHeight());
			
			for(int i = 0; i < blocks.length; i++) {
				if(blocks[i].getY() >= 100 * blocks.length) {
					generateRandomBlock(i);
					
				} else {
					blocks[i].setBounds(blocksXPositions[i], blocksYPositions[i], blocksWidth[i], 5);
					
					blocks[i].changeColorTransparency(new Color(0, 0, 0, blocksColorTransparency[i]), new Color(255, 0, 0, blocksColorTransparency[i]));
					
					if(blocks[i] instanceof WiperBlock) {
						blocks[i].changeTBarXPosition();
					}
				}
				
				blocks[i].draw(g);
			}
			
			ball.draw(g);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(collisionCheck()) {
			if(isBallJumping == false && isBallFalling == false) {
				ballFallingSpeed = blockFallingSpeed;
			}
			if(((previousCurrentIndex == 0 && currentIndex == blocks.length-1) || previousCurrentIndex > currentIndex) && didScore == false) {
				score++;
				didScore = true;
				scoreLabel.setText("Score: " + score);
			}
		} else if(isBallJumping == false) {
			isBallFalling = true;
			currentIndex = -1;
			if(ballFallingSpeed < 15) {
				ballFallingSpeed++;
			}
		}
		
		for(int i = 0; i < blocks.length; i++) {
			if(blocksYPositions[i] >= 100 * blocks.length) {
				blocksColorTransparency[i] = 255;
				
				changeBlocksSpeedTimer++;
				if(changeBlocksSpeedTimer == 10) {
					//blockFallingSpeed = (int)(Math.random() * 2) + 1;
					changeBlocksSpeedTimer = 0;
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
				
				if(modes[0].isSelected()) {
					int brickWidthSize = (int)(Math.random() * 16) + 45;
					blocksWidth[i] = brickWidthSize;
					blocks[i].setSize(brickWidthSize, 5);
				}
				
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
		
		if(isBallJumping == false && isBallFalling == false) {
			if(modes[1].isSelected() && isLeftKeyDown == false && isRightKeyDown == false) {
				stopBallSlowly = true;
			} else if(isLeftKeyDown == false && isRightKeyDown == false) {
				ballHorizontalDirection = 0;
			}
		}
		
		if(stopBallSlowly) {
			if(isBallJumping == false && isBallFalling == false) {
				slowingDownSpeed += 0.5;
				if(ballHorizontalDirection > 0) {
					ballHorizontalDirection -= (int) slowingDownSpeed;
				} else if(ballHorizontalDirection < 0) {
					ballHorizontalDirection += (int) slowingDownSpeed;
				}
				if(slowingDownSpeed == 1.0) {
					slowingDownSpeed = 0.0;
				}
				if(ballHorizontalDirection == 0) {
					stopBallSlowly = false;
					slowingDownSpeed = 0.0;
				}
			}
		}
		
		if(isBallLosingHealth) {
			ballHealthLosingSpeed += 5;
		}
		
		ballX += ballHorizontalDirection;
		ballY += ballFallingSpeed;
		
		if(ballY > 600) {
			ballHealth = 0;
			ballHealthLabel.setText("Health: " + ballHealth);
		}
		
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
			if(blocksColorTransparency[i] > 0) {
				if((ballX + ball.getWidth() <= blocksXPositions[i] && ballX + ball.getWidth() + ballHorizontalDirection >= blocksXPositions[i]) || (ballX >= blocksXPositions[i] + blocksWidth[i] && ballX + ballHorizontalDirection <= blocksXPositions[i] + blocksWidth[i])) {
					if((ballY + ballFallingSpeed >= blocksYPositions[i] && ballY + ballFallingSpeed <= blocksYPositions[i] + 5) || (ballY + ball.getHeight() + ballFallingSpeed >= blocksYPositions[i] && ballY + ball.getHeight() + ballFallingSpeed <= blocksYPositions[i] + 5) || (ballY + ballFallingSpeed <= blocksYPositions[i] && ballY + ball.getHeight() + ballFallingSpeed >= blocksYPositions[i] + 5)) {
						ballHorizontalDirection = 0;
					}
				}
				if(isBallJumping) {
					if(ballX + ball.getWidth() + ballHorizontalDirection > blocksXPositions[i] && ballX + ballHorizontalDirection < blocksXPositions[i] + blocksWidth[i]) {
						if(blocksYPositions[i] + 5 + blockFallingSpeed == ballY) {
							isBallJumping = false;
						}
					}
				}
				if(blocks[i] instanceof RegularBlock || blocks[i] instanceof HalfRedBlock) {
					if((ballX + ball.getWidth() + ballHorizontalDirection > blocksXPositions[i] && ballX + ballHorizontalDirection < blocksXPositions[i] + blocksWidth[i]) && (ballY + ball.getHeight() + ballFallingSpeed > (int) blocksYPositions[i] && ballY + ball.getHeight() <= (int)(blocksYPositions[i]))) {
						
						changeBlockColorTransparency(i);
						
						if((blocks[i] instanceof HalfRedBlock)) {
							if(blocks[i].isRedOnRightSide() && ballX + ball.getWidth() > blocksXPositions[i] + blocksWidth[i]/2) {
								ballLoseHealth(true);
							} else if(blocks[i].isRedOnRightSide() == false && ballX < blocksXPositions[i] + blocksWidth[i]/2) {
								ballLoseHealth(true);
							} else if(blocks[i].isRedOnRightSide() && ballX + ball.getWidth() < blocksXPositions[i] + blocksWidth[i]/2) {
								ballLoseHealth(false);
							} else if(blocks[i].isRedOnRightSide() == false && ballX > blocksXPositions[i] + blocksWidth[i]/2) {
								ballLoseHealth(false);
							}
							
						} else {
							ballLoseHealth(false);
						}
						
						isBallFalling = false;
						currentIndex = i;
						ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight());
						return true;
					}
				} else if(blocks[i] instanceof WiperBlock) {
					int TBarXPosition = blocks[i].TBarXPosition();
					
					if(ballX >= TBarXPosition + 5 && ballY + ball.getHeight() + ballFallingSpeed > blocksYPositions[i] - blocks[i].TBarHeight() && ballY + ball.getHeight() <= blocksYPositions[i]) {
						if(ballX + ballHorizontalDirection <= TBarXPosition + 5) {
							ballHorizontalDirection = 0;
							if(blocks[i].isTBarRight() == false && TBarXPosition > blocksXPositions[i]) {
								if(isLeftKeyDown) {
									ballX = TBarXPosition + 5 - 1;
								}
							} else {
								ballX = TBarXPosition + 5 + 1;
							}
							ball.setLocation(ballX, ballY);
						}
					} else if(ballX + ball.getWidth() <= TBarXPosition && ballY + ball.getHeight() + ballFallingSpeed > blocksYPositions[i] - blocks[i].TBarHeight() && ballY + ball.getHeight() <= blocksYPositions[i]) {
						if(ballX + ball.getWidth() + ballHorizontalDirection >= TBarXPosition) {
							ballHorizontalDirection = 0;
							if(blocks[i].isTBarRight() && TBarXPosition + 5 < blocksXPositions[i] + blocksWidth[i]) {
								if(isRightKeyDown) {
									ballX = TBarXPosition - (int) ball.getWidth() + 1;
								}
							} else {
								ballX = TBarXPosition - (int) ball.getWidth() - 1;
							}
							ball.setLocation(ballX, ballY);
						}
					}
					
					if(ballX + ball.getWidth() + ballHorizontalDirection > TBarXPosition && ballX + ballHorizontalDirection < TBarXPosition + 5 && ballY + ball.getHeight() + ballFallingSpeed >= blocksYPositions[i] - blocks[i].TBarHeight() && ballY + ball.getHeight() <= blocksYPositions[i] - blocks[i].TBarHeight()) {
						changeBlockColorTransparency(i);
						isBallFalling = false;
						currentIndex = i;
						ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight() - blocks[i].TBarHeight());
						return true;
					} else if((ballX + ball.getWidth() + ballHorizontalDirection > blocksXPositions[i] && ballX + ballHorizontalDirection < blocksXPositions[i] + blocksWidth[i]) && (ballY + ball.getHeight() + ballFallingSpeed > (int) blocksYPositions[i] && ballY + ball.getHeight() <= blocksYPositions[i])) {
						changeBlockColorTransparency(i);
						isBallFalling = false;
						currentIndex = i;
						ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight());
						return true;
					}
				}
			}
		}
			
		if(isBallLosingHealth == true) {
			ballLoseHealth(false);
		}
		
		if(currentIndex > -1) {
			previousCurrentIndex = currentIndex;
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
			
			switch(blockFallingSpeed) {
				case 1:
					ballJumpYDistance = 145;
					break;
				case 2:
					ballJumpYDistance = 140;
					break;
			}
			
			ballJumpSpeed = 5;
			ballFallingSpeed = 0;
			previousCurrentIndex = currentIndex;
			didScore = false;
			currentIndex = -1;
		}
	}
	
	public boolean isBallJumping() {
		return isBallJumping;
	}

	public boolean isBallFalling() {
		return isBallFalling;
	}
	
	public void generateRandomBlock(int index) {
		int randBlock = (int)(Math.random() * differentTypesOfBlocks);
		switch(randBlock) {
			case 0:
				blocks[index] = new RegularBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], 5);
				break;
			case 1:
				blocks[index] = new HalfRedBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], 5);
				break;
			case 2:
				blocks[index] = new WiperBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], 5);
				break;	
		}
	}

	public void changeBlockColorTransparency(int index) {
		if(modes[2].isSelected()) {
			if(blockFallingSpeed == 1 && blocksColorTransparency[index] - 2 >= 0) {
				blocksColorTransparency[index] -= 2;
			} else if(blockFallingSpeed == 2 && blocksColorTransparency[index] - 7 >= 0) {
				blocksColorTransparency[index] -= 7;
			} else if (blocksColorTransparency[index] - 1 >= 0) {
				blocksColorTransparency[index]--;
			}
		}
	}
	
	public void ballLoseHealth(boolean shouldLose) {
		if(shouldLose == true) {
			ball.changeColor(Color.RED);
			if(isBallLosingHealth == false) {
				ballHealth -= 5;
				ballHealthLabel.setText("Health: " + ballHealth);
				isBallLosingHealth = true;
			} else if(ballHealthLosingSpeed == 200) {
				ballHealth -= 5;
				ballHealthLabel.setText("Health: " + ballHealth);
				ballHealthLosingSpeed = 0;
			}
		} else {
			ball.changeColor(new Color(0, 179, 89));
			isBallLosingHealth = false;
			ballHealthLosingSpeed = 0;
		}
	}
	
	public boolean isFrictionlessMode() {
		return modes[1].isSelected();
	}
	
	public void stopBallSlowly(boolean x) {
		stopBallSlowly = x;
	}

	public void rightKeyDown(boolean x) {
		isRightKeyDown = x;
	}

	public void leftKeyDown(boolean x) {
		isLeftKeyDown = x;
	}
}
