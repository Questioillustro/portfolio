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
		System.out.println("Index: " + index);
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
	
	public String getAccType ( )
	{
		return first;
	}
	
}