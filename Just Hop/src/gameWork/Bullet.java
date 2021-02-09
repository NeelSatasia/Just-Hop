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
	
	public Bullet(int x, int y) {
		bulletX = x;
		bulletY = y;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillArc(bulletX, bulletY, 10, 10, 0, 360);
	}
}
