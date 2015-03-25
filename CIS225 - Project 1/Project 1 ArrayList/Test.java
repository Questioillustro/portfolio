import java.io.*;
import java.util.*;

class bankTest {

	private static Bank b1 = new Bank();
	private static Scanner keyIn = new Scanner(System.in);
	
	public static void main ( String[] args )
	{
		Customer t = new Customer();
		readIn();
		b1.deleteCustomer("bush","george","saving");
		b1.sortList();
		b1.printList();
		
	
	
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
}