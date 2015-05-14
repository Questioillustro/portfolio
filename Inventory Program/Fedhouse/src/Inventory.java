import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

class Inventory extends JPanel
{
	private static JTable table;
	private static DefaultTableModel tableModel;	
	private static JScrollPane scroller;	
	private static String[][] data;
	private String[] columnnames = {"Product","Cost",
									"Status","Quantity", "Asking Price", "Low Price"};
	private JPanel frame;
	private Style formatter;
	private JButton add, delete, sortBt;
	private BtListen bt;
	private JComboBox sorter;
	private static int selectedRow;
	private static ArrayList<Item> completeList;
	private static JTextField totalHigh, totalLow;
	private JLabel tHigh_l, tLow_l, sort_l;

	public Inventory()
	{
		//Misc variables
		formatter = new Style();
		bt = new BtListen();
		selectedRow = -1;
		
		//Table elements
		data = new String[100][6];
		tableModel = new DefaultTableModel(data, columnnames);
		table = new JTable(tableModel);
		scroller  = new JScrollPane(table);
		scroller.setBorder(BorderFactory.createTitledBorder("NoFileSelected"));
		scroller.setBackground(formatter.getBgColor());
		
		//Panels
		frame = new JPanel();
		frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
		frame.setBackground(formatter.getBgColor());
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(0,10,10));
		buttons.setBackground(formatter.getBgColor());
		
		//Buttons
		add = new JButton("Add");
		add.addActionListener(bt);
		add.setActionCommand("AddItem");
		
		delete = new JButton("Delete");
		delete.addActionListener(bt);
		delete.setActionCommand("DeleteItem");
		
		sortBt = new JButton("Sort");
		sortBt.addActionListener(bt);
		sortBt.setActionCommand("Sort");
		
		
		//Labels and textfields
		tHigh_l = new JLabel("Total(H)");
		tLow_l = new JLabel("Total(L)");
		totalHigh = new JTextField();
		totalHigh.setPreferredSize(new Dimension(60,25));
		totalLow = new JTextField();
		totalLow.setPreferredSize(new Dimension(60,25));
		sorter = new JComboBox(columnnames);
		sorter.insertItemAt("None", 0);
		sorter.setSelectedIndex(0);
		sort_l = new JLabel("Sort by");
		
		
		//Panel building
		buttons.add(add);
		buttons.add(delete);
		buttons.add(Box.createRigidArea(new Dimension(75,0)));
		buttons.add(sort_l);
		buttons.add(sorter);
		buttons.add(sortBt);
		buttons.add(Box.createRigidArea(new Dimension(75,0)));
		buttons.add(tHigh_l);
		buttons.add(totalHigh);
		buttons.add(Box.createRigidArea(new Dimension(10,0)));		
		buttons.add(tLow_l);
		buttons.add(totalLow);

		
		frame.add(scroller);
		frame.add(buttons);
		
		MouseListener mouseListener = new MouseListener()
		{
		     public void mouseClicked(MouseEvent e) 
		     {
		         if (e.getClickCount() == 1) 
		         {
		        	 selectedRow = table.getSelectedRow();	
		        	 if(selectedRow < completeList.size())
		        		 InventoryGeneralFields.fillFields(completeList.get(selectedRow));
		        	 else
		        	 {	
		        		 InventoryGeneralFields.clearFields();
		        		 InventoryGeneralFields.setEditableFields(true);
		        	 }
		         }
		     }

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		};
		
		table.addMouseListener(mouseListener);
	}
	
	public static void addItem(Item i)
	{
		completeList.add(i);
		updateData(completeList);
	}
	
	public static void sortList()
	{
		
	}
	
	public static void deleteItem()
	{
		selectedRow = table.getSelectedRow();
		completeList.remove(selectedRow);
		updateData(completeList);
		
		//Reset general fields to newly selected item
		selectedRow = table.getSelectedRow();
		if(selectedRow < completeList.size())
			InventoryGeneralFields.fillFields(completeList.get(selectedRow));
		else
			InventoryGeneralFields.clearFields();
	}
	
	public static void updateItem(Item i)
	{
		completeList.set(selectedRow, i);
		updateData(completeList);
	}

	public static ArrayList<Item> getCompleteList()
	{
		return completeList;
	}
	
	public static void setCompleteList(ArrayList<Item> arrayList, String filename)
	{
		completeList = arrayList;
		updateData(completeList);
		scroller.setBorder(BorderFactory.createTitledBorder(filename));
	}
	
	public static void updateData(ArrayList<Item> temp)
	{
		double totalH = 0, totalL = 0;
		
		for(int i = 0; i < 99; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				table.setValueAt("", i, j);
			}
		}
		
		for(int i = 0; i < temp.size(); i++)
		{
			
			table.setValueAt(temp.get(i).getItem(), i, 0);
			table.setValueAt(Double.toString(temp.get(i).getCost()), i, 1);
			table.setValueAt(temp.get(i).getStatus(), i, 2);
			table.setValueAt(Integer.toString(temp.get(i).getQuantity()), i, 3);
			table.setValueAt(Double.toString(temp.get(i).getAsking()), i, 4);
			table.setValueAt(Double.toString(temp.get(i).getBottom()), i, 5);
			
			totalH += temp.get(i).getAsking() * temp.get(i).getQuantity();
			totalL += temp.get(i).getBottom() * temp.get(i).getQuantity();
		}
		
		totalHigh.setText(Double.toString(totalH));
		totalLow.setText(Double.toString(totalL));
	}
	
	public JPanel getPanel()
	{
		return frame;
	}
}