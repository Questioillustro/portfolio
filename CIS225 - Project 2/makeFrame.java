/*
<package>
	GUI Calculator in Java
<.package>
<description>
	Frame creator for the calculator GUI
<.description>
<keywords>
	frame, swing, gui
<.keywords>
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class makeFrame extends JFrame{
		
		private JTextField outputWin = new JTextField();
		private JTextField inputWin = new JTextField();
	   
		private JMenuItem jmiCalc,
								jmiClear,
								jmiExit,
								About,
								InputInfo;
		
		private fractionCalculator fc1;
	
	public makeFrame ( )
	{
		//Create text fields for 'Enter Equation' and Input window
		JPanel p1 = new JPanel();
		JTextField eq = new JTextField("Enter Equation");
			eq.setEditable(false);
				p1.setLayout(new GridLayout(2,2));
				p1.add(eq);
				p1.add(inputWin);
	
		//Create i/o text fields
		JTextField val = new JTextField("Value");
			val.setEditable(false);
			outputWin.setEditable(false);
				p1.add(val);
				p1.add(outputWin);
		
		//Create action buttons panel
		JPanel p2 = new JPanel();
		JButton calculate = new JButton("Calculate");
		JButton clear = new JButton("Clear");
			p2.setLayout(new GridLayout(1,2));
			p2.add(calculate);
			p2.add(clear);
		
		//Add panels to the frame
		add(p1, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);
		
		//Create Menus
		JMenuBar jmenuBar = new JMenuBar();

      //Set menu bar to the frame
      setJMenuBar(jmenuBar);

      //Create two menus labeled Operation and Help
      JMenu fileMenu = new JMenu("Operation");
      JMenu helpMenu = new JMenu("Help");
      	jmenuBar.add(fileMenu);
      	jmenuBar.add(helpMenu);

    	//Create items for 'Operations' menu
      fileMenu.add(jmiCalc = new JMenuItem("Calculate", 'L'));
      fileMenu.add(jmiClear = new JMenuItem("Clear", 'R'));
      fileMenu.addSeparator();
      fileMenu.add(jmiExit = new JMenuItem("Exit",'E'));

      // Set keyboard shortcuts for 'Operations' menu
	   jmiCalc.setAccelerator(
	        KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
	   jmiClear.setAccelerator(
	        KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
	   jmiExit.setAccelerator(
           KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		
	   //Add 'Help' menu items	
      helpMenu.add(About = new JMenuItem("About", 'T'));
      helpMenu.add(InputInfo = new JMenuItem("Input Information", 'I'));
	 
	   //Set keyboard shortcuts for 'Help' menu
	   About.setAccelerator(
	        KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
	   InputInfo.setAccelerator(
	        KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
			  
	   //Create listeners for buttons
		ButtonListener btlisten = new ButtonListener();
		calculate.addActionListener(btlisten);
		clear.addActionListener(btlisten);
		
	   //Create listeners for menus
		jmiCalc.addActionListener(btlisten);
      jmiClear.addActionListener(btlisten);
      jmiExit.addActionListener(btlisten);
		About.addActionListener(btlisten);
		InputInfo.addActionListener(btlisten);
		

	}//End makeFrame constructor
	
	private void outPut(String s)
	{
		outputWin.setText(s);
	}
	
	//Inner class sets actions for each button pressed in GUI
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("Calculate"))
			{
				if (inputWin.getText().length() != 9 && inputWin.getText().length() != 10){//Ensure input is of valid length
					errorMessage eM = new errorMessage();
				} else{
					Fraction result;
					fc1 = new fractionCalculator(inputWin.getText());	//Builds fractions and sets operation
						
						if (fc1.getResult().equals("0")){	//A zero is returned from getResult if there is an input error
							errorMessage eM = new errorMessage();
						} else {	
							outPut(fc1.getResult());	//Performs operation and outputs the result
						  }
				  }
			} //End 'Calculate' algorithm
			else if (e.getActionCommand().equals("Clear"))
			{
				outputWin.setText("");
				inputWin.setText("");
			}
			else if (e.getActionCommand().equals("About"))
			{
				aboutFrame af = new aboutFrame();
			}
			else if (e.getActionCommand().equals("Input Information"))
			{
				inputInfoFrame ii = new inputInfoFrame();
			}
		}//End actionPerformed
	}//End ButtonListener class
	
	public class aboutFrame extends JFrame
	{
		public aboutFrame ( )
		{
			JOptionPane.showMessageDialog(null, "Performs the following operations between 2 fractions:" +
															"\n*  /  +  -  <  >  =  <=  =>" +
															"\n\nWritten by Stephen Brewster",
															"Fraction Calculator by Stephen Brewster",
															JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public class inputInfoFrame extends JFrame
	{
		public inputInfoFrame ( )
		{
			JOptionPane.showMessageDialog(null, "Input must be in the format:\n  1/2 * 2/3" +
															"\nA space is required between the operands and operation.",
															"Input Information",
															JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public class errorMessage extends JFrame
	{
		public errorMessage( )
		{
			JOptionPane.showMessageDialog(null, "Input Error: see Help / Input Information",
															"Input Error",
															JOptionPane.ERROR_MESSAGE);			
		}
	}	
}//End makeFrame Class