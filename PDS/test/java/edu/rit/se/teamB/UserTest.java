package edu.rit.se.teamB;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	public void testUserCreation(){
		
		User testCurrentUser = new User("dan", "1234zq", 2);
		
		assertEquals("Verifies username equals dan", "dan", testCurrentUser.getUsername());
		
		assertEquals("Verifies password equals 1234zq", "1234zq", testCurrentUser.getPassword());
		
		assertEquals("Verifies permission level equals 2", 2, testCurrentUser.getPermissionLevel());

		

		}
	
	

}
