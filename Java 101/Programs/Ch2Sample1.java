/* 
	Program: RootCalculator
	Author: Stephen Brewster
	Date: 9/20/2010
	Purpose: The purpose of this program is to extract coefficient values from a string, then calculate the roots of the equation
*/	


class RootCalculator{
	public static void main(String[] args){
	
	//String assignment
	String coefAInput = "The coefficient a is {2.34}";
	String coefBInput = "For the b coefficient, the number is {9.42}";
	String coefCInput = "Now then, the coefficient for c is going to be {6.74}";
		
	//Variable assignment
	int a,b,c,loc;
	
	loc = coefAInput.indexOf("{");
	System.out.println(loc);
	
	}
}
	
	
	
	