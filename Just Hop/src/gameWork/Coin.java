package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Coin extends Rectangle {
	
	public Coin(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 5;
		this.height = 5;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(230, 92, 0));
		g.fillArc(this.x, this.y, 10, 10, 0, 360);
	}
	
	public void changeYPosition(int newYPosition) {
		this.y = newYPosition;
	}
}
