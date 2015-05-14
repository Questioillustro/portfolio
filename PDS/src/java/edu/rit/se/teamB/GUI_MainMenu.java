/**
 * 
 */
package edu.rit.se.teamB;

/**
 * @author Tony Chen
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.image.BufferedImage;

public class GUI_MainMenu extends JPanel{

	//Private variables
	private GUI_Controller parent;
	private JFrame fullpanel;
	private JButton Manage = new JButton("Management");
	
	/**
	 * Main menu Constructor that initializes all the buttons and action listeners
	 */
	public GUI_MainMenu(GUI_Controller p)
	{
		parent = p;
		initialize();
	}
	
	/**
	 * Initializer
	 */
	private void initialize(){
		btlisten bt = new btlisten();

		fullpanel = new JFrame();
		
		JButton Logout= new JButton("Logout");
		JButton OrderLookup = new JButton("Lookup Order");
		JButton PlaceOrder = new JButton("Place Order");
		
		Logout.setPreferredSize(parent.getBtSize());
		Logout.setBackground(parent.getBtColor());
		Logout.setFont(parent.getBtFont());
		
		OrderLookup.setPreferredSize(parent.getBtSize());
		OrderLookup.setBackground(parent.getBtColor());
		OrderLookup.setFont(parent.getBtFont());
		
		PlaceOrder.setPreferredSize(parent.getBtSize());
		PlaceOrder.setBackground(parent.getBtColor());
		PlaceOrder.setFont(parent.getBtFont());
		
		Manage.setPreferredSize(parent.getBtSize());
		Manage.setBackground(parent.getBtColor());
		Manage.setFont(parent.getBtFont());

//Left Panel
		JPanel leftpanelcontainer = new JPanel();
		leftpanelcontainer.setBackground(parent.getBgColor());
		leftpanelcontainer.setBorder(parent.getPanelBorder());
		leftpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel leftpanel = new JPanel();
		leftpanel.setLayout(new GridLayout(3,1,0,30));
		leftpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		leftpanel.setBackground(parent.getBgColor());
	
			JPanel left_subpanel1 = new JPanel();
			left_subpanel1.setBackground(parent.getBgColor());
			
			JPanel left_subpanel2 = new JPanel();
			left_subpanel2.setBackground(parent.getBgColor());
			
			JPanel left_subpanel3 = new JPanel();
			left_subpanel3.setBackground(parent.getBgColor());
		
//Right Panel			
		JPanel rightpanelcontainer = new JPanel();	
		rightpanelcontainer.setBackground(parent.getBgColor());
		rightpanelcontainer.setBorder(parent.getPanelBorder());
		rightpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel rightpanel = new JPanel();
		rightpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		rightpanel.setBackground(parent.getBgColor());	
	
//Center Panel		
		JPanel centerpanel = new JPanel();
		centerpanel.setBorder(parent.getPanelBorder());
		JLabel bgImage = new JLabel(parent.getBackgroundImage());
		centerpanel.add(bgImage);
		centerpanel.setBackground(parent.getBgPanelColor());

//Build Panels
		left_subpanel1.add(Logout);
		left_subpanel2.add(OrderLookup);
		left_subpanel3.add(PlaceOrder);
		
		leftpanel.add(left_subpanel1);
		leftpanel.add(left_subpanel2);
		leftpanel.add(left_subpanel3);
		leftpanelcontainer.add(leftpanel);

		rightpanel.add(Manage);
		rightpanelcontainer.add(rightpanel);
		
		//centerpanel.add(new GUI_KeyBoard().getContainer());
		
		fullpanel.add(leftpanelcontainer, BorderLayout.WEST);
		fullpanel.add(centerpanel, BorderLayout.CENTER);
		fullpanel.add(rightpanelcontainer, BorderLayout.EAST);

//Add ActionListeners
		Manage.addActionListener(bt);
		Logout.addActionListener(bt);
		OrderLookup.addActionListener(bt);
		PlaceOrder.addActionListener(bt);
		
	}
	
	/**
	 * Sets the manager button to visible or not
	 * @param b Whether the current user is a manager or nto
	 */
	public void setManager(boolean b){
		Manage.setVisible(b);	
	}
	
	/**
	 * Accessor for the current container
	 * @return The current container
	 */
	public Container getContainer()
	{
		return fullpanel.getContentPane();
	}

	/**
	 * ActionListener that triggers when a button is pressed
	 */
	private class btlisten implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getActionCommand().equals("Place Order"))
			{
				parent.mainMenuCommand("custlookup");
			}
			if(e.getActionCommand().equals("Management"))
			{
				parent.mainMenuCommand("manage");
			}
			if(e.getActionCommand().equals("Logout"))
			{
				parent.mainMenuCommand("log");
			}
			if(e.getActionCommand().equals("Lookup Order"))
			{
				parent.mainMenuCommand("orderlookup");
			}
		}
	}
}//GUI_MainMenu
