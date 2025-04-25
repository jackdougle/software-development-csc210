/*
 * Author: Jack Douglass
 * File: App.java (Long Assignment 07)
 * Class: CSC 210, Fall 2024
 * Purpose: This program makes a visual GUI of the Garden Project, a Java 
 * project that created a visual garden using Plant objects and then visually 
 * represented them with a 2D array of characters. This program creates a 
 * visual GUI that displays the garden dynamically, with each input command 
 * affected the garden once applied. This program has a user interface, and a 
 * space for the user to input commands. To use this program, the user should 
 * input their desired garden dimensions as arguments via the Terminal and then
 * type in commands into the text box once the visual GUI has loaded. The user
 * should then hit the "Apply Action" to see their request applied to the garden.
 * Not all commands are valid, and invalid commands will not be applied and may
 * receive an error message.
 */

package workspace.files;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    // Constants for the program
    private final static int TEXT_SIZE = 120;
    private final static int RECT_SIZE = 6;
    private final static int CELL_SIZE = 15;

    private static int SIZE_ACROSS;
    private static int SIZE_DOWN;

    private static int ROWS;
    private static int COLS;

    // Define as null so it is a global variable and can be instantiated later
    private Garden myGarden = null;

    public static void main(String[] args) throws FileNotFoundException {
        ROWS = Integer.valueOf(args[0]);
        COLS = Integer.valueOf(args[1]);
        launch(args);
    }

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        // This function initializes the Garden object to the correct paramters and creates 
        // the GraphicsContext instance that will be the canvas for the garden visual GUI

        SIZE_ACROSS = ROWS * 75;
        SIZE_DOWN = COLS * 75;

        myGarden = new Garden(ROWS, COLS);

        TextArea command = new TextArea();

        Button execute = new Button("Apply Action");

        TextField userInput = new TextField("Input Commands Here");

        GraphicsContext gc = setupStage(primaryStage, SIZE_ACROSS, SIZE_DOWN, command, 
        execute, userInput);

        gardenDraw(gc);

        primaryStage.show();
        simulateGarden(gc, command, execute, userInput);
    }

    /**
     * @param gc
     *            GraphicsContext for drawing garden to.
     * @param command
     *            Reference to text area to show simulation commands.
     */
    private void simulateGarden(GraphicsContext gc, TextArea command, Button execute, 
    TextField input) throws FileNotFoundException {
        // This function runs the garden files, reads the input file and then 
        // updates the Garden object myGarden, which is used to update the GUI

        execute.setOnAction((actionEvent) -> {
            String commandStr = input.getText();

            // Get request from TextField and apply it to Garden
            parseCommand(commandStr, command, gc);
            input.clear();
        });
    }

    @SuppressWarnings("exports")
    public void parseCommand(String commandStr, TextArea command, GraphicsContext gc) {
            // This function casts user input requests into RunGarden methods,
            // affecting the visual garden
            String[] lst = commandStr.split(" ");
            lst[0] = lst[0].toUpperCase();

            // Variable checks if plot with input coordinates can have the action applied
            boolean valid = true;
            if (lst.length > 1 && lst[1].charAt(0) == '(') {
                valid = myGarden.validCoords(lst[0], lst[1]);
            }

            // Output lines with content only
            if (valid) command.appendText("> " + String.join(" ", lst) + "\n");
            else command.appendText("Can't " + lst[0].toLowerCase() + " there.\n");

            // Delegate RunGarden functions based on input command
            if (lst[0].equals("PLANT")) RunGarden.parsePlant(lst, myGarden);
            else if (lst[0].equals("GROW")) RunGarden.parseGrow(lst, myGarden);
            else if (lst[0].equals("PICK")) RunGarden.parsePick(lst, myGarden);
            else if (lst[0].equals("CUT")) RunGarden.parseCut(lst, myGarden);
            else if (lst[0].equals("HARVEST")) RunGarden.parseHarvest(lst, myGarden);
            else if (lst[0].equals("CHOP")) RunGarden.parseChop(lst, myGarden);
            
            // Update visual GUI after each command
            gardenDraw(gc);
    }

    /**
     * Sets up the whole application window and returns the GraphicsContext from
     * the canvas to enable later drawing. Also sets up the TextArea, which
     * should be originally be passed in empty.
     * 
     * @param primaryStage
     *            Reference to the stage passed to start().
     * @param canvas_width
     *            Width to draw the canvas.
     * @param canvas_height
     *            Height to draw the canvas.
     * @param command
     *            Reference to a TextArea that will be setup.
     * @return Reference to a GraphicsContext for drawing on.
     */
    @SuppressWarnings("exports")
    public GraphicsContext setupStage(Stage primaryStage, int canvas_width,
            int canvas_height, TextArea command, Button execute, TextField input) {
        // Border pane will contain canvas for drawing and text area underneath
        BorderPane p = new BorderPane();

        // Canvas(pixels across, pixels down)
        Canvas canvas = new Canvas(SIZE_ACROSS, SIZE_DOWN);

        // Command TextArea will hold the commands from the file
        command.setPrefHeight(TEXT_SIZE);
        command.setEditable(false); 

        // Make textField cover length of the app
        input.setPrefSize(437, 20);

        // Place the canvas, button, input, and command output areas in pane.
        p.setTop(canvas);
        p.setRight(execute);
        p.setLeft(input);
        p.setBottom(command);

        primaryStage.setTitle("Jack Douglass's Digital Garden, Now With User Input!");
        primaryStage.setScene(new Scene(p));

        return canvas.getGraphicsContext2D();
    }

    @SuppressWarnings("exports")
    public void gardenReset(GraphicsContext gc) {
        // This function resets the garden to get rid of outdated visual elements

        gc.setFill(Color.valueOf("white"));  // Put white canvas over previous elements
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    @SuppressWarnings("exports")
    public void gardenDraw(GraphicsContext gc) {
        // This function updates the visual GUI based on the newest Garden object

        char[][] charGarden = RunGarden.visualizeGarden(myGarden);
        Plot[][] plots = myGarden.getPlots();
        gardenReset(gc);

        for (int i = 0; i < charGarden.length; i++) {
            for (int j = 0; j < charGarden[i].length; j++) {
                // Iterate through the charGarden to translate to visual GUI garden
                if (charGarden[i][j] == '.') {
                    gc.setFill(new Color(0, 0.65, 0, 0.3));
                    gc.fillRect(5 + j * CELL_SIZE, 5 + i * CELL_SIZE, RECT_SIZE, RECT_SIZE);
                // Change color (RGB values) and shape of drawing based on type of Plant
                // Use truncated division to get correct plot number (plot = 5 * 5)
                } else if (plots[i / 5][j / 5].getPlant() instanceof Flower) {
                    gc.setFill(new Color(0.65, 0, 0, 0.5));
                    gc.fillOval(2 + j * CELL_SIZE, 2 + i * CELL_SIZE, RECT_SIZE * 2, RECT_SIZE * 2);
                } else if (plots[i / 5][j / 5].getPlant() instanceof Tree) {
                    gc.setFill(new Color(0.43, 0.25, 0.13, 0.5));
                    gc.fillRect(0.5 + j * CELL_SIZE, 2 + i * CELL_SIZE, RECT_SIZE*2.5, RECT_SIZE*2);
                } else if (plots[i / 5][j / 5].getPlant() instanceof Vegetable) {
                    gc.setFill(new Color(0, 0.5, 0, 0.5));
                    gc.fillRect(5 + j * CELL_SIZE, 2 + i * CELL_SIZE, RECT_SIZE, RECT_SIZE * 2);
                } else if (plots[i / 5][j / 5].getPlant() instanceof Vine) {
                    gc.setFill(new Color(0, 0.75, 0, 0.5));
                    gc.fillOval(4 + j * CELL_SIZE, 2 + i * CELL_SIZE, RECT_SIZE*1.5, RECT_SIZE*2);
                }
            }
        }
    }

}