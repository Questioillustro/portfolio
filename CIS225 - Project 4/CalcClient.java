/*
<package>
	Calculator Server
<.package>
<description>
    Client for the Calculator Server, connects to the server and sends calculation requests, receives results
<.description>
<keywords>
	client, socket, io
<.keywords>
*/

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

import java.io.EOFException;
import java.io.IOException;

public class CalcClient
{
	private Socket connectToServer;
	private BufferedReader fromServer;
	private PrintWriter toServer;
	private String calcLine = "";
	private boolean flag = false;
	
	public CalcClient()
   {
		try
		{
		//Establish connection to server
		connectToServer = new Socket("localhost",8000);
		
		//Establish input from server
		fromServer = new BufferedReader(new InputStreamReader
                  (connectToServer.getInputStream()));
		//Establish output to server
		toServer = new PrintWriter
					  (connectToServer.getOutputStream(), true);
	}//End try
   catch(IOException e1)
   {
      System.err.println("Client died with exception: " + e1.toString());
      System.exit(0);
   }

}//E - CalcClient()

	public String setCalcLine ( String s )
	{
		try
		{
			toServer.println(s);
			toServer.flush();
			return fromServer.readLine();
		}
		catch(IOException e1)
		{
			return "Error";
		}
	}
	
	public void close()
	{
		try
		{
			toServer.close();    //Close output stream
			fromServer.close();  // Close input stream
		   connectToServer.close(); //Close socket
		} 
      catch (IOException ioException)
      {
			ioException.printStackTrace();
	   }
	}	
}//E - CalcClient Class

