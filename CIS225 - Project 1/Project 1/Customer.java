/*
<package>
	Bank Application
<.package>
<description>
    Customer object class
<.description>
<keywords>
    customer class
<.keywords>
*/

//************************************************
//           Name:        Steven Brewster, Calvin Burnup
//           Course:      CSC 225
//           Instructor:  Wexler
//           Project:     Customer.java
//           Due Date:    March 10th 2011
//
//************************************************
//******************************************************************************
// Program Customer.java - Contains initial contrsuctors and calls for methods.
// The Parent class of Savings and Checking.
//******************************************************************************

import java.util.*;

public class Customer {
	
	protected String first, last, phone;
	private int index, id;

	public Customer()
	{
		first   = "";
		last    = "";
		phone   = "";
	}
	
	public Customer(String l, String f)
	{
		first   = f;
		last    = l;
		phone   = "";
	}
	
	public Customer(String l, String f, String pho)
	{
		first   = f;
		last    = l;
		phone   = pho;
	}
	
	public void getInfo ()
	{
		System.out.println("\nCustomer: " + last + ", " + first);
		System.out.println("Id Number: " + id);
		System.out.println("Phone: " + phone);
	}
	
	public void withdraw ( double d )
	{ }
	
	public void deposit ( double d )
	{ }
	
	public void setId ( int i )
	{
		id = i;
	}
	
	public int getId ( )
	{
		return id;
	}
	
	public void setIndex ( int ind )
	{
		index = ind;
	}
	
	public int getIndex ( )
	{
		return index;
	}
	
	public String getLastName ( )
	{
		return last;
	}
	
	public String getFirstName ( )
	{
		return first;
	}
	
}