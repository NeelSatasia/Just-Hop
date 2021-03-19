package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class WiperBlock extends Blocks {
	
	Color blockColor;
	
	int TBarXPosition = 0;
	
	boolean isTBarRight = true;
	
	int TBarHeight = (int)(Math.random() * 21) + 25;
	
	int randX;
	int coinChance = (int)(Math.random() * 10);
	Coin coin;
	
	public WiperBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		this.blockColor = Color.BLACK;
		
		TBarXPosition = this.x + (this.width/2) - (5/2);
		
		if(coinChance > 5) {
			randX = (int) (Math.random() * (this.width - 7)) + this.x;
			coin = new Coin(randX, this.y + 4);
		}
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.fillRect(TBarXPosition, this.y - TBarHeight, 5, TBarHeight); //Wiper
		
		if(coinChance > 5) {
			coin.changeYPosition(this.y + 4);
			coin.draw(g);
		}
	}
	
	@Override
	public void changeColorTransparency(int colorTransparency) {
		blockColor = new Color(0, 0, 0, colorTransparency);
	}
	
	@Override
	public void changeTBarXPosition() {
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
	
	@Override
	public int TBarXPosition() {
		return (int) TBarXPosition;
	}
	
	@Override
	public boolean isTBarRight() {
		return isTBarRight;
	}
	
	@Override
	public int TBarHeight() {
		return TBarHeight;
	}
}

