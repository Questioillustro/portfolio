import java.util.ArrayList;

import javax.swing.JTextField;

class Item implements java.io.Serializable
{
	private String item, status, cpu, lcd, notes;
	private int quantity, hd;
	private double cost, asking, bottom, ram;
	
	public Item()
	{
		item = "Default";
		status = "Default";
		cpu = "Default";
		lcd = "Default";
		quantity = 0;
		hd = 0;
		cost = 0.0;
		asking = 0.0;
		bottom = 0.0;
		ram = 0.0;
	}
	
	public Item(String item, String status, String cpu, String lcd, String notes, 
				int quantity, int hd,
				double cost, double asking, double bottom, double ram)
	{
		this.item = item;
		this.status = status;
		this.cpu = cpu;
		this.lcd = lcd;
		this.notes = notes;
		this.quantity = quantity;
		this.hd = hd;
		this.cost = cost;
		this.asking = asking;
		this.bottom = bottom;
		this.ram = ram;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getLcd() {
		return lcd;
	}

	public void setLcd(String lcd) {
		this.lcd = lcd;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getHd() {
		return hd;
	}

	public void setHd(int hd) {
		this.hd = hd;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getAsking() {
		return asking;
	}

	public void setAsking(double asking) {
		this.asking = asking;
	}

	public double getBottom() {
		return bottom;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public double getRam() {
		return ram;
	}

	public void setRam(double ram) {
		this.ram = ram;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}