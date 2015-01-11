import java.util.*;
class Bank {

	private ArrayList[] customerArrayList = new ArrayList[20];
	private int count_acc = 0, count_uniqueCust = 0;
	private boolean id[] = new boolean[20];
	
	public void addCustomer(Customer c)
	{
		Customer temp = new Customer();

	c.setId(findId());
		if (isCustomer(c.getLastName(), c.getFirstName()))
		{
			temp = retrieve(c.getLastName(), c.getFirstName());
			c.setIndex(temp.getIndex());
			customerArrayList[c.getIndex()].add(c);
			count_acc++;
		} else {
			customerArrayList[count_uniqueCust] = new ArrayList<Customer>();
			c.setIndex(count_uniqueCust);
			customerArrayList[count_uniqueCust].add(c);
			count_acc++;
			count_uniqueCust++;
		}
	}
	
	public void printList ( )
	{
		ArrayList<Customer> temp = new ArrayList<Customer>();
		
		for (int i = 0; i < count_uniqueCust; i++)
		{
			temp = customerArrayList[i];
				for (int j = 0; j < temp.size(); j++)
				{
					temp.get(j).getInfo();
					System.out.println("Sub Index: " + j);
				}
		}
	}
	
	public Customer retrieve ( String l, String f )
	{
		Customer c = new Customer();
		ArrayList<Customer> temp;
		
			for (int i = 0; i < count_uniqueCust; i++)
			{
				temp = customerArrayList[i];
				c = temp.get(0);
				if (c.getLastName().equalsIgnoreCase(l) &&
				    c.getFirstName().equalsIgnoreCase(f))
				{
					return c;	 
				}
				
			}
			
			return c;
	}
	
	public boolean isCustomer ( String l, String f )
	{
		Customer c = new Customer();
		boolean flag = false;
		ArrayList<Customer> temp;
		
			for (int i = 0; i < count_uniqueCust; i++)
			{
				temp = customerArrayList[i];
				c = temp.get(0);
				if (c.getLastName().equalsIgnoreCase(l) &&
				    c.getFirstName().equalsIgnoreCase(f))
				{
					return true;	 
				}
			}
		
		return flag;
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
	
	public void deleteCustomer( String l, String f, String acc )
	{
		Customer c = retrieve ( l, f );
		ArrayList<Customer> temp = customerArrayList[c.getIndex()];
		
		for (int i = 0; i < temp.size(); i++)
		{
			c = temp.get(i);
			if (c.getAccType().equalsIgnoreCase(acc))
			{
				id[c.getId()] = false;
				customerArrayList[c.getIndex()].remove(i);
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
	
	public void sortList ( )
	{
		ArrayList<Customer> passer = new ArrayList<Customer>();
		Customer first, last;
		String alpha, omega;
		boolean flag = true;
		
		while (flag)
		{
			flag = false;
			for (int i = 0; i < count_uniqueCust - 1; i++)
			{
				passer = customerArrayList[i];
				alpha = passer.get(0).getLastName();
				
				passer = customerArrayList[i + 1];				
				omega = passer.get(0).getLastName();
				
				if ( alpha.compareTo(omega) > 0 )
				{
					passer = customerArrayList[i];
					customerArrayList[i] = customerArrayList[i + 1];
					customerArrayList[i + 1] = passer;
					flag = true;
				}
			}
		}
		
		for (int i = 0; i < count_uniqueCust; i++)
		{
			passer = customerArrayList[i];
				for (int j = 0; j < passer.size(); j++)
				{
					passer.get(j).setIndex(i);
				}
		}
	}
}