package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class RegularBlock extends Blocks {
	
	Color blockColor;
	
	int randX;
	int coinChance = (int)(Math.random() * 10);
	Coin coin;
	
	public RegularBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		this.blockColor = Color.BLACK;
		
		if(coinChance > 5) {
			randX = (int) (Math.random() * (this.width - 5)) + this.x;
			coin = new Coin(randX, this.y + 4);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		if(coinChance > 5) {
			coin.changeYPosition(this.y + 4);
			coin.draw(g);
		}
	}
	
	@Override
	public void changeColorTransparency(int colorTransparency) {
		blockColor = new Color(0, 0, 0, colorTransparency);
	}
}
