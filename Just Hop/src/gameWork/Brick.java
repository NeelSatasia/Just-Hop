package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Brick extends Rectangle {
	
	Color brickColor;
	
	public Brick(int x, int y, int w, int h, Color color) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.brickColor = color;
	}	
	
	public void draw(Graphics g) {
		g.setColor(this.brickColor);
		g.fillRect(this.x, this.y, this.width, this.height);
	}
	
	public void changeColor(Color color) {
		this.brickColor = color;
	}
}
