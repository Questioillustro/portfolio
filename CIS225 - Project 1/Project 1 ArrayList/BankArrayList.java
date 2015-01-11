import java.util.*;
class BankArrayList {

	private ArrayList[] customerArrayList = new ArrayList[20];
	private int count_acc = 0, count_cust = 0;
	private boolean id[] = new boolean[1000];
	
	public void addCustomer(Customer c)
	{
		if ( count == customerArrayList.length - 1 )
			customerArrayList = ensureCapacity();
		
		customerArrayList[count] = new ArrayList<Customer>();	
		customerArrayList
				
		customerArrayList[count] = c;
		customerArrayList[count].setIndex(count); 
		customerArrayList[count].setId(findId());
		count++;
	}
	
	public Customer[] ensureCapacity ( )
	{
		ArrayList[] temp = new ArrayList[ customerArrayList.length + 5 ];
			for (int i = 0; i < customerArrayList.length; i++)
			{
				temp[i] = customerArrayList[i];
			}
		return temp;
	}
	
	public void deleteCustomer( String l, String f )
	{
		Customer c = retrieve ( l, f );
		int temp_index = c.getIndex();
		id[c.getId()] = false;
		customerArrayList[temp_index] = null;
		
		for (int i = temp_index; i < count; i++)
		{
			customerArrayList[i] = customerArrayList[i + 1];
		}

		count--;
	}
	
	public void deleteCustomer( Customer c )
	{
		int temp_index = c.getIndex();
		id[c.getId()] = false;
		customerArrayList[temp_index] = null;
		
		for (int i = temp_index; i < count; i++)
		{
			customerArrayList[i] = customerArrayList[i + 1];
		}

		count--;
	}
	
	public void printList ( )
	{
		for (int i = 0; i < count; i++)
		{
			customerArrayList[i].getInfo();
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
			if (customerArrayList[i].getLastName().equalsIgnoreCase(l) &&
			   customerArrayList[i].getFirstName().equalsIgnoreCase(f))
			{   
				c = customerArrayList[i];
			}
		}
		
		return c;
	}
	
   public Customer retrieve ( int d )
	{
		Customer c = new Customer();
		
		for (int i = 0; i < count; i++)
		{
			if ( customerArrayList[i].getId() == d )
			   		c = customerArrayList[i];
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
				alpha = customerArrayList[i].getLastName();
				omega = customerArrayList[i + 1].getLastName();
				
				if ( alpha.compareTo(omega) > 0 )
				{
					passer = customerArrayList[i];
					customerArrayList[i] = customerArrayList[i + 1];
					customerArrayList[i].setIndex(i);
					customerArrayList[i + 1] = passer;
					customerArrayList[i + 1].setIndex(i + 1);
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