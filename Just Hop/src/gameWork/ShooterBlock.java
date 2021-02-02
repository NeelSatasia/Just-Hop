package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class ShooterBlock extends Blocks implements ActionListener {
	
	Timer timer = new Timer(5, this);
	
	Color brickColor;
	
	public ShooterBlock(int x, int y, int w, int h, Color color) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.brickColor = color;
		
		timer.start();
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.brickColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.setColor(Color.BLUE);
		g.fillRect(this.x+(this.width/2)-8, this.y+this.height, 15, 15);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}

