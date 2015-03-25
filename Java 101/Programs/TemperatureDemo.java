/*
	Program: TemperatureDemo
	Author: Stephen Brewster
	Date: 9/10/10
	Purpose: This program retrieves time and temperature data from the ATemperatureReading .class file
*/

import javax.swing.*;

class TemperatureDemo{

	public static void main(String[] args){
	
		ATemperatureReading t1 = new ATemperatureReading(); //Creates an instance of the ATemperatureReading class
			
		t1.setTime("12:34");  		//Passes a string to the setTime method
		t1.setTemperature("75");	//Passes a string to the setTemperature method
		
 	  	System.out.println("Time: " + t1.getTime());						//Prints user-inputed time data
		System.out.println("Temperature: " + t1.getTemperature() + "°");	//Prints user-inputed temperature data
		
		
	}

}