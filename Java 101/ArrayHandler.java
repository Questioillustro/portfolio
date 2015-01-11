import java.util.*;

class ArrayHandler{
	Random random = new Random();
	
	public void generateArray(int[] sortedArray){
		for (int i = 1; i <= 10; i++){
			sortedArray[i-1] = i;
		}
	}
	
	public void printArray(int[] sortedArray){
		for (int i = 0; i <= 14; i++){
			//Prints array, displaying '0' as 'null'
			if (sortedArray[i] != 0){		
			System.out.print(sortedArray[i] + " ");
			} else {
			System.out.print("null ");
			}
		try {
			Thread.sleep(100); //100 ms delay
		} catch(InterruptedException e) {
		e.printStackTrace();
		  }
		}
	}
	
	public void deleteElement(int[] sortedArray, int n){
		sortedArray[n-1] = 0;
	}
	
	public void packSorted(int[] sortedArray){
		for (int i = 0; i <= 13; i++){
			if (sortedArray[i] == 0){
				for (int j = i; j <= 13; j++){
				sortedArray[j] = sortedArray[j+1];
				}
			}
		}
	}
	
	public void generateUnsorted(int[] unsortedArray){
		for (int i = 0; i <= 12; i++){
			unsortedArray[i] = random.nextInt(100) + 1;
		}
		
	}
	
	public void packUnsorted(int[] unsortedArray){
		int end = 0, empty = 0;
		for (int i = 1; i <= 13; i++){
			if (unsortedArray[i] == 0 && unsortedArray[i-1] !=0 && unsortedArray[i+1] !=0){
				empty = i;
			} 
			if (unsortedArray[i] != 0 && unsortedArray[i-1] != 0 && unsortedArray[i+1] == 0){
				end = i;
			}
		}
		unsortedArray[empty] = unsortedArray[end];
		unsortedArray[end] = 0;
	}
			
			

}