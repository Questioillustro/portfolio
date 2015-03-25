/*
<package>
	JDBC Database
<.package>
<description>
    Class for database interaction using JDBC
<.description>
<keywords>
	jdbc, database, sql
<.keywords>
*/

import java.sql.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

class DataBase
{
   private Connection conn;
   private Statement stat;
	
	//Constructor
	public DataBase(String pfile) throws SQLException, IOException
	{
		conn = getConnection(pfile);
		stat = conn.createStatement();	
	}
		
	//Creates a connection from the properties file for the database
	public Connection getConnection(String pfile)
           throws SQLException, IOException
   {
 	  Properties props   = new Properties();
	  FileInputStream in = new FileInputStream(pfile);
	  
	  props.load(in);
     in.close();

     String drivers = props.getProperty("jdbc.drivers");
     
	  if (drivers != null)
     		System.setProperty("jdbc.drivers", drivers);
   
	  String url = props.getProperty("jdbc.url");
     String username = props.getProperty("jdbc.username");
     String password = props.getProperty("jdbc.password");

     return
  		   DriverManager.getConnection(url, username, password);
   }//end getConnection
	
	//Adds a table to the database
	public void createTable(String tName, String fields) throws SQLException
	{
		String cmd = "";
		removeTable(tName);

      cmd = "CREATE TABLE " + tName + " (" + fields + ")";

      stat = conn.createStatement();
		stat.execute(cmd);

    }//end CreateTable
	 
	 //Delets a table from the database
	 public void removeTable(String tName) throws SQLException
	 {
	 	String cmd = "";
		try
		{
			cmd = "DROP TABLE " + tName;
			stat = conn.createStatement();
			stat.execute(cmd);
      }
		catch ( SQLException e)
		{
		
		}
	 }
	 	
	 //Recieves strings for table name, fields, and values to be inserted
	 //A query statement is generated from the passed strings and executes
	 public void insertDataToTable(String tName, String fields, String values)
	 {
	    String query;
 	    ResultSet result = null;

       try
		 {
			query = "INSERT INTO " + tName + "(" + fields + ") VALUES " + "(" + values + ")";
	
			int i = stat.executeUpdate (query);
		 }
		 catch ( SQLException e)
		 {

			   System.out.println("in load table  -->"+ e.toString());

			   if (e.toString().toLowerCase().endsWith(
											"no resultset was produced"))

					System.out.println( "Data Was inserted correctly!!!!");

			   else

					System.out.println( "Error in Inserting data!!!!!");
       }
    }  // end insertDataToTable

	 //Recieves a query, executes, and prints the results
	 public void executeQuery(String query, int fieldNum)
	 {
	 	System.out.println("*** Result for the following query ***\n" + query + "\n");
		
	 	try
		{
	 		ResultSet result = stat.executeQuery(query);
			while (result.next())
			{
				for(int i = 1; i <= fieldNum; i++)
				{
					System.out.print(result.getString(i) + "\t");
				}
				System.out.print("\n");
			}
		  System.out.println();
		}
	   catch ( SQLException e)
		{
			System.out.println("Error with query");
		}

	 }
	
	//Outputs meta data for the table
	public void showTablesInfo(String tableName)
   {
   	try
      {
			Statement stat2 = conn.createStatement();
      	ResultSet rs = stat2.executeQuery("SELECT * FROM " + tableName);
      	ResultSetMetaData rsmd = rs.getMetaData();

      	System.out.println("Number of columns in " + tableName + ":"
      	                     +rsmd.getColumnCount());
      	for (int i = 1; i <= rsmd.getColumnCount(); i++)
      	{
         	String columnName = rsmd.getColumnLabel(i);
         	int columnWidth  = rsmd.getColumnDisplaySize(i);


         	System.out.print( "Column Name: " + columnName );
            System.out.println( "\t Column Width: " + columnWidth );

         }
      	rs.close();
		}
      catch(SQLException e)
      {
		  JOptionPane.showMessageDialog(null, "in showTableInfo --> "+e);
	  }
	  System.out.println();
   }//End showTablesInfo	 
	 
	 //Closes the database
	 public void close() throws SQLException, IOException
	 {
		 stat.close();
		 conn.close();
    }
}	 

		