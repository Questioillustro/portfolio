/*
<package>
    Encryptor
<.package>
<description>
    Starts the server for the Encrypted chat application
<.description>
<keywords>
    socket, io, server, threads
<.keywords>
*/

import java.io.*;
import java.net.*;
import java.util.*;
import java.io.EOFException;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

public class EncryptServer extends JFrame
{
   private Socket connectToClient;
	private BufferedReader fromClient[] = new BufferedReader[10];
	private PrintWriter toClient[] = new PrintWriter[10];
	private JTextArea servWin = new JTextArea();
	private String clientList[] = new String[10];
	private int numClients = 0, temp;
	private boolean clientIDs[] = new boolean[10];

	
	public static void main(String[] args)
	{
		new EncryptServer();
	}
		
	public EncryptServer()
	{
		//Create GUI
		setLayout(new BorderLayout());
      add(new JScrollPane(servWin), BorderLayout.CENTER);
      setTitle("Server");
      setSize(500, 300);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
		
		try
		{
			//Create socket for connection
			ServerSocket socket = new ServerSocket(8000);
			servWin.append("Encryptor Server started at " + new Date() + '\n');
			
   		//Primary loop for input/output      
			boolean cont = true;
         while(cont == true)
         {	
	   		//Listen for client connection
				connectToClient = socket.accept();
				
				temp = getID();
				
				//I/O stream
				fromClient[temp] = new BufferedReader(new InputStreamReader(
							  			connectToClient.getInputStream()));
				toClient[temp] = new PrintWriter(
   		               	     connectToClient.getOutputStream(), true);	
				
				//Set client username recieved from client						  												 
				clientList[temp] = fromClient[temp].readLine();
				
				//Confirm client username sent to users
				for(int i = 0; i < numClients; i++)
				{
					toClient[i].println("User " + clientList[numClients] + " connected.");
					toClient[numClients].println("User " + clientList[i] + " connected.");
				}
				
				//Output connection information in server GUI
				servWin.append("\nNew connection established at " + new Date() +
								"\nUser UserID: " + clientList[temp] +
								"\nUser IP: " + connectToClient.getInetAddress());				
				
								
				//Create new thread
				ProcessNewTask newTask = new ProcessNewTask(temp);
				
				//Start new thread
				new Thread(newTask).start();
				
				numClients++;

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
	private int ID;
	private String lineIn, lineOut;
	
	//Constructor for the thread task
	public ProcessNewTask(int n)
	{
		ID = n;
	}
	
	//Contains connectivity and processing code
	public void run()
	{
		try
		{
			toClient[ID].println("Connection accepted!");
			toClient[ID].println("Welcome " + clientList[ID]);										  
		
			//Primary COMMUNICATION loop
			while(true)
			{	
				//Recieves input from client
				lineIn = fromClient[ID].readLine();
				
				processInput(lineIn);
			
				//Calculates result and sends to clients			
				for(int i = 0; i < numClients; i++)
				{
					toClient[i].println(clientList[ID] + ": " + lineOut); 
					toClient[i].flush();
				}
			  
			 	//Output to server window  
				servWin.append("\nMessage recieved from ClientID: " + clientList[ID] +
									"- " + lineIn);
				servWin.append("\nResult returned to client " + clientList[ID] +
									"- " + lineOut); 
				
				lineOut = "";
				
			}//E - while{}
			
		}//E - try[]
      catch(IOException e) 
		{
        servWin.append("\nConnection Error: ClientID = " + clientList[ID]);
		  close();
		}
	
	}//E - run()
		
		//Disconnects the client
		private void close()
		{
			try
			{
				 for(int i = 0; i < numClients; i++)
				 {
					toClient[i].println("User " + clientList[ID] + " disconnected.");
				 }
				 
		 		 toClient[ID].close();
		  		 fromClient[ID].close();
		  		 servWin.append("\nConnection to " + clientList[ID] + " closed");
				 clientIDs[ID] = false;
				 clientList[ID] = "";
				 numClients--;
				 
			}catch (IOException e){
				 servWin.append("\nError closing connection");
			}
		}
			
		//Method for processing fraction result
		private String processInput( String s)
		{
			lineOut = s;
			return lineOut;
		}//E - processInput()	
	}//E - ProcessNewTask{}
	
		private int getID( )
		{
			for(int i = 0; i < 10; i++)
			{
				if(!clientIDs[i])
				{
					clientIDs[i] = true;
					return i;
				}
			}
			return 0;
		}
}//End Class			  				