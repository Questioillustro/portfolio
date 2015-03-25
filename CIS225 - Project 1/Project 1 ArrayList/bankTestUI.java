import java.io.*;
import java.util.*;

class bankTestUI {

	private static Bank b1 = new Bank();
	private static Scanner keyIn = new Scanner(System.in);
	
	public static void main ( String[] args )
	{
		int choice = 0;
		readIn();
		
		while (choice != 8)
		{
		
			choice = menu();
			switch (choice)
			{
				case 1: add();
							break;
				case 2: b1.deposit("Obama","Barak",200.00);
							break;
				case 3: b1.withdraw("palin","sara",3000.00);
							break;
				case 4: retrieveCustomer();
							break;
				case 5: remove();
							break;
				case 6: b1.sortList();
							break;
				case 7: b1.printList();
							break;
			}
		}
	}
	
	private static void readIn()
	{
		FileReader theFile;
		BufferedReader fileIn = null;
		String oneLine= "";

		
		try {
	   	theFile = new FileReader( "customerlist.txt" );
         fileIn  = new BufferedReader( theFile );
		   while( ( oneLine = fileIn.readLine( ) ) != null )
     		 	readInCustomer ( oneLine );
		}     
		catch( Exception e )
      	{  System.out.println( e ); }
	}
	
	private static void readInCustomer(String s)
	{
		String splitter[] = s.split(",");
		
		
		if (splitter[4].equals("checking"))
		{
			checkingAcc c1 = new checkingAcc(splitter[0],
														splitter[1],
														splitter[2],
														Double.parseDouble(splitter[3]));
			b1.addCustomer(c1);
		}
		if (splitter[4].equals("saving"))
		{
			savingAcc s1 = new savingAcc(splitter[0],
												  splitter[1],
												  splitter[2],
												  Double.parseDouble(splitter[3]));
			b1.addCustomer(s1);
		}

	}
	
	private static int menu ( )
	{
 		int c = 0;
		System.out.print("\n1.	Add a customer\n" +
								 "2.	Deposit\n" +
								 "3.	Withdraw\n" +
								 "4.	Retrieve a customer\n" +
								 "5.	Remove  a customer\n" +
								 "6.	Sort the customers  (in ascending order)\n" +
								 "7.	Print the list of customers ( last  name first name, id, and balance)\n" +
								 "8.	Quit\n" +
								 "--> ");
		c = keyIn.nextInt();
		return c;
	}
	
	private static void add ( )
	{
		String l, f, pho;
		double bal;
		System.out.print("Customer's last name: ");
			l = keyIn.next();
		System.out.print("Customer's first name: ");
			f = keyIn.next();
		System.out.print("Customer's phone number: ");
			pho = keyIn.next();
		System.out.print("Starting balance: ");
			bal = Double.parseDouble(keyIn.next());
		
		checkingAcc c1 = new checkingAcc(l,f,pho,bal);
		b1.addCustomer(c1);
		
		System.out.println("+++New customer successfully created+++");
	}
	
	private static void remove ( )
	{
		String choice = "";
		Customer deleted = new Customer();
		deleted = retrieveCustomer();
		System.out.println("Remove this customer? (y/n)");
			choice = keyIn.next();
			
			if (choice.equalsIgnoreCase("y"))
			{
				b1.deleteCustomer(deleted);
				System.out.println("---Customer has been removed---");
			} else {
				System.out.println("***Customer has not been removed***");
			}
	}
		
		
	
	private static Customer retrieveCustomer ( )
	{
		String l, f;
		int id, choice;
		Customer c = new Customer();
		
		System.out.print("Search by name or Id number?\n" +
							  "1. Name\n" +
							  "2. Id\n" +
							  "--> ");
		choice = keyIn.nextInt();
		
		switch (choice)
		{
		case 1: System.out.print("Last name: ");
				  	l = keyIn.next();
				  System.out.print("First name: ");
					f = keyIn.next();
				  c = b1.retrieve(l,f);
				   break;
		
		case 2: System.out.print("Id number: ");
					id = keyIn.nextInt();
				  c = b1.retrieve(id);
					break;
		}

		c.getInfo();
		return c;
	}
	
	
}