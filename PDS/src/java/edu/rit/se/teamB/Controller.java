package edu.rit.se.teamB;

import java.util.*;

public class Controller
{
	private Kitchen kitchen;
	private Delivery delivery;
	private ArrayList<Order> orders;
	private ArrayList<Order> completed;
	private Timer timer;
	private TimerTask timerTask;
	private double timeRatio;
	private ArrayList<String> locations;
	private GUI_Controller parent;
	private Menu menu;
	
	/**
	 * Default constructor
	 * 
	 * @param cooks num of cooks
	 * @param drivers num of delivery drivers
	 */
	public Controller(int cooks, int drivers, GUI_Controller c)
	{
		menu = new Menu();
		kitchen = new Kitchen(2, cooks);
		delivery = new Delivery(drivers);
		orders = new ArrayList<Order>();
		completed = new ArrayList<Order>();
		parent = c;
		
		timeRatio = 1.0;
		timer = new Timer();
		timerTask = new CountTask();
		timer.scheduleAtFixedRate(timerTask, (long)0, (long)(1000 * timeRatio));
	}
	
	/**
	 * Inner class of type TimerTask, executes the commands in run() on a regular interval
	 *
	 */
	class CountTask extends TimerTask {
	    public void run() {
	    	kitchen.incrementAll();
	    	checkDone(); //Looks for completed items and re-adds them to the order
	    	delivery.incrementAll();
	    	checkDelivered(); //Looks for delivered orders and adds them to the completed list
	    	incrementTotalTimes();
			
	    	//Output order details to simulation windows
	    	parent.reportOrderStatus(orders);
	    }
	}//CountTask

	/**
	 * Returns the menu
	 * @return Menu
	 */
	public Menu getMenu()
	{
		return menu;
	}
	
	/**
	 * Calls the method to write the locations to a file
	 */
	public void writeLocationList(){
		delivery.writeLocations();
	}
	
	public void writeMenu() {
		menu.writeMenu();
	}
	
    //Adds successfully delivered orders to the daily completion list
    private void checkDelivered() 
    {
    	for(int i = 0; i < orders.size(); i++)
    	{
    		if(orders.get(i).getComplete())
    		{
    			orders.get(i).setStatus("Completed");
    		}
    	}
	}//checkdelivered();
	
	/**
	 * Increments the total time for active orders
	 */
	private void incrementTotalTimes()
	{
		for(int i = 0; i < orders.size(); i++)
		{
			if(!orders.get(i).getStatus().equals("Completed"))
				orders.get(i).incTotalTime();
		}
	}
	
	//Reassmebles the order with completed items
	private void checkDone() 
	{
		for(int i = 0; i < orders.size(); i++)
		{
				if(orders.get(i).getStatus().equals("Finished"))
					sendOrderToDelivery(orders.get(i));
		}

	}//checkdone();
	
	/**
	 * Changes the simulation speed using r as a ratio multiplier for 1 second
	 * @param r Ratio of sim time to real time
	 */
	public void setTimeRatio(double r)
	{
		timeRatio = r;
		timer.cancel();
		timer = new Timer();
		timerTask = new CountTask();
		timer.scheduleAtFixedRate(timerTask, (long)0, (long)(1000 * timeRatio));
	}
	

	/**
	 * Returns a list of locations
	 * @return Arraylist of locations
	 */
	public ArrayList<String> getLocations(){
		ArrayList<String> list = new ArrayList<String>(delivery.getlocations().keySet());
		return list;
	}
	
	/**
	 * Returns a map of locations and time of delivery to locations
	 * @return The map of locations and times of delivery
	 */
	public Map<String, Integer> getLocTime(){
		return delivery.getlocations();
	}
	
	/**
	 * Passes int to kitchen to set the number of cooks
	 * @param c The number of cooks
	 */
	public void setCooks(int c)
	{
		kitchen.setCooks(c);
	}
	
	/**
	 * Gets etd from kitchen
	 * @return The estimated time of delivery
	 */
	public int getETD(String l, ArrayList<Item> items)
	{
		return (kitchen.calcWaitTime(items) + delivery.getDeliveryTime(l));
	}
	
	/**
	 * Sends an order to the kitchen
	 * @param o The order to send to the kitchen
	 */
	public void sendOrderToKitchen(Order o)
	{
		orders.add(o);
		kitchen.addOrder(o);
		o.setStatus("In Kitchen");
	}
	
	/**
	 * Sends an order to delivery
	 * @param o The order to send to delivery
	 */
	public void sendOrderToDelivery(Order o)
	{
		delivery.deliver(o);
	}
	
	/**
	 * Cancels an active order
	 * @param phone The customer's phone numnber
	 */
	public void cancelOrder(String phone) {
		if(isOrder(phone)){
			orders.remove(getOrder(phone));
			kitchen.cancelOrder(phone);
		}
			
		else
			System.out.println("No current active orders matches that phone number.");
		
	}

	/**
	 * Checks to see if an order exists
	 * @param phone the order's customer's phone number 
	 * @return Whether the order exists or not
	 */
	private boolean isOrder(String phone) {
		for(int i = 0; i < orders.size(); i++)
		{
			if(orders.get(i).getID().equals(phone))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns an order looked up based on customer's phone number
	 * @param phone The phone number to lookup by
	 * @return Null if nothing found, or the order if found.
	 */
	public Order getOrder(String phone) {
		for(int i = 0; i < orders.size(); i++)
		{
			if(orders.get(i).getID().equals(phone))
				return orders.get(i);
		}
		return null;
	}
	
}