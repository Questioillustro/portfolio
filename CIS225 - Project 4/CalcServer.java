/*
<package>
	Calculator Server
<.package>
<description>
	Server for the Calculator Server application, establishes connections with CalcClients, receives requests, responds with results
<.description>
<keywords>
	server, socket, io, threads
<.keywords>
*/

import java.io.*;
import java.net.*;
import java.util.*;
import java.io.EOFException;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

public class CalcServer extends JFrame
{
   private Socket connectToClient;
	private JTextArea servWin = new JTextArea();
	
	 	public static void main(String[] args) 
	 	{
  			new CalcServer();
   	}
		
	public CalcServer()
	{
		//Create GUI
		setLayout(new BorderLayout());
      add(new JScrollPane(servWin), BorderLayout.CENTER);
      setTitle("Server");
      setSize(500, 300);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
		
		int clientID = 1;
		
		try
		{
			//Create socket for connection
			ServerSocket socket = new ServerSocket(8000);
			servWin.append("Server started at " + new Date() + '\n');
			
   		//Primary loop for input/output      
			boolean cont = true;
         while(cont == true)
         {	
	   		//Listen for client connection
				connectToClient = socket.accept();	
				//Output connection information in server GUI
				servWin.append("\nNew connection established at " + new Date() +
								"\nUser UserID: " + clientID +
								"\nUser IP: " + connectToClient.getInetAddress());
				
				//Create new thread
				ProcessNewTask newTask = new ProcessNewTask(connectToClient, clientID);
				
				//Start new thread
				new Thread(newTask).start();
				
				clientID++;
			}//E - while{}
		}//E - try{}
	   catch(IOException ex)
		{
  		    System.err.println(ex);
      }	
	}//E - CalcServer	
	
//THREAD task method, establishes/processes each client	
class ProcessNewTask implements Runnable
{
	private Socket serverSock;
	private int clientID;
	String lineIn;
	
	//Constructor for the thread task
	public ProcessNewTask(Socket socket, int id)
	{
		serverSock = socket;
		clientID = id;
	}
	
	//Contains connectivity and processing code
	public void run()
	{
		try
		{
			//Establish INPUT stream from client
	      BufferedReader fromClient = new BufferedReader(new InputStreamReader(
							  					 serverSock.getInputStream()));
			//Establish OUTPUT stream to client
			PrintWriter toClient = new PrintWriter(
   		               	     serverSock.getOutputStream(), true);		
		
			//Primary COMMUNICATION loop
			while(true)
			{	
				//Recieves input from client
				lineIn = fromClient.readLine();
			
				//Calculates result and sends to client			
				toClient.println(processInput(lineIn)); 
			  
			 	//Output to server window  
				servWin.append("\n" + lineIn + " : recieved from ClientID: " + clientID);
				servWin.append("\nResult: " + processInput(lineIn) + " returned to client " + clientID); 
				
			}//E - while{}
		}//E - try[]
      catch(IOException e) 
		{
        servWin.append("\nConnection Error: ClientID = " + clientID);
		}
	}//E - run()
		
		//Method for processing fraction result
		private String processInput( String s)
		{
			Fraction result;
		   fractionCalculator fc1 = new fractionCalculator(s);	//Builds fractions and sets operation
			
			//A double-zero is returned from getResult if there is an input error			
			if (fc1.getResult().equals("00"))
			{	
				return "Input Error";
			} 
			else 
			{	
				return fc1.getResult(); //Performs operation and outputs the result
  	  	   }	
		}//E - processInput()	
	}//E - ProcessNewTask{}
}//End Class			  				