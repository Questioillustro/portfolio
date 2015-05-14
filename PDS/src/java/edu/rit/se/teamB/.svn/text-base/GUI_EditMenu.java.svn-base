package edu.rit.se.teamB;

/**
 * @author Tony Chen
 * @Team: Autobots
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GUI_EditMenu extends JPanel {

	//Private variables
	private GUI_Controller parent;
	private JFrame fullpanel;
	private Menu menu;
	private btlisten bt = new btlisten();
	private menulisten menuclick = new menulisten();
	private JTextField ItemName = new JTextField();
	private JTextField Price = new JTextField();
	private JTextField prepTime = new JTextField();
	private JTextField ovenSpace = new JTextField();
	private JTextField cookTime = new JTextField();
	private JTextField waitTime = new JTextField();
	private int index = -999;
	private String olditemname;
	private JCheckBox checkBox;
	private ArrayList<JButton> buttonlist = new ArrayList<JButton>();
	private JPanel centerpanel = new JPanel();
	
	/**
	 * Main menu Constructor that initializes all the buttons and action listeners
	 */
	public GUI_EditMenu(GUI_Controller p) {
		parent = p;
		initialize();
	}
	
	/**
	 * Initialize method that constructs all the needed panels and sets the layouts
	 */
	private void initialize() {
		Mouse_Listener ml = new Mouse_Listener();
		
		fullpanel = new JFrame();
		menu = parent.getMenu();
		
		JButton Back = new JButton();
		Back.setIcon(parent.getBackArrow());
		Back.setHorizontalTextPosition(JButton.CENTER);
		Back.setActionCommand("Back");
		
		JButton LogOut = new JButton("Log Out");
		
		Back.setPreferredSize(parent.getBtSize());
		Back.setBackground(parent.getBtColor());
		Back.setFont(parent.getBtFont());
		
		LogOut.setPreferredSize(parent.getBtSize());
		LogOut.setBackground(parent.getBtColor());
		LogOut.setFont(parent.getBtFont());
		
//Left Panel
		JPanel leftpanelcontainer = new JPanel();
		leftpanelcontainer.setBackground(parent.getBgColor());
		leftpanelcontainer.setBorder(parent.getPanelBorder());
		leftpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel leftpanel = new JPanel();
		leftpanel.setLayout(new GridLayout(3,1,0,30));
		leftpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		leftpanel.setBackground(parent.getBgColor());
		
		Back.addActionListener(bt);
		LogOut.addActionListener(bt);

		leftpanel.add(Back);
		//leftpanel.add(LogOut);
		leftpanelcontainer.add(leftpanel);
		
//Right Panel			
		
		JPanel rightpanelcontainer = new JPanel();
		rightpanelcontainer.setBackground(parent.getBgColor());
		rightpanelcontainer.setBorder(parent.getPanelBorder());
		rightpanelcontainer.setPreferredSize(parent.getSidePanelWidth());
		
		JPanel rightpanel = new JPanel();
		rightpanel.setLayout(new GridLayout(8,2));
		rightpanel.setBackground(parent.getBgColor());
		
		ItemName.setPreferredSize(new Dimension(110,30));
		cookTime.setPreferredSize(new Dimension(110,30));
		waitTime.setPreferredSize(new Dimension(110,30));
		prepTime.setPreferredSize(new Dimension(110,30));
		ovenSpace.setPreferredSize(new Dimension(110,30));
		Price.setPreferredSize(new Dimension(110,30));
		
		JLabel jname = new JLabel("Item Name:");
		JLabel jprice = new JLabel("Item Price:");
		JLabel jprep = new JLabel("Prep Time:");
		JLabel jcook = new JLabel("Cook Time:");
		JLabel joven = new JLabel("Oven Space:");
		JLabel jwait = new JLabel("Wait Time:");
		
		rightpanel.add(jname);
		rightpanel.add(ItemName);
		rightpanel.add(jprice);
		rightpanel.add(Price);
		rightpanel.add(jprep);
		rightpanel.add(prepTime);
		rightpanel.add(jcook);
		rightpanel.add(cookTime);
		rightpanel.add(joven);
		rightpanel.add(ovenSpace);
		rightpanel.add(jwait);
		rightpanel.add(waitTime);
		
		ItemName.addMouseListener(ml);
		Price.addMouseListener(ml);
		prepTime.addMouseListener(ml);
		cookTime.addMouseListener(ml);
		ovenSpace.addMouseListener(ml);
		waitTime.addMouseListener(ml);
		
		JButton set = new JButton("Set");
		JButton reset = new JButton("Reset");
		
		set.addActionListener(bt);
		reset.addActionListener(bt);
		
		JLabel lblCanHaveToppings = new JLabel("Can have toppings?");
		rightpanel.add(lblCanHaveToppings);
		
		checkBox = new JCheckBox("");
		rightpanel.add(checkBox);
		rightpanel.add(set);
		rightpanel.add(reset);
		
		JPanel rightpanel2 = new JPanel();
		rightpanel2.setLayout(new GridLayout(2,1));
		rightpanel2.setBackground(parent.getBgColor());
		
		JButton Add = new JButton("Add");
		Add.addActionListener(bt);
		Add.setPreferredSize(parent.getBtSize());
		JButton Remove = new JButton("Remove");
		Remove.addActionListener(bt);
		Remove.setPreferredSize(parent.getBtSize());
		rightpanel2.add(Add);
		rightpanel2.add(Remove);
		
		rightpanelcontainer.add(rightpanel);
		rightpanelcontainer.add(rightpanel2);
	
//Center Panel		
		
		centerpanel.setBorder(parent.getPanelBorder());
		centerpanel.setBackground(parent.getBgPanelColor());
		
//Populate the Panel with Menu Items
		
		for (int i = menu.getItemList().size()-1; i >= 0; i--) {
			JPanel c = new JPanel();
			JButton b = new JButton(menu.getItemList().get(i).getItemName());
			b.addActionListener(menuclick);
			b.setPreferredSize(parent.getBtSize());
			buttonlist.add(b);
			c.add(buttonlist.get(menu.getItemList().size()-1 - i));
			c.setName(menu.getItemList().get(i).getItemName());
			centerpanel.add(c);
		}
		
		//centerpanel.add(new GUI_KeyBoard().getContainer());
		
		fullpanel.getContentPane().add(leftpanelcontainer, BorderLayout.WEST);
		fullpanel.getContentPane().add(centerpanel, BorderLayout.CENTER);
		fullpanel.getContentPane().add(rightpanelcontainer, BorderLayout.EAST);
		
	}
	
	/**
	 * Method to return the contents of the main panel
	 */
	public Container getContainer()	{
		return fullpanel.getContentPane();
	}

	/**
	 * ActionListener for the Menu buttons
	 */
	private class menulisten implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < menu.getItemList().size(); i++) {
				if (e.getActionCommand() == menu.getItemList().get(i).getItemName()) {
					index = i;
					olditemname = menu.getItem(i).getItemName();
				}
			}
			ItemName.setText(menu.getItem(index).getItemName());
			Price.setText(Double.toString(menu.getItem(index).getPrice()));
			prepTime.setText(Integer.toString(menu.getItem(index).getPrepTime()));
			cookTime.setText(Integer.toString(menu.getItem(index).getCookTime()));
			waitTime.setText(Integer.toString(menu.getItem(index).getWaitTime()));
			ovenSpace.setText(Integer.toString(menu.getItem(index).getOvenSpaceUnits()));
		}
	}
	
	/*
	 * ActionListener that triggers when a specific button is pressed
	 */
	private class btlisten implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Back")) {
				parent.mainMenuCommand("manage");
			}
			if(e.getActionCommand().equals("Logout")) {
				parent.mainMenuCommand("logout");
			}
			if(e.getActionCommand().equals("Remove")) {
				if(JOptionPane.showConfirmDialog(parent,"Do you really want to remove: " 
				+ ItemName.getText() 
				+ "?","Confirm Item Removal.",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					for (Component com: centerpanel.getComponents()) {
						if (com.getName() == olditemname) {
							centerpanel.remove(com);
							menu.getItemList().remove(index);
							buttonlist.remove(index);
						}
					}
				}
				clearfields();
				centerpanel.updateUI();
			}
			if(e.getActionCommand().equals("Add")) {
				if(JOptionPane.showConfirmDialog(parent,"Do you really want to add: " 
				+ ItemName.getText() 
				+ "?","Confirm Item Addition.",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					String item = ItemName.getText();
					Double price = Double.parseDouble(Price.getText());
					int cook = Integer.parseInt(cookTime.getText());
					int prep = Integer.parseInt(prepTime.getText());
					int wait = Integer.parseInt(waitTime.getText());
					int oven = Integer.parseInt(ovenSpace.getText());
					Item newitem = new Item(item,price,oven,prep,cook,wait, checkBox.isSelected());
					menu.ezadditem(newitem);
					JPanel c = new JPanel();
					c.setName(item);
					JButton j = new JButton(item);
					j.addActionListener(menuclick);
					j.setPreferredSize(parent.getBtSize());
					buttonlist.add(0, j);
					c.add(buttonlist.get(0));
					centerpanel.add(c, 0);
					centerpanel.updateUI();
				}
				clearfields();
			}
			if(e.getActionCommand().equals("Set") && index >= 0) {
				String item = ItemName.getText();
				Double price = Double.parseDouble(Price.getText());
				int cook = Integer.parseInt(cookTime.getText());
				int prep = Integer.parseInt(prepTime.getText());
				int wait = Integer.parseInt(waitTime.getText());
				int oven = Integer.parseInt(ovenSpace.getText());
				menu.getItem(index).setItemName(item);
				menu.getItem(index).setPrice(price);
				menu.getItem(index).setCookTime(cook);
				menu.getItem(index).setPrepTime(prep);
				menu.getItem(index).setWaitTime(wait);
				menu.getItem(index).setOvenSpaceUnits(oven);
				for (JButton j: buttonlist) {
					if (j.getText() == olditemname) {
						j.setText(item);
						j.addActionListener(menuclick);
					}
				}
				for (Component com: centerpanel.getComponents()) {
					if (com.getName() == olditemname) {
						com.setName(item);
					}
				}
				clearfields();
			}
			if(e.getActionCommand().equals("Reset")) {
				if (index >=0) {
					ItemName.setText(menu.getItem(index).getItemName());
					Price.setText(Double.toString(menu.getItem(index).getPrice()));
					prepTime.setText(Integer.toString(menu.getItem(index).getPrepTime()));
					cookTime.setText(Integer.toString(menu.getItem(index).getCookTime()));
					waitTime.setText(Integer.toString(menu.getItem(index).getWaitTime()));
					ovenSpace.setText(Integer.toString(menu.getItem(index).getOvenSpaceUnits()));
				}
				else {
					clearfields();
				}
			}
		}
	}
	
	/**
	 * Method to clear the textfields
	 */
	private void clearfields() {
		ItemName.setText("");
		Price.setText("");
		prepTime.setText("");
		cookTime.setText("");
		waitTime.setText("");
		ovenSpace.setText("");
		index = -999;
		olditemname = "";
	}
	
}//GUI_MainMenu