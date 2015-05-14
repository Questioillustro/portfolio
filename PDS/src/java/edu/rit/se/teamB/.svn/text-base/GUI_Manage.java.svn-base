/**
 * 
 */
package edu.rit.se.teamB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Tony Chen
 *
 */
public class GUI_Manage extends JPanel implements ActionListener{

	private GUI_Controller parent;
	private JFrame fullpanel;
	
	/**
	 * The default constructor
	 */
	public GUI_Manage(GUI_Controller p)
	{
		parent = p;
		initialize();
	}
	
	/**
	 * Initializer
	 */
	private void initialize()
	{
		fullpanel = new JFrame();
		
		JButton Back = new JButton();
		JButton EditUsers = new JButton("Manage Users");
		JButton EditMenu = new JButton("Edit Menu");
		JButton EditLocations = new JButton("Edit Locations");
		JButton Simulation = new JButton("Simulation");
		JButton Reports = new JButton("Reports");
		JButton Exit = new JButton("Exit");

		Exit.setPreferredSize(parent.getBtSize());
		Exit.setBackground(parent.getBtColor());
		Exit.setFont(parent.getBtFont());
		
		Back.setPreferredSize(parent.getBtSize());
		Back.setBackground(parent.getBtColor());
		Back.setIcon(parent.getBackArrow());
		Back.setHorizontalTextPosition(JButton.CENTER);
		Back.setActionCommand("Back");
		
		EditUsers.setPreferredSize(parent.getBtSize());
		EditUsers.setBackground(parent.getBtColor());
		EditUsers.setFont(parent.getBtFont());

		EditMenu.setPreferredSize(parent.getBtSize());
		EditMenu.setBackground(parent.getBtColor());
		EditMenu.setFont(parent.getBtFont());
		
		Reports.setPreferredSize(parent.getBtSize());
		Reports.setBackground(parent.getBtColor());
		Reports.setFont(parent.getBtFont());
		
		Simulation.setPreferredSize(parent.getBtSize());
		Simulation.setBackground(parent.getBtColor());
		Simulation.setFont(parent.getBtFont());
		
		EditLocations.setPreferredSize(parent.getBtSize());
		EditLocations.setBackground(parent.getBtColor());
		EditLocations.setFont(parent.getBtFont());
	
//Left Panel
		JPanel leftpanelcontainer = new JPanel();
		leftpanelcontainer.setBackground(parent.getBgColor());
		leftpanelcontainer.setBorder(parent.getPanelBorder());
		leftpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel leftpanel = new JPanel();
		leftpanel.setLayout(new GridLayout(3,1,0,10));
		leftpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		leftpanel.setBackground(parent.getBgColor());
	
			JPanel left_subpanel1 = new JPanel();
			left_subpanel1.setBackground(parent.getBgColor());

			JPanel left_subpanel2 = new JPanel();
			left_subpanel2.setBackground(parent.getBgColor());
			
//Right Panel			
		JPanel rightpanelcontainer = new JPanel();	
		rightpanelcontainer.setBackground(parent.getBgColor());
		rightpanelcontainer.setBorder(parent.getPanelBorder());
		rightpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel rightpanel = new JPanel();
		rightpanel.setLayout(new GridLayout(5,1,0,30));
		rightpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		rightpanel.setBackground(parent.getBgColor());	

			JPanel right_subpanel1 = new JPanel();
			right_subpanel1.setBackground(parent.getBgColor());
		
			JPanel right_subpanel2 = new JPanel();
			right_subpanel2.setBackground(parent.getBgColor());
		
			JPanel right_subpanel3 = new JPanel();
			right_subpanel3.setBackground(parent.getBgColor());
		
			JPanel right_subpanel4 = new JPanel();
			right_subpanel4.setBackground(parent.getBgColor());
		
			JPanel right_subpanel5 = new JPanel();
			right_subpanel5.setBackground(parent.getBgColor());
	
//Center Panel		
			JPanel centerpanel = new JPanel();
			centerpanel.setBorder(parent.getPanelBorder());
			JLabel bgImage = new JLabel(parent.getBackgroundImage());
			centerpanel.add(bgImage);
			centerpanel.setBackground(parent.getBgPanelColor());

//Build Panels		
		left_subpanel1.add(Back);
		left_subpanel2.add(Exit);
		
		right_subpanel1.add(EditMenu);
		right_subpanel2.add(EditUsers);
		right_subpanel3.add(EditLocations);
		right_subpanel4.add(Simulation);
		right_subpanel5.add(Reports);
		
		leftpanel.add(left_subpanel1);
		leftpanel.add(left_subpanel2);
		
		rightpanel.add(right_subpanel1);
		rightpanel.add(right_subpanel2);
		rightpanel.add(right_subpanel3);
		rightpanel.add(right_subpanel4);
		rightpanel.add(right_subpanel5);
		
		leftpanelcontainer.add(leftpanel);
		rightpanelcontainer.add(rightpanel);
		
		fullpanel.add(leftpanelcontainer, BorderLayout.WEST);
		fullpanel.add(centerpanel, BorderLayout.CENTER);
		fullpanel.add(rightpanelcontainer, BorderLayout.EAST);

//Add ActionListeners
		Back.addActionListener(this);
		EditUsers.addActionListener(this);
		EditMenu.addActionListener(this);
		EditLocations.addActionListener(this);
		Simulation.addActionListener(this);
		Reports.addActionListener(this);
		Exit.addActionListener(this);
	}//Initialize
	
	/**
	 * Accessor for container
	 * @return the container
	 */
	public Container getContainer() {
		return fullpanel.getContentPane();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Edit Menu")) {
			parent.mainMenuCommand("editmenu");
		}
		if (e.getActionCommand().equals("Edit Locations")) {
			parent.mainMenuCommand("editlocations");
		}
		if (e.getActionCommand().equals("Back")) {
			parent.mainMenuCommand("mainmenu");
		}
		if (e.getActionCommand().equals("Simulation")) {
			parent.mainMenuCommand("sim");
		}
		if (e.getActionCommand().equals("Manage Users")) {
			parent.mainMenuCommand("usermanager");
		}
		if (e.getActionCommand().equals("Reports")) {
			parent.mainMenuCommand("reports");
		}
		if (e.getActionCommand().equals("Exit")) {
			parent.cleanUp();
		}
	}
}