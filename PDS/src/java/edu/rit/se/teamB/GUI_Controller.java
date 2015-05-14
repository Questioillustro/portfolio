package edu.rit.se.teamB;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;

public class GUI_Controller extends JFrame
{
	private User currentUser;
	private Login userDB = new Login();
	private JPanel cards;

	private GUI_TakeOrder takeOrder;
	private GUI_Login logIn;
	private GUI_MainMenu mainmenu;
	private GUI_Manage manage;
	private GUI_Simulation sim;
	private GUI_EditMenu editmenu;
	private GUI_Reports reports;
	private GUI_UserManager usermanager;
	private GUI_CustomerLookup custlookup;
	private GUI_OrderLookup orderlookup;
	private Controller pds;
	private static JFrame keyframe;
	private static GUI_KeyBoard keyboard;
	private static GUI_Controller frame;
	private GUI_EditLocations editlocations;
	
	
	static int loggedIn = 0;
	
	//Graphics scheme variables
	private Color bgColor, etchColor, bgPanelColor, btColor, btFontColor;
	private Border panelBorder; 
	private Dimension stdBtSize, sidePanelWidth;
	private Font btFont;
	private ImageIcon backarrow = new ImageIcon(getClass().getResource("backarrow.jpg"));
	private ImageIcon background = new ImageIcon(getClass().getResource("background.jpg"));

	private GUI_Controller()
	{
		setUpUserDB();
		keyboard = new GUI_KeyBoard(this);
		
		//Color options
		bgColor = new Color(250,250,250); //Active panel background color
		etchColor = new Color(220,230,230); //Highlight color for borders
		bgPanelColor = new Color (230,230,230); //Inactive background color
		//btColor = new Color(200,100,23);
		btColor = new JButton().getBackground(); //Default button color
		btFontColor = new Color(0,0,0); //Button font color
		
		//Border options
		panelBorder = BorderFactory.createEtchedBorder(etchColor, Color.BLACK);
		//panelBorder = BorderFactory.createLineBorder(Color.BLACK);
		
		//Dimension options
		stdBtSize = new Dimension(120,120);  //Standard button size
		sidePanelWidth = new Dimension(250,0); //Side panel width
		
		//Fonts
		btFont = new Font("Calibri", Font.BOLD, 14);
		
		// After loading users, create user manager pane
		mainmenu = new GUI_MainMenu(this);
		manage  = new GUI_Manage(this);
		logIn  = new GUI_Login(this);
		reports = new GUI_Reports(this);
		pds = new Controller(3, 3, this);
		takeOrder = new GUI_TakeOrder(this);
		editmenu  = new GUI_EditMenu(this);
		sim = new GUI_Simulation(this, 3, 3, 1.0, 2);		
		cards = new JPanel(new CardLayout());
		editlocations = new GUI_EditLocations(this);
		custlookup = new GUI_CustomerLookup(this);
		orderlookup = new GUI_OrderLookup(this);
		usermanager = new GUI_UserManager(this);

		
		//Build cardlayout
		cards.add(logIn.getContainer(), "log");
		cards.add(mainmenu.getContainer(), "mainmenu");
		cards.add(takeOrder.getContainer(), "takeOrder");
		cards.add(manage.getContainer(), "manage");
		cards.add(sim.getContainer(), "sim");
		cards.add(custlookup.getContainer(), "custlookup");
		cards.add(usermanager, "usermanager");
		cards.add(editmenu.getContainer(), "editmenu");
		cards.add(reports.getContainer(), "reports");
		cards.add(editlocations.getContainer(), "editlocations");
		cards.add(orderlookup.getContainer(), "orderlookup");
		add(cards);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true); 
		
		
		// When this window is closed, reopen the UserView
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
            	cleanUp();
            }
        });
	}	
	
	/**
	 * Load customer and user databases
	 */
	private void setUpUserDB(){
		// Load system users database
		userDB.loadUserDB();
		
		// Load customer database
		new Customer().loadCustomers();
	}
	
	/**
	 * Sets the customer in the takeOrder panel
	 * @param Customer
	 */
	public void setCustomer(Customer c)
	{
		takeOrder.setCustomer(c);
	}
	
	public Color getBtColor()
	{
		return btColor;
	}
	
	/**
	 * Returns the graphic for back buttons
	 * @return ImageIcon
	 */
	public ImageIcon getBackArrow()
	{
		return backarrow;
	}
	
	/**
	 * Returns the graphic for centerpanel background
	 * @return ImageIcon
	 */
	public ImageIcon getBackgroundImage()
	{
		return background;
	}
	
	/**
	 * Returns color for panel backgrounds
	 * @return Color
	 */
	public Color getBgColor()
	{
		return bgColor;
	}
	
	/**
	 * Returns color for button font
	 * @return Color
	 */
	public Color getBtFontColor()
	{
		return btFontColor;
	}
	
	/**
	 * Returns the standard border for panels
	 * @return Border
	 */
	public Border getPanelBorder()
	{
		return panelBorder;
	}
	
	/**
	 * Returns the bgColor for the bgPanel
	 * @Return Color
	 */
	public Color getBgPanelColor()
	{
		return bgPanelColor;
	}
	
	/**
	 * Returns the standard dimensions for JButtons
	 * @return Dimension
	 */
	public Dimension getBtSize()
	{
		return stdBtSize;
	}
	
	/**
	 * Returns the dimension for sizing the side panels
	 * @return
	 */
	public Dimension getSidePanelWidth()
	{
		return sidePanelWidth;
	}
	
	/**
	 * Gets the button font
	 * @return The font for buttons
	 */
	public Font getBtFont()
	{
		return btFont;
	}

	/**
	 * Performs closing functions on program exit
	 */
	public void cleanUp(){
		userDB.writeUserList();	
		pds.writeLocationList();
		pds.writeMenu();
		new Customer().writeCustomers();
		System.exit(0);
	}
	
	/**
	 * Returns the database of users with system permission 
	 * @return
	 */
	public Login getUserDB(){
		return userDB;
	}
	
	/**
	 * Gets the list of locations from controller
	 */
	public ArrayList<String> getLocations(){
		return pds.getLocations();
	}
	
	/**
	 * Gets the map of locations and times from controller
	 */
	public Map<String, Integer> getLocTime(){
		return pds.getLocTime();
	}
	
	/**
	 * Gets the Menu from controller and returns it
	 * @return Menu
	 */
	public Menu getMenu()
	{
		return pds.getMenu();
	}
	
	
	/**
	 * Receives command from main menu and displays the requested panel
	 */
	public void mainMenuCommand(String p)
	{
		if(p.equals("log"))
		{
			loggedIn = 0;
		}
		if(p.equals("takeOrder"))
		{
			takeOrder.reValidateMenu(pds.getMenu().getItemList());
		}
		
		CardLayout c1 = (CardLayout)cards.getLayout();
		c1.show(cards, p);
	}
	
	/**
	 * Changes the simulations speed by ratio
	 * @param r
	 */
	public void setSimSpeed(double r)
	{
		pds.setTimeRatio(r);
	}
	
	/**
	 * Passes an int to the controller to set the number of cooks
	 * @param c The number of cooks
	 */
	public void setCooks(int c)
	{
		pds.setCooks(c);
	}
	
	/**
	 * Sends list of active orders to the reports panel
	 * @param orders
	 */
	public void reportOrderStatus(ArrayList<Order> orders)
	{
		reports.updateOrders(orders);
	}

	/**
	 * Finds and returns a customer
	 * @param phone The phone number to look up by
	 * @return the Customer who has that phone number
	 */
	public Customer lookupCust(String phone){
		return Customer.lookUp(phone);
	}
	
	/**
	 * Sends a new order to the kitchen and resets the take order panel for the next order
	 * @param o The order to send to the kitchen
	 */
	public void newOrder(Order o){
		pds.sendOrderToKitchen(o);
	}
	
	/**
	 * Returns the currently logged in user
	 * @return The currently logged in user
	 */
	public User getCurrentUser(){
		return currentUser;
	}
	
	/**
	 * Returns ETD from kitchen
	 * @return The estimated time of delivery for this order/location
	 */
	public int getETD(String loc, ArrayList<Item> items)
	{
		return pds.getETD(loc, items);
	}
	
	/**
	 * Receives login information from the GUI_Login panel
	 * @param user The current user to be logged in as
	 */
	public void loginUpdate(User user){
		if(user != null){
			currentUser = user;
			CardLayout cl = (CardLayout)cards.getLayout();
			cl.next(cards);
			mainmenu.setManager(currentUser.getPermissionLevel());
		}
	}
	
	/**
	 * Returns an order from lookup method in controller
	 * @return Order
	 */
	public Order getOrder(String phone)
	{
		return pds.getOrder(phone);
	}
	
	/**
	 * Passes an order to TakeORder for editing
	 * @param Order
	 */
	public void editOrder(Order o)
	{
		takeOrder.setOrder(o);
		mainMenuCommand("takeOrder");
	}
	
	/**
	 * Sets visibility of keyboard
	 * @param b Whether to set keyboard visible or not
	 */
	public static void setKeyboardVisible(boolean b)
	{
		frame.requestFocus();
		keyframe.setVisible(b);
	}
	
	/**
	 * Returns visibility state of the keyboard
	 * @return Boolean statement representing whether the keyboard is showing or not
	 */
	public static boolean getKeyboardVisible()
	{
		return keyframe.isShowing();
	}
	
	/**
	 * Sets keyboard target
	 * @param t The textfield to set as currently editing
	 */
	public static void setKeyboardTarget(JTextField t)
	{
		keyboard.setTarget(t);
	}
	
	/**
	 * Returns the keyboard
	 * @return The keyboard
	 */
	private GUI_KeyBoard getKeyboard()
	{
		return keyboard;
	}
	
	/**
	 * Main method
	 * @param args Not used for this release, command line arguments
	 */
	public static void main(String[] args)
	{
	
		frame = new GUI_Controller();
	    frame.setTitle("PDS");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1440,900);
	    frame.setVisible(true);
	    
			keyframe = new JFrame();
			keyboard = frame.getKeyboard();
			keyframe.add(keyboard.getContainer());
			keyframe.setUndecorated(true);
			keyframe.setLocation(400,450);
			keyframe.setSize(700,350);
			keyframe.setVisible(false);	
			keyframe.setAlwaysOnTop(true);
	}
}