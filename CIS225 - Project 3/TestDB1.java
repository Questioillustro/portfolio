///***********************************************************************
//Program:TestDB1.java
//***********************************************************************

import java.sql.*;
import java.io.*;
import java.util.*;

/**
   This program tests that the database and the JDBC
   driver are correctly configured.
*/
class TestDB1
{
   Connection conn;
   Statement stat;


   /**
      constructor
      the constructor will set the connection
      and will create a statement with the connection
   */

    public TestDB1(String pfile) throws SQLException, IOException
    {

		conn = getConnection(pfile);
		stat = conn.createStatement();
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
      create a Greetings,
      if the table already exist, erase the table and continue to create a new one
      this method uses SQLException to determine if the table does not exist
   */
   public void createTable() throws SQLException
	{
		String cmd;
		try
		{
			cmd = "DROP TABLE Greetings";
			stat = conn.createStatement();
			stat.execute(cmd);
        }
		catch ( SQLException e)
		{
			System.out.println ( "Table Greeting did not exist-->"+
			                     "continue to create Greetings table");
		}
        cmd ="CREATE TABLE Greetings (Name CHAR(20))";
		stat.execute(cmd);

		cmd = "INSERT INTO Greetings VALUES ('Hello, World!')";
		stat = conn.createStatement();
		stat.execute(cmd);

		// create another statement
		cmd = "INSERT INTO Greetings VALUES ('Hello, class!')";
		stat = conn.createStatement();
		stat.execute(cmd);

    }

    public void displayTable() throws SQLException
    {
		 System.out.println("displaying the table");
		 String query = "SELECT * FROM Greetings";
		 ResultSet result= stat.executeQuery(query);

		while ( result.next())
		  System.out.println(result.getString(1));

		 result.close();


	}

    /**
      close the statement and the connection
    */
	public void close() throws SQLException, IOException
	{
		 stat.close();
		 conn.close();
    }


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
		 TestDB1 db = new TestDB1(pfile);

		  //db.setConnection(pfile);
		  db.createTable();
		  db.displayTable();
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
   }// end main


}// end class TestDB1



