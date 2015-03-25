/*
<package>
	Graphical Histogram
<.package>
<description>
    Displays Histogram information for a list of years
<.description>
<keywords>
    swing, frame, histogram
<.keywords>
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class DistributionMain extends JFrame{
		private JTextArea win = new JTextArea();
		private JTextField year[] = new JTextField[6];
		private JScrollPane scroller = new JScrollPane(win);
		private JButton graph = new JButton("Graph");
		private JButton data = new JButton("Data");
		private JButton next = new JButton("Next >>");
		private JButton prev = new JButton("<< Previous");
		private Color DEFAULT = new Color(0,0,0);
		private String avail_years[] = {"2011",
												  "2020",
												  "2030",
												  "2040",
												  "2050",
												  "2060"};
		private int navTrack = 0;
		private Color highlight = new Color(210,255,210);	
		private Color text = new Color(10,10,255);	
		
	public static void main(String[] args)
	{
  		 DistributionMain frame = new DistributionMain();
   	 frame.setTitle("Population Distribution Graph by Stephen Brewster");
   	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	 frame.setSize(800,600);
   	 frame.setVisible(true);
	}//E - main
	
	public DistributionMain()
	{
		DEFAULT = graph.getBackground();	//Creates the default color for buttons
		
		//Initialize year labels
		for (int i = 0; i < 6; i++)
		{
			year[i] = new JTextField(avail_years[i]);
			year[i].setEditable(false);
			year[i].setFont(new Font("Courier New", Font.BOLD, 20));
			year[i].setHorizontalAlignment(JTextField.CENTER);
			year[i].setBackground(Color.white);
			year[i].setBorder(BorderFactory.createBevelBorder(1));
		}
 
 		year[0].setBackground(highlight);
		year[0].setForeground(text);
//---------------------------------------------------------------------------

//Creates navigation button panes
		JPanel next_pane = new JPanel();
		next_pane.setLayout(new GridLayout(1,1));
		next_pane.setBorder(BorderFactory.createBevelBorder(1));
			next_pane.add(next);
		//Previous	
		JPanel prev_pane = new JPanel();
		prev_pane.setLayout(new GridLayout(1,1));
		prev_pane.setBorder(BorderFactory.createBevelBorder(1));
			prev_pane.add(prev);
		//Next	
		JPanel nav_pane = new JPanel();
		nav_pane.setLayout(new GridLayout(1,2));
		nav_pane.setBorder(BorderFactory.createCompoundBorder(
								 BorderFactory.createTitledBorder("Navigation Options"),
								 BorderFactory.createBevelBorder(1)));
								 
		nav_pane.setBackground(Color.white);																				
			nav_pane.add(prev_pane);
			nav_pane.add(next_pane);
//--------------------------------------------------------------------------					

//Creates the Graph/Data panes
		JPanel button_pane = new JPanel();
		button_pane.setLayout(new GridLayout(1,2));
		button_pane.setBorder(BorderFactory.createCompoundBorder(
									 BorderFactory.createTitledBorder("Display Options"),
									 BorderFactory.createBevelBorder(1)));

		button_pane.setBackground(Color.white);																					
			//Graph
			JPanel graph_pane = new JPanel();
			graph_pane.setLayout(new GridLayout(1,1));
			graph_pane.setBorder(BorderFactory.createBevelBorder(1));
				graph_pane.add(graph);
			//Data
			JPanel data_pane = new JPanel();
			data_pane.setLayout(new GridLayout(1,1));
			data_pane.setBorder(BorderFactory.createBevelBorder(1));
				data_pane.add(data);			
			button_pane.add(graph_pane);
			button_pane.add(data_pane);
//--------------------------------------------------------------------------

//Constructs full left side pane
		JPanel control_pane = new JPanel();
		control_pane.setLayout(new GridLayout(8,1));
		control_pane.setBorder(BorderFactory.createBevelBorder(1));
		control_pane.setBackground(Color.white);		
			control_pane.add(nav_pane);
			control_pane.add(button_pane);
		for (int i = 0; i < 6; i++)
		{
			year[i].setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
			control_pane.add(year[i]);
		}				
		control_pane.setPreferredSize(new Dimension(230,0));
//--------------------------------------------------------------------------

		//OUTPUT WINDOW PANE
		JPanel win_panel = new JPanel();
		win.setFont(new Font("courier new", Font.PLAIN, 16));
		win_panel.setLayout(new GridLayout(1,1));
		win_panel.setBorder(BorderFactory.createTitledBorder("Display"));
			win.setLineWrap(true);
			win.setWrapStyleWord(true);
		win_panel.add(scroller);
	
		//Create listeners
		ButtonListener btListener = new ButtonListener();
			graph.addActionListener(btListener);
			data.addActionListener(btListener);
			next.addActionListener(btListener);
			prev.addActionListener(btListener);
		
		add(control_pane, BorderLayout.WEST);		
		add(win_panel, BorderLayout.CENTER);

	}//E - Distribution constructor
	
	class ButtonListener implements ActionListener
	{
  		public void actionPerformed(ActionEvent e)
 	 	{
   		//Displays distribution graph 		
			if (e.getActionCommand().equals("Graph"))
    		{
				displayGraph();
								
				//Alternate color on buttons
				graph.setBackground(Color.white);
				data.setBackground(DEFAULT);
    		}
			
			//Displays the raw data from file
			if (e.getActionCommand().equals("Data"))
			{
				displayData();
								
				//Alternate color on buttons
				data.setBackground(Color.white);
				graph.setBackground(DEFAULT);
			}
			
			//Moves to next available year
			if (e.getActionCommand().equals("Next >>"))
			{
				year[navTrack].setBackground(Color.white);
				year[navTrack].setForeground(Color.black);
				navTrack++;
				
				if(navTrack > 5)
						navTrack = 0;
						
				year[navTrack].setBackground(highlight);
				year[navTrack].setForeground(text);
				
				//Automatically displays the selected option
				if (graph.getBackground() == Color.white)
					displayGraph();
				else if(data.getBackground() == Color.white)
					displayData();
			}//Next >>
			
			if (e.getActionCommand().equals("<< Previous"))
			{
				year[navTrack].setBackground(Color.white);				
				year[navTrack].setForeground(Color.black);
				navTrack--;
				
				if(navTrack < 0)
						navTrack = 5;
				
				year[navTrack].setBackground(highlight);
				year[navTrack].setForeground(text);				
				
				//Automatically displays the selected option
				if (graph.getBackground() == Color.white)
					displayGraph();
				else if(data.getBackground() == Color.white)
					displayData();
			}
		}
	}//E - ButtonListener{}
	
	//Displays the currently loaded dispersion graph
	private void displayGraph()
	{
		win.setText("");			
			try
			{
				Year y = new Year(year[navTrack].getText() + ".txt");
  				win.setText(y.printGraph(year[navTrack].getText()));
			}
			catch(IOException err)
			{
				win.setText("File, " + year[navTrack].getText() + ".txt, not found");
			}		
	}//displayGraph()
	
	//Displays the currently loaded data
	private void displayData()
	{
		win.setText("");
			try
			{
				Year y = new Year(year[navTrack].getText() + ".txt");
				win.setText(y.printYear(year[navTrack].getText()));
			}
			catch(IOException err)
			{
				win.setText("File, " + year[navTrack].getText() + ".txt, not found");
			}
	}//displayData()
}//E - DistributionMain