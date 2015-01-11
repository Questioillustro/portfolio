/* 
	Program: RootCalculator
	Author: Stephen Brewster
	Date: 9/20/2010
	Purpose: The purpose of this program is to extract coefficient values from a string,
				then calculate the roots of the equation
*/	


class RootCalculator{
	public static void main(String[] args){
	
	//String assignment
	String coefAInput = "The coefficient a is {2.34}";
	String coefBInput = "For the b coefficient, the number is {9.42}";
	String coefCInput = "Now then, the coefficient for c is going to be {6.74}";
	String tempCoef;
		
	//Variable assignment
	double a,b,c,xp,xm;
	int start,end;
	
	//Extracting the value of (a) from the string
	start = coefAInput.indexOf("{")+1;
	end = coefAInput.indexOf("}");
	tempCoef = coefAInput.substring(start,end);
	a = Double.parseDouble(tempCoef);
	
	//Extracting the value of (b) from the string
	start = coefBInput.indexOf("{")+1;
	end = coefBInput.indexOf("}");
	tempCoef = coefBInput.substring(start,end);
	b = Double.parseDouble(tempCoef);
	
	//Extracting the value of (c) from the string
	start = coefCInput.indexOf("{")+1;
	end = coefCInput.indexOf("}");
	tempCoef = coefCInput.substring(start,end);
	c = Double.parseDouble(tempCoef);
	
	//Finding the roots
	System.out.println("Calculating the roots of: " + a + "x^2 + " + b + "x + " + c + " = 0");
	xm = (-b - Math.sqrt(Math.pow(b,2) - 4 * a * c)) / (2 * a);
	xp = (-b + Math.sqrt(Math.pow(b,2) - 4 * a * c)) / (2 * a);

	System.out.println("Results: {" + xp + ", " + xm + "}");

	}
}
	
	
	
	