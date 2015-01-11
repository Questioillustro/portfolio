/* 
	Program: ATemperatureReading
	Author: Stephen Brewster
	Date: 9/10/10
	Purpose: This program illustrates the creation of classes and methods
*/

import java.util.*;	//Imports the necessary class for user input functions

class ATemperatureReading{

	private String time, temperature;	//Declaring strings for time and temperature data
	Scanner scanner = new Scanner(System.in);	//Creating user input object
	
	public void setTime(String t){
		time = t;	//Method for assigning default value to time
	}
	
	public void setTemperature(String temp){
		temperature  = temp;	//method for assigning default value to temperature
	}
	
	public String getTime(){
		System.out.print("Enter the time(HH:MM): ");	//Outputs instructions to user
		time = scanner.next();	//assigns user input to String time
		return time;	//returns user-inputed string
	}
	
	public String getTemperature(){
		System.out.print("Enter the temperature(°F): ");	//Outputs instructions to user
		temperature = scanner.next();	//assigns user input to String temperature
		return temperature;	//returns user-inputed string
	}
}
