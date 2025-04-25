package com.example;

public class Pizza extends Food {
	
	private Size size;
	public enum Size { PERSONAL, MEDIUM, LARGE }
	
	private Flavor flavor;
	public enum Flavor {
		CHEESE,
		PEPPERONI,
		HAWAIIAN
	}
	
	// constructor
	public Pizza(Flavor flavor, Size size) {
		this.label = size.toString() + " ";
		this.label += flavor.toString() + " PIZZA";
		this.flavor = flavor;
		this.size = size;
	}
	
	// getter and setter
	public double getPrice() {
		switch (flavor) {
		case CHEESE:
			return 8.80 + size.ordinal() * 5;
		case PEPPERONI:
			return 9.90 + size.ordinal() * 5;
		case HAWAIIAN:
			return 9.45 + size.ordinal() * 5;
		default:
			return 0;
		}
	}
	

}