package edu.rit.se.teamB;

import java.io.Serializable;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author teamB
 *
 */

public class Order implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private int numitems;
	private Customer customer;
	private ArrayList<Item> items;
	private double subtotal = 0.0;
	private double taxrate = 0.08;
	private double total = 0.0;
	private String status, location;
	@SuppressWarnings("unused")
	private int totalelapsed = 0;
	private int elapseddelivtime = 0;
	private int delivtime;
	private int waittime = 0;
	private int etd = 0;
	private boolean complete = false;
	
	/**
	 * Default constructor
	 */
	public Order()
	{
		items = new ArrayList<Item>();
		customer = new Customer();
	}//Order()
	
	/**
	 * Add an item to order
	 * @param i item to add
	 */
	public void addItem(Item i)
	{
		items.add(i);
		refreshTotal();
	}
	
	/**
	 * Returns order customer
	 * @return the customer associated with this order
	 */
	public Customer getCustomer(){
		return customer;
	}
	/**
	 * Removes an item from order
	 * @param i Index of item
	 */
	public void removeItem(int i){		
		items.remove(i);
		refreshTotal();
	}
	
	/**
	 * Reloads the total
	 */
	public void refreshTotal(){
		total = 0;
		subtotal = 0;
		for(int i = 0; i < items.size(); i++){
			subtotal += items.get(i).getPrice();
			total = subtotal + subtotal*taxrate;
		}
	}
	
	/**
	 * Accessor order size
	 * @return number of items in order
	 */
	public int getOrderSize(){
		return items.size();
	}
	
	/**
	 * Accessor for order items
	 * @return Arraylist of items
	 */
	public ArrayList<Item> getItems()
	{
		return items;
	}
	
	/**
	 * 
	 * @return Total with no tax
	 */
	public double getSubTotal()
	{
		return subtotal;
	}
	
	/**
	 * 
	 * @return Total with tax
	 */
	public double getTotal()
	{
		return total;
	}
	
	/**
	 * @return Calulated tax based on subtotal
	 */
	public double getTax()
	{
		return subtotal*taxrate;
	}
	
	/**
	 * Sets customer for this order
	 * @param c Customer to set
	 */
	public void setCustomer(Customer c)
	{
		customer = c;
		location = customer.getLocation();
	}
	
	/**
	 * The location this order will be delivered to
	 * @return String of location
	 */
	public String getlocation() 
	{
		return location;
	}
	
	/**
	 * Sets delivery order
	 * @param s The new location
	 */
	public void setlocation(String s) {
		location = s;
	}
	
	/**
	 * Current status of order
	 * @return String of status
	 */
	public String getStatus()
	{
		return status;
	}
	
	/**
	 * Sets status of order
	 * @param s String of new status
	 */
	public void setStatus(String s)
	{
		status = s;
	}
	
	/**
	 * Set number of items
	 * @param n number of items
	 */
	public void setNumItems(int n)
	{
		numitems = n;
	}
	
	/**
	 * Gets number of items
	 * @return number of items
	 */
	public int getNumItems()
	{
		return items.size();
	}
	
	/**
	 * DEPRECIATED, CLI
	 * @return
	 */
	public String printOrder()
	{
		String temp = "";
		DecimalFormat f = new DecimalFormat("##0.00");
		
		temp += "\nPhone:  " + customer.getPhone() + "\n";
		temp += "Name:     " + customer.getName() + "\n";
		temp += "Location: " + customer.getLocation() + "\n";
		temp += "Status :  " + status + "\n" ;
		temp += "Time Elapsed: " + totalelapsed/60 + ":" + totalelapsed%60 + "\n"; 
		temp += "ETD: " + etd + ":" + "00" + "\n\n";
		for(int i = 0; i < items.size(); i++)
		{
			temp += (items.get(i).printItem());
    	}
		 
		temp += ("\nSub Total:   $" + f.format(subtotal) + "\n");
		temp += ("Tax:         $" + f.format(subtotal*taxrate) + "\n");
		temp += ("Total:       $" + f.format(total)) + "\n";
		return temp;
	}
	
	/**
	 * Returns order id
	 * 
	 * @return order id
	 */
	public String getID()
	{
		return customer.getPhone();
	}
	
	/**
	 * Increments wait time
	 */
	public void incWaitTime() {
		waittime += 1;
	}
	
	/**
	 * Gets the current wait time
	 * @return wait time
	 */
	public int getWaitTime()
	{
		return waittime;
	}
	
	/**
	 * Set delivery time
	 * @param d delivery time
	 */
	public void setDelivTime(int d)
	{
		delivtime = d;
	}
	
	/**
	 * Set ETD
	 * @param e ETD
	 */
	public void setETD(int e)
	{
		etd = e;
	}
	
	/**
	 * Get ETD
	 * @return ETD
	 */
	public int getETD()
	{
		return etd;
	}
	
	/**
	 * delivery time accessor
	 * @return actual delivery time
	 */
	public int getDelivTime()
	{
		return delivtime;
	}
			
	/**
	 * Elapsed time so far for delivery
	 * @return elapsed delivery time
	 */
	public int getElapsedDelivTime() {
		return elapseddelivtime;
	}
	
	/**
	 * Increment elapsed delivery time
	 */
	public void incDelivTime() {
		elapseddelivtime ++;
	}
	
	/**
	 * Increment total time
	 */
	public void incTotalTime()
	{
		totalelapsed++;
	}
	
	/**
	 * Set complete
	 * @param t whether order is complete or not
	 */
	public void setComplete(boolean t)
	{
		complete = t;
	}
	
	/**
	 * returns whether it's complete
	 * @return if the order is complete
	 */
	public boolean getComplete()
	{
		return complete;
	}
	
	/**
	 * Get percent complete
	 * @return the percent complete the order is
	 */
	public int getCompletionPercent()
	{
		int per = 0;
		double preptotal = 0;
		double cooktotal = 0;
		double prepelapse = 0;
		double cookelapse = 0;
		
		for(int i = 0; i < items.size(); i++)
		{
			preptotal += items.get(i).getPrepTime();
			cooktotal += items.get(i).getCookTime();
			cookelapse += items.get(i).getElapsedCookTime();
			prepelapse += items.get(i).getElapsedPrepTime();
		}
		per = (int)((prepelapse + cookelapse)/(preptotal + cooktotal) * 100);
		return per;
	}
	
	/**
	 * Clear order
	 */
	public void clearAll()
	{
		items.clear();
		subtotal = 0;
		total = 0;
		numitems = 0;
		customer = null;
	}
}//Order{}