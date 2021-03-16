package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Bullet extends Rectangle {
	
	int bulletX = 0;
	int bulletY = 0;
	
	int bulletInitialYPosition;
	
	boolean didHitBall = false;
	
	public Bullet(int x, int y) {
		bulletX = x;
		bulletY = y;
		bulletInitialYPosition = bulletY;
	}

	public void draw(Graphics g, Ball ball) {
		g.setColor(Color.BLUE);
		g.fillRect(bulletX, bulletY += 5, 10, 10);
		didHitBall = false;
		
		if(bulletY > 600) {
			bulletY = bulletInitialYPosition;
		} else if((bulletX + this.width > ball.getX() && bulletX < ball.getX() + ball.getWidth() && bulletY + this.height > ball.getY() && bulletY + this.height <= ball.getY() + ball.getHeight())) {
			didHitBall = true;
			bulletY = bulletInitialYPosition;
		}
	}

	public void changeBulletX(int x) {
		bulletX = x;
	}

	public void changeBulletY(int y) {
		bulletY = y;
	}
	
	public void bulletInitialYPosition(int blockYPosition) {
		bulletInitialYPosition = blockYPosition;
	}
	
	public boolean didHitBall() {
		return didHitBall;
	}
	
}
