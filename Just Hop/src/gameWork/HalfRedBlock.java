package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class HalfRedBlock extends Blocks {
	
	Color blockFirstColor;
	Color blockSecondColor;
	
	public HalfRedBlock(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.blockFirstColor = Color.BLACK;
		this.blockSecondColor = Color.RED;
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.blockFirstColor);
		g.fillRect(this.x, this.y, this.width/2, this.height);
		
		int rect2X = this.width/2;
		
		g.setColor(this.blockSecondColor);
		g.fillRect(this.x+rect2X, this.y, rect2X, this.height);
	}
	
	@Override
	public void changeColorTransparency(Color halfBlack, Color halfRed) {
		blockFirstColor = halfBlack;
		blockSecondColor = halfRed;
	}
}
