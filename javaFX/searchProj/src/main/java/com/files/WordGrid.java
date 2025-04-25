/* 
 * Author: Jack Douglass
 * File: WordGrid.java
 * Class: CSC 210, Fall 2024
 * Purpose: This program simulates a grid of letters. This program works by creating a 
 * 2D array of Strings based on an input String containing the desired dimensions. 
 * Initially, the 2D String array will be filled with placeholder periods if the 
 * 'makeGrid' function is called. This done so words can be inserted into the grid via 
 * another class. This is done as this class is designed to be used as part of a word 
 * search game that uses multiple classes. Once all of the desired words are put into 
 * the grid, the 'fillGrid' function can be called to switch any remaining placeholders 
 * with random letters to complete the table and make it usable for word search games.
 */

package com.files;

import java.util.Random;
import java.util.Arrays;

public class WordGrid {
	private String[][] letterGrid;
	private Random random;
	
	// Dimensions
	private int y;
	private int x;
	
	private final static String[] ALPHABET = { "a", "b", "c", "d", 
			"e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", 
			"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	
	
	public WordGrid(String inptLine) {
		// This constructur creates a new WordGrid object, instantiates a Random object, 
		// and creates a 2D String array with input size values
		
		random = new Random();
		
		String[] dimensionlist = inptLine.split(" ");
		x = Integer.valueOf(dimensionlist[0]); y = Integer.valueOf(dimensionlist[1]);
		
		letterGrid = new String[y][x];
	}
	
	public void makeGrid() {
		// This function fills up the 2D String array with placeholder period values.
		
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				letterGrid[i][j] = ".";
			}
		}
	}
	
	public void fillGrid() {
		// This function fills up the 2D String array representing the grid of letters
		// with random letters in every spot that is not taken up by a placeholder period.
		
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				int r = random.nextInt(26);
				String letter = ALPHABET[r]; // Get random letter
				
				if (letterGrid[i][j].equals(".")) letterGrid[i][j] = letter.toUpperCase();
			}
		}
	}
	
	public String toString() {
		// This function converts letterGrid into a String that can be printed to visually
		// represent the grid.
		
		String output = "";
		
		for (int i = 0; i < y; i++) {
			if (i == 0) {  // add row of lowercase letters to help user with getting x coord
				String[] firstRow = Arrays.copyOfRange(ALPHABET, 0, x);
				output += "   " + String.join(" ", firstRow) + "\n";
			}
			
			String rowNumber = String.valueOf(i) + " ";
			if (i < 10) rowNumber = "0" + rowNumber;  // create column of numbers to help user
			output += rowNumber +  String.join(" ", letterGrid[i]) + "\n";
		}
		return output;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setGrid(String[][] grid) {
		this.letterGrid = grid;
	}
	
	public String[][] getGrid() {
		return letterGrid;
	}
}
