package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BallBullet extends Rectangle {
	
	public BallBullet(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 10;
		this.height = 10;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillArc(this.x, this.y, 10, 10, 0, 360);
		
		this.y -= 5;
	}
}
