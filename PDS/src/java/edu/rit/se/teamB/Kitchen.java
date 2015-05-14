package edu.rit.se.teamB;

import java.util.ArrayList;

public class Kitchen
{
	private ArrayList<Order> orders;
	private ArrayList<Item> prepwaiting;
	private ArrayList<Item> cookwaiting;
	private ArrayList<Item> cooked;
	private Item[] cooks;
	private Oven[] ovens;
	private int numovens, 
	            numcooks;
	private int ovencapacity = 40;
	
	/**
	 * Constructs kitchen with number of ovens and cooks
	 * @param o numOvens
	 * @param c numCooks
	 */
	public Kitchen(int o, int c)
	{
		numovens = o;
		numcooks = c;
		cooks = new Item[numcooks];
		ovens = new Oven[numovens];
		
		orders = new ArrayList<Order>();
		prepwaiting = new ArrayList<Item>();
		cookwaiting = new ArrayList<Item>();
		cooked = new ArrayList<Item>();
		for(int i = 0; i < cooks.length; i++)
		{
			cooks[i] = null;
		}
		
		for(int i = 0; i < ovens.length; i++)
		{
			ovens[i] = new Oven(ovencapacity);
		}
	}//Kitchen()
	
	/**
	 * Adds an order to the kitchen to be made
	 * @param o the order to add
	 */
	public void addOrder(Order o)
	{
		o.setStatus("In Kitchen");
		orders.add(o);
		for(int i = 0; i < o.getItems().size(); i++)
		{
			if(o.getItems().get(i).getPrepTime() == 0)
			{
				o.getItems().get(i).setStatus("In Cook Queue");
				cookwaiting.add(o.getItems().get(i));
			}
			if (o.getItems().get(i).getPrepTime() > 0)
			{
				o.getItems().get(i).setStatus("In Prep Queue");
				prepwaiting.add(o.getItems().get(i));
			}
		}
	}
	
	/**
	 * Calculates waitTime
	 * @return the calculated wait time
	 */
	public int calcWaitTime(ArrayList<Item> items)
	{
		int etd = 0;
		int availcooks = 0;
		int avgprep = 0;
		int avgcook = 0;
		int qwait = 0;
		
		for(int i = 0; i < items.size(); i++)
		{
			avgprep += items.get(i).getPrepTime();
			avgcook += items.get(i).getCookTime();
		}
		avgprep /= items.size();
		avgcook /= items.size();

		etd += avgcook;
		etd += avgprep;
		
		//Calculate wait time to be prepped
		for(int i = 0; i < prepwaiting.size(); i++)
		{
			qwait += prepwaiting.get(i).getPrepTime();
		}
		qwait = etd/numcooks;
		etd += qwait;
		
		//Calculate wait time to be cooked
		for(int i = 0;i < cookwaiting.size(); i++)
		{
			qwait += cookwaiting.get(i).getCookTime();
		}
		etd += qwait;
		return etd;
	}
	
	/**
	 * Goes through all items that are cooking and 
	 * increments their times and takes the next step if they 
	 * need
	 */
	public void incrementAll()
	{
		//Check for oven space and add next item
		if(cookwaiting.size() > 0)
		{
			if(ovenSpaceAvailable())
			{
				sendNextItemToOven();
			}//while
		}//if
		
		//check for available cooks and give them an item to prep
		if(prepwaiting.size() > 0)
		{
			if(cookIsAvailable())
			{
				sendNextItemToPrep();
			}
		}
		
		//increment prep times
		for(int i = 0; i < cooks.length; i++)
		{
			if(cooks[i] != null) 
			{ 
				cooks[i].incrementPrepTime(); 
				if(cooks[i].getElapsedPrepTime() >= cooks[i].getPrepTime())
				{ 
					cookwaiting.add(cooks[i]);
					cooks[i] = null;
				}//if
			} 
		}
		
		//increment cook times
		for(int i = 0; i < ovens.length; i++)
		{
			ovens[i].increment();
		}
		checkDone(); //Check for finished items after increment
	}//IncrementAll
	
	/**
	 * Sets num of cooks
	 * @param c new num of cooks
	 */
	public void setCooks(int c)
	{
		Item temp[] = new Item[c];
		if(c < numcooks)
		{
			for(int i = c; i < cooks.length; i++)
			{
				if(cooks[i] != null)
					prepwaiting.add(0, cooks[i]);
			}
		}
		
		for(int i = 0; i < cooks.length; i++)
		{
			temp[i] = cooks[i];
		}
		cooks = temp;
		numcooks = c;
	}
	
	/**
	 * Check for finished items in the oven and extracts them
	 */
	public void checkDone()
	{
		boolean verify = true;
		for(int i = 0; i < orders.size(); i++)
		{
			for(int j = 0; j < orders.get(i).getItems().size(); j++)
			{
				if(!orders.get(i).getItems().get(j).getStatus().equals("Finished"))
					verify = false;
			}
			if(verify)
			{
				orders.get(i).setStatus("Finished");
				orders.remove(i);
			}
		}
	}//checkdone()

	/**
	 * checks for next item to be prepped and moves it
	 */
	private void sendNextItemToPrep() 
	{
		for(int i = 0; i < cooks.length; i++)
		{
			if(cooks[i] == null) 
			{ 
				prepwaiting.get(0).setStatus("Being Prepared");
				cooks[i] = prepwaiting.get(0);
				prepwaiting.remove(0);
				break;
			}
		}
	}//sendNextItemToPrep()
	
	/**
	 * checks for next item to be cooked and moves it
	 */
	private void sendNextItemToOven()
	{
		for(int i = 0; i < ovens.length; i++)
		{
			if(cookwaiting.get(0).getOvenSpaceUnits() <= ovens[i].getRemainingCapacity())
			{
				cookwaiting.get(0).setStatus("In Oven");
				ovens[i].addItem(cookwaiting.get(0));
				cookwaiting.remove(0);
				break;
			}
		}
	}

	/**
	 * checks if a prep cook is available
	 * @return boolean representing whether a cook is available
	 */
	public boolean cookIsAvailable()
	{
		for(int i = 0; i < cooks.length; i++)
		{
			if(cooks[i] == null) { return true; }
		}
		
		return false;
	}
	
	/**
	 * Checks if the next item in cooking queue will fit in an oven
	 * @return boolean representing results
	 */
	public boolean ovenSpaceAvailable()
	{
		for(int i = 0; i < ovens.length; i++)
		{
			if(cookwaiting.get(0).getOvenSpaceUnits() <= ovens[i].getRemainingCapacity())
				return true;
		}//for
		return false;
	}//ovenspaceavailable
	
	/**
	 * Prints out status of kitchen
	 */
	public String printKitchen()
	{
		int availcooks = 0;
		for(int i = 0; i < cooks.length; i++)
		{
			if(cooks[i] == null)
				availcooks++;
		}
		
		String temp = "Cooks Avalailable: " + availcooks + "/" + cooks.length;
		temp += "   Oven Space Available: ";
		for(int i = 0; i < ovens.length; i++)
		{
			temp += "Oven " + (i+1) + ": " + ovens[i].getRemainingCapacity() + " | ";
		}
		
		temp += "\n----------------------------------------------\n";
		
		for(int i = 0; i < orders.size(); i++)
		{
			temp += orders.get(i).printOrder();
			temp += "\n----------------------------------------------\n";
		}
		return temp;
	}//printkitchen
	
	/**
	 * Cancels an order in the kitchen
	 * @param p the phone number of the order to cancel
	 */
	public void cancelOrder(String p)
	{
		//Remove the items from order 'o' from the prep waiting list
		for(int i = 0; i < prepwaiting.size(); i++)
		{
			if(prepwaiting.get(i).getID().equals(p)) { prepwaiting.remove(i); }
		}
		
		//Remove the items from order 'o' from the cook wait list
		for(int i = 0; i < cookwaiting.size(); i++)
		{
			if(cookwaiting.get(i).getID().equals(p)) { cookwaiting.remove(i); }
		}
		
		//Remove items from the prep cook station
		for(int i = 0; i < cooks.length; i++)
		{
			if(p.equals(cooks[i].getID())) { cooks[i] = null; }
		}
		
		//Remove cooking items from ovens
		for(int i = 0; i < ovens.length; i++)
		{
			ovens[i].removeOrder(p);
		}
	}
}//Kitchen{}

