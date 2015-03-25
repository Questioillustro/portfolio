/**
	Program: BDNumber
	Author: Stephen Brewster
	Date: 10/25/10
	Purpose: This program models the Fraction class, performing similar
		methods with integers in place of fractions.
	Runs With: BDNumber
*/

class BDNumberMain{

	public static void main( String[] args) {
		BDNumber n1, n2, sum, diff;
			
			n1 = new BDNumber(200);
			n2 = new BDNumber("10101011");
			
			sum = n1.add(n2);
			diff = n1.subtract(n2);
			
			System.out.print("First number = " + n1.getDec() + " = " + n1.getBin());
				System.out.print("\nSecond number = " + n2.getDec() + " = " + n2.getBin());
			
			System.out.print("\n\nAddition:\nResult = " + sum.getDec() + " = " + sum.getBin());
			
			System.out.print("\n\nSubtraction:\nResult = " + diff.getDec() + " = " + diff.getBin());
			
	}
}	
		
	
	
	
	
	
	
/*	
		//Recieve input from user
		System.out.println("---Enter 2 decimal or binary numbers to add or subtract---");
		System.out.println("**When performing subtraction, result must be greater than 0.**");
					
			do {	
				System.out.print("\nWill the first number be a binary or decimal number?");
				System.out.print("\n1. Binary");
				System.out.print("\n2. Decimal");
				System.out.print("\nEnter choice: ");
					c = key_in.nextInt();
			} while (c !=1 && c != 2);
					
		switch (c){
			case 1: System.out.print("\nEnter Binary number: ");
						bin_num = key_in.next();
						break;
			
			case 2: System.out.print("\nEnter Decimal number: ");
						dec_num = key_in.nextInt();
						break;
		}
			BDNumber n1 = new BDNumber(
			
			do {	
				System.out.print("\nWill the second number be a binary or decimal number?");
				System.out.print("\n1. Binary");
				System.out.print("\n2. Decimal");
				System.out.print("\nEnter choice: ");
					c = key_in.nextInt();
			} while (c !=1 && c != 2);
					
		switch (c){
			case 1: System.out.print("\nEnter Binary number: ");
						bin_num = key_in.next();
							break;
			
			case 2: System.out.print("\nEnter Decimal number: ");
						dec_num = key_in.nextInt();
							break;
		}

				BDNumber n2 = new BDNumber(dec_num);
	}
}
*/