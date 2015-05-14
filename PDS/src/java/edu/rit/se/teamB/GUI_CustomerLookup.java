package edu.rit.se.teamB;

/**
 * @author Chad Campbel
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.image.BufferedImage;

public class GUI_CustomerLookup extends JPanel{

	//Private variables
	private GUI_Controller parent; // GUI Controller
	
	private JButton Back= new JButton(); // Back button

	private JFrame fullpanel; // This panel
	private JTextField phone_tf;
	private JTextField custName_tf;
	private JComboBox cAddr_cb;
	private ArrayList<String> locations;
	private Customer customer;

	
	/**
	 * Main menu Constructor that calls the init function
	 * @param p The GUI Controller instantiating this panel
	 */
	public GUI_CustomerLookup(GUI_Controller p)
	{
		parent = p;
		initialize();
	}
	
	/**
	 * Initializes all the buttons and action listeners
	 */
	private void initialize(){
		// Button listener and keyboard listener
		btlisten bt = new btlisten();
		Mouse_Listener fl = new Mouse_Listener();
		
		//Get locations from controller
		locations = parent.getLocations();
		
		fullpanel = new JFrame();
		
		// Back button maker
		Back.setPreferredSize(parent.getBtSize());
		Back.setBackground(parent.getBtColor());
		Back.setIcon(parent.getBackArrow());
		Back.setHorizontalTextPosition(JButton.CENTER);
		Back.setActionCommand("Back");
		
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
		
	
//Center Panel		
		JPanel centerpanel = new JPanel();
		centerpanel.setBorder(parent.getPanelBorder());
		centerpanel.setBackground(parent.getBgColor());
		fullpanel.getContentPane().add(rightpanelcontainer, BorderLayout.EAST);
	

//Build Panels
		left_subpanel1.add(Back);
		
		leftpanel.add(left_subpanel1);
		leftpanel.add(left_subpanel2);
		leftpanel.add(left_subpanel3);
		leftpanelcontainer.add(leftpanel);
		rightpanelcontainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel rightpanel = new JPanel();
		rightpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		rightpanel.setBackground(parent.getBgColor());	
		rightpanel.setLayout(new GridLayout(3, 1, 0, 30));
		
		JPanel panel_4 = new JPanel();
		rightpanel.add(panel_4);
		panel_4.setLayout(new GridLayout(3, 1, 0, 60));
		
		JPanel panel_1 = new JPanel();
		panel_4.add(panel_1);
		
		JButton search_b = new JButton("Search");
		search_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		panel_1.add(search_b);
		search_b.setPreferredSize(parent.getBtSize());
		search_b.setBackground(parent.getBtColor());
		search_b.setFont(parent.getBtFont());
		
		// Action Listener for search button. If nothing entered for phone num, call to action
		search_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(phone_tf.getText().isEmpty()){
					GUI_Controller.setKeyboardVisible(false);
					JOptionPane.showMessageDialog(parent, "Please enter a phone number to search before searching.");
				}
				
				// Otherwise search for customer
				else{
					locations = parent.getLocations();
					cAddr_cb.removeAllItems();

					for(Object str : locations.toArray()) {
					   cAddr_cb.addItem(str);
					}
					// Set customer to results of customer lookup
					customer = Customer.lookUp(phone_tf.getText());
					// If customer !exists prompt for information
					if(customer == null){
						GUI_Controller.setKeyboardVisible(false);
						JOptionPane.showMessageDialog(parent, "Customer not found, please supply customer info.");
					}
					// Otherwise fill in found customer information
					else{
						custName_tf.setText(customer.getName());
						cAddr_cb.setSelectedItem(customer.getLocation());
					}
					custName_tf.setEnabled(true);
					cAddr_cb.setEnabled(true);
				}
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_4.add(panel_2);
		
		// Favorite Order button
		JButton btnUseFavoriteOrder = new JButton("Use Favorite Order and Continue");
		btnUseFavoriteOrder.setPreferredSize(parent.getBtSize());
		btnUseFavoriteOrder.setBackground(parent.getBtColor());
		btnUseFavoriteOrder.setFont(parent.getBtFont());
		panel_2.add(btnUseFavoriteOrder);
		
		JPanel panel_3 = new JPanel();
		panel_4.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		cAddr_cb = new JComboBox(locations.toArray());
		
		
		// Continue button
		JButton cont = new JButton("Continue");
		cont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					// Check if required customer fields are filled out
					if(phone_tf.getText().isEmpty() ||
							(cAddr_cb.getSelectedIndex() < 0)){
						// If not then tell user they need it before continuing
						GUI_Controller.setKeyboardVisible(false);
						JOptionPane.showMessageDialog(parent, "Customer information not filled out please complete " +
								"this before continuing.");
					}
					else{
						customer = new Customer(phone_tf.getText(), custName_tf.getText(),(String)cAddr_cb.getSelectedItem());
						customer.addCustomer(customer);
						
						GUI_Controller.setKeyboardVisible(false);
						
						parent.setCustomer(customer);
						reset();
						parent.mainMenuCommand("takeOrder");
					}
			}
		});
		panel_3.add(cont);
		cont.setPreferredSize(parent.getBtSize());
		cont.setBackground(parent.getBtColor());
		cont.setFont(parent.getBtFont());
		
		panel_3.setBackground(parent.getBgColor());
		panel_4.setBackground(parent.getBgColor());
		panel_2.setBackground(parent.getBgColor());
		
				rightpanelcontainer.add(rightpanel);
		
		fullpanel.getContentPane().add(leftpanelcontainer, BorderLayout.WEST);
		fullpanel.getContentPane().add(centerpanel, BorderLayout.CENTER);
		centerpanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_5 = new JPanel();
		centerpanel.add(panel_5);
		panel_5.setBackground(parent.getBgColor());
		panel_5.setLayout(new BorderLayout(0, 30));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel_6.add(panel);
		panel.setBackground(parent.getBgColor());
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{95, 0, 0};
		gbl_panel.rowHeights = new int[]{14, 14, 14, 22, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel phone_l = new JLabel("Phone Number:     ");
		
		GridBagConstraints gbc_phone_l = new GridBagConstraints();
		gbc_phone_l.anchor = GridBagConstraints.NORTHEAST;
		gbc_phone_l.insets = new Insets(0, 0, 5, 5);
		gbc_phone_l.gridx = 0;
		gbc_phone_l.gridy = 0;
		panel.add(phone_l, gbc_phone_l);
		
		phone_tf = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(phone_tf, gbc_textField);
		phone_tf.setColumns(10);
		JLabel cName_l = new JLabel("Customer name:    ");
		GridBagConstraints gbc_cName_l = new GridBagConstraints();
		gbc_cName_l.anchor = GridBagConstraints.NORTHEAST;
		gbc_cName_l.insets = new Insets(0, 0, 5, 5);
		gbc_cName_l.gridx = 0;
		gbc_cName_l.gridy = 1;
		panel.add(cName_l, gbc_cName_l);
		
		custName_tf = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		panel.add(custName_tf, gbc_textField_1);
		custName_tf.setColumns(10);
		JLabel cAddr_l = new JLabel("Customer Address: ");
		GridBagConstraints gbc_cAddr_l = new GridBagConstraints();
		gbc_cAddr_l.anchor = GridBagConstraints.NORTHWEST;
		gbc_cAddr_l.insets = new Insets(0, 0, 5, 5);
		gbc_cAddr_l.gridx = 0;
		gbc_cAddr_l.gridy = 2;
		panel.add(cAddr_l, gbc_cAddr_l);
		GridBagConstraints gbc_cAddr_cb = new GridBagConstraints();
		gbc_cAddr_cb.insets = new Insets(0, 0, 5, 0);
		gbc_cAddr_cb.anchor = GridBagConstraints.NORTH;
		gbc_cAddr_cb.gridx = 1;
		gbc_cAddr_cb.gridy = 2;
		panel.add(cAddr_cb, gbc_cAddr_cb);
		cAddr_cb.setSelectedIndex(-1);
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.SOUTH);
		panel_6.setBackground(parent.getBgColor());
		panel_7.setBackground(parent.getBgColor());
		
		// Initialize name and location fields to uneditable
		custName_tf.setEnabled(false);
		cAddr_cb.setEnabled(false);
		phone_tf.addMouseListener(fl);
		custName_tf.addMouseListener(fl);
		
		phone_tf.setText("5856983169");


//Add ActionListeners
		Back.addActionListener(bt);
		cont.addActionListener(bt);
	}
	
	/**
	 * Gets the container of this pane
	 * @return The container of this pane
	 */
	public Container getContainer()
	{
		return fullpanel.getContentPane();
	}
	
	/**
	 * Resets the fields to the default state, uneditable and blank.
	 */
	public void reset(){
		phone_tf.setText("");
		custName_tf.setText("");
		custName_tf.setEnabled(false);
		cAddr_cb.setSelectedIndex(-1);
		cAddr_cb.setEnabled(false);
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
				reset();
				parent.mainMenuCommand("mainmenu");
			}
		}
	}
}//GUI_CustomerLookup