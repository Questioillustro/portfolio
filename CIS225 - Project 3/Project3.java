/*
<package>
	Database Interaction - Project 1
<.package>
<description>
    First database application! Basic table creation and interaction
<.description>
<keywords>
	database, sql, jdbc
<.keywords>
*/

import java.io.*;
import java.sql.*;

class Project3
{
	public static void main (String args[])
	{
		FileReader theFile;
  	   BufferedReader fileIn = null;
  		String oneLine;
		String splitter[];
		
		try
	   {
		 //Create database connection
		 DataBase db = new DataBase("dbprops2.dat");
		  
		  //Create the two tables
		  db.createTable("Employees","EmployeeID INTEGER, " +
		  					  "FirstName VARCHAR(10), " +
							  "LastName VARCHAR(10), " +
							  "Department VARCHAR(20), " +
							  "Hours INTEGER");
							  
		  db.createTable("Location","Department VARCHAR(20), " +
		                 "City VARCHAR(20), " +
							  "State CHAR(2)");	
		
//**** Employee Insertion Code***********

		  //Set up the 'Employees' file for data input
		  theFile = new FileReader( "Table1.txt" );
        fileIn = new BufferedReader( theFile );
				
		  String values = "";
		  String tName = "Employees";
		  String fields = "EmployeeID, FirstName, LastName, Department, Hours";
            	
	     //Loop to read in and insert data to the 'Employees' table
		  while( ( oneLine = fileIn.readLine( ) ) != null )
        {
	   		splitter = oneLine.split(",");
						
				for (int i = 0; i < splitter.length; i++)
				{
					values += splitter[i];
					if(i != splitter.length - 1)
						values += ",";
				}

				db.insertDataToTable(tName, fields, values);
				values = ""; //Reset data string for next line of input
      	}
			
//******** Location Insertion Code ***********
			
		  //Insert data from Table2.txt into the 'Location' table
  		  theFile = new FileReader( "Table2.txt" );
        fileIn = new BufferedReader( theFile );

		  values = "";
		  tName = "Location";
		  fields = "Department, City, State";
		  
		  //Loop to read in and insert data to the 'Location' table
		  while( ( oneLine = fileIn.readLine( ) ) != null )
        {
	   		splitter = oneLine.split(",");
						
				for (int i = 0; i < splitter.length; i++)
				{
					values += splitter[i];
					if(i != splitter.length - 1)
						values += ",";
				}

				db.insertDataToTable(tName, fields, values);
				values = ""; //Reset data string for next line of input
      	}

//********** Output for the inserted data *********
			db.showTablesInfo("Employees");
			db.showTablesInfo("Location");
			db.executeQuery("SELECT * FROM Employees",5);
			db.executeQuery("SELECT * FROM Location",3);
			db.executeQuery("SELECT * FROM Employees WHERE Employees.Department = " +
								 "'Computer';",5);	
			db.executeQuery("SELECT * FROM Employees WHERE Employees.Department = " +
								 "'Computer' and Employees.Hours < 40;",5);
			db.executeQuery("SELECT Employees.EmployeeID, Employees.FirstName, " +
								 "Employees.LastName, Employees.Hours, Location.City FROM " +
								 "Employees, Location WHERE Employees.Department = " +
								 "Location.Department AND Location.City = 'Brighton';", 5);		
			db.close();	//close database						  				  
	  }
	  //Catch and print any exceptions
	  catch (SQLException ex)
	  {
		 while (ex != null)
		 {
			ex.printStackTrace();
			ex = ex.getNextException();
		 }
	  }
	  catch (IOException ex)
	  {
		 ex.printStackTrace();
	  }
	  catch (Exception ex)
	  {
	  		 ex.printStackTrace();
	  }
		
	}//end main
}//end class
		