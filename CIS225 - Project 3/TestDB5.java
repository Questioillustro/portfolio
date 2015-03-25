//***********************************************************************
//Program:TestDB5.java
//***********************************************************************


import java.sql.*;
import java.io.*;
import java.util.*;

/**
   This program tests that the database and the JDBC
   driver are correctly configured, Creating queries by Joining Tables
*/
class TestDB5
{
   Connection conn;
   Statement stat;




    public TestDB5(String pfile) throws SQLException, IOException
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

    public void displayTable() throws SQLException
    {


// query 1
 System.out.println();


		 System.out.println("--query 1----displaying records from Books and Prublishers Tables----");
		 String query = "SELECT distinct Books.Title, Books.ISBN, Books.Publisher_Id, Publishers.Publisher_Id, "+
		                "Publishers.Name " +
		                 "FROM Books, Publishers " +
		                 "WHERE Books.Publisher_Id <='00788' ";
		 ResultSet result= stat.executeQuery(query);

		while ( result.next())
		   System.out.println(result.getString(1)+" "+ result.getString(2)+" "+
		                     result.getString(3)+" "+result.getString(4)+" "+result.getString(5));


// query 2
         System.out.println();

         System.out.println("--query 2----displaying records from Books and " +
                                    "Publishers Tables 3 rd  time ----");
		   query = "SELECT distinct Books.Title, Books.ISBN, Books.Publisher_Id, Publishers.Publisher_Id, "+
		           "Publishers.Name " +
                 "FROM Books, Publishers " +
                 "WHERE Books.Publisher_Id <='00788' AND " +
                 "Books.Publisher_Id = Publishers.Publisher_Id ";
					  // AND " +"Publishers.Name = 'Osborne/McGraw-Hill' ";

		 result= stat.executeQuery(query);

		while ( result.next())
		   System.out.println(result.getString(1)+" "+ result.getString(2)+" "+
		                     result.getString(3)+" "+result.getString(4)+" "+result.getString(5));

// query 3
         System.out.println();

         System.out.println("--query 3----displaying records from Books and " +
                                    "Publishers Tables 3 rd  time ----");
		 query = "SELECT distinct  Books.ISBN, Books.Publisher_Id,"+
		                                "Publishers.Name " +
                 "FROM Books, Publishers " +
                 "WHERE Books.Publisher_Id <='00788' AND " +
                 "Books.Publisher_Id = Publishers.Publisher_Id "+
					  "AND " +"Publishers.Name = 'Osborne/McGraw-Hill' ";

		 result= stat.executeQuery(query);

		while ( result.next())
		   System.out.println(result.getString(1)+" "+ result.getString(2)+" "+
		                     result.getString(3));

//  Query 4

         System.out.println();

         System.out.println("--query 4----displaying records from Books and " +
                                    "Publishers Tables 4 th time ----");
		 query = "SELECT distinct *                FROM Books, Publishers " +
                 "WHERE Books.Publisher_Id ='00788' AND " +
                 "Books.Publisher_Id = Publishers.Publisher_Id and "+
                 "Publishers.Name = 'Wiley' ";
		 result= stat.executeQuery(query);

		while ( result.next())
		   System.out.println(result.getString(1)+" "+ result.getString(2)+" "+
		   					  result.getString(3)+" "+ result.getString(4)+" "+
		   					  result.getString(5)+" "+ result.getString(6)+" "+
		                     result.getString(7)+" "+result.getString(8));



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
		 TestDB5 db = new TestDB5(pfile);

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


}// end class TestDB2



