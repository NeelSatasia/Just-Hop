package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class HalfRedBlock extends Blocks {
	
	Color blackColor;
	Color redColor;
	
	int randNum;
	boolean isRedPartOnRightSide = false;
	
	public HalfRedBlock(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.blackColor = Color.BLACK;
		this.redColor = Color.RED;
		
		randNum = (int)(Math.random() * 6);
		
		if(randNum < 3) {
			isRedPartOnRightSide = true;
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
	}
	
	@Override
	public void changeColorTransparency(Color halfBlack, Color halfRed) {
		blackColor = halfBlack;
		redColor = halfRed;
	}
	
	@Override
	public boolean isRedOnRightSide() {
		return isRedPartOnRightSide;
	}
}
