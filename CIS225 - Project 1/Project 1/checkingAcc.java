/*
<package>
	Bank Application
<.package>
<description>
    Checking account logic for Bank Application
<.description>
<keywords>
    bank logic
<.keywords>
*/

class checkingAcc extends Customer {

	private double balance;
	
	public checkingAcc ( String l, 
							 String f, 
							 String pho, 
							 double bal )
	{
		super(l, f, pho);
		balance = bal;
	}
	
	public checkingAcc ( String l,
							 String f )
	{
		super(l, f);
		balance = 0.0;
	}
	
	public checkingAcc ( )
	{
		super();
		balance = 0.0;
	}
	
	public void getInfo ()
	{
		super.getInfo();
		System.out.println("Checking account balance: " + balance);
	}
	
	
	public void deposit  ( double d )
	{
		if ( d < 0 )
			System.out.println("\n**Error: Deposit cannot be negative**");
		else {
			balance += d;
			super.getInfo();
			System.out.println("Amount deposited: " + d);
			System.out.println("New balance: " + balance);
		}
	}
	
	public void withdraw ( double w )
	{
		if ( w >= balance ) 
		{
			System.out.println("\n**Error: Requested funds exceeds available balance**");
			w = balance;
		} else if ( w < 0 )
			System.out.println("\n**Error: Invalid withdrawal amount**");
		else {
			balance -= w;
			super.getInfo();
			System.out.println("Amount withdrawn: " + w);
			System.out.println("Remaining balance: " + balance);
		}
	}	 
}	