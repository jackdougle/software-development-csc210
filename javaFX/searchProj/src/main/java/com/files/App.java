/*
 * Author: Jack Douglass
 * File: App.java (LA Bonus)
 * Class: CSC 210, Fall 2024
 * Purpose: The purpose of this class is to create a visual GUI that can simulate 
 * a word search game. This program works by using JavaFX software and packages 
 * to create a visual and interactable GUI that can run the word search game that 
 * is programmed in the three accompanying files: WordList.java, WordGrid.java, 
 * and WordSearch.java. To use this program, the user should run this program and 
 * the visual GUI should pop up. Once it has launched, the user should look at the 
 * word grid and look for words they find. They should then put the asked for 
 * information about the words they find and keep going until all words on the grid 
 * have been found.
 */

 package com.files;

 import java.io.FileNotFoundException;

 import java.util.ArrayList;
 
 import javafx.application.Application;
 import javafx.event.EventHandler;
 import javafx.scene.Scene;
 import javafx.scene.canvas.Canvas;
 import javafx.scene.canvas.GraphicsContext;
 import javafx.scene.control.Button;
 import javafx.scene.control.TextArea;
 import javafx.scene.control.TextField;
 import javafx.scene.input.MouseEvent;
 import javafx.scene.layout.BorderPane;
 import javafx.scene.paint.Color;
 import javafx.stage.Stage;
 
 public class App extends Application {
 
     // Constants for the program
     private final static int TEXT_SIZE = 30;
     private final static int CELL_SIZE = 25;
 
     private static int SIZE_ACROSS;
     private static int SIZE_DOWN;
 
     private static int ROWS;
     private static int COLS;
 
     // Define as null so it is a global variable and can be instantiated later
     private WordGrid myGrid = null;
     private WordList myList =  new WordList();
 
     public static void main(String[] args) throws FileNotFoundException {
         launch(args);
     }
 
     @SuppressWarnings("exports")
     @Override
     public void start(Stage primaryStage) throws FileNotFoundException {
         // This function initializes the Garden object to the correct paramters and creates 
         // the GraphicsContext instance that will be the canvas for the garden visual GUI

         ArrayList<String> lines = WordSearch.readFile("words.txt"); 

         String dimensions = lines.remove(0);

         myGrid = new WordGrid(dimensions);
         myGrid.makeGrid();
         String[][] letterGrid = myGrid.getGrid();

         COLS = Integer.valueOf(dimensions.split(" ")[0]);
         ROWS = Integer.valueOf(dimensions.split(" ")[1]);

         SIZE_ACROSS = (COLS + 1) * CELL_SIZE;
         SIZE_DOWN = (ROWS + 1) * CELL_SIZE;

         for (String s : lines) WordSearch.addWord(letterGrid, myList, s);

         //myGrid.fillGrid();

         TextArea command = new TextArea();
 
         Button apply = new Button("Guess");
 
         TextField word = new TextField("Input Word Here");
         TextField info = new TextField("Row, Column, Orientation");
 
         GraphicsContext gc = setupStage(primaryStage, SIZE_ACROSS, SIZE_DOWN, command, 
         apply, word, info);
 
         gridDraw(gc);
 
         primaryStage.show();
         simulateWordSearch(gc, command, apply, word, info);
     }
 
     /**
      * @param gc
      *            GraphicsContext for drawing garden to.
      * @param command
      *            Reference to text area to show simulation commands.
      */
     private void simulateWordSearch(GraphicsContext gc, TextArea command, Button apply, 
     TextField word, TextField info) throws FileNotFoundException {
         // This function runs the garden files, reads the input file and then 
         // updates the Garden object myGarden, which is used to update the GUI
 
         apply.setOnAction((actionEvent) -> {
             String wordStr = word.getText();
             String infoStr = info.getText();
 
             // Get request from TextField and apply it to Garden
             parseInputs(wordStr, infoStr, command, gc);
             word.setText("Input word here");
             info.setText("X, Y, Orientation");
         });

         word.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                word.clear();
            }
         });

         info.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                info.clear();
            }
         });
     }
 
     @SuppressWarnings("exports")
     public void parseInputs(String word, String info, TextArea command, GraphicsContext gc) {
        // This function casts user input requests into WordSearch methods,
        // affecting the word search grid

        String[][] letterGrid = myGrid.getGrid();

        String[] infoList = info.split(", ");
        String row = infoList[0];
        String col = infoList[1];
        String dir = infoList[2];

        if(WordSearch.searchWord(letterGrid, dir, word, col, row)) {
            WordSearch.removeWord(letterGrid, myList, dir, word, col, row);
            command.appendText(word.toLowerCase() + " removed\n");
        } else command.appendText(word.toLowerCase() + " not found\n");

        gridDraw(gc);
        
        if (myList.isEmpty()) {
            command.appendText("All words found!");
        }

     }

     @SuppressWarnings("exports")
     public GraphicsContext setupStage(Stage primaryStage, int canvas_width, int canvas_height, 
        TextArea command, Button apply, TextField word, TextField info) {
         // Border pane will contain canvas for drawing and text area underneath
         BorderPane p = new BorderPane();
 
         // Canvas(pixels across, pixels down)
         Canvas canvas = new Canvas(SIZE_ACROSS, SIZE_DOWN);
 
         // Command TextArea will hold the commands from the file
         command.setPrefWidth(350);
         command.setPrefHeight(TEXT_SIZE * 4);
         command.setEditable(false); 
 
         // Make textField cover length of the app
         word.setPrefSize(150, 20);
         info.setPrefSize(150, 20);
 
         // Place the canvas, button, input, and command output areas in pane.
         p.setTop(canvas);
         p.setRight(apply);
         p.setLeft(word);
         p.setCenter(info);
         p.setBottom(command);
 
         primaryStage.setTitle("Jack Douglass's Word Search Game!");
         primaryStage.setScene(new Scene(p));
 
         return canvas.getGraphicsContext2D();
     }
 
     @SuppressWarnings("exports")
     public void gridReset(GraphicsContext gc) {
         // This function resets the garden to get rid of outdated visual elements
 
         gc.setFill(Color.valueOf("white"));  // Put white canvas over previous elements
         gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
         gc.setFill(Color.valueOf("black"));  // Change color back to black
     }
 
     @SuppressWarnings("exports")
     public void gridDraw(GraphicsContext gc) {
         // This function updates the visual GUI based on the newest Garden object

         gridReset(gc);

         String[][] letterGrid = myGrid.getGrid();

         for (int i = 0; i < letterGrid.length; i++) {
            for (int j = 0; j < letterGrid[i].length; j++) {
                if (i == 0) {
                    gc.setFill(Color.valueOf("gray"));
                    String col = "" + (char) (97 + j);
                    gc.fillText(col, 8.75 + (j + 1) * CELL_SIZE, 17.5);
                } 
                if (j == 0) {
                    gc.setFill(Color.valueOf("gray"));
                    String row = "" + i;
                    if (i < 10) row = "0" + row;
                    gc.fillText(row, 7.5, 17.5 + (i + 1) * CELL_SIZE);
                }
                gc.setFill(Color.valueOf("black"));

                String letter = letterGrid[i][j];
                gc.fillText(letter, 7.5 + (j + 1) * CELL_SIZE, 17.5 + (i + 1) * CELL_SIZE);
            }
         }


     }
 
 }