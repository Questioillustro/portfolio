/**
 * 
 */
package edu.rit.se.teamB;

/**
 * @author teamB
 *
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI_Simulation extends JPanel{

	private JTextField drivers;
	private JTextField cooks;
	private JTextField ovens;
	private JTextField simspd;
	
	private JLabel ldrivers = new JLabel("Drivers:");
	private JButton bdrivers = new JButton("Set");
	private JButton driverUp = new JButton("+");
	private JButton driverDow = new JButton("-");
	private int driverCount;
	
	
	private JButton bsimspd = new JButton("Set");
	private JLabel lsimspd = new JLabel("Speed:");
	private JButton simUp = new JButton("+");
	private JButton simDow = new JButton("-");
	private double simSpeed;
	
	private JButton bcooks = new JButton("Set");
	private JLabel lcooks = new JLabel("Cooks:");
	private JButton cookUp = new JButton("+");
	private JButton cookDow = new JButton("-");
	private int cookCount;
	
	private JButton bovens = new JButton("Set");
	private JLabel lovens = new JLabel("Ovens:");
	private JButton ovenUp = new JButton("+");
	private JButton ovenDow = new JButton("-");
	private int ovenCount;
		
	private GUI_Controller parent;
	private JButton Back = new JButton();
	private JFrame fullpanel;
	
	/**
	 * Default constructor
	 * @param p calling panel
	 * @param cooks num cooks
	 * @param drivers num drivers
	 * @param speed sim speed
	 * @param ovens num ovens
	 */
	public GUI_Simulation(GUI_Controller p, int cooks, int drivers, double speed, int ovens)
	{
		parent = p;
		cookCount = cooks;
		driverCount = drivers;
		simSpeed = speed;
		ovenCount = ovens;
		initialize();
	}
	
	/**
	 * Initializer
	 */
	private void initialize(){
		btlisten bt = new btlisten();
		Mouse_Listener ml = new Mouse_Listener();
		
		fullpanel = new JFrame();
		
		drivers = new JTextField(Integer.toString(driverCount));
		cooks = new JTextField(Integer.toString(cookCount));
		simspd = new JTextField(Double.toString(simSpeed));
		simspd.addMouseListener(ml);
		ovens = new JTextField(Integer.toString(ovenCount));
		
		Back.setPreferredSize(parent.getBtSize());
		Back.setBackground(parent.getBtColor());
		Back.setFont(parent.getBtFont());
		Back.setIcon(parent.getBackArrow());
		Back.setHorizontalTextPosition(JButton.CENTER);
		Back.setActionCommand("Back");
		
		bdrivers.setActionCommand("setDriver");
		bdrivers.addActionListener(bt);
		driverUp.setActionCommand("incDriver");
		driverUp.addActionListener(bt);
		driverDow.setActionCommand("decDriver");
		driverDow.addActionListener(bt);
		
		bcooks.setActionCommand("setCook");
		bcooks.addActionListener(bt);
		cookUp.setActionCommand("incCook");
		cookUp.addActionListener(bt);
		cookDow.setActionCommand("decCook");
		cookDow.addActionListener(bt);
		
		bsimspd.setActionCommand("setSim");
		bsimspd.addActionListener(bt);
		simUp.setActionCommand("incSim");
		simUp.addActionListener(bt);
		simDow.setActionCommand("decSim");
		simDow.addActionListener(bt);
		
		bovens.setActionCommand("setOven");
		bovens.addActionListener(bt);
		ovenUp.setActionCommand("incOven");
		ovenUp.addActionListener(bt);
		ovenDow.setActionCommand("decOven");
		ovenDow.addActionListener(bt);
		

		
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
			
//Right Panel			
		JPanel rightpanelcontainer = new JPanel();	
		rightpanelcontainer.setBackground(parent.getBgColor());
		rightpanelcontainer.setBorder(parent.getPanelBorder());
		rightpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel rightpanel = new JPanel(new GridLayout(4,1,0,50));
		rightpanel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
		rightpanel.setBackground(parent.getBgColor());	
		
			JPanel right_subpanel1 = new JPanel(new GridLayout(2,3));
			right_subpanel1.setBackground(parent.getBgColor());
		
			JPanel right_subpanel2 = new JPanel(new GridLayout(2,3));
			right_subpanel2.setBackground(parent.getBgColor());
		
			JPanel right_subpanel3 = new JPanel(new GridLayout(2,3));
			right_subpanel3.setBackground(parent.getBgColor());
			
			JPanel right_subpanel4 = new JPanel(new GridLayout(2,3));
			right_subpanel4.setBackground(parent.getBgColor());
	
//Center Panel		
			JPanel centerpanel = new JPanel();
			centerpanel.setBorder(parent.getPanelBorder());
			JLabel bgImage = new JLabel(parent.getBackgroundImage());
			centerpanel.add(bgImage);
			centerpanel.setBackground(parent.getBgPanelColor());

//Build Panels
		left_subpanel1.add(Back);
		
		leftpanel.add(left_subpanel1);
		leftpanelcontainer.add(leftpanel);
		
		right_subpanel1.add(lcooks);
		right_subpanel1.add(cooks);
		right_subpanel1.add(bcooks);
		right_subpanel1.add(cookUp);
		right_subpanel1.add(cookDow);
		
		right_subpanel2.add(ldrivers);
		right_subpanel2.add(drivers);
		right_subpanel2.add(bdrivers);
		right_subpanel2.add(driverUp);
		right_subpanel2.add(driverDow);
		
		right_subpanel3.add(lsimspd);
		right_subpanel3.add(simspd);
		right_subpanel3.add(bsimspd);
		//right_subpanel3.add(simUp);
		//right_subpanel3.add(simDow);
		

		rightpanel.add(right_subpanel1);
		rightpanel.add(right_subpanel2);
		rightpanel.add(right_subpanel3);
		rightpanelcontainer.add(rightpanel);
		
		//centerpanel.add(new GUI_KeyBoard().getContainer());
		
		fullpanel.add(leftpanelcontainer, BorderLayout.WEST);
		fullpanel.add(centerpanel, BorderLayout.CENTER);
		fullpanel.add(rightpanelcontainer, BorderLayout.EAST);

//Add ActionListeners
		Back.addActionListener(bt);
	}//Initialize()
	
	/**
	 * Accessor for the container
	 * @return the container
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
			if(e.getActionCommand().equals("Back"))
			{
				parent.mainMenuCommand("manage");
			}
			
			if(e.getActionCommand().equals("incCook"))
			{
				cookCount++;
				cooks.setText(Integer.toString(cookCount));
			}
			
			if(e.getActionCommand().equals("decCook"))
			{
				if(cookCount > 0)
				{	
					cookCount--;
					cooks.setText(Integer.toString(cookCount));
				}
			}
			
			if(e.getActionCommand().equals("incDriver"))
			{
				driverCount++;
				drivers.setText(Integer.toString(driverCount));
			}
			
			if(e.getActionCommand().equals("decDriver"))
			{
				if(driverCount > 0)
				{	
					driverCount--;
					drivers.setText(Integer.toString(driverCount));
				}
			}
				
			if(e.getActionCommand().equals("setCook"))
			{
				parent.setCooks(Integer.parseInt(cooks.getText()));
			}
			
			if(e.getActionCommand().equals("setSim"))
			{
				parent.setSimSpeed(Double.parseDouble(simspd.getText()));
			}
		}
	}
}//GUI_MainMenu