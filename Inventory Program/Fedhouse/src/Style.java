import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.Border;

class Style
{
	private Dimension navButtonSize;
	private Color panelBG;
	private Color buttonColor;
	private Border panelBorder;
	private Dimension panelWidth;
	
	public Style()
	{
		navButtonSize = new Dimension(125,75);
		panelBG = new Color(250,250,250);
		buttonColor = Color.WHITE;
		panelBorder = BorderFactory.createBevelBorder(0);
		panelWidth = new Dimension(200,0);
	}
	
	public void setLabelProperties(JLabel l)
	{
		l.setPreferredSize(new Dimension(35,25));
		l.setBackground(Color.WHITE);
	}
	
	public void setTextFieldProperties(JTextField t)
	{
		t.setPreferredSize(new Dimension(120,25));
		t.setBackground(Color.WHITE);
	}
	
	public Dimension getNavBtSize()
	{
		return navButtonSize;
	}
	
	public Color getBgColor()
	{
		return panelBG;
	}
	
	public Color getbtColor()
	{
		return buttonColor;
	}
	
	public Border getPanelBorder()
	{
		return panelBorder;
	}
	
	public Dimension getSidePanelWidth()
	{
		return panelWidth;
	}
}