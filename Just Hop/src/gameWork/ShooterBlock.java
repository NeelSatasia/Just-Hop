package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ShooterBlock extends Blocks {
	
	Color blockColor = Color.BLACK;
	
	int gunXPosition;
	
	int counter = 0;
	
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public ShooterBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		
		gunXPosition = this.x + ((this.width/2) - 10) + 5;
		
		bullets.add(new Bullet(this.x + ((this.width/2) - 10) + 5, this.y + this.height));
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.setColor(blockColor);
		g.fillRect(this.x + ((this.width/2) - 10), this.y + this.height, 20, 15);
		g.fillRect(gunXPosition, this.y + this.height + 15, 10, 5);
		
		
		counter++;
		
		if(counter == 50) {
			bullets.add(new Bullet(this.x + ((this.width/2) - 10) + 5, this.y + this.height));
			counter = 0;
		}
		
		for(Bullet bullet: bullets) {
			bullet.draw(g);
		}
	}
	
	@Override
	public void changeColorTransparency(int colorTransparency) {
		blockColor = new Color(0, 0, 0, colorTransparency);
	}
	
	@Override
	public ArrayList<Bullet> getBulletsList() {
		return bullets;
	}
	
	@Override
	public void removeBullet(int index) {
		bullets.remove(index);
	}
}
