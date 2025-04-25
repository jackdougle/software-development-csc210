
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
  
class RunGardenTest {
	
	private String readExpectedOutput(String filename) throws FileNotFoundException {
        File myFile = new File(filename);
        Scanner myReader = new Scanner(myFile);
        String content = "";
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            content += line + "\n";
        }
        myReader.close();

        return content;

    }

	@Test
	void testAddTooMuch() throws FileNotFoundException {
		PrintStream standardOut = System.out;
	    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    String studentOutput;
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] names = {"addtoomuch.in"};
        RunGarden.main(names);
        String expectedOutput = readExpectedOutput("pa5-addtoomuch.out");
        studentOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput.trim(), studentOutput.trim());
        System.setOut(standardOut);
	}
	
	@Test
	void testBadCommands() throws FileNotFoundException {
		PrintStream standardOut = System.out;
	    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    String studentOutput;
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] names = {"badcommands.in"};
        RunGarden.main(names);
        String expectedOutput = readExpectedOutput("pa5-badcommands.out");
        studentOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput.trim(), studentOutput.trim());
        System.setOut(standardOut);
	}
	
	@Test
	void testOneBananaGrowsOnce() throws FileNotFoundException {
		PrintStream standardOut = System.out;
	    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    String studentOutput;
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] names = {"onebanana_growonce.in"};
        RunGarden.main(names);
        String expectedOutput = readExpectedOutput("pa5-onebanana_growonce.out");
        studentOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput.trim(), studentOutput.trim());
        System.setOut(standardOut);
	}
	
	@Test
	void testOneOfEverythingGrowsOnce() throws FileNotFoundException {
		PrintStream standardOut = System.out;
	    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    String studentOutput;
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] names = {"oneofeverything_growonce.in"};
        RunGarden.main(names);
        String expectedOutput = readExpectedOutput("pa5-oneofeverything_growonce.out");
        studentOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput.trim(), studentOutput.trim());
        System.setOut(standardOut);
	}
	
	@Test
	void testOneRoseGrowOnce() throws FileNotFoundException {
		PrintStream standardOut = System.out;
	    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    String studentOutput;
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] names = {"onerose_growonce.in"};
        RunGarden.main(names);
        String expectedOutput = readExpectedOutput("pa5-onerose_growonce.out");
        studentOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput.trim(), studentOutput.trim());
        System.setOut(standardOut);
	}
	
	@Test
	void testOneYamGrowsOnce() throws FileNotFoundException {
		PrintStream standardOut = System.out;
	    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    String studentOutput;
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] names = {"oneyam_growonce.in"};
        RunGarden.main(names);
        String expectedOutput = readExpectedOutput("pa5-oneyam_growonce.out");
        studentOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput.trim(), studentOutput.trim());
        System.setOut(standardOut);
	}
	
	@Test
	void testPlantCool() throws FileNotFoundException {
		PrintStream standardOut = System.out;
	    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    String studentOutput;
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] names = {"plantcool.in"};
        RunGarden.main(names);
        String expectedOutput = readExpectedOutput("pa5-plantcool.out");
        studentOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput.trim(), studentOutput.trim());
        System.setOut(standardOut);
	}
	
	@Test
	void testThreeTreesGrowThree() throws FileNotFoundException {
		PrintStream standardOut = System.out;
	    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	    String studentOutput;
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] names = {"threetrees_growthree.in"};
        RunGarden.main(names);
        String expectedOutput = readExpectedOutput("pa5-threetrees_growthree.out");
        studentOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput.trim(), studentOutput.trim());
        System.setOut(standardOut);
	}

}