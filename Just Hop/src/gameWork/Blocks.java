package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Blocks extends Rectangle {
	
	//different blocks' different features
	
	public void draw(Graphics g) {
	}
	
	public void changeColorTransparency(int colorTransparency) {
	}
	
	public boolean isRedOnRightSide() {
		return false;
	}
	
	public void changeTBarXPosition(boolean b) {
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
	
	public ArrayList<BlockBullet> getBulletsList() {
		return null;
	}
	
	public void removeBullet(int index) {
	}

	public HealthBooster getHealthBooster() {
		return null;
	}

	public void addHealthBooster(boolean b) {
	}
	
	public Coin getCoin() {
		return null;
	}

	public void addCoin(boolean b) {
	}

	public void freezeBullets(boolean b) {
	}
}
