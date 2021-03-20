package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Ball extends Rectangle {
	
	Color ballColor;
	
	ArrayList<BallBullet> bullets = new ArrayList<BallBullet>();
	
	boolean shootBullets = false;
	
	int bulletReloadSpeed = 0;
	
	public Ball(int x, int y, int w, int h, Color color) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.ballColor = color;
	}	
	
	public void draw(Graphics g) {
		g.setColor(this.ballColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		if(shootBullets) {
			bulletReloadSpeed++;
			if(bulletReloadSpeed == 50) {
				bullets.add(new BallBullet(this.x + (this.width/2) - 5, this.y));
				bulletReloadSpeed = 0;
			}
		} else if(bulletReloadSpeed > 0) {
			bulletReloadSpeed = 0;
		}
		
		
		int i = 0;
		while(i < bullets.size()) {
			if(bullets.get(i).y > 0) {
				bullets.get(i).draw(g);
				i++;
			} else {
				bullets.remove(i);
			}
		}
	}
	
	public void changeColor(Color color) {
		ballColor = color;
	}
	
	public Color getBallColor() {
		return ballColor;
	}
}
