package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class UpsideDownTBlock extends Blocks {
	
	Color blockColor;
	
	double TBarXPosition = 0;
	
	boolean isTBarRight = false;
	
	public UpsideDownTBlock(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.blockColor = Color.BLACK;
		
		TBarXPosition = this.x + (this.width/2) - (5/2);
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.blockColor);
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g.fillRect((int) TBarXPosition, this.y - 40, 5, 40);
	}
	
	@Override
	public void changeColorTransparency(Color color) {
		blockColor = color;
	}
	
	@Override
	public void changeBlockTPositionX() {
		if(TBarXPosition == this.x) {
			isTBarRight = true;
		} else if(TBarXPosition == this.x + this.width - 5) {
			isTBarRight = false;
		}
		
		if(isTBarRight == true) {
			TBarXPosition += 0.5;
		} else {
			TBarXPosition -= 0.5;
		}
	}
	
	@Override
	public int blockTPositionX() {
		return (int) TBarXPosition;
	}
	
	@Override
	public boolean isTBarRight() {
		return isTBarRight;
	}
}

