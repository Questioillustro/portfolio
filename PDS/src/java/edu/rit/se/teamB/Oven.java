package edu.rit.se.teamB;

import java.util.ArrayList;

public class Oven
{
	private int capacity;
	private int remainingcapacity;
	private ArrayList<Item> cooking;
	
	/**
	 * Default constructor
	 * @param c Oven capacity
	 */
	public Oven(int c)
	{
		capacity = c;
		remainingcapacity = c;
		cooking = new ArrayList<Item>();
	}
	
	/**
	 * Set oven capacity
	 * @param c the new capacity
	 */
	public void setCapacity(int c)
	{
		capacity = c;
	}
	
	/**
	 * Get oven capacity
	 * @return the oven capacity
	 */
	public int getCapacity()
	{
		return capacity;
	}
	
	/**
	 * Remaining capacity
	 * @return Remaining capacity
	 */
	public int getRemainingCapacity()
	{
		return remainingcapacity;
	}
	
	/**
	 * Change remaining capacity
	 * @param c new remaining capacity
	 */
	public void changeRemainingCapacity(int c)
	{
		remainingcapacity += c;
	}
	
	/**
	 * Add item to oven
	 * @param i item to add
	 */
	public void addItem(Item i)
	{
		if(i.getCookTime() == 0)
		{
			i.setStatus("Finished");
		}
		if(i.getCookTime() > 0)
		{
			cooking.add(i);
			changeRemainingCapacity(-(i.getOvenSpaceUnits()));
		}
	}
	
	/**
	 * remove order from oven
	 * @param id Id of order to remove
	 */
	public void removeOrder(String id)
	{
		for(int i = 0; i < cooking.size(); i++)
		{
			if(cooking.get(i).getID().equals(id)) 
			{ 
				changeRemainingCapacity(cooking.get(i).getOvenSpaceUnits());
				cooking.remove(i);
			}
		}
	}


	/**
	 * Increment cooking time for orders in oven
	 */
	public void increment() {
		if(cooking.size() > 0)
		{
			for(int i = 0; i < cooking.size(); i++)
			{
				cooking.get(i).incrementCookTime();
				if(cooking.get(i).getElapsedCookTime() >= cooking.get(i).getCookTime())
				{
					cooking.get(i).setStatus("Finished");
					changeRemainingCapacity(cooking.get(i).getOvenSpaceUnits());
					if(cooking.size() == 1)
					{
						cooking.remove(0);
						break;
					}
					else
						cooking.remove(i);
				}//if
			}//for
		}//if
	}//increment()
}//Oven()