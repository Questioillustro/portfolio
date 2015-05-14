package edu.rit.se.teamB;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

public class GUI_TakeOrder extends JPanel
{
	private JFrame frame;
	private JPanel fullpanel;

	private JButton cancel,
					back,
					menuItemButtons[],
					placeorder,
					remove,
					removeTop,
					finish,
					modify;
	private JLabel subtotal_l,
				   tax_l,
				   total_l,
				   etd_l;
	private Menu menu;
	private JTextArea itemDisplay;
	private JTextField subtotal,
					   tax,
					   total,
					   etd;
	private JScrollPane scroller;
	private JScrollPane topScroller;
	
	private List<Item> menuItems;
	private List<String> menuItemString;
	private List<String> toppings;
	
	private JList topList;
	private JList itemList;

	private DefaultListModel topListModel;
	private DefaultListModel itemListModel;

	private JCheckBox right;
	private JCheckBox left;
	
	private boolean addTopping;
	private boolean addDressing;
	
	private Order order;
	private Customer customer;
	private int numMenuButtons = 10;
	private GUI_Controller parent;
	private DecimalFormat f = new DecimalFormat("##0.00");
	
	
	//Constructor ****************	//Constructor ****************
	public GUI_TakeOrder(GUI_Controller p)
	{
		order = new Order();
		customer = new Customer();
		parent = p;
		initialize();
		
	}//Constructor()
	
	public Container getContainer()
	{
		return frame.getContentPane();
	}
	/**
	 * Initializes the panel
	 */
	private void initialize()
	{
		btlisten bt = new btlisten();
		menu = new Menu();
		menuItems = menu.getItemList();
		menuItemString = new ArrayList<String>();
		toppings = menu.getToppings();
		menuItemButtons = new JButton[numMenuButtons];
		
		frame = new JFrame();
		fullpanel = new JPanel();
			fullpanel.setLayout(new BorderLayout());
			
		//Initialize the menu for Item information retrieval

		itemListModel = new DefaultListModel();
		itemList = new JList(itemListModel);
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemList.setSelectedIndex(0);
		itemList.setVisibleRowCount(10);
		scroller = new JScrollPane(itemList);
		scroller.setPreferredSize(new Dimension(210,150));
		scroller.setBorder(BorderFactory.createTitledBorder("Order Items"));
		
		topListModel = new DefaultListModel();
		topList = new JList(topListModel);
		topList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		topList.setSelectedIndex(0);
		topList.setVisibleRowCount(10);
		topScroller = new JScrollPane(topList);
		topScroller.setPreferredSize(new Dimension(210,150));
		topScroller.setBorder(BorderFactory.createTitledBorder("Item Modifications"));
		
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(parent.getBtSize());
		cancel.setBackground(parent.getBtColor());
		
		back = new JButton();
		back.setIcon(parent.getBackArrow());
		back.setHorizontalTextPosition(JButton.CENTER);
		back.setActionCommand("Back");
		back.setPreferredSize(parent.getBtSize());
		back.setBackground(parent.getBtColor());
		
		modify = new JButton("Modify");
		modify.setBackground(parent.getBtColor());
		
		remove = new JButton("Remove");
		remove.setActionCommand("RemoveItem");
		
		removeTop = new JButton("Remove");
		removeTop.setActionCommand("RemoveTop");
		
		placeorder = new JButton("Place Order");

		itemDisplay = new JTextArea("");
		itemDisplay.setEditable(false);
		
		subtotal_l = new JLabel("Subtotal");
		subtotal = new JTextField("");
			subtotal.setEditable(false);
		
		tax_l = new JLabel("Tax");
		tax = new JTextField("");
			tax.setPreferredSize(new Dimension(100,25));
			tax.setEditable(false);
		
		total_l = new JLabel("Total");
		total = new JTextField("");
			total.setEditable(false);
			
		etd_l = new JLabel("ETD");
		etd = new JTextField("");
			etd.setEditable(false);		
			
		ImageIcon pizza = new ImageIcon(getClass().getResource("pizza.gif"));
		JLabel pizza_l = new JLabel(pizza);
		right = new JCheckBox();
			right.setSelected(true);
		left = new JCheckBox();
			left.setSelected(true);
		
		finish = new JButton("Finish Toppings");
		finish.addActionListener(bt);
		
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
			
//Create display RIGHT side panel *******************************************
		JPanel rightpanelcontainer = new JPanel();	
		rightpanelcontainer.setBackground(parent.getBgColor());
		rightpanelcontainer.setBorder(parent.getPanelBorder());
		rightpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
			
			JPanel rightpanel= new JPanel(new GridLayout(4,1));
			rightpanel.setPreferredSize(new Dimension(230,850));
			rightpanel.setBackground(parent.getBgColor());		
			
				JPanel right_subpanel1Container = new JPanel();
				right_subpanel1Container.setBackground(parent.getBgColor());
				JPanel right_subpanel1 = new JPanel(new GridLayout(2,1));
				right_subpanel1.setBackground(parent.getBgColor());
				
				JPanel right_subpanel2Container = new JPanel();
				right_subpanel2Container.setBackground(parent.getBgColor());
				JPanel right_subpanel2 = new JPanel(new GridLayout(2,1));
				right_subpanel2.setBackground(parent.getBgColor());
		
				JPanel right_subpanel3Container = new JPanel();
				right_subpanel3Container.setBackground(parent.getBgColor());
				JPanel right_subpanel3 = new JPanel(new GridLayout(2,1,0,0));
				right_subpanel3.setBackground(parent.getBgColor());
				
				JPanel right_subpanel4Container = new JPanel();
				right_subpanel4Container.setBackground(parent.getBgColor());
				JPanel right_subpanel4 = new JPanel(new GridLayout(2,1,0,0));
				right_subpanel4.setBackground(parent.getBgColor());
				
		
		//Display and remove/modify buttons
		JPanel display = new JPanel();
		display.setBackground(parent.getBgColor());
		display.add(scroller);
				
		
		//MiniPanel for modify/remove buttons
		JPanel orderModButtons = new JPanel();
		orderModButtons.setBackground(parent.getBgColor());
		orderModButtons.add(modify);
		orderModButtons.add(remove);
		
		//MiniPanel for topping view
		JPanel toppingWindow = new JPanel();
		toppingWindow.setBackground(parent.getBgColor());
		toppingWindow.add(topScroller);
		
		//MiniPanel for toppping remove button
		JPanel removeTopper = new JPanel();
		removeTopper.setBackground(parent.getBgColor());
		removeTopper.add(removeTop);
		
		//Minipanel for pizza side selection
		JPanel pizzaside = new JPanel();
		pizzaside.setBackground(parent.getBgColor());
		pizzaside.add(left);
		pizzaside.add(pizza_l);
		pizzaside.add(right);
		
		//MiniPanel for finish button
		JPanel finishpanel = new JPanel();
		finishpanel.setBackground(parent.getBgColor());
		finishpanel.add(finish);
		
		//Creates a special panel to contain the table of totals and companion labels
		JPanel totalTable = new JPanel(new GridLayout(4,2));
		totalTable.setBackground(parent.getBgColor());
			
		//Add all labels and text fields to the panel
			totalTable.add(subtotal_l);
			totalTable.add(subtotal);
			totalTable.add(tax_l);
			totalTable.add(tax);
			totalTable.add(total_l);
			totalTable.add(total);
			totalTable.add(etd_l);
			totalTable.add(etd);
		
		JPanel placeorderpanel = new JPanel();
		placeorderpanel.setBackground(parent.getBgColor());
		placeorderpanel.add(placeorder);
		
//Create center panel
		JPanel centercontainer = new JPanel();
		centercontainer.setBackground(parent.getBgColor());
		centercontainer.setBorder(parent.getPanelBorder());
		
		JPanel center = new JPanel();
		center.setBackground(parent.getBgColor());
		center.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
		center.setLayout(new GridLayout(2,5,10,10));
		JPanel itemPanels[] = new JPanel[numMenuButtons];

		for(int i = 0; i < numMenuButtons; i++)
		{
			itemPanels[i] = new JPanel();
			if(i < menuItems.size())
			{
				menuItemString.add(menuItems.get(i).getItemName());
				menuItemButtons[i] = new JButton(menuItems.get(i).getItemName());
				menuItemButtons[i].addActionListener(bt);
			}	
			else
			{
				menuItemButtons[i] = new JButton();
			}
			menuItemButtons[i].setPreferredSize(parent.getBtSize());
			menuItemButtons[i].setBackground(parent.getBtColor());
			center.add(menuItemButtons[i]);
		}
//Build panels
		//Add order text area, Totals Table, and Place Order button to the display pane
		right_subpanel1.add(display);
		right_subpanel1.add(orderModButtons);
		right_subpanel1Container.add(right_subpanel1);
		
		right_subpanel2.add(toppingWindow);	
		right_subpanel2.add(removeTopper);
		right_subpanel2Container.add(right_subpanel2);
		
		right_subpanel3.add(totalTable);
		right_subpanel3.add(placeorderpanel);
		right_subpanel3Container.add(right_subpanel3);
			
		right_subpanel4.add(pizzaside);
		right_subpanel4.add(finishpanel);
		right_subpanel4Container.add(right_subpanel4);
		
		rightpanel.add(right_subpanel1Container);
		rightpanel.add(right_subpanel2Container);
		rightpanel.add(right_subpanel4Container);
		rightpanel.add(right_subpanel3Container);
		
		rightpanelcontainer.add(rightpanel);

		left_subpanel1.add(back);
		left_subpanel2.add(cancel);
		leftpanel.add(left_subpanel1);
		leftpanel.add(left_subpanel2);
		leftpanelcontainer.add(leftpanel);
		
		centercontainer.add(center);
		
		fullpanel.add(rightpanelcontainer, BorderLayout.EAST); //Finalize the right side of the TakeOrder panel		
		fullpanel.add(leftpanelcontainer, BorderLayout.WEST); //Finalizes left side of TakeOrder panel
		fullpanel.add(centercontainer, BorderLayout.CENTER);
		frame.add(fullpanel);
		
		//Create listeners for all buttons ******************************************
			cancel.addActionListener(bt);
			back.addActionListener(bt);
			placeorder.addActionListener(bt);
			remove.addActionListener(bt);
			removeTop.addActionListener(bt);
			modify.addActionListener(bt);
			
		//Setup mousevent for item selection in the order display
		MouseListener mouseListener = new MouseAdapter() {
			     public void mouseClicked(MouseEvent e) {
			         if (e.getClickCount() == 1) {
		        		 int indexI = itemList.getSelectedIndex();		             

			        	 if(e.getComponent().equals(itemList))
			        	 {
			        		 topListModel.clear();
	
			        		 if(order.getItems().get(indexI).isToppable())
			        			 updateModList(order.getItems().get(indexI).getMod());
			        	 }

			             if(order.getItems().get(indexI).isToppable()){
     					 	updateModList(order.getItems().get(indexI).getMod());
			             	
			             }
     					 else
     						topListModel.clear();

    		          }//if
			     }
			 };
			 itemList.addMouseListener(mouseListener);
			 topList.addMouseListener(mouseListener);
	}//initialize()
	
	/**
	 * Populates grid of buttons with List
	 * @param List<String>
	 */
	public void rePopGrid(List<String> data)
	{
		for(int i = 0; i < numMenuButtons; i++)
		{
			if(i < data.size())
			{
				menuItemButtons[i].setText(data.get(i));
				menuItemButtons[i].setActionCommand(data.get(i));
			}
			else
			{
				menuItemButtons[i].setText("");
				menuItemButtons[i].setActionCommand("");
			}
		}	
	}
	
	/**
	 * ReValidates the menu in the event of changes during runtime
	 * @param ArrayList<Item> 
	 */
	public void reValidateMenu(List<Item> menu)
	{
		menuItems = menu;
		menuItemString.clear();
		for(int i = 0; i < numMenuButtons; i++)
		{
			if(i < menu.size())
			{
				menuItemString.add(menuItems.get(i).getItemName());
			}
		}
		rePopGrid(menuItemString);
	}
	
	/**
	 * Sets a customer
	 * @param Customer
	 */
	public void setCustomer(Customer c)
	{
		customer = new Customer(c.getPhone(), c.getName(), c.getLocation());
	}
	
	/**
	 * Imports an existing order
	 * @param Order
	 */
	public void setOrder(Order o)
	{
		itemListModel.clear();
		topListModel.clear();
		order = o;
		
		for(int i = 0; i < order.getItems().size(); i++)
		{
			itemListModel.add(i, order.getItems().get(i).getItemName());
		}
		
		itemList.setSelectedIndex(itemListModel.size()-1);
		if(((String)itemListModel.get(itemListModel.size() - 1)).contains(" Pizza"))
			updateModList(order.getItems().get(order.getItems().size()-1).getMod());
		
		refreshTotals();
	}
	
	/**
	 * Multiplies the selected item using the value in the quantity field
	 *@param int
	 *
	 */
	private void changeQuantity(int q)
	{
		int index = itemList.getSelectedIndex();
		Item temp = order.getItems().get(index);
		
		for(int i = 0; i < q - 1; i++)
		{
			addItem(new Item(temp));
		}
	}
	
	/**
	 * Adds an item to the order
	 * @param item
	 */
	private void addItem(Item item)
	{
		order.addItem(new Item(item.getItemName(), 
							   item.getPrice(),
							   item.getOvenSpaceUnits(),
							   item.getPrepTime(),
							   item.getCookTime(),
							   item.getWaitTime(),
							   item.isToppable()));
		topListModel.clear();

		//Output handling
		int end = itemListModel.getSize();
		itemListModel.add(end, item.getItemName());
		itemList.setSelectedIndex(end);
		
		if(item.isToppable()){		
			if(item.getItemName().contains(" Pizza"))
			{
				addTopping = true;
				addTopping("Pepperoni");
				addTopping("Cheese");
			}
			rePopGrid(toppings);
			addTopping = true;
		}
			
		refreshTotals();
	}
	
	/**
	 * Removes the currently selected item from the order
	 */
	private void removeItem()
	{
		if(itemListModel.size() > 0)
		{
			int index = itemList.getSelectedIndex();

			order.removeItem(index);				
			itemListModel.remove(index);
			refreshTotals();

			if(index > 0)
			{
				itemList.setSelectedIndex(index - 1);
			}//else if()
			else
			{
				itemList.setSelectedIndex(index);
			}
		}//if()
	}//removeItem()
	
	/**
	 * Adds a topping to the selected item
	 * @param String
	 */
	private void addTopping(String t)
	{
		String side = "";
		if(right.isSelected() && left.isSelected())
			side = " - Whole";
		else if(right.isSelected() && !left.isSelected())
			side = " - Right";
		else if(!right.isSelected() && left.isSelected())
			side = " - Left";
		else
			return;
			
		int index = itemList.getSelectedIndex();
		order.getItems().get(index).addMod(t + side);
		
		index = topListModel.size();
		topListModel.add(index, t + side);
		topList.setSelectedIndex(index);
		
		order.refreshTotal();
		refreshTotals();
	}
	
	/**
	 * Adds a topping to the selected item
	 */
	private void removeTopping()
	{
		int indexI = itemList.getSelectedIndex();
		int indexT = topList.getSelectedIndex();
		order.getItems().get(indexI).removeMod(indexT);
		
		topListModel.remove(indexT);
		if(topListModel.size() > 1)
		{
			topList.setSelectedIndex(indexT - 1);
		}
		else if(topListModel.size() == 1)
		{
			topList.setSelectedIndex(0);
		}
		
		order.refreshTotal();
		refreshTotals();
	}
	
	/**
	 * Updates the list of modifications
	 */
	private void updateModList(List<String> mods)
	{
		topListModel.clear();
		for(int i = 0; i < mods.size(); i++)
		{
			topListModel.add(i, mods.get(i));
		}
	}
	
	/**
	 * Refreshes the totals for the display after an item has been added/removed/changed
	 */
	private void refreshTotals()
	{
		subtotal.setText(" $" + f.format(order.getSubTotal()));
		tax.setText(" $" + f.format(order.getTax()));
		total.setText(" $" + f.format(order.getTotal()));
		etd.setText(Integer.toString(parent.getETD(customer.getLocation(), order.getItems())/60));
	}

	/**
	 * Action Listener
	 * @author sxb7781
	 *
	 */
	private class btlisten implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Cancel")) 
			{ 
				order = new Order();
				topListModel.clear();
				itemListModel.clear();
				if(addTopping)
					addTopping = false;
									
				//Reset buttons and fields
				subtotal.setText("");
				tax.setText("");
				total.setText("");
				etd.setText("");
				
				parent.mainMenuCommand("mainmenu");
			}
			
			if(e.getActionCommand().equals("Back")) 
			{ 
				parent.mainMenuCommand("mainmenu");
			}
			
			if(e.getActionCommand().equals("Place Order") && order.getItems().size() > 0)
			{ 
				order.setCustomer(customer);
				order.setlocation(customer.getLocation());
				order.setETD(Integer.parseInt(etd.getText()));
				parent.newOrder(order);
				
				//Clear variables for next order
				order = new Order();
				topListModel.clear();
				itemListModel.clear();
				if(addTopping)
					addTopping = false;
									
				//Reset buttons and fields
				subtotal.setText("");
				tax.setText("");
				total.setText("");
				etd.setText("");
				parent.mainMenuCommand("mainmenu");
			}//place order
			
			if(e.getActionCommand().equals("RemoveItem") && itemListModel.size() > 0) 
			{
				removeItem();
				rePopGrid(menuItemString);
				if(addTopping)
					addTopping = false;
			}
			
			if(e.getActionCommand().equals("Modify") && itemListModel.size() > 0) 
			{
				int index = itemList.getSelectedIndex();
				if(order.getItems().get(index).isToppable())
				{
					rePopGrid(toppings);
					addTopping = true;
				}
			}
			
			
			if(e.getActionCommand().equals("RemoveTop") && topListModel.size() > 0) 
			{
				removeTopping();
			}
			
			if(e.getActionCommand().equals("Finish Toppings")) 
			{
				addTopping = false;
				rePopGrid(menuItemString);
			}
			
			//Checks for an action on the menu buttons
			for(int i = 0; i < menuItemButtons.length; i++)
			{
				if(e.getActionCommand().equals(menuItemButtons[i].getActionCommand()) && !e.getActionCommand().equals(""))
					{ 
						if(addTopping)
							addTopping(toppings.get(i));
						else
							addItem(menuItems.get(i));
	   				}
			}//for()
		}//actionPerformed()
	}//ActionListener()
}