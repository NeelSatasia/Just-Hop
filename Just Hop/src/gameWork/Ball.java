package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball extends Rectangle {
	
	Color ballColor;
	
	public Ball(int x, int y, int w, int h, Color color) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.ballColor = color;
	}	
	
	public void draw(Graphics g) {
		g.setColor(this.ballColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		//g.fillArc(this.x, this.y, this.width, this.height, 0, 360);
	}
	
	public void changeColor(Color color) {
		ballColor = color;
	}
}
