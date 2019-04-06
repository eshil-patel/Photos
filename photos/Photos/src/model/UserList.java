package model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2546262621080163608L;
	private ArrayList<User> UL = new ArrayList<User>();
	public UserList(){
		UL = new ArrayList<User>();
	}
	public void addUser(User m){
		UL.add(m);
	}
	public String toString(){
		return UL.toString();
	}
	public ArrayList<User> getList() {
		return UL;
	}
	public boolean hasUser(String name) {
		User t = new User(name);
		if (UL.contains(t)) {
			return true;
		}
		return false;
	}
	public User getUser(String name) {
		User t= new User(name);
		return UL.get(UL.indexOf(t));
	}
	
}
