package edu.rit.se.teamB;

import java.util.ArrayList;

/**
 * @author teamB
 *
 */

public class Pizza extends Item {

	private static final long serialVersionUID = 1L;
	private ArrayList<String> toppings;
	private double size;
	private int numtoppings = 0;
	
	public Pizza(Item i) 
	{	
		super(i);
		toppings = new ArrayList<String>();
		if(i.getItemName().equalsIgnoreCase("small")) { setSize(1.0); }
		if(i.getItemName().equalsIgnoreCase("medium")) { setSize(1.5); }
		if(i.getItemName().equalsIgnoreCase("large")) { setSize(2.0); }
	}
	
	public void addTopping(String t, String s) 
	{
		s = s.toUpperCase();
		price += size;
		toppings.add(t + "-" + s);
		numtoppings++;
	}
	
	public void removeTopping(String t) 
	{
		for(int i = 0; i < toppings.size(); i++)
		{
			if(toppings.get(i).equals(t))
			{
				toppings.remove(i);
				numtoppings--;
				price -= size;
				break;
			}
		}
	}
	

	public ArrayList<String> getToppings() 
	{
		return toppings;
	}

	public double getSize() 
	{
		return size;
	}

	public void setSize(double s) {
		size = s;
	}
	
	public int getNumToppings()
	{
		return numtoppings;
	}
	
	public void printToppings()
	{
		for(int i = 0; i < toppings.size(); i++)
		{
			System.out.println("\t" + toppings.get(i));
		}
	}
}
