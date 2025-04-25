/* Author: Jack Douglass
 * File: App.java (Short Assignment 10)
 * Class: CSC 210, Fall 2024
 * Purpose: The purpose of this program is to serve as a digital paint 
 * program that allows the user to draw many things and create digital 
 * art. This program has several capabilities, as it can make text 
 * stencils, make custom polygons that can be drawn with, and other novel 
 * features like a pen brush function. This program has twelve colors to 
 * choose from and a clear canvas and eraser tool. It uses JavaFX to create 
 * an interactable visual GUI for the user. To use this program, the user 
 * should run this program (via the run button in the top right corner, 
 * above the main function, or using the terminal) and then the visual 
 * GUI will load in. Once the GUI is loaded, the user should then draw on 
 * the canvas by dragging their mouse across and select their desired brush 
 * type and color by pressing on the buttons around the screen. The user can 
 * hit the clear canvas button or the eraser button to undo a mistake or 
 * start over.
 */
package com.example;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;

/*
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private Brush myBrush;
    private TextField stencilInput;

    public static void main(String[] args) {
        launch();
    }

    private void drawSomething(int x, int y, Group group, TextField inpt) {
        double size = myBrush.getSize();
        Color col = myBrush.getColor();
        switch (myBrush.getType()) {
        case TRIANGLE:
            Polygon tri = new Polygon();
            tri.setFill(col);
            tri.getPoints().addAll(new Double[] {
                0.0, -size, -size, size, size, size});
            tri.setLayoutX(x);
            tri.setLayoutY(y);
            group.getChildren().add(tri);
            break;
        case SQUARE:
            Rectangle rect = new Rectangle();
            rect.setFill(col);
            rect.setWidth(size * 2);
            rect.setHeight(size * 2);
            rect.setLayoutX(x - size);
            rect.setLayoutY(y - size);
            group.getChildren().add(rect);
            break;
        case STENCIL:
            Text txt = new Text(inpt.getText());
            txt.setFill(col);
            txt.setFont(Font.font("Verdana", myBrush.getSize()));
            txt.setLayoutX(x);
            txt.setLayoutY(y);
            group.getChildren().add(txt);
            break;
        case POLYGON3:
            Double[] c3 = myBrush.getCoords();
            Polygon three = new Polygon();
            three.setFill(col);
            three.getPoints().addAll(new Double[] {
                0.0, 0.0, c3[2] - c3[0], c3[3] - c3[1], 
                c3[4] - c3[0], c3[5] - c3[1]});
            three.setLayoutX(x);
            three.setLayoutY(y);
            group.getChildren().add(three);
            break;
        case POLYGON4:
            Double[] c4 = myBrush.getCoords();
            Polygon four = new Polygon();
            four.setFill(col);
            four.getPoints().addAll(new Double[] {
                0.0, 0.0, c4[2] - c4[0], c4[3] - c4[1], c4[4] - c4[0], 
                c4[5] - c4[1], c4[6] - c4[0], c4[7] - c4[1]});
            four.setLayoutX(x);
            four.setLayoutY(y);
            group.getChildren().add(four);
            break;
        case PEN:
            Rectangle pen = new Rectangle();
            pen.setFill(col);
            pen.setWidth(2);
            pen.setHeight(20);
            pen.setLayoutX(x);
            pen.setLayoutY(y);
        group.getChildren().add(pen);
        break;
        default:
            Circle circ = new Circle();
            circ.setFill(col);
            circ.setRadius(size);
            circ.setCenterX(x);
            circ.setCenterY(y);
            group.getChildren().add(circ);
        }
    }

    private void drawDot(double x, double y, Group group) {
        Circle circ = new Circle();
        circ.setFill(myBrush.getColor());
        circ.setRadius(2);
        circ.setCenterX(x);
        circ.setCenterY(y);
        group.getChildren().add(circ);
    }

    private void reset(Group root) {
        Rectangle newCanvas = new Rectangle();
        newCanvas.setFill(Color.WHITE);
        newCanvas.setWidth(640);
        newCanvas.setHeight(480);
        newCanvas.setLayoutX(0);
        newCanvas.setLayoutY(0);
        root.getChildren().add(newCanvas);
        addButtons(root);
        addColors(root);
        addShapes(root);
    }

    private void addShapes(Group group) {
        Rectangle rect = new Rectangle();
        rect.setFill(myBrush.getColor());
        rect.setWidth(30);
        rect.setHeight(30);
        rect.setLayoutX(445);
        rect.setLayoutY(5);
        rect.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                myBrush.setType(Brush.BrushType.SQUARE);
            }
        });
        group.getChildren().add(rect);

        Polygon tri = new Polygon();
        tri.setFill(myBrush.getColor());
        tri.getPoints().addAll(new Double[] {
            10.0, -15.0, -5.0, 15.0, 25.0, 15.0});
        tri.setLayoutX(406);
        tri.setLayoutY(20);
        tri.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                myBrush.setType(Brush.BrushType.TRIANGLE);
            }
        });
        group.getChildren().add(tri);

        Circle circ = new Circle();
        circ.setFill(myBrush.getColor());
        circ.setRadius(15);
        circ.setCenterX(503);
        circ.setCenterY(20);
        circ.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                myBrush.setType(Brush.BrushType.CIRCLE);
            }
        });
        group.getChildren().add(circ);

        Button pen = new Button("Pen");
        pen.setLayoutX(534);
        pen.setLayoutY(5);

        pen.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                myBrush.setType(Brush.BrushType.PEN);
            }
        });
        group.getChildren().add(pen);
    }

    private void addColors(Group group) {
        Color[] cols = {Color.AQUA, Color.BLUE, Color.PURPLE, Color.PINK, Color.RED, Color.ORANGE,
            Color.YELLOW, Color.YELLOWGREEN, Color.GREEN, Color.BEIGE, Color.BROWN, Color.BLACK};
        for (int i = 0; i < 12; i++) {
            Circle temp = new Circle();
            Color c = cols[i];
            temp.setFill(c);
            temp.setRadius(15);
            temp.setLayoutX(20);
            temp.setLayoutY(45 + (35 * i));
            temp.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    myBrush.setColor(c);
                    addShapes(group);
                }
            });
            group.getChildren().add(temp);
        }
    }

    private void addButtons(Group root) {
        Button increment = new Button("Increase size");
        increment.setLayoutX(45);
        increment.setLayoutY(5);

        Button decrement = new Button("Decrease size");
        decrement.setLayoutX(140);
        decrement.setLayoutY(5);

        Button eraser = new Button("Eraser");
        eraser.setLayoutX(241);
        eraser.setLayoutY(5);

        Button clear = new Button("Clear canvas");
        clear.setLayoutX(300);
        clear.setLayoutY(5);

        Button stencil = new Button ("Add text");
        stencil.setLayoutX(45);
        stencil.setLayoutY(448);

        stencilInput = new TextField("Input stencil text here");
        stencilInput.setLayoutX(115);
        stencilInput.setLayoutY(448);

        Button custom3 = new Button ("Custom 3-Sided Polygon");
        custom3.setLayoutX(281);
        custom3.setLayoutY(448);

        Button custom4 = new Button ("Custom 4-Sided Polygon");
        custom4.setLayoutX(442);
        custom4.setLayoutY(448);

        Button backdrop = new Button();
        backdrop.setPrefWidth(39);
        backdrop.setPrefHeight(430);
        backdrop.setLayoutX(1);
        backdrop.setLayoutY(22);
        root.getChildren().add(backdrop);
        increment.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                myBrush.incr();
            }
        });
        decrement.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                myBrush.decr();
            }
        });
        eraser.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                myBrush.setColor(Color.WHITE);
            }
        });
        clear.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                reset(root);
            }
        });
        stencil.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                myBrush.setType(Brush.BrushType.STENCIL);
            }
        });
        custom3.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                make3SidedPolygon(root);
            }
        });
        custom4.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                make4SidedPolygon(root);
            }
        });

        root.getChildren().add(increment);
        root.getChildren().add(decrement);
        root.getChildren().add(eraser);
        root.getChildren().add(clear);
        root.getChildren().add(stencil);
        root.getChildren().add(stencilInput);
        root.getChildren().add(custom3);
        root.getChildren().add(custom4);
    }

    private void make3SidedPolygon(Group group) {
        Double[] coords = new Double[8];
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            int i = -1;
            public void handle(MouseEvent arg0) {
                double x = arg0.getX(); double y = arg0.getY();
                if (y > 45 && y < 425 && x > 50) {
                    if (i < 3) drawDot(x, y, group);
                    i += 1;
                }
                if (i == 0) {
                    coords[0] = x;
                    coords[1] = y;
                } else if (i == 1) {
                    coords[2] = x;
                    coords[3] = y;
                } else if (i == 2) {
                    coords[4] = x;
                    coords[5] = y;
                    myBrush.setCoords(coords);
                    myBrush.setType(Brush.BrushType.POLYGON3);
                }
            }
        });

    }

    private void make4SidedPolygon(Group group) {
        Double[] coords = new Double[8];
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            int i = -1;
            public void handle(MouseEvent arg0) {
                double x = arg0.getX(); double y = arg0.getY();
                if (y > 45 && y < 425 && x > 50) {
                    if (i < 3) drawDot(x, y, group);
                    i += 1;
                }
                if (i == 0) {
                    coords[0] = x;
                    coords[1] = y;
                } else if (i == 1) {
                    coords[2] = x;
                    coords[3] = y;
                } else if (i == 2) {
                    coords[4] = x;
                    coords[5] = y;
                } else if (i == 3) {
                    coords[6] = x;
                    coords[7] = y;
                    myBrush.setCoords(coords);
                    myBrush.setType(Brush.BrushType.POLYGON4);
                }
            }
        });
    }
    
    @Override
    @SuppressWarnings("exports")
    public void start(Stage stage) throws IOException {
        stage.setTitle("Jack Douglass's Paint Program! (Short Assignment 10)");

        Group root = new Group();
        myBrush = new Brush();
        myBrush.setColor(Color.BLACK);

        scene = new Scene(root, 640, 480);

        addButtons(root);
        addShapes(root);
        addColors(root);

        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                int xCoord = (int) arg0.getX();
                int yCoord = (int) arg0.getY();
                if (yCoord > 45 && yCoord < 425 && xCoord > 50) drawSomething(xCoord, yCoord, root, stencilInput);
            }
        });

        stage.setScene(scene);
        stage.show();
    }
   
}