/*
	This class works with file Quizmotron
*/

import java.util.*;

class QuestionSet{
	Scanner scanner = new Scanner(System.in);
	
	//Question 1
		public boolean qOne(){
		System.out.println("\n1. The data members of a class are declared outside of the class declaration.");
		System.out.print("Enter T/F: ");
			String aOne = scanner.next();
		if (aOne.equals("f") || aOne.equals("F"))
			return true;
			else 
			return false;
		}

	//Question 2
		public boolean qTwo(){
		System.out.println("\n2. While it is not a syntax error, it does violate the Java conventions to declare a method as 'private.'");
		System.out.print("Enter T/F: ");
			String aTwo = scanner.next();
		if (aTwo.equals("t") || aTwo.equals("T"))
			return true;
			else 
			return false;
		}

	//Question 3
		public boolean qThree(){
		System.out.println("\n3. Which statement about constructors is not true?");
		System.out.println("a) constructors do not return data and so the return type is always declared 'void.'");
		System.out.println("b) a programmer-defined class must always include a constructor method declaration.");
		System.out.println("c) constructor will typically use the accessibility modifier, 'private.'");
		System.out.println("d) all of the above are true");	
		System.out.print("Enter your answer: ");
			String aThree = scanner.next();
		if (aThree.equals("b") || aThree.equals("B"))
			return true;
			else
			return false;
		}
		
	//Question 4
		public boolean qFour(){
		System.out.println("\n4. A class declaration may have more than one constructor. Multiple constructors are called");
		System.out.println("a) overloaded");
		System.out.println("b) supercharged");
		System.out.println("c) uploaded");
		System.out.println("d) subjective");
		System.out.print("Enter your answer: ");	
			String aFour = scanner.next();
		if (aFour.equals("a") || aFour.equals("A"))
			return true;
			else
			return false;
		}

	//Question 5
		public boolean qFive(){
		System.out.println("\n5. Which of the following fall under the category, control statement?");
		System.out.println("a) selection statements");
		System.out.println("b) repetition statements");
		System.out.println("c) return statements");
		System.out.println("d) a and b, above");	
		System.out.print("Enter your answer: ");
			String aFive = scanner.next();
		if (aFive.equals("d") || aFive.equals("D"))
			return true;
			else
			return false;
		}

	//Obtaining Question order from user
		public String getOrder(){
		System.out.println("Enter the order in which you would like the questions given(without spaces). There are 5 Questions.");
		System.out.println("Example: entering '54321' will give the questions in reverse order.");
		System.out.print(":");
		String order = scanner.next();
		
	
	//Converting order string to int variables
		int o1 = Integer.parseInt(order.substring(0,1));
				int o2 = Integer.parseInt(order.substring(1,2));
					int o3 = Integer.parseInt(order.substring(2,3));
						int o4 = Integer.parseInt(order.substring(3,4));
							int o5 = Integer.parseInt(order.substring(4,5));
		
	//Input error correction for non-valid numbers or invalid length
		while (o1+o2+o3+o4+o5 != 15 || order.length() != 5){
		System.out.print("\nError: invalid composition, input must be numbers 1 to 5 and 5 characters long), please try again: ");
			order = scanner.next();
					o1 = Integer.parseInt(order.substring(0,1));
						o2 = Integer.parseInt(order.substring(1,2));
							o3 = Integer.parseInt(order.substring(2,3));
								o4 = Integer.parseInt(order.substring(3,4));
									o5 = Integer.parseInt(order.substring(4,5));
		}
			return order;
		}
}
		