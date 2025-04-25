package com.example;

public abstract class Food {
	
	protected double price;
	protected String label;
	
	// constructor
	public Food() {
		
	}
	
	// getters and setters
	public abstract double getPrice();

	public String toString() {
		String message = "$" + getPrice() + "\t" + label;
		return message + "\n";
	}

}