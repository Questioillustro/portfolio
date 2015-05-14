	package edu.rit.se.teamB;

import java.io.Serializable;

/**
 * User class that holds login and permission information for a particular user.
 * 
 * @author teamB
 *
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	//private int permissionLevel;
	private boolean manager;
	
	/**
	 * Default constructor
	 * 
	 */
	public User() {
		username = "";
		password = "";
		manager = false;
	}
	
	/**
	 * Constructor
	 * 
	 * @param username
	 * @param password
	 * @param permissionLevel
	 */
	public User(String username, String password, boolean permissionLevel)
	{
		this.username = username;
		this.password = password;
		manager = permissionLevel;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getPermissionLevel() {
		return manager;
	}

	public void setPermissionLevel(boolean permissionLevel) {
		manager = permissionLevel;
	}
	
	public String toString(){
		return username;
	}
	
	public boolean equals(Object other){
		if(((User)other).getUsername().equals(getUsername())){ return true; }
		return false;
	}
	

}
