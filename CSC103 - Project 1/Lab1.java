//****************************************************************
//*Program Name: Lab1
//*Author: Stephen Brewster
//*Abstract: Used to read in data lines from a text file to be
//*  			 used in the construction of quadratic objects.
//*----------------------------------------
//*Classes: 3
//*Methods: Several methods to process the quadratic formulas
//*
//********************************************************************

import java.io.*;

/**
*@class: Lab1
*@author: Stephen Brewster
*/

class Lab1 {
	
	public static void main (String[] args) 
	{
		//object declarations
		QuadTest qt = new QuadTest();
		FileReader theFile;
  	   BufferedReader fileIn = null;
      
		String oneLine;
		int testNum = 1;
		
		//try catch block, reads in one line of the data file at a time and passes it
		//to the QuadTest class for parsing
		try 
        {
    			qt.printIntro();        
				theFile = new FileReader( "inputCoef.txt" );
            fileIn = new BufferedReader( theFile );
            	
					while( ( oneLine = fileIn.readLine( ) ) != null )
               {
						System.out.println("\n****** Test Number " + testNum + " ******\n");
						qt.parse( oneLine );
						testNum++;
					}
         }
        catch( Exception e )
            {  System.out.println( e ); }

	}
}