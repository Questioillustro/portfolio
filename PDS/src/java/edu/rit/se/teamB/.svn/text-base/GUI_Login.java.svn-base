package edu.rit.se.teamB;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;


public class GUI_Login extends JPanel{
	
	private JFrame frame;
	private GUI_Controller parent;
	private JTextField username;
	private JTextField password;
	private JLabel username_l;
	private JLabel password_l;
	private JButton login;
	private Login log;
	private User loggedIn;
	private JButton btnExit;
	private GUI_KeyBoard keyboard;
	
	/**
	 * Default constructor
	 */
	public GUI_Login(GUI_Controller parent)
	{
		this.parent = parent;
		initialize();
	}
	
	/**
	 * Initializer
	 */
	private void initialize()
	{
		frame = new JFrame();
		
		loggedIn = null;
		log = new Login();
		
		username_l = new JLabel("Username: ");
		password_l = new JLabel("Password: ");
		
		username = new JTextField("admin");
			username.setPreferredSize(new Dimension(100,25));
		password = new JTextField("admin");
			password.setPreferredSize(new Dimension(100,25));
	
		login = new JButton("Login");
		login.setBackground(parent.getBtColor());
		login.setFont(parent.getBtFont());
		login.setForeground(parent.getBtFontColor());
		
		keyboard = new GUI_KeyBoard(null);

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
	
//Center Panel		
		JPanel centercontainer = new JPanel();
		centercontainer.setBorder(parent.getPanelBorder());
		centercontainer.setBackground(parent.getBgPanelColor());
		centercontainer.setBorder(parent.getPanelBorder());
		
			JPanel center_subpaneltop = new JPanel();
			center_subpaneltop.setBackground(parent.getBgPanelColor());
			center_subpaneltop.setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
			
			JPanel borderPanel = new JPanel();
			borderPanel.setBorder(parent.getPanelBorder());
		
		JPanel fullpanel = new JPanel();
		fullpanel.setBackground(parent.getBgColor());
		fullpanel.setLayout(new GridLayout(3,1,0,0));
		fullpanel.setBorder(BorderFactory.createTitledBorder("Login"));
		fullpanel.setPreferredSize(new Dimension(250,160));
			
		JPanel name_fields = new JPanel();
		name_fields.setBackground(parent.getBgColor());
		
		JPanel pass_fields = new JPanel();
		pass_fields.setBackground(parent.getBgColor());
		
		JPanel button = new JPanel();
		button.setBackground(parent.getBgColor());

//Build Panels
		leftpanel.add(left_subpanel1);
		leftpanelcontainer.add(leftpanel);
		
		name_fields.add(username_l);
		name_fields.add(username);
		pass_fields.add(password_l);
		pass_fields.add(password);
		button.add(login);

		
		fullpanel.add(name_fields);
		fullpanel.add(pass_fields);		
		fullpanel.add(button);
		borderPanel.add(fullpanel);
		
		//center_subpaneltop.add(keyboard.getContainer());
		center_subpaneltop.add(borderPanel);
		centercontainer.add(center_subpaneltop);

		frame.add(centercontainer, BorderLayout.CENTER);
		frame.add(leftpanelcontainer, BorderLayout.WEST);
		frame.add(rightpanelcontainer, BorderLayout.EAST);
		
		//Add Listener
		btlisten bt = new btlisten();
		Mouse_Listener fl = new Mouse_Listener();
		
		login.addActionListener(bt);
		username.addMouseListener(fl);
		password.addMouseListener(fl);

		log = parent.getUserDB();
	}
	
	/**
	 * Returns the login panel
	 * 
	 */
	public Container getContainer()
	{
		return frame.getContentPane();
	}

	private class btlisten implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Login"))
			{
				loggedIn = log.userLogin(username.getText(), password.getText());
			}
			if(loggedIn != null){
				parent.loginUpdate(loggedIn);
				keyboard.clearTarget();
				password.setText("");
				username.setText("");
				parent.setKeyboardVisible(false);
			}
			else{
				JOptionPane.showMessageDialog(parent, "Username or password not valid.");
			}
		}
	}
	

}
