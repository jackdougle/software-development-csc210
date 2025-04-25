// Author: Jack Douglass
// File: Vegetable.java
// Class: CSC 210, Fall 2024
// Purpose: This class simulates a vegetable, and is a subclass of Plant. It defines methods for getting the symbol of the vegetable. To use this class, a user should provide a file containing instructions to create a garden with a Vegetable. They should add this file as an argument to RunGarden.java.

//package com.gradescope.garden;

public class Vegetable extends Plant{
	
	public Vegetable(String type) {
		super(type);
	}

	public char getLetter() {
		return type.toLowerCase().charAt(0);
	}
	
}