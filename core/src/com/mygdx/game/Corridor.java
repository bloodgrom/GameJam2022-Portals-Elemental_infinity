package com.mygdx.game;

public class Corridor {
    public int startCoordX;
    public int startCoordY;
    public int width;
    public int length;

    public Corridor(int startCoordX, int startCoordY, int width) {
      
        this.startCoordX = startCoordX;
        this.startCoordY = startCoordY;
        this.width = width;
    }

    public static double calculateDistanceBetweenPoints(double x1, double y1, double x2, double y2) {       
    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
}
}

