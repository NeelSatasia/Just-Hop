package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class SpikyBlock extends Blocks{
	
	Color brickColor;
	
	public SpikyBlock(int x, int y, int w, int h, Color color) {
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
		
		g.fillRect(this.x+this.width-5, this.y-25, 5, 25);
		
		g.fillPolygon(new int[] {this.x + (this.width/2 + 5), this.x + this.width - 5, this.x + this.width - 5}, new int[] {this.y - 5, this.y, this.y - 10}, 3);
		g.fillPolygon(new int[] {this.x + (this.width/2 + 5), this.x + this.width - 5, this.x + this.width - 5}, new int[] {this.y - 15, this.y - 10, this.y - 20}, 3);
		
		g.setColor(Color.RED);
		g.fillRect(this.x+(this.width/2) + 5, this.y-30, this.width/2 - 5, 5);
	}
}

