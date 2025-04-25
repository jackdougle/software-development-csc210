import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class test {
	
//	public static void main(String[] args) {
//		Flower flowey = new Flower("sunflower");
//		Class <?> c = flowey.getClass();
//		System.out.print(c.getName().getClass().getName());
//		
//		// gradescope: com.gradescope.garden.Flower
//		
//	}
	
	
	public static void horizontalInsert(String[][] grid, Word word, int num, Random rand) {
		String str = word.getWord();
		int r = rand.nextInt(grid[num].length - str.length());
		for (int j = 0; j < str.length(); j++) {
			String letter = word.getLetter(j);
			grid[num][r + j] = letter.toUpperCase();
			word.addCoord(num, r + j);
		}
	}
	
	public static void verticalInsert(String[][] grid, Word word, int num, Random rand) {
		String str = word.getWord();
		int r = rand.nextInt(grid.length - str.length());
		for (int i = 0; i < str.length(); i++) {
			String letter = word.getLetter(i);
			grid[r + i][num] = letter.toUpperCase();
			word.addCoord(r + i, num);
		}
	}
	
	public static void diagonalInsert(String[][] grid, Word word, int num, Random rand) {
		String str = word.getWord();
		int r = rand.nextInt(grid.length - str.length());
		for (int i = 0; i < str.length(); i++) {
			String letter = word.getLetter(i);
			grid[num + i][r + i] = letter.toUpperCase();
			word.addCoord(num + i, r + i);
		}
	}
	
//	public void addAllWords(String[][] grid) {
//		HashMap<int[], String> letterList = database.getLetterList();
//		for (int [] c : letterList.keySet()) {
//			int i = c[0]; int j = c[1];
//			grid[i][j] = letterList.get(c);
//		}
//	}
	
	public static void addWords(String[][] grid, ArrayList<String> words) {
		Random randy = new Random();
		for (int i = 0; i < words.size(); i++) {
			Word temp = new Word(words.get(i));
			
			
			int insertType = randy.nextInt(3);
			if (insertType == 0 && words.get(i).length() + i < grid.length) {
				diagonalInsert(grid, temp, i, randy);
			} else if (insertType == 1 && words.get(i).length() + i < grid.length) {
				verticalInsert(grid, temp, i, randy);
			} else horizontalInsert(grid, temp, i, randy);
		}
	}
	

	
}