/*
	Runs with LoopTutorial
*/

class LoopList{

	public void printList(int c){
	
	switch (c){
	case 1:
	//Print Loop 1
	System.out.println("");
	System.out.println("Loop #1");
	System.out.println("int n, sum = 0;");
	System.out.println("for (n = 1; n < 10; n++);");
	System.out.println("  sum = sum + n;");
	System.out.println("System.out.println('The sum of {1,2,3,...,9,10} is: ' + sum);");
	break;
	
	case 2:
	//Print Loop 2
	System.out.println("");
	System.out.println("Loop #2");
	System.out.println("int number = 10;");
	System.out.println("while (number > 0)\n  {");
	System.out.println("  System.out.println(number);");
	System.out.println("  number = number + 1;\n  }");
	break;
	
	case 3:
	//Print Loop 3
	System.out.println("");
	System.out.println("Loop #3");
	System.out.println("int limit = 10;");
	System.out.println("for (n = 1; n < limit; n++)\n  {");
	System.out.println("  System.out.println('n == ' + n);");
	System.out.println("  System.out.println('limit ==' + limit);");
	System.out.println("  limit = n + 2;\n  }");
	break;
	
	case 4:
	//Print Loop 4
	System.out.println("");
	System.out.println("Loop #4");
	System.out.println("number = 10;");
	System.out.println("while (number > 0)\n  {");
	System.out.println("  number = number - 2;");
	System.out.println("  if(number == 4)");
	System.out.println("    break; //break what?");
	System.out.println("    System.out.println(number);\n  }");
	System.out.println("System.out.println('The end.');");
	break;
	
	case 5:
	//Print Loop 5
	System.out.println("");
	System.out.println("Loop #5");
	System.out.println("number = 10;");
	System.out.println("while (number > 0)\n  {");
	System.out.println("  number = number - 2;");
	System.out.println("  if(number == 4)");
	System.out.println("    continue; //continue what?");
	System.out.println("    System.out.println(number);\n  }");
	System.out.println("System.out.println('The end.');");
	break;
	}
	
	}
	
	public void runLoop(int c){
		
		switch (c){
	
		//Run loop #1
		case 1:	
			int n, sum = 0;
			for (n = 1; n < 10; n++);
  				sum = sum + n;
			System.out.println("\nThe sum of {1,2,3,...,9,10} is: " + sum);
		break;
		
		//Run loop #2
		case 2:
			int number = 10;
		try {
			while (number > 0){
    			System.out.println(number);
	 			number = number + 1;
					if (number > 50){
						throw new Exception("Error: Infinite loop detected.");
					}
			}
		}catch (Exception e){
			System.out.print(e.getMessage());
			break;
		}
		break;
		
		//Run loop #3
		case 3:
			int limit = 10;
		try {
			for (n = 1; n < limit; n++){
		   	System.out.println("n == " + n);
				System.out.println("limit ==" + limit);
				limit = n + 2;
					if (limit > 50){
						throw new Exception("Error: Infinite loop detected.");
					} 
			}
		}catch (Exception e){
						System.out.print(e.getMessage());
						break;
		 }	 
		break;
		
		//Run loop #4
		case 4:
			number = 10;
				while (number > 0){
				   number = number - 2;
						if(number == 4)
	    				break; //break what?
		 			System.out.println(number);
				}
			System.out.println("The end.");
		break;
			
		//Run loop #5
		case 5:
			number = 10;
			while (number > 0){
   			number = number - 2;
					if(number == 4)
	   				 continue; //continue what?
	 		System.out.println(number);
			}
			System.out.println("The end.");
		break;
		}
	}
}
	
