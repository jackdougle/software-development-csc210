// Author: Jack Douglass
// File: Tree.java
// Class: CSC 210, Fall 2024
// Purpose: This class simulates a tree, and is a subclass of Plant. It defines methods for getting the symbol of the tree. To use this class, a user should provide a file containing instructions to create a garden with a Tree. They should add this file as an argument to RunGarden.java.

//package com.gradescope.garden;

public class Tree extends Plant{
	
	public Tree(String type) {
		super(type);
	}

	public char getLetter() {
		return type.toLowerCase().charAt(0);
	}
	
}
