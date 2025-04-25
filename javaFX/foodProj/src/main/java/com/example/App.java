/*
 * Author: Jack Douglass
 * File: App.java (Short Assignment 09)
 * Class: CSC 210, Fall 2024
 * Purpose: The purpose of this program is to simulate an app ordering Pizza and Sundaes
 * from a restaurant. It does this using a visual GUI using JavaFX software. The app uses
 * buttons, text boxes and text areas to add food to the current food order, remove a 
 * food, calculate the cost, and update the status of the order. This program uses multiple 
 * helper classes to simulate food objects and each food order. This program (App.java) 
 * then implements these to have a functional app. To use this app, the user should run 
 * the program and start a new order. They should then add whatever foods they like from 
 * the available selection. They should then use the buttons to place their order, and then 
 * update the status of their under until it is delivered. They can then begin a new order 
 * if they desire with the "Begin New Food Order" Button.
 */

package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        stage.setTitle("Jack Douglass's Pizzeria and Ice Cream Parlor");

        // we will be using a panel
        BorderPane myPanel = new BorderPane();

        // set up text area
        TextArea myTextArea = new TextArea();
        myTextArea.setPrefHeight(200);
        myTextArea.setEditable(false);
        
        // place text area in the panel
        myPanel.setBottom(myTextArea);

        // grid layout for buttons
        GridPane myGrid = new GridPane();
        myGrid.setPadding(new Insets(10, 10, 10, 10));
        myPanel.setCenter(myGrid);

        // start new order -- add a new button to start a new order
        // this hardcodes one order

        Counter alucard = new Counter();

        Button newOrder = new Button("Begin New Food Order");
        GridPane.setConstraints(newOrder, 0, 1);
        myGrid.getChildren().add(newOrder);

        FoodOrder myOrder = new FoodOrder("0001");
        TextField userEntry = new TextField("No Orders Yet");
        GridPane.setConstraints(userEntry, 0, 3);
        userEntry.setEditable(false);
        myGrid.getChildren().add(userEntry);

        newOrder.setOnAction((actionEvent) -> {
            myTextArea.clear();
            if (alucard.getNum() > 0) myTextArea.appendText("Order Number: 000" + alucard.getNum() + " is finished\n");
            else myTextArea.appendText("First order of the day!\n");
            alucard.incr();
            myOrder.newOrder(alucard.getNum());
            userEntry.setText("Order Number: 000" + alucard.getNum());
            myTextArea.appendText("Order Number: 000" + alucard.getNum() + "\n");
            //myOrder = new FoodOrder("000" + alucard.getNum());
        });

        // place order button
        Button submit = new Button("Place Order");
        GridPane.setConstraints(submit, 1, 0);
        myGrid.getChildren().add(submit);

        submit.setOnAction((actionEvent) -> {
            myTextArea.clear();
            myTextArea.appendText(myOrder.toString());
            if(myOrder.items.isEmpty()) myTextArea.appendText("\nNo food to order, order cannot be placed.");
            else myTextArea.appendText("\nOrder Number: " + myOrder.getOrderNumber() + " is placed.");
        });

        Button cook = new Button("Prepare Food");
        GridPane.setConstraints(cook, 1, 2);
        myGrid.getChildren().add(cook);

        cook.setOnAction((actionEvent) -> {
            myOrder.setStatus(FoodOrder.OrderStatus.READY);
            myTextArea.appendText("\nOrder Number: " + myOrder.getOrderNumber() + " is ready!");
        });

        Button deliver = new Button("Deliver Food");
        GridPane.setConstraints(deliver, 1, 4);
        myGrid.getChildren().add(deliver);

        deliver.setOnAction((actionEvent) -> {
            myOrder.setStatus(FoodOrder.OrderStatus.INROUTE);
            myTextArea.appendText("\nOrder Number: " + myOrder.getOrderNumber() + " is in route.");
        });

        Button give = new Button("Finish Delivery");
        GridPane.setConstraints(give, 1, 6);
        myGrid.getChildren().add(give);

        give.setOnAction((actionEvent) -> {
            myOrder.setStatus(FoodOrder.OrderStatus.INROUTE);
            myTextArea.appendText("\nOrder Number: " + myOrder.getOrderNumber() + " is delivered!");
        });

        // add size and flavor options for pizza
        final ToggleGroup pizzaSize = addPizzaSizeOptions(myGrid);
        final ToggleGroup sundaeSize = addSundaeSizeOptions(myGrid);
        final ToggleGroup pizzaFlavor = addPizzaFlavorOptions(myGrid);
        final ToggleGroup sundaeFlavor = addSundaeFlavorOptions(myGrid);

        // add pizza button
        Button addPizza = new Button("Add Pizza");
        GridPane.setConstraints(addPizza, 3, 6);
        myGrid.getChildren().add(addPizza);

        // action for add pizza button
        addPizza.setOnAction((actionEvent) -> {
            // get selected flavor
            Pizza.Flavor flavor = (Pizza.Flavor) pizzaFlavor.getSelectedToggle().getUserData();
            // get selected size
            Pizza.Size size = (Pizza.Size) pizzaSize.getSelectedToggle().getUserData();
            // call constructor
            Food myFood = new Pizza(flavor, size);
            // add food to order
            myOrder.addFood(myFood);
            // print it out to tell user something happened
            myTextArea.appendText(myFood.toString());
        });

        Button addSundae = new Button("Add Sundae");
        GridPane.setConstraints(addSundae, 5, 6);
        myGrid.getChildren().add(addSundae);

        // action for add pizza button
        addSundae.setOnAction((actionEvent) -> {
            // get selected flavor
            Sundae.Flavor flavor = (Sundae.Flavor) sundaeFlavor.getSelectedToggle().getUserData();
            // get selected size
            Sundae.Size size = (Sundae.Size) sundaeSize.getSelectedToggle().getUserData();
            // call constructor
            Food myFood = new Sundae(flavor, size);
            // add food to order
            myOrder.addFood(myFood);
            // print it out to tell user something happened
            myTextArea.appendText(myFood.toString());
        });

        Button removeFood = new Button("Remove Food"); 
        GridPane.setConstraints(removeFood, 0, 5);
        myGrid.getChildren().add(removeFood);

        removeFood.setOnAction((actionEvent) -> {
            myTextArea.deleteText(42, myTextArea.getLength() - 1);
            myOrder.removeFood(myOrder.getLastFood());
        });

        scene = new Scene(myPanel, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private ToggleGroup addPizzaSizeOptions(GridPane myGrid) {
        // set up group so that only one option is selected
        final ToggleGroup groupSize = new ToggleGroup();

        // create small radio option
        RadioButton small = new RadioButton("Small");
        small.setSelected(true); // set selected to avoid errors
        // add it to group
        small.setToggleGroup(groupSize);
        // set up user data to be enum option from Pizza class
        small.setUserData(Pizza.Size.PERSONAL);
        // position option and add it to the grid
        GridPane.setConstraints(small, 2, 0);
        myGrid.getChildren().add(small);

        RadioButton medium = new RadioButton("Medium");
        medium.setToggleGroup(groupSize);
        medium.setUserData(Pizza.Size.MEDIUM);
        GridPane.setConstraints(medium, 2, 2);
        myGrid.getChildren().add(medium);

        RadioButton large = new RadioButton("Large");
        large.setToggleGroup(groupSize);
        large.setUserData(Pizza.Size.LARGE);
        GridPane.setConstraints(large, 2, 4);
        myGrid.getChildren().add(large);

        return groupSize;
    }

    private ToggleGroup addSundaeSizeOptions(GridPane myGrid) {
        // set up group so that only one option is selected
        final ToggleGroup groupSize = new ToggleGroup();

        // create small radio option
        RadioButton small = new RadioButton("One Scoop");
        small.setSelected(true); // set selected to avoid errors
        // add it to group
        small.setToggleGroup(groupSize);
        // set up user data to be enum option from Pizza class
        small.setUserData(Sundae.Size.ONE_SCOOP);
        // position option and add it to the grid
        GridPane.setConstraints(small, 4, 0);
        myGrid.getChildren().add(small);

        RadioButton medium = new RadioButton("Two Scoops");
        medium.setToggleGroup(groupSize);
        medium.setUserData(Sundae.Size.TWO_SCOOPS);
        GridPane.setConstraints(medium, 4, 2);
        myGrid.getChildren().add(medium);

        RadioButton large = new RadioButton("Three Scoops");
        large.setToggleGroup(groupSize);
        large.setUserData(Sundae.Size.THREE_SCOOPS);
        GridPane.setConstraints(large, 4, 4);
        myGrid.getChildren().add(large);

        return groupSize;
    }

    private ToggleGroup addPizzaFlavorOptions(GridPane myGrid) {
        // set up group so that only one option is selected
        final ToggleGroup groupFlavor = new ToggleGroup();

        // create cheese radio option
        RadioButton cheese = new RadioButton("Cheese");
        cheese.setSelected(true); // set selected to avoid errors
        // add it to group
        cheese.setToggleGroup(groupFlavor);
        // set up user data to be enum option from Pizza class
        cheese.setUserData(Pizza.Flavor.CHEESE);
        // position option and add it to the grid
        GridPane.setConstraints(cheese, 3, 0);
        myGrid.getChildren().add(cheese);

        RadioButton pepperoni = new RadioButton("Pepperoni");
        pepperoni.setToggleGroup(groupFlavor);
        pepperoni.setUserData(Pizza.Flavor.PEPPERONI);
        GridPane.setConstraints(pepperoni, 3, 2);
        myGrid.getChildren().add(pepperoni);

        RadioButton hawaiian = new RadioButton("Hawaiian");
        hawaiian.setToggleGroup(groupFlavor);
        hawaiian.setUserData(Pizza.Flavor.HAWAIIAN);
        GridPane.setConstraints(hawaiian, 3, 4);
        myGrid.getChildren().add(hawaiian);

        return groupFlavor;
    }

    @SuppressWarnings("exports")
    public ToggleGroup addSundaeFlavorOptions(GridPane myGrid) {
        final ToggleGroup groupFlavor = new ToggleGroup();

        RadioButton vanilla = new RadioButton("Vanilla");
        vanilla.setSelected(true);
        vanilla.setToggleGroup(groupFlavor);
        vanilla.setUserData(Sundae.Flavor.VANILLA);
        GridPane.setConstraints(vanilla, 5, 0);
        myGrid.getChildren().add(vanilla);

        RadioButton chocolate = new RadioButton("Chocolate");
        chocolate.setToggleGroup(groupFlavor);
        chocolate.setUserData(Sundae.Flavor.CHOCOLATE);
        GridPane.setConstraints(chocolate, 5, 2);
        myGrid.getChildren().add(chocolate);

        RadioButton bSplit = new RadioButton("Banana Split");
        bSplit.setToggleGroup(groupFlavor);
        bSplit.setUserData(Sundae.Flavor.BANANA_SPLIT);
        GridPane.setConstraints(bSplit, 5, 4);
        myGrid.getChildren().add(bSplit);

        return groupFlavor;
    }

    public static void main(String[] args) {
        launch();
    }

}