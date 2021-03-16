package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class ShooterBlock extends Blocks {
	
	Color blockColor = Color.BLACK;
	
	Bullet bullet;
	
	boolean bulletHitBlock = false;
	
	Blocks[] duplicateBlocks;
	Ball duplicateBall;
	
	public ShooterBlock(int x, int y, int w, Blocks[] blocks, Ball ball) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		duplicateBlocks = blocks;
		bullet = new Bullet(this.x + ((this.width/2) - (15/2)), this.y + this.height);
		duplicateBall = ball;
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.setColor(blockColor);
		g.fillRect(this.x + ((this.width/2) - (15/2)), this.y + this.height, 15, 15);
		
		bullet.bulletInitialYPosition(this.y + this.height);
		bullet.draw(g, duplicateBall);
	}
	
	@Override
	public void changeColorTransparency(int colorTransparency) {
		blockColor = new Color(0, 0, 0, colorTransparency);
	}
	
	@Override
	public boolean didBallLoseHealthFromBullet() {
		return bullet.didHitBall();
	}
}
