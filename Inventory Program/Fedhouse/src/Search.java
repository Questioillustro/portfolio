import java.awt.Dimension;

import javax.swing.*;

class Search extends JPanel
{
	private JButton search, clear;
	private JLabel item_l, cpu_l, hd_l, ram_l, lcd_l, asking_l;
	private JTextField item, cpu, hd, ram, lcd, asking;
	private JPanel frame;
	private Style formatter;
	
	public Search()
	{
		formatter = new Style();
		
		frame = new JPanel();
		frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
		frame.setBorder(BorderFactory.createTitledBorder("Search"));
		frame.setBackground(formatter.getBgColor());

				
		item_l = new JLabel("Item");
		cpu_l = new JLabel("CPU");
		hd_l = new JLabel("HDD");
		ram_l = new JLabel("RAM");
		lcd_l = new JLabel("LCD");
		asking_l = new JLabel("Asking");
		
		item = new JTextField();
		item.setPreferredSize(new Dimension(120,20));
		cpu = new JTextField();
		cpu.setPreferredSize(new Dimension(120,20));
		hd = new JTextField();
		hd.setPreferredSize(new Dimension(120,20));
		ram = new JTextField();
		ram.setPreferredSize(new Dimension(120,20));
		lcd = new JTextField();
		lcd.setPreferredSize(new Dimension(120,20));
		asking = new JTextField();
		asking.setPreferredSize(new Dimension(120,20));
		
		search = new JButton("Search");
		clear = new JButton("Clear");
		
		JPanel itemP = new JPanel();
		itemP.setBackground(formatter.getBgColor());
		itemP.add(item_l);
		itemP.add(item);
		
		JPanel cpuP = new JPanel();
		cpuP.setBackground(formatter.getBgColor());
		cpuP.add(cpu_l);
		cpuP.add(cpu);
		
		JPanel ramP = new JPanel();
		ramP.setBackground(formatter.getBgColor());
		ramP.add(ram_l);
		ramP.add(ram);
		
		JPanel hdP = new JPanel();
		hdP.setBackground(formatter.getBgColor());
		hdP.add(hd_l);
		hdP.add(hd);
		
		JPanel lcdP = new JPanel();
		lcdP.setBackground(formatter.getBgColor());
		lcdP.add(lcd_l);
		lcdP.add(lcd);
		
		JPanel askingP = new JPanel();
		askingP.setBackground(formatter.getBgColor());
		askingP.add(asking_l);
		askingP.add(asking);
		
		JPanel buttons = new JPanel();
		buttons.setBackground(formatter.getBgColor());
		buttons.add(search);
		buttons.add(clear);
		
		frame.add(itemP);
		frame.add(cpuP);
		frame.add(hdP);
		frame.add(ramP);
		frame.add(lcdP);
		frame.add(askingP);
		frame.add(buttons);
	}
	
	public JPanel getPanel()
	{
		return frame;
	}
	
}