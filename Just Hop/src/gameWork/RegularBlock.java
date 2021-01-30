package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class RegularBlock extends Blocks{
	
	Color brickColor;
	
	public RegularBlock(int x, int y, int w, int h, Color color) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.brickColor = color;
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.brickColor);
		g.fillRect(this.x, this.y, this.width, this.height);
	}
}
