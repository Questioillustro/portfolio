package edu.rit.se.teamB;


import junit.framework.TestCase;

public class DeliveryTest extends TestCase {
	
	public void testremovenewlocation() {
		Delivery deli = new Delivery(1);
		deli.ezaddlocation("Home", 9000);
		deli.removelocation("Home");
		assertFalse(deli.getlocations().containsKey("location"));
	}
	
	public void testezaddlocation() {
		Delivery deli = new Delivery(1);
		deli.ezaddlocation("Home", 9000);
		assertTrue(deli.getlocations().containsKey("Home"));
	}
	
	public void testprintstatus() {
		Delivery deli = new Delivery(4);
		deli.printStatus();
	}
	
	public void testdeliver() {
		Delivery deli = new Delivery(2);
		Order o = new Order();
		o.setlocation("RIT");
		deli.deliver(o);
		assertTrue(deli.getwaiting().size() == 1);
	}
	
}
	

