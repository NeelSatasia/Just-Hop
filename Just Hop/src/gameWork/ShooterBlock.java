package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ShooterBlock extends Blocks {
	
	Color blockColor = Color.BLACK;
	
	int gunXPosition;
	
	int counter = 0;
	
	ArrayList<BlockBullet> bullets = new ArrayList<BlockBullet>();
	
	int randX;
	int coinChance = (int)(Math.random() * 10);
	Coin coin;
	
	int blockColorTransparency = 255;

	public ShooterBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		
		gunXPosition = this.x + ((this.width/2) - 10) + 5;
		
		bullets.add(new BlockBullet(this.x + ((this.width/2) - 10) + 5, this.y + this.height));
		
		if(coinChance > 5) {
			randX = (int) (Math.random() * (this.width - 5)) + this.x;
			coin = new Coin(randX, this.y + 4);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.setColor(blockColor);
		g.fillRect(this.x + ((this.width/2) - 10), this.y + this.height, 20, 15);
		g.fillRect(gunXPosition, this.y + this.height + 15, 10, 5);
		
		if(coinChance > 5) {
			coin.changeYPosition(this.y + 4);
			coin.draw(g);
		}
		
		counter++;
		
		if(counter == 50 && blockColorTransparency == 255) {
			bullets.add(new BlockBullet(this.x + ((this.width/2) - 10) + 5, this.y + this.height));
			counter = 0;
		}
		
		for(BlockBullet bullet: bullets) {
			g.setColor(blockColor);
			bullet.draw(g);
		}
	}
	
	@Override
	public void changeColorTransparency(int colorTransparency) {
		blockColor = new Color(0, 0, 0, colorTransparency);
		blockColorTransparency = colorTransparency;
	}
	
	@Override
	public ArrayList<BlockBullet> getBulletsList() {
		return bullets;
	}
	
	@Override
	public void removeBullet(int index) {
		bullets.remove(index);
	}
}
