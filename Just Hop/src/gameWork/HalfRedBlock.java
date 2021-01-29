package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class HalfRedBlock extends Blocks {
	
	Color brickColor;
	
	public HalfRedBlock(int x, int y, int w, int h, Color color) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.brickColor = color;
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.brickColor);
		g.fillRect(this.x, this.y, this.width/2, this.height);
		
		int rect2X = this.width/2;
		
		g.setColor(Color.RED);
		g.fillRect(this.x+rect2X, this.y, rect2X, this.height);
	}
}
