// Author: Jack Douglass
// File: RunGarden.java
// Class: CSC 210, Fall 2024
// Purpose: This program creates a garden comprised of empty spots (null) and Plant objects and makes it visible through creating a 2D array of characters to represent it. It does this by using instructions provided in an inputed file. This class defines methods for parsing instructions in an input file (main), processing different instructions (parsePlant, parseGrow, parsePrint, parsePick, parseCut, parseHarvest), creating a 2D array of chars to represent the garden (visualizeGarden) and a method to print the 2D char array garden (printCharGarden).A user looking to use this program should input a file as an argument for this class. The file should contain instructions on how large the garden should be, what plants are planted, which and when plants are removed, and when the char garden is printed.

package workspace.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RunGarden {
	
	public static char[][] visualizeGarden(Garden myGarden) {
		// this method creates a 2D char array that represents the garden placed as a parameter
		
		Plot[][] plots = myGarden.getPlots();
		
		int rows = plots.length; int cols = plots[0].length;
		
		// each plot is a 5 * 5 grid, thus number of character is 25 * plot number
		char[][] charGarden = new char[rows * 5][cols * 5];
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[i].length; j++) {
				
				Plant[][] plants = plots[i][j].getPlants();
				for (int p = 0; p < 5; p++) {
					for (int q = 0; q < 5; q++) {
						if (plants[p][q] == null) {
							// null spots are represented as periods
							charGarden[i * 5 + p][j * 5 + q] = '.';
						} else {
							// plants are displayed as type's first letter
							charGarden[i * 5 + p][j * 5 + q] = plants[p][q].getLetter();
						}
					}
				}
			}
		}
		
		return charGarden;
	}
	
	public static void printCharGarden(char[][] charGarden, Boolean last) {
		// prints the charGarden, 1 line at a time; the function's output
		
		for (int i = 0; i < charGarden.length; i++) {
			String line = "";
			for (char letter : charGarden[i]) {
				line += letter;
			}
			// make sure final line of program leaves no extra space
			if (i >= charGarden.length - 1 && last) System.out.print(line);
			else System.out.println(line);
		}
	}
	
	public static void parsePlant(String[] line, Garden myGarden) {
		// parses inputed PLANT command, and starts plant functions in the Garden

		myGarden.addPlant(line[1], line[2].toLowerCase().strip());
	}
	
	public static void parseGrow(String[] line, Garden myGarden) {
		// parses inputed GROW command, and starts plant functions in the Garden
		
		if (line.length == 2) {
			// System.out.println("> GROW " + line[1]);
			myGarden.grow(Integer.valueOf(line[1]));
		} else {
			// System.out.println("> GROW " + line[1] + " " + line[2]);
		
			if (line[2].contains(",")) {
				String noParens = line[2].substring(1, line[2].length() - 1);
				String[] nums = noParens.split(",");
				myGarden.grow(Integer.valueOf(line[1]), Integer.valueOf(nums[0]), Integer.valueOf(nums[1]));
			}
			
			//  handle class grow requests here to not do type grow requests
			else if (line[2].toUpperCase().equals("FLOWER")) myGarden.grow("Flower", Integer.valueOf(line[1]));
			else if (line[2].toUpperCase().equals("TREE")) myGarden.grow("Tree", Integer.valueOf(line[1]));
			else if (line[2].toUpperCase().equals("VEGETABLE")) myGarden.grow("Vegetable", Integer.valueOf(line[1])); 
			else if (line[2].toUpperCase().equals("VINE")) myGarden.grow("Vine", Integer.valueOf(line[1])); 
			
			else myGarden.grow(Integer.valueOf(line[1]), line[2]);
		}
			
		// System.out.print("\n");
	}
	
	public static void parsePick(String[] line, Garden myGarden) {
		// parses inputed PICK command, and starts plant functions in the Garden
		
		if (line.length == 1) {
			// System.out.println("> " + line[0].toUpperCase());
			myGarden.pick();
		} else {
			// System.out.println("> " + line[0].toUpperCase() + " " + line[1]);
			
			if (line[1].contains(",")) {
				// extract int values from coordinate inputs
				String noParens = line[1].substring(1, line[1].length() - 1);
				String[] coords = noParens.split(",");
				myGarden.pick(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
			} else {
				myGarden.pick(line[1]);
			}
		}

		// System.out.print("\n");
	}
	
	public static void parseCut(String[] line, Garden myGarden) {
		// parses inputed CUT command, and starts plant functions in the Garden
		
		if (line.length == 1) {
			// System.out.println("> " + line[0].toUpperCase());
			myGarden.cut();
		} else {
			// System.out.println("> " + line[0].toUpperCase() + " " + line[1]);
			
			if (line[1].contains(",")) {
				// extract int values from coordinate inputs
				String noParens = line[1].substring(1, line[1].length() - 1);
				String[] coords = noParens.split(",");
				myGarden.cut(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
			} else {
				myGarden.cut(line[1]);
			}
		}
		
		// System.out.print("\n");
	}

	public static void parseHarvest(String[] line, Garden myGarden) {
		// parses inputed HARVEST command, and starts plant functions in the Garden
		
		if (line.length == 1) {
			// System.out.println("> " + line[0].toUpperCase());
			myGarden.harvest();
		} else {
			// System.out.println("> " + line[0].toUpperCase() + " " + line[1]);
			
			if (line[1].contains(",")) {
				// extract int values from coordinate inputs
				String noParens = line[1].substring(1, line[1].length() - 1);
				String[] coords = noParens.split(",");
				myGarden.harvest(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
			} else {
				myGarden.harvest(line[1]);
			}
		}
		
		// System.out.print("\n");
	}

	public static void parseChop(String[] line, Garden myGarden) {
		// parses inputed CHOP command, and starts plant functions in the Garden
		
		if (line.length == 1) {
			// System.out.println("> " + line[0].toUpperCase());
			myGarden.chop();
		} else {
			// System.out.println("> " + line[0].toUpperCase() + " " + line[1]);
			
			if (line[1].contains(",")) {
				// extract int values from coordinate inputs
				String noParens = line[1].substring(1, line[1].length() - 1);
				String[] coords = noParens.split(",");
				myGarden.chop(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
			} else {
				myGarden.chop(line[1]);
			}
		}
		
		// System.out.print("\n");
	}
	
	public static void parsePrint(String[] line, Garden myGarden, boolean last) {
		// parses inputed PRINT command, and starts plant functions in the Garden
		
		System.out.println("> PRINT");
		
		char[][] charGarden = visualizeGarden(myGarden);
		
		// if last inputed command of file, don't leave newline after printed charGarden
		if (last) printCharGarden(charGarden, true);
		else printCharGarden(charGarden, false);
	
	}
	
	public void main(String[] args) throws FileNotFoundException {
		String filename = args[0];
		File myFile = new File(filename);
		Garden myGarden = null;
		
		int rows = 1;
		int cols = 1;
		
		@SuppressWarnings("resource")
		Scanner fReader = new Scanner(myFile);
		
		while (fReader.hasNextLine()) {
			String[] line = fReader.nextLine().split(" ");
			
			if (line[0].equals("rows:")) {
				rows = Integer.valueOf(line[1].strip());
				line = fReader.nextLine().split(" ");
			}
			
			if (line[0].equals("cols:")) {
				cols = Integer.valueOf(line[1].strip());
				if (cols > 16) {  // max of 8 characters across (16 * 5)
					System.out.print("Too many plot columns.");
					return;
				}
			}
			
			else if (myGarden == null) myGarden = new Garden(rows, cols);
			
			// get first word of every line to parse inputs
			
			else if (line[0].toUpperCase().equals("PLANT")) parsePlant(line, myGarden);
			else if (line[0].toUpperCase().equals("GROW")) parseGrow(line, myGarden);
			else if (line[0].toUpperCase().equals("PRINT")) {
				// print without new line after final charGarden is printed
				if (fReader.hasNextLine()) {
					parsePrint(line, myGarden, false);
					System.out.print("\n");
				} else parsePrint(line, myGarden, true);
			}
			
			else if (line[0].toUpperCase().equals("PICK")) parsePick(line, myGarden);
			else if (line[0].toUpperCase().equals("CUT")) parseCut(line, myGarden);
			else if (line[0].toUpperCase().equals("HARVEST")) parseHarvest(line, myGarden);
			else if (line[0].toUpperCase().equals("CHOP")) parseChop(line, myGarden);
		}

		fReader.close();
		
	}

}
