package edu.rit.se.teamB;

import junit.framework.TestCase;

public class ItemTest extends TestCase {
	
	public void testIsPrepped()	{
		Item testItem = new Item("Small Pizza",8.00, 1, 8, 13, 10);	
		testItem.incrementPrepTime();
		
		assertEquals("Verifies prep time stepper functionality", 1, testItem.getElapsedPrepTime() );
	
	}

	public void testIsCooked()	{
		Item testItem = new Item("Small Pizza",8.00, 1, 8, 13, 10);	
		testItem.incrementCookTime();
		
		assertEquals("Verifies cook time stepper functionality", 1, testItem.getElapsedCookTime() );
	
	}
	
	public void testHasWaitedTooLong()	{
		Item testItem = new Item("Small Pizza",8.00, 1, 8, 13, 10);	
		testItem.incrementWaitTime();
		
		assertEquals("Verifies wait time stepper functionality", 1, testItem.getElapsedWaitTime() );
		
	}
	
	public void testItemIsPrepped()	{
		Item testItem = new Item("Small Pizza",8.00, 1, 1, 13, 10);	
		testItem.isPrepped();
		
		assertEquals("Verifies prep time", 1, testItem.getPrepTime() );
		
	}
	
	public void testItemIsCooked()	{
		Item testItem = new Item("Small Pizza",8.00, 1, 8, 1, 10);	
		testItem.isCooked();
		
		assertEquals("Verifies cook time", 1, testItem.getCookTime() );
		
	}
	
	public void testItemHasWaitedTooLong()	{
		Item testItem = new Item("Small Pizza",8.00, 1, 8, 13, 1);	
		testItem.hasWaitedTooLong();
		
		assertEquals("Verifies wait time", 1, testItem.getWaitTime() );
		
	}
}
	
