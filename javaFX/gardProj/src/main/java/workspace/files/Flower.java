// Author: Jack Douglass
// File: Flower.java
// Class: CSC 210, Fall 2024
// Purpose: This class simulates a flower and is a subclass of Plant. It defines methods for getting the symbol of the flower. To use this class, a user should provide a file containing instructions to create a garden with a Flower. They should add this file as an argument to RunGarden.java.

package workspace.files;

public class Flower extends Plant {
	
	public Flower(String type) {
		super(type);
	}

	public char getLetter() {
		return type.toLowerCase().charAt(0);
	}
	
}