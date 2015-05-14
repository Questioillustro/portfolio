package edu.rit.se.teamB;

import junit.framework.TestCase;

public class LoginTest extends TestCase {
	
	public void testGetUserMethodFailure(){
		Login testLogin = new Login();
		User nUser = new User("dan","1234zq", 2);
		
		assertEquals("Verifying that user dan is not in the system", 0, testLogin.addUser(nUser.getUsername(), "1234zq", 2));
		
		
	}
	public void testAddUserWhereUserNotFound(){
		Login testLogin = new Login();
		User currentUser = new User("dan","1234zq", 2);
		int returnCode;
			
		
		returnCode = testLogin.addUser(currentUser);
		assertEquals("Verifying that user dan has been added to the system","User added.", testLogin.GetReturnCode(returnCode));
	}
	public void testUserAlreadyExists(){
		Login testLogin = new Login();
		User currentUser = new User("dan","1234zq", 2);
		int returnCode;
		
		
		testLogin.addUser(currentUser);
		returnCode = testLogin.addUser(currentUser);
		assertEquals("Verifies user does already exsist in database", "Username already in use.",testLogin.GetReturnCode(returnCode));
	
	}
	
	public void testLoginSuccessfully(){
		Login testLogin = new Login();
		User currentUser = new User("dan", "1234zq", 2);
		int returnCode;
		
		
		testLogin.addUser(currentUser);
		returnCode = testLogin.userLogin(currentUser);
		assertEquals("Verifies the user successfully loggged in", "Login success.", testLogin.GetReturnCode(returnCode));
		
		}
	
	public void testLoginUnsuccessfully(){
		Login testLogin = new Login();
		User currentUser = new User("dan", "1234zq", 2);
		int returnCode;
		
		returnCode = testLogin.userLogin(currentUser);
		assertEquals("Verifies the user successfully loggged in", "Login failed.", testLogin.GetReturnCode(returnCode));
		
	}

}
