/*
<package>
	GUI Calculator in Java
<.package>
<description>
    Logic controller for the calculator
<.description>
<keywords>
	controller, calculator logic
<.keywords>
*/

import java.util.*;

public class fractionCalculator{
	private Fraction frac1, frac2, rFrac;
	private int operator;
	private String result, inline;
	
	public fractionCalculator( String s )
	{
		inline = s;
		String[] e, 
					f1, 
					f2;
		
		e = s.split(" ");  //Splits string from inputWin text field into fractions and operation
		
		//Create arrays of fraction values
		f1 = e[0].split("/"); 		
		f2 = e[2].split("/"); 
		
		operator = (int)e[1].charAt(0); //Set ASCII value of operand to 'operator'
			if (e[1].length() == 2) //If Operand is 2 characters long...
				operator += (int)e[1].charAt(1); //Adds ASCII value of second character to 'operator'
		
		//Create fraction objects from strings
		frac1 = new Fraction(Integer.parseInt(f1[0]),Integer.parseInt(f1[1]));
		frac2 = new Fraction(Integer.parseInt(f2[0]),Integer.parseInt(f2[1]));
	}
	
	//Checks for arithmetic operation, then executes and builds a return String
	public String getResult ( )
	{
		switch(operator)
		{
			//*,+,/,- functions
			//*
			case 42: rFrac = Fraction.multiply(frac1,frac2);
						result = inline + " = " + rFrac.getN() + "/" + rFrac.getD(); 
						break;
			//+
			case 43: rFrac = Fraction.add(frac1,frac2);
						result = inline + " = " + rFrac.getN() + "/" + rFrac.getD();
						break;
			///
			case 47: rFrac = Fraction.divide(frac1,frac2);
						result = inline + " = " + rFrac.getN() + "/" + rFrac.getD();
						break;
			//-
			case 45: rFrac = Fraction.subtract(frac1,frac2);
						result = inline + " = " + rFrac.getN() + "/" + rFrac.getD();
						break;
			
			//<,>,= functions
			//<
			case 60: if (Fraction.lessThan(frac1,frac2)){
							result = frac1.getN() + "/" + frac1.getD() + 
							" is less than " + 
							frac2.getN() + "/" + frac2.getD();
						} else {
							result = frac1.getN() + "/" + frac1.getD() + 
							" is not less than " + 
							frac2.getN() + "/" + frac2.getD();
						}
						break;
			//>
			case 62: if (Fraction.greaterThan(frac1,frac2)){
							result = frac1.getN() + "/" + frac1.getD() + 
							" is greater than " + 
							frac2.getN() + "/" + frac2.getD();
						} else {
							result = frac1.getN() + "/" + frac1.getD() + 
							" is not greater than " + 
							frac2.getN() + "/" + frac2.getD();
						}
						break;
			//=
			case 61: if (Fraction.equalTo(frac1,frac2)){
							result = frac1.getN() + "/" + frac1.getD() + 
							" is equal to " + 
							frac2.getN() + "/" + frac2.getD();
						} else {
							result = frac1.getN() + "/" + frac1.getD() + 
							" is not equal to " + 
							frac2.getN() + "/" + frac2.getD();
						}
						break;
			//<=
			case 121: if (Fraction.lessThan(frac1,frac2) || Fraction.equalTo(frac1,frac2)){
							result = frac1.getN() + "/" + frac1.getD() + 
							" is < or = to " + 
							frac2.getN() + "/" + frac2.getD();
						} else {
							result = frac1.getN() + "/" + frac1.getD() + 
							" is not < or = to " + 
							frac2.getN() + "/" + frac2.getD();
						}
						break;
			//=>
			case 123: if (Fraction.greaterThan(frac1,frac2) || Fraction.equalTo(frac1,frac2)){
							result = frac1.getN() + "/" + frac1.getD() + 
							" is > or = to " + 
							frac2.getN() + "/" + frac2.getD();
						} else {
							result = frac1.getN() + "/" + frac1.getD() + 
							" is not > or = to " + 
							frac2.getN() + "/" + frac2.getD();
						}
						break;
			
			default:	result = "0";
						break;
						
		}
		return result;
	}	
}
	