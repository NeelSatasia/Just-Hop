package gameWork;

import java.awt.Graphics;
import java.awt.Rectangle;

public class BlockBullet extends Rectangle {
	
	public BlockBullet(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 10;
		this.height = 10;
	}
	
	public void draw(Graphics g) {
		g.fillPolygon(new int[] {this.x, this.x + 10, this.x + 5}, new int[] {this.y, this.y, this.y + 10}, 3); //bullet
		this.y += 5;
	}
	
	public int getBulletXPosition() {
		return this.x;
	}

	public int getBulletYPosition() {
		return this.y;
	}
}
