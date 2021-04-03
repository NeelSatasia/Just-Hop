package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class WiperBlock extends Blocks {
	
	Color blockColor;
	
	int TBarXPosition;
	
	boolean isTBarRight = true;
	
	int TBarHeight = (int)(Math.random() * 21) + 25;
	
	int randX;
	Coin coin = null;
	
	HealthBooster healthBooster = null;
	
	public WiperBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		this.blockColor = Color.BLACK;
		
		TBarXPosition = this.x + (this.width/2) - (5/2);
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.fillRect(TBarXPosition, this.y - TBarHeight, 5, TBarHeight); //Wiper
		
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
	public void changeTBarXPosition(boolean b) {
		if(b) {
			if(isTBarRight == true) {
				TBarXPosition++;
			} else {
				TBarXPosition--;
			}
			
			if(TBarXPosition == this.x) {
				isTBarRight = true;
			} else if(TBarXPosition == this.x + this.width - 5) {
				isTBarRight = false;
			}
		}
	}
	
	@Override
	public int TBarXPosition() {
		return TBarXPosition;
	}
	
	@Override
	public boolean isTBarRight() {
		return isTBarRight;
	}
	
	@Override
	public int TBarHeight() {
		return TBarHeight;
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

