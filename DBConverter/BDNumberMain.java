/*
<package>
	Decimal-Binary Converter
<.package>
<description>
    Converts numbers between decimal and binary
<.description>
<keywords>
	driver, main
<.keywords>
*/

/**
	Program: BDNumber
	Author: Stephen Brewster
	Date: 10/25/10
	Purpose: This program models the Fraction class, performing similar
		methods with integers in place of fractions.
	Runs With: BDNumber
*/
import java.util.*;
class BDNumberMain{

	public static void main( String[] args) {
		BDNumber toConvert;
		Scanner keyIn = new Scanner(System.in);	
		int baseIn, baseOut;
		String num;
			
			System.out.print("What base will the number be in? (2,8,10,16): ");
				baseIn = keyIn.nextInt();
			System.out.print("What base are you converting to? (2,8,10,16): ");
				baseOut = keyIn.nextInt();
			System.out.print("Enter the number: ");
				num = keyIn.next();
				
			toConvert = new BDNumber(num,baseIn);
			
			//System.out.print(num + " in base " + baseOut + " is: " + toConvert.getConversion(baseOut) + ".");
	}
}	
		
	
	
	
	
	
	
