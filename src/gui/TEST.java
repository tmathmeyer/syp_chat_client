package gui;

import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JFrame;

public class TEST {
	public static void main(String[] args) throws IOException, InterruptedException{
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new GridLayout(1,1));
		
		
		ChatPanel cf = new ChatPanel();
		f.add(cf);
		
		f.setResizable(false);
		f.pack();
		f.setVisible(true);
		
		Thread.sleep(1000);
		cf.showOptionsPane();
	}
}
