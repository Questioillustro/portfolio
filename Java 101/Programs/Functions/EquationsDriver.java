/*
	Program: Main driver file for multiple physics, calculus and digital electronic equations.
*/

	
class EquationsDriver {
	public static void main(String[] args) {
		FunctionList fL = new FunctionList();

//Print Function List
	fL.printList();
	int choice = fL.getChoice();


		while (choice != 0){
	
//Calling chosen method
			switch (choice){
			
				case 1: AngleVectors c1 = new AngleVectors();
							c1.getAngle();
								break;
			}	
		fL.printList();
		choice = fL.getChoice();	
		
		}	
	}
}


