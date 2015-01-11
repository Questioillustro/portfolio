/*
<package>
	GUI Calculator in Java
<.package>
<description>
    Swing class for creating menus
<.description>
<keywords>
	swing, menu
<.keywords>
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class CreateMenus extends JFrame implements ActionListener

{

  private JMenuItem jmiCalc;
  private JMenuItem jmiClear;
  private JMenuItem jmiExit;
  private JMenuItem About;
  private JMenuItem InputInfo;

//-----------------------------------------------------------

/** Default constructor*/
   public CreateMenus()
   {
//-------------------------------------------------------------

     JMenuBar jmenuBar = new JMenuBar();

     //Set menu bar to the frame
     setJMenuBar(jmenuBar);

     //Create two menus labeled Operation and Help
     JMenu fileMenu = new JMenu("Operation");
     JMenu helpMenu = new JMenu("Help");
     jmenuBar.add(fileMenu);
     jmenuBar.add(helpMenu);

	  //Create menu items
     fileMenu.add(jmiCalc = new JMenuItem("Calculate", 'L'));
     fileMenu.add(jmiClear = new JMenuItem("Clear", 'R'));

     //The menu components
     jmiCalc.setIcon(new ImageIcon("new.gif"));
     jmiClear.setIcon(new ImageIcon("open.gif"));

     fileMenu.addSeparator();

     fileMenu.add(jmiExit = new JMenuItem("Exit",'E'));

      // Set keyboard accelerators
	  jmiCalc.setAccelerator(
	        KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
	  jmiClear.setAccelerator(
	        KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
	  jmiExit.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

     helpMenu.add(About = new JMenuItem("About", 'T'));
     helpMenu.add(InputInfo = new JMenuItem("Input Information", 'I'));
		
	  About.setAccelerator(
	        KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
	  InputInfo.setAccelerator(
	        KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

       //Register Listeners for JMenuItems and JRadioButtonMenuItems
       jmiCalc.addActionListener(this);
       jmiClear.addActionListener(this);
       jmiExit.addActionListener(this);
		 About.addActionListener(this);
		 InputInfo.addActionListener(this);
    } // end default constructor

/** Handle ActionEvent from menu items and buttons*/
  public void actionPerformed(ActionEvent e)
  {
    String actionCommand = e.getActionCommand();

    if (e.getSource() instanceof JMenuItem)
     {

 	  if ("Calculate".equals(actionCommand))
 	      respondToCalc();
 	  else if ("Clear".equals(actionCommand))
 	      respondToClear();

 	  else if ("Exit".equals(actionCommand))
 	      System.exit(0);
	  else if("About".equals(actionCommand))
	      respondToAbout();
	  else if("Input Information".equals(actionCommand))
	      respondToInputInfo();
    }// end instance of JMenuItem

   } //end actionPerformed

   public void respondToCalc()
   {
	   System.out.println("The file, Calculate option was selected");
   }//end respondToNew



   public void respondToClear()
   {
	 System.out.println("The file, Clear option was selected");
   }//end respondToOpen
	
	public void respondToAbout()
	{
		System.out.println("Computing Fraction Value");
	}
	
	public void respondToInputInfo()
	{
		System.out.println("Example for Input");
		System.out.println("1/2 + 2/3");
		System.out.println("Need space between operands and operation");
	}
}//end class CreateMenu