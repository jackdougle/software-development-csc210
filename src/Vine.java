// Author: Jack Douglass
// File: Vine.java
// Class: CSC 210, Fall 2024
// Purpose: This class simulates a vine. It defines methods for getting the symbol of the vine. To use this class, a user should provide a file containing instructions to create a garden with a Vine. They should add this file as an argument to RunGarden.java.


//package com.gradescope.garden;

public class Vine extends Plant{
	
	public Vine (String type) {
		super(type);
	}

	public char getLetter() {
		return type.toLowerCase().charAt(0);
	}
	
}