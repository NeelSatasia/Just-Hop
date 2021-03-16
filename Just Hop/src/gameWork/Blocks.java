package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blocks extends Rectangle{
	
	//different blocks' different features
	
	public void draw(Graphics g) {
	}
	
	public void changeColorTransparency(int colorTransparency) {
	}
	
	public boolean isRedOnRightSide() {
		return false;
	}
	
	public void changeTBarXPosition() {
	}
	
	public int TBarXPosition() {
		return 0;
	}

	public boolean isTBarRight() {
		return false;
	}

	public int TBarHeight() {
		return 0;
	}
	
	public int secondBlockXPosition() {
		return 0;
	}
	
	public boolean didBallLoseHealthFromBullet() {
		return false;
	}
}
