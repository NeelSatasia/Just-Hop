package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class WiperBlock extends Blocks {
	
	Color blockColor;
	
	double TBarXPosition = 0;
	
	boolean isTBarRight = true;
	
	int TBarHeight = 50;
	
	public WiperBlock(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.blockColor = Color.BLACK;
		
		TBarXPosition = this.x + (this.width/2) - (5/2);
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.fillRect((int) TBarXPosition, this.y - TBarHeight, 5, TBarHeight);
	}
	
	@Override
	public void changeColorTransparency(Color color, Color color2) {
		blockColor = color;
	}
	
	@Override
	public void changeTBarXPosition() {
		if(TBarXPosition == this.x) {
			isTBarRight = true;
		} else if(TBarXPosition == this.x + this.width - 5) {
			isTBarRight = false;
		}
		
		if(isTBarRight == true) {
			TBarXPosition++;
		} else {
			TBarXPosition--;
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

