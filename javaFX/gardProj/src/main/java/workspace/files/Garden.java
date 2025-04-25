// Author: Jack Douglass
// File: Garden.java
// Class: CSC 210, Fall 2024
// Purpose: This class simulates a garden with plots. A plot can either have a flower, tree, or a vegetable. These plants are represented by the Flower, Tree and Vegetable classes respectively. To construct a garden and use this class, a user should provide a file containing instructions as an argument to RunGarden.java.

package workspace.files;

public class Garden {
	private Plot[][] plots;
	
	// constant String[] values here to sort plants by type
	private static final String[] FLOWER_TYPES = {"iris", "lily", "rose", "daisy", "tulip", "sunflower"};
	private static final String[] TREE_TYPES = {"oak", "willow", "banana", "coconut", "pine", "birch"};
	private static final String[] VEGETABLE_TYPES = {"garlic", "zucchini", "tomato", "yam", "lettuce", "avocado"};
	private static final String[] VINE_TYPES = {"kudzu", "hogweed", "dodder", "wisteria", "knotweed", "hydrangea"};
	
	public Garden(int rows, int cols) {
		plots = new Plot[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				plots[i][j] = new Plot();
			}
		}
	}
	
	public Plot[][] getPlots() {
		return plots;
	}
	
	public void addPlant(String plot, String type) {
		// adds a plant to the garden using coordinates and type
		
		String subStr = plot.substring(1, plot.length() - 1);
		String[] coords = subStr.split(",");

		int x = Integer.valueOf(coords[0]);
		int y = Integer.valueOf(coords[1]);

		if (!isInBounds(x, y) || plots[x][y].getPlant() != null) {
			// System.out.println("Can't plant there.\n");
		} else {
			for (int i = 0; i < 6; i++) {
				if (type.equals(FLOWER_TYPES[i])) plots[x][y].addFlower(type);
				else if (type.equals(TREE_TYPES[i])) plots[x][y].addTree(type);
				else if (type.equals(VEGETABLE_TYPES[i])) plots[x][y].addVegetable(type);
				else if (type.equals(VINE_TYPES[i])) plots[x][y].addVine(type);
			}
		}
	}
	
	public void grow(int amt) {
		// grows every plant by 'amt' additions
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[i].length; j++) {
				plots[i][j].grow(amt);
			}
		}
	}
	
	public void grow(int amt, int x, int y) {
		// grows plant in specified plot in coordinates (x, y)
		
		if (!isInBounds(x, y)) {
			// System.out.println("\nCan't grow there.");
		} else plots[x][y].grow(amt);
		
	}
	
	public void grow(int amt, String type) {
		// grows plants of type 'type'
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots.length; j++) {
				
				if (plots[i][j].getType() != null) {  // make sure there is a plant
					if (plots[i][j].getType().equals(type)) plots[i][j].grow(amt);
				}
			}
		}
	}
	
	public void grow(String clazz, int amt) {
		// grows plants of Class 'clazz'
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots.length; j++) {
				
				if (plots[i][j].getPlant() != null) {
					String c = plots[i][j].getPClass();
					if (c.equals(clazz)) plots[i][j].grow(amt);
				}
			}
		}
	}
	
	public boolean isInBounds(int x, int y) {
		// checks if coordinates are in bounds of garden
		
		return (x < plots.length && x >= 0 && y < plots[0].length && y >= 0);
	}
	
	public void pick() {
		// removes all Flowers from the Garden
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[0].length; j++) {
				if (plots[i][j].getPlant() != null) {
					if (plots[i][j].getPClass().equals("Flower")) plots[i][j].reset();
				}
			}
		}
	}
	
	public void pick(int x, int y) {
		// removes Flower from Plot (x, y) if that Plot has a Flower
		
		if(!isInBounds(x, y) || plots[x][y].getPlant() == null || !plots[x][y].getPClass().equals("Flower")) {
			// System.out.println("\nCan't pick there.");
		} else plots[x][y].reset();
	}
	
	public void pick(String type) {
		// removes Flowers of type 'type'
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[0].length; j++) {
				if (plots[i][j].getPlant() != null) {
					if (plots[i][j].getType().equals(type)) plots[i][j].reset();
				}
			}
		}
	}
	
	public void cut() {
		// removes all Trees from the Garden
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[0].length; j++) {
				if (plots[i][j].getPClass() != null) {
					if (plots[i][j].getPClass().equals("Tree")) plots[i][j].reset();
				}
			}
		}
	}
	
	public void cut(int x, int y) {
		// removes Tree from Plot (x, y) if that Plot has a Tree
		
		if(!isInBounds(x, y) || plots[x][y].getPlant() == null || !plots[x][y].getPClass().equals("Tree")) {
			// System.out.println("\nCan't cut there.");
		} else plots[x][y].reset();
	}
	
	public void cut(String type) {
		// removes Trees of type 'type'
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[0].length; j++) {
				if (plots[i][j].getPlant() != null) {
					if (plots[i][j].getType().equals(type)) plots[i][j].reset();
				}
			}
		}
	}
	
	public void harvest() {
		// removes all Vegetables from the Garden
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[0].length; j++) {
				if (plots[i][j].getPlant() != null) {
					if (plots[i][j].getPClass().equals("Vegetable")) plots[i][j].reset();
				}
			}
		}
	}
	
	public void harvest(int x, int y) {
		// removes Vegetable from Plot (x, y) if that Plot has a Vegetable
		
		if(!isInBounds(x, y) || plots[x][y].getPlant() == null || !plots[x][y].getPClass().equals("Vegetable")) {
			// System.out.println("\nCan't harvest there.");
		} else plots[x][y].reset();
	}
	
	public void harvest(String type) {
		// removes Vegetables of type 'type'
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[0].length; j++) {
				if (plots[i][j].getPlant() != null) {
					if (plots[i][j].getType().equals(type)) plots[i][j].reset();
				}
			}
		}
	}

	public void chop() {
		// removes all Vines from the Garden
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[0].length; j++) {
				if (plots[i][j].getPlant() != null) {
					if (plots[i][j].getPClass().equals("Vine")) plots[i][j].reset();
				}
			}
		}
	}
	
	public void chop(int x, int y) {
		// removes Vine from Plot (x, y) if that Plot has a Vine
		
		if(!isInBounds(x, y) || plots[x][y].getPlant() == null || !plots[x][y].getPClass().equals("Vine")) {
			// System.out.println("\nCan't chop there.");
		} else plots[x][y].reset();
	}
	
	public void chop(String type) {
		// removes Vines of type 'type'
		
		for (int i = 0; i < plots.length; i++) {
			for (int j = 0; j < plots[0].length; j++) {
				if (plots[i][j].getPlant() != null) {
					if (plots[i][j].getType().equals(type)) plots[i][j].reset();
				}
			}
		}
	}

	public boolean validCoords(String cType, String coords) {
		// This functions checks if the coordinates provided in user inputs correspond 
		// to a plot where the requested command can successfully be executed

		String[] nums = coords.substring(1, coords.length() - 1).split(",");
		int x = Integer.valueOf(nums[0]); int y = Integer.valueOf(nums[1]);

		// Only define Plant object to be checked if coordinates are in bounds
		Plant p;
		if (isInBounds(x, y)) p = plots[x][y].getPlant();
		else return false;

		// Switch case because each command has different prerequisites
		switch (cType) {
		case "PLANT":
			return (p == null);  // Cannot plant on an occupied plot
		case "GROW":
			return (p != null && p.getSize() < 5);
		case "PICK":
			return (p instanceof Flower);
		case "HARVEST":
			return (p instanceof Vegetable);
		case "CUT":
			return (p instanceof Tree);
		case "CHOP":
			return (p instanceof Vine);
		default:
			return false;
		}
	}
	
}
