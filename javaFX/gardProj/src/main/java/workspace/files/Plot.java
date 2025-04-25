// Author: Jack Douglass
// File: Plot.java
// Class: CSC 210, Fall 2024
// Purpose: The purpose of this program is to simulate a plot of land that could hold a plant. To do this, it has a 2D array of Plant objects, the amount and location of the Plant objects determine what type of plant it is and how grown it is. The parts of the 2D Plant array that are null represent spaces with no plant. To use this program, input a file into RunGarden.java containing instructions to create a garden. That will create Plot instances, as Plot.java is a file that serves as part of the functionality of Garden.java and RunGarden.java.

package workspace.files;

import java.util.ArrayList;

public class Plot {
	private Plant[][] plants = new Plant[5][5];

	public Plot() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				plants[i][j] = null;
			}
		}
	}
	
	public void addFlower(String type) {
		// adds a Flower object to the Plot
		
		this.plants[2][2] = new Flower(type);
	}
	
	public void addTree(String type) {
		// adds a Tree object to the Plot
		
		this.plants[4][2] = new Tree(type);
	}
	
	public void addVegetable(String type) {
		// adds a Vegetable object to the Plot
		
		this.plants[0][2] = new Vegetable(type);
	}

	public void addVine(String type) {
		// adds a Vine object to the Plot

		this.plants[4][0] = new Vine(type);
	}
	
	public void grow(int amt) {
		// this method increases the size of whatever plant is inside of it, each plant type grows in a different way
		
		Plant p = this.getPlant();
		if (p instanceof Flower) this.fGrow(amt);
		else if (p instanceof Tree) this.tGrow(amt);
		else if (p instanceof Vegetable) this.veGrow(amt);
		else if (p instanceof Vine) this.viGrow(amt);
	}
	
	public void fGrow(int amt) {
		// grow method for Flowers
		
		Plant rootFlower = plants[2][2];
		String fType = rootFlower.getType();
		
		while (amt > 0) {
			// new null spots are turned into flowers depending on Flower's current stage of development
			if (rootFlower.getSize() == 1) {
				plants[1][2] = new Flower(fType);
				plants[2][1] = new Flower(fType);
				plants[2][3] = new Flower(fType);
				plants[3][2] = new Flower(fType);
			} else if (rootFlower.getSize() == 2) {
				plants[0][2] = new Flower(fType);
				plants[1][1] = new Flower(fType);
				plants[1][3] = new Flower(fType);
				plants[2][0] = new Flower(fType);
				plants[2][4] = new Flower(fType);
				plants[3][1] = new Flower(fType);
				plants[3][3] = new Flower(fType);
				plants[4][2] = new Flower(fType);
			} else if (rootFlower.getSize() == 3) {
				plants[0][1] = new Flower(fType);
				plants[0][3] = new Flower(fType);
				plants[1][0] = new Flower(fType);
				plants[1][4] = new Flower(fType);
				plants[3][0] = new Flower(fType);
				plants[3][4] = new Flower(fType);
				plants[4][1] = new Flower(fType);
				plants[4][3] = new Flower(fType);
			} else if (rootFlower.getSize() == 4) {
				plants[0][0] = new Flower(fType);
				plants[0][4] = new Flower(fType);
				plants[4][0] = new Flower(fType);
				plants[4][4] = new Flower(fType);
			}
			
			rootFlower.grow();
			amt -= 1;
		}
	}
	
	public void tGrow(int amt) {
		// grow method for Trees
		
		Plant rootTree = plants[4][2];
		String tType = rootTree.getType();
		
		while (amt > 0) {
			// Trees grow upwards, so each cell is above the previous one
			if (rootTree.getSize() == 1) {
				plants[3][2] = new Tree(tType);
			} else if (rootTree.getSize() == 2) {
				plants[2][2] = new Tree(tType);
			} else if (rootTree.getSize() == 3) {
				plants[1][2] = new Tree(tType);
			} else if (rootTree.getSize() == 4) {
				plants[0][2] = new Tree(tType);
			}
			
			rootTree.grow();
			amt -= 1;
		}
	}
	
	public void veGrow(int amt) {
		// grow method for Vegetables
		
		Plant rootVegetable = plants[0][2];
		String vType = rootVegetable.getType();
		
		while (amt > 0) {
			// Vegetables grow downwards, so each cell is below the previous one
			if (rootVegetable.getSize() == 1) {
				plants[1][2] = new Vegetable(vType);
			} else if (rootVegetable.getSize() == 2) {
				plants[2][2] = new Vegetable(vType);
			} else if (rootVegetable.getSize() == 3) {
				plants[3][2] = new Vegetable(vType);
			} else if (rootVegetable.getSize() == 4) {
				plants[4][2] = new Vegetable(vType);
			}
			
			rootVegetable.grow();
			amt -= 1;
		}
	}

	public void viGrow(int amt) {
		// grow method for Vines
		
		Plant rootVine = plants[4][0];
		String vType = rootVine.getType();
		
		while (amt > 0) {
			// Vines grow downwards, so each cell is below the previous one
			if (rootVine.getSize() == 1) {
				plants[3][1] = new Vine(vType);
			} else if (rootVine.getSize() == 2) {
				plants[2][2] = new Vine(vType);
			} else if (rootVine.getSize() == 3) {
				plants[1][3] = new Vine(vType);
			} else if (rootVine.getSize() == 4) {
				plants[0][4] = new Vine(vType);
			}
			
			rootVine.grow();
			amt -= 1;
		}
	}
	
	public void reset() {
		// gets rid of all plants in this plot by making every spot null
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				plants[i][j] = null;
			}
		}
	}
	
	public Plant getPlant() {
		// returns the plant in the plot if there is one

		for (int i = 0; i < plants.length; i++) {
			for (int j = 0; j < plants[i].length; j++) {
				if (plants[i][j] != null) return plants[i][j];
			}
		}
		return null;
	}
	
	public Plant[][] getPlants() {
		return plants;
	}
	
	public String getType() {
		// returns the type of the plant in the plot, if there is a plant
		
		if (this.getPlant() != null) {
			return this.getPlant().getType();
		} else return null;
	}
	
	public String getPClass() {
		// returns the class of the plot's plant, in string format
		
		// large loop to circumvent gradescope's modification to the class name
		if (this.getPlant() != null) {
			Class<?> gs_class = this.getPlant().getClass();
			String str = gs_class.getName();
			ArrayList<String> lst = new ArrayList<String>();
			String word = "";
			
			// creates a list of words that comprise gradescope's class name to actual class name can be derived
			for (int r = 0; r < str.length(); r++) {
				if (r == str.length() - 1) {
					word += str.charAt(r);
					lst.add(word);
				} if (str.charAt(r) != '.') {
					word += str.charAt(r);
				} else {
					lst.add(word);
					word = "";
				}
			}
			
			String c = lst.get(lst.size() - 1);
			return c;
		} else {
			return null;
		}
		

	}

	public Plant[][] getPlot() {
		return plants;
	}
}
