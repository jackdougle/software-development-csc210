// Author: Jack Douglass
// File: Plant.java
// Class: CSC 210, Fall 2024
// Purpose: This abstract class simulates a plant. It defines methods for growing a plant, getting the plant's letter (used to represent it in a visual garden), getting the plant's type, and getting and setting the plant's size. To use this class, a user should provide a file containing instructions to create a garden with a Plant. They should add this file as an argument to RunGarden.java.

package workspace.files;

public abstract class Plant {
	protected String type;
	protected int size;
	
	public Plant(String type) {
		this.type = type;
		this.setSize(1);
	}
	
	public void grow() {
		// this method increases the plant's size by 1
		
		setSize(getSize() + 1);
	}
	
	public abstract char getLetter();
	
	public String getType() {
		return type.toLowerCase();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String toString() {
		return "This is a " + type.toLowerCase();
	}

}
