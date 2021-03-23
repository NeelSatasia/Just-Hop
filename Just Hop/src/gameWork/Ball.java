package gameWork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Ball extends Rectangle {
	
	Color ballColor;
	
	ArrayList<BallBullet> bullets = new ArrayList<BallBullet>();
	
	boolean shootBullets = false;
	
	int bulletReloadSpeedCounter = 0;
	int bulletReloadSpeed = 50;
	
	int ballType;
	
	public Ball(int ballNum, int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 16;
		this.height = 16;
		ballType = ballNum;
	}	
	
	public void draw(Graphics g) {
		if(ballType == 1) {
			g.setColor(new Color(0, 153, 0));
			g.fillRect(this.x, this.y, this.width, this.height);
		} else if(ballType == 2) {
			g.setColor(new Color(102, 255, 102));
			g.fillRect(this.x, this.y, 4, 4);
			g.fillRect(this.x + 6, this.y, 4, 4);
			g.fillRect(this.x + (this.width - 4), this.y, 4, 4);
			
			g.setColor(new Color(0, 204, 0));
			g.fillRect(this.x, this.y + 6, 4, 4);
			g.fillRect(this.x + 6, this.y + 6, 4, 4);
			g.fillRect(this.x + (this.width - 4), this.y + 6, 4, 4);
			
			g.setColor(new Color(0, 102, 0));
			g.fillRect(this.x, this.y + (this.height - 4), 4, 4);
			g.fillRect(this.x + 6, this.y + (this.height - 4), 4, 4);
			g.fillRect(this.x + (this.width - 4), this.y + (this.height - 4), 4, 4);
		} else if(ballType == 3) {
			g.setColor(new Color(230, 0, 0));
			g.fillPolygon(new int[] {this.x, this.x + (this.width/2/2), this.x + (this.width/2)}, new int[] {this.y, this.y - 10, this.y}, 3);
			g.fillPolygon(new int[] {this.x + (this.width/2), this.x + (this.width - (this.width/4)), this.x + this.width}, new int[] {this.y, this.y - 10, this.y}, 3);
			
			g.setColor(Color.BLACK);
			g.fillRect(this.x, this.y, this.width, this.height);
			
			g.setColor(new Color(77, 195, 255));
			g.fillRect(this.x + 2, this.y + 4, 3, 3);
			g.fillRect(this.x + (this.width - 5), this.y + 4, 3, 3);
			g.setColor(Color.RED);
			g.fillRect(this.x + 4, this.y + (this.height - 4), 8, 2);
			g.fillRect(this.x + 2, this.y + (this.height - 5), 2, 2);
			g.fillRect(this.x + (this.width - 4), this.y + (this.height - 5), 2, 2);
		} else if(ballType == 4) {
			g.setColor(new Color(0, 153, 0));
			g.fillPolygon(new int[] {this.x, this.x, this.x + 6}, new int[] {this.y + 2, this.y + (this.height - 2), this.y + 8}, 3);
			g.setColor(new Color(255, 102, 0));
			g.fillPolygon(new int[] {this.x + 2, this.x + (this.width - 2), this.x + 8}, new int[] {this.y, this.y, this.y + 6}, 3);
			g.setColor(new Color(0, 51, 204));
			g.fillPolygon(new int[] {this.x + this.width, this.x + this.width, this.x + (this.width - 6)}, new int[] {this.y + 2, this.y + (this.height - 2), this.y + 8}, 3);
			g.setColor(new Color(230, 0, 115));
			g.fillPolygon(new int[] {this.x + 2, this.x + (this.width - 2), this.x + 8}, new int[] {this.y + this.height, this.y + this.height, this.y + (this.height - 6)}, 3);
		}
		
		if(shootBullets) {
			bulletReloadSpeedCounter++;
			if(bulletReloadSpeedCounter == bulletReloadSpeed) {
				bullets.add(new BallBullet(this.x + (this.width/2) - 5, this.y));
				bulletReloadSpeedCounter = 0;
			}
		} else if(bulletReloadSpeedCounter > 0) {
			bulletReloadSpeedCounter = 0;
		}
		
		
		int i = 0;
		while(i < bullets.size()) {
			if(bullets.get(i).y > 0) {
				bullets.get(i).draw(g);
				i++;
			} else {
				bullets.remove(i);
			}
		}
	}
	
	public void draw(Graphics g, int ball) {
		
	}
	
	public void changeColor(Color color) {
		ballColor = color;
	}
	
	public Color getBallColor() {
		return ballColor;
	}
}
