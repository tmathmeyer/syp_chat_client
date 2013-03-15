package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import edu.wpi.tmathmeyer.protocol.Packet;
import edu.wpi.tmathmeyer.protocol.chat.CommandPacket;
import edu.wpi.tmathmeyer.protocol.chat.MessageGroupListPacket;
import edu.wpi.tmathmeyer.protocol.chat.MessagePacket;
import edu.wpi.tmathmeyer.protocol.chat.UsersPacket;



public class Chat extends MainPanel implements ActionListener{
	
	
	
	JPanel messagePanel = new JPanel();
	JPanel users = new JPanel();
	
	JTextPane messages = new JTextPane();
	Style style = messages.addStyle("I'm a Style", null);
	StyledDocument doc = messages.getStyledDocument();
	JScrollPane jsp = new JScrollPane(messages);
	
	JTextPane userNames = new JTextPane();
	Style usrStyle = userNames.addStyle("lol this is pointless", null);
	StyledDocument usernameDoc = userNames.getStyledDocument();
	JScrollPane jspnames = new JScrollPane(userNames);
	
	JButton send = new JButton(">");
	
	JTextField messageInput = new JTextField();
	
	ChatPanel cp;
	
	OnlineUsers or = new OnlineUsers();
	
	
	
	public Chat(ChatPanel cp){
		this.cp = cp;
		
		this.setLayout(null);
    	
    	messages.setEditable(false);
    	messagePanel.setLayout(new GridLayout(1,1));
    	messagePanel.add(jsp);
    	
    	userNames.setEditable(false);
    	
    	
    	this.messageInput.setOpaque(false);
    	
    	
    	this.messagePanel.setBounds(0, 0, 1000, 570);
    	this.add(this.messagePanel);
    	this.messageInput.setBounds(50,625,870,30);
    	this.add(this.messageInput);
    	this.send.setBounds(920,625,60,30);
    	this.jspnames.setBounds(1000, 0, 200, 700);
    	this.add(this.send);
    	this.add(this.jspnames);
    	
    	
    	send.addActionListener(this);
    	messageInput.addActionListener(this);  	
	}

	
	
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == send || ae.getSource() == messageInput){
			String mess = messageInput.getText();
			messageInput.setText("");
			try {
				if (mess.charAt(0) == '/'){
					System.out.println("LOL");
					this.cp.sendPacket(new CommandPacket(mess.substring(1).split(" ")[0], mess.contains(" ")?mess.substring(mess.indexOf(" ")):"", (byte) 0x00));
				}
				else
					this.cp.sendMessage(mess);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	public void processPacket(Packet p){
		if (p instanceof MessagePacket){
			MessagePacket mp = (MessagePacket) p;
			Color msgColr = new Color(Integer.parseInt(mp.getMessageHex(), 16)); 
			Color usrColr = new Color(Integer.parseInt(mp.getUserHex(), 16)); 
			try{
				StyleConstants.setForeground(style, Color.red.darker().darker());
				String print = "["+mp.getHour()+":"+mp.getMinute()+":"+mp.getSecond()+"]";
				this.doc.insertString(this.doc.getLength(), print, style);
				
				StyleConstants.setForeground(style, usrColr);
				print = "<"+mp.getUsername()+">  ";
				this.doc.insertString(this.doc.getLength(), print, style);
				
				StyleConstants.setForeground(style, msgColr);
				print = mp.getMessage()+"\n";
				this.doc.insertString(this.doc.getLength(), print, style);
			} catch(Exception e) {
				
			}
		}
		
		if (p instanceof UsersPacket){
			UsersPacket up = (UsersPacket) p;
			or.populateGroup(up.getGroupID(), up.getUsers());
			try {
				this.userNames.setText("");
				this.or.writeUsers(usrStyle, usernameDoc);
				this.revalidate();
			} catch (BadLocationException e) {}
		}
		
		if (p instanceof MessageGroupListPacket){
			MessageGroupListPacket mglp = (MessageGroupListPacket) p;
			byte[] groups = mglp.getGroups();
			String[] groupNames = mglp.getGroupNames();
			
			for(int i = 0; i < groups.length; i++){
				this.or.addGroup(groups[i], groupNames[i]);
			}
		}
		
	}
	
}
