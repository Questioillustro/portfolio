/*
	Program: ArrayPack
	Author: Stephen Brewster	
	Date: 12/7/2010	
	Purpose: This program generates an array, deletes a value from it, then repacks the array
				so that all elements are contiguous (no null values in between real values
*/

class ArrayPack {
	
	public static void main (String[] args){
		int[] sortedArray = new int[15];
		generateArray(sortedArray);
		
		System.out.print(sortedArray);
		
		
	
	
	}
	
	public static void generateArray(int[] sortedArray){
		for (int i = 0; i <= 10; i++){
			sortedArray[i] = i;
		}
	}
	
}