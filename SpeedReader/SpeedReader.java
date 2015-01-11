/*
<package>
	Speed Reader
<.package>
<description>
    Tests reading speed
<.description>
<keywords>
    swing, frame
<.keywords>
*/

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class SpeedReader extends JFrame{
		private JTextArea io_win = new JTextArea();
		private JTextField wpm = new JTextField("200");
		private JScrollPane io_scroller = new JScrollPane(io_win);
		private JButton begin = new JButton("Begin");
		private JButton clear = new JButton("Clear");
		private String text;
		private String splitter[];
		private long delay, temp;
		private int inc;
		private Timer timer;
		
// **** MAIN METHOD ********************************
public static void main (String[] args) {
	 SpeedReader frame = new SpeedReader();
    frame.setTitle("Speed-Reader by Stephen Brewster");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700,400);
    frame.setVisible(true);
}		

	// **** FRAME CREATION METHOD *******************
	public SpeedReader()
	{
		//OUTPUT WINDOW PANE
		JPanel io_win_panel = new JPanel();
		io_win_panel.setLayout(new GridLayout(1,1));
		io_win_panel.setBorder(BorderFactory.createTitledBorder("Display"));
			io_win.setLineWrap(true);
			io_win.setWrapStyleWord(true);
		io_win_panel.add(io_scroller);
		
		//CONTROL WINDOW PANE
		JPanel wpm_pane = new JPanel();
		wpm_pane.setLayout(new GridLayout(1,2));
		wpm_pane.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
		wpm_pane.add(new JLabel("WPM"));
			wpm_pane.add(wpm);
		
		JPanel begin_pane = new JPanel();
		begin_pane.setLayout(new GridLayout(1,1));
		begin_pane.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
			begin_pane.add(begin);

		
		JPanel clear_pane = new JPanel();
		clear_pane.setLayout(new GridLayout(1,1));
		clear_pane.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
			clear_pane.add(clear);
			
		JPanel control_pane = new JPanel();
		control_pane.setLayout(new GridLayout(4,1));

			//control_pane.add(begin);
			control_pane.add(begin_pane);
			control_pane.add(clear_pane);
			control_pane.add(wpm_pane);
		
		//Create listeners
		ButtonListener btListener = new ButtonListener();
			begin.addActionListener(btListener);
			clear.addActionListener(btListener);
		
		
		add(control_pane, BorderLayout.WEST);		
		add(io_win_panel, BorderLayout.CENTER);
	}
	
	//	**** ACTION EVENT CLASS****************************************
	class ButtonListener implements ActionListener
	{
  		public void actionPerformed(ActionEvent e)
 	 	{
    		if (e.getActionCommand().equals("Begin"))
    		{
				begin.setText("Stop");
     		   text = io_win.getText();
				io_win.setText("");
				splitter = text.split(" ");
				
				inc = 0;
				text = wpm.getText();
				temp = Long.valueOf(text);
				delay = 60000/temp;
				
				new Printer(delay);	
    		}
			
			if (e.getActionCommand().equals("Stop"))
			{
				timer.cancel();
				begin.setText("Begin");				
			}
			
			if (e.getActionCommand().equals("Clear"))
			{
				io_win.setText("");
			}
		}
	}

	class Printer {

   	public Printer(long mili) {
      	timer = new Timer();
         timer.schedule(new PrintTask(), 0, mili);
		}

    class PrintTask extends TimerTask {
    		public void run() {
					io_win.append(splitter[inc] + " ");
					inc++;
				if (inc == splitter.length)
				{					
					timer.cancel();
					begin.setText("Begin");
				}
        }
    }

	}//End Printer class
}//End SpeedReader class