package gameWork;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Coin extends Rectangle {
	
	public Coin(int x, int y) {
		this.width = 12;
		this.height = 12;
		this.x = x;
		this.y = y + 5;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, 12, 12);
		g.setColor(new Color(255, 255, 102));
		g.fillRect(this.x+1, this.y+1, 11, 11);
		
		g.setColor(new Color(204, 82, 0));
		g.setFont(new Font("Times New Roman", Font.BOLD, 10));
		g.drawString("C", this.x+3, this.y+10);
	}
	
	public void changeYPosition(int newYPosition) {
		this.y = newYPosition + 5;
	}
}
