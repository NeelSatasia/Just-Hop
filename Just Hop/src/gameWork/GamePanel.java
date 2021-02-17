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
	
	Ball ball = new Ball(ballX, ballY, 20, 20, Color.BLACK);
	
	Blocks[] blocks = new Blocks[10];
	int[] blocksXPositions = new int[blocks.length];
	int[] blocksYPositions = new int[blocks.length];
	int[] blocksWidth = new int[blocks.length];
	int[] blocksColorTransparency = new int[blocks.length];
	
	int blockYPositioner = -25;
	
	int blockFallingSpeed = 1;
	int ballFallingSpeed = 4;
	
	int ballHorizontalDirection = 0;
	int previousBallHorizontalDirection = 0;
	
	boolean isBallJumping = false;
	boolean isBallFalling = false;
	boolean isBallInTheAir = false;
	
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
	
	boolean trickedMode = false;
	
	Blocks trickBlock;
	int trickBlockIndex = -1;
	
	int ballHealthLosingSpeed = 0;
	boolean isBallLosingHealth = false;
	
	JCheckBox[] modes = new JCheckBox[3];
	
	public GamePanel() {
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		
		changeBrickXPositions();
		
		ballX = (int) (blocksXPositions[3]);
		ballY = (int) (blocksYPositions[3] - ball.getHeight());
		
		ball.setLocation(ballX, ballY);
		
		this.add(scoreLabel);
		scoreLabel.setBounds(10, 10, 150, 30);
		scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		this.add(ballHealthLabel);
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
		
		if(ballY > 600 || ballHealth == 0) {
			timer.stop();
			
			JButton gameOverButton = new JButton("Try Again");
			this.add(gameOverButton);
			gameOverButton.setBounds(370, 285, 60, 30);
			gameOverButton.setEnabled(true);
			gameOverButton.setBorder(null);
			gameOverButton.setBackground(Color.BLACK);
			gameOverButton.setForeground(Color.WHITE);
			gameOverButton.setFocusable(false);
			
			int modesListY = 320;
			
			for(int i = 0; i < modes.length; i++) {
				this.add(modes[i]);
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
					
					timer.restart();
				}
			});
		} else {
			
			ball.setBounds(ballX, ballY, (int) ball.getWidth(), (int) ball.getHeight());
			
			for(int i = 0; i < blocks.length; i++) {
				if(trickBlockIndex == -1 && blocksXPositions[i] == 350) {
					int trickBlockRandNum = (int)(Math.random() * 6);
					int nextIndex = -1;
					if(i - 1 >= 0) {
						nextIndex = i - 1;
					} else {
						nextIndex = blocks.length - 1;
					}
					trickBlockIndex = nextIndex;
					int trickBlockXPosition = -1;
					
					if(blocksXPositions[nextIndex] == 200) {
						trickBlockXPosition = 500;
					} else {
						trickBlockXPosition = 200;
					}
					
					if(trickBlockRandNum >= 0 && trickBlockRandNum < 6) {
						trickBlock = new RegularBlock(trickBlockXPosition, blocksYPositions[trickBlockIndex], blocksWidth[trickBlockIndex], 5);
						trickBlock.draw(g);
					}
				}
				
				if(blocks[i].getY() >= 100 * blocks.length) {
					generateRandomBlock(i);
					
				} else {
					blocks[i].setBounds(blocksXPositions[i], blocksYPositions[i], blocksWidth[i], 5);
					
					if(blocks[i] instanceof HalfRedBlock) {
						blocks[i].changeColorTransparency(new Color(0, 0, 0, blocksColorTransparency[i]), new Color(255, 0, 0, blocksColorTransparency[i]));
					} else {
						blocks[i].changeColorTransparency(new Color(0, 0, 0, blocksColorTransparency[i]));
					}
					
					if(blocks[i] instanceof UpsideDownTBlock) {
						blocks[i].changeBlockTPositionX();
					}
				}
				
				blocks[i].draw(g);
			}
			
			ball.draw(g);
			
			if(collisionCheck()) {
				ballFallingSpeed = blockFallingSpeed;
				if(blocks[currentIndex] instanceof UpsideDownTBlock) {
					int TBarXPosition = blocks[currentIndex].blockTPositionX();
					if(ballY + ball.getHeight() > blocksYPositions[currentIndex] - 25) {
						if(ballX + ball.getWidth() == TBarXPosition) {
							ballX--;
							ball.setLocation(ballX, ballY);
						} else if(ballX == TBarXPosition + 5) {
							ballX++;
							ball.setLocation(ballX, ballY);
						}
					}
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
			if(modes[1].isSelected()) {
				if(ballHorizontalDirection == -4) {
					ballHorizontalDirection = -1;
				} else if(ballHorizontalDirection == 4) {
					ballHorizontalDirection = 1;
				}
			} else {
				ballHorizontalDirection = 0;
			}
			
			isBallInTheAir = false;
		}
		
		if(isBallLosingHealth) {
			ballHealthLosingSpeed += 5;
		}
		
		if(currentIndex > -1 || previousCurrentIndex > -1) {
			int index = -1;
			if(currentIndex > -1) {
				index = currentIndex;
			} else {
				index = previousCurrentIndex;
			}
			if(blocks[index] instanceof UpsideDownTBlock) {
				int num = blocks[index].blockTPositionX();
				if(ballHorizontalDirection == -4) {
					if(ballX >= num + 5 && ballY + ball.getHeight() > blocksYPositions[index] - 25) {
						if(ballX + ballHorizontalDirection <= num + 5) {
							previousBallHorizontalDirection = ballHorizontalDirection;
							ballHorizontalDirection = 0;
						}
					}
				} else if(ballHorizontalDirection == 4) {
					if(ballX + ball.getWidth() <= num && ballY + ball.getHeight() > blocksYPositions[index] - 25) {
						if(ballX + ball.getWidth() + ballHorizontalDirection >= num) {
							previousBallHorizontalDirection = ballHorizontalDirection;
							ballHorizontalDirection = 0;
						}
					}
				} else if(ballHorizontalDirection == 0 && isBallJumping && ballY + ball.getHeight() < blocksYPositions[index] - 25) {
					ballHorizontalDirection = previousBallHorizontalDirection;
				}
			}
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
			if(blocksColorTransparency[i] > 0) {
				if(blocks[i] instanceof RegularBlock || blocks[i] instanceof HalfRedBlock) {
					if((ball.getX() + ball.getWidth() > blocksXPositions[i] && ball.getX() < blocksXPositions[i] + blocksWidth[i]) && (ballY + ballFallingSpeed > (int)(blocksYPositions[i] - ball.getHeight()) && ballY <= (int)(blocksYPositions[i]))) {
						
						changeBlockColorTransparency(i);
						
						if((blocks[i] instanceof HalfRedBlock)) {
							if(blocks[i].isRedOnRightSide() && ballX + ball.getWidth() > blocksXPositions[i] + blocksWidth[i]/2 + 5) {
								ballLoseHealth(true);
							} else if(blocks[i].isRedOnRightSide() == false && ballX + 5 < blocksXPositions[i] + blocksWidth[i]/2) {
								ballLoseHealth(true);
							} else if(blocks[i].isRedOnRightSide() && ballX + ball.getWidth() < blocksXPositions[i] + blocksWidth[i]/2 + 5) {
								ballLoseHealth(false);
							} else if(blocks[i].isRedOnRightSide() == false && ballX + 5 > blocksXPositions[i] + blocksWidth[i]/2) {
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
				} else if(blocks[i] instanceof UpsideDownTBlock) {
					int num = blocks[i].blockTPositionX();
					if(ballX + ball.getWidth() > num && ballX < num + 5 && ballY + ballFallingSpeed > (int)(blocksYPositions[i] - ball.getHeight() - 25) && ballY <= (int)(blocksYPositions[i] - 25)) {
						changeBlockColorTransparency(i);
						isBallFalling = false;
						currentIndex = i;
						ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight() - 25);
						return true;
					} else if((ball.getX() + ball.getWidth() > blocksXPositions[i] && ball.getX() < blocksXPositions[i] + blocksWidth[i]) && (ballY + ballFallingSpeed > (int)(blocksYPositions[i] - ball.getHeight()) && ballY <= (int)(blocksYPositions[i]))) {
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
		previousBallHorizontalDirection = ballHorizontalDirection;
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
					ballJumpYDistance = 150;
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
	
	public void isBallInTheAir(boolean isTrue) {
		isBallInTheAir = isTrue;
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
				blocks[index] = new UpsideDownTBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], 5);
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
			ball.changeColor(Color.BLACK);
			isBallLosingHealth = false;
			ballHealthLosingSpeed = 0;
		}
	}
	
	public boolean isFrictionlessMode() {
		return modes[1].isSelected();
	}
	
}
