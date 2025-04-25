/* 
 * Author: Jack Douglass
 * File: WordSearch.java
 * Class: CSC 210, Fall 2024
 * Purpose: This program simulates a word search game. This program works by creating the 
 * word search board out of random letters. The dimensions of the board (how many letters 
 * long and tall the board is) is determined from information from a user input file. The 
 * user input file also contains a list of words to add into the letter myGrid. The letter 
 * myGrid, with all of the input words, is then turned into a file which is the program's 
 * output. To use this program, the user should provide a file name as a program argument. 
 * This filename should correspond to a file that is a list of words (one on every line) 
 * that should be input into the file. The first line of the file should contain the 
 * desired dimensions for the word myGrid in the format "x y". The user will then receive 
 * the finished word myGrid as an output file.
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WordSearch {
	
	public static ArrayList<String> readFile(String filename) throws FileNotFoundException {
		// This function creates a file from an input filename and reads its contents. This file's 
		// information contains the dimensions of the letter grid and the words to put inside it.
		
		File wordFile = new File(filename);
		Scanner fReader = new Scanner(wordFile);
		
		ArrayList<String> lines = new ArrayList<>();
		
		while (fReader.hasNextLine()) {
			lines.add(fReader.nextLine());
		}
		
		fReader.close();
		return lines;
	}
	
	public static void addWord(String[][] letterGrid, WordList database, String word) {
		// This function finds a location and an orientation for a word to be placed in the letter grid.
		// It tests orientation/location combos until it finds one that works in the grid (no overlapping
		// words) and then it places it there.
		
		Random rand = new Random();
		
		int yStart = rand.nextInt(letterGrid.length); int xStart = rand.nextInt(letterGrid[0].length);
		
		int xIncr = rand.nextInt(2); int yIncr = rand.nextInt(2);
		
		if (xIncr == 0 && yIncr == 0) {  // If both are 0, the word will not be added
			yIncr += 1;
			xIncr += 1;
		}
		
		if (works(letterGrid, word, yStart, xStart, yIncr, xIncr)) applyWord(letterGrid, database, word, yStart, xStart, yIncr, xIncr);
		else addWord(letterGrid, database, word);
	}
	
	public static boolean works(String[][] letterGrid, String word, int yStart, int xStart, int yIncr, int xIncr) {
		// This function tests if a word can be successfully placed into the letter grid given a starting 
		// location and orientation.
		
		for (int i = 0; i < word.length(); i++) {
			int[] positions = getPositions(yStart, yIncr, xStart, xIncr, i);
			
			// Avoid collisions
			if (!inBounds(letterGrid, positions[0], positions[1]) || letterGrid[positions[0]][positions[1]] != ".") return false;
		}
		
		return true;
	}
	
	public static boolean inBounds(String[][] letterGrid, int y, int x) {
		// This function checks if given coordinates are within the bounds of the letter grid.
		
		return (y > 0 && y < letterGrid.length && x > 0 && x < letterGrid[y].length);
	}
	
	public static void applyWord(String[][] letterGrid, WordList database, String word, int yStart, int xStart, int yIncr, int xIncr) {
		// This function puts a word into the letter grid. It does this by starting that the given starting 
		// coordinates and then progressing along a given direction (denoted by the given x and y increments)
		
		database.addWord(word.toUpperCase());
		
		for (int i = 0; i < word.length(); i++) {
			String letter = String.valueOf(word.charAt(i));
			
			int[] positions = getPositions(yStart, yIncr, xStart, xIncr, i);
			
			// Index 'i' is multiplied by increment to get horizontal, vertical, and diagonal words.
			letterGrid[positions[0]][positions[1]] = letter.toUpperCase();
		}
		
	}
	
	public static void readInputs(WordGrid myGrid, WordList database) {
		// This function reads in inputs from a user in order to play a word search game. It does this using 
		// a REPL (read-eval-print-loop) and asks the user for information about the word they found and then
		// it checks the area to see if the user was correct. It ends the game once all words have been found
		
		String[][] letterGrid = myGrid.getGrid();
		Scanner iReader = new Scanner(System.in);
		
		while (!database.isEmpty()) {  // Go until all words have been found
			System.out.print(myGrid.toString());
			
			System.out.print("\nEnter word: ");  // Get word
			String word = iReader.next().toUpperCase().strip();
			
			System.out.print("Enter x: ");  // Location
			String x = iReader.next().strip();
			
			System.out.print("Enter y: ");
			String y = iReader.next().strip();
			
			System.out.print("[H]orizontal [V]ertical [D]iagonal? ");  // Orientation
			String dir = iReader.next().toUpperCase().strip();
			
			if (searchWord(letterGrid, dir, word, y, x)) removeWord(letterGrid, database, dir, word, y, x);
			else System.out.println("\n" + word.toLowerCase() + " not found\n");
		}
		
		System.out.println(myGrid.toString());  // Print final word grid
		System.out.println("All words found!");
		System.exit(0);  // Exit the game
		iReader.close();
	}

	public static boolean searchWord(String[][] letterGrid, String dir, String word, String y, String x) {
		// This function checks to see if the user's input word information is correct
		
		int[] increments = getIncrements(dir);
		int yIncr = increments[0]; int xIncr = increments[1];
		
		int yStart = -1; int xStart = -1;
		
		// Convert ASCII value of char to usable number, alphabet starts at 97 so subtract 97
		yStart = Integer.valueOf(x); xStart = (int) y.strip().charAt(0) - 97; 
		
		for (int i = 0; i < word.length(); i++) {
			String letter = String.valueOf(word.charAt(i));
			
			int[] positions = getPositions(yStart, yIncr, xStart, xIncr, i); 
			
			// Index 'i' is multiplied by increment to get horizontal, vertical, and diagonal words.
			if (!letterGrid[positions[0]][positions[1]].equals(letter)) return false;
		}
		
		return true;
	}
	
	public static void removeWord(String[][] letterGrid, WordList database, String dir, String word, String y, String x) {
		// Removes a word from the word grid and word database and replaces it with asterisks
		
		int[] increments = getIncrements(dir);
		int yIncr = increments[0]; int xIncr = increments[1];
		
		int yStart = -1; int xStart = -1; 
		yStart = Integer.valueOf(x); xStart = (int) y.strip().charAt(0) - 97;
		
		for (int i = 0; i < word.length(); i++) {
			
			int[] positions = getPositions(yStart, yIncr, xStart, xIncr, i);
			
			// Index 'i' is multiplied by increment to get words of all orientations
			letterGrid[positions[0]][positions[1]] = "*";
		}
		
		database.removeWord(word);
		System.out.println("\n" + word.toLowerCase() + " removed\n");
	}
	
	public static int[] getIncrements(String dir) {
		// This function gets the x and y values the grid iterators will be multiplied by based 
		// on the direction the user inputs
		
		int yIncr; int xIncr;
		if (dir.equals("H")) {
			yIncr = 0; xIncr = 1;  // Horizontal traversal: x coordinate moves but y does not
		} else if (dir.equals("V")) {
			yIncr = 1; xIncr = 0;  
		} else {
			yIncr = 1; xIncr = 1;
		}
		
		int[] increments = {yIncr, xIncr};
		return increments;
	}
	
	public static int[] getPositions(int yStart, int yIncr, int xStart, int xIncr, int idx) {
		// This function calculates the x and y coordinates being modified or examined by multiplying 
		// the each dimensions increment by the index, and then adding both values to each starting value
		
		int[] positions = {yStart + yIncr * idx, xStart + xIncr * idx};
		
		return positions;
	}
	
	public static void main(String[] args) throws IOException {
		String filename = args[0];
		ArrayList<String> lines = readFile(filename);
		
		WordList database = new WordList();
		
		WordGrid myGrid = new WordGrid(lines.remove(0));
		myGrid.makeGrid();
		String[][] letterGrid = myGrid.getGrid();

		while (lines.size() > 0) addWord(letterGrid, database, lines.remove(0));
		
		myGrid.fillGrid();
		
		readInputs(myGrid, database);
	}

}
