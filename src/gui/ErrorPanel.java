package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ErrorPanel extends JPanel implements Runnable{
	String[] errorMessage = {""};
	int timeLeft = 0;
	ChatPanel p;
	
	public ErrorPanel(ChatPanel p){
		this.p = p;
		new Thread(this).start();
		this.setOpaque(false);
	}
	
	
	public void paintComponent(Graphics g){
		g.setColor(new Color(255,0,0,98));
		g.fillRect(0,0,800,800);
		g.setColor(Color.WHITE);
		
		g.setFont(new Font("Monospaced", Font.BOLD, 24));
		FontMetrics fm = g.getFontMetrics();
	    int h = (fm.getAscent()-1)*this.errorMessage.length + 5*(this.errorMessage.length -1);
	    g.drawLine(0, 50, 300, 50);
		for(int i = 0; i < this.errorMessage.length; i++){
			int w = fm.stringWidth(errorMessage[i]);
			g.drawString(errorMessage[i], (300-w)/2, (88-h)/2+(i+1)*24+i*5);
		}
	}
	
	public void setText(String[] s){
		this.errorMessage = s;
	}


	@Override
	public void run() {
		while(true){
			if (timeLeft > 0){
				try {Thread.sleep(1);} catch (InterruptedException e) {}
				timeLeft -=2;
			}
			else{
				this.p.hideErrorPanel();
			}
		}
	}
}
