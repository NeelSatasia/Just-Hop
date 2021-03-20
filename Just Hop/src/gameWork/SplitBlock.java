package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class SplitBlock extends Blocks {
	
	int whichBlockSmaller = (int)(Math.random() * 2) + 1;
	int secondBlockXPosition = 0;
	int ballWidth = 0;
	
	Color blockColor;
	
	int randX;
	Coin coin = null;
	
	HealthBooster healthBooster = null;
	
	public SplitBlock(int x, int y, int w, int ballW) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		ballWidth = ballW;
		
		if(whichBlockSmaller == 1) {
			secondBlockXPosition = this.x + (this.width/2);
		} else {
			secondBlockXPosition = this.x + ((this.width/2) + ballWidth);
		}
		
		blockColor = Color.BLACK;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(blockColor);
		if(whichBlockSmaller == 1) {
			g.fillRect(this.x, this.y, (this.width/2) - (ballWidth), this.height);
			g.fillRect(secondBlockXPosition, this.y, this.width/2, this.height);
		} else {
			g.fillRect(this.x, this.y, this.width/2, this.height);
			g.fillRect(secondBlockXPosition, this.y, (this.width/2) - (ballWidth), this.height);
		}
		
		if(coin != null) {
			coin.changeYPosition(this.y);
			coin.draw(g);
		}
		
		if(healthBooster != null) {
			healthBooster.changeYPosition(this.y);
			healthBooster.draw(g);
		}
	}
	
	@Override
	public int secondBlockXPosition() {
		return secondBlockXPosition;
	}
	
	@Override
	public void changeColorTransparency(int colorTransparency) {
		blockColor = new Color(0, 0, 0, colorTransparency);
	}
	
	@Override
	public HealthBooster getHealthBooster() {
		return healthBooster;
	}
	
	@Override
	public void addHealthBooster(boolean b) {
		if(b) {
			healthBooster = new HealthBooster((int)(Math.random() * (this.width - 16)) + this.x, this.y);
		} else {
			healthBooster = null;
		}
	}
	
	@Override
	public Coin getCoin() {
		return coin;
	}
	
	@Override
	public void addCoin(boolean b) {
		if(b) {
			randX = (int) (Math.random() * (this.width - 12)) + this.x;
			coin = new Coin(randX, this.y);
		} else {
			coin = null;
		}
	}
}
