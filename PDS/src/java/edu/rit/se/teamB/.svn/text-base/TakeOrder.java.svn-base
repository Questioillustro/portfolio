package edu.rit.se.teamB;

import java.util.*;

/*
 * The TakeOrder class is launched from the CLI/GUI to take the user through the process of taking an order.
 * Sequence:
 * 1. Customer is looked up
 * 	    	If a customer is not found a new one is created
 * 2. The Customer object is inserted into the Order object
 * 3. User is prompted with menu options to add to the order
 * 4. Item objects are selected, modified with a submenu, and then
 * 5. Item is added to Order object
 * 6. Once Order object is complete it is returned to the CLI/GUI to be placed into the Kitchen object
 * 
 */

public class TakeOrder
{
	private Scanner key_in = new Scanner(System.in);
	private Order order;
	private Customer customer = null;
	private Menu menu;
	private String command;
	
	public TakeOrder()
	{
		command = "";
		menu = new Menu();
		order = new Order();

	}//TakeOrder()
	
	public Order editOrder(Order o)
	{
		customer = o.getCustomer();
		order = o;
		
		System.out.println("*** Taking/Editing order ***");
		System.out.println("Enter '?' for order taking commands");
		command = "";
		
		while(!command.equals("done"))
		{
			command = "";
			System.out.print(">> ");
			command = key_in.next().toLowerCase();
			command.toLowerCase();
			if(command.equals("?")) { printCmdList(); }
			if(command.equals("getcustomer")) { getCustomer(); }
			if(command.equals("additem")) { getNextItem(); }
			if(command.equals("removeitem")) { removeItem(); }
			if(command.equals("edititem")) { editItem(); }
			if(command.equals("showorder")) { order.printOrder(); }
			if(command.equals("done")) { 
				if(!finalizeOrder())
					command = "";
				}
			if(command.equals("cancel")) { break; }
			
		}//While()
		if(command.equals("cancel"))
			return null;
		else
			return order;
	}
	
	private boolean finalizeOrder() {
		//Finalize order
		
		if(customer == null){
			System.out.println("Customer not yet set, please set customer " +
					"before finalizing order.");
			return false;
		}
			else{
				Order o = order;
				customer.addToHistory(o);
				
				for(int i = 0; i < order.getItems().size(); i++)
				{
					order.getItems().get(i).setID(order.getID());
				}
				System.out.println("Order has been completed and is now " +
						"awaiting preparation in the kitchen.");
		}
		return true;
	}

	private void editItem() 
	{
		order.printOrder();
		System.out.print("Item number to edit (-1 to cancel): ");
		int choice = key_in.nextInt();
		if(choice != -1)
			if(choice < order.getOrderSize() && choice > -1)
				if(order.getItems().get(choice).getItemName().contains(" Pizza")){
					Pizza p = (Pizza) order.getItems().get(choice);
					command = "";
					ArrayList<String> toppings = menu.getToppings(); //Import toppings
					
					while(!command.equals("c"))
					{
						System.out.print("(A)dd or (R)emove a topping or (C)ancel? (A/R/C): ");
						command = key_in.next().toLowerCase();
					
						if(command.equals("a"))
						{
							//Print toppings
							for(int i = 0; i < toppings.size(); i++)
							{
								System.out.println(i + ". " + toppings.get(i));
							}
							//Acquire desired topping and side from user
							int topper = -1;
							while(topper < 0 || topper > toppings.size()){
								System.out.print("Add topping (number): ");
								topper = key_in.nextInt();
							}
							
							String side = "A";
							while(!side.equals("L") && !side.equals("R") && !side.equals("W")){
								System.out.print("Add to...Left-side(L), Right-side(R), Whole(W): ");
								side = key_in.next();
							}
							
						
							//Add topping to pizza
							p.addTopping((String)toppings.get(topper), side);
						}//if()
						else if(command.equals("r")){
							for(int i = 0; i < p.getToppings().size(); i++){
								System.out.println(i + ". " + p.getToppings().get(i));
							}
							int topper = -1;
							while(topper < 0 || topper > p.getToppings().size()){
								System.out.print("Remove topping (number): ");
									topper = key_in.nextInt();
								}
								
								p.removeTopping(p.getToppings().get(topper));
								order.getItems().set(choice, p);				
						}
						p = (Pizza) order.getItems().get(choice);
						order.refreshTotal();
					}//while()
					
						
				}
				else{
					System.out.println(order.getItems().get(choice).getItemName() +
							" is not editable.");
				}
			else{
				System.out.println("Item #" + choice + " does not exist.");
			}
		else
			System.out.println("Item modification cancelled.");
	
		
	}

	private void removeItem() 
	{
		order.printOrder();
		System.out.print("Item number to delete (-1 to cancel): ");
		int choice = key_in.nextInt();
		if(choice != -1)
			if(choice < order.getOrderSize() && choice > -1)
			order.removeItem(choice);
			else{
				System.out.println("Item #" + choice + " does not exist.");
			}
		else
			System.out.println("Deletion cancelled.");
	}

	private void printCmdList() 
	{
		System.out.println("getcustomer : retrieves/creates customer for the order (execute a second time to change the customer)");
		System.out.println("additem     : opens menu to add an item");
		System.out.println("removeitem  : opens order items and allows for deletion of an item");
		System.out.println("edititem    : opens order items and allows for editing an item");
		System.out.println("showorder   : displays order information");
		System.out.println("done        : completes the order and sends it to the kitchen");
		System.out.println("cancel      : cancels the order progress and goes to main menu");
	}

	//Looks up customer and inserts it into the Order object
	public void getCustomer()
	{
		String phone = "";
		System.out.print("Enter Customer phone number: ");
		phone = key_in.next();
		
		if((customer = Customer.lookUp(phone)) == null)
		{
			System.out.println("Customer not found, please enter information for new customer");
			System.out.print("Enter customer name: ");
			String name = key_in.next();
			System.out.print("Enter customer location: ");
			String location = key_in.next();	
			
			customer = new Customer(phone, name, location);
			customer.addCustomer(customer);
			System.out.println("New customer created!");
			customer.printInfo();
		}
		else
		{
			System.out.println("Customer found!");
			customer.printInfo();
		}
		order.setCustomer(customer);
	}//getCustomer()
	
	public void getNextItem()
	{
		menu.printMenu();		
		System.out.print("Add item: ");
		int choice = key_in.nextInt();
		
		//If the item chosen is a pizza, call the add toppings submenu
		if(choice <= 2)
		{
			getPizza(choice);
		}
		else
		{
			order.addItem(menu.getItem(choice));
		}
	}
	
	public Order getOrder()
	{
		return order;
	}
	
	public void getPizza(int choice)
	{
		command = "";
		Item t = menu.getItem(choice);
		Pizza p = new Pizza(t);
		ArrayList<String> toppings = menu.getToppings(); //Import toppings
		
		while(!command.equalsIgnoreCase("n"))
		{
			System.out.print("Add a topping? (y/n): ");
			command = key_in.next();
		
			if(command.equalsIgnoreCase("y"))
			{
				//Print toppings
				for(int i = 0; i < toppings.size(); i++)
				{
					System.out.println(i + ". " + toppings.get(i));
				}
				//Acquire desired topping and side from user
				System.out.print("Add topping (number): ");
				int topper = key_in.nextInt();
				
				String side = "A";
				while(!side.equalsIgnoreCase("L") && !side.equalsIgnoreCase("R") && !side.equalsIgnoreCase("W")){
					System.out.print("Add to...Left-side(L), Right-side(R), Whole(W): ");
					side = key_in.next();
				}
				
			
				//Add topping to pizza
				p.addTopping((String)toppings.get(topper), side);
			}//if()
		}//while()
		order.addItem(p);

	}//getPizza()
}