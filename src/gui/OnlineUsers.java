package gui;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class OnlineUsers {
	HashMap<Byte, OnlineRoom> rooms = new HashMap<Byte, OnlineRoom>();
	
	
	public OnlineUsers(){
		
	}
	
	public void addGroup(byte b, String name){
		if (this.rooms.get(b) == null)
			this.rooms.put(b, new OnlineRoom(name));
	}
	
	public void populateGroup(byte b, String[] users){
		if (this.rooms.get(b) == null) return;
		OnlineRoom or = this.rooms.get(b);
		or.setUsers(users);
		this.rooms.put(b, or);
	}
	
	public void writeUsers(Style s, StyledDocument sd) throws BadLocationException{
		for(OnlineRoom or : rooms.values()){
			StyleConstants.setForeground(s, Color.GREEN.darker().darker());
			String print = or.name+"\n";
			sd.insertString(sd.getLength(), print, s);
			if (or.isPopulated()){
				for(String user : or.users){
					StyleConstants.setForeground(s, Color.BLACK);
					String name = "     "+user+"\n";
					sd.insertString(sd.getLength(), name, s);
				}
				or.setUsers(null);
			}
		}
		
		
	}
}
