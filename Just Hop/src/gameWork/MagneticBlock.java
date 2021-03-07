package gameWork;

import java.awt.Color;
import java.awt.Graphics;

public class MagneticBlock extends Blocks {
	
	Color blockColor = Color.BLACK;
	Color magnetbarColor = new Color(104, 104, 104);
	
	int TBarXPosition = (int)(Math.random() * 1);
	int TBarHeight = (int)(Math.random() * 6) + 30;
	
	public MagneticBlock(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = 5;
		
		if(TBarXPosition() == 0) {
			TBarXPosition = x;
		} else {
			TBarXPosition = x + w - 5;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		//magnetic bar
		g.setColor(magnetbarColor);
		g.fillRect(TBarXPosition, this.y - TBarHeight, 5, TBarHeight);
	}
	
	@Override
	public void changeColorTransparency(int colorTransparency) {
		blockColor = new Color(0, 0, 0, colorTransparency);
		magnetbarColor = new Color(104, 104, 104, colorTransparency);
	}
	
	@Override
	public int TBarXPosition() {
		return TBarXPosition;
	}
	
	@Override
	public int TBarHeight() {
		return TBarHeight;
	}
}
