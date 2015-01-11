/*
<package>
	Lab 1 - csc103
<.package>
<description>
	Tests the quadratic functions of the lab by reading in a file and verifying results
<.description>
<keywords>
	test, quadratic, parse
<.keywords>
*/

import java.io.*;


public class QuadTest {

	/**
	 * @param Class: QuadTest
	 * @param 
	 * @param Author: Stephen Brewster
	 * @param Description: Solution for Ch.2, projects 8 & 9, page 95.
	 * 					   Works with Quadratic class to create quadratic
	 * 					   equation objects to be manipulated in various manners.
	 */
	
	Quadratic q1,
				 q2,
				 sum,
				 scaled,
				 clone;
				 
	double[] coeffValues;
		
	public void parse(String fileLine)
	{
	// Split the line from the file into an array then convert string values
	// into double vaules.
		String[] splitter = fileLine.split(" ");
		
		if (splitter.length == 8)
		{
			coeffValues = new double[splitter.length];
			
			for (int i = 0; i <= splitter.length - 1; i++)
			{	
				coeffValues[i] = Double.parseDouble(splitter[i]);
			}
		
	// Use values from coeffValues array to create quadratic objects
			q1 = new Quadratic(coeffValues[0],
								    coeffValues[1], 
									 coeffValues[2]);
								 
			q2 = new Quadratic(coeffValues[5], 
									 coeffValues[6], 
									 coeffValues[7]);
								 
			sum = new Quadratic();
			sum = Quadratic.sum(q1,q2);
		
			calculations();
		}  else 
				System.out.println("Data line, " + fileLine + ", is of insufficient length");
	}
	

	public static void printIntro()
	{
		System.out.println("Welcome to the quadratic manipulation program!" +
						   "\nThis program will read lines of data from inputCoef.txt" +
						   "\nwhich will consist of coefficient values for 2 quadratics" +
						   "\nand 2 values which will be used to scale and solve the quadratics.");
	}
	
	
	private void calculations()
	{
		System.out.println("The first quadratic is: ");
		q1.show();
		
		System.out.println("\nThe result of the first quadratic expression with x = " +
								    coeffValues[3] + " is: " + q1.evalExpression(coeffValues[3]));
				
		scaled = new Quadratic();
		scaled = Quadratic.scale(coeffValues[4],q1);						
			
			System.out.println("\nThe first quadratic after scaling with R = " + coeffValues[4] + " is: ");
			scaled.show();
			
		q1.findRoots();
		if (q1.getNumRoots() == 3.0)
			System.out.println("\nNumber of roots: Infinite");
		else
			System.out.println("\nNumber of roots: " + q1.getNumRoots());
		
		if (q1.getNumRoots() == 2.0)
		{	
		System.out.println("Value of root one: " + q1.getRootOne());
		System.out.println("Value of root two: " + q1.getRootTwo());
		}
		
		if (q1.getNumRoots() == 1.0)
			System.out.println("Value of root: " + q1.getRootOne());
		
		System.out.println("\nThe second quadratic is: ");
		q2.show();
		
		System.out.println("\nThe quadratic which is the sum of the first 2 is: ");
		sum.show();
		
		clone = new Quadratic();
		clone = Quadratic.clone(q1);
			
			System.out.println("\nA clone of the first quadratic is: ");
			clone.show();
	}

}

