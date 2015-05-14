package edu.rit.se.teamB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class MenuTest extends TestCase {
	
	public void testprintmenu() {
		Menu menu = new Menu();
		menu.printMenu();
	}
	
	public void testezadditem() {
		Menu menu = new Menu();
		Item i = new Item("Food",00.00,0,0,0,0);
		menu.ezadditem(i);
		menu.printMenu();
	}
	
	public void testremovemenuitem() {
		Menu menu = new Menu();
		Item i = new Item("Food",00.00,0,0,0,0);
		menu.ezadditem(i);
		menu.printMenu();
		menu.removemenuitem(i);
		menu.printMenu();
	}
	
	public void testaddtopping() {
		Menu menu = new Menu();
		int s = menu.getToppings().size();
		menu.addtopping("Xtra Chz");
		assertTrue(menu.getToppings().size() == s + 1);
	}
	
	public void testremovetopping(String s) {
		Menu menu = new Menu();
		int s2 = menu.getToppings().size();
		menu.removetopping("Peppers");
		assertTrue(menu.getToppings().size() == s2 -1);
	}
	
	public void testgetItemList()
	{
		Menu menu = new Menu();
		System.out.println(menu.getItemList().size());
	}
	
	public void testgetItem()
	{
		Menu menu = new Menu();
		System.out.println(menu.getItem(0).getItemName());
	}
	
	public void testgetToppings()
	{
		Menu menu = new Menu();
		System.out.println(menu.getToppings().size());
	}
	
	public void testgetToppingPrice()
	{
		Menu menu = new Menu();
		System.out.println(menu.getToppingPrice());
	}
	
	public void testsaladTopping()
	{
		Menu menu = new Menu();
		System.out.println(menu.saladTopping()[0]);
	}
	
}
