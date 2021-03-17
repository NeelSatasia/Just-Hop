package gameWork;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends Rectangle {
	
	int bulletXPosition;
	int bulletYPosition;
	
	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
		bulletXPosition = x;
		bulletYPosition = y;
		this.width = 10;
		this.height = 10;
	}
	
	public void draw(Graphics g) {
		g.fillPolygon(new int[] {this.x, this.x + 10, this.x + 5}, new int[] {bulletYPosition, bulletYPosition, bulletYPosition + 10}, 3);
		bulletYPosition += 5;
	}
	
	public int getBulletXPosition() {
		return bulletXPosition;
	}

	public int getBulletYPosition() {
		return bulletYPosition;
	}
}
