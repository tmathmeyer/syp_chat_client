package gui.loading;

import java.awt.Color;
import java.awt.Graphics;

public class Sattelite implements ISattelite{

	boolean waiting = false;
	ISattelite parent;
	double angle;
	int radius;
	public Sattelite[] sats;
	int depth, maxDepth;
	
	public Sattelite(ISattelite parent, double angle, int radius, int satCount, int depth, int maxDepth){
		this.parent = parent;
		this.angle = angle;
		this.radius = radius;
		this.depth = maxDepth-depth;
		this.maxDepth = maxDepth;
		
		if (depth > 0){
			sats = new Sattelite[satCount];
			for(int i = 0; i < satCount; i++){
				double rad = (i*Math.PI*2)/((double)satCount);
				sats[i] = new Sattelite(this, rad, (int) (radius/1.5), satCount, depth-1, maxDepth);
			}
		}
	}
	
	
	@Override
	public ISattelite getOrbit() {
		return parent;
	}

	@Override
	public int getX() {
		return (int) (Math.cos(this.getAngle())*this.getRadius()+this.getOrbit().getX());
	}

	@Override
	public int getY() {
		return (int) (Math.sin(this.getAngle())*this.getRadius()+this.getOrbit().getY());
	}

	@Override
	public double getAngle() {
		return this.angle;
	}

	@Override
	public int getRadius() {
		return this.radius;
	}


	@Override
	public void drawMe(Graphics g) {
		
		if (sats!=null){
			for(ISattelite i : sats){
				i.drawMe(g);
			}
		}
		
		g.setColor(new Color(255-(255/(maxDepth+1))*depth, 0, 255-(255/(maxDepth+1))*depth));
		if (this.waiting && Math.random()>.6)
			g.setColor(new Color(0, 255-(255/maxDepth)*depth, 0));
		g.drawLine(this.getX(), this.getY(), this.getOrbit().getX(), this.getOrbit().getY());
		int x = this.getX();
		int y = this.getY();
		
		g.fillOval(x-5, y-5, 10, 10);
		
	}


	@Override
	public void update() {
		this.angle+=.005*Math.pow(1.5, this.depth);
		if (sats!=null){
			for(ISattelite i : sats){
				i.update();
			}
		}
	}


	@Override
	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
		if (sats!=null){
			for(ISattelite i : sats){
				i.setWaiting(waiting);
			}
		}
	}

}
