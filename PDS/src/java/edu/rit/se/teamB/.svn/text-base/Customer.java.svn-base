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

/**
 * @author teamB
 *
 */

public class Customer implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String phone;
	private String name;
	private String location;
	private String preferences;
	private ArrayList<Order> orderhistory;
	private static ArrayList<Customer> customers = new ArrayList<Customer>();
	
	/**
	 * Default constructor, sets phone to "0000000000"
	 */
	public Customer()
	{		
		phone = "0000000000";
	}
	
	/**
	 * Accessor for the customer list
	 * 
	 * @return An arraylist containing all customers
	 */
	public ArrayList<Customer> getList(){
		return customers;
	}
	
	/**
	 * Assignment for the customer list
	 * @param c the new list of customers to set the current one to.
	 */
	public void setList(ArrayList<Customer> c){
		customers = c;
	}
	
	/**
	 * Customer constructor
	 * @param p customer phone number
	 * @param n customer name
	 * @param l customer location
	 */
	public Customer(String p, String n, String l)
	{
		phone = p;
		name = n;
		location = l;
		preferences = "None";
		orderhistory = new ArrayList<Order>();
	
	}
	
	/**
	 * Accessor for the customer phone number
	 * @return customer phone number
	 */
	public String getPhone()
	{
		return phone;
	}
	
	/**
	 * Assignment method for customer phone number
	 * @param p the new phone number to set the customer's phone number to
	 */
	public void setPhone(String p){
		phone = p;
	}
	
	/**
	 * Accessor for customer's name
	 * @return customer's name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Assignment for customer's name
	 * @param n the new name to set the customer's name to
	 */
	public void setName(String n){
		name = n;
	}
	
	/**
	 * Accessor for customer's location
	 * @return String containing customer's location
	 */
	public String getLocation()
	{
		return location;
	}
	
	/**
	 * Assignment for customer's location
	 * @param l the new location
	 */
	public void setLocation(String l){
		location = l;
	}
	
	/**
	 * Accessor for getting customer's preferences
	 * @return String of customer preferences
	 */
	public String getPreferences()
	{
		return preferences;
	}
	
	/**
	 * Assignment for customer's preferences
	 * @param pf new preferences
	 */
	public void setPreference(String pf){
		preferences = pf;
	}
	
	/**
	 * Get customer's order history
	 * @return Arraylist of orders for the customer
	 */
	public ArrayList<Order> getHistory()
	{
		return orderhistory;
	}
	
	/**
	 * Adds an order to customer's order history
	 * @param o new order to add
	 */
	public void addToHistory(Order o){
		orderhistory.add(o);
	}
	
	/**
	 * Comparator function based on phone number
	 * @return whether this customer is the same as another customer
	 */
	public boolean equals(Object other){
		if(getPhone().equals(((Customer) other).getPhone())){
			return true;
		}
		return false;
	}
	
	/**
	 * Method that finds whether a customer is in the customer list or not
	 * @param p Phone number lookup
	 * @return Customer that has that phone number
	 */
	public static Customer lookUp(String p)
	{
		Customer tCust = null;
		
	    for(int i = 0; i < customers.size(); i++) {
	        if(customers.get(i).getPhone().equals(p)) 
	        {
	            tCust = customers.get(i);
	            break;
	        }
	    }
	    return tCust;
	}
	
	/**
	 * Prints out customer information including last order and preferences
	 */
	public void printInfo()
	{
		System.out.println("Phone: " + phone);
		System.out.println("Name: " + name);
		System.out.println("Location: " + location);
		System.out.println("Preferences: " + preferences);
		System.out.println("Last Order: ");
		/**
		 * if(!orderhistory.isEmpty())
		 
			orderhistory.get(0).printShort();
		else
			System.out.println("   No recent orders on file.");
		*/
	}

	/**
	 * Add a customer to the list of customers
	 * @param c the new customer to add
	 */
	public static void addCustomer(Customer c) {
		customers.add(c);
	}
	
	/**
	 * Writes the current list of customers to a file
	 */
	public void writeCustomers(){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("customers.dat")));
			
			oos.writeObject(new Customer().getList());
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
	 * Loads customers into the list of customers from the local file. If local file not found,
	 * then make a new, blank database.
	 */
	public void loadCustomers(){
		ArrayList<Customer> customers;
		try {
			File db = new File("customers.dat");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(db));
			if(db.exists() && db.canRead()){
				customers = (ArrayList<Customer>) ois.readObject();
				new Customer().setList(customers);
			}
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Customer Database not found, making new one...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
