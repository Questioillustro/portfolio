//***********************************************************************
//Program:TestDB.java
//***********************************************************************
/**
   @author Cay Horstmann
*/

import java.sql.*;
import java.io.*;
import java.util.*;

/**
   This program tests that the database and the JDBC
   driver are correctly configured.
*/
class TestDB
{
   public static void main (String args[])
   {

      try
      {
         Connection conn = getConnection();
         Statement stat = conn.createStatement();


         //stat.execute("DROP TABLE Greetings");

         stat.execute("CREATE TABLE Greetings (Name CHAR(20))");
         stat.execute(
            "INSERT INTO Greetings VALUES ('Hello, World!')");

         ResultSet result
            = stat.executeQuery("SELECT * FROM Greetings");
         result.next();
         System.out.println(result.getString(1));
         result.close();

         stat.execute("DROP TABLE Greetings");



         stat.close();
         conn.close();
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
   }// end Main



   /**
      Gets a connection from the properties specified
      in the file database.properties
      @return the database connection
   */
   public static Connection getConnection()
      throws SQLException, IOException
   {
      Properties props = new Properties();
      //FileInputStream in
      //  = new FileInputStream("database.properties");// change this to
                                                    //the properties file
      FileInputStream in
	           = new FileInputStream("dbprops.dat");// change this to
                                                    //the properties file
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
}// end class TestDB1



