package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Bullet extends Rectangle {
	
	private int bulletX = 0;
	private int bulletY = 0;
	
	private int bulletInitialY = 0;
	
	private int yDistance = 0;
	
	public Bullet(int x, int y) {
		bulletX = x;
		bulletY = y;
		
		bulletInitialY = y;
	}
	
	public void draw(Graphics g, int currentBlockYLocation) {
		g.setColor(Color.BLUE);
		g.fillArc(bulletX, bulletY + 5, 10, 10, 0, 360);
		bulletY += 3;
		yDistance += 3;
		
		if(yDistance >= 265) {
			bulletInitialY = currentBlockYLocation;
			bulletY = bulletInitialY;
			yDistance = 0;
		}
	}
}
