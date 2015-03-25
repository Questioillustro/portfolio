/*
	Program: ArrayPack
	Author: Stephen Brewster	
	Date: 12/7/2010	
	Purpose: This program generates an array, deletes a value from it, then repacks the array
				so that all elements are contiguous (no null values in between real values
*/
import java.util.*;
class ArrayPack {
	
	public static void main (String[] args){
		int n;
		int[] sortedArray = new int[15];
		int[] unsortedArray = new int[15];
		
		Scanner scanner = new Scanner(System.in);
		ArrayHandler a1 = new ArrayHandler();
		
		System.out.print("First, repacking a sorted array.\n\n");
		a1.generateArray(sortedArray);
		a1.printArray(sortedArray);
		
		System.out.println("\n\nSelect an element to delete (1-10): ");
		n = scanner.nextInt();
		System.out.println();
		
		a1.deleteElement(sortedArray,n);
		a1.printArray(sortedArray);
		System.out.println();
		
		a1.packSorted(sortedArray);
		a1.printArray(sortedArray);
		System.out.println("\n\n");
		
		System.out.print("Now, repacking an unsorted array.\n\n");
		
		a1.generateUnsorted(unsortedArray);
		a1.printArray(unsortedArray);
				
		System.out.println("\n\nSelect an element to delete (1-12): ");
		n = scanner.nextInt();
		System.out.println();
		
		a1.deleteElement(unsortedArray,n);
		a1.printArray(unsortedArray);
		System.out.println();
		
		a1.packUnsorted(unsortedArray);
		a1.printArray(unsortedArray);
		
		
	
		
		
		
		
		
		
		
	
	
	} // End main
	
	
} //End class