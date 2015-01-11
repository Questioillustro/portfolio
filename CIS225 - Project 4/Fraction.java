/*
<package>
	Calculator Server
<.package>
<description>
    Performs calculator logic for the Calculator Server
<.description>
<keywords>
	calculator logic
<.keywords>
*/

public class Fraction{
	private int numerator, denominator;

	public Fraction(int n, int d)
	{
		numerator = n;
		denominator = d;
	}
	
	public int getN ( )
	{
		return numerator;
	}
	
	public int getD ( )
	{
		return denominator;
	}
	
	//Multiplies fractions 'f' and 's', returns result
	public static Fraction multiply ( Fraction f, Fraction s )
	{
		return new Fraction((f.getN() * s.getN()),(f.getD() * s.getD()));
	}
	
	//Adds fractions 'f' and 's', does not find common denominator, returns result
	public static Fraction add ( Fraction f, Fraction s )
	{
		return new Fraction((f.getN() * s.getD()) + (s.getN() * f.getD()),f.getD() * s.getD());
	}
	
	//Divides fraction 'f' by fraction 's', returns result
	public static Fraction divide ( Fraction f, Fraction s )
	{
		return new Fraction((f.getN() * s.getD()),s.getN() * f.getD());
	}
	
	//Subtracts fraction 's' from 'f', returns result
	public static Fraction subtract ( Fraction f, Fraction s )
	{	
		return new Fraction((f.getN() * s.getD()) - (s.getN() * f.getD()),f.getD() * s.getD());
	}
	
	//Returns true if first fraction is less than second fraction
	public static boolean lessThan ( Fraction f, Fraction s )
	{
		return (((double)f.getN()/(double)f.getD()) < ((double)s.getN()/(double)s.getD()));
	}
	
	//Returns true if first fraction is greater than second fraction
	public static boolean greaterThan ( Fraction f, Fraction s )
	{
		return (((double)f.getN()/(double)f.getD()) > ((double)s.getN()/(double)s.getD()));
	}
	
	//Returns true if fractions are equal
	public static boolean equalTo ( Fraction f, Fraction s )
	{
		return (((double)f.getN()/(double)f.getD()) == ((double)s.getN()/(double)s.getD()));
	}
}		