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
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

/**
 * Virtual Keyboard Widget
 * @author Stephen Brewster
 *
 */

public class GUI_KeyBoard extends JPanel
{
	private static JTextField target;
	
	private JPanel hidekeyboardpanel;
	private JButton hidekeyboard;
	
	private JPanel numPanels[];
	private JButton numButtons[];
	
	private JPanel lettPanels[];
	private JButton lettButtons[];
	
	private JPanel spacePanel;
	private JButton space;
	
	private JPanel shiftPanel;
	private JButton shift;
	
	private JPanel periodpanel;
	private JButton period;
	
	private JPanel backspacePanel;
	private JButton backspace;
	
	private JPanel fullpanel;
	private JPanel fullkeyboard;
	private JPanel rowPanel[];
	
	private boolean shifted;
	private String qwerty = "qwertyuiopasdfghjklzxcvbnm";
	private Dimension keysize = new Dimension(45,45);
	
	private GUI_Controller parent;
	
	/**
	 * Default constructor
	 * @param p The calling panel
	 */
	public GUI_KeyBoard(GUI_Controller p)
	{
		parent = p;
		target = new JTextField();
		initialize();
	}

	/**
	 * The initializer
	 */
	void initialize()
	{
		fullpanel = new JPanel();
		fullpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btlisten bt = new btlisten();
		shifted = false;

		fullkeyboard = new JPanel();
		fullkeyboard.setLayout(new GridLayout(5,1));
		
		hidekeyboard = new JButton("Hide");
		hidekeyboard.addActionListener(bt);
		hidekeyboard.setPreferredSize(new Dimension(70,45));
		hidekeyboardpanel = new JPanel();
		hidekeyboardpanel.add(hidekeyboard);
			

		shift = new JButton("Shift");
		shift.addActionListener(bt);
		shift.setPreferredSize(new Dimension(70,45));
		shiftPanel = new JPanel();
		shiftPanel.add(shift);
		
		period = new JButton(".");
		period.addActionListener(bt);
		period.setPreferredSize(new Dimension(45,45));
		periodpanel = new JPanel();
		periodpanel.add(period);
		
		backspace = new JButton("<--");
		backspace.addActionListener(bt);
		backspace.setPreferredSize(new Dimension(70,45));
		backspacePanel = new JPanel();
		backspacePanel.add(backspace);
		
		space = new JButton();
		space.setActionCommand("space");
		space.addActionListener(bt);
		space.setPreferredSize(new Dimension(300,45));
		spacePanel = new JPanel();
		spacePanel.add(space);
		
		numPanels = new JPanel[10];
		rowPanel = new JPanel[5];
		numButtons = new JButton[10];
		lettPanels = new JPanel[27];
		lettButtons = new JButton[27];

		//Initialize the panels that contain each row of buttons
		for(int i = 0; i < 5; i++)
		{
			rowPanel[i] = new JPanel();
		}
		
		//Initialize the 1st row of buttons (numbers)
		for(int i = 0; i < 10; i++)
		{
			numButtons[i] = new JButton(Character.toString((char)(i+48)));
			numButtons[i].addActionListener(bt);
			numPanels[i] = new JPanel();
			numButtons[i].setPreferredSize(keysize);
			numPanels[i].add(numButtons[i]);
			rowPanel[0].add(numPanels[i]);
		}

		//Initialize buttons for letters
		for(int i = 0; i < 26; i++)
		{
			lettButtons[i] = new JButton(Character.toString(qwerty.charAt(i)));
			lettButtons[i].addActionListener(bt);
			lettButtons[i].setPreferredSize(keysize);
			lettPanels[i] = new JPanel();
			lettPanels[i].add(lettButtons[i]);
		}

		//Create row 1 of letters
		for(int i = 0; i < 10; i++)
		{
			rowPanel[1].add(lettPanels[i]);
		}
		
		//Create row 2 of letters
		for(int i = 10; i < 19; i++)
		{
			rowPanel[2].add(lettPanels[i]);
		}
		
		//create row 3 of letters
		for(int i = 19; i < 26; i++)
		{
			rowPanel[3].add(lettPanels[i]);
		}
		
	//Build keyboard
		rowPanel[0].add(backspace);
		rowPanel[3].add(periodpanel);	
		rowPanel[3].add(shift);
		rowPanel[4].add(space);
		rowPanel[4].add(hidekeyboard);
		
		fullkeyboard.add(rowPanel[0]);
		fullkeyboard.add(rowPanel[1]);	
		fullkeyboard.add(rowPanel[2]);
		fullkeyboard.add(rowPanel[3]);
		fullkeyboard.add(rowPanel[4]);
		
		fullpanel.add(fullkeyboard);

	}//Initialize
	
	/**
	 * Accessor for container
	 * @return the container of this
	 */
	public Container getContainer()
	{
		return fullpanel;
	}
	
	/**
	 * Set focused field
	 * @param t the field to focus
	 */
	public void setTarget(JTextField t)
	{
		target = t;
	}
	
	/**
	 * Untargeter for keyboard
	 */
	public void clearTarget()
	{
		target = new JTextField();
	}
	
	/**
	 * Check for target equivalency
	 * @param t The source target
	 * @return Result of equal to that target
	 */
	public static boolean targetEqual(JTextField t)
	{
		return target.equals(t);
	}
	
	
	private class btlisten implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Hide"))
			{
				parent.setKeyboardVisible(false);
			}
			if(target != null)
			{
			for(int i = 0; i < 10; i++)
			{
				if(e.getActionCommand().equals(numButtons[i].getActionCommand()))
				{
					target.setText(target.getText() + numButtons[i].getActionCommand().toString());
				}
				
			}//Number Buttons check
			for(int i = 0; i < 26; i++)
			{
				if(e.getActionCommand().equals(lettButtons[i].getActionCommand()))
				{
					if(shifted)
					{
						char c = e.getActionCommand().toString().charAt(0);
						int n = (int)c;
						c = (char)(n-32);
						target.setText(target.getText() + c);
						shifted = false;
					}
					else
						target.setText(target.getText() + lettButtons[i].getActionCommand().toString());
				}
			}//Letter Buttons check
			
			
			//Backspace
			if(e.getActionCommand().equals("<--"))
			{
				if(target.getText().length() > 0)
				{
				   StringBuffer buf = new StringBuffer( target.getText().length() - 1 );
				   buf.append(target.getText().substring(0, target.getText().length()-1) );
				   target.setText(buf.toString());
				}
			}
			
			//Space
			if(e.getActionCommand().equals("space"))
			{
				target.setText(target.getText() + " ");
			}
			
			//Shift
			if(e.getActionCommand().equals("Shift"))
			{
				shifted = !shifted;
			}
			
			if(e.getActionCommand().equals("."))
			{
				target.setText(target.getText() + ".");
			}
			}//if(target)
			
		}//ActionPerformed
	}//ActionListener
}