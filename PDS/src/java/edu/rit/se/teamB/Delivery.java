package edu.rit.se.teamB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author teamB
 *
 */

public class Delivery {

	private Order[] drivers;
	private ArrayList<Order> waiting;
	private Map<String,Integer> locations = new HashMap<String,Integer>();
	
	/**
	 * Default Constructor
	 * @param DriverNum The number of drivers available. 
	 */
	public Delivery(int DriverNum) {
		loadLocations();
		
		drivers = new Order[DriverNum];
		waiting = new ArrayList<Order>();
		
		for(int i = 0; i < drivers.length; i++)
		{
			drivers[i] = null;
		}
	}
	
	/**
	 * Prints out the status of all drivers
	 * 
	 */
	public String printStatus() {
		String temp = "";
		
		for (int i = 0; i < drivers.length; i++) {
			if (drivers[i] != null) {
				temp += ("Driver#" + i + " is delivering ---\n");
				temp += drivers[i].printOrder();
				if(!drivers[i].getComplete())
					temp += ("ETA Deliver:" + drivers[i].getElapsedDelivTime() + "/" + locations.get(drivers[i].getlocation().toLowerCase()) * 60 + "\n");
				temp += ("ETA Return: " + drivers[i].getElapsedDelivTime() + "/" + drivers[i].getDelivTime() + "\n");
				temp += "\n----------------------------------------------\n";
			}
			else {
				temp += ("Driver#" + i + " is ready to deliver\n");
			}
		}
		return temp;
	}
	
	/**
	 * Starts the delivery process for an order by changing it's status
	 * 
	 * @param o the order to be delivered
	 */
	public void deliver(Order o) {
		o.setStatus("Awaiting Delivery");
		o.setDelivTime(locations.get(o.getlocation()) * 120);
		waiting.add(o);
	}
	
	/**
	 * Increments the time taken since last check on delivery status, and if any
	 * drivers are available, they are assigned any orders ready to be delivered.
	 * 
	 */
	public void incrementAll() {
		if(waiting.size() > 0)
		{
			//assign orders to any available drivers
			for(int i = 0; i < drivers.length; i++)
			{
				if(drivers[i] == null && waiting.size() > 0)
				{
					drivers[i] = waiting.get(0);
					drivers[i].setStatus("In Transit");
					waiting.remove(0);
				}
			}
			//increment wait times for orders in q
			for(int i = 0; i < waiting.size(); i++)
			{
				waiting.get(i).incWaitTime();
			}
		}
		
		//increment deliver times for drivers on delivery
		for(int i = 0; i < drivers.length; i++)
		{
			if(drivers[i] != null)
			{
				drivers[i].incDelivTime();
				if(((drivers[i].getElapsedDelivTime() + 120) >= locations.get(drivers[i].getlocation()) * 60) && !drivers[i].getComplete())
				{
					drivers[i].setStatus("Delivered");
					drivers[i].setComplete(true);
				}
				if(drivers[i].getElapsedDelivTime() >= drivers[i].getDelivTime() && drivers[i].getComplete())
				{
					drivers[i] = null;
				}
			}
		}//for
	}
	
	
	/**
	 * A check to see if any drivers are available to do a delivery
	 * 
	 * @return boolean representing if any drivers are ready to do a delivery
	 * 
	 */
	public boolean driverAvailable()
	{
		for(int i = 0; i < drivers.length; i++)
		{
			if(drivers[i] == null)
				return true;
		}
		return false;
	}
	
	/**
	 * Returns the time for delivery for a location
	 * 
	 * @param l The location to check the time for
	 */
	public int getDeliveryTime(String l)
	{
		return locations.get(l) * 60;
	}
	
	
	/**
	 * Mutator that can remove a current location
	 * 
	 * @param s A string to remove from the list of locations
	 */
	public void removelocation(String s) {
		if (locations.containsKey(s.toLowerCase())) {
			locations.remove(s.toLowerCase());
		}
	}
	
	/**
	 * Easy add function for location that does not include prompting for information,
	 * instead location name and time of delivery are passed as function args
	 * 
	 * @param loc
	 * @param dTime
	 */
	public void ezaddlocation(String loc, int dTime){
		locations.put(loc.toLowerCase(), dTime);
	}
	
	/**
	 * Accessor for the locations Map
	 * 
	 * @return locations - a map contains all current locations.
	 * 
	 */
	public Map<String,Integer> getlocations() {
		return locations;
	}
	
	/**
	 * Accessor that returns the list of orders awaiting delivery.
	 * 
	 */
	public ArrayList<Order> getwaiting() {
		return waiting;
	}
	
	/**
	 * Sets num of drivers
	 * @param c new num of drivers
	 */
	public void setDrivers(int c) {
		if(c > drivers.length) {
			Order temp[] = new Order[c];
			for(int i = 0; i < temp.length; i++)
			{
				temp[i] = drivers[i];
			}
			drivers = temp;
		}
	}
	
	/**
	 * Writes the current list of locations to a file
	 */
	public void writeLocations(){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("locations.dat")));
			
			oos.writeObject(locations);
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
	 * Loads location into the list of locations from the local file. If local file not found,
	 * then make a new, default database.
	 */
	public void loadLocations(){
		ArrayList<Customer> customers;
		try {
			File db = new File("locations.dat");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(db));
			if(db.exists() && db.canRead()){
				locations = (Map<String, Integer>)ois.readObject();
			}
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Locations Database not found, making new one...");
			locations.put("RIT", 18);
			locations.put("University of Rochester", 12);
			locations.put("Nazareth College", 25);
			locations.put("St. John Fisher", 21);
			locations.put("Roberts Wesleyan College", 25);
			locations.put("Monroe Community College", 18);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}