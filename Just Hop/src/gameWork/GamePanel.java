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
	
	Ball ball = new Ball(ballX, ballY, 15, 15, new Color(31, 122, 31));
	
	Blocks[] blocks = new Blocks[10];
	int[] blocksXPositions = new int[blocks.length];
	int[] blocksYPositions = new int[blocks.length];
	int[] blocksWidth = new int[blocks.length];
	int[] blocksColorTransparency = new int[blocks.length];
	
	HealthBooster[] healthBooster = new HealthBooster[blocks.length];
	
	int blockYPositioner;
	
	int blockVerticalSpeed;
	int ballVerticalSpeed;
	
	int ballHorizontalSpeed;
	
	boolean isBallJumping;
	boolean isBallFalling;
	
	int currentBallJumpYDistance;
	double ballJumpSpeed;
	
	int changeBlocksSpeedTimer;
	
	int currentIndex;
	private int previousIndex;
	
	boolean didScore;
	int score;
	JLabel scoreLabel = new JLabel("Score: " + score);
	
	int ballHealth;
	JLabel ballHealthLabel = new JLabel("Health: " + ballHealth);
	
	int differentTypesOfBlocks = 5;
	
	int ballHealthLosingSpeed;
	boolean isBallLosingHealth;
	
	boolean stopBallSlowly;
	double slowingDownSpeed;
	
	boolean isRightKeyDown;
	boolean isLeftKeyDown;
	
	boolean changedDirectionInAir;
	
	JCheckBox[] modes = new JCheckBox[3];
	
	JButton startgame = new JButton("Start Game");
	JButton customize = new JButton("Customize");
	JButton store = new JButton("Store");
	JButton music = new JButton("Music");
	
	public GamePanel() {
		
		setLayout(null);
		setBackground(new Color(255, 255, 102));
		
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
		
		add(startgame);
		startgame.setBounds(365, 185, 70, 30);
		enableButton(startgame);
		
		startgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuButtons(false);
				
				startGame();
				timer.start();
			}
		});
		
		add(customize);
		customize.setBounds(365, 225, 70, 30);
		enableButton(customize);
		
		customize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				menuButtons(false);
			}
		});
		
		add(store);
		store.setBounds(365, 265, 70, 30);
		enableButton(store);
		
		store.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				menuButtons(false);
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(ballHealth == 0 && timer.isRunning()) {
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
					
					startGame();
				}
			});
		} else if(timer.isRunning()) {
			ball.setLocation(ballX, ballY);
			ball.draw(g);
			
			for(int i = 0; i < blocks.length; i++) {
				if(blocks[i].getY() >= 100 * blocks.length) {
					generateRandomBlock(i);
					
					if(ballHealth < 50) {
						int healthBoosterChance = (int)(Math.random() * 21);
						if(healthBoosterChance <= 10) {
							int healthBoosterXPosition = (int)(Math.random() * (blocksWidth[i] - ball.getWidth())) + blocksXPositions[i];
							healthBooster[i] = new HealthBooster(healthBoosterXPosition, blocksYPositions[i]);
						}
					}
				} else {
					blocks[i].setBounds(blocksXPositions[i], blocksYPositions[i], blocksWidth[i], 5);
					
					blocks[i].changeColorTransparency(blocksColorTransparency[i]);
					
					if(healthBooster[i] != null) {
						if(ballHealth < 100) {
							healthBooster[i].setLocation((int) healthBooster[i].getX(), blocksYPositions[i]);
						} else {
							healthBooster[i] = null;
						}
					}
					
					blocks[i].changeTBarXPosition();
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
			if(currentIndex > -1) {
				previousIndex = currentIndex;
				currentIndex = -1;
			}
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
			if(changedDirectionInAir) {
				changedDirectionInAir = false;
			}
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
		
		for(int i = 0; i < blocks.length; i++) {
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
			
			if(blocks[i] instanceof ShooterBlock) {
				for(int j = 0; j < blocks[i].getBulletsList().size(); j++) {
					if(blocks[i].getBulletsList().get(j).getBulletXPosition() + blocks[i].getBulletsList().get(j).getWidth() >= ballX && blocks[i].getBulletsList().get(j).getBulletXPosition() <= ballX + ball.getWidth()) {
						if(blocks[i].getBulletsList().get(j).getBulletYPosition() + blocks[i].getBulletsList().get(j).getHeight() >= ballY && blocks[i].getBulletsList().get(j).getBulletYPosition() <= ballY + ball.getHeight()) {
							if(ballHealth - 10 >= 0) {
								ballHealth -= 10;
							} else {
								ballHealth = 0;
							}
							
							ballHealthLabel.setText("Health: " + ballHealth);
							
							blocks[i].removeBullet(j);
							break;
						}
					}
					if(blocks[i].getBulletsList().get(j).getBulletYPosition() >= 600) {
						blocks[i].removeBullet(j);
						break;
					}
					if(ballY + ball.getHeight() <= blocksYPositions[i] && blocks[i].getBulletsList().get(j).getBulletYPosition() <= blocksYPositions[i] + blocks[i].getHeight() + 15) {
						blocks[i].removeBullet(j);
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
	
	public void startGame() {
		score = 0;
		scoreLabel.setText("Score: " + score);
		
		ballHealth = 100;
		ballHealthLabel.setText("Health: " + ballHealth);
		
		add(scoreLabel);
		scoreLabel.setBounds(10, 10, 150, 30);
		scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		add(ballHealthLabel);
		ballHealthLabel.setBounds(10, 40, 150, 30);
		ballHealthLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		ballHorizontalSpeed = 0;
		blockVerticalSpeed = 1;
		ballVerticalSpeed = blockVerticalSpeed;
		slowingDownSpeed = 0.0;
		currentBallJumpYDistance = 0;
		ballJumpSpeed = 0;
		ballHealthLosingSpeed = 0;
		
		isBallLosingHealth = false;
		isBallJumping = false;
		isBallFalling = false;
		stopBallSlowly = false;
		didScore = false;
		changedDirectionInAir = false;
		
		isRightKeyDown = false;
		isLeftKeyDown = false;
		
		changeBlocksXPositions();
		
		blockYPositioner = -25;
		
		for(int i = 0; i < blocks.length; i++) {
			healthBooster[i] = null;
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
		}
		
		ballX = blocksXPositions[3];
		ballY = 200;
		
		ball.setLocation(ballX, ballY);
		
		ball.changeColor(new Color(31, 122, 31));
		
		currentIndex = 3;
		previousIndex = 3;
		
		timer.restart();
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
					if(ballY + ball.getHeight() + ballVerticalSpeed >= blocksYPositions[i] && ballY <= blocksYPositions[i] + 5) {
						ballHorizontalSpeed = 0;
					}
				}
				if(isBallJumping) {
					int num = previousIndex - 1;
					if(num < 0) {
						num = blocks.length - 1;
					}
					if(ballX + ball.getWidth() + ballHorizontalSpeed > blocksXPositions[num] && ballX + ballHorizontalSpeed < blocksXPositions[num] + blocksWidth[num]) {
						if(blocksYPositions[num] + 5 + blockVerticalSpeed <= ballY + ballVerticalSpeed) {
							isBallJumping = false;
							ballVerticalSpeed = 4;
							return false;
						}
					}
				}
				if(blocks[i] instanceof RegularBlock || blocks[i] instanceof HalfRedBlock) {
					if(collisionCheck(i)) {
						if((blocks[i] instanceof HalfRedBlock)) {
							if(blocks[i].isRedOnRightSide() && ballX + ball.getWidth() > blocksXPositions[i] + blocksWidth[i]/2) {
								ballLoseHealth(true);
							} else if(blocks[i].isRedOnRightSide() == false && ballX < blocksXPositions[i] + blocksWidth[i]/2) {
								ballLoseHealth(true);
							} else if(isBallLosingHealth == true) {
								ballLoseHealth(false);
							}
						}
						
						return true;
					}
				} else if(blocks[i] instanceof WiperBlock) {
					int TBarXPosition = blocks[i].TBarXPosition();
					
					if(ballY + ball.getHeight() + ballVerticalSpeed > blocksYPositions[i] - blocks[i].TBarHeight() && ballY + ball.getHeight() <= blocksYPositions[i]) {
						if(ballX >= TBarXPosition + 5 && ballX + ballHorizontalSpeed <= TBarXPosition + 5) {
							ballHorizontalSpeed = 0;
							if(blocks[i].isTBarRight() == false && TBarXPosition > blocksXPositions[i]) {
								if(isLeftKeyDown) {
									if(TBarXPosition + 5 - 1 < blocksXPositions[i] + blocksWidth[i] - 1) {
										ballX = TBarXPosition + 5 - 1;
									}
								}
							} else {
								ballX = TBarXPosition + 5 + 1;
							}
						} else if(ballX + ball.getWidth() <= TBarXPosition && ballX + ball.getWidth() + ballHorizontalSpeed >= TBarXPosition) {
							ballHorizontalSpeed = 0;
							if(blocks[i].isTBarRight() && TBarXPosition + 5 < blocksXPositions[i] + blocksWidth[i]) {
								if(isRightKeyDown) {
									if(TBarXPosition - (int) ball.getWidth() + 1 > blocksXPositions[i]) {
										ballX = TBarXPosition - (int) ball.getWidth() + 1;
									}
								}
							} else {
								ballX = TBarXPosition - (int) ball.getWidth() - 1;
							}
						}
						ball.setLocation(ballX, ballY);
					}
					
					if(collisionCheck(i)) {
						return true;
					}
				} else if(blocks[i] instanceof SplitBlock) {
					int secondBlockXPosition = blocks[i].secondBlockXPosition();
					
					if(ballY + ball.getHeight() + ballVerticalSpeed >= blocksYPositions[i] && ballY + ball.getHeight() <= blocksYPositions[i]) {
						if(ballX + ball.getWidth() + ballHorizontalSpeed > blocksXPositions[i] && ballX < secondBlockXPosition - ball.getWidth() && ballX + ballHorizontalSpeed < secondBlockXPosition - ball.getWidth()) {
							currentIndex = i;
							ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight() - 1);
							return true;
						} else if(ballX + ball.getWidth() > secondBlockXPosition && ballX + ball.getWidth() + ballHorizontalSpeed > secondBlockXPosition && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) {
							currentIndex = i;
							ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight() - 1);
							return true;
						} else if(ballX + ball.getWidth() + ballHorizontalSpeed > blocksXPositions[i] && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) {
							if((ballX + ballHorizontalSpeed >= secondBlockXPosition - ball.getWidth()) || (ballX + ball.getWidth() + ballHorizontalSpeed <= secondBlockXPosition)) {
								if(ballY + ballVerticalSpeed < blocksYPositions[i] + 5) {
									ballX = secondBlockXPosition - (int) ball.getWidth();
									ballHorizontalSpeed = 0;
								}
							}
						}
					} else if(blocks[previousIndex] instanceof SplitBlock && ballY > blocksYPositions[previousIndex] + 5 && ballY + ball.getHeight() < blocksYPositions[previousIndex] + 5 + (ball.getHeight() * 2)) {
						if(ballX == secondBlockXPosition - (int) ball.getWidth()) {
							changeBallHorizontalSpeed();
						}
					}
				} else if(blocks[i] instanceof ShooterBlock) {
					if(collisionCheck(i)) {
						return true;
					}
				}
			}
		}
		
		if(isBallLosingHealth == true) {
			ballLoseHealth(false);
		}
		
		return false;
	}
	
	public boolean collisionCheck(int i) {
		if(ballX + ball.getWidth() + ballHorizontalSpeed > blocksXPositions[i] && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) {
			if(ballY + ball.getHeight() + ballVerticalSpeed >= blocksYPositions[i] && ballY + ball.getHeight() <= blocksYPositions[i]) {
				currentIndex = i;
				ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight() - 1);
				return true;
			} else if(blocks[i] instanceof WiperBlock) {
				int TBarXPosition = blocks[i].TBarXPosition();
				
				if(isBallJumping) {
					if((ballX + ball.getWidth() > blocksXPositions[previousIndex] && ballX < blocksXPositions[previousIndex] + blocksWidth[previousIndex]) && (ballY + ball.getHeight() < blocksYPositions[previousIndex] - blocks[previousIndex].TBarHeight())) {
						changeBallHorizontalSpeed();
					}
				}
				
				if(ballX + ball.getWidth() + ballHorizontalSpeed > TBarXPosition && ballX + ballHorizontalSpeed < TBarXPosition + 5) {
					if(ballY + ball.getHeight() + ballVerticalSpeed >= blocksYPositions[i] - blocks[i].TBarHeight() && ballY + ball.getHeight() <= blocksYPositions[i] - blocks[i].TBarHeight()) {
						currentIndex = i;
						ballY = (int)(blocksYPositions[currentIndex] - ball.getHeight() - blocks[i].TBarHeight() - 1);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void changeBallHorizontalSpeed(int speed) {
		if(isBallJumping || isBallFalling) {
			if(ballHorizontalSpeed > 0 && speed < 0) {
				changedDirectionInAir = true;
				ballHorizontalSpeed--;
				ballHorizontalSpeed *= -1;
			} else if(ballHorizontalSpeed < 0 && speed > 0) {
				changedDirectionInAir = true;
				ballHorizontalSpeed++;
				ballHorizontalSpeed *= -1;
			} else if(ballHorizontalSpeed == 0 && changedDirectionInAir == false) {
				ballHorizontalSpeed = speed;
			}
		}
		else {
			ballHorizontalSpeed = speed;
		}
	}
	
	public void changeBallHorizontalSpeed() {
		if(ballHorizontalSpeed == 0 && changedDirectionInAir == false) {
			if(isRightKeyDown) {
				ballHorizontalSpeed = 4;
			} else if(isLeftKeyDown) {
				ballHorizontalSpeed = -4;
			}
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
				blocks[index] = new RegularBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
				break;
			case 1:
				blocks[index] = new HalfRedBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
				break;
			case 2:
				blocks[index] = new WiperBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
				break;
			case 3:
				blocks[index] = new SplitBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], (int) ball.getWidth());
				break;
			case 4:
				blocks[index] = new ShooterBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
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
			ball.changeColor(new Color(31, 122, 31));
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
	
	public void enableButton(JButton button) {
		button.setEnabled(true);
		button.setBackground(Color.DARK_GRAY);
		button.setBorder(null);
		button.setForeground(Color.WHITE);
		button.setFocusable(false);
	}
	
	public void disableButton(JButton button) {
		button.setEnabled(false);
		button.setBackground(new Color(217, 217, 217));
		button.setBorder(null);
		button.setForeground(new Color(26, 26, 26));
		button.setFocusable(false);
	}
	
	public void menuButtons(boolean show) {
		if(show == false) {
			startgame.hide();
			customize.hide();
			store.hide();
			remove(startgame);
			remove(customize);
			remove(store);
		} else {
			add(startgame);
			add(customize);
			add(store);
			startgame.show();
			customize.show();
			store.show();
		}
	}
}
