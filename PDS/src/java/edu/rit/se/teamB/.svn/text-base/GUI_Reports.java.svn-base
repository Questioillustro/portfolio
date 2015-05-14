/**
 * 
 */
package edu.rit.se.teamB;

/**
 * @author Stephen Brewster
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class GUI_Reports extends JPanel{

	//Private variables
	private GUI_Controller parent;
	private JTextArea orderDetails = new JTextArea();
	private JButton back = new JButton();
	private JButton search = new JButton("Search");
	private DefaultListModel orderListModel;
	private JList orderList;
	private JScrollPane orderscroller, detailscroller;
	private JFrame fullpanel;
	private String[] columnnames = {"Phone/ID", "Name","Location","Status","Item Count", "Completion"};
	private JTable table;
	private AbstractTableModel tableModel;
	private String[][] data;
	private ArrayList<Order> orders;
	
	/**
	 * Main menu Constructor that initializes all the buttons and action listeners
	 */
	public GUI_Reports(GUI_Controller p)
	{
		parent = p;
		initialize();
	}
	
	/**
	 * Initializer
	 */
	private void initialize(){
		btlisten bt = new btlisten();
		data = new String[100][6];
		back.setPreferredSize(parent.getBtSize());
		back.setIcon(parent.getBackArrow());
		back.setHorizontalTextPosition(JButton.CENTER);
		back.setActionCommand("Back");
		
		for(int i = 0; i < 100; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				data[i][j] = "";
			}
		}
		
		tableModel = new DefaultTableModel(data, columnnames);
		table = new JTable(tableModel);
		orderscroller  = new JScrollPane(table);
		
		orderDetails.setEditable(false);
		orderDetails.setFont(new Font("Calibri", Font.PLAIN, 12));
		detailscroller = new JScrollPane(orderDetails);
		detailscroller.setPreferredSize(new Dimension(200,370));
		
		fullpanel = new JFrame();
	
//Left Panel
		JPanel leftpanelcontainer = new JPanel();
		leftpanelcontainer.setBackground(parent.getBgColor());
		leftpanelcontainer.setBorder(parent.getPanelBorder());
		leftpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel leftpanel = new JPanel();
		leftpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		leftpanel.setLayout(new GridLayout(3,1,0,0));
		leftpanel.setBackground(parent.getBgColor());
	
			JPanel left_subpanel1 = new JPanel();
			left_subpanel1.setBackground(parent.getBgColor());
			
			JPanel left_subpanel2 = new JPanel();
			
			JPanel left_subpanel3 = new JPanel();

//Right Panel
		JPanel rightpanelcontainer = new JPanel();	
		rightpanelcontainer.setBackground(parent.getBgColor());
		rightpanelcontainer.setBorder(parent.getPanelBorder());
		rightpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel rightpanel = new JPanel();
		rightpanel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
		rightpanel.setBackground(parent.getBgColor());	
			
			JPanel right_subpanel1 = new JPanel();
			right_subpanel1.setBorder(BorderFactory.createTitledBorder("Order Details"));
			right_subpanel1.setBackground(parent.getBgColor());
	
//Center Panel
		JPanel centercontainer = new JPanel();
		centercontainer.setBackground(parent.getBgColor());
		centercontainer.setLayout(new GridLayout(2,1));
		centercontainer.setBorder(BorderFactory.createEmptyBorder(30,20,20,20));
		
		JPanel centerpanel = new JPanel();
		centerpanel.setLayout(new GridLayout(1,1));
		centerpanel.setBorder(BorderFactory.createTitledBorder("Order Display"));
		
			JPanel reportspanel = new JPanel();
			
		
		
//Build Panels		
		centerpanel.add(orderscroller);
		centercontainer.add(centerpanel);
		
		left_subpanel1.add(back);
		leftpanel.add(left_subpanel1);
		leftpanelcontainer.add(leftpanel);
		
		right_subpanel1.add(detailscroller);
		rightpanel.add(right_subpanel1);
		rightpanelcontainer.add(rightpanel);
		
		fullpanel.add(leftpanelcontainer, BorderLayout.WEST);
		fullpanel.add(centercontainer, BorderLayout.CENTER);
		fullpanel.add(rightpanelcontainer, BorderLayout.EAST);

//Add ActionListeners
		back.addActionListener(bt);
		search.addActionListener(bt);
		
		MouseListener mouseListener = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		         if (e.getClickCount() == 1) {
		        	 updateDetails();
		          }//if
		     }
		 };
		 table.addMouseListener(mouseListener);
	}
	
	/**
	 * Updates order display window
	 * 
	 */
	public void updateOrders(ArrayList<Order> o)
	{
		orders = o;
		int j = orders.size()-1;
		for(int i = 0; i < 100; i++)
		{
			if(i < orders.size())
			{
				tableModel.setValueAt(orders.get(j).getID(), i, 0);
				tableModel.setValueAt(orders.get(j).getCustomer().getName(), i, 1);
				tableModel.setValueAt(orders.get(j).getlocation(), i, 2);
				tableModel.setValueAt(orders.get(j).getStatus(), i, 3);
				tableModel.setValueAt(Integer.toString(orders.get(j).getNumItems()), i, 4);
				tableModel.setValueAt(Integer.toString(orders.get(j).getCompletionPercent()) + "%", i, 5);
				j--;
			}
		}
		if(table.getSelectedRow() >= 0)
			updateDetails();
	}
	
	/**
	 * Updates the details of selected order
	 */
	private void updateDetails()
	{
		int index = table.getSelectedRow();
		index = orders.size() - index - 1;
		if(index < orders.size())
			orderDetails.setText(orders.get(index).printOrder());
	}
	
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
			if(e.getActionCommand().equals("Search"))
			{
				
			}
		}
	}
}//GUI_MainMenu
