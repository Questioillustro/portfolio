//************************************************************************
//program : TestDB4.java
//************************************************************************
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
   This program use meta Data to get the table name, and displaying the
   columns for each table, and the records for all tables,
   the program is using a collection to store the table information.
   this program allows you to display any records for any table
   ( without being specific on the table information)
   */
class TestDB4
{
   private Connection conn;
   private Statement stat;
   //private ArrayList fields;




    public TestDB4(String pfile) throws SQLException, IOException
    {
		//constructor
		conn = getConnection(pfile);

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
	//inserted only the first record
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

    }  // end insertDataToTable



/**
   Get the name of the tables
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
    {     String tableName = mrs.getString(3);
          System.out.println("\n\n Table Name  " +tableName);
          showTablesInfo( tableName);

	}
      	mrs.close();

}


/**
  Using Meta Data to display the information about each table, like the coulmn name,
  column size, column type
*/
public void showTablesInfo(String tableName)
   {
    try
     {
		//create a new arraylist for each table
		ArrayList<String> fields = null;
		fields = new ArrayList<String>();

		Statement stat2 = conn.createStatement();
      	ResultSet rs = stat2.executeQuery("SELECT * FROM " + tableName);
      	ResultSetMetaData rsmd = rs.getMetaData();

      	System.out.println("number of columns in  " + tableName + " table is  "
      	                     +rsmd.getColumnCount());


         	for (int i = 1; i <= rsmd.getColumnCount(); i++)
      	{

         	String columnName = rsmd.getColumnLabel(i);
         	int columnWidth  = rsmd.getColumnDisplaySize(i);

         	System.out.print( "   column Name  " + " " +columnName );
            System.out.println( " \tcolumn Width  " + " " +columnWidth );

            //add to the arrayList the column lable of the current table
             fields.add(columnName);
        }

        //display the records of the Person table
       // if ( tableName.equals("Person")  )
        {

		    displayTableData(tableName, fields);

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

    public void displayTableData(String tableName, ArrayList<String> fields)
    {

      try
      {
		 Statement stat3 = conn.createStatement();
		 System.out.println("\n----displaying the records for " +
		                     tableName+" Table ----");

		 String query = "SELECT * FROM " + tableName ;
		 ResultSet result= stat3.executeQuery(query);

         System.out.println("Printing using ArrayList ");

		 while ( result.next())
		 {
		  // System.out.println(result.getString(1)+" "+ result.getInt(2)+" "+
          //                     result.getString(3)+" "+result.getString(4));

           String fieldName  = "";
           for (int i = 1; i <= fields.size(); i++)
		   {
              fieldName = fieldName + " " + result.getString(i) ;
	       }
	       System.out.println(fieldName + " ");
         }
         System.out.println();
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
		 TestDB4 db = new TestDB4(pfile);

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


}// end class TestDB4


