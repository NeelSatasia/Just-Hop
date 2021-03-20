package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ShooterBlock extends Blocks {
	
	Color blockColor = Color.BLACK;
	
	int gunXPosition;
	
	int gunReloadCounter = 0;
	
	ArrayList<BlockBullet> bullets = new ArrayList<BlockBullet>();
	
	int randX;
	Coin coin = null;
	
	HealthBooster healthBooster = null;
	
	int blockColorTransparency = 255;

	public ShooterBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		
		gunXPosition = this.x + ((this.width/2) - 10) + 5;
		
		bullets.add(new BlockBullet(this.x + ((this.width/2) - 10) + 5, this.y + this.height));
	}
	
	@Override
	public void draw(Graphics g) {
		
		gunReloadCounter++;
		
		if(gunReloadCounter == 50 && blockColorTransparency == 255) {
			bullets.add(new BlockBullet(this.x + ((this.width/2) - 10) + 5, this.y + this.height));
			gunReloadCounter = 0;
		}
		
		for(BlockBullet bullet: bullets) {
			g.setColor(blockColor);
			bullet.draw(g);
		}
		
		g.setColor(blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.setColor(blockColor);
		g.fillRect(this.x + ((this.width/2) - 10), this.y + this.height, 20, 15);
		g.fillRect(gunXPosition, this.y + this.height + 15, 10, 5);
		
		if(coin != null) {
			coin.changeYPosition(this.y);
			coin.draw(g);
		}
		
		if(healthBooster != null) {
			healthBooster.changeYPosition(this.y);
			healthBooster.draw(g);
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
	
	@Override
	public HealthBooster getHealthBooster() {
		return healthBooster;
	}
	
	@Override
	public void addHealthBooster(boolean b) {
		if(b) {
			healthBooster = new HealthBooster((int)(Math.random() * (this.width - 16)) + this.x, this.y);
		} else {
			healthBooster = null;
		}
	}
	
	@Override
	public Coin getCoin() {
		return coin;
	}
	
	@Override
	public void addCoin(boolean b) {
		if(b) {
			randX = (int) (Math.random() * (this.width - 12)) + this.x;
			coin = new Coin(randX, this.y);
		} else {
			coin = null;
		}
	}
}
