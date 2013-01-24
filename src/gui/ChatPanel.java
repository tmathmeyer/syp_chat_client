package gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.JPanel;

import protocol.ControlPacket;
import protocol.Packet;

import client.Client;
import client.Reciever;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel implements Client, MouseListener, Runnable{
	
	boolean authenticated = false;
	boolean attemptingToAuthenticate = true;
	
	MainPanel mainPanel = new LoadingPane();
	OptionsPanel optionPanel = new LoginPanel(this);
	ErrorPanel errorPanel = new ErrorPanel(this);
	
	int secondsForError = 0;
	
	Socket s;
	
	DataOutputStream writer;
	
	String username;
	String hostname = "130.215.229.196";
	
	int port = 25566;
	
	
	
	public ChatPanel() throws IOException{
		this.addMouseListener(this);
		this.setPreferredSize(new Dimension(1200,700));
		this.setLayout(null);
		this.add(errorPanel);
		this.add(optionPanel);
		this.addMainPanel(this.mainPanel);
	}
	
	
	public void addMainPanel(MainPanel p){
		try{this.remove(mainPanel);}catch(Exception e){}
		this.mainPanel = p;
		this.mainPanel.setBounds(0,0,1200,700);
		this.add(mainPanel);
	}
	
	
	@Override
	public boolean isOptimizedDrawingEnabled(){
		return false;
	}
	
	
	
	public void showOptionsPane(){
		this.setOptionPanelSize(350, 0, 4);
	}
	
	
	
	public void hideOptionsPane(){
		this.setOptionPanelSize(0, 350, -4);
	}
	
	
	
	public void setOptionPanelSize(int target, double current, double direction){
		this.revalidate();
		long time = System.nanoTime();
		this.optionPanel.setBounds(0,0,(int) current,700);
		optionPanel.revalidate();
		while(System.nanoTime()-time < 500000)continue;
		if (Math.abs(current-target) > 1) this.setOptionPanelSize(target, current+direction, direction/1.01);
		
	}
	
	
	
	public void setMainPanelWaiting(boolean waiting){
		this.mainPanel.setWaiting(waiting);
	}


	
	public void connectToServer(String ip, int port){
		try{
			this.s = new Socket(ip, port);
			this.writer = new DataOutputStream(s.getOutputStream());
			new Thread(this).start();
			this.startReciever(new Reciever(this.getSocket(), this));
			System.out.println("connected to server");
		}
		catch(Exception e){
			this.showErrorPanel("Error: bad host 002");
		}
	}
	
	
	
	public String getUsername(){
		return this.username;
	}
	
	
	
	public String getHostName(){
		return this.hostname;
	}
	
	
	
	public int getPort(){
		return this.port;
	}
	
	
	
	public void setHostname(String s){
		this.hostname = s;
	}
	
	
	
	public void setPort(int i){
		this.port = i;
	}
	
	
	
	public void showErrorPanel(String[] ep){
		this.errorPanel.timeLeft = 10000;
		this.errorPanel.setText(ep);
		this.errorPanel.setBounds(625, 300, 300, 100);
		this.revalidate();
	}
	
	public void showErrorPanel(String ep){
		String[] e = {ep};
		this.showErrorPanel(e);
	}
	
	public void hideErrorPanel(){
		this.errorPanel.setBounds(0, 0, 0, 0);
		this.revalidate();
	}
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public void run() {
		
	}



	@Override
	public boolean authenticate(Packet p) throws Exception {
		if (!(p instanceof ControlPacket))return false;
		this.authenticated = ((ControlPacket)p).data == 0x01;
		
		
		if (this.authenticated){
			this.addMainPanel(new Chat(this));
			this.revalidate();
		}
		else{
			this.showOptionsPane();
			String[] e = {"Error: invalid","username or password"};
			this.showErrorPanel(e);
		}
		
		
		
		
		
		return this.authenticated;
	}



	@Override
	public void closeOutStream() throws Exception {
		this.writer.close();
	}



	@Override
	public DataOutputStream getByteOutputStream() {
		return this.writer;
	}



	@Override
	public Socket getSocket() {
		return this.s;
	}



	@Override
	public byte getVersionID() {
		return 0x0f;
	}



	@Override
	public void login(String username, String password) throws Exception {
		this.username = username;
		writer.writeByte(0x00);
		writer.writeShort(username.length());
		writer.writeShort(password.length());
		writer.writeChars(username);
		writer.writeChars(password);
		writer.flush();
	}
	
	public void register(String username, String password) throws Exception {
		this.username = username;
		writer.writeByte(0x20);
		writer.writeShort(username.length());
		writer.writeShort(password.length());
		writer.writeChars(username);
		writer.writeChars(password);
		writer.flush();
	}



	@Override
	public void processPacket(Packet p) throws Exception {
		if (p instanceof ControlPacket)this.authenticate(p);
		else this.mainPanel.processPacket(p);
	}



	@Override
	public void sendMessage(String message) throws Exception {
		Calendar c = Calendar.getInstance();
		short s = (short) c.get(Calendar.SECOND);
		short m = (short) c.get(Calendar.MINUTE);
		short h = (short) c.get(Calendar.HOUR);
		
		
		writer.writeByte(0x01);
		writer.writeByte(0x00);
		writer.writeShort(h);
		writer.writeShort(m);
		writer.writeShort(s);
		writer.writeShort(message.length());
		writer.flush();
		writer.writeChars(message);
		writer.flush();
		writer.writeShort(username.length());
		writer.writeChars(username);
		writer.flush();
	}



	@Override
	public void startReciever(Reciever r) {
		new Thread(r).start();
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
