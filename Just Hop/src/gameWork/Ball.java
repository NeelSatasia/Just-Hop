package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball extends Rectangle {
	
	Color brickColor;
	
	public Ball(int x, int y, int w, int h, Color color) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.brickColor = color;
	}	
	
	public void draw(Graphics g) {
		g.setColor(this.brickColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		g.setColor(Color.BLACK);
		g.drawRect(this.x-1, this.y-1, this.width+1, this.height+1);
	}
}
