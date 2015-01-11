/*
<package>
	Vocab Expander
<.package>
<description>
    Pops up an alert from a list of externally supplied strings, originally made as a type of vocabulary flash card
<.description>
<keywords>
	swing, gui, blob class
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import java.awt.event.*;
import javax.swing.JFileChooser;
import java.io.*;
import java.util.*;

class VocabExpander extends JFrame{
	private JTextArea display_win = new JTextArea();
	private JButton start = new JButton("Start");
	private JTextField interval = new JTextField("5");
	private JMenu file = new JMenu("File");
	private JScrollPane scroller = new JScrollPane(display_win);
	private JPanel controls = new JPanel();	
	private JPanel start_panel = new JPanel();
	private JPanel complete = new JPanel();
	private JCheckBox ckbx[] = new JCheckBox[20];
	private JLabel cklbl[] = new JLabel[20];
	private String text;
	private String splitter[];
	private long delay, temp;
	private int inc, instructions = 20;
	private Timer timer;

public static void main(String[] args)
{
	 VocabExpander frame = new VocabExpander();
    frame.setTitle("Vocabulary Expander");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700,400);
    frame.setVisible(true);
	
}
	public VocabExpander()
	{
		 //Create Menu Bar
		 JMenuBar menuBar = new JMenuBar();
		 setJMenuBar(menuBar);
		 	 menuBar.add(file);
		
		 JMenuItem fileImport;
		 	file.add(fileImport = new JMenuItem("Import File"));
		 
		 //Create Panels **************************
		 	controls.setLayout(new GridLayout(2,1));
			controls.setBorder(BorderFactory.createTitledBorder("Controls"));
			
		 //Panel for START BUTTON
		 	start_panel.setLayout(new GridLayout(1,1));
			start_panel.setBorder(BorderFactory.createEmptyBorder(15,10,5,10));
			start_panel.add(start);

		 //Panel for INTERVAL
		 JPanel interval_panel = new JPanel();
		 	interval_panel.setLayout(new GridLayout(1,2));
			interval_panel.setBorder(BorderFactory.createEmptyBorder(15,10,10,10));
			interval_panel.add(new JLabel("Interval (s)  "));
			interval_panel.add(interval);

		//Add sub panels to control panel	
			controls.add(interval_panel);
			controls.add(start_panel);			

		
		//Panel for WINDOW	
		 JPanel window = new JPanel();
		 	window.setLayout(new GridLayout(1,1));
			window.setBorder(BorderFactory.createTitledBorder("Display"));
			display_win.setMargin(new Insets(10,10,10,10));
			window.add(scroller);
		
		//Edit Complete panel
		JPanel com_win = new JPanel();
			com_win.setBorder(BorderFactory.createTitledBorder("Complete"));
			com_win.setLayout(new GridLayout(1,1));
			complete.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
			complete.setLayout(new GridLayout(20,2));
			for(int i = 0; i < 20; i++)
			{
				ckbx[i] = new JCheckBox();
				cklbl[i] = new JLabel("Complete");
				complete.add(ckbx[i]);
				complete.add(cklbl[i]);
				ckbx[i].setVisible(false);
				cklbl[i].setVisible(false);
			}

			com_win.add(complete);
			
		//Add panels to layout
		add(controls, BorderLayout.WEST);
		add(window, BorderLayout.CENTER);
		add(com_win, BorderLayout.EAST);
		
		//Create LISTENERS
		ButtonListener btListen = new ButtonListener();
			start.addActionListener(btListen);
			fileImport.addActionListener(btListen);
			
	}// E - VocabExpander()
	
	//Recieves a file and outputs its contents to the input window
	private void outPutFile (File f)
	{
		String line;
		display_win.setText("");
		try
		{
			FileReader theFile = new FileReader(f);
			BufferedReader readIn = new BufferedReader(theFile);
			while ((line = readIn.readLine()) != null)
			{
				display_win.append(line + "\n");
			}
		}
		catch (Exception e)
		{
			System.out.println( e );
		}
	}// E - outPutFile()
	
	//ActionListner class
	class ButtonListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("Import File"))
			{
				File file = new File("ExampleText.txt");
				JFileChooser chooser = new JFileChooser();
					 int retval = chooser.showOpenDialog(null);
					 if (retval == JFileChooser.APPROVE_OPTION)
					 {	
					 	file = chooser.getSelectedFile();
						outPutFile(file);
					 }
			}
			
			if (e.getActionCommand().equals("Start"))
			{
				inc = 0; //Set Array increment to 0
				text = display_win.getText(); //Set text in display_win to string
				splitter = text.split("\\n"); //Split text by line break into array
				start.setText("Stop"); //Change text on start button to "stop"
				
				text = interval.getText(); //Obtain value from "interval" textfield
				temp = Long.valueOf(text); //Convert interval from string to long
				delay = temp * 1000; //Convert seconds to mili-seconds
				
				display_win.setText("");
				display_win.setEditable(false);
				interval.setEditable(false);
		
				new WordAlert(delay); //Begin displaying instructions on set interval					
			}
			
			if (e.getActionCommand().equals("Stop"))
			{
				timer.cancel();
				start.setText("Start");
				display_win.setEditable(true);
				interval.setEditable(true);	
			}
		
		}// E - actionPerformed()
	}// E - ButtonListener{}
	
	class WordAlert {

   	public WordAlert(long mili) {
      	timer = new Timer();
         timer.schedule(new PrintTask(), mili, mili);
		}

    class PrintTask extends TimerTask {
    		public void run() {
				   JOptionPane.showMessageDialog(null, splitter[inc]); //Give instruction as alert
				   display_win.append(splitter[inc] + "\n"); //Display the instruction in the window
				   ckbx[inc].setVisible(true); //Display checkbox for the instruction
					inc++;
				if (inc == splitter.length)
				{					
					timer.cancel();
					start.setText("Start");
					display_win.setEditable(true);
					interval.setEditable(true);
				}
        }
    }

	}//End Printer class


}// E - VocabExpander{}