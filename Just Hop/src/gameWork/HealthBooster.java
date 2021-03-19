package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HealthBooster extends Rectangle {
	
	boolean collisionWithBall = false;
	
	public HealthBooster(int x, int y) {
		this.width = 16;
		this.height = 16;
		this.x = x;
		this.y = y - this.height;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(51, 255, 51));
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.setColor(new Color(0, 102, 0));
		g.fillRect(this.x + 2, this.y + 6, 12, 4);
		g.fillRect(this.x + 6, this.y + 2, 4, 12);
	}
	
	public void changeYPosition(int newYPosition) {
		this.y = newYPosition - this.height;
	}
}
