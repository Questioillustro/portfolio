/* 
	Program: Quizmotron
	Author: Stephen Brewster
	Date: 10/4/2010
	Purpose: This program will accept a user String indicating the order
				that 5 questions will be asked and provide a grade
	Runs with file: QuestionSet
*/

import java.util.*;

class Quizmotron{

	public static void main ( String[] args ){
		Scanner scanner = new Scanner(System.in);
			boolean qA;
			int grade=0,cnt=1;
			String order, chk;
		QuestionSet q1 = new QuestionSet();
		
		order = q1.getOrder();
		
while (cnt < 6){
		chk = order.substring(cnt-1,cnt);
					
		//Calling Question 1 Method
	if (chk.equals("1")){
		qA = q1.qOne();
		if (qA)
		grade += 20;
	} 
				
		//Calling Question 2 Method
	if (chk.equals("2")){	
		qA = q1.qTwo();
		if (qA)
		grade += 20; 
	}
		
		//Calling Question 3 Method
	if (chk.equals("3")){	
		qA = q1.qThree();
		if (qA)
		grade += 20;
	}
	
		//Calling Question 4 Method
	if (chk.equals("4")){	
		qA = q1.qFour();
		if (qA)
		grade += 20;
	}
		
		//Calling Question 5 method
	if (chk.equals("5")){
		qA = q1.qFive();
		if (qA)
		grade += 20;
	}
	cnt += 1;
}
		
		//Computing final grade
		switch (grade){
			case 0: System.out.println("\nYou didn't get any right, study harder.");
						break;
			
			case 20: System.out.println("\nYou missed 4 questions. Your grade is a 20, which is an F.");
						break;

			case 40: System.out.println("\nYou missed 3 questions. Your grade is a 40, which is an F.");
						break;
				
			case 60: System.out.println("\nYou missed 2 questions. Your grade is a 60, lets call it a D-.");
						break;
						
			case 80: System.out.println("\nYou missed 1 question. Your grade is an 80, we'll make that a B.");
						break;
						
			case 100: System.out.println("\nYou recieve a 100 for this quiz. Good job sport.");
						break;
			}
	}
}