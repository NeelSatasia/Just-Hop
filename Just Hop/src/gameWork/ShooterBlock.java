package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class ShooterBlock extends Blocks {
	
	Color blockColor = Color.BLACK;
	
	int bulletXPosition;
	int bulletYPosition;

	public ShooterBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		
		bulletXPosition = this.x + ((this.width/2) - 10) + 5;
		bulletYPosition = this.y + this.height;
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.setColor(blockColor);
		g.fillRect(this.x + ((this.width/2) - 10), this.y + this.height, 20, 15);
		g.fillRect(bulletXPosition, this.y + this.height + 15, 10, 5);
		
		g.fillPolygon(new int[] {bulletXPosition, bulletXPosition + 10, bulletXPosition + 5}, new int[] {bulletYPosition, bulletYPosition, bulletYPosition + 10}, 3);
		
		bulletYPosition += 5;
	}
	
	@Override
	public void changeColorTransparency(int colorTransparency) {
		blockColor = new Color(0, 0, 0, colorTransparency);
	}
	
	@Override
	public int getBulletXPosition() {
		return bulletXPosition;
	}
	
	@Override
	public int getBulletYPosition() {
		return bulletYPosition;
	}
	
	@Override
	public void setBulletYPosition() {
		bulletYPosition = this.y + this.height;
	}
}
