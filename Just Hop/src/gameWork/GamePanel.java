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
	
	private int ballX = 300;
	private int ballY = 200;
	
	private Ball ball = new Ball(ballX, ballY, 20, 20, Color.BLACK);
	
	private Blocks[] blocks = new Blocks[10];
	private int[] blocksXPositions = new int[blocks.length];
	private int[] blocksYPositions = new int[blocks.length];
	private int[] blocksWidth = new int[blocks.length];
	private int[] blocksColorTransparency = new int[blocks.length];
	
	private int blockYPositioner = -25;
	
	private int blockFallingSpeed = 1;
	private int ballFallingSpeed = 4;
	
	private int ballHorizontalDirection = 0;
	
	private boolean isBallJumping = false;
	private boolean isBallFalling = false;
	private boolean isBallInTheAir = false;
	
	private int ballJumpYDistance = 0;
	private int ballJumpSpeed = 0;
	
	private int changeBlocksSpeedTimer = 0;
	
	private int currentIndex = 0;
	private int previousCurrentIndex = 0;
	
	private boolean didScore = false;
	private int score = 0;
	private JLabel scoreLabel = new JLabel("Score: " + score);
	
	private int ballHealth = 100;
	private JLabel ballHealthLabel = new JLabel("Health: " + ballHealth);
	
	private int differentTypesOfBlocks = 3;
	
	private boolean frictionlessMode = false;
	private boolean randomBlockSizeMode = false;
	private boolean breakableBlocksMode = false;
	private boolean trickedMode = false;
	
	private Blocks trickBlock;
	private int trickBlockIndex = -1;
	
	private int ballHealthLosingSpeed = 0;
	private boolean isBallLosingHealth = false;
	
	public GamePanel() {
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		
		changeBrickXPositions();
		
		for(int i = 0; i < blocks.length; i++) {
			blocksYPositions[i] += blockYPositioner;
			blockYPositioner = blocksYPositions[i] + 100;
			
			if(randomBlockSizeMode) {
				blocksWidth[i] = (int)(Math.random() * 16) + 45;
			} else {
				blocksWidth[i] = 60;
			}
			
			blocksColorTransparency[i] = 255;
			
			generateRandomBlock(i);
		}
		
		ballX = (int) (blocksXPositions[3] + (30 - ball.getHeight()/2) - 5);
		ballY = (int) (blocksYPositions[3] - ball.getHeight());
		
		ball.setLocation(ballX, ballY);
		
		this.add(scoreLabel);
		scoreLabel.setBounds(10, 10, 150, 30);
		scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		this.add(ballHealthLabel);
		ballHealthLabel.setBounds(10, 40, 150, 30);
		ballHealthLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(ballY > 600 || ballHealth == 0) {
			timer.stop();
		}
		
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
					trickBlock = new RegularBlock(trickBlockXPosition, blocksYPositions[trickBlockIndex], blocksWidth[trickBlockIndex], 5, new Color(0, 0, 0, blocksColorTransparency[trickBlockIndex]));
					trickBlock.draw(g);
				}
			}
			
			if(blocks[i].getY() >= 100 * blocks.length) {
				generateRandomBlock(i);
				
			} else {
				if(blocks[i] instanceof RegularBlock) {
					blocks[i] = new RegularBlock(blocksXPositions[i], blocksYPositions[i], blocksWidth[i], 5, new Color(0, 0, 0, blocksColorTransparency[i]));
				} else if(blocks[i] instanceof HalfRedBlock) {
					blocks[i] = new HalfRedBlock(blocksXPositions[i], blocksYPositions[i], blocksWidth[i], 5, new Color(0, 0, 0, blocksColorTransparency[i]));
				} else if(blocks[i] instanceof ShooterBlock) {
					blocks[i] = new ShooterBlock(blocksXPositions[i], blocksYPositions[i], blocksWidth[i], 5, new Color(0, 0, 0, blocksColorTransparency[i]));
				}
				
				if(trickBlockIndex > -1 && trickBlockIndex == i) {
					trickBlock = new RegularBlock((int)trickBlock.getX(), blocksYPositions[i], blocksWidth[i], 5, new Color(0, 0, 0, blocksColorTransparency[i]));
					trickBlock.draw(g);
				}
			}
			
			blocks[i].draw(g);
		}
		
		ball.draw(g);
		
		if(collisionCheck()) {
			ballFallingSpeed = blockFallingSpeed;
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
				
				if(randomBlockSizeMode) {
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
			if(frictionlessMode) {
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
			if(blocksColorTransparency[i] > 0 && (ball.getX() + ball.getWidth() > blocksXPositions[i] && ball.getX() < blocksXPositions[i] + blocksWidth[i])) {
				
				if((blocks[i] instanceof RegularBlock || blocks[i] instanceof HalfRedBlock || blocks[i] instanceof ShooterBlock) && ballY + ballFallingSpeed > (int)(blocksYPositions[i] - ball.getHeight()) && ballY <= (int)(blocksYPositions[i])) {
					if((blocks[i] instanceof HalfRedBlock) && ball.getX() + ball.getWidth() > blocksXPositions[i] + blocksWidth[i]/2 + 5) {
						ballLoseHealth(true);
					} else {
						ballLoseHealth(false);
					}
					isBallFalling = false;
					currentIndex = i;
					ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight());
					return true;
				}
			}
		}
			
		if(isBallLosingHealth == true) {
			ballLoseHealth(false);
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

	public boolean isRandomBlockSizeMode() {
		return randomBlockSizeMode;
	}

	public boolean isFrictionlessMode() {
		return frictionlessMode;
	}
	
	public void generateRandomBlock(int index) {
		int randBlock = (int)(Math.random() * differentTypesOfBlocks);
		switch(randBlock) {
			case 0:
				blocks[index] = new RegularBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], 5, new Color(0, 0, 0, blocksColorTransparency[index]));
				break;
			case 1:
				blocks[index] = new HalfRedBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], 5, new Color(0, 0, 0, blocksColorTransparency[index]));
				break;
			case 2:
				blocks[index] = new ShooterBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], 5, new Color(0, 0, 0, blocksColorTransparency[index]));
				break;
		}
	}

	public void changeBlockColorTransparency(int index) {
		if(breakableBlocksMode) {
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
}
