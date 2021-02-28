package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class SplitBlock extends Blocks {
	
	int whichBlockSmaller = (int)(Math.random() * 2) + 1;
	int secondBlockXPosition = 0;
	int ballWidth = 0;
	
	Color blockColor;
	
	public SplitBlock(int x, int y, int w, int h, int ballW) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		ballWidth = ballW;
		
		if(whichBlockSmaller == 1) {
			secondBlockXPosition = this.x + (this.width/2);
		} else {
			secondBlockXPosition = this.x + ((this.width/2) + ballWidth);
		}
		
		blockColor = Color.BLACK;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(blockColor);
		if(whichBlockSmaller == 1) {
			g.fillRect(this.x, this.y, (this.width/2) - (ballWidth), this.height);
			g.fillRect(secondBlockXPosition, this.y, this.width/2, this.height);
		} else {
			g.fillRect(this.x, this.y, this.width/2, this.height);
			g.fillRect(secondBlockXPosition, this.y, (this.width/2) - (ballWidth), this.height);
		}
	}
	
	@Override
	public int secondBlockXPosition() {
		return secondBlockXPosition;
	}
	
	@Override
	public void changeColorTransparency(Color color, Color color2) {
		blockColor = color;
	}
}
