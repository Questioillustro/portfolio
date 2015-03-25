//***********************************************************************
//Program: TestDB2_b.java
//**********************************************************************
import java.sql.*;
import java.io.*;
import java.util.*;

/**
   This program tests that the database and the JDBC
   driver are correctly configured.
*/
class TestDB2_b
{
   Connection conn;
   Statement stat;




    public TestDB2_b(String pfile) throws SQLException, IOException
    {
		//constructor
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

	  //String pfile = "dbprops.dat";  //database.properties file
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
		//	stat = conn.createStatement();
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


       // stat = conn.createStatement();
		stat.execute(cmd);

    }// end CreateTable


/**
    Insert data to the table the table  Person using executeUpdate statement
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
		   " ('Barak Obama', 45, 'Washington','DC'  )  " ;

		i =stat.executeUpdate (query);
		System.out.println(i);

				query = "INSERT INTO Person(Name, Age, City, State) VALUES " +
			 " ('Hilery  Clinton', 59, 'Rochester','NY'  )  ";

   	//	i=stat.executeUpdate (query);
		// System.out.println(i);
	   ResultSet rs1, rs2;
      rs1= stat.executeQuery(query);
   
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


    public void displayTable() throws SQLException
    {

		 System.out.println("----displaying the records----");
		 String query = "SELECT * FROM Person";
		 ResultSet result= stat.executeQuery(query);

		while ( result.next())
		   System.out.println(result.getString(1)+" "+ result.getInt(2)+" "+
		                     result.getString(3)+" "+result.getString(4));

		 result.close();


	}


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
		 TestDB2_b db = new TestDB2_b(pfile);

		  db.createTable();
		  db.insertDataToTable();
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
	  catch (Exception ex)
	  {
	  		 ex.printStackTrace();
	  }
   }// end main


}// end class TestDB2_b



