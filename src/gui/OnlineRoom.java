package gui;

public class OnlineRoom {
	String name = null;
	String[] users;
	
	
	public OnlineRoom(String name){
		this.setName(name);
	}
	
	public boolean isNamed(){
		return this.name!=null;
	}
	
	public boolean isPopulated(){
		return this.users != null;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	
	public void setUsers(String[] u){
		this.users = u;
	}
	
}
