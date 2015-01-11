/*
<package>
	Bank Application
<.package>
<description>
    Performs business logic for Banking application
<.description>
<keywords>
    list, array, user input
<.keywords>
*/

//************************************************
//           Name:        Steven Brewster, Calvin Burnup
//           Course:      CSC 225
//           Instructor:  Wexler
//           Project:     Bank.java
//           Due Date:    March 10th 2011
//
//************************************************
//******************************************************************************
// Program Customer.java - The method class, contains all pertanant algorithims.
// Most of the program functions though here.
//******************************************************************************

import java.util.*;
class Bank {

	private Customer customerList[] = new Customer[6];
	private int count = 0;
	private boolean id[] = new boolean[1000];
	Scanner keyIn = new Scanner(System.in);
	
	public void addCustomer(Customer c)
	{
		if ( count == customerList.length - 1 )
			customerList = ensureCapacity();
			
		customerList[count] = c;
		customerList[count].setIndex(count); 
		customerList[count].setId(findId());
		count++;
	}
	
	public Customer[] ensureCapacity ( )
	{
		Customer temp[] = new Customer[ customerList.length + 5 ];
			for (int i = 0; i < customerList.length; i++)
			{
				temp[i] = customerList[i];
			}
		return temp;
	}
	
	public void deleteCustomer( String l, String f )
	{
		Customer c = retrieve ( l, f );
		int temp_index = c.getIndex();
		id[c.getId()] = false;
		customerList[temp_index] = null;
		
		for (int i = temp_index; i < count; i++)
		{
			customerList[i] = customerList[i + 1];
		}

		count--;
	}
	
	public void printList ( )
	{
		for (int i = 0; i < count; i++)
		{
			customerList[i].getInfo();
		}
	}
	
	public int findId ( )
	{
		int i = 0;
		
		while(id[i])
		{
			i++;
		}
		id[i] = true;
		return i;
	}
	
	public Customer retrieve ( String l, String f )
	{
		Customer c = new Customer();
		int ticker = 0;
		
		for (int i = 0; i < count; i++)
		{
			if (customerList[i].getLastName().equalsIgnoreCase(l) &&
			   customerList[i].getFirstName().equalsIgnoreCase(f))
			{   
				c = customerList[i];
			}
		}
		
		return c;
	}
	
   public Customer retrieve ( int d )
	{
		Customer c = new Customer();
		
		for (int i = 0; i < count; i++)
		{
			if ( customerList[i].getId() == d )
			   		c = customerList[i];
		}
		
		return c;
	}

	public void sortList ( )
	{
		Customer passer = new Customer();
		String alpha, omega;
		boolean flag = true;
		
		while (flag)
		{
			flag = false;
			for (int i = 0; i < count - 1; i++)
			{
				alpha = customerList[i].getLastName();
				omega = customerList[i + 1].getLastName();
				
				if ( alpha.compareTo(omega) > 0 )
				{
					passer = customerList[i];
					customerList[i] = customerList[i + 1];
					customerList[i].setIndex(i);
					customerList[i + 1] = passer;
					customerList[i + 1].setIndex(i + 1);
					flag = true;
				}
			}
		}
	}
		
	public void deposit ( String l, String f, double d )
	{
		Customer d1 = this.retrieve(l,f);
		d1.deposit(d);
	}
	
	public void withdraw ( String l, String f, double d )
	{
		Customer w1 = this.retrieve(l,f);
		w1.withdraw(d);
	}	 
		
}