/*
<package>
	Bank Application
<.package>
<description>
    Console test of the bank applications operations
<.description>
<keywords>
    command line, menu interface, test
<.keywords>
*/

import java.io.*;
import java.util.*;

class bankTest {

	private static Bank b1 = new Bank();
	private static Scanner keyIn = new Scanner(System.in);
	
	public static void main ( String[] args )
	{
		int choice = 0;
		double emptyDouble = 0.0;
		String emptyString = "";
		readIn();
			
			//Sort customer list
			printMenu();
			System.out.println("Sort the list");
			menu(6,emptyDouble,emptyString,emptyString,emptyString);
						menu(7,emptyDouble,emptyString,emptyString,emptyString);
			
			//Print the list
			printMenu();
			System.out.println("Print the list");
			menu(7,emptyDouble,emptyString,emptyString,emptyString);
			
			//Withdraw $125 from  Smith John account
			printMenu();
			System.out.println("Withdraw $125 from  Smith John account");
			menu(3,125.00,"Smith","John","checking");
						menu(7,emptyDouble,emptyString,emptyString,emptyString);
			
			//Withdraw $300 from Smith John account
			printMenu();
			System.out.println("Withdraw $300 from Smith John account");
			menu(3,300.00,"Smith","John","checking");
			menu(7,emptyDouble,emptyString,emptyString,emptyString);
			
			//Deposit $500 to Clinton Hillery's account 
			printMenu();
			System.out.println("Deposit $500 to Clinton Hillery's account");
			menu(2,500.00,"Clinton","Hillery","saving");
			menu(7,emptyDouble,emptyString,emptyString,emptyString);
			
			//Remove Obama Barak from the list
			printMenu();
			System.out.println("Remove Obama Barak from the list");
 			menu(5,emptyDouble,"Obama","Barak",emptyString);
			menu(7,emptyDouble,emptyString,emptyString,emptyString);
			
			//Deposit -$100 into George Bush account
			printMenu();
			System.out.println("Deposit -$100 into George Bush account");
			menu(2,-100.00,"Bush","George","saving");
			menu(7,emptyDouble,emptyString,emptyString,emptyString);
			
			//Withdraw -$50 from Grove Kavin account 
			printMenu();
			System.out.println("Withdraw -$50 from Grove Kavin account");
			menu(3,-50.00,"Grove","Kavin","checking");
			menu(7,emptyDouble,emptyString,emptyString,emptyString);
	
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
	
	private static void menu ( int c,
										double dollars,
										String l,
										String f,
										String accType )
	{

		switch (c)
		{
			case 1: add();
						break;
			case 2: b1.deposit(l,f,dollars);
						break;
			case 3: b1.withdraw(l,f,dollars);
						break;
			case 4: b1.retrieve(l,f);
						break;
			case 5: b1.deleteCustomer(l,f);
						break;
			case 6: b1.sortList();
						break;
			case 7: b1.printList();
						break;
		}

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
	
	private static void printMenu()
	{
			System.out.print("\n1.	Add a customer\n" +
								 "2.	Deposit\n" +
								 "3.	Withdraw\n" +
								 "4.	Retrieve a customer\n" +
								 "5.	Remove  a customer\n" +
								 "6.	Sort the customers  (in ascending order)\n" +
								 "7.	Print the list of customers ( last  name first name, id, and balance)\n" +
								 "8.	Quit\n" +
								 "--> ");
	}
}