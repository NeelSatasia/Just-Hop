package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class RegularBlock extends Blocks {
	
	Color blockColor;
	
	public RegularBlock(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.blockColor = Color.BLACK;
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
	}
	
	public void changeColorTransparency(Color brickColor2) {
		blockColor = brickColor2;
	}
}
