package gameWork;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.LineBorder;

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
	int previousIndex;
	
	int differentTypesOfBlocks = 5;
	ArrayList<String> differentBlocksInGame = new ArrayList<String>();
	
	int highScore = 0;
	boolean didScore;
	int score;
	int scoreWorth = 1;
	JLabel scoreLabel = new JLabel("Score: " + score);
	JLabel highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.CENTER);
	
	int totalCoins = 0;
	int coins;
	int coinWorth = 0;
	int[] blocksCoinValue = new int[differentTypesOfBlocks];
	JLabel coinsLabel = new JLabel("Current Coins: " + coins);
	JLabel totalCoinsLabel = new JLabel("Total Coins: " + totalCoins, SwingConstants.CENTER);
	
	int ballHealth;
	JLabel ballHealthLabel = new JLabel("Health: " + ballHealth);
	
	int ballHealthLosingSpeed;
	boolean isBallLosingHealth;
	
	boolean stopBallSlowly;
	double slowingDownSpeed;
	
	boolean isRightKeyDown;
	boolean isLeftKeyDown;
	
	boolean changedDirectionInAir;
	
	String currentAbility = "";
	
	boolean ballFlyAbility = false;
	boolean ballShieldAbility = false;
	boolean ballFreezeAbility = false;
	
	int shieldPower = 2;
	int shieldActivationAmount = 300;
	int shieldPowerLevel = 1;
	int shieldPowerMaxLevel = 5;
	int shieldActivationAmountLevel = 1;
	int shieldActivationAmountMaxLevel = 5;
	int shieldPowerUpgradePrice = 60;
	int shieldActivationAmountUpgradePrice = 80;
	
	int flyPower = -1;
	int flyActivationAmount = 200;
	int flyPowerLevel = 1;
	int flyPowerMaxLevel = 4;
	int flyActivationAmountLevel = 1;
	int flyActivationAmountMaxLevel = 5;
	int flyPowerUpgradePrice = 50;
	int flyActivationAmountUpgradePrice = 70;
	
	int ballBulletReloadSpeedLevel = 1;
	int ballBulletReloadSpeedMaxLevel = 4;
	int ballBulletReloadSpeedUpgradePrice = 60;
	
	int freezeActivationAmount = 200;
	int freezeActivationAmountLevel = 1;
	int freezeActivationAmountMaxLevel = 5;
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
	boolean pause = false;
	
	JLabel gameNameLabel = new JLabel("Just Hop", SwingConstants.CENTER);
	
	JCheckBox[] differentBlocks = new JCheckBox[differentTypesOfBlocks];
	JCheckBox[] modes = new JCheckBox[3];
	
	JButton startgame = new JButton("Play");
	JButton customize = new JButton("Customize");
	JButton store = new JButton("Store");
	JButton instructions = new JButton("Instructions");
	JButton abilities = new JButton("Abilities");
	JButton upgrades = new JButton("Upgrades");
	JButton balls = new JButton("Balls");
	JButton exit = new JButton("Exit");
	
	boolean gameBackgroundAffect = true;
	
	File file = new File("gameData.txt");
	
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
		
		blocksCoinValue[0] = 1;
		blocksCoinValue[1] = 1;
		blocksCoinValue[2] = 2;
		blocksCoinValue[3] = 2;
		blocksCoinValue[4] = 3;
		
		differentBlocks[0] = new JCheckBox("Regular");
		differentBlocks[1] = new JCheckBox("HalfRed");
		differentBlocks[2] = new JCheckBox("Wiper");
		differentBlocks[3] = new JCheckBox("Split");
		differentBlocks[4] = new JCheckBox("Shooter");
		
		for(int i = 0; i < modes.length; i++) {
			modes[i].setFocusable(false);
			modes[i].setFont(new Font("Consolas", Font.PLAIN, 18));
			modes[i].setOpaque(false);
			
			int j = i;
			modes[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == modes[j]) {
						if(modes[j].isSelected()) {
							modes[j].setSelected(true);
							scoreWorth += 2;
						} else {
							modes[j].setSelected(false);
							scoreWorth -= 2;
						}
						saveData();
					}
				}
			});
			
			modes[i].addMouseListener(new MouseAdapter() {
				JLabel modeInfoLabel1 = new JLabel("+2 Score Worth");
				public void mouseEntered(MouseEvent e) {
					add(modeInfoLabel1);
					modeInfoLabel1.setBounds(modes[j].getX() + modes[j].getWidth(), modes[j].getY(), 120, 20);
					modeInfoLabel1.setFont(new Font("Consolas", Font.PLAIN, 15));
					modeInfoLabel1.setForeground(new Color(0, 128, 43));
					
					repaint();
				}
				
				public void mouseExited(MouseEvent e) {
					remove(modeInfoLabel1);
					repaint();
				}
			});
		}
		
		for(int i = 0; i < differentBlocks.length; i++) {
			differentBlocks[i].setFocusable(false);
			differentBlocks[i].setFont(new Font("Consolas", Font.PLAIN, 18));
			differentBlocks[i].setOpaque(false);
			
			differentBlocks[i].setSelected(true);
			differentBlocksInGame.add(differentBlocks[i].getText());
			coinWorth += blocksCoinValue[i];
			
			int j = i;
			differentBlocks[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == differentBlocks[j]) {
						if(differentBlocks[j].isSelected()) {
							differentBlocks[j].setSelected(true);
							differentBlocksInGame.add(differentBlocks[j].getText());
							coinWorth += blocksCoinValue[j];
						} else {
							if(differentBlocksInGame.size() - 1 > 0) {
								differentBlocks[j].setSelected(false);
								differentBlocksInGame.remove(differentBlocks[j].getText());
								coinWorth -= blocksCoinValue[j];
							} else {
								differentBlocks[j].setSelected(true);
							}
						}
						saveData();
					}
				}
			});
			
			differentBlocks[i].addMouseListener(new MouseAdapter() {
				JLabel blockInfoLabel1 = new JLabel("+" + blocksCoinValue[j] + " Coins");
				public void mouseEntered(MouseEvent e) {
					add(blockInfoLabel1);
					blockInfoLabel1.setBounds(differentBlocks[j].getX() + differentBlocks[j].getWidth(), differentBlocks[j].getY(), 80, 20);
					blockInfoLabel1.setFont(new Font("Consolas", Font.PLAIN, 15));
					blockInfoLabel1.setForeground(new Color(0, 128, 43));
					
					repaint();
				}
				
				public void mouseExited(MouseEvent e) {
					remove(blockInfoLabel1);
					repaint();
				}
			});
		}
		
		add(gameNameLabel);
		gameNameLabel.setBounds(0, 0, 800, 100);
		gameNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 80));
		gameNameLabel.setBackground(new Color(47, 79, 79));
		gameNameLabel.setForeground(Color.WHITE);
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
		startgame.setBackground(new Color(255, 69, 0));
		
		startgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startgame.setBounds(365, 290, 70, 30);
				startgame.setBorder(null);
				menuPage(false);
				
				startGame();
				timer.start();
			}
		});
		
		startgame.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				startgame.setBounds(360, startgame.getY() - 1, 80, 32);
				startgame.setBorder(new LineBorder(Color.BLACK, 1));
			}
			
			public void mouseExited(MouseEvent e) {
				startgame.setBounds(365, 290, 70, 30);
				startgame.setBorder(null);
			}
		});
		
		add(customize);
		customize.setBounds(365, 330, 70, 30);
		enableButton(customize);
		customize.setBackground(new Color(34, 139, 34));
		
		customize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customize.setBounds(365, 330, 70, 30);
				customize.setBorder(null);
				menuPage(false);
				add(exit);
				
				JLabel blocksLabel = new JLabel("Blocks", SwingConstants.CENTER);
				add(blocksLabel);
				blocksLabel.setBounds(180, 100, 100, 40);
				blocksLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
				blocksLabel.setBackground(new Color(255, 112, 77));
				blocksLabel.setOpaque(true);
				
				JLabel modesLabel = new JLabel("Modes", SwingConstants.CENTER);
				add(modesLabel);
				modesLabel.setBounds(460, 100, 100, 40);
				modesLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
				modesLabel.setBackground(new Color(77, 148, 255));
				modesLabel.setOpaque(true);
				
				int y = 170;
				
				for(int i = 0; i < differentBlocks.length; i++) {
					add(differentBlocks[i]);
					differentBlocks[i].setBounds(180, y, 110, 20);
					
					if(i < modes.length) {
						add(modes[i]);
						modes[i].setBounds(400, y, 270, 20);
					}
					y += 30;
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
		
		customize.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				customize.setBounds(360, customize.getY() - 1, 80, 32);
				customize.setBorder(new LineBorder(Color.BLACK, 1));
			}
			
			public void mouseExited(MouseEvent e) {
				customize.setBounds(365, 330, 70, 30);
				customize.setBorder(null);
			}
		});
		
		add(store);
		store.setBounds(365, 370, 70, 30);
		enableButton(store);
		store.setBackground(new Color(65, 105, 225));
		
		store.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				store.setBounds(365, 370, 70, 30);
				store.setBorder(null);
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
		
		store.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				store.setBounds(360, store.getY() - 1, 80, 32);
				store.setBorder(new LineBorder(Color.BLACK, 1));
			}
			
			public void mouseExited(MouseEvent e) {
				store.setBounds(365, 370, 70, 30);
				store.setBorder(null);
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
				
				JButton freezeAbilityButton = new JButton("Freeze Time");
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
						saveData();
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
						saveData();
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
				if(totalCoins >= 1000 && highScore >= 200 && ball.twoBulletsAtOnce == false) {
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
						saveData();
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
						saveData();
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
				if(ballFlyAbility && flyPowerLevel < flyPowerMaxLevel && totalCoins >= flyPowerUpgradePrice) {
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
						
						if(flyPowerLevel < flyPowerMaxLevel) {
							upgradeInfoLabel1.setText("Upgrade To Level " + (flyPowerLevel + 1));
							upgradeInfoLabel2.setText(flyPowerUpgradePrice + " Coins");
							
							if(totalCoins >= flyPowerUpgradePrice) {
								upgradeInfoLabel1.setForeground(new Color(41, 163, 41));
								upgradeInfoLabel2.setForeground(new Color(41, 163, 41));
							} else {
								upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
								upgradeInfoLabel2.setForeground(new Color(204, 0, 82));
							}
						} else {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + flyPowerLevel);
							upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
							upgradeInfoLabel2.setText("");
						}
						
						if(ballFlyAbility == false || flyPowerLevel == flyPowerMaxLevel || totalCoins < flyPowerUpgradePrice) {
							disableButton(flyPowerButton);
						}
						saveData();
					}
				});
				
				flyPowerButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	if(ballFlyAbility && flyPowerLevel < flyPowerMaxLevel) {
				    		upgradeInfoLabel1.setText("Upgrade To Level " + (flyPowerLevel + 1));
						} else if(ballFlyAbility && flyPowerLevel == flyPowerMaxLevel) {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + flyPowerLevel);
						} else {
							upgradeInfoLabel1.setText("Unlock The Ability");
						}
				    	
				    	add(upgradeInfoLabel1);
				    	upgradeInfoLabel1.setBounds(flyPowerButton.getX() + flyPowerButton.getWidth() + 5, flyPowerButton.getY(), 200, 17);
				    	upgradeInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(upgradeInfoLabel2);
				    	if(ballFlyAbility && flyPowerLevel < flyPowerMaxLevel) {
				    		upgradeInfoLabel2.setBounds(flyPowerButton.getX() + flyPowerButton.getWidth() + 5, upgradeInfoLabel1.getY() + upgradeInfoLabel1.getHeight() + 1, 200, 17);
				    		upgradeInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    		upgradeInfoLabel2.setText(flyPowerUpgradePrice + " Coins");
				    	} else {
				    		upgradeInfoLabel2.setText("");
				    	}
				    	
				    	if(ballFlyAbility && flyPowerLevel < flyPowerMaxLevel && totalCoins >= flyPowerUpgradePrice) {
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
				
				JButton flyActivationAmountButton = new JButton("Fly Time");
				add(flyActivationAmountButton);
				flyActivationAmountButton.setBounds(360, 230, 80, 30);
				if(ballFlyAbility && flyActivationAmountLevel < flyActivationAmountMaxLevel && totalCoins >= flyActivationAmountUpgradePrice) {
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
						
						if(flyActivationAmountLevel < flyActivationAmountMaxLevel) {
							upgradeInfoLabel1.setText("Upgrade To Level " + (flyActivationAmountLevel + 1));
							upgradeInfoLabel2.setText(flyActivationAmountUpgradePrice + " Coins");
							
							if(totalCoins >= flyActivationAmountUpgradePrice) {
								upgradeInfoLabel1.setForeground(new Color(41, 163, 41));
								upgradeInfoLabel2.setForeground(new Color(41, 163, 41));
							} else {
								upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
								upgradeInfoLabel2.setForeground(new Color(204, 0, 82));
							}
						} else {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + flyActivationAmountLevel);
							upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
							upgradeInfoLabel2.setText("");
						}
						
						if(ballFlyAbility == false || flyActivationAmountLevel == flyActivationAmountMaxLevel || totalCoins < flyActivationAmountUpgradePrice) {
							disableButton(flyActivationAmountButton);
						}
						saveData();
					}
				});
				
				flyActivationAmountButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	if(ballFlyAbility && flyActivationAmountLevel < flyActivationAmountMaxLevel) {
				    		upgradeInfoLabel1.setText("Upgrade To Level " + (flyActivationAmountLevel + 1));
						} else if(ballFlyAbility && flyActivationAmountLevel == flyActivationAmountMaxLevel) {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + flyActivationAmountLevel);
						} else {
							upgradeInfoLabel1.setText("Unlock The Ability");
						}
				    	
				    	add(upgradeInfoLabel1);
				    	upgradeInfoLabel1.setBounds(flyActivationAmountButton.getX() + flyActivationAmountButton.getWidth() + 5, flyActivationAmountButton.getY(), 200, 17);
				    	upgradeInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(upgradeInfoLabel2);
				    	if(ballFlyAbility && flyActivationAmountLevel < flyActivationAmountMaxLevel) {
				    		upgradeInfoLabel2.setBounds(flyActivationAmountButton.getX() + flyActivationAmountButton.getWidth() + 5, upgradeInfoLabel1.getY() + upgradeInfoLabel1.getHeight() + 1, 200, 17);
				    		upgradeInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    		upgradeInfoLabel2.setText(flyActivationAmountUpgradePrice + " Coins");
				    	} else {
				    		upgradeInfoLabel2.setText("");
				    	}
				    	
				    	if(ballFlyAbility && flyActivationAmountLevel < flyActivationAmountMaxLevel && totalCoins >= flyActivationAmountUpgradePrice) {
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
				
				JButton shieldPowerButton = new JButton("Shield Power");
				add(shieldPowerButton);
				shieldPowerButton.setBounds(355, 270, 90, 30);
				if(ballShieldAbility && shieldPowerLevel < shieldPowerMaxLevel && totalCoins >= shieldPowerUpgradePrice) {
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
						
						if(shieldPowerLevel < shieldPowerMaxLevel) {
							upgradeInfoLabel1.setText("Upgrade To Level " + (shieldPowerLevel + 1));
							upgradeInfoLabel2.setText(shieldPowerUpgradePrice + " Coins");
							
							if(totalCoins >= shieldPowerUpgradePrice) {
								upgradeInfoLabel1.setForeground(new Color(41, 163, 41));
								upgradeInfoLabel2.setForeground(new Color(41, 163, 41));
							} else {
								upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
								upgradeInfoLabel2.setForeground(new Color(204, 0, 82));
							}
						} else {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + shieldPowerLevel);
							upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
							upgradeInfoLabel2.setText("");
						}
						
						if(ballShieldAbility == false || shieldPowerLevel == shieldPowerMaxLevel || totalCoins < shieldPowerUpgradePrice) {
							disableButton(shieldPowerButton);
						}
						saveData();
					}
				});
				
				shieldPowerButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	if(ballShieldAbility && shieldPowerLevel < shieldPowerMaxLevel) {
				    		upgradeInfoLabel1.setText("Upgrade To Level " + (shieldPowerLevel + 1));
						} else if(ballShieldAbility && shieldPowerLevel == shieldPowerMaxLevel) {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + shieldPowerLevel);
						} else {
							upgradeInfoLabel1.setText("Unlock The Ability");
						}
				    	
				    	add(upgradeInfoLabel1);
				    	upgradeInfoLabel1.setBounds(shieldPowerButton.getX() + shieldPowerButton.getWidth() + 5, shieldPowerButton.getY(), 200, 17);
				    	upgradeInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(upgradeInfoLabel2);
				    	if(ballShieldAbility && shieldPowerLevel < shieldPowerMaxLevel) {
				    		upgradeInfoLabel2.setBounds(shieldPowerButton.getX() + shieldPowerButton.getWidth() + 5, upgradeInfoLabel1.getY() + upgradeInfoLabel1.getHeight() + 1, 200, 17);
				    		upgradeInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    		upgradeInfoLabel2.setText(shieldPowerUpgradePrice + " Coins");
				    	} else {
				    		upgradeInfoLabel2.setText("");
				    	}
				    	
				    	if(ballShieldAbility && shieldPowerLevel < shieldPowerMaxLevel && totalCoins >= shieldPowerUpgradePrice) {
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
				
				JButton shieldActivationAmountButton = new JButton("Shield Time");
				add(shieldActivationAmountButton);
				shieldActivationAmountButton.setBounds(355, 310, 90, 30);
				if(ballShieldAbility && shieldActivationAmountLevel < shieldActivationAmountMaxLevel && totalCoins >= shieldActivationAmountUpgradePrice) {
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
						
						if(shieldActivationAmountLevel < shieldActivationAmountMaxLevel) {
							upgradeInfoLabel1.setText("Upgrade To Level " + (shieldActivationAmountLevel + 1));
							upgradeInfoLabel2.setText(shieldActivationAmountUpgradePrice + " Coins");
							
							if(totalCoins >= shieldActivationAmountUpgradePrice) {
								upgradeInfoLabel1.setForeground(new Color(41, 163, 41));
								upgradeInfoLabel2.setForeground(new Color(41, 163, 41));
							} else {
								upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
								upgradeInfoLabel2.setForeground(new Color(204, 0, 82));
							}
						} else {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + shieldActivationAmountLevel);
							upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
							upgradeInfoLabel2.setText("");
						}
						
						if(ballShieldAbility == false || shieldActivationAmountLevel == shieldActivationAmountMaxLevel || totalCoins < shieldActivationAmountUpgradePrice) {
							disableButton(shieldActivationAmountButton);
						}
						saveData();
					}
				});
				
				shieldActivationAmountButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	if(ballShieldAbility && shieldActivationAmountLevel < shieldActivationAmountMaxLevel) {
				    		upgradeInfoLabel1.setText("Upgrade To Level " + (shieldActivationAmountLevel + 1));
						} else if(ballShieldAbility && shieldActivationAmountLevel == shieldActivationAmountMaxLevel) {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + shieldActivationAmountLevel);
						} else {
							upgradeInfoLabel1.setText("Unlock The Ability");
						}
				    	
				    	add(upgradeInfoLabel1);
				    	upgradeInfoLabel1.setBounds(shieldActivationAmountButton.getX() + shieldActivationAmountButton.getWidth() + 5, shieldActivationAmountButton.getY(), 200, 17);
				    	upgradeInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(upgradeInfoLabel2);
				    	if(ballShieldAbility && shieldActivationAmountLevel < shieldActivationAmountMaxLevel) {
				    		upgradeInfoLabel2.setBounds(shieldActivationAmountButton.getX() + shieldActivationAmountButton.getWidth() + 5, upgradeInfoLabel1.getY() + upgradeInfoLabel1.getHeight() + 1, 200, 17);
				    		upgradeInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    		upgradeInfoLabel2.setText(shieldActivationAmountUpgradePrice + " Coins");
				    	} else {
				    		upgradeInfoLabel2.setText("");
				    	}
				    	
				    	if(ballShieldAbility && shieldActivationAmountLevel < shieldActivationAmountMaxLevel && totalCoins >= shieldActivationAmountUpgradePrice) {
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
				
				JButton freezeActivationAmountButton = new JButton("Freeze Time");
				add(freezeActivationAmountButton);
				freezeActivationAmountButton.setBounds(355, 350, 90, 30);
				if(ballFreezeAbility && freezeActivationAmountLevel < freezeActivationAmountMaxLevel && totalCoins >= freezeActivationAmountUpgradePrice) {
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
						
						if(freezeActivationAmountLevel < freezeActivationAmountMaxLevel) {
							upgradeInfoLabel1.setText("Upgrade To Level " + (freezeActivationAmountLevel + 1));
							upgradeInfoLabel2.setText(freezeActivationAmountUpgradePrice + " Coins");
							
							if(totalCoins >= freezeActivationAmountUpgradePrice) {
								upgradeInfoLabel1.setForeground(new Color(41, 163, 41));
								upgradeInfoLabel2.setForeground(new Color(41, 163, 41));
							} else {
								upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
								upgradeInfoLabel2.setForeground(new Color(204, 0, 82));
							}
						} else {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + freezeActivationAmountLevel);
							upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
							upgradeInfoLabel2.setText("");
						}
						
						if(ballFreezeAbility == false || freezeActivationAmountLevel == freezeActivationAmountMaxLevel || totalCoins < freezeActivationAmountUpgradePrice) {
							disableButton(freezeActivationAmountButton);
						}
						saveData();
					}
				});
				
				freezeActivationAmountButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	if(ballFreezeAbility && freezeActivationAmountLevel < freezeActivationAmountMaxLevel) {
				    		upgradeInfoLabel1.setText("Upgrade To Level " + (freezeActivationAmountLevel + 1));
						} else if(ballFreezeAbility && freezeActivationAmountLevel == freezeActivationAmountMaxLevel) {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + freezeActivationAmountLevel);
						} else {
							upgradeInfoLabel1.setText("Unlock The Ability");
						}
				    	
				    	add(upgradeInfoLabel1);
				    	upgradeInfoLabel1.setBounds(freezeActivationAmountButton.getX() + freezeActivationAmountButton.getWidth() + 5, freezeActivationAmountButton.getY(), 200, 17);
				    	upgradeInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(upgradeInfoLabel2);
				    	if(ballFreezeAbility && freezeActivationAmountLevel < freezeActivationAmountMaxLevel) {
				    		upgradeInfoLabel2.setBounds(freezeActivationAmountButton.getX() + freezeActivationAmountButton.getWidth() + 5, upgradeInfoLabel1.getY() + upgradeInfoLabel1.getHeight() + 1, 200, 17);
				    		upgradeInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    		upgradeInfoLabel2.setText(freezeActivationAmountUpgradePrice + " Coins");
				    	} else {
				    		upgradeInfoLabel2.setText("");
				    	}
				    	
				    	if(ballFreezeAbility && freezeActivationAmountLevel < freezeActivationAmountMaxLevel && totalCoins >= freezeActivationAmountUpgradePrice) {
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
				
				JButton bulletReloadSpeedButton = new JButton("Reload Speed");
				add(bulletReloadSpeedButton);
				bulletReloadSpeedButton.setBounds(355, 390, 90, 30);
				if(ballBulletReloadSpeedLevel < ballBulletReloadSpeedMaxLevel && totalCoins >= ballBulletReloadSpeedUpgradePrice) {
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
						
						if(ballBulletReloadSpeedLevel < ballBulletReloadSpeedMaxLevel) {
							upgradeInfoLabel1.setText("Upgrade To Level " + (ballBulletReloadSpeedLevel + 1));
							upgradeInfoLabel2.setText(ballBulletReloadSpeedUpgradePrice + " Coins");
							
							if(totalCoins >= ballBulletReloadSpeedUpgradePrice) {
								upgradeInfoLabel1.setForeground(new Color(41, 163, 41));
								upgradeInfoLabel2.setForeground(new Color(41, 163, 41));
							} else {
								upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
								upgradeInfoLabel2.setForeground(new Color(204, 0, 82));
							}
						} else {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + ballBulletReloadSpeedLevel);
							upgradeInfoLabel1.setForeground(new Color(204, 0, 82));
							upgradeInfoLabel2.setText("");
						}
						
						if(ballBulletReloadSpeedLevel == ballBulletReloadSpeedMaxLevel || totalCoins < ballBulletReloadSpeedUpgradePrice) {
							disableButton(bulletReloadSpeedButton);
						}
						saveData();
					}
				});
				
				bulletReloadSpeedButton.addMouseListener(new MouseAdapter() {
				    public void mouseEntered(MouseEvent e) {
				    	if(ballBulletReloadSpeedLevel < ballBulletReloadSpeedMaxLevel) {
				    		upgradeInfoLabel1.setText("Upgrade To Level " + (ballBulletReloadSpeedLevel + 1));
						} else if(ballBulletReloadSpeedLevel == ballBulletReloadSpeedMaxLevel) {
							upgradeInfoLabel1.setText("Reached Maximum Level: " + ballBulletReloadSpeedLevel);
						}
				    	
				    	add(upgradeInfoLabel1);
				    	upgradeInfoLabel1.setBounds(bulletReloadSpeedButton.getX() + bulletReloadSpeedButton.getWidth() + 5, bulletReloadSpeedButton.getY(), 200, 17);
				    	upgradeInfoLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    	
				    	add(upgradeInfoLabel2);
				    	if(ballBulletReloadSpeedLevel < ballBulletReloadSpeedMaxLevel) {
				    		upgradeInfoLabel2.setBounds(bulletReloadSpeedButton.getX() + bulletReloadSpeedButton.getWidth() + 5, upgradeInfoLabel1.getY() + upgradeInfoLabel1.getHeight() + 1, 200, 17);
				    		upgradeInfoLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				    		upgradeInfoLabel2.setText(ballBulletReloadSpeedUpgradePrice + " Coins");
				    	} else {
				    		upgradeInfoLabel2.setText("");
				    	}
				    	
				    	if(ballBulletReloadSpeedLevel < ballBulletReloadSpeedMaxLevel && totalCoins >= ballBulletReloadSpeedUpgradePrice) {
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
							saveData();
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
		
		add(instructions);
		instructions.setBounds(360, 410, 80, 30);
		enableButton(instructions);
		instructions.setBackground(new Color(0, 128, 128));
		
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructions.setBounds(360, 410, 80, 30);
				instructions.setBorder(null);
				menuPage(false);
				add(exit);
				
				JLabel controlKeysLabel = new JLabel("Control Keys", SwingConstants.CENTER);
				add(controlKeysLabel);
				controlKeysLabel.setBounds(355, 150, 110, 40);
				controlKeysLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
				controlKeysLabel.setOpaque(true);
				controlKeysLabel.setBackground(Color.ORANGE);
				
				JLabel leftKeysLabel = new JLabel("Left Direction (Press Left Arrow Key or A)", SwingConstants.CENTER);
				add(leftKeysLabel);
				leftKeysLabel.setBounds(260, 200, 280, 20);
				leftKeysLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				
				JLabel rightKeysLabel = new JLabel("Right Direction (Press Right Arrow Key or D)", SwingConstants.CENTER);
				add(rightKeysLabel);
				rightKeysLabel.setBounds(260, 230, 280, 20);
				rightKeysLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				
				JLabel jumpKeysLabel = new JLabel("Jump (Press Up Arrow Key or W)", SwingConstants.CENTER);
				add(jumpKeysLabel);
				jumpKeysLabel.setBounds(260, 260, 280, 20);
				jumpKeysLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				
				JLabel shootKeyLabel = new JLabel("Shoot Bullets (Press B For Few Seconds)", SwingConstants.CENTER);
				add(shootKeyLabel);
				shootKeyLabel.setBounds(260, 290, 280, 20);
				shootKeyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				
				JLabel abilityKeyLabel = new JLabel("Use Ability (Press N)", SwingConstants.CENTER);
				add(abilityKeyLabel);
				abilityKeyLabel.setBounds(260, 320, 280, 20);
				abilityKeyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				
				JLabel pauseKeyLabel = new JLabel("Pause Game (Press Up Spacebar)", SwingConstants.CENTER);
				add(pauseKeyLabel);
				pauseKeyLabel.setBounds(260, 350, 280, 20);
				pauseKeyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				
				exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						remove(controlKeysLabel);
						remove(leftKeysLabel);
						remove(rightKeysLabel);
						remove(jumpKeysLabel);
						remove(shootKeyLabel);
						remove(abilityKeyLabel);
						remove(pauseKeyLabel);
						remove(exit);
						
						menuPage(true);
						repaint();
					}
				});
				
				repaint();
			}
		});
		
		instructions.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e ) {
				instructions.setBounds(355, instructions.getY() - 1, 90, 32);
				instructions.setBorder(new LineBorder(Color.BLACK, 1));
			}
			
			public void mouseExited(MouseEvent e ) {
				instructions.setBounds(360, 410, 80, 30);
				instructions.setBorder(null);
			}
		});
		
		exit.setBounds(15, 15, 60, 30);
		enableButton(exit);
		exit.setBackground(new Color(204, 0, 0));
		
		loadData();
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
			
			saveData();
			
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
					setBackground(new Color(133, 173, 173));
					menuPage(true);
					repaint();
				}
			});
			
		} else if(timer.isRunning() && isPlayingGame && pause == false) {
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
			ball.draw(g);
			for(Blocks block: blocks) {
				block.draw(g);
			}
			timer.stop();
			
			//g.setFont(new Font("Times New Roman", Font.PLAIN, 50));
			//g.drawString("Paused", 340, 260);
			//g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
			//g.drawString("(Press Spacebar To Unpause)", 305, 285);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(timer.isRunning()) {
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
						blockVerticalSpeed = (int)(Math.random() * 2) + 1;
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
					score += scoreWorth;
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
		scoreLabel.setForeground(Color.BLACK);
		
		add(ballHealthLabel);
		ballHealthLabel.setBounds(10, 40, 150, 30);
		ballHealthLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		ballHealthLabel.setText("Health: " + ballHealth);
		ballHealthLabel.setForeground(Color.BLACK);
		
		add(coinsLabel);
		coinsLabel.setBounds(10, 70, 150, 30);
		coinsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		coinsLabel.setText("Coins: " + coins);
		coinsLabel.setForeground(Color.BLACK);
		
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
		ball.bullets.clear();
		
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
					currentBallJumpYDistance = 170;
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
					case "Regular":
						blocks[index] = new RegularBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
						break;
					case "HalfRed":
						blocks[index] = new HalfRedBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
						break;
					case "Wiper":
						blocks[index] = new WiperBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
						break;
					case "Split":
						blocks[index] = new SplitBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index], (int) ball.getWidth());
						break;
					case "Shooter":
						blocks[index] = new ShooterBlock(blocksXPositions[index], blocksYPositions[index], blocksWidth[index]);
						break;
				}
				
				break;
			}
		}
	}

	public void changeBlockColorTransparency(int index) {
		if(modes[2].isSelected() && (currentAbility.equals("Freeze Time") == false || (currentAbility.equals("Freeze Time") && usingAbility == false))) {
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
			remove(instructions);
		} else {
			add(gameNameLabel);
			add(highScoreLabel);
			highScoreLabel.setText("High Score: " + highScore);
			add(totalCoinsLabel);
			totalCoinsLabel.setText("Total Coins: " + totalCoins);
			add(startgame);
			add(customize);
			add(store);
			add(instructions);
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
	
	public void saveData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("gameData.txt"));
			writer.write(highScore + "");
			writer.append("\n" + totalCoins);
			writer.append("\n" + coinWorth);
			writer.append("\n" + scoreWorth);
			
			writer.append("\n" + currentAbility);
			
			if(ballFlyAbility) {
				writer.append("\ntrue");
			} else {
				writer.append("\nfalse");
			}
			writer.append("\n" + flyPower);
			writer.append("\n" + flyActivationAmount);
			writer.append("\n" + flyPowerLevel + "");
			writer.append("\n" + flyActivationAmountLevel);
			writer.append("\n" + flyPowerUpgradePrice);
			writer.append("\n" + flyActivationAmountUpgradePrice);
			
			if(ballShieldAbility) {
				writer.append("\ntrue");
			} else {
				writer.append("\nfalse");
			}
			writer.append("\n" + shieldPower);
			writer.append("\n" + shieldActivationAmount);
			writer.append("\n" + shieldPowerLevel);
			writer.append("\n" + shieldActivationAmountLevel);
			writer.append("\n" + shieldPowerUpgradePrice);
			writer.append("\n" + shieldActivationAmountUpgradePrice);
			
			
			if(ballFreezeAbility) {
				writer.append("\ntrue");
			} else {
				writer.append("\nfalse");
			}
			writer.append("\n" + freezeActivationAmount);
			writer.append("\n" + freezeActivationAmountLevel);
			writer.append("\n" + freezeActivationAmountUpgradePrice);
			
			if(ball.twoBulletsAtOnce) {
				writer.append("\ntrue");
			} else {
				writer.append("\nfalse");
			}
			writer.append("\n" + ballBulletReloadSpeedLevel);
			writer.append("\n" + ballBulletReloadSpeedUpgradePrice);
			
			writer.append("\n" + ball.type + "\n");
			
			for(int i = 0; i < totalBalls.length; i++) {
				String space = " ";
				if(i == totalBalls.length - 1) {
					space = "";
				}
				
				writer.append(totalBalls[i] + space);
			}
			writer.append("\n" + ball.bulletReloadSpeed + "\n");
			
			for(int i = 0; i < modes.length; i++) {
				String space = " ";
				if(i == modes.length - 1) {
					space = "\n";
				}
				if(modes[i].isSelected()) {
					writer.append("true" + space);
				} else {
					writer.append("false" + space);
				}
			}
			
			for(int i = 0; i < differentBlocks.length; i++) {
				String space = " ";
				if(i == differentBlocks.length - 1) {
					space = "\n";
				}
				if(differentBlocks[i].isSelected()) {
					writer.append("true" + space);
				} else {
					writer.append("false" + space);
				}
			}
			
			for(int i = 0; i < differentBlocksInGame.size(); i++) {
				String blockName = differentBlocksInGame.get(i);
				String space = " ";
				if(i == differentBlocksInGame.size() - 1) {
					space = "";
				}
				writer.append(blockName + space);
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadData() {
		try {
			if(file.exists()) {
				Scanner reader = new Scanner(new FileReader(file));
				ArrayList<String> dataList = new ArrayList<String>();
				while(reader.hasNextLine()) {
					dataList.add(reader.nextLine());
				}
				if(dataList.size() == 32) {
					highScore = Integer.parseInt(dataList.get(0));
					highScoreLabel.setText("High Score: " + highScore);
					totalCoins = Integer.parseInt(dataList.get(1));
					totalCoinsLabel.setText("Total Coins: " + totalCoins);
					coinWorth = Integer.parseInt(dataList.get(2));
					scoreWorth = Integer.parseInt(dataList.get(3));
					
					currentAbility = dataList.get(4);
					
					if(dataList.get(5).equals("true")) {
						ballFlyAbility = true;
					}
					flyPower = Integer.parseInt(dataList.get(6));
					flyActivationAmount = Integer.parseInt(dataList.get(7));
					flyPowerLevel = Integer.parseInt(dataList.get(8));
					flyActivationAmountLevel = Integer.parseInt(dataList.get(9));
					flyPowerUpgradePrice = Integer.parseInt(dataList.get(10));
					flyActivationAmountUpgradePrice = Integer.parseInt(dataList.get(11));
					
					if(dataList.get(12).equals("true")) {
						ballShieldAbility = true;
					}
					shieldPower = Integer.parseInt(dataList.get(13));
					shieldActivationAmount = Integer.parseInt(dataList.get(14));
					shieldPowerLevel = Integer.parseInt(dataList.get(15));
					shieldActivationAmountLevel = Integer.parseInt(dataList.get(16));
					shieldPowerUpgradePrice = Integer.parseInt(dataList.get(17));
					shieldActivationAmountUpgradePrice = Integer.parseInt(dataList.get(18));
					
					if(dataList.get(19).equals("true")) {
						ballFreezeAbility = true;
					}
					freezeActivationAmount = Integer.parseInt(dataList.get(20));
					freezeActivationAmountLevel = Integer.parseInt(dataList.get(21));
					freezeActivationAmountUpgradePrice = Integer.parseInt(dataList.get(22));
					
					if(dataList.get(23).equals("true")) {
						ball.twoBulletsAtOnce = true;
					}
					ballBulletReloadSpeedLevel = Integer.parseInt(dataList.get(24));
					ballBulletReloadSpeedUpgradePrice = Integer.parseInt(dataList.get(25));
					ball.type = Integer.parseInt(dataList.get(26));
					
					String[] totalBallsData = dataList.get(27).split(" ");
					for(int i = 0; i < totalBalls.length; i++) {
						totalBalls[i] = Integer.parseInt(totalBallsData[i]);
					}
					
					ball.bulletReloadSpeed = Integer.parseInt(dataList.get(28));
					
					String[] modesData = dataList.get(29).split(" ");
					for(int i = 0; i < modes.length; i++) {
						if(modesData[i].equals("true")) {
							modes[i].setSelected(true);
						} else {
							modes[i].setSelected(false);
						}
					}
					
					String[] differentBlocksData = dataList.get(30).split(" ");
					for(int i = 0; i < differentBlocks.length; i++) {
						if(differentBlocksData[i].equals("true")) {
							differentBlocks[i].setSelected(true);
						} else {
							differentBlocks[i].setSelected(false);
						}
					}
					
					differentBlocksInGame.clear();
					String[] differentBlocksInGameData = dataList.get(31).split(" ");
					for(int i = 0; i < differentBlocksInGameData.length; i++) {
						differentBlocksInGame.add(differentBlocksInGameData[i]);
					}
				}
				
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
