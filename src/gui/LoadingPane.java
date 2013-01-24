package gui;

import gui.loading.Center;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class LoadingPane extends MainPanel implements Runnable{
	public boolean loading = false;
	Center o;
	
	public LoadingPane(){
		Thread th = new Thread(this);
		th.start();
	}
	
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 5000, 5000);
		
		g.setColor(Color.WHITE);
		o.drawMe(g);
	}
	
	public void run(){
		o = new Center(6, 4, 200);
		
		while(true){
			try{
				Thread.sleep(5);
				o.update();
				this.repaint();
			}
			catch(Exception e){}
		}
	}
	
	public void setWaiting(boolean waiting){
		this.o.setWaiting(waiting);
	}
}





