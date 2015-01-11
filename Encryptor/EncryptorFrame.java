/*
<package>
	Encryptor
<.package>
<description>
    Creates the GUI for the Encryptor application
<.description>
<keywords>
    swing, gui interface
<.keywords>
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFileChooser.*;
import java.io.*;
import java.net.*;
import java.util.*;


import java.io.EOFException;
import java.io.IOException;

public class EncryptorFrame extends JFrame {
	private JTextField inPutWin = new JTextField();
	private JTextArea outPutWin = new JTextArea();
	private JScrollPane scrollerOut = new JScrollPane(outPutWin);
	private JButton encrypt = new JButton("Encrypt");
	private JButton decrypt = new JButton("Decrypt");
	private Encryptor en1 = new Encryptor();
   private JMenu fileMenu = new JMenu("File");
	private JMenu encryptMenu = new JMenu("Encryption");
   private JMenu helpMenu = new JMenu("Help");
	private JMenu connect = new JMenu("Connect");
	private JMenu server = new JMenu("Server");
	private JTextArea clientListWin = new JTextArea();	
	private JScrollPane userScroll = new JScrollPane(clientListWin);

public static void main (String[] args) {
	 EncryptorFrame frame = new EncryptorFrame();
    frame.setTitle("Encryptor by Stephen Brewster");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700,400);
    frame.setVisible(true);
}

	private encryptorClient ec;
		private Socket connectToServer;
		private BufferedReader fromServer;
		private PrintWriter toServer;
		private String clientList[] = new String[10];
		private int numClients = 0;
	
	public EncryptorFrame ( )
	{
    try {
	    // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }
		
				
		//Create Encryption submenu
		JRadioButtonMenuItem fib, xrated, basic, plat;
		encryptMenu.add(basic = new JRadioButtonMenuItem("Basic"));
		encryptMenu.add(fib = new JRadioButtonMenuItem("Fibonacci"));
		encryptMenu.add(xrated = new JRadioButtonMenuItem("X-Rated"));
		encryptMenu.add(plat = new JRadioButtonMenuItem("Platinum"));
			ButtonGroup encryptBTG = new ButtonGroup();
			encryptBTG.add(basic);
			encryptBTG.add(fib);
			encryptBTG.add(xrated);
			encryptBTG.add(plat);
		
//************* Main window creation **************************

		//USER DISPLAY PANE
		clientListWin.setEditable(false);
		JPanel p1 = new JPanel();
		p1.setBorder(BorderFactory.createTitledBorder ("Users"));
		p1.setLayout(new GridLayout(1,1));
		p1.setMinimumSize(new Dimension(100,100));		
		p1.add(userScroll);

		
		//OUTPUT WINDOW PANE
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1,1));
		p2.setBorder(BorderFactory.createTitledBorder("Chat Window"));
			outPutWin.setLineWrap(true);
			outPutWin.setWrapStyleWord(true);
		p2.add(scrollerOut);
		
		//INPUT PANE
		JPanel p3 = new JPanel();
		JButton sendButton = new JButton("Send");
		
		//Sets the ENTER button to send message line
		inPutWin.setAction(send);
		sendButton.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "SEND");
		sendButton.getActionMap().put("SEND", send); 
		
		p3.setLayout(new BoxLayout(p3, BoxLayout.LINE_AXIS));
		p3.add(Box.createHorizontalGlue());
		p3.setBorder(BorderFactory.createTitledBorder("Input"));
		p3.add(sendButton);
		p3.add(inPutWin);	
		sendButton.setPreferredSize(new Dimension(113,0));

		//Create panel
		add(p1, BorderLayout.WEST);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.PAGE_END);

		p3.setPreferredSize(new Dimension(200,50));		
		p1.setPreferredSize(new Dimension(120,100));
		sendButton.setMaximumSize(new Dimension(0,60));	
						
		//Create menu
	   JMenuBar jmenuBar = new JMenuBar();
       setJMenuBar(jmenuBar);
		 jmenuBar.add(fileMenu);
		 jmenuBar.add(encryptMenu);
		 jmenuBar.add(connect);	
		 jmenuBar.add(server);	
		 jmenuBar.add(helpMenu);
		 
		//Create file submenu
		JMenuItem txtImport;
		fileMenu.add(txtImport = new JMenuItem("Import .txt File"));
		
		//Create Connection submenu
		JMenuItem conIP;
		connect.add(conIP = new JMenuItem("Connect to Server"));
		
		//Create Server submenu
		JMenuItem serverLaunch;
		server.add(serverLaunch = new JMenuItem("Launch Server"));
		
		//Create listeners
		ButtonListener btListener = new ButtonListener();
   	 decrypt.addActionListener(btListener);
		 basic.addActionListener(btListener);
		 fib.addActionListener(btListener);
		 xrated.addActionListener(btListener);
		 plat.addActionListener(btListener);
 		 txtImport.addActionListener(btListener);
		 conIP.addActionListener(btListener);
		 serverLaunch.addActionListener(btListener);
	}
	
	public void outPut(String s)
	{
		outPutWin.append("\n" + s);
	}

	//Recieves a file and outputs its contents to the input window
	private void outPutFile (File f)
	{
		String line;
		inPutWin.setText("");
		try
		{
			FileReader theFile = new FileReader(f);
			BufferedReader readIn = new BufferedReader(theFile);
			while ((line = readIn.readLine()) != null)
			{
				outPutWin.append(line + " ");
			}
		}
		catch (Exception e)
		{
			System.out.println( e );
		}
	}

//	**** ACTION EVENT CLASS****************************************
	class ButtonListener implements ActionListener
	{
  		public void actionPerformed(ActionEvent e)
 	 	{
    		if (e.getActionCommand().equals("Launch Server"))
    		{
     		   new EncryptServer();
    		}
			else if (e.getActionCommand().equals("Basic"))
			{
				en1 = new Encryptor();
				outPut("Basic Encryptor selected");
			}
			else if (e.getActionCommand().equals("Fibonacci"))
			{
				en1 = new Fibonacci();
				outPut("Fibonacci selected");
			}
			else if (e.getActionCommand().equals("X-Rated"))
			{
				en1 = new XratedEncrypt();
				outPut("X-rated selected");
			}
			else if (e.getActionCommand().equals("Platinum"))
			{
				en1 = new Platinum();
				outPut("Platinum selected");
			}
			else if (e.getActionCommand().equals("Import .txt File"))
			{
				outPut("Import chosen");
				File file = new File("ExampleText.txt");
				JFileChooser chooser = new JFileChooser();
					 int retval = chooser.showOpenDialog(null);
					 if (retval == JFileChooser.APPROVE_OPTION)
					 {	
					 	file = chooser.getSelectedFile();
						outPutFile(file);
					 }
			}
			else if (e.getActionCommand().equals("Connect to Server"))
			{
				new connectionFrame();
			}
 	   }// end actionPerformed
	}// end ButtonListner

// *** CONNECTION WINDOW CLASS **************************	
   class connectionFrame extends JFrame
	{
		JLabel user = new JLabel("User Name:");
		JLabel ip = new JLabel("Enter IP:");
		JLabel sock = new JLabel("Enter Socket:");
		JTextField userName = new JTextField("Anonymous");
		JTextField ipIn = new JTextField("");
		JTextField sockIn = new JTextField("8000");
		JButton conn = new JButton("Connect");
		JButton clrTxt = new JButton("Clear");
		
	public connectionFrame()
	{
	
		//Create connection panel
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(4,2));
		p.add(ip); //Label
		p.add(ipIn);//Textfield
		p.add(sock); //Label
		p.add(sockIn);//Textfield
		p.add(user); //Label
		p.add(userName);//Textfield
		p.add(conn); //Connect Button
		p.add(clrTxt); //Clear Button
		
		//Add listener for connect button
		conn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Creates the encryptorClient from text field info
				ec = new encryptorClient(userName.getText(), 
												 ipIn.getText(), 
												 Integer.parseInt(sockIn.getText()));
				
				//Sets the users name								 
				clientList[numClients] = userName.getText();
				numClients++; //Increment number of current users on server
				updateClients(); //Update display in 'Client' window
				new Thread(ec).start(); //Start client thread
			}});
		
		//Add listener for the clear button
		clrTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Clears txt in the connection frame
				ipIn.setText("");
				sockIn.setText("");
				userName.setText("");
			}});
					
		add(p); //Adds the panel to the layout
		
		setTitle("Connection Data"); //Title of the connection frame
      setSize(250,200);
      setVisible(true);
	}//end connectionFrame()
}//end connectionFrame class

// ***** CLIENT CLASS **********************************
	class encryptorClient implements Runnable
	{
		private String USER_NAME; //User name for the local client
	
		public encryptorClient(String UN, String ip, int sock)
 	   {
			USER_NAME = UN;
			try
			{
		
			//Establish connection to server
			connectToServer = new Socket(ip,sock);
		
			//Establish input from server
			fromServer = new BufferedReader(new InputStreamReader
                  (connectToServer.getInputStream()));
			//Establish output to server
			toServer = new PrintWriter
					  (connectToServer.getOutputStream(), true);
		
			//Sends USER_NAME to the server upon connection
			sendUserName();
		
			}//End try
  			 catch(IOException e1)
   		{
     			 System.err.println("Client died with exception: " + e1.toString());
      		 System.exit(0);
   		}

	}//E - CalcClient()
	
	//Thread method run() for the client
	public void run()
	{
		while(true)
		{
			String inline = ""; //From server
			String temp[]; //Splitter variable for inline
			
			try
			{
				inline = fromServer.readLine();
			}
			catch(IOException e)
			{
				System.err.println("Client died with exception: " + e.toString());
			}
			
			temp = inline.split(" ");

			//Uses the user connection update to generate client list
			//When a new client connects it uses the server line to update clientList
				if(temp[0].equals("User") && temp[2].equals("connected."))
				{
					addNewClient(temp[1]);
				}
				if(temp[0].equals("User") && temp[2].equals("disconnected."))
				{
					removeClient(temp[1]);
				}
					
				outPut(inline); //Writes line from server to window
		}//E - while()
	}//E - run()
	
	//Sends UserName to server on connection
	//Receives confirmation greeting from server
	private void sendUserName()
	{
		toServer.println(USER_NAME);
	}
	
	//Closes the connection
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
	
	//Passes a string to the server
	public void setCalcLine ( String s )
	{
		toServer.println(s);
		toServer.flush();
	}
	
	//Clears clientlist window and reprints
	public void updateClients (  )
	{
		clientListWin.setText("");
		for(int i = 0; i < numClients; i++)
		{
			clientListWin.append(clientList[i] + "\n");
		}
	}

	//Adds a new client to the list and updates the display
	private void addNewClient(String s)
	{
		clientList[numClients] = s; //Adds client
		numClients++;		//increment no. of clients connected	
		updateClients();	//updates display
	}
	
	//Removes a client from the list
	private void removeClient(String s)
	{
		//Loop through all clients
		for(int i = 0; i < numClients; i++)
		{
			//When the client is found, remove it
			if(clientList[i].equals(s))
			{
				clientList[i] = "";
				String t;
				
				//This loop repacks the list
				for(int j = i; j < numClients - 1; j++)
				{
					clientList[j] = clientList[j + 1];
				}
			}//E - if{}
		}//E - for{}
		
		numClients--;
		updateClients();
	}//E - removeClient{}
	
	private Action send = new AbstractAction("Send")
	{
		public void actionPerformed(ActionEvent e)
		{
			setCalcLine(inPutWin.getText());
			inPutWin.setText("");			
		}
	};
		
}// end EncryptorFrame()