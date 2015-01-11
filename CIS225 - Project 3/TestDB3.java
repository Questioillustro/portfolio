//***********************************************************************
//Program:TestDB3.java
//***********************************************************************
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
   This program use meta Data to get the table name and display the columns
   for each table, and the records for one table
   table
*/
class TestDB3
{
   Connection conn;
   Statement stat;




    public TestDB3(String pfile) throws SQLException, IOException
    {
		//constructor
		conn = getConnection(pfile);
		//stat = conn.createStatement();
		//stat2 = conn.createStatement();
	}
   /**
      Gets a connection from the properties specified
      in the file database.properties
      @return the database connection
   */
   public Connection getConnection(String pfile)
         throws SQLException, IOException
   {

	  //String pfile = "dbprops.dat";
      Properties props = new Properties();

      FileInputStream in
	           = new FileInputStream(pfile);
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
   }

  /**
    Create the table  Person
  */
   public void createTable() throws SQLException
	{
		String cmd;
		try
		{
			cmd = "DROP TABLE Person";
			stat = conn.createStatement();
			stat.execute(cmd);
        }
		catch ( SQLException e)
		{
			System.out.println ( "Table Person did not exist-->"+
			                     "continue to create Person table");
		}

        cmd ="CREATE TABLE Person (Name VARCHAR(30) ,"+
                                   "Age INTEGER,"+
                                   "City VARCHAR(20),"+
                                   "State CHAR(2))";


        stat = conn.createStatement();
		stat.execute(cmd);

    }// end CreateTable


/**
    Insert data to the table the table  Person
  */

public void insertDataToTable()
{
	String query;
    ResultSet result = null;

	 try
	 {

		query = "INSERT INTO Person(Name, Age, City, State) VALUES " +

		   " ('Steve Jones', 20, 'Rochester','NY'  )  " ;


		int i = stat.executeUpdate (query);
		System.out.println(i);


		query = "INSERT INTO Person(Name, Age, City, State) VALUES " +
		   " ('Goerge Bush', 60, 'Washington','DC'  )  " ;

		i =stat.executeUpdate (query);
		System.out.println(i);
				query = "INSERT INTO Person(Name, Age, City, State) VALUES " +
			 " ('Hileray Clinton', 59, 'Rochester','NY'  )  ";

		i=stat.executeUpdate (query);
		System.out.println(i);



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

 }  // end insertToTable



/**
	Using MetaData to get the tables Name
*/


public void getTableNames() throws SQLException
 {
	DatabaseMetaData meta;
	meta = conn.getMetaData();

	ResultSet mrs = meta.getTables(null, null, null,
                                  new String[] { "TABLE" });
      	System.out.println( "\nThe list of tables in this file" );
      	System.out.println( "-------------------------------" );
      	while (mrs.next())
      	{      String tableName = mrs.getString(3);
      	       System.out.println("\n\n Table Name  " +tableName);
      	       showTablesInfo( tableName);

	    }
      	mrs.close();

}



/**
  Using Meta Data to display the information about each table, like the column
  name, column size, column type
*/
public void showTablesInfo(String tableName)
   {
    try
     {
		Statement stat2 = conn.createStatement();
      	ResultSet rs = stat2.executeQuery("SELECT * FROM " + tableName);
      	ResultSetMetaData rsmd = rs.getMetaData();

      	System.out.println("   number of columns in  " + tableName + " table is  "
      	                     +rsmd.getColumnCount());
      	for (int i = 1; i <= rsmd.getColumnCount(); i++)
      	{
         	String columnName = rsmd.getColumnLabel(i);
         	int columnWidth  = rsmd.getColumnDisplaySize(i);


         	System.out.print( "   column Name  " + " " +columnName );
            System.out.println( " \t column Width  " + " " +columnWidth );

        }

        //display the records of the Person table
        if ( tableName.equals("Person")  )
        {

		    displayTableData(tableName);

	     }

      	rs.close();

      }
      catch(SQLException e)
      {
		  JOptionPane.showMessageDialog(null, "in showTableInfo --> "+e);
	  }

   }

   /**
      display the records of the table
   */


    public void displayTableData(String tableName)
    {

      try
      {
		 Statement stat3 = conn.createStatement();
		 System.out.println("\n----displaying the records for " +tableName+
		                                                        " Table ----");
		 String query = "SELECT * FROM " + tableName ;
		 ResultSet result= stat3.executeQuery(query);

		 while ( result.next())
		   System.out.println(result.getString(1)+" "+ result.getInt(2)+" "+
		                     result.getString(3)+" "+result.getString(4));

		 result.close();
      }
      catch(SQLException e)
      {
		 JOptionPane.showMessageDialog(null, "in displayTableData --> "+e);
	  }

    }

public void close() throws SQLException, IOException
	{
		 stat.close();
		 conn.close();
    }

//*************************************************************






 public static void main(String args[])
   {
	  String pfile = "dbprops.dat";



	  if (args.length > 0)
	 {
		   File f = new File(args[0]);
		   if (f.exists())
		   {
			  pfile = args[0];
		   }
	 }


	  try
	  {
		 TestDB3 db = new TestDB3(pfile);

		  db.createTable();
		  db.insertDataToTable();
	      db.getTableNames();
          db.close();

	  }
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
   }// end main


}// end class TestDB3



