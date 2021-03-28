package gameWork;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
	
	int[] totalBalls = new int[4];
	int[] ballsPrice = new int[totalBalls.length];
	
	Ball ball = new Ball(ballX, ballY);
	
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
	
	boolean ballIntersectionUnderBlock = false;
	
	int currentBallJumpYDistance;
	double ballJumpSpeed;
	
	int changeBlocksSpeedTimer;
	
	int currentIndex;
	private int previousIndex;
	
	int highScore = 1000;
	boolean didScore;
	int score;
	JLabel scoreLabel = new JLabel("Score: " + score);
	JLabel highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.CENTER);
	
	int totalCoins = 15000;
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
	
	String currentAbility = "Fly";
	
	boolean ballFlyAbility = true;
	boolean ballShieldAbility = false;
	boolean ballFreezeAbility = false;
	
	int shieldPower = 2;
	int shieldActivationAmount = 300;
	int shieldPowerLevel = 1;
	int shieldActivationAmountLevel = 1;
	int shieldPowerUpgradePrice = 60;
	int shieldActivationAmountUpgradePrice = 80;
	
	int flyPower = -1;
	int flyActivationAmount = 200;
	int flyPowerLevel = 1;
	int flyActivationAmountLevel = 1;
	int flyPowerUpgradePrice = 50;
	int flyActivationAmountUpgradePrice = 70;
	
	int ballBulletReloadSpeedLevel = 1;
	int ballBulletReloadSpeedUpgradePrice = 60;
	
	int freezeActivationAmount = 200;
	int freezeActivationAmountLevel = 1;
	int freezeActivationAmountUpgradePrice = 60;
	
	boolean usingAbility = false;
	int abilityUseCounter = 0;
	int maxAbilityUse = 0;
	int abilityReloadCounter = 2000;
	boolean isAbilityReloading = false;
	
	JLabel currentAbilityLabel = new JLabel();
	JProgressBar abilityProgressBar = new JProgressBar();
	
	boolean showBalls = false;
	
	boolean isPlayingGame = false;
	boolean pause;
	
	JLabel gameNameLabel = new JLabel("Block To Block", SwingConstants.CENTER);
	
	JCheckBox[] differentBlocks = new JCheckBox[differentTypesOfBlocks];
	JCheckBox[] modes = new JCheckBox[3];
	
	JButton startgame = new JButton("Start Game");
	JButton customize = new JButton("Customize");
	JButton store = new JButton("Store");
	JButton abilities = new JButton("Abilities");
	JButton upgrades = new JButton("Upgrades");
	JButton balls = new JButton("Balls");
	JButton exit = new JButton("Exit");
	
	public GamePanel() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		int ballPrice = 40;
		for(int i = 0; i < totalBalls.length; i++) {
			totalBalls[i] = 0;
			ballsPrice[i] = ballPrice;
			ballPrice += 10;
		}
		
		totalBalls[0] = 1;
		
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
				
				JLabel buyLabel = new JLabel("Abilities", SwingConstants.CENTER);
				add(buyLabel);
				buyLabel.setBounds(300, 130, 200, 50);
				buyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
				
				JButton flyingButton = new JButton("Fly Ability");
				add(flyingButton);
				flyingButton.setBounds(365, 190, 70, 30);
				
				JButton shieldButton = new JButton("Shield");
				add(shieldButton);
				shieldButton.setBounds(365, 230, 70, 30);
				
				JButton freezeAbilityButton = new JButton("Freeze Blocks");
				add(freezeAbilityButton);
				freezeAbilityButton.setBounds(355, 310, 90, 30);
				
				if((totalCoins >= 500 && highScore >= 100 && ballFlyAbility == false) || (ballFlyAbility && currentAbility.equals("Fly") == false)) {
					enableButton(flyingButton);
				} else {
					disableButton(flyingButton);
				}
				
				JLabel abilityInfoLabel1 = new JLabel("");
				JLabel abilityInfoLabel2 = new JLabel("");
				
				flyingButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(ballFlyAbility == false) {
							ballFlyAbility = true;
							totalCoins -= 500;
							abilityInfoLabel1.setText("Equip");
							abilityInfoLabel1.setForeground(new Color(41, 163, 41));
							abilityInfoLabel2.setText("");
						} else {
							currentAbility = "Fly";
							disableButton(flyingButton);
							abilityInfoLabel1.setText("Equipped");
							
							if(ballShieldAbility) {
								enableButton(shieldButton);
							}
							if(ballFreezeAbility) {
								enableButton(freezeAbilityButton);
							}
						}
					}
				});
				
				flyingButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	add(abilityInfoLabel1);
				    	abilityInfoLabel1.setBounds(flyingButton.getX() + flyingButton.getWidth() + 5, flyingButton.getY(), 200, 17);
				    	abilityInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	if(ballFlyAbility) {
				    		if(currentAbility.equals("Fly")) {
				    			abilityInfoLabel1.setText("Equipped");
				    		} else {
				    			abilityInfoLabel1.setText("Equip");
				    		}
				    	} else {
				    		abilityInfoLabel1.setText("500 coins");
				    	}
				    	
				    	add(abilityInfoLabel2);
				    	abilityInfoLabel2.setBounds(flyingButton.getX() + flyingButton.getWidth() + 5, abilityInfoLabel1.getY() + abilityInfoLabel1.getHeight() + 3, 200, 17);
				    	abilityInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	if(ballFlyAbility == false) {
				    		abilityInfoLabel2.setText("High score of 100 or more");
				    	} else {
				    		abilityInfoLabel2.setText("");
				    	}
				    	
				    	if(ballFlyAbility || (totalCoins >= 500 && ballFlyAbility == false)) {
				    		abilityInfoLabel1.setForeground(new Color(41, 163, 41));
				    	} else {
				    		abilityInfoLabel1.setForeground(new Color(204, 0, 82));
				    	}
				    	
				    	if(ballFlyAbility || (highScore >= 100 && ballFlyAbility == false)) {
				    		abilityInfoLabel2.setForeground(new Color(41, 163, 41));
				    	} else {
				    		abilityInfoLabel2.setForeground(new Color(204, 0, 82));
				    	}
				    	
				    	repaint();
				    }

				    public void mouseExited(MouseEvent e) {
				        remove(abilityInfoLabel1);
				        remove(abilityInfoLabel2);
				        repaint();
				    }
				});
				
				
				if((totalCoins > 800 && highScore >= 150 && ballShieldAbility == false) || (ballShieldAbility && currentAbility.equals("Shield") == false)) {
					enableButton(shieldButton);
				} else {
					disableButton(shieldButton);
				}
				
				shieldButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(ballShieldAbility == false) {
							ballShieldAbility = true;
							totalCoins -= 800;
							abilityInfoLabel1.setText("Equip");
							abilityInfoLabel1.setForeground(new Color(41, 163, 41));
							abilityInfoLabel2.setText("");
						} else {
							currentAbility = "Shield";
							disableButton(shieldButton);
							abilityInfoLabel1.setText("Equipped");
							
							if(ballFlyAbility) {
								enableButton(flyingButton);
							}
							if(ballFreezeAbility) {
								enableButton(freezeAbilityButton);
							}
						}
					}
				});
				
				shieldButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	add(abilityInfoLabel1);
				    	abilityInfoLabel1.setBounds(shieldButton.getX() + shieldButton.getWidth() + 5, shieldButton.getY(), 200, 17);
				    	abilityInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	if(ballShieldAbility) {
					    	if(currentAbility.equals("Shield")) {
					    		abilityInfoLabel1.setText("Equipped");
					    	} else {
					    		abilityInfoLabel1.setText("Equip");
					    	}
				    	} else {
				    		abilityInfoLabel1.setText("800 coins");
				    	}
				    	
				    	add(abilityInfoLabel2);
				    	abilityInfoLabel2.setBounds(shieldButton.getX() + shieldButton.getWidth() + 5, abilityInfoLabel1.getY() + abilityInfoLabel1.getHeight() + 3, 200, 17);
				    	abilityInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	if(ballShieldAbility == false) {
				    		abilityInfoLabel2.setText("High score of 150 or more");
				    	} else {
				    		abilityInfoLabel2.setText("");
				    	}
				    	
				    	if(ballShieldAbility || (totalCoins >= 800 && ballShieldAbility == false)) {
				    		abilityInfoLabel1.setForeground(new Color(41, 163, 41));
				    	} else {
				    		abilityInfoLabel1.setForeground(new Color(204, 0, 82));
				    	}
				    	
				    	if(ballShieldAbility || (highScore >= 150 && ballShieldAbility == false)) {
				    		abilityInfoLabel2.setForeground(new Color(41, 163, 41));
				    	} else {
				    		abilityInfoLabel2.setForeground(new Color(204, 0, 82));
				    	}
				    	
				    	repaint();
				    }

				    public void mouseExited(MouseEvent e) {
				        remove(abilityInfoLabel1);
				        remove(abilityInfoLabel2);
				        repaint();
				    }
				});
				
				JButton twoBulletsButton = new JButton("Two Bullets At Once");
				add(twoBulletsButton);
				twoBulletsButton.setBounds(340, 270, 120, 30);
				if(totalCoins > 1000 && highScore >= 200 && ball.twoBulletsAtOnce == false) {
					enableButton(twoBulletsButton);
				} else {
					disableButton(twoBulletsButton);
				}
				
				twoBulletsButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						disableButton(twoBulletsButton);
						ball.twoBulletsAtOnce = true;
						totalCoins -= 1000;
						abilityInfoLabel1.setText("Bought (Permanent Ability)");
						abilityInfoLabel1.setForeground(new Color(41, 163, 41));
						abilityInfoLabel2.setText("");
					}
				});
				
				twoBulletsButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	add(abilityInfoLabel1);
				    	abilityInfoLabel1.setBounds(twoBulletsButton.getX() + twoBulletsButton.getWidth() + 5, twoBulletsButton.getY(), 200, 17);
				    	abilityInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	if(ball.twoBulletsAtOnce) {
				    		abilityInfoLabel1.setText("Bought (Permanent Ability)");
				    	} else {
				    		abilityInfoLabel1.setText("1000 coins");
				    	}
				    	
				    	add(abilityInfoLabel2);
				    	abilityInfoLabel2.setBounds(twoBulletsButton.getX() + twoBulletsButton.getWidth() + 5, abilityInfoLabel1.getY() + abilityInfoLabel1.getHeight() + 3, 200, 17);
				    	abilityInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	if(ball.twoBulletsAtOnce) {
				    		abilityInfoLabel2.setText("");
				    	} else {
				    		abilityInfoLabel2.setText("High score of 200 or more");
				    	}
				    	
				    	if(ball.twoBulletsAtOnce || (totalCoins >= 1000 && ball.twoBulletsAtOnce == false)) {
				    		abilityInfoLabel1.setForeground(new Color(41, 163, 41));
				    	} else {
				    		abilityInfoLabel1.setForeground(new Color(204, 0, 82));
				    	}
				    	
				    	if(ball.twoBulletsAtOnce || (highScore >= 200 && ball.twoBulletsAtOnce == false)) {
				    		abilityInfoLabel2.setForeground(new Color(41, 163, 41));
				    	} else {
				    		abilityInfoLabel2.setForeground(new Color(204, 0, 82));
				    	}
				    	repaint();
				    }

				    public void mouseExited(MouseEvent e) {
				        remove(abilityInfoLabel1);
				        remove(abilityInfoLabel2);
				        repaint();
				    }
				});
				
				
				if((totalCoins >= 1200 && ballFreezeAbility == false) || (ballFreezeAbility && currentAbility.equals("Freeze Time") == false)) {
					enableButton(freezeAbilityButton);
				} else {
					disableButton(freezeAbilityButton);
				}
				
				freezeAbilityButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(ballFreezeAbility == false) {
							ballFreezeAbility = true;
							totalCoins -= 1200;
							abilityInfoLabel1.setText("Equip");
							abilityInfoLabel1.setForeground(new Color(41, 163, 41));
						} else {
							currentAbility = "Freeze Time";
							disableButton(freezeAbilityButton);
							abilityInfoLabel1.setText("Equipped");
							
							if(ballFlyAbility) {
								enableButton(flyingButton);
							}
							if(ballShieldAbility) {
								enableButton(shieldButton);
							}
						}
					}
				});
				
				freezeAbilityButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	add(abilityInfoLabel1);
				    	abilityInfoLabel1.setBounds(freezeAbilityButton.getX() + freezeAbilityButton.getWidth() + 5, freezeAbilityButton.getY(), 200, 17);
				    	abilityInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	if(ballFreezeAbility) {
				    		if(currentAbility.equals("Freeze Time")) {
				    			abilityInfoLabel1.setText("Equipped");
				    		} else {
				    			abilityInfoLabel1.setText("Equip");
				    		}
				    	} else {
				    		abilityInfoLabel1.setText("1200 Coins");
				    	}
				    	
				    	if(ballFreezeAbility || (totalCoins >= 1200 && ballFreezeAbility == false)) {
				    		abilityInfoLabel1.setForeground(new Color(41, 163, 41));
				    	} else {
				    		abilityInfoLabel1.setForeground(new Color(204, 0, 82));
				    	}
				    	
				    	repaint();
				    }

				    public void mouseExited(MouseEvent e) {
				        remove(abilityInfoLabel1);
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
						remove(freezeAbilityButton);
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
				storePage(false);
				remove(exit);
				
				JLabel upgradesLabel = new JLabel("Upgrades", SwingConstants.CENTER);
				add(upgradesLabel);
				upgradesLabel.setBounds(300, 130, 200, 50);
				upgradesLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
				
				JButton flyPowerButton = new JButton("Fly Power");
				add(flyPowerButton);
				flyPowerButton.setBounds(360, 190, 80, 30);
				if(ballFlyAbility && flyPowerLevel < 4 && totalCoins >= flyPowerUpgradePrice) {
					enableButton(flyPowerButton);
				} else {
					disableButton(flyPowerButton);
				}
				
				JLabel upgradeInfoLabel1 = new JLabel("");
				JLabel upgradeInfoLabel2 = new JLabel("");
				
				flyPowerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						totalCoins -= flyPowerUpgradePrice;
						flyPower--;
						flyPowerLevel++;
						flyPowerUpgradePrice += 20;
						
						if(ballFlyAbility == false || flyPowerLevel == 4 || totalCoins < flyPowerUpgradePrice) {
							disableButton(flyPowerButton);
						}
					}
				});
				
				upgradeButtonHover(flyPowerButton, ballFlyAbility, flyPowerLevel, 4, flyPowerUpgradePrice, upgradeInfoLabel1, upgradeInfoLabel2);
				
				JButton flyActivationAmountButton = new JButton("Fly Time");
				add(flyActivationAmountButton);
				flyActivationAmountButton.setBounds(360, 230, 80, 30);
				if(ballFlyAbility && flyActivationAmountLevel < 3 && totalCoins >= flyActivationAmountUpgradePrice) {
					enableButton(flyActivationAmountButton);
				} else {
					disableButton(flyActivationAmountButton);
				}
				
				flyActivationAmountButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						flyActivationAmount += 100;
						totalCoins -= flyActivationAmountUpgradePrice;
						flyActivationAmountLevel++;
						flyActivationAmountUpgradePrice += 30;
						
						if(ballFlyAbility == false || flyActivationAmountLevel == 3 || totalCoins < flyActivationAmountUpgradePrice) {
							disableButton(flyActivationAmountButton);
						}
					}
				});
				
				upgradeButtonHover(flyActivationAmountButton, ballFlyAbility, flyActivationAmountLevel, 3, flyActivationAmountUpgradePrice, upgradeInfoLabel1, upgradeInfoLabel2);
				
				JButton shieldPowerButton = new JButton("Shield Power");
				add(shieldPowerButton);
				shieldPowerButton.setBounds(355, 270, 90, 30);
				if(ballShieldAbility && shieldPowerLevel < 5 && totalCoins >= shieldPowerUpgradePrice) {
					enableButton(shieldPowerButton);
				} else {
					disableButton(shieldPowerButton);
				}
				
				shieldPowerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						shieldPower += 2;
						totalCoins -= shieldPowerUpgradePrice;
						shieldPowerLevel++;
						shieldPowerUpgradePrice += 20;
						
						if(ballShieldAbility == false || shieldPowerLevel == 5 || totalCoins < shieldPowerUpgradePrice) {
							disableButton(shieldPowerButton);
						}
					}
				});
				
				upgradeButtonHover(shieldPowerButton, ballShieldAbility, shieldPowerLevel, 5, shieldPowerUpgradePrice, upgradeInfoLabel1, upgradeInfoLabel2);
				
				JButton shieldActivationAmountButton = new JButton("Shield Time");
				add(shieldActivationAmountButton);
				shieldActivationAmountButton.setBounds(355, 310, 90, 30);
				if(ballShieldAbility && shieldActivationAmountLevel < 5 && totalCoins >= shieldActivationAmountUpgradePrice) {
					enableButton(shieldActivationAmountButton);
				} else {
					disableButton(shieldActivationAmountButton);
				}
				
				shieldActivationAmountButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						shieldActivationAmount += 100;
						totalCoins -= shieldActivationAmountUpgradePrice;
						shieldActivationAmountLevel++;
						shieldActivationAmountUpgradePrice += 30;
						
						if(ballShieldAbility == false || shieldActivationAmountLevel == 5 || totalCoins < shieldActivationAmountUpgradePrice) {
							disableButton(shieldActivationAmountButton);
						}
					}
				});
				
				upgradeButtonHover(shieldActivationAmountButton, ballShieldAbility, shieldActivationAmountLevel, 5, shieldActivationAmountUpgradePrice, upgradeInfoLabel1, upgradeInfoLabel2);
				
				JButton freezeActivationAmountButton = new JButton("Freeze Time");
				add(freezeActivationAmountButton);
				freezeActivationAmountButton.setBounds(355, 350, 90, 30);
				if(ballFreezeAbility && freezeActivationAmountLevel < 5 && totalCoins >= freezeActivationAmountUpgradePrice) {
					enableButton(freezeActivationAmountButton);
				} else {
					disableButton(freezeActivationAmountButton);
				}
				
				freezeActivationAmountButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						freezeActivationAmount += 100;
						totalCoins -= freezeActivationAmountUpgradePrice;
						freezeActivationAmountLevel++;
						freezeActivationAmountUpgradePrice += 40;
						
						if(ballFreezeAbility == false || freezeActivationAmountLevel == 5 || totalCoins < freezeActivationAmountUpgradePrice) {
							disableButton(freezeActivationAmountButton);
						}
					}
				});
				
				upgradeButtonHover(freezeActivationAmountButton, ballFreezeAbility, freezeActivationAmountLevel, 5, freezeActivationAmountUpgradePrice, upgradeInfoLabel1, upgradeInfoLabel2);
				
				JButton bulletReloadSpeedButton = new JButton("Reload Speed");
				add(bulletReloadSpeedButton);
				bulletReloadSpeedButton.setBounds(355, 390, 90, 30);
				if(ballBulletReloadSpeedLevel < 5 && totalCoins >= ballBulletReloadSpeedUpgradePrice) {
					enableButton(bulletReloadSpeedButton);
				} else {
					disableButton(bulletReloadSpeedButton);
				}
				
				bulletReloadSpeedButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ball.bulletReloadSpeed -= 10;
						totalCoins -= ballBulletReloadSpeedUpgradePrice;
						ballBulletReloadSpeedLevel++;
						ballBulletReloadSpeedUpgradePrice += 30;
						
						if(ballBulletReloadSpeedLevel == 3 || totalCoins < ballBulletReloadSpeedUpgradePrice) {
							disableButton(bulletReloadSpeedButton);
						}
					}
				});
				
				bulletReloadSpeedButton.addMouseListener(new MouseAdapter() {
					JLabel abilityInfoLabel1 = new JLabel();
					JLabel abilityInfoLabel2 = new JLabel();
					
				    public void mouseEntered(MouseEvent e) {
				    	if(ballBulletReloadSpeedLevel < 3) {
							abilityInfoLabel1.setText("Upgrade To Level " + (ballBulletReloadSpeedLevel + 1));
						} else if(ballBulletReloadSpeedLevel == 3) {
							abilityInfoLabel1.setText("Reached Maximum Level: " + ballBulletReloadSpeedLevel);
						}
				    	
				    	add(abilityInfoLabel1);
				    	abilityInfoLabel1.setBounds(bulletReloadSpeedButton.getX() + bulletReloadSpeedButton.getWidth() + 5, bulletReloadSpeedButton.getY(), 200, 17);
				    	abilityInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(abilityInfoLabel2);
				    	if(ballBulletReloadSpeedLevel < 3) {
					    	abilityInfoLabel2.setBounds(bulletReloadSpeedButton.getX() + bulletReloadSpeedButton.getWidth() + 5, abilityInfoLabel1.getY() + abilityInfoLabel1.getHeight() + 1, 200, 17);
					    	abilityInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
					    	abilityInfoLabel2.setText(ballBulletReloadSpeedUpgradePrice + " Coins");
				    	}
				    	
				    	if(ballBulletReloadSpeedLevel < 3 && totalCoins >= ballBulletReloadSpeedUpgradePrice) {
				    		abilityInfoLabel1.setForeground(new Color(41, 163, 41));
				    		abilityInfoLabel2.setForeground(new Color(41, 163, 41));
				    	} else {
				    		abilityInfoLabel1.setForeground(new Color(204, 0, 82));
				    		abilityInfoLabel2.setForeground(new Color(204, 0, 82));
				    	}
				    	
				    	repaint();
				    }

				    public void mouseExited(MouseEvent e) {
				        remove(abilityInfoLabel1);
				        remove(abilityInfoLabel2);
				        repaint();
				    }
				});
				
				
				JButton exitUpgradesPage = new JButton("Exit");
				add(exitUpgradesPage);
				exitUpgradesPage.setBounds(15, 15, 60, 30);
				enableButton(exitUpgradesPage);
				exitUpgradesPage.setBackground(new Color(204, 0, 0));
				
				exitUpgradesPage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						remove(upgradesLabel);
						remove(flyPowerButton);
						remove(flyActivationAmountButton);
						remove(shieldPowerButton);
						remove(shieldActivationAmountButton);
						remove(freezeActivationAmountButton);
						remove(bulletReloadSpeedButton);
						remove(exitUpgradesPage);
						
						storePage(true);
						add(exit);
						
						repaint();
					}
				});
				
				repaint();
			}
		});
		
		balls.setBounds(370, 255, 60, 30);
		enableButton(balls);
		
		balls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storePage(false);
				remove(exit);
				
				JLabel ballsLabel = new JLabel("Balls", SwingConstants.CENTER);
				add(ballsLabel);
				ballsLabel.setBounds(300, 130, 200, 50);
				ballsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
				
				showBalls = true;
				
				int buttonY = 190;
				
				JButton[] ballsButtons = new JButton[totalBalls.length];
				JLabel ballInfoLabel1 = new JLabel("Buy");
				
				for(int i = 0; i < totalBalls.length; i++) {
					ballsButtons[i] = new JButton("Buy");
					add(ballsButtons[i]);
					ballsButtons[i].setBounds(410, buttonY, 70, 20);
					buttonY += 30;
					if(totalCoins >= ballsPrice[i] && totalBalls[i] == 0) {
						enableButton(ballsButtons[i]);
					} else if(totalBalls[i] > 0 && ball.type != totalBalls[i]) {
						ballsButtons[i].setText("Equip");
						enableButton(ballsButtons[i]);
					} else if(totalBalls[i] > 0 && ball.type == totalBalls[i]) {
						ballsButtons[i].setText("Equipped");
						disableButton(ballsButtons[i]);
					} else {
						disableButton(ballsButtons[i]);
					}
					
					int j = i;
					
					ballsButtons[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(totalBalls[j] == 0) {
								totalCoins -= ballsPrice[j];
								totalBalls[j] = j + 1;
								ballInfoLabel1.setText("");
								ballsButtons[j].setText("Equip");
							} else {
								enableButton(ballsButtons[ball.type - 1]);
								ballsButtons[ball.type - 1].setText("Equip");
								
								ball.type = totalBalls[j];
								disableButton(ballsButtons[j]);
								ballsButtons[j].setText("Equipped");
							}
						}
					});
					
					ballsButtons[i].addMouseListener(new MouseAdapter() {
					    public void mouseEntered(MouseEvent e) {
					    	add(ballInfoLabel1);
					    	ballInfoLabel1.setBounds(ballsButtons[j].getX() + ballsButtons[j].getWidth() + 5, ballsButtons[j].getY(), 200, 17);
					    	ballInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
					    	
					    	if(totalBalls[j] == 0) {
					    		ballInfoLabel1.setText(ballsPrice[j] + " Coins");
					    		
					    		if(totalCoins >= ballsPrice[j]) {
						    		ballInfoLabel1.setForeground(new Color(41, 163, 41));
						    	} else {
						    		ballInfoLabel1.setForeground(new Color(204, 0, 82));
						    	}
					    	} else {
					    		ballInfoLabel1.setText("");
					    	}
					    	
					    	repaint();
					    }

					    public void mouseExited(MouseEvent e) {
					        remove(ballInfoLabel1);
					        repaint();
					    }
					});
				}
				
				JButton exitUpgradesPage = new JButton("Exit");
				add(exitUpgradesPage);
				exitUpgradesPage.setBounds(15, 15, 60, 30);
				enableButton(exitUpgradesPage);
				exitUpgradesPage.setBackground(new Color(204, 0, 0));
				
				exitUpgradesPage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						showBalls = false;
						remove(ballsLabel);
						for(int i = 0; i < totalBalls.length; i++) {
							remove(ballsButtons[i]);
							remove(ballsButtons[i]);
						}
						remove(exitUpgradesPage);
						
						storePage(true);
						add(exit);
						
						repaint();
					}
				});
				
				repaint();
			}
		});
		
		exit.setBounds(15, 15, 60, 30);
		enableButton(exit);
		exit.setBackground(new Color(204, 0, 0));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(showBalls) {
			int ballsX = 390;
			int ballsY = 190;
			
			g.setColor(new Color(0, 153, 0));
			g.fillRect(ballsX, ballsY, ball.width, ball.height);
			
			ballsY += 26;
			g.setColor(new Color(102, 255, 102));
			g.fillRect(ballsX, ballsY, 4, 4);
			g.fillRect(ballsX + 6, ballsY, 4, 4);
			g.fillRect(ballsX + (ball.width - 4), ballsY, 4, 4);
			
			g.setColor(new Color(0, 204, 0));
			g.fillRect(ballsX, ballsY + 6, 4, 4);
			g.fillRect(ballsX + 6, ballsY + 6, 4, 4);
			g.fillRect(ballsX + (ball.width - 4), ballsY + 6, 4, 4);
			
			g.setColor(new Color(0, 102, 0));
			g.fillRect(ballsX, ballsY + (ball.height - 4), 4, 4);
			g.fillRect(ballsX + 6, ballsY + (ball.height - 4), 4, 4);
			g.fillRect(ballsX + (ball.width - 4), ballsY + (ball.height - 4), 4, 4);
			
			ballsY += 36;
			g.setColor(new Color(230, 0, 0));
			g.fillPolygon(new int[] {ballsX, ballsX + (ball.width/2/2), ballsX + (ball.width/2)}, new int[] {ballsY, ballsY - 10, ballsY}, 3);
			g.fillPolygon(new int[] {ballsX + (ball.width/2), ballsX + (ball.width - (ball.width/4)), ballsX + ball.width}, new int[] {ballsY, ballsY - 10, ballsY}, 3);
			
			g.setColor(Color.BLACK);
			g.fillRect(ballsX, ballsY, ball.width, ball.height);
			
			g.setColor(new Color(77, 195, 255));
			g.fillRect(ballsX + 2, ballsY + 4, 3, 3);
			g.fillRect(ballsX + (ball.width - 5), ballsY + 4, 3, 3);
			g.setColor(Color.RED);
			g.fillRect(ballsX + 4, ballsY + (ball.height - 4), 8, 2);
			g.fillRect(ballsX + 2, ballsY + (ball.height - 5), 2, 2);
			g.fillRect(ballsX + (ball.width - 4), ballsY + (ball.height - 5), 2, 2);
			
			ballsY += 26;
			g.setColor(new Color(0, 153, 0));
			g.fillPolygon(new int[] {ballsX, ballsX, ballsX + 6}, new int[] {ballsY + 2, ballsY + (ball.height - 2), ballsY + 8}, 3);
			g.setColor(new Color(255, 102, 0));
			g.fillPolygon(new int[] {ballsX + 2, ballsX + (ball.width - 2), ballsX + 8}, new int[] {ballsY, ballsY, ballsY + 6}, 3);
			g.setColor(new Color(0, 51, 204));
			g.fillPolygon(new int[] {ballsX + ball.width, ballsX + ball.width, ballsX + (ball.width - 6)}, new int[] {ballsY + 2, ballsY + (ball.height - 2), ballsY + 8}, 3);
			g.setColor(new Color(230, 0, 115));
			g.fillPolygon(new int[] {ballsX + 2, ballsX + (ball.width - 2), ballsX + 8}, new int[] {ballsY + ball.height, ballsY + ball.height, ballsY + (ball.height - 6)}, 3);
			
		}
		
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
					if(currentAbility.equals("") == false) {
						remove(currentAbilityLabel);
						remove(abilityProgressBar);
					}
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
					
					if(usingAbility && currentAbility.equals("Freeze Time")) {
						blocks[i].changeTBarXPosition(false);
						blocks[i].freezeBullets(true);
					} else {
						blocks[i].changeTBarXPosition(true);
						blocks[i].freezeBullets(false);
					}
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
		if(pause == false && isPlayingGame) {
			if(usingAbility && isAbilityReloading == false) {
				abilityUseCounter--;
				abilityProgressBar.setValue(abilityUseCounter);
				if(abilityUseCounter == 0) {
					usingAbility = false;
					isAbilityReloading = true;
					abilityProgressBar.setMaximum(2000);
					abilityProgressBar.setForeground(Color.RED);
					abilityUseCounter = maxAbilityUse;
				}
			}
			if(isAbilityReloading) {
				abilityReloadCounter++;
				abilityProgressBar.setValue(abilityReloadCounter);
				if(abilityReloadCounter == 2000) {
					isAbilityReloading = false;
					abilityReloadCounter = 0;
					abilityProgressBar.setMaximum(maxAbilityUse);
					abilityProgressBar.setForeground(Color.GREEN);
				}
			}
		}
		if(usingAbility && currentAbility.equals("Freeze Time")) {
			blockVerticalSpeed = 0;
		} else if(blockVerticalSpeed == 0) {
			blockVerticalSpeed = 1;
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
			
			blocksYPositions[i] += blockVerticalSpeed;
		}
		
		if(collisionCheck()) {
			changeBlockColorTransparency(currentIndex);
			
			isBallFalling = false;
			ballVerticalSpeed = blockVerticalSpeed;
			
			if(((previousIndex == 0 && currentIndex == blocks.length - 1) || previousIndex > currentIndex) && didScore == false) {
				score++;
				didScore = true;
				scoreLabel.setText("Score: " + score);
			}
		} else if(isBallJumping == false) {
			if(currentIndex > -1) {
				previousIndex = currentIndex;
				currentIndex = -1;
			}
			isBallFalling = true;
			if(ballVerticalSpeed < 15) {
				ballVerticalSpeed++;
			}
		}
		
		if(isBallJumping == true) {
			if(ball.isFlying) {
				isBallJumping = false;
			} else {
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
		
		if(usingAbility && ball.isFlying && ballIntersectionUnderBlock == false) {
			ballVerticalSpeed = flyPower;
		}
		ballY += ballVerticalSpeed;
		
		for(int i = 0; i < blocks.length; i++) {
			if(blocks[i].getHealthBooster() != null && blocks[i].getHealthBooster().intersects(ball)) {
				blocks[i].addHealthBooster(false);
				
				if(ballHealth + 5 > 100) {
					ballHealth = 100;
				} else {
					ballHealth += 5;
				}
				
				ballHealthLabel.setText("Health: " + ballHealth);
			}
			
			if(blocks[i] instanceof ShooterBlock) {
				for(int j = 0; j < blocks[i].getBulletsList().size(); j++) {
					if(blocks[i].getBulletsList().get(j).intersects(ball)) {	
						if(pause == false && isPlayingGame && ballShieldAbility && usingAbility && currentAbility.equals("Shield")) {
							if(10 - shieldPower >= 0) {
								if(ballHealth - (10 - shieldPower) >= 0) {
									ballHealth -= (10 - shieldPower);
								} else {
									ballHealth = 0;
								}
							}
						} else if(ballHealth - 10 >= 0) {
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
			
			if(blocks[i].getCoin() != null && blocks[i].getCoin().intersects(ball)) {
				blocks[i].addCoin(false);
				coins += coinWorth;
				coinsLabel.setText("Coins: " + coins);
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
		
		if(currentAbility.equals("") == false) {
			add(currentAbilityLabel);
			currentAbilityLabel.setBounds(10, 105, 130, 20);
			currentAbilityLabel.setFont(new Font("Arial", Font.PLAIN, 15));
			currentAbilityLabel.setText(currentAbility + " Ability");
			abilityProgressBar.setBackground(Color.WHITE);
			abilityProgressBar.setForeground(Color.GREEN);
			add(abilityProgressBar);
			abilityProgressBar.setBounds(10, 125, 130, 15);
			
			abilityProgressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			abilityProgressBar.setMinimum(0);
		}
		
		switch(currentAbility) {
			case "Freeze Time":
				maxAbilityUse = freezeActivationAmount;
				break;
			case "Fly":
				maxAbilityUse = flyActivationAmount;
				break;
			case "Shield":
				maxAbilityUse = shieldActivationAmount;
				break;
		}
		
		usingAbility = false;
		abilityUseCounter = maxAbilityUse;
		abilityProgressBar.setMaximum(maxAbilityUse);
		abilityProgressBar.setValue(maxAbilityUse);
		abilityReloadCounter = 0;
		isAbilityReloading = false;
		
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
		ball.isFlying = false;
		ballIntersectionUnderBlock = false;
		
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
				if((ballX + ball.width <= blocksXPositions[i] && ballX + ball.width + ballHorizontalSpeed >= blocksXPositions[i]) || (ballX >= blocksXPositions[i] + blocksWidth[i] && ballX + ballHorizontalSpeed <= blocksXPositions[i] + blocksWidth[i])) {
					if(ballY + ball.height + ballVerticalSpeed >= blocksYPositions[i] && ballY <= blocksYPositions[i] + 5) {
						ballHorizontalSpeed = 0;
					}
				}
				if(isBallJumping || ball.isFlying) {
					if(ball.intersects(blocks[i])) {
						ballY = blocksYPositions[i] + blocks[i].height;
						ballIntersectionUnderBlock = true;
						return true;
					}
				}
				if(blocks[i] instanceof RegularBlock || blocks[i] instanceof HalfRedBlock) {
					if(collisionCheck(i)) {
						if((blocks[i] instanceof HalfRedBlock)) {
							if(blocks[i].isRedOnRightSide() && ballX + ball.width > blocksXPositions[i] + blocksWidth[i]/2) {
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
					
					if(ballY + ball.height + ballVerticalSpeed > blocks[i].y - blocks[i].TBarHeight() && ballY + ball.height <= blocks[i].y) {
						if(ballX >= TBarXPosition + 5 && ballX + ballHorizontalSpeed <= TBarXPosition + 5) {
							ballHorizontalSpeed = 0;
							if(usingAbility && currentAbility.equals("Freeze Time")) {
								ballX = TBarXPosition + 5;
							} else {
								if(blocks[i].isTBarRight() == false && TBarXPosition > blocksXPositions[i]) {
									if(isLeftKeyDown) {
										if(TBarXPosition + 5 - 1 < blocksXPositions[i] + blocksWidth[i] - 1) {
											ballX = TBarXPosition + 5 - 1;
										}
									}
								} else {
									ballX = TBarXPosition + 5 + 1;
								}
							}
						} else if(ballX + ball.width <= TBarXPosition && ballX + ball.width + ballHorizontalSpeed >= TBarXPosition) {
							ballHorizontalSpeed = 0;
							if(usingAbility && currentAbility.equals("Freeze Time")) {
								ballX = TBarXPosition - ball.width;
							} else {
								if(blocks[i].isTBarRight() && TBarXPosition + 5 < blocksXPositions[i] + blocksWidth[i]) {
									if(isRightKeyDown) {
										if(TBarXPosition - ball.width + 1 > blocksXPositions[i]) {
											ballX = TBarXPosition - ball.width + 1;
										}
									}
								} else {
									ballX = TBarXPosition - ball.width - 1;
								}
							}
						}
						ball.setLocation(ballX, ballY);
					}
					
					if(collisionCheck(i)) {
						return true;
					}
				} else if(blocks[i] instanceof SplitBlock) {
					int secondBlockXPosition = blocks[i].secondBlockXPosition();
					
					if(ballY + ball.height + ballVerticalSpeed >= blocks[i].y && ballY + ball.height <= blocks[i].y + blocks[i].height) {
						if(ballX + ball.width + ballHorizontalSpeed > blocksXPositions[i] && ballX < secondBlockXPosition - ball.width && ballX + ballHorizontalSpeed < secondBlockXPosition - ball.getWidth()) {
							currentIndex = i;
							ballY = blocks[currentIndex].y - ball.height;
							return true;
						} else if(ballX + ball.width > secondBlockXPosition && ballX + ball.width + ballHorizontalSpeed > secondBlockXPosition && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) {
							currentIndex = i;
							ballY = blocks[currentIndex].y - ball.height;
							return true;
						} else if(ballX + ball.width + ballHorizontalSpeed > blocksXPositions[i] && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) {
							if((ballX + ballHorizontalSpeed >= secondBlockXPosition - ball.width) || (ballX + ball.width + ballHorizontalSpeed <= secondBlockXPosition)) {
								if(ballY + ballVerticalSpeed < blocksYPositions[i] + 5) {
									ballX = secondBlockXPosition - ball.width;
									ballHorizontalSpeed = 0;
								}
							}
						}
					} else if(blocks[previousIndex] instanceof SplitBlock && ballY > blocksYPositions[previousIndex] + 5 && ballY + ball.height < blocksYPositions[previousIndex] + 5 + (ball.getHeight() * 2)) {
						if(ballX == secondBlockXPosition - ball.width) {
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
		
		if(ballIntersectionUnderBlock) {
			ballIntersectionUnderBlock = false;
		}
		
		return false;
	}
	
	public boolean collisionCheck(int i) {
		if(ballX + ball.width + ballHorizontalSpeed > blocksXPositions[i] && ballX + ballHorizontalSpeed < blocksXPositions[i] + blocksWidth[i]) {
			if(ballY + ball.height + ballVerticalSpeed >= blocks[i].y && ballY + ball.height <= blocks[i].y + blocks[i].height) {
				currentIndex = i;
				ballY = blocks[currentIndex].y - ball.height;
				return true;
			} else if(blocks[i] instanceof WiperBlock) {
				int TBarXPosition = blocks[i].TBarXPosition();
				
				if(isBallJumping) {
					if((ballX + ball.width > blocksXPositions[previousIndex] && ballX < blocksXPositions[previousIndex] + blocksWidth[previousIndex]) && (ballY + ball.height < blocksYPositions[previousIndex] - blocks[previousIndex].TBarHeight())) {
						changeBallHorizontalSpeed();
					}
				}
				
				if(ballX + ball.width + ballHorizontalSpeed > TBarXPosition && ballX + ballHorizontalSpeed < TBarXPosition + 5) {
					if(ballY + ball.height + ballVerticalSpeed >= blocks[i].y - blocks[i].TBarHeight() && ballY + ball.height <= blocks[i].y - blocks[i].TBarHeight()) {
						currentIndex = i;
						ballY = blocks[i].y - blocks[i].TBarHeight() - ball.height;
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
		if(isBallJumping == false && isBallFalling == false && pause == false && ball.isFlying == false) {
			isBallJumping = true;
			
			switch(blockVerticalSpeed) {
				case 0:
					currentBallJumpYDistance = 175;
					break;
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
			if(isBallLosingHealth == false || ballHealthLosingSpeed == 200) {
				if(pause == false && isPlayingGame && ballShieldAbility && usingAbility && currentAbility.equals("Shield")) {
					if(5 - shieldPower >= 0) {
						if(ballHealth - (5 - shieldPower) >= 0) {
							ballHealth -= (5 - shieldPower);
						} else {
							ballHealth = 0;
						}
					}
				} else if(ballHealth - 5 >= 0) {
					ballHealth -= 5;
				} else {
					ballHealth = 0;
				}
				
				ballHealthLosingSpeed = 0;
				
				if(isBallLosingHealth == false) {
					isBallLosingHealth = true;
				}
			}
			ballHealthLabel.setForeground(Color.RED);
			ballHealthLabel.setText("Health: " + ballHealth);
		} else {
			ballHealthLabel.setForeground(Color.BLACK);
			isBallLosingHealth = false;
			ballHealthLosingSpeed = 0;
		}
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
			highScoreLabel.setText("High Score: " + highScore);
			add(totalCoinsLabel);
			totalCoinsLabel.setText("Total Coins: " + totalCoins);
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

	public void ballShootBullets(boolean b) {
		if(isPlayingGame && pause == false) {
			ball.shootBullets = b;
		}
	}
	
	public void upgradeButtonHover(JButton button, boolean ability, int abilityLevel, int maxLevel, int abilityUpgradePrice, JLabel upgradeInfoLabel1, JLabel upgradeInfoLabel2) {
		button.addMouseListener(new MouseAdapter() {
			//JLabel abilityInfoLabel2 = new JLabel();
			
		    public void mouseEntered(MouseEvent e) {
		    	if(ability && abilityLevel < maxLevel) {
		    		upgradeInfoLabel1.setText("Upgrade To Level " + (abilityLevel + 1));
				} else if(ability && abilityLevel == maxLevel) {
					upgradeInfoLabel1.setText("Reached Maximum Level: " + abilityLevel);
				} else {
					upgradeInfoLabel1.setText("Unlock The Ability");
				}
		    	
		    	add(upgradeInfoLabel1);
		    	upgradeInfoLabel1.setBounds(button.getX() + button.getWidth() + 5, button.getY(), 200, 17);
		    	upgradeInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		    	
		    	add(upgradeInfoLabel2);
		    	if(ability && abilityLevel < maxLevel) {
		    		upgradeInfoLabel2.setBounds(button.getX() + button.getWidth() + 5, upgradeInfoLabel1.getY() + upgradeInfoLabel1.getHeight() + 1, 200, 17);
		    		upgradeInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		    		upgradeInfoLabel2.setText(abilityUpgradePrice + " Coins");
		    	}
		    	
		    	if(ability && abilityLevel < maxLevel && totalCoins >= abilityUpgradePrice) {
		    		upgradeInfoLabel1.setForeground(new Color(41, 163, 41));
		    		upgradeInfoLabel2.setForeground(new Color(41, 163, 41));
		    	} else {
		    		upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
		    		upgradeInfoLabel2.setForeground(new Color(204, 0, 82));
		    	}
		    	
		    	repaint();
		    }

		    public void mouseExited(MouseEvent e) {
		        remove(upgradeInfoLabel1);
		        remove(upgradeInfoLabel2);
		        repaint();
		    }
		});
	}
	
	public void usingAbility(boolean isKeyPressed) {
		if(isPlayingGame && pause == false && isAbilityReloading == false) {
			if(ballFreezeAbility && currentAbility.equals("Freeze Time")) {
				usingAbility = true;
			} else if(ballShieldAbility && currentAbility.equals("Shield")) {
				usingAbility = true;
			} else if(ballFlyAbility && currentAbility.equals("Fly")) {
				usingAbility = true;
				ball.isFlying = isKeyPressed;
			}
		} else if(ball.isFlying) {
			ball.isFlying = false;
		}
	}
}
