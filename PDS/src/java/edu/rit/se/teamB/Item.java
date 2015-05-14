package edu.rit.se.teamB;

import java.io.*;
import java.util.ArrayList;

/**
 * @author teamB
 *
 */

public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	private String orderid;
	private String status;
	private String itemName;
	protected double price;
	private int ovenSpaceUnits;
	private int prepTime;
	private int cookTime;
	private int waitTime;
	private int elapsedPrepTime;
	private int elapsedCookTime;
	private int elapsedWaitTime;
	private boolean sizeable, toppable;
	private ArrayList<String> modifications = new ArrayList<String>();
	private double size;
	private int pepCount = 1;
	private int chzCount = 1;
	
	/**
	 * Default constructor
	 */
	public Item() {
		itemName = "";
		price = 0;
		ovenSpaceUnits = 0;
		prepTime = 0;
		cookTime = 0;
		waitTime = 0;
		elapsedPrepTime = 0;
		elapsedCookTime = 0;
		elapsedWaitTime = 0;	
		toppable = false;
			
	}
	/**
	 * Copy constructor
	 * @param i The item to copy
	 */
	public Item(Item i) {
		itemName = i.getItemName();
		price = i.getPrice();
		ovenSpaceUnits = i.getOvenSpaceUnits();
		prepTime = i.getPrepTime();
		cookTime = i.getCookTime();
		waitTime = i.getWaitTime();
		modifications = i.getMod();
		size = i.getSize();
		sizeable = i.isSizeable();
		elapsedPrepTime = 0;
		elapsedCookTime = 0;
		elapsedWaitTime = 0;
		toppable = i.toppable;
			
	}
	
	/**
	 * Manual constructor
	 * @param itemName name of item
	 * @param price price of item
	 * @param ovenSpaceUnits how much space the item takes up in an oven
	 * @param prepTime time required for prep
	 * @param cookTime time required for cooking
	 * @param waitTime how long the food is good for
	 */
	public Item(String itemName,double price, int ovenSpaceUnits, int prepTime, int cookTime, int waitTime, boolean toppable) {
		this.itemName = itemName;
		this.price = price;
		this.ovenSpaceUnits = ovenSpaceUnits;
		this.prepTime = prepTime * 60;
		this.cookTime = cookTime * 60;
		this.waitTime = waitTime;
		elapsedPrepTime = 0;
		elapsedCookTime = 0;
		elapsedWaitTime = 0;
		if(itemName.contains(" Pizza"))
			sizeable = true;
		else
			sizeable = false;
		
		if(sizeable)
		{
			if(itemName.contains("Small"))
				size = 1.0;
			else if(itemName.contains("Medium"))
				size = 1.5;
			else if(itemName.contains("Large"))
				size = 2.0;
		}
		this.toppable = toppable;
	}
	
	/**
	 * Returns true if item is can have different sizes
	 * @return if item can have different sizes
	 */
	public boolean isSizeable()
	{
		return sizeable;
	}
	
	/**
	 * Adds a modification to the item
	 * @param m The modification to add
	 */
	public void addMod(String m)
	{
		modifications.add(m);
		if(m.contains("Pepperoni") && pepCount == 1)
		{
			pepCount = 0;
		}
		else if(m.contains("Cheese") && chzCount == 1)
		{
			chzCount = 0;
		}
		else
			price += size * 1.0;
	}
	
	/**
	 * Removes a modification from the string
	 * @param i remove modification
	 */
	public void removeMod(int i)
	{
		if(modifications.get(i).contains("Pepperoni") && pepCount == 0)
		{
			pepCount = 1;
		}
		else if(modifications.get(i).contains("Cheese") && chzCount == 0)
		{
			chzCount = 1;
		}
		else
			price -= size * 1.0;
		
		modifications.remove(i);
	}
	
	/**
	 * Returns item size
	 * @return the size of this item
	 */
	public double getSize()
	{
		return size;
	}
	
	/**
	 * Returns modifications list
	 * @return the modifications on this item
	 */
	public ArrayList<String> getMod()
	{
		return modifications;
	}

	/**
	 * Accessor for item status
	 * @return status as a String
	 */
	public String getStatus()
	{
		return status;
	}
	
	/**
	 * Assignment for item status
	 * @param s new status of item
	 */
	public void setStatus(String s)
	{
		status = s;
	}
	
	/**
	 * Accessor for getting item name
	 * @return the item's name 
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Assignment for item name
	 * @param itemName the new name for the item
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Accessor for item price
	 * @return the item's price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Assignment for item price
	 * @param p the new price of the item
	 */
	public void setPrice(double p) {
		price = p;
	}

	/**
	 * Accessor for getting item size in oven
	 * @return the item's size
	 */
	public int getOvenSpaceUnits() {
		return ovenSpaceUnits;
	}

	/**
	 * Assignment for item size
	 * @param ovenSpaceUnits the new item size
	 */
	public void setOvenSpaceUnits(int ovenSpaceUnits) {
		this.ovenSpaceUnits = ovenSpaceUnits;
	}

	/**
	 * Accessor for item prep time
	 * @return item's required prep time
	 */
	public int getPrepTime() {
		return prepTime;
	}

	/**
	 * Assignment for item prep time
	 * @param prepTime the new required prep time
	 */
	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	/**
	 * Accessor for item cook time
	 * @return item's cook time
	 */
	public int getCookTime() {
		return cookTime;
	}

	/**
	 * Assignment for item cook time
	 * @param cookTime the new required cook time
	 */
	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	/**
	 * Accessor for item wait time
	 * @return the item's max wait time
	 */
	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * Assignment for item wait time
	 * @param waitTime the new required wait time
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * Accessor for item's elapsed preptime
	 * @return the item's elapsed preptime
	 */
	public int getElapsedPrepTime() {
		return elapsedPrepTime;
	}

	/**
	 * Accessor for item's elapsed cook time
	 * @return the item's elapsed cook time
	 */
	public int getElapsedCookTime() {
		return elapsedCookTime;
	}

	/**
	 * Accessor for item's elapsed wait time
	 * @return the item's elapsed waittime
	 */
	public int getElapsedWaitTime() {
		return elapsedWaitTime;
	}
	
	/**
	 * Evaluates whether the item is done cooking
	 * @return boolean representing done cooking or not.
	 */
	public boolean isCooked() {
		if (cookTime <= elapsedCookTime);
		return true;
	}
	
	/**
	 * Evaluates whether the item is done being prepped
	 * @return boolean representing done being prepped or not.
	 */
	public boolean isPrepped()	{
		if (prepTime <= elapsedPrepTime);
		return true;
	}
	
	/**
	 * Evaluates whether the item cannot wait anymore
	 * @return boolean representing if the item is expired
	 */
	public boolean hasWaitedTooLong()	{
		if (waitTime <= elapsedWaitTime);
		return true;
	}
	
	/**
	 * Adds one time unit to elapsed cook time
	 */
	public void incrementCookTime()	{
		elapsedCookTime ++;
		
	}
	
	/**
	 * Adds one time unit to elapsed prep time
	 */
	public void incrementPrepTime()	{
		elapsedPrepTime ++;
	}
	
	/**
	 * Adds one time unit to elapsed wait time
	 */
	public void incrementWaitTime()	{
		elapsedWaitTime ++;
	}
	
	/**
	 * Assignment for item ID
	 * @param i the new ID of the item
	 */
	public void setID(String i)
	{
		orderid = i;
	}
	
	/**
	 * Accessor for item ID
	 * @return the item's ID
	 */
	public String getID()
	{
		return orderid;
	}
	
	/**
	 * Checks whether the item can have toppings
	 */
	public boolean isToppable(){
		return toppable;
	}
	
	/**
	 * Prints item's information
	 */
	public String printItem()
	{
		String temp = "";
		temp += itemName + ":  ";
		temp += status + "\n";
		temp += "Time Remaining: ";
		if(elapsedPrepTime > 0 && !(elapsedPrepTime >= prepTime))
			temp += (prepTime - elapsedPrepTime)/60 + ":" + (prepTime - elapsedPrepTime)%60 + "\n\n";
		else if(elapsedCookTime > 0 && !(elapsedCookTime >= cookTime))
			temp += (cookTime - elapsedCookTime)/60 + ":" + (cookTime - elapsedCookTime)%60+ "\n\n";
		else
			temp += "\n\n";
		return temp;
	}
	
	/**
	 * Gets the modifications of this item
	 * @return An ArrayList of modifications
	 */
	public ArrayList<String> getModifications()
	{
		return modifications;
	}
                     
}
