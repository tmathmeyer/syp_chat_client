package gui.loading;

import java.awt.Graphics;

public class Center implements ISattelite{

	public ISattelite[] orbits;
	
	public Center(int satCount, int depth, int radius){
		orbits = new Sattelite[satCount];
		for(int i = 0; i < satCount; i++){
			double rad = (i*Math.PI*2)/((double)satCount);
			orbits[i] = new Sattelite(this, rad, radius, satCount, depth-1, depth-1);
		}
	}
	
	
	@Override
	public ISattelite getOrbit() {
		return null;
	}

	@Override
	public int getX() {
		return 600;
	}

	@Override
	public int getY() {
		return 350;
	}

	@Override
	public double getAngle() {
		return 0;
	}

	@Override
	public int getRadius() {
		return 0;
	}

	@Override
	public void drawMe(Graphics g) {
		//g.fillOval(this.getX()-5, this.getY()-5, 10, 10);
		//g.drawLine(this.getX(), this.getY(), this.getOrbit().getX(), this.getOrbit().getY());
		for(ISattelite i : orbits){
			i.drawMe(g);
		}
	}


	@Override
	public void update() {
		for(ISattelite i : orbits){
			i.update();
		}
	}


	@Override
	public void setWaiting(boolean waiting) {
		for(ISattelite i : orbits){
			i.setWaiting(waiting);
		}
	}
}
