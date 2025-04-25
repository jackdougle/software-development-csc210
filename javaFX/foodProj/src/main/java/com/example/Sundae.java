package com.example;

public class Sundae extends Food {
	
	private Size size;
	public enum Size { ONE_SCOOP, TWO_SCOOPS, THREE_SCOOPS }
	
	private Flavor flavor;
	public enum Flavor {
		VANILLA,
		CHOCOLATE,
		BANANA_SPLIT
	}
	
	// constructor
	public Sundae(Flavor flavor, Size size) {
		this.label = size.toString() + " ";
		this.label += flavor.toString() + " SUNDAE";
		this.flavor = flavor;
		this.size = size;
	}
	
	// getter and setter
	public double getPrice() {
		switch (flavor) {
		case VANILLA:
			return 5.80 + size.ordinal() * 5;
		case CHOCOLATE:
			return 5.90 + size.ordinal() * 5;
		case BANANA_SPLIT:
			return 7.50 + size.ordinal() * 5;
		default:
			return 0;
		}
	}
	

}