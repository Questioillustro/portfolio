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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import java.awt.image.BufferedImage;

public class GUI_EditLocations extends JPanel{

	//Private variables
	private GUI_Controller parent;
	private JFrame fullpanel;
	
	private JButton Back = new JButton();
	private JButton btnAdd = new JButton("Add");
	private JButton btnRemove = new JButton("Remove");
	private JButton btnModify = new JButton("Modify");
	private JButton btnSave= new JButton("Save");
	private JButton btnCancel = new JButton("Cancel");
	
	private JTextField locationName = new JTextField();
	private JTextField locationDistance = new JTextField();
	
	private JList locationList;
	private Map<String, Integer> locationMap;
	JPanel centercontainer;
	Map<String, Integer> locations;
	
	/*
	 * Main menu Constructor that initializes all the buttons and action listeners
	 */
	public GUI_EditLocations(GUI_Controller p)
	{
		parent = p;
		initialize();
	}
	
	private void initialize(){
		btlisten bt = new btlisten();
		Mouse_Listener ml = new Mouse_Listener();
		
		fullpanel = new JFrame();
		
		Back.setPreferredSize(parent.getBtSize());
		Back.setBackground(parent.getBtColor());
		Back.setFont(parent.getBtFont());
		Back.setIcon(parent.getBackArrow());
		Back.setHorizontalTextPosition(JButton.CENTER);
		Back.setActionCommand("Back");
			
		locationMap = parent.getLocTime();
		locationList = new JList(locationMap.keySet().toArray());
		locationList.setFont(new Font("Arial",Font.PLAIN,34));
		
		
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
		rightpanelcontainer.setBorder(parent.getPanelBorder());
		rightpanel.setBackground(parent.getBgColor());
			
//Center Panel		
		centercontainer = new JPanel();
		centercontainer.setBorder(parent.getPanelBorder());
		centercontainer.setLayout(new GridLayout(2,1));
		centercontainer.setBackground(parent.getBgPanelColor());
		
		JPanel centerpanel = new JPanel();
		centerpanel.setLayout(new GridLayout(1,1));
		centerpanel.setBorder(BorderFactory.createTitledBorder("Available Locations"));
		

//Build Panels
		centerpanel.add(locationList);
		centercontainer.add(centerpanel);
		
		left_subpanel1.add(Back);
		
		leftpanel.add(left_subpanel1);
		leftpanel.add(left_subpanel2);
		leftpanel.add(left_subpanel3);
		leftpanelcontainer.add(leftpanel);
        rightpanelcontainer.setLayout(new GridLayout(0, 1, 0, 0));
        rightpanel.setLayout(new GridLayout(2, 1, 0, 5));
            
		rightpanelcontainer.add(rightpanel);
		
			JPanel right_subpanel1 = new JPanel();
			rightpanel.add(right_subpanel1);
			right_subpanel1.setBackground(parent.getBgColor());
			right_subpanel1.setLayout(new GridLayout(2, 2, 0, 10));
			
			JLabel jname = new JLabel ("Location Name");
			right_subpanel1.add(jname);
			right_subpanel1.add(locationName);
			JLabel jdistance = new JLabel ("Distance(Minutes)");
			right_subpanel1.add(jdistance);
			right_subpanel1.add(locationDistance);
			
			locationName.setEditable(false);
			locationDistance.setEditable(false);
			locationName.addMouseListener(ml);
			locationDistance.addMouseListener(ml);
			
				JPanel right_subpanel2 = new JPanel();
				rightpanel.add(right_subpanel2);
				right_subpanel2.setBackground(parent.getBgColor());
				right_subpanel2.setLayout(new GridLayout(5, 1, 0, 30));
				right_subpanel2.add(btnAdd);
				
				btnAdd.setPreferredSize(parent.getBtSize());
				btnAdd.setBackground(parent.getBtColor());
				btnAdd.setFont(parent.getBtFont());
				right_subpanel2.add(btnModify);
				
				
		btnModify.setPreferredSize(parent.getBtSize());
		btnModify.setBackground(parent.getBtColor());
		btnModify.setFont(parent.getBtFont());
		right_subpanel2.add(btnRemove);
		
		btnRemove.setPreferredSize(parent.getBtSize());
		btnRemove.setBackground(parent.getBtColor());
		btnRemove.setFont(parent.getBtFont());
		
		btnSave = new JButton("Save");
		right_subpanel2.add(btnSave);
		btnSave.setEnabled(false);
		btnSave.setPreferredSize(parent.getBtSize());
		
		btnCancel = new JButton("Cancel");
		right_subpanel2.add(btnCancel);
		btnCancel.setEnabled(false);
		btnCancel.setPreferredSize(parent.getBtSize());
		btnCancel.addActionListener(bt);
		btnSave.addActionListener(bt);
		btnRemove.addActionListener(bt);
		btnModify.addActionListener(bt);
		btnAdd.addActionListener(bt);
		//centerpanel.add(new GUI_KeyBoard().getContainer());
		
		fullpanel.getContentPane().add(leftpanelcontainer, BorderLayout.WEST);
		fullpanel.add(centerpanel, BorderLayout.CENTER);
		fullpanel.getContentPane().add(rightpanelcontainer, BorderLayout.EAST);

//Add ActionListeners
		Back.addActionListener(bt);

	}
	
	/**
	 * Returns container for this pane
	 * @return Container of this panel
	 */
	public Container getContainer()
	{
		return fullpanel.getContentPane();
	}

	/*
	 * ActionListener that triggers when a button is pressed
	 */
	private class btlisten implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getActionCommand().equals("Back"))
			{
				parent.mainMenuCommand("manage");
				doneEditing();
			}
			if(e.getActionCommand().equals("Add"))
			{
				editing();
			}	
			if(e.getActionCommand().equals("Remove"))	
			{
				if(JOptionPane.showConfirmDialog(
					    parent,
					    "Do you really want to remove location: " + locationList.getSelectedValue() + "?",
					    "Confirm location removal.",
					    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					locationMap.remove(locationList.getSelectedValue().toString());
				}
			}
			if(e.getActionCommand().equals("Modify")){
				editing();
				locationName.setText(locationList.getSelectedValue().toString());
				locationDistance.setText(locationMap.get(locationList.getSelectedValue().toString()).toString());
			}
			if(e.getActionCommand().equals("Save")){
				locationMap.put(locationName.getText(), Integer.parseInt(locationDistance.getText()));
				doneEditing();
			}
			if(e.getActionCommand().equals("Cancel")){
				doneEditing();
			}
			
			locationMap = parent.getLocTime();
			locationList.setListData(locationMap.keySet().toArray());
		}
		
		/**
		 * Sets fields to editable for editing
		 */		
		private void editing(){
			locationName.setEditable(true);
			locationDistance.setEditable(true);
			
			btnAdd.setEnabled(false);
			btnRemove.setEnabled(false);
			btnModify.setEnabled(false);
			btnSave.setEnabled(true);
			btnCancel.setEnabled(true);
			
			locationList.setEnabled(false);
		}
		
		/**
		 * Sets fields to uneditable
		 */	
		private void doneEditing(){
			locationName.setText("");
			locationDistance.setText("");
			
			locationName.setEditable(false);
			locationDistance.setEditable(false);
			
			btnAdd.setEnabled(true);
			btnRemove.setEnabled(true);
			btnModify.setEnabled(true);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
			
			locationList.setEnabled(true);
		}

	}
	
}//GUI_MainMenu