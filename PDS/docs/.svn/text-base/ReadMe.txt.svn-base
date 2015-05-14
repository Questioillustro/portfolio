._________________.
| PDS System v2.0 |
._________________.
 
   ~~~~~~~~~~~~~~~~~~
=-|==================|-=
  |  Authors: TeamB  |
  |                  |
  |   Chad Campbell  |
  |     Tony Chen    |
  |     Dan Flint    |
  | Stephen Brewster |
  |==================|
=- ~~~~~~~~~~~~~~~~~~ -=

============================
--  Graphical Interface   --
============================

~~~~~~~~~~~~~~
-Instructions-
~~~~~~~~~~~~~~

	---------
	1. On Run
	--------- 

	You will be prompted to enter login credentials, default user is:
	User: admin
	Password: admin

	--------------
	2. Once Log In
	-------------- 
	
		There are four buttons:

		Logout            : Logs out and goes to initial login screen
		Order Lookup      : (Not currently implemented) Allows you to look up an active order by a phone number.
		Place Order       : Initiates the order taking process
		Manager Functions : Advanced options for the manager including user, menu and simulation management.

	---------------
	3.1 Place Order
	---------------
	
		a) Once you start the order taking process you may begin by either adding items or
			looking up a customer.
		
			* The order must have customer name, address and phone number filled out and an item in the order
				to be placed. Empty orders and orders with no customer are not allowed.

		b) If you look up a customer, we plan to display the customer's latest (or favorite) order and allow you to use it
			as a starting point for the order.

		c) Items are added via the appropriate buttons on the left, and adding an item updates the order display
			on the left and all information such as subtotal, tax and total

		d) Modify Item is not implemented yet but it will allow you to edit the toppings on items

		e) Remove item just removes the highlighted item from the order

		f) Once an order is placed, and all requirements are met then the order is sent to the kitchen simulation from our CLI

	---------------------
	3.2 Manager Functions (Not fully functional)
	---------------------

		a) This menu is meant to alter values associated with simulation such as number of cooks, drivers and etc.

	-----------------------------
	4. Incomplete functionalities
	-----------------------------	
	
		GUI does not do any order simulation yet
		Unable to add/remove menu items or types of toppings
		Unable to add/remove users	

============================
-- Command Line Interface --
============================

Instructions:
	1. On run you will be prompted to enter login credentials, default user is:
	User: admin
	Password: admin
	
	2. Once logged in the CLI is capable of receiving the following commands.
		takeorder   : begins order taking process
		cancelorder : cancels order under phone# (Prompt will be displayed after entering)
		checkorder  : returns order status
		editorder   : finds the desired order and opens editing options
		showall     : prints list of current orders and their status
		changespeed : multiplies the sim speed by a double
		adduser     : add another user to login system
		exit        : exits the PDS system
		
	3. When takeorder is instantiated a new list of commands will become available.
		getcustomer : retrieves/creates customer for the order (execute a second time to change the customer)
		additem     : opens menu to add an item
		removeitem  : opens order items and allows for deletion of an item
		edititem    : opens order items and allows for editing an item
		showorder   : displays order information
		cancel      : discards the current order and goes to the main menu
		done        : completes the order and sends it to the kitchen
		
	4. Once an order is placed, showall is the primary command for viewing the status of the simulation
	
Known Bugs:
	- Input error correction is limited, please enter only valid inputs as specified by the CLI prompts and readme file, failure to do so
	  may result in a system crash.
	- Inputing a value for the changespeed function below .01 is not advisable and can disrupt simulation stability
	- The editorder function causes a system crash
	
Incomplete functionalities:
	- Canceling an order completely destroys all active items for that order and does not affect the order if it being delivered
		* this functionality will be enhanced to allow for the usage of canceled items for in house sales, such orders will be
		  marked with a special status "HOUSE ITEM"	
	- Cooks, drivers, ovens, and other modifiable variables are not yet changeable during runtime
	- Simulation speed changing will include an instantaneous completion option which will run through all active orders at full processing speed
	- The editorder functionality is not operational once the order has been submitted to the kitchen
		* this problem is tied to the methodology of separating the items from the order upon sending it to the kitchen and will be fixed in R2
	- New toppings cannot yet be added
	- No estimation of time of delivery is provided
	
	
