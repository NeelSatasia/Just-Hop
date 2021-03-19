package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class RegularBlock extends Blocks {
	
	Color blockColor;
	
	int randX;
	int coinChance = (int)(Math.random() * 10);
	Coin coin = null;
	
	HealthBooster healthBooster = null;
	
	public RegularBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		this.blockColor = Color.BLACK;
		
		if(coinChance > 5) {
			randX = (int) (Math.random() * (this.width - 12)) + this.x;
			coin = new Coin(randX, this.y);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
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
}
