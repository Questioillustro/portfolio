/*
	Program: Generates a list of available methods
	See EquationsDriver.java
*/

import java.util.*;

class FunctionList{
	Scanner scanner = new Scanner(System.in);
	
	public void printList(){
	System.out.println("---Vectors---");
	System.out.println("1. Functions of two vectors");
	
	
	System.out.println();
	System.out.println("0. Exit");
	}
	
	public int getChoice(){
	System.out.print("Enter selection: ");
	int choice = scanner.nextInt();
		while (choice < 0 || choice > 1){	//Input error correction
			System.out.print("\nError: Input is invalid.\n");
				System.out.print("Enter selection: ");
		choice = scanner.nextInt();
		}
	return choice;
	}
}
	