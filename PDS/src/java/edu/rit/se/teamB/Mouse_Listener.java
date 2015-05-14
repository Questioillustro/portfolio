package edu.rit.se.teamB;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class Mouse_Listener implements MouseListener
{
	private static final String JTextField = null;

	/**
	 * Sets keyboard visible if clicked on a text field
	 */
	public void mouseClicked(MouseEvent fe)
	{
		if (fe.getClickCount() == 1) 
	    {
			if(! GUI_KeyBoard.targetEqual((JTextField)fe.getComponent()))
	        {
				GUI_Controller.setKeyboardTarget((JTextField)fe.getComponent());
	        	GUI_Controller.setKeyboardVisible(true);
	         }
			if(! GUI_Controller.getKeyboardVisible())
			{
				GUI_Controller.setKeyboardVisible(true);
			}
	    }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
	