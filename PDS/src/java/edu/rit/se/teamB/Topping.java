package edu.rit.se.teamB;


public class Topping {

	String name;
	float price;
	
	public Topping() {
		name = "";
		price = 0.0f;
	}
	
	public Topping(String name, float price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
