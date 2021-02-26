package gameWork;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
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
	
	HealthBooster[] healthBooster = new HealthBooster[blocks.length];
	
	int blockYPositioner = -25;
	
	int blockVerticalSpeed = 1;
	int ballVerticalSpeed = blockVerticalSpeed;
	
	int ballHorizontalSpeed = 0;
	
	boolean isBallJumping = false;
	boolean isBallFalling = false;
	
	int currentBallJumpYDistance = 0;
	double ballJumpSpeed = 0.0;
	
	int changeBlocksSpeedTimer = 0;
	
	int currentIndex = 0;
	private int previousIndex = 0;
	
	boolean didScore = false;
	int score = 0;
	JLabel scoreLabel = new JLabel("Score: " + score);
	
	int ballHealth = 100;
	JLabel ballHealthLabel = new JLabel("Health: " + ballHealth);
	
	int differentTypesOfBlocks = 4;
	
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
		
		changeBlocksXPositions();
		
		ballX = blocksXPositions[3];
		
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
					
					ballHorizontalSpeed = 0;
					ballVerticalSpeed = blockVerticalSpeed;
					slowingDownSpeed = 0.0;
					currentBallJumpYDistance = 0;
					ballJumpSpeed = 0;
					ballHealthLosingSpeed = 0;
					
					isBallLosingHealth = false;
					isBallJumping = false;
					isBallFalling = false;
					stopBallSlowly = false;
					
					changeBlocksXPositions();
					
					blockYPositioner = -25;
					
					for(int i = 0; i < blocks.length; i++) {
						blocksYPositions[i] = 0;
						blocksYPositions[i] += blockYPositioner;
						blockYPositioner = blocksYPositions[i] + 100;
						
						if(modes[0].isSelected()) {
							blocksWidth[i] = (int)(Math.random() * 16) + 45;
						} else {
							blocksWidth[i] = 60;
						}
						
						blocksColorTransparency[i] = 255;
						
						generateRandomBlock(i);
						blocks[i].draw(g);
					}
					
					ballX = blocksXPositions[3];
					ballY = 200;
					
					ball.setLocation(ballX, ballY);
					
					ball.changeColor(new Color(0, 179, 89));
					
					timer.restart();
				}
			});
		} else {
			ball.setLocation(ballX, ballY);
			ball.draw(g);
			
			for(int i = 0; i < blocks.length; i++) {
				if(blocks[i].getY() >= 100 * blocks.length) {
					generateRandomBlock(i);
					if(ballHealth < 100) {
						int healthBoosterChance = (int)(Math.random() * 21);
						if(healthBoosterChance <= 10) {
							int healthBoosterXPosition = (int)(Math.random() * (blocksWidth[i] - ball.getWidth())) + blocksXPositions[i];
							healthBooster[i] = new HealthBooster(healthBoosterXPosition, blocksYPositions[i]);
						}
					}
				} else {
					blocks[i].setBounds(blocksXPositions[i], blocksYPositions[i], blocksWidth[i], 5);
					
					blocks[i].changeColorTransparency(new Color(0, 0, 0, blocksColorTransparency[i]), new Color(255, 0, 0, blocksColorTransparency[i]));
					
					if(healthBooster[i] != null) {
						if(ballHealth < 100) {
							healthBooster[i].setLocation((int) healthBooster[i].getX(), blocksYPositions[i]);
						} else {
							healthBooster[i] = null;
						}
					}
					
					if(blocks[i] instanceof WiperBlock) {
						blocks[i].changeTBarXPosition();
					}
				}
				
				blocks[i].draw(g);
				
				if(healthBooster[i] != null) {
					healthBooster[i].draw(g);
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
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
			
			blocksYPositions[i] += blockVerticalSpeed;
		}
		
		if(collisionCheck()) {
			changeBlockColorTransparency(currentIndex);
			isBallFalling = false;
			ballVerticalSpeed = blockVerticalSpeed;
			
			if(((previousIndex == 0 && currentIndex == blocks.length-1) || previousIndex > currentIndex) && didScore == false) {
				score++;
				didScore = true;
				scoreLabel.setText("Score: " + score);
			}
		} else if(isBallJumping == false) {
			isBallFalling = true;
			currentIndex = -1;
			if(ballVerticalSpeed < 15) {
				ballVerticalSpeed++;
			}
		}
		
		if(isBallJumping == true) {
			if(currentBallJumpYDistance > 0) {
				if(currentBallJumpYDistance <= 45) {
					ballVerticalSpeed++;
				}
				currentBallJumpYDistance -= 5;
			} else {
				isBallJumping = false;
				isBallFalling = true;
			}
		}
		
		if(isBallJumping == false && isBallFalling == false) {
			if(modes[1].isSelected() && isLeftKeyDown == false && isRightKeyDown == false) {
				stopBallSlowly = true;
			} else if(isLeftKeyDown == false && isRightKeyDown == false) {
				ballHorizontalSpeed = 0;
			}
		}
		
		if(stopBallSlowly) {
			if(isBallJumping == false && isBallFalling == false) {
				slowingDownSpeed += 0.5;
				if(ballHorizontalSpeed > 0) {
					ballHorizontalSpeed -= (int) slowingDownSpeed;
				} else if(ballHorizontalSpeed < 0) {
					ballHorizontalSpeed += (int) slowingDownSpeed;
				}
				if(slowingDownSpeed == 1.0) {
					slowingDownSpeed = 0.0;
				}
				if(ballHorizontalSpeed == 0) {
					stopBallSlowly = false;
					slowingDownSpeed = 0.0;
				}
			}
		}
		
		if(isBallLosingHealth) {
			ballHealthLosingSpeed += 5;
		}
		
		ballX += ballHorizontalSpeed;
		ballY += ballVerticalSpeed;
		
		for(int i = 0; i < healthBooster.length; i++) {
			if(healthBooster[i] != null) {
				if(ballY + ball.getHeight() >= healthBooster[i].getY() && ballY <= healthBooster[i].getY() + healthBooster[i].getHeight()) {
					if(ballX + ball.getWidth() >= healthBooster[i].getX() && ballX <= healthBooster[i].getX() + healthBooster[i].getWidth()) {
						healthBooster[i] = null;
						if(ballHealth + 5 > 100) {
							ballHealth = 100;
						} else {
							ballHealth += 5;
						}
						ballHealthLabel.setText("Health: " + ballHealth);
						break;
					}
				}
			}
		}
		
		if(ballY > 600) {
			ballHealth = 0;
			ballHealthLabel.setText("Health: " + ballHealth);
		}
		
		repaint();
	}
	
	public void changeBlocksXPositions() {
		for(int i = 0; i < blocksXPositions.length; i++) {
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
			
			if(i - 1 >= 0 && blockXPosition == blocksXPositions[i-1]) {
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
			if(i - 1 >= 0 && (blockXPosition != 350 && blocksXPositions[i-1] != 350)) {
				blockXPosition = 350;
			}
			
			
			blocksXPositions[i] = blockXPosition;
		}
	}
	
	
	public boolean collisionCheck() {
		for(int i = 0; i < blocks.length; i++) {
			if(blocksColorTransparency[i] > 0) {
				if((ballX + ball.getWidth() <= blocksXPositions[i] && ballX + ball.getWidth() + ballHorizontalSpeed >= blocksXPositions[i]) || (ballX >= blocksXPositions[i] + blocksWidth[i] && ballX + ballHorizontalSpeed <= blocksXPositions[i] + blocksWidth[i])) {
					if((ballY + ballVerticalSpeed >= blocksYPositions[i] && ballY + ballVerticalSpeed <= blocksYPositions[i] + 5) || (ballY + ball.getHeight() + ballVerticalSpeed >= blocksYPositions[i] && ballY + ball.getHeight() + ballVerticalSpeed <= blocksYPositions[i] + 5) || (ballY + ballVerticalSpeed <= blocksYPositions[i] && ballY + ball.getHeight() + ballVerticalSpeed >= blocksYPositions[i] + 5)) {
						ballHorizontalSpeed = 0;
					}
				}
				if(isBallJumping) {
					if(ballX + ball.getWidth() + ballHorizontalSpeed > blocksXPositions[i] && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) {
						if(blocksYPositions[i] + 5 == ballY + ballVerticalSpeed) {
							isBallJumping = false;
							ballVerticalSpeed = 4;
							return false;
						}
					}
				}
				if(blocks[i] instanceof RegularBlock || blocks[i] instanceof HalfRedBlock) {
					if((ballX + ball.getWidth() + ballHorizontalSpeed > blocksXPositions[i] && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) && (ballY + ball.getHeight() + ballVerticalSpeed >= (int) blocksYPositions[i] && ballY + ball.getHeight() <= (int)(blocksYPositions[i]))) {
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
						
						currentIndex = i;
						ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight());
						return true;
					}
				} else if(blocks[i] instanceof WiperBlock) {
					int TBarXPosition = blocks[i].TBarXPosition();
					
					if(ballX >= TBarXPosition + 5 && ballY + ball.getHeight() + ballVerticalSpeed > blocksYPositions[i] - blocks[i].TBarHeight() && ballY + ball.getHeight() <= blocksYPositions[i]) {
						if(ballX + ballHorizontalSpeed <= TBarXPosition + 5) {
							ballHorizontalSpeed = 0;
							if(blocks[i].isTBarRight() == false && TBarXPosition > blocksXPositions[i]) {
								if(isLeftKeyDown) {
									ballX = TBarXPosition + 5 - 1;
								}
							} else {
								ballX = TBarXPosition + 5 + 1;
							}
							ball.setLocation(ballX, ballY);
						}
					} else if(ballX + ball.getWidth() <= TBarXPosition && ballY + ball.getHeight() + ballVerticalSpeed > blocksYPositions[i] - blocks[i].TBarHeight() && ballY + ball.getHeight() <= blocksYPositions[i]) {
						if(ballX + ball.getWidth() + ballHorizontalSpeed >= TBarXPosition) {
							ballHorizontalSpeed = 0;
							if(blocks[i].isTBarRight() && TBarXPosition + 5 < blocksXPositions[i] + blocksWidth[i]) {
								if(isRightKeyDown) {
									ballX = TBarXPosition - (int) ball.getWidth() + 1;
								}
							} else {
								ballX = TBarXPosition - (int) ball.getWidth() - 1;
							}
							ball.setLocation(ballX, ballY);
						}
					} else {
						if(isBallJumping) {
							if((ballX + ball.getWidth() > blocksXPositions[previousIndex] && ballX < blocksXPositions[previousIndex] + blocksWidth[previousIndex]) && (ballY + ball.getHeight() < blocksYPositions[previousIndex] - blocks[previousIndex].TBarHeight())) {
								if(isRightKeyDown) {
									ballHorizontalSpeed = 4;
								} else if(isLeftKeyDown) {
									ballHorizontalSpeed = -4;
								}
							}
						}
					}
					
					if(ballX + ball.getWidth() + ballHorizontalSpeed > TBarXPosition && ballX + ballHorizontalSpeed < TBarXPosition + 5 && ballY + ball.getHeight() + ballVerticalSpeed >= blocksYPositions[i] - blocks[i].TBarHeight() && ballY + ball.getHeight() <= blocksYPositions[i] - blocks[i].TBarHeight()) {
						currentIndex = i;
						ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight() - blocks[i].TBarHeight());
						return true;
					} else if((ballX + ball.getWidth() + ballHorizontalSpeed > blocksXPositions[i] && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) && (ballY + ball.getHeight() + ballVerticalSpeed >= blocksYPositions[i] && ballY + ball.getHeight() <= blocksYPositions[i])) {
						currentIndex = i;
						ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight());
						return true;
					}
				} else if(blocks[i] instanceof SplitBlock) {
					int secondBlockXPosition = blocks[i].secondBlockXPosition();
					
					if(ballY + ball.getHeight() + ballVerticalSpeed >= blocksYPositions[i] && ballY + ball.getHeight() <= blocksYPositions[i]) {
						if(ballX + ball.getWidth() + ballHorizontalSpeed > blocksXPositions[i] && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) {
							if(ballX + ball.getWidth() + ballHorizontalSpeed > blocksXPositions[i] && ballX + ballHorizontalSpeed < blocksXPositions[i] + (secondBlockXPosition - (ball.getWidth() + 1))) {
								currentIndex = i;
								ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight());
								return true;
							} else if(ballX + ball.getWidth() + ballHorizontalSpeed > secondBlockXPosition && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) {
								currentIndex = i;
								ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight());
								return true;
							}
						}
					}
				}
			}
		}
		
		if(currentIndex > -1) {
			previousIndex = currentIndex;
		}
		
		if(isBallLosingHealth == true) {
			ballLoseHealth(false);
		}
		
		return false;
	}
	
	public void changeBallHorizontalDirection(String direction) {
		switch(direction) {
			case "left":
				ballHorizontalSpeed = -4;
				break;
			case "right":
				ballHorizontalSpeed = 4;
				break;
			case "none":
				ballHorizontalSpeed = 0;
				break;
		}
	}
	
	public void makeBallJump() {
		if(isBallJumping == false && isBallFalling == false) {
			isBallJumping = true;
			
			switch(blockVerticalSpeed) {
				case 1:
					currentBallJumpYDistance = 150;
					break;
				case 2:
					currentBallJumpYDistance = 140;
					break;
			}
			
			ballVerticalSpeed = -5;
			previousIndex = currentIndex;
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
			case 3:
				blocks[index] = new SplitBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], 5, (int) ball.getWidth());
				break;
		}
	}

	public void changeBlockColorTransparency(int index) {
		if(modes[2].isSelected()) {
			if(blockVerticalSpeed == 1 && blocksColorTransparency[index] - 2 >= 0) {
				blocksColorTransparency[index] -= 2;
			} else if(blockVerticalSpeed == 2 && blocksColorTransparency[index] - 7 >= 0) {
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

	public void rightKeyDown(boolean x) {
		isRightKeyDown = x;
	}

	public void leftKeyDown(boolean x) {
		isLeftKeyDown = x;
	}
}
