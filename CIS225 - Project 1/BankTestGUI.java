/*
<package>
	GUI for Bank Application
<.package>
<description>
    Currently non operating GUI interface for the Bank application
<.description>
<keywords>
    swing, frame, non-working
<.keywords>
*/

import java.awt.*;
import javax.swing.*;

public class BankTestGUI extends JFrame {
	static JTextArea outPutWin = new JTextArea(50);

	public static void main (String[] args) {
	 BankTestGUI frame = new BankTestGUI();
    frame.setTitle("Bank of Brewster");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600,600);
    frame.setVisible(true);
	 outPut("Hi");
	 	 outPut("Hi");
		 	 outPut("Hi");

	}
	
	public BankTestGUI ( )
	{
		//Menu Panel creation
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(7,1));
		p1.add(new JButton("Add Customer"));
		p1.add(new JButton("Delete Customer"));
		p1.add(new JButton("Sort List"));
		p1.add(new JButton("Find Customer"));
		p1.add(new JButton("Make Deposit"));
		p1.add(new JButton("Make Withdrawal"));
		p1.add(new JButton("Print List"));
		
		//Main window creation
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1,1));
		p2.add(outPutWin);
		
		//Create panel
		add(p1, BorderLayout.WEST);
		add(p2, BorderLayout.CENTER);
	}
	
	public static void outPut(String s)
	{
		outPutWin.setText(s);
	}
}