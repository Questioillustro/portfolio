package edu.rit.se.teamB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

/**
 * Login class that is used to hold user information and methods of validating that information.
 * 
 * @author teamB
 *
 */
public class Login {

	//userList is a HashMap of users.
	private ArrayList<User> userList;
	private Map<Integer,String> returnCodeMap;
	
	/**
	 * Default Constructor
	 * 
	 */
	public Login() {
		userList = new ArrayList<User>();
		returnCodeMap = new HashMap<Integer, String>();
		
		returnCodeMap.put(new Integer(0), "User added.");
		returnCodeMap.put(new Integer(1), "Username already in use.");
		returnCodeMap.put(new Integer(2), "Login failed.");
		returnCodeMap.put(new Integer(3), "Login success.");
	}
	
	/**
	 * Add a user to the system.
	 * 
	 * @param username - the username
	 * @param password - user's password
	 * @param pLevel - permission level of the user
	 * @return returnCode
	 */
	public int addUser(String username, String password, boolean pLevel){
		
		Integer returnValue = 0;
		User nUser = new User(username, password, pLevel);
		
		// Check if user is already in the system
		if(userList.contains(nUser)){
			returnValue = 1;  
			return returnValue;  //User already exists
		}
		
		//We did not find the user so add them to the system
		userList.add(nUser);
		return returnValue;  //User successfully added
		
	}
	
	/**
	 * Add user to database (Depreciated, CLI)
	 * @param u User to add
	 * @return The results of adding customer
	 */
	public int addUser(User u){
		return addUser(u.getUsername(), u.getPassword(), u.getPermissionLevel());
	}
	
	/**
	 * Gets the list of users
	 * @return ArrayList of users
	 */
	public ArrayList<User> getUserList(){
		return userList;
	}
	
	/**
	 * Method used to check if a given user is in the system and if the login info they are using is correct.
	 * 
	 * @param loginName
	 * @return returnCode
	 */
	public User userLogin(String loginName, String password)
	{
		int returnValue = 0;
		User nUser = new User(loginName, password, true);
		
		// If the user is in the list and the password is correct, login
		if(userList.contains(nUser)){
			if(nUser.getPassword().equals(userList.get(userList.indexOf(nUser)).getPassword())){
				returnValue = userList.indexOf(nUser);
				return userList.get(returnValue); // Login Successful
			}
		}
		
		return null; // Login Failed
	}
	
	/**
	 * Logs in a user (DEPRECIATED, CLI)
	 * @param u The user to login as
	 * @return The result of trying to login
	 */
	public int userLogin(User u){
		if(userLogin(u.getUsername(), u.getPassword()) != null){
			return 2;
		}
		return 3;
	}
	
	/**
	 * Load the customer database from a local file
	 */
	@SuppressWarnings("unchecked")
	public void loadUserDB(){
		ObjectInputStream ois;
		
		try {
			File db = new File("users.dat");
			ois = new ObjectInputStream(new FileInputStream(db));
			if(db.exists() && db.canRead()){
				userList = (ArrayList<User>) ois.readObject();
			}
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("User Database not found, making default...");
			userList.add(new User("admin", "admin", true));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Get the string value of the given return code.
	 * 
	 * @param code
	 * @return value: String value of returnCode
	 */
	public String GetReturnCode(int code)
	{
		String returnValue = "";
		
		if(returnCodeMap.containsKey(code))
		{
			returnValue = returnCodeMap.get(code);
		}
		
		return returnValue;
	}
	
	/**
	 * Write the current list of users to a file
	 */
	public void writeUserList(){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("users.dat")));
			
			oos.writeObject(userList);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
