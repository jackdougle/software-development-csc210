/*
 * Author: Jack Douglass
 * File: Brush.java
 * Class: CSC 210, Fall 2024
 * Purpose: The purpose of this program is to simulate a brush in a 
 * digital paint program. The brush has multiple types each with different 
 * functions. This program is intented to be used in "App.java", which is 
 * an aforementioned digital paint program. To use this class, the user 
 * should run App.java and draw on the blank white canvas by dragging their
 * mouse along it. They can modify the brush by clicking on the different 
 * shapes and brush type buttons. These include a square brush, a triangle 
 * brush, a pen, and stencil, and more.
 */

package com.example;

import javafx.scene.paint.Color;

public class Brush {
    private int size;
    private Color color;
    public enum BrushType { CIRCLE, SQUARE, TRIANGLE, 
        STENCIL, POLYGON3, POLYGON4, PEN }
    private BrushType bType;
    private Double[] coords;

    public Brush() {
        size = 10;
        color = Color.BLACK;
        bType = BrushType.CIRCLE;
    }

    @SuppressWarnings("exports")
    public void setColor(Color newColor) {
        color = newColor;
    }

    public void setType(BrushType newType) {
        bType = newType;
    }

    public void setSize(int s) {
        size = s;
    }

    public void incr() {
        if (size < 100) size += 1;
    }

    public void decr() {
        if (size > 1) size -= 1;
    }

    public void setCoords(Double[] c) {
        coords = c;
    }

    @SuppressWarnings("exports")
    public Color getColor() {
        return color;
    }

    public BrushType getType() {
        return bType;
    }

    public int getSize() {
        return size;
    }

    public Double[] getCoords() {
        return coords;
    }

}
