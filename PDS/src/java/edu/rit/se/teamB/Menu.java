package edu.rit.se.teamB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * @author teamB
 *
 */

public class Menu implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<Item> menu = new ArrayList<Item>();
	private List<String> topping = new ArrayList<String>();
	private double toppingprice = 1.0;
	private String[] saladTopping = {"Poppyseed", "Ranch", "Italian", "Balsamic Vinegar"};
	
	/**
	 * Default constructor
	 */
	public Menu() {
		loadmenu();
	}
	
	/**
	 * Prints out the menu
	 */
	public void printMenu() {
		for (int i = 0; i < menu.size(); i++) {
			System.out.print(i + ". ");
			System.out.println(menu.get(i).getItemName());
		}
	}
		
	/**
	 * Walkthrough of adding an item to the menu
	 */
	public void addmenuitem() {
		System.out.println("Please select a name for this new item");
		Scanner in = new Scanner(System.in);
		String itemname = in.nextLine();
		System.out.println("Please select the price for this new item");
		Scanner in2 = new Scanner(System.in);
		double itemprice = Double.parseDouble(in2.nextLine());
		System.out.println("Please select the preptime for this new item");
		Scanner in3 = new Scanner(System.in);
		int itempreptime = Integer.parseInt(in3.nextLine());
		System.out.println("Please select the cooktime for this new item");
		Scanner in4 = new Scanner(System.in);
		int itemcooktime = Integer.parseInt(in4.nextLine());
		System.out.println("Please select the ovenspace units for this new item");
		Scanner in5 = new Scanner(System.in);
		int itemunitspace = Integer.parseInt(in5.nextLine());
		//Item newitem = new Item(itemname,itemprice,itemunitspace,itempreptime,itemcooktime,0);
		//menu.add(newitem);
	}
	
	/**
	 * Easy item add to menu
	 * @param I the new item to add to the menu
	 */
	public void ezadditem(Item I) {
		menu.add(I);
	}
	
	/**
	 * Remove an item from the menu
	 * @param I the item to remove
	 */
	public void removemenuitem(Item I) {
		if (menu.contains(I)) {
			menu.remove(I);
		}
		//Maybe some error checking using boolean values with the client
	}
	
	/**
	 * Adds a topping to the list of toppings
	 * @param s the new topping 
	 */
	public void addtopping(String s) {
		topping.add(s);
	}
	
	/**
	 * Removes a topping from the list of toppings
	 * @param s the topping to remove
	 */
	public void removetopping(String s) {
		if (topping.contains(s)) {
			topping.remove(s);
		}
		//Maybe some error checking using boolean values with the client
	}
	
	/**
	 * Accessor for the list of items
	 * @return List of items
	 */
	public List<Item> getItemList()
	{
		return menu;
	}
	
	/**
	 * Gets the item at an index
	 * @param i the index
	 * @return the item at index i
	 */
	public Item getItem(int i)
	{
		return menu.get(i);
	}
	
	/**
	 * Gets the arraylist of toppings
	 * @return the arraylist of toppings
	 */
	public ArrayList<String> getToppings()
	{
		return (ArrayList<String>) topping;
	}
	
	/**
	 * accessor for topping price
	 * @return the topping price
	 */
	public double getToppingPrice()
	{
		return toppingprice;
	}
	
	/**
	 * returns salad toppings
	 * @return string array of toppings
	 */
	public String[] saladTopping()
	{
		return saladTopping;
		
	}
	
	/**
	 * Writes the current menu to a file
	 */
	public void writeMenu(){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("menu.dat")));
			
			oos.writeObject(menu);
			oos.writeObject(topping);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the menu from a file and loads it
	 */
	public void loadmenu(){
		try {
			File db = new File("menu.dat");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(db));
			if(db.exists() && db.canRead()){
				menu = (ArrayList<Item>)ois.readObject();
				topping = (ArrayList<String>)ois.readObject();
			}
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Menu Database not found, making new one...");
			Item SmallPizza = new Item("Small Pizza",8.00, 1,8,13,0, true);
			Item MediumPizza = new Item("Medium Pizza",11.00, 2,10,15,0, true);
			Item LargePizza = new Item("Large Pizza",16.00, 4,15,20,0, true);
			Item PizzaLogs = new Item("Pizza Logs",6.00, 1,0,10,0, false);
			Item TossedSalad = new Item("Tossed Salad",5.00, 0,5,0,0, true);
			menu.add(SmallPizza);
			menu.add(MediumPizza);
			menu.add(LargePizza);
			menu.add(PizzaLogs);
			menu.add(TossedSalad);
			topping.add("Mushrooms");
			topping.add("Onions");
			topping.add("Sausage");
			topping.add("Peppers");
			topping.add("Pepperoni");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
