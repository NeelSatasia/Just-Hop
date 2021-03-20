package gameWork;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
	
	Timer timer = new Timer(5, this);
	
	int ballX = 300;
	int ballY = 200;
	
	Ball ball = new Ball(ballX, ballY, 16, 16, new Color(31, 122, 31));
	
	Blocks[] blocks = new Blocks[10];
	int[] blocksXPositions = new int[blocks.length];
	int[] blocksYPositions = new int[blocks.length];
	int[] blocksWidth = new int[blocks.length];
	int[] blocksColorTransparency = new int[blocks.length];
	
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
	
	int highScore = 0;
	boolean didScore;
	int score;
	JLabel scoreLabel = new JLabel("Score: " + score);
	JLabel highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.CENTER);
	
	int totalCoins;
	int coins;
	int coinWorth = 0;
	JLabel coinsLabel = new JLabel("Current Coins: " + coins);
	JLabel totalCoinsLabel = new JLabel("Total Coins: " + totalCoins, SwingConstants.CENTER);
	
	int ballHealth;
	JLabel ballHealthLabel = new JLabel("Health: " + ballHealth);
	
	int differentTypesOfBlocks = 5;
	ArrayList<String> differentBlocksInGame = new ArrayList<String>();
	
	int ballHealthLosingSpeed;
	boolean isBallLosingHealth;
	
	boolean stopBallSlowly;
	double slowingDownSpeed;
	
	boolean isRightKeyDown;
	boolean isLeftKeyDown;
	
	boolean changedDirectionInAir;
	
	boolean ballFlyingAbility = false;
	boolean ballHasShield = false;
	boolean ballShootsTwoBullets = false;
	
	int shieldPower = 0;
	int shieldActivationAmount = 0;
	int flyPower = 0;
	int flyActivationAmount = 0;
	int bulletReloadSpeed = 50;
	
	boolean isPlayingGame = false;
	boolean pause;
	
	JLabel gameNameLabel = new JLabel(" \"Just Hop\" ", SwingConstants.CENTER);
	
	JCheckBox[] differentBlocks = new JCheckBox[differentTypesOfBlocks];
	JCheckBox[] modes = new JCheckBox[3];
	
	JButton startgame = new JButton("Start Game");
	JButton customize = new JButton("Customize");
	JButton store = new JButton("Store");
	JButton abilities = new JButton("Abilities");
	JButton upgrades = new JButton("Upgrades");
	JButton balls = new JButton("Balls");
	JButton music = new JButton("Music");
	JButton exit = new JButton("Exit");
	
	public GamePanel() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		modes[0] = new JCheckBox("Blocks With Random Sizes");
		modes[1] = new JCheckBox("Slippery Blocks");
		modes[2] = new JCheckBox("Transparent Blocks");
		
		differentBlocks[0] = new JCheckBox("Regular Blocks");
		differentBlocks[1] = new JCheckBox("HalfRed Blocks");
		differentBlocks[2] = new JCheckBox("Wiper Blocks");
		differentBlocks[3] = new JCheckBox("Split Blocks");
		differentBlocks[4] = new JCheckBox("Shooter Blocks");
		
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
		
		for(int i = 0; i < differentBlocks.length; i++) {
			differentBlocks[i].setFocusable(false);
			differentBlocks[i].setFont(new Font("Consolas", Font.PLAIN, 10));
			
			differentBlocks[i].setSelected(true);
			differentBlocksInGame.add(differentBlocks[i].getText());
			
			coinWorth++;
			
			int j = i;
			differentBlocks[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == differentBlocks[j]) {
						if(differentBlocks[j].isSelected()) {
							differentBlocks[j].setSelected(true);
							differentBlocksInGame.add(differentBlocks[j].getText());
							coinWorth++;
						} else {
							if(differentBlocksInGame.size() - 1 > 0) {
								differentBlocks[j].setSelected(false);
								differentBlocksInGame.remove(differentBlocks[j].getText());
								coinWorth--;
							} else {
								differentBlocks[j].setSelected(true);
							}
						}
					}
				}
			});
		}
		
		add(gameNameLabel);
		gameNameLabel.setBounds(0, 0, 800, 100);
		gameNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 80));
		gameNameLabel.setBackground(new Color(230, 92, 0));
		gameNameLabel.setOpaque(true);
		
		add(highScoreLabel);
		highScoreLabel.setBounds(200, 185, 400, 50);
		highScoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		
		add(totalCoinsLabel);
		totalCoinsLabel.setBounds(200, 235, 400, 40);
		totalCoinsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		add(startgame);
		startgame.setBounds(365, 290, 70, 30);
		enableButton(startgame);
		
		startgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPage(false);
				
				startGame();
				timer.start();
			}
		});
		
		add(customize);
		customize.setBounds(365, 330, 70, 30);
		enableButton(customize);
		
		customize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPage(false);
				add(exit);
				
				JLabel blocksLabel = new JLabel("Blocks");
				add(blocksLabel);
				blocksLabel.setBounds(200, 100, 100, 40);
				blocksLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
				
				JLabel modesLabel = new JLabel("Modes");
				add(modesLabel);
				modesLabel.setBounds(450, 100, 100, 40);
				modesLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
				
				int y = 150;
				
				for(int i = 0; i < differentBlocks.length; i++) {
					add(differentBlocks[i]);
					differentBlocks[i].setBounds(170, y, 120, 20);
					
					if(i < modes.length) {
						add(modes[i]);
						modes[i].setBounds(410, y, 180, 20);
					}
					y += 25;
				}
				
				exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						remove(exit);
						remove(blocksLabel);
						remove(modesLabel);
						for(int i = 0; i < differentBlocks.length; i++) {
							remove(differentBlocks[i]);
							if(i < modes.length) {
								remove(modes[i]);
							}
						}
						
						menuPage(true);
						repaint();
					}
				});
				
				repaint();
			}
		});
		
		add(store);
		store.setBounds(365, 370, 70, 30);
		enableButton(store);
		
		store.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPage(false);
				add(exit);
				
				storePage(true);
				
				exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						storePage(false);
						remove(exit);
						
						menuPage(true);
						
						repaint();
					}
				});
				
				repaint();
			}
		});
		
		abilities.setBounds(365, 175, 70, 30);
		enableButton(abilities);
		
		abilities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storePage(false);
				remove(exit);
				
				JLabel buyLabel = new JLabel("Buy Abilities", SwingConstants.CENTER);
				add(buyLabel);
				buyLabel.setBounds(300, 170, 200, 50);
				buyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
				
				JButton flyingButton = new JButton("Fly Power");
				add(flyingButton);
				flyingButton.setBounds(365, 230, 70, 30);
				if(totalCoins >= 500 && highScore >= 200 && ballFlyingAbility == false) {
					enableButton(flyingButton);
				} else {
					disableButton(flyingButton);
				}
				
				flyingButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						disableButton(flyingButton);
						ballFlyingAbility = true;
						totalCoins -= 500;
						flyPower = -1;
						flyActivationAmount = 5000;
					}
				});
				
				flyingButton.addMouseListener(new MouseAdapter() {
					JLabel abilityInfoLabel1 = new JLabel("500 coins or more");
					JLabel abilityInfoLabel2 = new JLabel("High score of 200 or more");
					
				    public void mouseEntered(MouseEvent e) {
				    	add(abilityInfoLabel1);
				    	abilityInfoLabel1.setBounds(flyingButton.getX() + flyingButton.getWidth() + 5, flyingButton.getY(), 200, 17);
				    	abilityInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(abilityInfoLabel2);
				    	abilityInfoLabel2.setBounds(flyingButton.getX() + flyingButton.getWidth() + 5, abilityInfoLabel1.getY() + abilityInfoLabel1.getHeight() + 3, 200, 17);
				    	abilityInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	repaint();
				    }

				    public void mouseExited(MouseEvent e) {
				        remove(abilityInfoLabel1);
				        remove(abilityInfoLabel2);
				        repaint();
				    }
				});
				
				JButton shieldButton = new JButton("Shield");
				add(shieldButton);
				shieldButton.setBounds(365, 270, 70, 30);
				if(totalCoins > 800 && highScore >= 300 && ballHasShield == false) {
					enableButton(shieldButton);
				} else {
					disableButton(shieldButton);
				}
				
				shieldButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						disableButton(shieldButton);
						ballHasShield = true;
						totalCoins -= 800;
						shieldPower = 2;
						shieldActivationAmount = 5000;
					}
				});
				
				shieldButton.addMouseListener(new MouseAdapter() {
					JLabel abilityInfoLabel1 = new JLabel("800 coins or more");
					JLabel abilityInfoLabel2 = new JLabel("High score of 300 or more");
					
				    public void mouseEntered(MouseEvent e) {
				    	add(abilityInfoLabel1);
				    	abilityInfoLabel1.setBounds(shieldButton.getX() + shieldButton.getWidth() + 5, shieldButton.getY(), 200, 17);
				    	abilityInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(abilityInfoLabel2);
				    	abilityInfoLabel2.setBounds(shieldButton.getX() + shieldButton.getWidth() + 5, abilityInfoLabel1.getY() + abilityInfoLabel1.getHeight() + 3, 200, 17);
				    	abilityInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	repaint();
				    }

				    public void mouseExited(MouseEvent e) {
				        remove(abilityInfoLabel1);
				        remove(abilityInfoLabel2);
				        repaint();
				    }
				});
				
				JButton twoBulletsButton = new JButton("Two Bullets");
				add(twoBulletsButton);
				twoBulletsButton.setBounds(365, 310, 70, 30);
				if(totalCoins > 1000 && highScore >= 350 && ballShootsTwoBullets == false) {
					enableButton(twoBulletsButton);
				} else {
					disableButton(twoBulletsButton);
				}
				
				twoBulletsButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						disableButton(twoBulletsButton);
						ballShootsTwoBullets = true;
						totalCoins -= 1000;
					}
				});
				
				twoBulletsButton.addMouseListener(new MouseAdapter() {
					JLabel abilityInfoLabel1 = new JLabel("1000 coins or more");
					JLabel abilityInfoLabel2 = new JLabel("High score of 350 or more");
					
				    public void mouseEntered(MouseEvent e) {
				    	add(abilityInfoLabel1);
				    	abilityInfoLabel1.setBounds(twoBulletsButton.getX() + twoBulletsButton.getWidth() + 5, twoBulletsButton.getY(), 200, 17);
				    	abilityInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(abilityInfoLabel2);
				    	abilityInfoLabel2.setBounds(twoBulletsButton.getX() + twoBulletsButton.getWidth() + 5, abilityInfoLabel1.getY() + abilityInfoLabel1.getHeight() + 3, 200, 17);
				    	abilityInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	repaint();
				    }

				    public void mouseExited(MouseEvent e) {
				        remove(abilityInfoLabel1);
				        remove(abilityInfoLabel2);
				        repaint();
				    }
				});
				
				JButton exitAbilitiesPage = new JButton("Exit");
				add(exitAbilitiesPage);
				exitAbilitiesPage.setBounds(15, 15, 60, 30);
				enableButton(exitAbilitiesPage);
				exitAbilitiesPage.setBackground(new Color(204, 0, 0));
				
				exitAbilitiesPage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						remove(buyLabel);
						remove(flyingButton);
						remove(shieldButton);
						remove(twoBulletsButton);
						remove(exitAbilitiesPage);
						storePage(true);
						add(exit);
						
						repaint();
					}
				});
				
				repaint();
			}
		});
		
		upgrades.setBounds(365, 215, 70, 30);
		enableButton(upgrades);
		
		upgrades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						storePage(true);
					}
				});
			}
		});
		
		balls.setBounds(370, 255, 60, 30);
		enableButton(balls);
		
		balls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						storePage(true);
					}
				});
			}
		});
		
		exit.setBounds(15, 15, 60, 30);
		enableButton(exit);
		exit.setBackground(new Color(204, 0, 0));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(ballHealth == 0 && timer.isRunning() && pause == false) {
			timer.stop();
			isPlayingGame = false;
			
			totalCoins += coins;
			
			if(score > highScore) {
				highScore = score;
				highScoreLabel.setText("High Score: " + highScore);
			}
			
			JLabel newHighScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.CENTER);
			add(newHighScoreLabel);
			newHighScoreLabel.setBounds(250, 150, 300, 50);
			newHighScoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			
			JLabel newTotalCoinsLabel = new JLabel("Total Coins: " + totalCoins, SwingConstants.CENTER);
			add(newTotalCoinsLabel);
			newTotalCoinsLabel.setBounds(225, 210, 350, 30);
			newTotalCoinsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
			
			JButton tryAgainButton = new JButton("Try Again");
			add(tryAgainButton);
			tryAgainButton.setBounds(370, 260, 60, 30);
			enableButton(tryAgainButton);
			
			JButton exit = new JButton("Exit");
			add(exit);
			exit.setBounds(370, 300, 60, 30);
			enableButton(exit);
			exit.setBackground(new Color(204, 0, 0));
			
			tryAgainButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					remove(newHighScoreLabel);
					remove(newTotalCoinsLabel);
					remove(tryAgainButton);
					remove(exit);
					
					repaint();
					startGame();
				}
			});
			
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					remove(ballHealthLabel);
					remove(scoreLabel);
					remove(coinsLabel);
					remove(newHighScoreLabel);
					remove(newTotalCoinsLabel);
					remove(tryAgainButton);
					remove(exit);
					
					menuPage(true);
					repaint();
				}
			});
			
		} else if(timer.isRunning() && pause == false) {
			ball.setLocation(ballX, ballY);
			ball.draw(g);
			
			for(int i = 0; i < blocks.length; i++) {
				if(blocks[i].y >= 100 * blocks.length) {
					generateRandomBlock(i);
					
					if(ballHealth < 50) {
						int healthBoosterChance = (int)(Math.random() * 21);
						if(healthBoosterChance <= 10) {
							blocks[i].addHealthBooster(true);
						}
					}
					
					int coinChance = (int)(Math.random() * 21);
					if(coinChance > 10) {
						blocks[i].addCoin(true);
					}
				} else {
					blocks[i].setBounds(blocksXPositions[i], blocksYPositions[i], blocksWidth[i], 5);
					
					blocks[i].changeColorTransparency(blocksColorTransparency[i]);
					
					blocks[i].changeTBarXPosition();
				}
				
				blocks[i].draw(g);
			}
		} else if(pause) {
			timer.stop();
			
			g.setFont(new Font("Times New Roman", Font.PLAIN, 50));
			g.drawString("Paused", 340, 260);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
			g.drawString("(Press Spacebar To Unpause)", 305, 285);
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
			if(blocks[i].getHealthBooster() != null && blocks[i].getHealthBooster().intersects(ball)) {
				blocks[i].addHealthBooster(false);
			}
			
			if(blocks[i] instanceof ShooterBlock) {
				for(int j = 0; j < blocks[i].getBulletsList().size(); j++) {
					if(blocks[i].getBulletsList().get(j).intersects(ball)) {		
						if(ballHealth - 10 >= 0) {
							ballHealth -= 10;
						} else {
							ballHealth = 0;
						}
						
						ballHealthLabel.setText("Health: " + ballHealth);
						
						blocks[i].removeBullet(j);
						break;
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
			
			int j = 0;
			while(j < ball.bullets.size()) {
				if(blocks[i].getCoin() != null && blocks[i].getCoin().intersects(ball.bullets.get(j))) {
					ball.bullets.remove(j);
					blocks[i].addCoin(false);
					coins += coinWorth;
					coinsLabel.setText("Coins: " + coins);
				} else if(blocks[i].intersects(ball.bullets.get(j))) {
					ball.bullets.remove(j);
				} else {
					j++;
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
		ballHealth = 100;
		
		coins = 0;
		
		add(scoreLabel);
		scoreLabel.setBounds(10, 10, 150, 30);
		scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		scoreLabel.setText("Score: " + score);
		
		add(ballHealthLabel);
		ballHealthLabel.setBounds(10, 40, 150, 30);
		ballHealthLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		ballHealthLabel.setText("Health: " + ballHealth);
		
		add(coinsLabel);
		coinsLabel.setBounds(10, 70, 150, 30);
		coinsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		coinsLabel.setText("Coins: " + coins);
		
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
		
		ball.shootBullets = false;
		
		isPlayingGame = true;
		pause = false;
		
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
		if(ballHorizontalSpeed == 0 && changedDirectionInAir == false && pause == false) {
			if(isRightKeyDown) {
				ballHorizontalSpeed = 4;
			} else if(isLeftKeyDown) {
				ballHorizontalSpeed = -4;
			}
		}
	}
	
	public void makeBallJump() {
		if(isBallJumping == false && isBallFalling == false && pause == false) {
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
	
	public void generateRandomBlock(int index) {
		int randBlock = (int)(Math.random() * differentBlocksInGame.size());
		
		for(int i = 0; i < differentBlocksInGame.size(); i++) {
			if(i == randBlock) {
				switch(differentBlocksInGame.get(i)) {
					case "Regular Blocks":
						blocks[index] = new RegularBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
						break;
					case "HalfRed Blocks":
						blocks[index] = new HalfRedBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
						break;
					case "Wiper Blocks":
						blocks[index] = new WiperBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
						break;
					case "Split Blocks":
						blocks[index] = new SplitBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], (int) ball.getWidth());
						break;
					case "Shooter Blocks":
						blocks[index] = new ShooterBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
						break;
				}
				
				break;
			}
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
	
	public void menuPage(boolean show) {
		if(show == false) {
			remove(gameNameLabel);
			remove(highScoreLabel);
			remove(totalCoinsLabel);
			remove(startgame);
			remove(customize);
			remove(store);
		} else {
			add(gameNameLabel);
			add(highScoreLabel);
			add(totalCoinsLabel);
			add(startgame);
			add(customize);
			add(store);
		}
	}
	
	public void storePage(boolean show) {
		if(show) {
			add(abilities);
			add(upgrades);
			add(balls);
		} else {
			remove(abilities);
			remove(upgrades);
			remove(balls);
		}
	}
	
	public void pauseTheGame(boolean pauseit) {
		if(isPlayingGame) {
			pause = pauseit;
			if(pause == false) {
				timer.start();
			}
		}
	}
	
	public boolean isGamePaused() {
		return pause;
	}

	public void ballShootBullets(boolean b) {
		if(isPlayingGame && pause == false) {
			ball.shootBullets = b;
		}
	}
}
