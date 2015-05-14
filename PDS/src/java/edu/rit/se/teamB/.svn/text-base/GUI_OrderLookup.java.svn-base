package edu.rit.se.teamB;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Tony Chen
 * @Team: AutoBots
 */
public class GUI_OrderLookup extends JPanel{

	private GUI_Controller parent;
	private JFrame fullpanel;
	private JTextField textFieldPhoneNumber = new JTextField();
	private JTextField textFieldCustomerName = new JTextField();
	private JTextField textFieldCustomerAddress = new JTextField();
	private JLabel LabelPhoneNumber = new JLabel("Phone Number:");
	private JLabel LabelCustomerName = new JLabel("Customer Name:");
	private JLabel LabelCustomerAddress = new JLabel("Customer Address:");
	private JTextArea status = new JTextArea();
	private btlisten bt = new btlisten();
	private JButton editOrder = new JButton("Edit");
	//private JButton cancelOrder = new JButton("Cancel");
	private Order order;
	
	/**
	 * Main menu Constructor that initializes all the buttons and action listeners
	 */
	public GUI_OrderLookup(GUI_Controller p) {
		parent = p;
		initialize();
	}
	
	/**
	 * Method to initialize the Panels and construct the components
	 */
	public void initialize() {
		
		fullpanel = new JFrame();
		Mouse_Listener ml = new Mouse_Listener();
		
		editOrder.addActionListener(bt);
		textFieldPhoneNumber.setPreferredSize(new Dimension(200,30));
		textFieldPhoneNumber.addMouseListener(ml);

		status.setEditable(false);
		JScrollPane scroller = new JScrollPane(status);
		scroller.setBorder(BorderFactory.createTitledBorder("Order Details"));
		scroller.setPreferredSize(new Dimension(350,350));
		scroller.setBackground(Color.WHITE);
		
		JButton Back = new JButton();
		Back.setPreferredSize(parent.getBtSize());
		Back.setIcon(parent.getBackArrow());
		Back.setHorizontalTextPosition(JButton.CENTER);
		Back.setActionCommand("Back");
		Back.addActionListener(bt);
		
		JButton LogOut = new JButton("Log Out");
		LogOut.addActionListener(bt);
		LogOut.setPreferredSize(parent.getBtSize());
		
		JButton Search = new JButton("Search");
		Search.addActionListener(bt);
		
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
		

		left_subpanel1.add(Back);
		//left_subpanel2.add(LogOut);
		leftpanel.add(left_subpanel1);
		leftpanel.add(left_subpanel2);
		leftpanelcontainer.add(leftpanel);
		
		
//Center Panel	
		
		JPanel centerpanel = new JPanel();
		centerpanel.setBorder(parent.getPanelBorder());
		centerpanel.setBackground(parent.getBgColor());
		centerpanel.setBorder(parent.getPanelBorder());
		
		JPanel centercontainer = new JPanel();
		centercontainer.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
		centercontainer.setBackground(parent.getBgColor());
		
		JPanel centersubpanelcontainer = new JPanel();
		centersubpanelcontainer.setLayout(new BoxLayout(centersubpanelcontainer, BoxLayout.Y_AXIS));
		centersubpanelcontainer.setBackground(parent.getBgColor());

		
			JPanel center_subpanel1 = new JPanel();
			center_subpanel1.setBackground(parent.getBgColor());
		
			center_subpanel1.add(LabelPhoneNumber);
			center_subpanel1.add(textFieldPhoneNumber);
			center_subpanel1.add(Search);
		
			JPanel center_subpanel2 = new JPanel(new GridLayout(2,1));
			center_subpanel2.setBackground(parent.getBgColor());
				
				JPanel buttonpanel = new JPanel();
				buttonpanel.setBackground(parent.getBgColor());
				
				buttonpanel.add(editOrder);
				//buttonpanel.add(cancelOrder);
			
			center_subpanel2.add(scroller);
			center_subpanel2.add(buttonpanel);
			
			
		centersubpanelcontainer.add(center_subpanel1);
		centersubpanelcontainer.add(center_subpanel2);
		centercontainer.add(centersubpanelcontainer);
		
		centerpanel.add(centercontainer);
		
//Right Panel
		
		JPanel rcontain = new JPanel();
		
		rcontain.setBackground(parent.getBgColor());
		rcontain.setBorder(parent.getPanelBorder());
		rcontain.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel rightpanel = new JPanel();
		
		rightpanel.setLayout(new GridLayout(3,2));
		rightpanel.setBackground(parent.getBgColor());
		
		rightpanel.add(LabelCustomerName);
		textFieldCustomerName.setPreferredSize(new Dimension(100,30));
		textFieldCustomerName.setEditable(false);
		rightpanel.add(textFieldCustomerName);
		rightpanel.add(LabelCustomerAddress);
		textFieldCustomerAddress.setPreferredSize(new Dimension(100,30));
		textFieldCustomerAddress.setEditable(false);
		rightpanel.add(textFieldCustomerAddress);
		
		rcontain.add(rightpanel);
		
		fullpanel.add(leftpanelcontainer, BorderLayout.WEST);
		fullpanel.add(rcontain, BorderLayout.EAST);
		fullpanel.add(centerpanel, BorderLayout.CENTER);

	}

	/**
	 * Method to return the panel for the controller to display
	 * @return
	 */
	public Container getContainer()	{
		return fullpanel.getContentPane();
	}
	
	/**
	 * Action Listener for certain buttons
	 * @author Tony Chen
	 *
	 */
	private class btlisten implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Back")) {
				textFieldPhoneNumber.setText("");
				textFieldCustomerAddress.setText("");
				textFieldCustomerName.setText("");
				status.setText("");
				parent.mainMenuCommand("mainmenu");
			}
			if (e.getActionCommand().equals("Log Out")) {
				parent.mainMenuCommand("login");
			}
			if (e.getActionCommand().equals("Search")) {
				Customer customer = parent.lookupCust(textFieldPhoneNumber.getText());
				if(customer == null){
					JOptionPane.showMessageDialog(parent, "Customer not found, please enter PhoneNumber again.");
				}
				// Otherwise fill in found customer information
				else{
					//status.setEditable(true);
					order = parent.getOrder(textFieldPhoneNumber.getText());
					status.setText(order.printOrder());
					textFieldCustomerName.setText(order.getCustomer().getName());
					textFieldCustomerAddress.setText(order.getCustomer().getLocation());
				}
			}
			
			if (e.getActionCommand().equals("Edit") && order != null)
			{
				parent.editOrder(order);
			}
		}
	}
}
