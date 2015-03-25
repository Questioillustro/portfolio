/*
	Program: LoopTutorial
	Author: Stephen Brewster
	Date: 11/6/2010
	Purpose: This program presents 5 loops in a tutorial fashion
	Note: Runs with LoopList
*/

import java.util.*;
import javax.swing.*;

class LoopTutorial {
	
	public static void main (String[] args){
	
	//objects
	Scanner keyIn = new Scanner(System.in);
	LoopList l1   = new LoopList();
	
		
		//data members
		int c = 0;
		String repeat = "";
		boolean flag = true;
		
	//Begin main loop
 	while (flag){
		
		
	//Recieve user input with error handling
	while (flag){		
		try{
			System.out.print("Enter a loop to examine (1-5):");
			c = keyIn.nextInt();
				
					if (c < 0 || c > 5){
						throw new Exception("Error: Entered value, " + c + ", is out of range.");
					}
					
			flag = false; //while loop kick
		}catch (InputMismatchException e) {
			keyIn.next();
			JOptionPane.showMessageDialog(null, "Error: InputMismatchException. Value must be an integer.");
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	flag = true;
	
			
	//Print: desired loop and output of loop with delay
	l1.printList(c); //print loop
	delay();
	l1.runLoop(c); //print output of loop
	
	//Check with user if they wish to examine another loop
	while (flag){
		try{
			System.out.print("\n\nWould you like to examine another loop? (Y/N):");
			repeat = keyIn.next();
				if (!(repeat.equals("y") || repeat.equals("Y") || repeat.equals("N") || repeat.equals("n"))){
					throw new Exception("Error: Input must be Y/y or N/n");
				}
			
		flag = false; //while loop	kick
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	flag = true;
	
	//Check for program continuation
	repeat = repeat.toLowerCase();
			if (repeat.equals("n")){
				flag = false;
			}
	} //End of Primary While loop

	} //End of Main
	
	//3 second delay method
	private static void delay(){
	System.out.print("\nPreparing loop output ");
	
	for (int i = 1; i <= 3; i++){
	System.out.print(" . ");                        		
		try {
			Thread.sleep(1200); //100 ms delay
		} catch(InterruptedException e) {
		e.printStackTrace();
		  }
	}	  
		System.out.println("\n");
	}
	
}
