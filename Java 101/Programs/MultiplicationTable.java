/*
	Program: MultiplicationTable
	Author: Stephen Brewster
	Date: 10/13/2010
	Purpose: The purpose of this program is to generate multiplication tables based on user inputs
*/

import java.util.*;
import javax.swing.*;

class MultiplicationTable{


	public static void main (String[] args){
		Scanner keyboard = new Scanner(System.in);
		String remain;
		String formatString = "%6d";
		int x,y;
	
	//Main loop
	do{
	System.out.print("Enter first number: ");
	x = keyboard.nextInt();
	
	System.out.print("Enter second number, (>=" + x + ") : ");
	y = keyboard.nextInt();
		
		//Input error correction loop
		while ( y < x ){
			System.out.print("\nError: Second number must be > or = First number.");
			System.out.print("\nEnter second number, (>=" + x +") : ");
			y = keyboard.nextInt();
		}
		
	System.out.print("\n");	
	
	//Calling Methods
	makeHeader(x, y, formatString);
	makeTable(x, y, formatString);
		
	
	System.out.print("Make another? (Y/N): ");
	remain = keyboard.next();
	
		}while (remain.equals("y") || remain.equals("Y"));
	
	System.out.println("\nHave a great day!");
	}
	
	

private static void makeHeader(int low, int high, String f){
	System.out.print("   ");
	//Print numbers in header
	for ( int i = low; i <= high; i++ ){
		System.out.format(f,i);
	} 
	
	//Print border
			System.out.print("\n  +-----");
				for (int i = low; i < high; i++ ){
					System.out.print("-------");
				}
}
	
private static void makeTable(int low, int high, String f){
	System.out.print("\n");
	
	//Creating the table
	for ( int i = low; i <= high; i++ ){
		System.out.print(i + " |");
			
			for ( int j = low; j<= high; j++ ){
				System.out.format(f, i*j);	
			}
		
		System.out.print("\n");	
	}
	System.out.print("\n");
}


}	
	
	
	
	