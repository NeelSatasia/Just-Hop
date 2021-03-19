package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class HalfRedBlock extends Blocks {
	
	Color blackColor;
	Color redColor;
	
	int randNum;
	boolean isRedPartOnRightSide = false;
	
	int randX;
	int coinChance = (int)(Math.random() * 10);
	Coin coin = null;
	
	public HalfRedBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		this.blackColor = Color.BLACK;
		this.redColor = Color.RED;
		
		randNum = (int)(Math.random() * 6);
		
		if(randNum < 3) {
			isRedPartOnRightSide = true;
		}
		
		if(coinChance > 5) {
			randX = (int) (Math.random() * (this.width - 12)) + this.x;
			coin = new Coin(randX, this.y);
		}
	}	
	
	@Override
	public void draw(Graphics g) {
		if(isRedPartOnRightSide) {
			g.setColor(this.blackColor);
		} else {
			g.setColor(this.redColor);
		}
		
		g.fillRect(this.x, this.y, this.width/2, this.height);
		
		int rect2X = this.width/2;
		
		if(isRedPartOnRightSide) {
			g.setColor(this.redColor);
		} else {
			g.setColor(this.blackColor);
		}
		
		g.fillRect(this.x+rect2X, this.y, rect2X, this.height);
		
		if(coin != null) {
			coin.changeYPosition(this.y);
			coin.draw(g);
		}
	}
	
	@Override
	public void changeColorTransparency(int colorTransparency) {
		blackColor = new Color(0, 0, 0, colorTransparency);
		redColor = new Color(255, 0, 0, colorTransparency);
	}
	
	@Override
	public boolean isRedOnRightSide() {
		return isRedPartOnRightSide;
	}
}
