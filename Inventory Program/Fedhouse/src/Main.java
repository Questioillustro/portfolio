import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import java.io.File;
import java.util.ArrayList;

import javax.swing.JTextField;


public class Main extends JFrame{
	
	private static JFrame frame;
	private JButton saveDB, loadDB, inventoryBt, create;
	private static Style formatter;
	private Inventory inventory;
	private InventoryGeneralFields inventoryGF;
	private Search search;
	private BtListen bt;
	private static JList files;
	private static DefaultListModel fileModel;
	private static JScrollPane scroller;
	private static FileReaderWriter frw;
	private static String file;
	private static JTextField newFileName;
	private static JLabel ser;
	
	/**
	 * Default constructor
	 */
	public Main()
	{
		bt = new BtListen();
		formatter = new Style();
		frw = new FileReaderWriter();
		
		fileModel = new DefaultListModel();
		files = new JList(fileModel);
		files.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		files.setSelectedIndex(0);
		files.setVisibleRowCount(10);
		scroller = new JScrollPane(files);
		scroller.setPreferredSize(new Dimension(60,200));
		scroller.setBorder(BorderFactory.createTitledBorder("Files"));
		scroller.setBackground(formatter.getBgColor());
		
		inventory = new Inventory();
		inventoryGF = new InventoryGeneralFields();
		search = new Search();
		
		
		buildFileList();
		
		inventoryBt = new JButton("Inventory");
			inventoryBt.setPreferredSize(formatter.getNavBtSize());
		saveDB = new JButton("SaveFile");
			saveDB.setPreferredSize(formatter.getNavBtSize());
			saveDB.addActionListener(bt);
		loadDB = new JButton("LoadFile");
			loadDB.setPreferredSize(formatter.getNavBtSize());
			loadDB.addActionListener(bt);	
		create = new JButton("Create");
			create.setPreferredSize(formatter.getNavBtSize());
			create.addActionListener(bt);
		
		ser = new JLabel(".ser");
		newFileName = new JTextField();
		newFileName.setPreferredSize(new Dimension(110,20));
		scroller.setPreferredSize(new Dimension(160,200));
			
//Left Panel		
		JPanel leftpanelcontainer = new JPanel();
		leftpanelcontainer.setBackground(formatter.getBgColor());
		leftpanelcontainer.setBorder(formatter.getPanelBorder());
		leftpanelcontainer.setPreferredSize(formatter.getSidePanelWidth());
		
		JPanel leftpanel = new JPanel();
		leftpanel.setAlignmentX(CENTER_ALIGNMENT);
		leftpanel.setLayout(new BoxLayout(leftpanel, BoxLayout.Y_AXIS));
		leftpanel.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		leftpanel.setPreferredSize(new Dimension(170,400));
		leftpanel.setBackground(formatter.getBgColor());
		

			leftpanel.add(saveDB);
			saveDB.setAlignmentX(CENTER_ALIGNMENT);
			leftpanel.add(Box.createRigidArea(new Dimension(0,10)));
			leftpanel.add(scroller);
			scroller.setAlignmentX(CENTER_ALIGNMENT);
			leftpanel.add(Box.createRigidArea(new Dimension(0,10)));
			leftpanel.add(loadDB);
			loadDB.setAlignmentX(CENTER_ALIGNMENT);
			leftpanel.add(Box.createRigidArea(new Dimension(0,30)));
				
				JPanel filenameP = new JPanel();
				filenameP.setBackground(formatter.getBgColor());
				filenameP.add(newFileName);
				filenameP.add(ser);
				
			leftpanel.add(filenameP);
			leftpanel.add(Box.createRigidArea(new Dimension(0,5)));
			leftpanel.add(create);
			create.setAlignmentX(CENTER_ALIGNMENT);

			loadDB.setAlignmentX(CENTER_ALIGNMENT);
			
			
//Right Panel
		JPanel rightpanelcontainer = new JPanel();	
		rightpanelcontainer.setBackground(formatter.getBgColor());
		rightpanelcontainer.setBorder(formatter.getPanelBorder());	
		rightpanelcontainer.setPreferredSize(formatter.getSidePanelWidth());
			
			JPanel rightpanel = new JPanel(new CardLayout());
			rightpanel.setBackground(formatter.getBgColor());	
			rightpanel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
	
//Center Panel		
		JPanel centercontainer = new JPanel();
		centercontainer.setLayout(new BoxLayout(centercontainer, BoxLayout.Y_AXIS));
		centercontainer.setBorder(formatter.getPanelBorder());
		centercontainer.setBackground(formatter.getBgColor());
		centercontainer.setBorder(formatter.getPanelBorder());
		
			JPanel center_subpaneltop = new JPanel(new CardLayout());
			center_subpaneltop.setBackground(formatter.getBgColor());
			center_subpaneltop.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
			JPanel center_subpanelBot = new JPanel(new CardLayout());
			center_subpanelBot.setBackground(formatter.getBgColor());
			center_subpanelBot.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
			
//Build Panels
		leftpanelcontainer.add(leftpanel);
		
		center_subpaneltop.add(inventory.getPanel(), "Inventory");
		center_subpanelBot.add(inventoryGF.getPanel(), "IGeneral");
	
		centercontainer.add(center_subpaneltop);
		centercontainer.add(center_subpanelBot);
		
		rightpanel.add(search.getPanel());
		rightpanelcontainer.add(rightpanel);

		add(centercontainer, BorderLayout.CENTER);
		add(leftpanelcontainer, BorderLayout.WEST);
		add(rightpanelcontainer, BorderLayout.EAST);
	}
	
	public static void saveFile()
	{
		frw.writeToFile(file, Inventory.getCompleteList());
	}
	
	public static void loadFile()
	{
		InventoryGeneralFields.clearFields();
		file = fileModel.get(files.getSelectedIndex()).toString();
		Inventory.setCompleteList((ArrayList<Item>)frw.readFromFile(file), file);
	}
	
	public static void createFile()
	{
		InventoryGeneralFields.clearFields();
		file = newFileName.getText();
		file += ".ser";
		Inventory.setCompleteList(new ArrayList<Item>(), file);
		frw.writeToFile(file, new ArrayList<Item>());
		newFileName.setText("");
		buildFileList();
	}
	
	private static void buildFileList()
	{
		fileModel.clear();
		File dir = new File(".");
		int fileCount = 0;

		String[] children = dir.list();
		if (children == null) 
		{}
		else 
		{
			for (int i=0; i<children.length; i++) 
			{
				if(children[i].endsWith(".ser"))
				{
					fileModel.add(fileCount, children[i]);
					fileCount++;
				}
		    }
		}
	}
	
	public static void main(String[] args)
	{
		frame = new Main();
	    frame.setTitle("PDS");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.setVisible(true);
		
	}
}
