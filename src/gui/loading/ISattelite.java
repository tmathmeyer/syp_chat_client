package gui.loading;

import java.awt.Graphics;

public interface ISattelite {
	public ISattelite getOrbit();
	public int getX();
	public int getY();
	public double getAngle();
	public int getRadius();
	public void drawMe(Graphics g);
	public void update();
	public void setWaiting(boolean waiting);
}
