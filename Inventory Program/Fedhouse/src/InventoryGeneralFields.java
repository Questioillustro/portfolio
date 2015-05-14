import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

class InventoryGeneralFields extends JPanel
{
	private JLabel item_l, cost_l, status_l, quantity_l, asking_l, bottom_l, notes_l,
					cpu_l, ram_l, hd_l, lcd_l;
	private static JTextField item, cost, status, quantity, asking, bottom, cpu, ram, hd, lcd;
	private JPanel fields1, fields2, fields3;
	private Style formatter;
	private JButton save, edit, clear;
	private static JTextArea notes;
	private JScrollPane scroller;
	private JPanel frame;
	private BtListen bt;
	
	public InventoryGeneralFields()
	{
		formatter = new Style();
		bt = new BtListen();
		
		frame = new JPanel();
		frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
		frame.setBackground(formatter.getBgColor());
		
		fields1 = new JPanel();
		fields1.setBackground(formatter.getBgColor());
		
		fields2 = new JPanel();
		fields2.setBackground(formatter.getBgColor());
		
		fields3 = new JPanel();
		fields3.setBackground(formatter.getBgColor());
			
		JPanel buttons = new JPanel();
		buttons.setBackground(formatter.getBgColor());
		
		save = new JButton("Save");
		save.addActionListener(bt);
		save.setActionCommand("SaveItem");
		edit = new JButton("Edit");
		edit.addActionListener(bt);
		edit.setActionCommand("EditItem");
		clear = new JButton("Clear");
		clear.addActionListener(bt);
		clear.setActionCommand("ClearFields");		

		notes = new JTextArea();
		scroller = new JScrollPane(notes);
		scroller.setPreferredSize(new Dimension(500,100));
		scroller.setBorder(BorderFactory.createTitledBorder("Notes"));
		scroller.setBackground(formatter.getBgColor());

		
		//---------------------------------------
		item_l = new JLabel("Item");
		cost_l = new JLabel("Cost");
		status_l = new JLabel("Status");
		quantity_l = new JLabel("Quantity");
		asking_l = new JLabel("Asking");
		bottom_l = new JLabel("Bottom");
		notes_l = new JLabel("Notes");
		cpu_l = new JLabel("CPU");
		ram_l = new JLabel("RAM");
		hd_l = new JLabel("HDD");
		lcd_l = new JLabel("LCD");
		
		
		//----------------------------------------
		item = new JTextField();
			item.setPreferredSize(new Dimension(150,25));
		cost = new JTextField(); 
			cost.setPreferredSize(new Dimension(50,25));
		status = new JTextField(); 
			status.setPreferredSize(new Dimension(50,25));
		quantity = new JTextField(); 
			quantity.setPreferredSize(new Dimension(50,25));
		asking = new JTextField();
			asking.setPreferredSize(new Dimension(50,25));
		bottom = new JTextField();
			bottom.setPreferredSize(new Dimension(50,25));
		cpu = new JTextField();
			cpu.setPreferredSize(new Dimension(150,25));
		ram = new JTextField();
			ram.setPreferredSize(new Dimension(50,25));
		hd = new JTextField();
			hd.setPreferredSize(new Dimension(50,25));
		lcd = new JTextField();
			lcd.setPreferredSize(new Dimension(50,25));
		
		fields1.add(item_l);
		fields1.add(item);
		fields1.add(cost_l);
		fields1.add(cost);
		fields1.add(status_l);
		fields1.add(status);
		fields1.add(quantity_l);
		fields1.add(quantity);
		fields1.add(asking_l);
		fields1.add(asking);
		fields1.add(bottom_l);
		fields1.add(bottom);
		fields2.add(cpu_l);
		fields2.add(cpu);
		fields2.add(ram_l);
		fields2.add(ram);
		fields2.add(hd_l);
		fields2.add(hd);
		fields2.add(lcd_l);
		fields2.add(lcd);
		fields3.add(scroller);

		buttons.add(save);
		buttons.add(edit);
		buttons.add(clear);    
		
		frame.add(fields1);
		frame.add(fields2);
		frame.add(fields3);
		frame.add(buttons);
		
		setEditableFields(true);
	}
	
	public JPanel getPanel()
	{
		return frame;
	}
	
	public static void addNewItem()
	{
		Item temp = new Item(item.getText(), status.getText(), cpu.getText(), lcd.getText(), notes.getText(), 
							 Integer.parseInt(quantity.getText()), Integer.parseInt(hd.getText()),
							 Double.parseDouble(cost.getText()), Double.parseDouble(asking.getText()),
							 Double.parseDouble(bottom.getText()), Double.parseDouble(ram.getText()));
		Inventory.addItem(temp);
	}
	
	public static void updateExisting()
	{
		Item temp = new Item(item.getText(), status.getText(), cpu.getText(), lcd.getText(), notes.getText(), 
				 Integer.parseInt(quantity.getText()), Integer.parseInt(hd.getText()),
				 Double.parseDouble(cost.getText()), Double.parseDouble(asking.getText()),
				 Double.parseDouble(bottom.getText()), Double.parseDouble(ram.getText()));
		Inventory.updateItem(temp);	
	}
	
	public static void fillFields(Item i)
	{
		item.setText(i.getItem());
		status.setText(i.getStatus());
		cpu.setText(i.getCpu());
		lcd.setText(i.getLcd());
		notes.setText(i.getNotes());
		quantity.setText(Integer.toString(i.getQuantity()));
		hd.setText(Integer.toString(i.getHd()));
		cost.setText(Double.toString(i.getCost()));
		asking.setText(Double.toString(i.getAsking()));
		bottom.setText(Double.toString(i.getBottom()));
		ram.setText(Double.toString(i.getRam()));
		setEditableFields(false);
	}
	
	public static void setEditableFields(boolean t)
	{
		item.setEditable(t);
		cost.setEditable(t);
		status.setEditable(t);
		quantity.setEditable(t);
		asking.setEditable(t);
		bottom.setEditable(t);
		cpu.setEditable(t);
		ram.setEditable(t);
		hd.setEditable(t);
		lcd.setEditable(t);
		notes.setEditable(t);
	}
	
	public static void clearFields()
	{
		item.setText("");
		cost.setText("");
		status.setText("");
		quantity.setText("");
		asking.setText("");
		bottom.setText("");
		cpu.setText("");
		ram.setText("");
		hd.setText("");
		lcd.setText("");
		notes.setText("");
	}
}